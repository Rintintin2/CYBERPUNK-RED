package com.example.core.rules

data class InterfaceCheckResult(
    val interfaceRank: Int,
    val roll: D10RollOutcome,
    val total: Int,
    val difficultyValue: Int,
    val succeeded: Boolean
)

data class RezDamageResult(
    val damageDice: Nd6RollOutcome,
    val rezBefore: Int,
    val rezAfter: Int,
    val derezzed: Boolean
)

/**
 * Deterministic Netrunning action resolution (Interface + 1d10 vs DV, Program/ICE REZ damage).
 */
class NetrunningEngine(
    private val diceEngine: DiceEngine
) {
    fun performInterfaceCheck(
        interfaceRank: Int,
        modifier: Int = 0,
        difficultyValue: Int
    ): InterfaceCheckResult {
        val roll = diceEngine.rollD10Exploding()
        val total = interfaceRank + modifier + roll.netDieTotal
        return InterfaceCheckResult(
            interfaceRank = interfaceRank,
            roll = roll,
            total = total,
            difficultyValue = difficultyValue,
            succeeded = total > difficultyValue
        )
    }

    fun applyRezDamage(
        damageDiceCount: Int,
        currentRez: Int
    ): RezDamageResult {
        val roll = diceEngine.rollNd6(damageDiceCount)
        val after = (currentRez - roll.total).coerceAtLeast(0)
        return RezDamageResult(
            damageDice = roll,
            rezBefore = currentRez,
            rezAfter = after,
            derezzed = after == 0
        )
    }
}
