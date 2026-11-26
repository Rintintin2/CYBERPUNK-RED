package com.example.core.rules

import kotlin.math.ceil

data class DamageApplicationResult(
    val damageDiceOutcome: Nd6RollOutcome,
    val stoppingPowerBefore: Int,
    val effectiveStoppingPower: Int,
    val stoppingPowerAfter: Int,
    val penetratedArmor: Boolean,
    val hpDamageDealt: Int,
    val hpAfter: Int,
    val criticalInjuryTriggered: Boolean,
    val criticalInjury: CriticalInjuryDefinition? = null,
    val woundStateAfter: WoundState
)

data class DeathSaveResult(
    val dieRoll: Int,
    val penaltyApplied: Int,
    val effectiveRoll: Int,
    val bodyTarget: Int,
    val survived: Boolean
)

data class FacedownResult(
    val playerRoll: D10RollOutcome,
    val playerTotal: Int,
    val opponentRoll: D10RollOutcome,
    val opponentTotal: Int,
    val playerWon: Boolean,
    val isTie: Boolean
)

data class CoverDamageResult(
    val damageDiceOutcome: Nd6RollOutcome,
    val coverHpBefore: Int,
    val coverHpAfter: Int,
    val coverDestroyed: Boolean
)

enum class WeaponQuality(
    val labelPt: String,
    val attackModifier: Int,
    val jamsOnNaturalOne: Boolean
) {
    POOR("Baixa Qualidade", 0, true),
    STANDARD("Qualidade Padrão", 0, false),
    EXCELLENT("Qualidade Excelente", 1, false);

    /**
     * Core Rulebook p. 342: Poor/Excellent weapon pricing scaled from Standard base price.
     */
    fun adjustedPriceEb(standardCostEb: Int): Int = when (this) {
        STANDARD -> standardCostEb
        POOR -> when {
            standardCostEb <= 50 -> 20
            standardCostEb <= 100 -> 50
            standardCostEb <= 500 -> 100
            standardCostEb <= 1000 -> 500
            else -> standardCostEb / 2
        }
        EXCELLENT -> when {
            standardCostEb <= 50 -> 100
            standardCostEb <= 100 -> 500
            standardCostEb <= 500 -> 1000
            standardCostEb <= 1000 -> 5000
            else -> standardCostEb
        }
    }
}

data class SmartAmmoRerollResult(
    val dieRoll: D10RollOutcome,
    val luckStat: Int,
    val total: Int,
    val targetDv: Int,
    val hit: Boolean
)

/**
 * Deterministic Combat damage, SP ablation, Critical Injuries, Facedown, Cover,
 * and Death Save rules for Cyberpunk RED (Core Rulebook pp. 168–189, 222, 342–347).
 */
