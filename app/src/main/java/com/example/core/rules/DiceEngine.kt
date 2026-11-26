package com.example.core.rules

import kotlin.random.Random

/**
 * Deterministic dice rolling contract.
 * Kept strictly in pure Kotlin with zero AI or Android framework dependencies.
 */
interface DiceRoller {
    fun rollDie(sides: Int): Int
}

class RandomDiceRoller(
    private val random: Random = Random.Default
) : DiceRoller {
    override fun rollDie(sides: Int): Int {
        require(sides >= 2) { "O dado deve ter ao menos 2 lados." }
        return random.nextInt(1, sides + 1)
    }
}

/**
 * Scripted roller for deterministic unit tests and reproducible combat/skill simulations.
 */
class ScriptedDiceRoller(
    private val scriptedRolls: List<Int>
) : DiceRoller {
    private var cursor = 0

    override fun rollDie(sides: Int): Int {
        if (scriptedRolls.isEmpty()) return 1
        val value = scriptedRolls[cursor % scriptedRolls.size]
        cursor++
        return value.coerceIn(1, sides)
    }
}

enum class CriticalMarker {
    NONE,
    CRITICAL_SUCCESS,
    CRITICAL_FAILURE
}

data class D10RollOutcome(
    val initialDie: Int,
    val secondaryDie: Int?,
    val marker: CriticalMarker,
    val netDieTotal: Int
)

data class Nd6RollOutcome(
    val dice: List<Int>,
    val total: Int,
    val sixCount: Int,
    val triggersCriticalInjury: Boolean
)

/**
 * Official Cyberpunk RED dice resolution:
 * - 1d10: natural 10 explodes (+ 1d10), natural 1 implodes (- 1d10).
 * - Nd6 damage/humanity: 2 or more 6s trigger a Critical Injury (in damage rolls).
 */
class DiceEngine(
    private val roller: DiceRoller = RandomDiceRoller()
) {
    fun rollD10Exploding(): D10RollOutcome {
        val first = roller.rollDie(10)
        return when (first) {
            10 -> {
                val second = roller.rollDie(10)
                D10RollOutcome(
                    initialDie = 10,
                    secondaryDie = second,
                    marker = CriticalMarker.CRITICAL_SUCCESS,
                    netDieTotal = 10 + second
                )
            }
            1 -> {
                val second = roller.rollDie(10)
                D10RollOutcome(
                    initialDie = 1,
                    secondaryDie = second,
                    marker = CriticalMarker.CRITICAL_FAILURE,
                    netDieTotal = 1 - second
                )
            }
            else -> D10RollOutcome(
                initialDie = first,
                secondaryDie = null,
                marker = CriticalMarker.NONE,
                netDieTotal = first
            )
        }
    }

    fun rollPlainD10(): Int = roller.rollDie(10)

    fun rollD6(): Int = roller.rollDie(6)

    fun rollD100(): Int = roller.rollDie(100)

    fun rollNd6(count: Int): Nd6RollOutcome {
        require(count >= 1) { "Quantidade de dados deve ser >= 1." }
        val dice = List(count) { roller.rollDie(6) }
        val sixes = dice.count { it == 6 }
        return Nd6RollOutcome(
            dice = dice,
            total = dice.sum(),
            sixCount = sixes,
            triggersCriticalInjury = sixes >= 2
        )
    }
}
