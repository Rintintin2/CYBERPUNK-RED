package com.example.core.rules

enum class OfficialDifficulty(val labelPt: String, val dv: Int) {
    SIMPLE("Simples", 9),
    EVERYDAY("Cotidiano", 13),
    DIFFICULT("Difícil", 15),
    PROFESSIONAL("Profissional", 17),
    HEROIC("Heroico", 21),
    INCREDIBLE("Incrível", 24),
    LEGENDARY("Lendário", 29)
}

enum class RangeWeaponCategory(val labelPt: String) {
    PISTOL("Pistola"),
    SMG("Submetralhadora (SMG)"),
    SHOTGUN_SLUG("Espingarda (Balote)"),
    ASSAULT_RIFLE("Rifle de Assalto"),
    SNIPER_RIFLE("Rifle de Precisão"),
    BOW_CROSSBOW("Arco / Besta"),
    GRENADE_LAUNCHER("Lança-Granadas"),
    ROCKET_LAUNCHER("Lança-Foguetes")
}

enum class RangeBracket(val labelMeters: String, val index: Int) {
    M0_6("0 a 6 m", 0),
    M7_12("7 a 12 m", 1),
    M13_25("13 a 25 m", 2),
    M26_50("26 a 50 m", 3),
    M51_100("51 a 100 m", 4),
    M101_200("101 a 200 m", 5),
    M201_400("201 a 400 m", 6),
    M401_800("401 a 800 m", 7)
}

data class CoverMaterialSpec(
    val materialPt: String,
    val thickHp: Int,
    val thinHp: Int
)

/**
 * Official Cyberpunk RED Range DV Tables, Autofire Tables, and Cover Hit Points (pp. 173, 182).
 */
object OfficialCombatTables {
    private val singleShotRangeTable: Map<RangeWeaponCategory, List<Int?>> = mapOf(
        RangeWeaponCategory.PISTOL to listOf(13, 15, 20, 25, 30, 30, null, null),
        RangeWeaponCategory.SMG to listOf(15, 13, 15, 20, 25, 25, 30, null),
        RangeWeaponCategory.SHOTGUN_SLUG to listOf(13, 15, 20, 25, 30, 35, null, null),
        RangeWeaponCategory.ASSAULT_RIFLE to listOf(17, 16, 15, 13, 15, 20, 25, 30),
        RangeWeaponCategory.SNIPER_RIFLE to listOf(30, 25, 25, 20, 15, 16, 17, 20),
        RangeWeaponCategory.BOW_CROSSBOW to listOf(15, 13, 15, 17, 20, 22, null, null),
        RangeWeaponCategory.GRENADE_LAUNCHER to listOf(16, 15, 15, 17, 20, 22, 25, null),
        RangeWeaponCategory.ROCKET_LAUNCHER to listOf(17, 16, 15, 15, 20, 20, 25, 30)
    )

    private val autofireRangeTable: Map<RangeWeaponCategory, Pair<Int, List<Int?>>> = mapOf(
        RangeWeaponCategory.SMG to (3 to listOf(20, 17, 20, 25, 30, null, null, null)),
        RangeWeaponCategory.ASSAULT_RIFLE to (4 to listOf(22, 20, 17, 20, 25, null, null, null))
    )

    fun getSingleShotDv(category: RangeWeaponCategory, bracket: RangeBracket): Int? {
        return singleShotRangeTable.getValue(category)[bracket.index]
    }

    fun getAutofireDvAndMultiplier(
        category: RangeWeaponCategory,
        bracket: RangeBracket
    ): Pair<Int, Int>? {
        val entry = autofireRangeTable[category] ?: return null
        val dv = entry.second[bracket.index] ?: return null
        return dv to entry.first
    }

    val coverMaterials: List<CoverMaterialSpec> = listOf(
        CoverMaterialSpec("Aço", 50, 25),
        CoverMaterialSpec("Pedra", 40, 20),
        CoverMaterialSpec("Vidro Blindado", 30, 15),
        CoverMaterialSpec("Concreto", 25, 10),
        CoverMaterialSpec("Madeira", 20, 5),
        CoverMaterialSpec("Gesso / Espuma / Plástico", 15, 0)
    )

    fun brawlingDiceForBody(body: Int, hasCyberarm: Boolean): Int {
        return when {
            body >= 11 -> 4
            body >= 7 -> 3
            body >= 5 || hasCyberarm -> 2
            else -> 1
        }
    }
}
