package com.example.core.ai

import com.example.core.rules.RedRole
import com.example.core.rules.RedStat
import com.example.core.rules.WoundState

enum class AiProviderMode(val labelPt: String) {
    OFFLINE_DETERMINISTIC("Modo Offline • Motor Determinístico"),
    REMOTE_AI_READY("IA Externa (Contrato Pronto)")
}

data class GmContextSnapshot(
    val campaignTitle: String,
    val inGameDay: Int,
    val inGameHour: Int,
    val edgerunnerHandle: String,
    val edgerunnerRole: RedRole,
    val currentHp: Int,
    val maxHp: Int,
    val woundState: WoundState,
    val currentHumanity: Int,
    val eurodollars: Int,
    val activeMissionTitle: String?,
    val recentJournalLines: List<String>,
    val referencedRuleSnippets: List<String>
)

data class SuggestedSkillCheck(
    val stat: RedStat,
    val skillName: String,
    val difficultyValue: Int,
    val rationale: String
)

data class GmProposal(
    val narrativeText: String,
    val speakerLabel: String,
    val suggestedCheck: SuggestedSkillCheck? = null,
    val providerMode: AiProviderMode
)
