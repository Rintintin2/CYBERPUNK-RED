package com.example.core.rules

import com.example.data.local.entity.CyberwareEntity

data class CyberwareSlotSummary(
    val fashionwareUsed: Int = 0,
    val fashionwareMax: Int = 7,
    val internalUsed: Int = 0,
    val internalMax: Int = 7,
    val externalUsed: Int = 0,
    val externalMax: Int = 7,
    val neuralLinkCount: Int = 0,
    val neuralUsed: Int = 0,
    val neuralMax: Int = 0,
    val cybereyeCount: Int = 0,
    val cyberopticUsed: Int = 0,
    val cyberopticMax: Int = 0,
    val cyberaudioSuiteCount: Int = 0,
    val cyberaudioUsed: Int = 0,
    val cyberaudioMax: Int = 0,
    val cyberarmCount: Int = 0,
    val cyberarmUsed: Int = 0,
    val cyberarmMax: Int = 0,
    val cyberlegCount: Int = 0,
    val cyberlegUsed: Int = 0,
    val cyberlegMax: Int = 0,
    val speedwareCount: Int = 0
)

sealed class CyberwareValidationResult {
    data class Valid(
        val slotSummary: CyberwareSlotSummary,
        val warningsPt: List<String> = emptyList()
    ) : CyberwareValidationResult()

    data class Blocked(
        val reasonPt: String
    ) : CyberwareValidationResult()
}

/**
 * Deterministic Cyberpunk RED cyberware rule validator:
 * - Foundational prerequisites (Neural Link, Cybereye, Cyberaudio Suite, Cyberarm, Cyberleg, Chipware Socket)
 * - Category & Option Slot budgets (Core pp. 111–116 & Black Chrome / Going Metal)
 * - Max 1 Speedware (Kerenzikov vs Sandevistan)
 * - Linear Frame / Grafted Muscle & Bone Lace BODY scaling & prerequisites
 * - Reliable Borgware classification (catalog category + fallback slotArea)
 */
object CyberwareValidator {

    fun findSpec(name: String, slotArea: String = ""): OfficialCyberwareSpec? {
        val cleanName = name.trim()
        if (cleanName.isEmpty()) return null
        val exact = OfficialCyberwareCatalog.allCyberware.firstOrNull { spec ->
            spec.namePt.equals(cleanName, ignoreCase = true) ||
                spec.nameEn.equals(cleanName, ignoreCase = true)
        }
        if (exact != null) return exact

        val normalized = cleanName.lowercase()
        val partial = OfficialCyberwareCatalog.allCyberware.firstOrNull { spec ->
            normalized.contains(spec.nameEn.lowercase()) ||
                normalized.contains(spec.namePt.substringBefore(" (").lowercase())
        }
        if (partial != null) return partial

        return if (slotArea.isNotBlank()) {
            OfficialCyberwareCatalog.allCyberware.firstOrNull { spec ->
                spec.category.labelPt.equals(slotArea.trim(), ignoreCase = true) &&
                    spec.namePt.contains(cleanName, ignoreCase = true)
            }
        } else null
    }

    fun isBorgware(cw: CyberwareEntity): Boolean {
        val spec = findSpec(cw.name, cw.slotArea)
        return spec?.category == CyberwareCategory.BORGWARE ||
            cw.slotArea.contains("Borgware", ignoreCase = true)
    }

    fun isBorgwareSpec(name: String, slotArea: String): Boolean {
        val spec = findSpec(name, slotArea)
        return spec?.category == CyberwareCategory.BORGWARE ||
            slotArea.contains("Borgware", ignoreCase = true)
    }

    fun hasPainEditor(cyberware: List<CyberwareEntity>): Boolean =
        cyberware.any { cw ->
            cw.installed && (
                cw.name.contains("Pain Editor", ignoreCase = true) ||
                    cw.name.contains("Editor de Dor", ignoreCase = true)
                )
        }

    fun countGraftedMuscle(cyberware: List<CyberwareEntity>): Int =
        cyberware.count { cw ->
            cw.installed && (
                cw.name.contains("Grafted Muscle", ignoreCase = true) ||
                    cw.name.contains("Músculos e Ossos Enxertados", ignoreCase = true)
                )
        }

    fun computeBodyWithGraftedMuscle(baseBody: Int, cyberware: List<CyberwareEntity>): Int {
        var body = baseBody.coerceAtLeast(1)
        val graftedCount = countGraftedMuscle(cyberware)
        repeat(graftedCount) {
            if (body < 10) {
                body = (body + 2).coerceAtMost(10)
            }
        }
        return body
    }

