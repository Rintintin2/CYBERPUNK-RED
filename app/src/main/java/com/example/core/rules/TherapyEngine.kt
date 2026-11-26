package com.example.core.rules

enum class TherapyType(
    val labelPt: String,
    val clinicCostEb: Int,
    val selfMaterialsEb: Int,
    val medicalTechDv: Int,
    val baseDiceCount: Int,
    val descriptionPt: String
) {
    STANDARD_HUMANITY(
        labelPt = "Terapia de Humanidade Padrão",
        clinicCostEb = 500,
        selfMaterialsEb = 100,
        medicalTechDv = 15,
        baseDiceCount = 2,
        descriptionPt = "1 semana • Recupera 2d6 Humanidade (Clínica 500eb ou Materiais 100eb + Tech Médica DV15)."
    ),
    EXTREME_HUMANITY(
        labelPt = "Terapia de Humanidade Extrema",
        clinicCostEb = 1000,
        selfMaterialsEb = 500,
        medicalTechDv = 17,
        baseDiceCount = 4,
        descriptionPt = "1 semana • Recupera 4d6 Humanidade (Clínica 1.000eb ou Materiais 500eb + Tech Médica DV17)."
    ),
    ADDICTION(
        labelPt = "Terapia de Dependência",
        clinicCostEb = 1000,
        selfMaterialsEb = 500,
        medicalTechDv = 15,
        baseDiceCount = 0,
        descriptionPt = "1 semana • Remove 1 vício químico (Clínica 1.000eb ou Materiais 500eb + Tech Médica DV15)."
    )
}

data class TherapyOutcome(
    val therapyType: TherapyType,
    val paidCostEb: Int,
    val useClinicService: Boolean,
    val medTechCheckRoll: D10RollOutcome?,
    val medTechCheckTotal: Int?,
    val checkSucceeded: Boolean,
    val diceCountRolled: Int,
    val brainwasherDeltaApplied: Int,
    val diceRollOutcome: Nd6RollOutcome?,
    val humanityRecovered: Int,
    val humanityLossBefore: Int,
    val humanityLossAfter: Int,
    val newCurrentHumanity: Int,
    val maxEffectiveHumanity: Int,
    val summaryPt: String
)

/**
 * Official Cyberpunk RED Therapy Rules (Core pp. 230–231) + All About Agents Brainwasher modifier.
 *
 * Permanent Humanity loss floor: each cyberware with HL > 0 reduces max Humanity by 2 (4 for Borgware).
 * Therefore, Therapy cannot lower `humanityLoss` below `maxHumanityReduction`.
 */
class TherapyEngine(
    private val diceEngine: DiceEngine = DiceEngine()
) {
    fun performTherapy(
        therapyType: TherapyType,
        empathyMax: Int,
        currentHumanityLoss: Int,
        maxHumanityReduction: Int,
        brainwasherDiceDelta: Int = 0,
        useClinicService: Boolean = true,
        medTechBase: Int = 0
    ): TherapyOutcome {
        val rawMaxHumanity = CharacterCalculator.calculateMaxHumanity(empathyMax)
        val permanentFloor = maxHumanityReduction.coerceAtLeast(0)
        val maxEffectiveHumanity = (rawMaxHumanity - permanentFloor).coerceAtLeast(0)
        val cost = if (useClinicService) therapyType.clinicCostEb else therapyType.selfMaterialsEb

        val (medRoll, medTotal, succeeded) = if (useClinicService) {
            Triple(null, null, true)
        } else {
            val r = diceEngine.rollD10Exploding()
            val total = medTechBase + r.netDieTotal
            Triple(r, total, total > therapyType.medicalTechDv)
        }

        if (!succeeded) {
            val currentHum = (rawMaxHumanity - currentHumanityLoss.coerceAtLeast(0))
                .coerceIn(0, maxEffectiveHumanity)
            return TherapyOutcome(
                therapyType = therapyType,
                paidCostEb = cost,
                useClinicService = false,
                medTechCheckRoll = medRoll,
                medTechCheckTotal = medTotal,
                checkSucceeded = false,
                diceCountRolled = 0,
                brainwasherDeltaApplied = 0,
                diceRollOutcome = null,
                humanityRecovered = 0,
                humanityLossBefore = currentHumanityLoss,
                humanityLossAfter = currentHumanityLoss,
                newCurrentHumanity = currentHum,
                maxEffectiveHumanity = maxEffectiveHumanity,
                summaryPt = "${therapyType.labelPt} falhou no teste de Tecnologia Médica ($medTotal vs DV ${therapyType.medicalTechDv}). Materiais gastos: ${cost}eb."
            )
        }

        if (therapyType == TherapyType.ADDICTION) {
            val currentHum = (rawMaxHumanity - currentHumanityLoss.coerceAtLeast(0))
                .coerceIn(0, maxEffectiveHumanity)
            return TherapyOutcome(
                therapyType = therapyType,
                paidCostEb = cost,
                useClinicService = useClinicService,
                medTechCheckRoll = medRoll,
                medTechCheckTotal = medTotal,
                checkSucceeded = true,
                diceCountRolled = 0,
                brainwasherDeltaApplied = 0,
                diceRollOutcome = null,
                humanityRecovered = 0,
                humanityLossBefore = currentHumanityLoss,
                humanityLossAfter = currentHumanityLoss,
                newCurrentHumanity = currentHum,
                maxEffectiveHumanity = maxEffectiveHumanity,
                summaryPt = "Terapia de Dependência concluída (7 dias, -${cost}eb): 1 vício removido. Atenção: por 1 ano, recaída falha automaticamente."
            )
        }

        val effectiveDiceCount = (therapyType.baseDiceCount + brainwasherDiceDelta).coerceAtLeast(1)
        val diceOutcome = diceEngine.rollNd6(effectiveDiceCount)
        val lossAfter = (currentHumanityLoss - diceOutcome.total).coerceAtLeast(permanentFloor)
        val recovered = (currentHumanityLoss - lossAfter).coerceAtLeast(0)
        val newCurrentHumanity = (rawMaxHumanity - lossAfter).coerceIn(0, maxEffectiveHumanity)

        val brainwasherNote = when {
            brainwasherDiceDelta > 0 -> " (+${brainwasherDiceDelta}d6 Brainwasher)"
            brainwasherDiceDelta < 0 -> " (${brainwasherDiceDelta}d6 Brainwasher)"
            else -> ""
        }

        return TherapyOutcome(
            therapyType = therapyType,
            paidCostEb = cost,
            useClinicService = useClinicService,
            medTechCheckRoll = medRoll,
            medTechCheckTotal = medTotal,
            checkSucceeded = true,
            diceCountRolled = effectiveDiceCount,
            brainwasherDeltaApplied = brainwasherDiceDelta,
            diceRollOutcome = diceOutcome,
            humanityRecovered = recovered,
            humanityLossBefore = currentHumanityLoss,
            humanityLossAfter = lossAfter,
            newCurrentHumanity = newCurrentHumanity,
            maxEffectiveHumanity = maxEffectiveHumanity,
            summaryPt = "${therapyType.labelPt} (${effectiveDiceCount}d6${brainwasherNote}=${diceOutcome.total}): recuperou +$recovered Humanidade (Atual: $newCurrentHumanity/$maxEffectiveHumanity)."
        )
    }
}
