package com.example.core.rules

import kotlin.math.ceil

enum class NpcThreatTier(val labelPt: String) {
    MOOK("Capanga (Mook)"),
    LIEUTENANT("Tenente (Lieutenant)"),
    MINI_BOSS("Subchefe (Mini Boss)"),
    BOSS("Chefe (Boss)")
}

data class OfficialNpcWeaponSpec(
    val namePt: String,
    val damageDice: Int,
    val quality: WeaponQuality = WeaponQuality.STANDARD,
    val specialNotesPt: String = ""
)

data class OfficialNpcSpec(
    val id: String,
    val namePt: String,
    val nameEn: String,
    val tier: NpcThreatTier,
    val roleSummaryPt: String,
    val intelligence: Int,
    val reflexes: Int,
    val dexterity: Int,
    val technique: Int,
    val cool: Int,
    val willpower: Int,
    val move: Int,
    val body: Int,
    val empathy: Int,
    val hp: Int,
    val seriouslyWounded: Int,
    val deathSave: Int,
    val headArmorSp: Int,
    val bodyArmorSp: Int,
    val armorLabelPt: String,
    val armorPenaltyActive: Boolean = false,
    val roleAbilityLabelPt: String? = null,
    val weapons: List<OfficialNpcWeaponSpec>,
    val programs: List<String> = emptyList(),
    val gearAndCyberwarePt: List<String>,
    val keySkillBasesPt: String
)

/**
 * Canonical Part 2 NPCs & Mooks (Core Rulebook pp. 412–416):
 * 4 Mooks, 3 Lieutenants, 2 Mini Bosses, 1 Cyberpsycho Boss + Party Size Scaling.
 */
object OfficialNpcCatalog {

    /**
     * Core Rulebook p. 412:
     * - Mooks: 2 per Player (Solo Play uses partySize = 1 -> 2, or minimum 1-2 depending on scene)
     * - Lieutenants: 1 per Player
     * - Mini Bosses: 1 per 2 Players (ceil)
     * - Bosses: 1 per 4 Players (ceil, min 1)
     */
    fun recommendedCount(tier: NpcThreatTier, partySize: Int): Int {
        val players = partySize.coerceAtLeast(1)
        return when (tier) {
            NpcThreatTier.MOOK -> players * 2
            NpcThreatTier.LIEUTENANT -> players
            NpcThreatTier.MINI_BOSS -> ceil(players / 2.0).toInt().coerceAtLeast(1)
            NpcThreatTier.BOSS -> ceil(players / 4.0).toInt().coerceAtLeast(1)
        }
    }

