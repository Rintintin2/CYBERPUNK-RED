package com.example.core.ai

import com.example.core.rules.SkillCheckResult

/**
 * Contract for any future AI Game Master service (e.g., Gemini / Firebase AI Logic).
 *
 * Architectural invariants:
 * 1. The AI NEVER rolls dice or computes HP/SP/Humanity/DV outcomes.
 * 2. The AI receives an immutable [GmContextSnapshot] and either proposes a check
 *    or narrates an already-computed [SkillCheckResult].
 */
interface GameMasterGateway {
    val providerMode: AiProviderMode

    suspend fun processPlayerAction(
        playerInput: String,
        context: GmContextSnapshot
    ): GmProposal

    suspend fun narrateCheckOutcome(
        actionDescription: String,
        outcome: SkillCheckResult,
        context: GmContextSnapshot
    ): GmProposal
}
