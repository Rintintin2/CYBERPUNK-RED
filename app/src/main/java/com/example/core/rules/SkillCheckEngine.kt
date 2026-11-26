package com.example.core.rules

data class SkillCheckRequest(
    val stat: RedStat,
    val statValue: Int,
    val skillName: String,
    val skillLevel: Int,
    val luckSpent: Int = 0,
    val modifier: Int = 0,
    val difficultyValue: Int
)

data class SkillCheckResult(
    val request: SkillCheckRequest,
    val roll: D10RollOutcome,
    val total: Int,
    val succeeded: Boolean,
    val margin: Int
)

data class InitiativeResult(
    val reflexes: Int,
    val dieRoll: Int,
    val total: Int
)

/**
 * Deterministic Cyberpunk RED Skill Check & Initiative resolution.
 * Official rule: Total = STAT + SKILL + LUCK + MODIFIERS + 1d10 (exploding 10 / imploding 1).
 * A check succeeds when Total > DV (you must BEAT the DV).
 */
class SkillCheckEngine(
    private val diceEngine: DiceEngine
) {
    fun performSkillCheck(request: SkillCheckRequest): SkillCheckResult {
        val roll = diceEngine.rollD10Exploding()
        val total = request.statValue +
            request.skillLevel +
            request.luckSpent.coerceAtLeast(0) +
            request.modifier +
            roll.netDieTotal
        val margin = total - request.difficultyValue
        return SkillCheckResult(
            request = request,
            roll = roll,
            total = total,
            succeeded = total > request.difficultyValue,
            margin = margin
        )
    }

    fun rollInitiative(reflexes: Int): InitiativeResult {
        val die = diceEngine.rollPlainD10()
        return InitiativeResult(
            reflexes = reflexes,
            dieRoll = die,
            total = reflexes + die
        )
    }
}