    val allNpcs: List<OfficialNpcSpec> = listOf(
        OfficialNpcSpec(
            id = "bodyguard",
            namePt = "Guarda-Costas",
            nameEn = "Bodyguard",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Segurança pessoal contratado para proteger VIPs em trânsito.",
            intelligence = 3, reflexes = 6, dexterity = 5, technique = 2, cool = 4,
            willpower = 4, move = 4, body = 6, empathy = 3,
            hp = 35, seriouslyWounded = 18, deathSave = 6,
            headArmorSp = 7, bodyArmorSp = 7, armorLabelPt = "Kevlar (PA 7)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Espingarda Baixa Qualidade", 5, WeaponQuality.POOR),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4, WeaponQuality.STANDARD)
            ),
            gearAndCyberwarePt = listOf("Munição Balote ×25", "Munição Pistola Muito Pesada ×25", "Comunicador de Rádio"),
            keySkillBasesPt = "Briga 11 • Dirigir Terrestre 10 • Armas Curtas 10 • Armas Longas 10 • Percepção 9 • Atletismo 9 • Evasão 7 • Furtividade 7"
        ),
        OfficialNpcSpec(
            id = "boosterganger",
            namePt = "Boosterganger",
            nameEn = "Boosterganger",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Membro de gangue callejera agressivo, viciado em cromo e violência.",
            intelligence = 2, reflexes = 6, dexterity = 5, technique = 2, cool = 4,
            willpower = 2, move = 4, body = 4, empathy = 3,
            hp = 20, seriouslyWounded = 10, deathSave = 4,
            headArmorSp = 4, bodyArmorSp = 4, armorLabelPt = "Couro (PA 4)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Pistola Muito Pesada Baixa Qualidade", 4, WeaponQuality.POOR),
                OfficialNpcWeaponSpec("Rippers (Cibergarras)", 2, WeaponQuality.STANDARD, "Ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Munição Pistola Muito Pesada ×30", "Celular Descartável", "Rippers", "Techhair"),
            keySkillBasesPt = "Armas Curtas 12 • Armas Brancas 11 • Dirigir Terrestre 10 • Atletismo 9 • Briga 9 • Evasão 7 • Furtividade 7"
        ),
        OfficialNpcSpec(
            id = "road_ganger",
            namePt = "Ganger de Estrada",
            nameEn = "Road Ganger",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Invasor motorizado da Estrada Aberta e do Perímetro.",
            intelligence = 4, reflexes = 6, dexterity = 4, technique = 4, cool = 3,
            willpower = 3, move = 3, body = 3, empathy = 3,
            hp = 25, seriouslyWounded = 13, deathSave = 3,
            headArmorSp = 4, bodyArmorSp = 4, armorLabelPt = "Couro (PA 4)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Besta (Crossbow)", 4),
                OfficialNpcWeaponSpec("Arma Branca Leve", 1, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Munição Pistola MP ×20", "Flechas ×20", "Corda, Lanterna", "Neural Link (Interface Plugs)"),
            keySkillBasesPt = "Dirigir Terrestre 12 • Arquearia 10 • Armas Curtas 10 • Atletismo 10 • Mecânica Terrestre 10 • Armas Brancas 8 • Furtividade 8"
        ),
        OfficialNpcSpec(
            id = "security_operative",
            namePt = "Operativo de Segurança",
            nameEn = "Security Operative",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Guarda armado padrão de prédios Corporativos e Zonas Moderadas.",
            intelligence = 3, reflexes = 7, dexterity = 4, technique = 2, cool = 2,
            willpower = 3, move = 3, body = 5, empathy = 3,
            hp = 30, seriouslyWounded = 15, deathSave = 5,
            headArmorSp = 7, bodyArmorSp = 7, armorLabelPt = "Kevlar (PA 7)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto Baixa Qualidade", 5, WeaponQuality.POOR),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Arma Branca Média", 2, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Munição Rifle ×40", "Munição Pistola MP ×20", "Comunicador de Rádio"),
            keySkillBasesPt = "Tiro Automático 10 • Armas Curtas 10 • Armas Longas 10 • Atletismo 8 • Concentração 7 • Evasão 6"
        ),
        OfficialNpcSpec(
            id = "netrunner_npc",
            namePt = "Netrunner (Tenente)",
            nameEn = "Netrunner",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Hacker tático armado com cyberdeck completo de ataque e defesa.",
            intelligence = 7, reflexes = 5, dexterity = 4, technique = 7, cool = 4,
            willpower = 5, move = 5, body = 3, empathy = 4,
            hp = 30, seriouslyWounded = 15, deathSave = 3,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Bodyweight Suit (PA 11)",
            roleAbilityLabelPt = "Interface 4",
            weapons = listOf(
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)
            ),
            programs = listOf("Banhammer", "DeckKRASH", "Eraser", "Hellbolt", "Shield", "Sword", "Worm"),
            gearAndCyberwarePt = listOf("Munição Pistola MP ×50", "Lanterna", "Óculos de Virtualidade", "Neural Link (Interface Plugs)"),
            keySkillBasesPt = "Interface 4 • Tecnologia Básica 13 • Falsificação 13 • Espec. Local 13 • Criptografia 11 • Dedução 11 • Eletrônica/Seg. 11 • Armas Curtas 10"
        ),
        OfficialNpcSpec(
            id = "reclaimer_chief",
            namePt = "Chefe Reclaimer",
            nameEn = "Reclaimer Chief",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Líder veterano do Perímetro Reivindicado, especialista em cerco e sobrevivência.",
            intelligence = 3, reflexes = 6, dexterity = 6, technique = 5, cool = 4,
            willpower = 5, move = 4, body = 6, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Espingarda (Shotgun)", 5),
                OfficialNpcWeaponSpec("Pistola Pesada", 3),
                OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Balote ×25", "Munição P. Pesada ×25", "Agent, Grapple Gun, Rádio, Camping", "Filtros Nasais, Neural Link (Chipware Socket, Tactile Boost)"),
            keySkillBasesPt = "Atletismo 12 • Resistência 11 • Dirigir Terrestre 10 • Armas Curtas 10 • Armas Brancas 10 • Armas Longas 10 • Furtividade 10"
        ),
        OfficialNpcSpec(
            id = "security_officer",
            namePt = "Oficial de Segurança",
            nameEn = "Security Officer",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Comandante tático blindado com escudo balístico e Kerenzikov.",
            intelligence = 4, reflexes = 6, dexterity = 4, technique = 4, cool = 6,
            willpower = 5, move = 4, body = 7, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 7,
            headArmorSp = 13, bodyArmorSp = 13,
            armorLabelPt = "Medium Armorjack (PA 13, -2 aplicado) + Escudo Balístico (10 PV)",
            armorPenaltyActive = true,
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto", 5),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Arma Branca Média", 2, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Rifle ×50, Pistola MP ×30, Escudo (10 PV)", "Binóculos, Algemas ×2, Rádio", "Neural Link (Kerenzikov Speedware +2 Inic.)"),
            keySkillBasesPt = "Tiro Automático 10 (12 s/PA) • Dirigir 10 (12) • Atletismo 8 (10) • Evasão 8 (10) • Armas Curtas/Longas/Brancas 8 (10) • Res. Tortura/Drogas 10"
        ),
        OfficialNpcSpec(
            id = "outrider",
            namePt = "Outrider (Batedor Nômade)",
            nameEn = "Outrider",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Caçador de comboio de alta precisão equipado com ciberópticos táticos.",
            intelligence = 6, reflexes = 8, dexterity = 8, technique = 3, cool = 5,
            willpower = 6, move = 6, body = 6, empathy = 6,
            hp = 40, seriouslyWounded = 20, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11)",
            roleAbilityLabelPt = "Moto (Família Nômade) 4",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto", 5),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Arma Branca Leve", 1, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Rifle ×60, Pistola MP ×40, Rastreadores, Algemas ×2", "Cyberaudio (Audição Amplificada)", "Cybereye (Targeting Scope, TeleOptics)", "Neural Link (Interface Plugs)"),
            keySkillBasesPt = "Moto 4 • REF 8 Esquiva Balas • Percepção 14 • Armas Longas 14 • Armas Curtas 14 • Rastreamento 14 • Evasão 14 • Tiro Automático 12"
        ),
        OfficialNpcSpec(
            id = "pyro",
            namePt = "Pyro (Incendiário)",
            nameEn = "Pyro",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Solo obcecado por fogo com Lança-Chamas e granadas incendiárias.",
            intelligence = 5, reflexes = 8, dexterity = 6, technique = 7, cool = 4,
            willpower = 4, move = 6, body = 5, empathy = 3,
            hp = 35, seriouslyWounded = 18, deathSave = 5,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11)",
            roleAbilityLabelPt = "Combat Awareness 4",
            weapons = listOf(
                OfficialNpcWeaponSpec("Lança-Chamas (Flamethrower)", 3, specialNotesPt = "Incendeia alvos (4 de dano direto/turno na regra oficial)"),
                OfficialNpcWeaponSpec("Pistola Pesada", 3),
                OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Cartuchos Incendiários ×8, Munição P. MP ×50", "Granada Incendiária ×1, Flashbang ×1", "Level Dampeners, Anti-Dazzle ×2, Filtros Nasais"),
            keySkillBasesPt = "Combat Awareness 4 • REF 8 Esquiva Balas • Armeiro 15 • Armas Pesadas 14 • Armas Curtas 14 • Demolições 13 • Evasão 12"
        ),
        OfficialNpcSpec(
            id = "cyberpsycho",
            namePt = "Ciberpsicopata (Boss)",
            nameEn = "Cyberpsycho",
            tier = NpcThreatTier.BOSS,
            roleSummaryPt = "Ameaça máxima urbana com Armadura Subdérmica, Pain Editor e armamento embutido.",
            intelligence = 5, reflexes = 8, dexterity = 8, technique = 5, cool = 4,
            willpower = 7, move = 8, body = 10, empathy = 0,
            hp = 55, seriouslyWounded = 28, deathSave = 10,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Armadura Subdérmica (PA 11)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Lança-Granadas Popup", 6, specialNotesPt = "2× Granadas AP (5×5)"),
                OfficialNpcWeaponSpec("SMG Pesada Popup", 3, specialNotesPt = "Automático ×3"),
                OfficialNpcWeaponSpec("Cybersnake", 4, specialNotesPt = "Ignora ½ PA"),
                OfficialNpcWeaponSpec("Wolvers (Garras)", 3, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf(
                "2× Ciberbraços (Popup Grenade Launcher ×2, Popup Heavy SMG, Wolvers)",
                "2× Ciberpernas (Jump Boosters ×2)",
                "Cybersnake, Grafted Muscle & Bone Lace, Armadura Subdérmica",
                "Neural Link (Chipware Socket, Pain Editor — ignora penalidade de Ferimento Grave)"
            ),
            keySkillBasesPt = "REF 8 Esquiva Balas • COR 10 • Armas Pesadas 16 • Tiro Automático 16 • Briga 16 • Armas Brancas 16 • Evasão 16 • Atletismo 16"
        )
    )

    val allIncludingDlcNpcs: List<OfficialNpcSpec>
        get() = allNpcs + OfficialDlcNpcsAndNets.hardenedNpcs + OfficialTalesAndHookBookCatalog.namedDlcNpcs

    fun hardenedRecommendedCount(tier: NpcThreatTier, partySize: Int): Int {
        val players = partySize.coerceAtLeast(1)
        return when (tier) {
            NpcThreatTier.MOOK -> players
            NpcThreatTier.LIEUTENANT -> ceil(players / 2.0).toInt().coerceAtLeast(1)
            NpcThreatTier.MINI_BOSS -> ceil(players / 3.0).toInt().coerceAtLeast(1)
            NpcThreatTier.BOSS -> ceil(players / 4.0).toInt().coerceAtLeast(1)
        }
    }

    fun findByEnglishName(nameEn: String): OfficialNpcSpec? =
        allIncludingDlcNpcs.firstOrNull { it.nameEn.equals(nameEn, ignoreCase = true) || it.id.equals(nameEn, ignoreCase = true) }

    val allExtendedNpcs: List<OfficialNpcSpec>
        get() = allIncludingDlcNpcs +
            OfficialStreetratPregensCatalog.allPregens.map { it.toNpcSpec() } +
            OfficialGangsAndCitizensCatalog.gangGuideEntries.map { it.statCard } +
            OfficialGangsAndCitizensCatalog.everydayCitizens.map { it.statCard } +
            OfficialDangerGalNpcs.allDgdNpcs.map { it.npc }

    fun findExtendedByNameOrId(query: String): OfficialNpcSpec? =
        allExtendedNpcs.firstOrNull {
            it.id.equals(query, ignoreCase = true) ||
                it.nameEn.equals(query, ignoreCase = true) ||
                it.namePt.equals(query, ignoreCase = true)
        }
}
