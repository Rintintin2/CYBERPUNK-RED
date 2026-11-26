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

data class StealthNetContestResult(
    val actionLabelPt: String,
    val runnerRoll: D10RollOutcome,
    val runnerTotal: Int,
    val oppositionRoll: D10RollOutcome,
    val oppositionTotal: Int,
    val runnerStealthMaintained: Boolean,
    val breakdownPt: String
)

data class AgentHackResult(
    val accessMode: AgentAccessMode,
    val qualityTier: AgentQualityTier,
    val elecSecBase: Int,
    val bonusApplied: Int,
    val roll: D10RollOutcome,
    val total: Int,
    val difficultyValue: Int,
    val succeeded: Boolean,
    val breakdownPt: String
)

data class HusToolResult(
    val roll1d10: Int,
    val earnedEb: Int,
    val triggeredEncounter: Boolean,
    val summaryPt: String
)

data class BrainwasherResult(
    val roll1d10: Int,
    val isEvenPenalty: Boolean,
    val therapyDiceDelta: Int,
    val summaryPt: String
)

/**
 * Deterministic Netrunning action resolution:
 * - Standard Core (Interface + 1d10 vs DV, Program/ICE REZ damage)
 * - Going Quiet Stealth Netrunning (Quietly Jack In, Black ICE PER evasion, Watcher search)
 * - All About Agents (Agent Hacking, HusTool, Brainwasher)
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

    fun quietlyJackIn(
        interfaceRank: Int,
        watcherInterfaceRank: Int
    ): StealthNetContestResult {
        val runnerRoll = diceEngine.rollD10Exploding()
        val watcherRoll = diceEngine.rollD10Exploding()
        val runnerTotal = interfaceRank + runnerRoll.netDieTotal
        val oppTotal = watcherInterfaceRank + watcherRoll.netDieTotal
        val success = runnerTotal > oppTotal
        return StealthNetContestResult(
            actionLabelPt = "Quietly Jack In (Entrar em Furtividade)",
            runnerRoll = runnerRoll,
            runnerTotal = runnerTotal,
            oppositionRoll = watcherRoll,
            oppositionTotal = oppTotal,
            runnerStealthMaintained = success,
            breakdownPt = "Interface($interfaceRank)+1d10(${runnerRoll.netDieTotal})=$runnerTotal vs Watcher($watcherInterfaceRank)+1d10(${watcherRoll.netDieTotal})=$oppTotal"
        )
    }

    fun evadeBlackIceInStealth(
        interfaceRank: Int,
        cloakBonus: Int,
        blackIcePerception: Int
    ): StealthNetContestResult {
        val runnerRoll = diceEngine.rollD10Exploding()
        val iceRoll = diceEngine.rollD10Exploding()
        val runnerTotal = interfaceRank + cloakBonus + runnerRoll.netDieTotal
        val iceTotal = blackIcePerception + iceRoll.netDieTotal
        val success = runnerTotal > iceTotal
        return StealthNetContestResult(
            actionLabelPt = "Evadir Black ICE em Furtividade",
            runnerRoll = runnerRoll,
            runnerTotal = runnerTotal,
            oppositionRoll = iceRoll,
            oppositionTotal = iceTotal,
            runnerStealthMaintained = success,
            breakdownPt = "Int($interfaceRank)+Cloak($cloakBonus)+1d10(${runnerRoll.netDieTotal})=$runnerTotal vs ICE PER($blackIcePerception)+1d10(${iceRoll.netDieTotal})=$iceTotal"
        )
    }

    /**
     * Going Quiet p. 4:
     * - When Watcher actively uses a NET Action to search (isWatcherActiveSearch = true),
     *   Watcher must beat Runner (watcherTotal > runnerTotal => detected; tie keeps stealth).
     * - When Runner encounters a Watcher passively (isWatcherActiveSearch = false),
     *   Runner must beat Watcher (runnerTotal > watcherTotal => stealth maintained).
     */
    fun contestWatcherVsStealth(
        interfaceRank: Int,
        cloakBonus: Int,
        watcherInterface: Int,
        watcherPathfinder: Int,
        isWatcherActiveSearch: Boolean = true
    ): StealthNetContestResult {
        val runnerRoll = diceEngine.rollD10Exploding()
        val watcherRoll = diceEngine.rollD10Exploding()
        val runnerTotal = interfaceRank + cloakBonus + runnerRoll.netDieTotal
        val watcherTotal = watcherInterface + watcherPathfinder + watcherRoll.netDieTotal
        val maintained = if (isWatcherActiveSearch) {
            !(watcherTotal > runnerTotal)
        } else {
            runnerTotal > watcherTotal
        }
        val modeLabel = if (isWatcherActiveSearch) "Busca de Watcher" else "Encontro c/ Watcher"
        return StealthNetContestResult(
            actionLabelPt = "$modeLabel vs Runner Furtivo",
            runnerRoll = runnerRoll,
            runnerTotal = runnerTotal,
            oppositionRoll = watcherRoll,
            oppositionTotal = watcherTotal,
            runnerStealthMaintained = maintained,
            breakdownPt = "Runner($interfaceRank+$cloakBonus)+1d10(${runnerRoll.netDieTotal})=$runnerTotal vs Watcher($watcherInterface+PF $watcherPathfinder)+1d10(${watcherRoll.netDieTotal})=$watcherTotal"
        )
    }

    fun hackAgent(
        elecSecBase: Int,
        accessMode: AgentAccessMode,
        qualityTier: AgentQualityTier,
        hasSocialInfoBonus: Boolean = false,
        crunchWhistleInterfaceBonus: Int = 0
    ): AgentHackResult {
        val dv = qualityTier.dvFor(accessMode)
        val bonus = (if (hasSocialInfoBonus) 2 else 0) + crunchWhistleInterfaceBonus.coerceAtLeast(0)
        val roll = diceEngine.rollD10Exploding()
        val total = elecSecBase + bonus + roll.netDieTotal
        val succeeded = total > dv
        return AgentHackResult(
            accessMode = accessMode,
            qualityTier = qualityTier,
            elecSecBase = elecSecBase,
            bonusApplied = bonus,
            roll = roll,
            total = total,
            difficultyValue = dv,
            succeeded = succeeded,
            breakdownPt = "Elet/Seg($elecSecBase)${if (bonus > 0) "+Bônus($bonus)" else ""}+1d10(${roll.netDieTotal})=$total vs DV $dv"
        )
    }

    fun rollHusTool(): HusToolResult {
        val r = diceEngine.rollPlainD10()
        return when (r) {
            1 -> HusToolResult(r, 0, false, "1d10=1: Trabalho falhou! Sem pagamento após 30 min.")
            10 -> HusToolResult(r, 10, true, "1d10=10: Ganhou +10 eb em 30 min, mas gerou um Encontro Aleatório na rua!")
            else -> HusToolResult(r, 10, false, "1d10=$r: Bico concluído em 30 min (+10 eb).")
        }
    }

    fun rollBrainwasher(): BrainwasherResult {
        val r = diceEngine.rollPlainD10()
        val isEven = r % 2 == 0
        return if (isEven) {
            BrainwasherResult(
                roll1d10 = r,
                isEvenPenalty = true,
                therapyDiceDelta = -1,
                summaryPt = "1d10=$r (Par): Condicionamento mental falhou! Próxima Terapia recupera -1d6 de Humanidade."
            )
        } else {
            BrainwasherResult(
                roll1d10 = r,
                isEvenPenalty = false,
                therapyDiceDelta = 1,
                summaryPt = "1d10=$r (Ímpar): Condicionamento eficaz! Próxima Terapia recupera +1d6 extra de Humanidade."
            )
        }
    }
}