    fun computeEffectiveBody(baseBody: Int, cyberware: List<CyberwareEntity>): Int {
        var body = computeBodyWithGraftedMuscle(baseBody, cyberware)
        val activeNames = cyberware.filter { it.installed }.map { it.name.lowercase() }
        val hasOmega = activeNames.any { it.contains("omega") || it.contains("ω") }
        val hasBeta = activeNames.any { it.contains("beta") || it.contains("β") }
        val hasSigmaOrInternal12 = activeNames.any {
            it.contains("sigma") || it.contains("σ") ||
                it.contains("fūma") || it.contains("fuma") ||
                it.contains("swat") || it.contains("vermilion")
        }
        body = when {
            hasOmega -> maxOf(body, 16)
            hasBeta -> maxOf(body, 14)
            hasSigmaOrInternal12 -> maxOf(body, 12)
            else -> body
        }
        return body
    }

    private fun isSpeedwareName(name: String): Boolean {
        val lower = name.lowercase()
        return lower.contains("kerenzikov") || lower.contains("sandevistan")
    }

    private fun isChipware(spec: OfficialCyberwareSpec?, name: String): Boolean {
        val lowerName = name.lowercase()
        if (lowerName.contains("chipware socket") || lowerName.contains("soquete")) return false
        if (lowerName.contains("memory chip")) return false
        if (spec != null) {
            return spec.notesPt.contains("Chipware", ignoreCase = true) ||
                spec.namePt.contains("(Chip)", ignoreCase = true)
        }
        return lowerName.contains("(chip)") || lowerName.contains("pain editor") ||
            lowerName.contains("editor de dor") || lowerName.contains("translation chip")
    }

    private fun isLegOption(spec: OfficialCyberwareSpec?, name: String): Boolean {
        val lower = (spec?.nameEn ?: name).lowercase()
        return lower.contains("foot") || lower.contains("ciberpé") ||
            lower.contains("jump booster") || lower.contains("zero gravity thrusters")
    }

    private fun resolveCategory(spec: OfficialCyberwareSpec?, slotArea: String): CyberwareCategory? {
        if (spec != null) return spec.category
        return CyberwareCategory.entries.firstOrNull {
            it.labelPt.equals(slotArea.trim(), ignoreCase = true) ||
                it.name.equals(slotArea.trim(), ignoreCase = true)
        }
    }

    fun computeSlotSummary(cyberware: List<CyberwareEntity>): CyberwareSlotSummary {
        val active = cyberware.filter { it.installed }
        var fashionUsed = 0
        var internalUsed = 0
        var externalUsed = 0
        var neuralLinks = 0
        var neuralUsed = 0
        var eyes = 0
        var opticMax = 0
        var opticUsed = 0
        var audioSuites = 0
        var audioMax = 0
        var audioUsed = 0
        var arms = 0
        var armMax = 0
        var armUsed = 0
        var legs = 0
        var legMax = 0
        var legUsed = 0
        var speedwareCount = 0

        active.forEach { cw ->
            val spec = findSpec(cw.name, cw.slotArea)
            val cat = resolveCategory(spec, cw.slotArea)
            val slots = spec?.optionSlotsUsed ?: 1
            val lower = (spec?.nameEn ?: cw.name).lowercase()

            if (isSpeedwareName(cw.name)) speedwareCount++

            when (cat) {
                CyberwareCategory.FASHIONWARE -> fashionUsed += slots
                CyberwareCategory.INTERNAL -> internalUsed += slots
                CyberwareCategory.EXTERNAL -> externalUsed += slots
                CyberwareCategory.NEURALWARE -> {
                    if (lower.contains("neural link")) {
                        neuralLinks++
                    } else {
                        neuralUsed += slots
                    }
                }
                CyberwareCategory.CYBEROPTICS -> {
                    if (lower.contains("cybereye")) {
                        eyes++
                        opticMax += if (lower.contains("sponsored")) 2 else 3
                    } else {
                        opticUsed += slots
                    }
                }
                CyberwareCategory.CYBERAUDIO -> {
                    if (lower.contains("cyberaudio suite")) {
                        audioSuites++
                        audioMax += if (lower.contains("discount")) 1 else 3
                    } else {
                        audioUsed += slots
                    }
                }
                CyberwareCategory.CYBERLIMBS -> {
                    when {
                        lower.contains("cyberarm") && spec?.isFoundational == true -> {
                            arms++
                            armMax += if (lower.contains("neo-soviet")) 3 else 4
                        }
                        lower.contains("cyberleg") && spec?.isFoundational == true -> {
                            legs++
                            legMax += if (lower.contains("romanova")) 6 else 3
                        }
                        isLegOption(spec, cw.name) -> legUsed += slots
                        else -> armUsed += slots
                    }
                }
                CyberwareCategory.BORGWARE -> {
                    when {
                        lower.contains("bug eye") -> {
                            eyes++
                            opticMax += 5
                        }
                        lower.contains("monovision") -> {
                            eyes++
                            opticMax += 3
                        }
                        lower.contains("multioptic") -> {
                            opticMax += 15
                        }
                        lower.contains("sensor array") -> {
                            audioMax += 5
                        }
                        lower.contains("modular finger enthusiast") -> {
                            armUsed += slots
                        }
                    }
                }
                null -> Unit
            }
        }

        return CyberwareSlotSummary(
            fashionwareUsed = fashionUsed,
            internalUsed = internalUsed,
            externalUsed = externalUsed,
            neuralLinkCount = neuralLinks,
            neuralUsed = neuralUsed,
            neuralMax = neuralLinks * 5,
            cybereyeCount = eyes,
            cyberopticUsed = opticUsed,
            cyberopticMax = opticMax,
            cyberaudioSuiteCount = audioSuites,
            cyberaudioUsed = audioUsed,
            cyberaudioMax = audioMax,
            cyberarmCount = arms,
            cyberarmUsed = armUsed,
            cyberarmMax = armMax,
            cyberlegCount = legs,
            cyberlegUsed = legUsed,
            cyberlegMax = legMax,
            speedwareCount = speedwareCount
        )
    }

