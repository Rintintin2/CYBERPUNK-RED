package com.example.core.rules

import kotlin.math.ceil
import kotlin.math.floor

/**
 * Deterministic derived stat calculator following official Cyberpunk RED formulas.
 */
object CharacterCalculator {

    /**
     * Official formula: Max HP = 10 + (5 * ceil((BODY + WILL) / 2))
     */
    fun calculateMaxHp(body: Int, willpower: Int): Int {
        val avgRoundedUp = ceil((body.coerceAtLeast(1) + willpower.coerceAtLeast(1)) / 2.0).toInt()
        return 10 + (5 * avgRoundedUp)
    }

    /**
     * Official formula: Seriously Wounded Threshold = ceil(Max HP / 2)
     */
    fun calculateSeriouslyWoundedThreshold(maxHp: Int): Int {
        return ceil(maxHp / 2.0).toInt()
    }

    /**
     * Official formula: Max Humanity = EMP * 10
     */
    fun calculateMaxHumanity(empathyMax: Int): Int {
        return empathyMax.coerceAtLeast(0) * 10
    }

    /**
     * Official formula: Current EMP = floor(Current Humanity / 10), minimum 0.
     */
    fun calculateCurrentEmpathy(currentHumanity: Int): Int {
        return floor(currentHumanity.coerceAtLeast(0) / 10.0).toInt()
    }

    /**
     * Official formula (p. 230): Each installed cyberware with HL > 0 reduces max Humanity by 2
     * (4 for Borgware).
     */
    fun calculateEffectiveMaxHumanity(
        empathyMax: Int,
        cyberwareWithHlCount: Int,
        borgwareWithHlCount: Int
    ): Int {
        val base = calculateMaxHumanity(empathyMax)
        val reduction = (cyberwareWithHlCount.coerceAtLeast(0) * 2) +
            (borgwareWithHlCount.coerceAtLeast(0) * 4)
        return (base - reduction).coerceAtLeast(0)
    }

    fun evaluateWoundState(
        currentHp: Int,
        maxHp: Int,
        isDead: Boolean = false
    ): WoundState {
        if (isDead) return WoundState.DEAD
        if (currentHp <= 0) return WoundState.MORTALLY_WOUNDED
        val threshold = calculateSeriouslyWoundedThreshold(maxHp)
        return when {
            currentHp >= maxHp -> WoundState.UNINJURED
            currentHp <= threshold -> WoundState.SERIOUSLY_WOUNDED
            else -> WoundState.LIGHTLY_WOUNDED
        }
    }

    fun computeArmorPenalty(
        headArmorSp: Int,
        bodyArmorSp: Int,
        overrideArmorPenalty: Int? = null
    ): Int {
        if (overrideArmorPenalty != null) return overrideArmorPenalty
        val maxSp = maxOf(headArmorSp, bodyArmorSp)
        return when {
            maxSp >= 15 -> -4
            maxSp >= 12 -> -2
            else -> 0
        }
    }

    fun computeDerivedMetrics(
        stats: CharacterStats,
        currentHp: Int,
        totalHumanityLoss: Int,
        maxHumanityReduction: Int = 0,
        isDead: Boolean = false,
        headArmorSp: Int = 0,
        bodyArmorSp: Int = 0,
        installedCyberware: List<com.example.data.local.entity.CyberwareEntity> = emptyList(),
        overrideArmorPenalty: Int? = null
    ): DerivedCharacterMetrics {
        val effectiveBody = if (installedCyberware.isNotEmpty()) {
            CyberwareValidator.computeEffectiveBody(stats.body, installedCyberware)
        } else {
            stats.body
        }
        val effectiveMaxHumReduction = if (maxHumanityReduction == 0 && installedCyberware.isNotEmpty()) {
            installedCyberware
                .filter { it.installed && it.humanityCost > 0 }
                .sumOf { if (CyberwareValidator.isBorgware(it)) 4 else 2 }
        } else {
            maxHumanityReduction.coerceAtLeast(0)
        }
        val maxHp = calculateMaxHp(effectiveBody, stats.willpower)
        val threshold = calculateSeriouslyWoundedThreshold(maxHp)
        val rawMaxHumanity = calculateMaxHumanity(stats.empathyMax)
        val effectiveMaxHumanity = (rawMaxHumanity - effectiveMaxHumReduction).coerceAtLeast(0)
        val currentHumanity = (rawMaxHumanity - totalHumanityLoss.coerceAtLeast(0))
            .coerceIn(0, effectiveMaxHumanity)
        val currentEmp = calculateCurrentEmpathy(currentHumanity)
        val woundState = evaluateWoundState(currentHp = currentHp, maxHp = maxHp, isDead = isDead)
        val painEditorActive = CyberwareValidator.hasPainEditor(installedCyberware)
        val effectiveWoundPenalty = if (painEditorActive && woundState == WoundState.SERIOUSLY_WOUNDED) {
            0
        } else {
            woundState.actionPenalty
        }
        val armorPenalty = computeArmorPenalty(headArmorSp, bodyArmorSp, overrideArmorPenalty)
        return DerivedCharacterMetrics(
            maxHp = maxHp,
            seriouslyWoundedThreshold = threshold,
            deathSaveBase = effectiveBody,
            maxHumanity = effectiveMaxHumanity,
            currentHumanity = currentHumanity,
            currentEmpathy = currentEmp,
            woundState = woundState,
            woundPenalty = woundState.actionPenalty,
            armorPenalty = armorPenalty,
            effectiveWoundPenalty = effectiveWoundPenalty,
            painEditorActive = painEditorActive,
            effectiveBody = effectiveBody
        )
    }
}
