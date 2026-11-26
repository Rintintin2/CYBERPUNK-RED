package com.example.core.ai

import com.example.core.rules.CriticalMarker
import com.example.core.rules.RedStat
import com.example.core.rules.SkillCheckResult

/**
 * Local deterministic session director used while external AI services are not connected.
 * Never fabricates dice results or invents homebrew mechanics.
 */
class LocalSoloOracleGateway : GameMasterGateway {
    override val providerMode: AiProviderMode = AiProviderMode.OFFLINE_DETERMINISTIC

    override suspend fun processPlayerAction(
        playerInput: String,
        context: GmContextSnapshot
    ): GmProposal {
        val trimmed = playerInput.trim()
        val missionTag = context.activeMissionTitle ?: "Sem contrato ativo"
        val suggested = inferCheckSuggestion(trimmed)

        val narrative = buildString {
            append("Registro de cena [Dia ${context.inGameDay}, ${context.inGameHour.toString().padStart(2, '0')}:00] ")
            append("• Operativo: ${context.edgerunnerHandle} (${context.edgerunnerRole.displayNamePt}) ")
            append("• Contrato: $missionTag.\n")
            append("Ação declarada: \"$trimmed\". ")
            if (suggested != null) {
                append("Diretriz determinística: execute um teste de ${suggested.stat.code} + ${suggested.skillName} contra DV ${suggested.difficultyValue} na barra de rolagem.")
            } else {
                append("Ação registrada no diário de campanha. Conecte um provedor de IA na etapa futura para narração adaptativa em tempo real.")
            }
        }

        return GmProposal(
            narrativeText = narrative,
            speakerLabel = "Diretor Solo (Offline)",
            suggestedCheck = suggested,
            providerMode = providerMode
        )
    }

    override suspend fun narrateCheckOutcome(
        actionDescription: String,
        outcome: SkillCheckResult,
        context: GmContextSnapshot
    ): GmProposal {
        val critNote = when (outcome.roll.marker) {
            CriticalMarker.CRITICAL_SUCCESS -> " [Crítico 10 + ${outcome.roll.secondaryDie}]"
            CriticalMarker.CRITICAL_FAILURE -> " [Falha Crítica 1 - ${outcome.roll.secondaryDie}]"
            CriticalMarker.NONE -> ""
        }
        val verdict = if (outcome.succeeded) "SUCESSO" else "FALHA"
        val text = "$verdict determinístico em $actionDescription: " +
            "${outcome.request.stat.code}(${outcome.request.statValue}) + " +
            "${outcome.request.skillName}(${outcome.request.skillLevel}) + " +
            "1d10(${outcome.roll.netDieTotal})$critNote = ${outcome.total} vs DV ${outcome.request.difficultyValue}."

        return GmProposal(
            narrativeText = text,
            speakerLabel = "Motor de Regras",
            suggestedCheck = null,
            providerMode = providerMode
        )
    }

    private fun inferCheckSuggestion(input: String): SuggestedSkillCheck? {
        val lower = input.lowercase()
        return when {
            lower.contains("hack") || lower.contains("net") || lower.contains("sistema") || lower.contains("ice") ->
                SuggestedSkillCheck(RedStat.INT, "Interface / Eletrônica", 13, "Ação de invasão ou sistema eletrônico.")
            lower.contains("atir") || lower.contains("tiro") || lower.contains("pistola") || lower.contains("rifle") ->
                SuggestedSkillCheck(RedStat.REF, "Armas Curtas / Longas", 13, "Ação armada requerendo teste determinístico.")
            lower.contains("furtiv") || lower.contains("escond") || lower.contains("infiltr") ->
                SuggestedSkillCheck(RedStat.DEX, "Furtividade", 13, "Movimentação furtiva contra percepção.")
            lower.contains("negoc") || lower.contains("persuad") || lower.contains("suborn") || lower.contains("convers") ->
                SuggestedSkillCheck(RedStat.COOL, "Persuasão / Negociação", 13, "Interação social com NPC.")
            lower.contains("percep") || lower.contains("procur") || lower.contains("investig") ->
                SuggestedSkillCheck(RedStat.INT, "Percepção", 13, "Varredura de ambiente ou pistas.")
            else -> null
        }
    }
}