    fun validateInstallation(
        baseBody: Int,
        existingCyberware: List<CyberwareEntity>,
        name: String,
        slotArea: String
    ): CyberwareValidationResult {
        val active = existingCyberware.filter { it.installed }
        val summary = computeSlotSummary(active)
        val spec = findSpec(name, slotArea)
        val cat = resolveCategory(spec, slotArea)
        val slotsNeeded = spec?.optionSlotsUsed ?: 1
        val lower = (spec?.nameEn ?: name).lowercase()
        val isFoundational = spec?.isFoundational == true
        val warnings = mutableListOf<String>()

        // 1. Speedware uniqueness (Core p. 112: max 1 Speedware)
        if (isSpeedwareName(name) && summary.speedwareCount >= 1) {
            return CyberwareValidationResult.Blocked(
                "Limite oficial atingido: apenas 1 Speedware (Kerenzikov ou Sandevistan) pode ser instalado."
            )
        }

        // 2. Category prerequisites & slot caps
        when (cat) {
            CyberwareCategory.FASHIONWARE -> {
                if (summary.fashionwareUsed + slotsNeeded > summary.fashionwareMax) {
                    return CyberwareValidationResult.Blocked(
                        "Fashionware sem espaço (${summary.fashionwareUsed}/${summary.fashionwareMax} slots)."
                    )
                }
            }

            CyberwareCategory.INTERNAL -> {
                if (summary.internalUsed + slotsNeeded > summary.internalMax) {
                    return CyberwareValidationResult.Blocked(
                        "Cyberware Interno sem espaço (${summary.internalUsed}/${summary.internalMax} slots)."
                    )
                }
            }

            CyberwareCategory.EXTERNAL -> {
                if (summary.externalUsed + slotsNeeded > summary.externalMax) {
                    return CyberwareValidationResult.Blocked(
                        "Cyberware Externo sem espaço (${summary.externalUsed}/${summary.externalMax} slots)."
                    )
                }
            }

            CyberwareCategory.NEURALWARE -> {
                if (!isFoundational) {
                    if (summary.neuralLinkCount <= 0) {
                        return CyberwareValidationResult.Blocked(
                            "\"$name\" requer um Neural Link instalado antes."
                        )
                    }
                    if (isChipware(spec, name)) {
                        val hasChipSocket = active.any {
                            it.name.contains("Chipware Socket", true) ||
                                it.name.contains("Soquete", true)
                        }
                        if (!hasChipSocket) {
                            return CyberwareValidationResult.Blocked(
                                "\"$name\" é Chipware e requer um Soquete de Chipware (Chipware Socket)."
                            )
                        }
                    }
                    if (summary.neuralUsed + slotsNeeded > summary.neuralMax) {
                        return CyberwareValidationResult.Blocked(
                            "Slots de Neuralware esgotados (${summary.neuralUsed}/${summary.neuralMax}). Instale outro Neural Link se aplicável."
                        )
                    }
                }
            }

            CyberwareCategory.CYBEROPTICS -> {
                if (!isFoundational) {
                    if (summary.cyberopticMax <= 0) {
                        return CyberwareValidationResult.Blocked(
                            "\"$name\" requer um Cybereye (Ciberolho) ou visor óptico base."
                        )
                    }
                    if (summary.cyberopticUsed + slotsNeeded > summary.cyberopticMax) {
                        return CyberwareValidationResult.Blocked(
                            "Slots de Ciberópticos esgotados (${summary.cyberopticUsed}/${summary.cyberopticMax})."
                        )
                    }
                }
            }

            CyberwareCategory.CYBERAUDIO -> {
                if (isFoundational) {
                    if (summary.cyberaudioSuiteCount >= 1) {
                        return CyberwareValidationResult.Blocked(
                            "Limite oficial: apenas 1 Cyberaudio Suite pode ser instalado por corpo."
                        )
                    }
                } else {
                    if (summary.cyberaudioSuiteCount <= 0) {
                        return CyberwareValidationResult.Blocked(
                            "\"$name\" requer uma Cyberaudio Suite instalada antes."
                        )
                    }
                    if (summary.cyberaudioUsed + slotsNeeded > summary.cyberaudioMax) {
                        return CyberwareValidationResult.Blocked(
                            "Slots de Ciberáudio esgotados (${summary.cyberaudioUsed}/${summary.cyberaudioMax})."
                        )
                    }
                }
            }

            CyberwareCategory.CYBERLIMBS -> {
                val meatArmAllowed = lower.contains("standard hand") ||
                    lower.contains("modular finger cyberhand") ||
                    lower.contains("subdermal grip") ||
                    lower.contains("limb covering")
                val meatFootAllowed = lower.contains("standard foot") || lower.contains("limb covering")

                if (lower.contains("subdermal grip") && summary.neuralLinkCount <= 0) {
                    return CyberwareValidationResult.Blocked(
                        "Subdermal Grip requer Neural Link instalado."
                    )
                }

                if (!isFoundational) {
                    if (isLegOption(spec, name)) {
                        if (!meatFootAllowed && summary.cyberlegCount <= 0) {
                            return CyberwareValidationResult.Blocked(
                                "\"$name\" requer uma Cyberleg (Ciberperna) instalada."
                            )
                        }
                        if (summary.cyberlegCount > 0 && summary.cyberlegUsed + slotsNeeded > summary.cyberlegMax) {
                            return CyberwareValidationResult.Blocked(
                                "Slots de Ciberperna esgotados (${summary.cyberlegUsed}/${summary.cyberlegMax})."
                            )
                        }
                    } else {
                        if (!meatArmAllowed && summary.cyberarmCount <= 0) {
                            return CyberwareValidationResult.Blocked(
                                "\"$name\" requer um Cyberarm (Ciberbraço) instalado."
                            )
                        }
                        if (!meatArmAllowed && summary.cyberarmUsed + slotsNeeded > summary.cyberarmMax) {
                            return CyberwareValidationResult.Blocked(
                                "Slots de Ciberbraço esgotados (${summary.cyberarmUsed}/${summary.cyberarmMax})."
                            )
                        }
                    }
                }
            }

            CyberwareCategory.BORGWARE -> {
                val graftedCount = countGraftedMuscle(active)
                val bodyWithMuscle = computeBodyWithGraftedMuscle(baseBody, active)
                when {
                    lower.contains("sensor array") && summary.cyberaudioSuiteCount <= 0 -> {
                        return CyberwareValidationResult.Blocked(
                            "Sensor Array requer Cyberaudio Suite instalada."
                        )
                    }
                    lower.contains("sigma") || lower.contains("σ") -> {
                        if (bodyWithMuscle < 6 || graftedCount < 1) {
                            return CyberwareValidationResult.Blocked(
                                "Linear Frame Σ (Sigma) exige COR ≥ 6 e ao menos 1x Músculos e Ossos Enxertados."
                            )
                        }
                    }
                    lower.contains("beta") || lower.contains("β") ||
                        lower.contains("fūma") || lower.contains("fuma") ||
                        lower.contains("swat") || lower.contains("vermilion") -> {
                        if (bodyWithMuscle < 8 || graftedCount < 2) {
                            return CyberwareValidationResult.Blocked(
                                "\"$name\" exige COR ≥ 8 e 2x Músculos e Ossos Enxertados instalados."
                            )
                        }
                    }
                    lower.contains("modular finger enthusiast") -> {
                        if (summary.cyberarmCount <= 0) {
                            return CyberwareValidationResult.Blocked(
                                "Cibermão Modular 8 Dedos requer um Cyberarm (2 slots)."
                            )
                        }
                        if (summary.cyberarmUsed + slotsNeeded > summary.cyberarmMax) {
                            return CyberwareValidationResult.Blocked(
                                "Slots de Ciberbraço insuficientes (${summary.cyberarmUsed}/${summary.cyberarmMax})."
                            )
                        }
                    }
                }
            }

            null -> {
                warnings.add("Categoria customizada: validação de slots ignorada.")
            }
        }

        return CyberwareValidationResult.Valid(summary, warnings)
    }
}
