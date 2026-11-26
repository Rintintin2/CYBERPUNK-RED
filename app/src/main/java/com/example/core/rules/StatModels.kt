package com.example.core.rules

/**
 * Canonical 10 Core Stats from Cyberpunk RED.
 */
enum class RedStat(
    val code: String,
    val labelPt: String
) {
    INT("INT", "Inteligência"),
    REF("REF", "Reflexos"),
    DEX("DES", "Destreza"),
    TECH("TEC", "Técnica"),
    COOL("CAR", "Carisma"),
    WILL("VON", "Vontade"),
    LUCK("SOR", "Sorte"),
    MOVE("MOV", "Movimento"),
    BODY("COR", "Corpo"),
    EMP("EMP", "Empatia")
}

/**
 * Canonical 10 Roles from Cyberpunk RED.
 */
enum class RedRole(
    val displayNamePt: String,
    val roleAbilityPt: String
) {
    SOLO("Solo", "Percepção de Combate"),
    NETRUNNER("Netrunner", "Interface"),
    TECH("Técnico", "Gambiarra / Criação"),
    MEDTECH("Medtech", "Medicina"),
    MEDIA("Mídia", "Credibilidade"),
    EXEC("Executivo", "Trabalho em Equipe"),
    LAWMAN("Homem da Lei", "Reforço"),
    FIXER("Fixer", "Operador"),
    NOMAD("Nômade", "Moto"),
    ROCKERBOY("Rockerboy", "Impacto Carismático")
}

enum class WoundState(val labelPt: String, val actionPenalty: Int) {
    UNINJURED("Ileso", 0),
    LIGHTLY_WOUNDED("Ferimento Leve", 0),
    SERIOUSLY_WOUNDED("Ferimento Grave", -2),
    MORTALLY_WOUNDED("Mortalmente Ferido", -4),
    DEAD("Morto", -99)
}

data class CharacterStats(
    val intelligence: Int,
    val reflexes: Int,
    val dexterity: Int,
    val technique: Int,
    val cool: Int,
    val willpower: Int,
    val luckMax: Int,
    val move: Int,
    val body: Int,
    val empathyMax: Int
) {
    fun getStat(stat: RedStat, currentEmpathy: Int = empathyMax, currentLuck: Int = luckMax): Int {
        return when (stat) {
            RedStat.INT -> intelligence
            RedStat.REF -> reflexes
            RedStat.DEX -> dexterity
            RedStat.TECH -> technique
            RedStat.COOL -> cool
            RedStat.WILL -> willpower
            RedStat.LUCK -> currentLuck
            RedStat.MOVE -> move
            RedStat.BODY -> body
            RedStat.EMP -> currentEmpathy
        }
    }

    fun statSum(): Int =
        intelligence + reflexes + dexterity + technique + cool +
            willpower + luckMax + move + body + empathyMax
}

data class DerivedCharacterMetrics(
    val maxHp: Int,
    val seriouslyWoundedThreshold: Int,
    val deathSaveBase: Int,
    val maxHumanity: Int,
    val currentHumanity: Int,
    val currentEmpathy: Int,
    val woundState: WoundState,
    val woundPenalty: Int,
    val armorPenalty: Int = 0,
    val effectiveWoundPenalty: Int = woundPenalty,
    val painEditorActive: Boolean = false,
    val effectiveBody: Int = deathSaveBase
) {
    fun armorStatPenalty(stat: RedStat): Int =
        if (stat == RedStat.REF || stat == RedStat.DEX || stat == RedStat.MOVE) armorPenalty else 0

    fun totalCheckPenalty(stat: RedStat): Int = effectiveWoundPenalty + armorStatPenalty(stat)
}