class CombatEngine(
    private val diceEngine: DiceEngine
) {
    /**
     * Official Cyberpunk RED damage resolution:
     * - Melee Weapons & Martial Arts ignore HALF of defender SP (rounded up: ceil(SP / 2)).
     * - Brawling does NOT halve SP.
     * - If rawDamage > effectiveSP: excess deals HP damage (doubled for Headshots) and ablates actual SP by ablateAmount (default 1; Armor-Piercing = 2; Rubber = 0).
     * - Critical Injury (2+ sixes on damage dice, unless canTriggerCritical is false e.g. Rubber Ammo):
     *   deals +5 direct Bonus Damage AND triggers a 2d6 Critical Injury regardless of armor penetration.
     * - Rubber Ammo (nonLethalMinOneHp): cannot drop a target with > 1 HP below 1 HP.
     */
    fun applyWeaponDamage(
        damageDiceCount: Int,
        flatBonus: Int = 0,
        targetCurrentHp: Int,
        targetMaxHp: Int,
        targetArmorSp: Int,
        isHeadshot: Boolean = false,
        ignoreHalfArmor: Boolean = false,
        ablateAmount: Int = 1,
        canTriggerCritical: Boolean = true,
        nonLethalMinOneHp: Boolean = false
    ): DamageApplicationResult {
        val roll = diceEngine.rollNd6(damageDiceCount)
        val rawDamage = (roll.total + flatBonus).coerceAtLeast(0)
        val sp = targetArmorSp.coerceAtLeast(0)
        val effectiveSp = if (ignoreHalfArmor) ceil(sp / 2.0).toInt() else sp
        val penetrated = rawDamage > effectiveSp

        val penetratedDamage = if (penetrated) {
            val baseThrough = rawDamage - effectiveSp
            if (isHeadshot) baseThrough * 2 else baseThrough
        } else {
            0
        }

        val critTriggered = canTriggerCritical && roll.triggersCriticalInjury
        val criticalBonus = if (critTriggered) 5 else 0
        val criticalInjury = if (critTriggered) {
            OfficialCriticalInjuries.rollCriticalInjury(diceEngine, isHead = isHeadshot)
        } else {
            null
        }

        val totalHpLoss = penetratedDamage + criticalBonus
        val actualAblate = if (penetrated && sp > 0) ablateAmount.coerceAtLeast(0) else 0
        val spAfter = (sp - actualAblate).coerceAtLeast(0)
        val minHp = if (nonLethalMinOneHp && targetCurrentHp > 1) 1 else 0
        val hpAfter = (targetCurrentHp - totalHpLoss).coerceAtLeast(minHp)
        val effectiveHpLoss = (targetCurrentHp - hpAfter).coerceAtLeast(0)
        val woundAfter = CharacterCalculator.evaluateWoundState(
            currentHp = hpAfter,
            maxHp = targetMaxHp,
            isDead = false
        )

        return DamageApplicationResult(
            damageDiceOutcome = roll,
            stoppingPowerBefore = sp,
            effectiveStoppingPower = effectiveSp,
            stoppingPowerAfter = spAfter,
            penetratedArmor = penetrated,
            hpDamageDealt = effectiveHpLoss,
            hpAfter = hpAfter,
            criticalInjuryTriggered = critTriggered,
            criticalInjury = criticalInjury,
            woundStateAfter = woundAfter
        )
    }

    /**
     * Core Rulebook p. 346 (Smart Ammunition):
     * If a Single Shot misses its DV by 4 or less, reroll with 1d10 + 10 + LUCK (requires Targeting Scope).
     */
    fun resolveSmartAmmoSecondChance(
        luckStat: Int,
        targetDv: Int
    ): SmartAmmoRerollResult {
        val roll = diceEngine.rollD10Exploding()
        val total = 10 + luckStat + roll.netDieTotal
        return SmartAmmoRerollResult(
            dieRoll = roll,
            luckStat = luckStat,
            total = total,
            targetDv = targetDv,
            hit = total > targetDv
        )
    }

    /**
     * Official Cyberpunk RED Death Save (p. 187 / p. 222):
     * Roll 1d10 + deathSavePenalty. Succeeds if result < BODY. Natural 10 always fails.
     */
    fun rollDeathSave(
        bodyStat: Int,
        currentDeathSavePenalty: Int
    ): DeathSaveResult {
        val die = diceEngine.rollPlainD10()
        val penalty = currentDeathSavePenalty.coerceAtLeast(0)
        val effective = die + penalty
        val survived = die != 10 && effective < bodyStat
        return DeathSaveResult(
            dieRoll = die,
            penaltyApplied = penalty,
            effectiveRoll = effective,
            bodyTarget = bodyStat,
            survived = survived
        )
    }

    /**
     * Official Bullet Dodging prerequisite (p. 172): REF 8 or higher allows defending against Ranged/Explosion attacks
     * with DEX + Evasion + 1d10.
     */
    fun canDodgeBullets(reflexes: Int): Boolean = reflexes >= 8

    /**
     * Official Facedown (p. 194 / Combat p. 170): COOL + Reputation + 1d10 contested check.
     */
    fun resolveFacedown(
        playerCool: Int,
        playerReputation: Int,
        opponentCool: Int,
        opponentReputation: Int
    ): FacedownResult {
        val pRoll = diceEngine.rollD10Exploding()
        val oRoll = diceEngine.rollD10Exploding()
        val pTotal = playerCool + playerReputation + pRoll.netDieTotal
        val oTotal = opponentCool + opponentReputation + oRoll.netDieTotal
        return FacedownResult(
            playerRoll = pRoll,
            playerTotal = pTotal,
            opponentRoll = oRoll,
            opponentTotal = oTotal,
            playerWon = pTotal > oTotal,
            isTie = pTotal == oTotal
        )
    }

    fun applyCoverDamage(
        damageDiceCount: Int,
        flatBonus: Int = 0,
        coverCurrentHp: Int
    ): CoverDamageResult {
        val roll = diceEngine.rollNd6(damageDiceCount)
        val total = (roll.total + flatBonus).coerceAtLeast(0)
        val before = coverCurrentHp.coerceAtLeast(0)
        val after = (before - total).coerceAtLeast(0)
        return CoverDamageResult(
            damageDiceOutcome = roll,
            coverHpBefore = before,
            coverHpAfter = after,
            coverDestroyed = after == 0
        )
    }
}

