package com.example.core.rules

data class GangGuideEntry(
    val id: String,
    val gangNamePt: String,
    val gangNameEn: String,
    val districtsPt: String,
    val affiliationThemePt: String,
    val statCard: OfficialNpcSpec
)

data class EverydayCitizenEntry(
    val id: String,
    val namePt: String,
    val nameEn: String,
    val variantsPt: List<String>,
    val statCard: OfficialNpcSpec
)

private fun quickStatNpc(
    id: String,
    namePt: String,
    nameEn: String,
    tier: NpcThreatTier,
    summaryPt: String,
    hp: Int,
    combatNumber: Int,
    initBonus: Int,
    move: Int,
    headSp: Int,
    bodySp: Int,
    armorLabelPt: String,
    weapons: List<OfficialNpcWeaponSpec>,
    roleAbilityPt: String? = null,
    gearPt: List<String> = emptyList()
): OfficialNpcSpec {
    val ref = initBonus.coerceIn(2, 10)
    val dex = (combatNumber / 2).coerceIn(2, 8)
    val body = ((hp - 10) / 5).coerceIn(2, 10)
    return OfficialNpcSpec(
        id = id,
        namePt = namePt,
        nameEn = nameEn,
        tier = tier,
        roleSummaryPt = summaryPt,
        intelligence = 4,
        reflexes = ref,
        dexterity = dex,
        technique = 4,
        cool = 4,
        willpower = body,
        move = move,
        body = body,
        empathy = 4,
        hp = hp,
        seriouslyWounded = (hp + 1) / 2,
        deathSave = body,
        headArmorSp = headSp,
        bodyArmorSp = bodySp,
        armorLabelPt = armorLabelPt,
        roleAbilityLabelPt = roleAbilityPt,
        weapons = weapons,
        gearAndCyberwarePt = gearPt,
        keySkillBasesPt = "Nº Combate $combatNumber • Inic +$initBonus • MOV $move"
    )
}

/**
 * Complete data extracted from:
 * - Night City Gang Guide (Gangues_de_night_City.pdf, 24 pp.): All 44 Gangs + Stat Cards
 * - Everyday People (Pessoas_aleat_rias.pdf, 12 pp.): All 20 Citizens + 45 Variants
 */
object OfficialGangsAndCitizensCatalog {

    val gangGuideEntries: List<GangGuideEntry> = listOf(
        GangGuideEntry(
            "gang_6th_street", "Guarda da 6th Street", "6th Street Guard",
            "Heywood Norte, Pacifica, Rancho Coronado, Pequena Europa",
            "Veteranos patrióticos de guerra atuando como milícia de bairro.",
            quickStatNpc("gang_6th_street", "Guarda da 6th Street", "6th Street Guard", NpcThreatTier.MOOK,
                "Miliciano endurecido (Solo 2) armado com Rifle de Assalto.", 40, 12, 5, 4, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Rifle de Assalto", 5), OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "Ignora ½ PA")),
                "Combat Awareness 2", listOf("Munição Rifle", "Braço cibernético/óculos táticos"))
        ),
        GangGuideEntry(
            "gang_albino_alligators", "Capanga dos Albino Alligators", "Albino Alligators Mook",
            "Rancho Coronado", "Gangue agressiva dos arredores com Rippers.",
            quickStatNpc("gang_albino_alligators", "Capanga Albino Alligators", "Albino Alligators Mook", NpcThreatTier.MOOK,
                "Gangster ágil armado com Rippers e Pistola Pesada PQ.", 25, 10, 6, 4, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Rippers", 2, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_andersons", "Capanga dos Andersons", "Andersons Mook",
            "Pacifica Playground", "Clã excêntrico territorial de Pacifica.",
            quickStatNpc("gang_andersons", "Capanga dos Andersons", "Andersons Mook", NpcThreatTier.MOOK,
                "Atirador de bairro com Pistola Pesada PQ.", 35, 6, 4, 4, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Briga", 2)))
        ),
        GangGuideEntry(
            "gang_arzin_tynon", "Capanga Arzin Tynon", "Arzin Tynon Mook",
            "Watson Development, Nova Westbrook", "Mercenários endurecidos com Espingardas.",
            quickStatNpc("gang_arzin_tynon", "Capanga Arzin Tynon", "Arzin Tynon Mook", NpcThreatTier.MOOK,
                "Mercenário endurecido (Solo 2) de alto HP com VHP e Espingarda.", 50, 12, 5, 5, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Espingarda (Shotgun)", 5), OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)),
                "Combat Awareness 2")
        ),
        GangGuideEntry(
            "gang_bozos", "Capanga Bozo", "Bozo Mook",
            "Night City Sul", "Palhaços biossimulados sádicos e imprevisíveis.",
            quickStatNpc("gang_bozos", "Capanga Bozo", "Bozo Mook", NpcThreatTier.MOOK,
                "Palhaço psicopata veloz (MOV 6) com Pistola Pesada PQ.", 30, 10, 5, 6, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Briga / Armadilha", 1)))
        ),
        GangGuideEntry(
            "gang_culper_ring", "Operativo Culper Ring", "Culper Ring Operative",
            "Base Militar NorCal", "Espiões e infiltradores de elite ligados à base militar.",
            quickStatNpc("gang_culper_ring", "Operativo Culper Ring", "Culper Ring Operative", NpcThreatTier.LIEUTENANT,
                "Infiltrador tático (Solo 4) em Light Armorjack.", 40, 13, 6, 6, 11, 11, "Light Armorjack (PA 11)",
                listOf(OfficialNpcWeaponSpec("Rifle de Assalto", 5), OfficialNpcWeaponSpec("Pistola Pesada", 3)),
                "Combat Awareness 4")
        ),
        GangGuideEntry(
            "gang_deadwoods", "Capanga DeadWoods", "DeadWoods Mook",
            "The Glen, Sul, Porto, Heywood Docks/Industrial", "Soldados de rua endurecidos com armas Excelentes.",
            quickStatNpc("gang_deadwoods", "Capanga DeadWoods", "DeadWoods Mook", NpcThreatTier.MOOK,
                "Combatente endurecido (Solo 2) armado com Pistola Pesada EQ.", 45, 12, 5, 5, 4, 7, "Couro/Kevlar (4/7)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada Exc.", 3, WeaponQuality.EXCELLENT), OfficialNpcWeaponSpec("Briga", 3)),
                "Combat Awareness 2")
        ),
        GangGuideEntry(
            "gang_dirty_hippies", "Capanga Dirty Hippies", "Dirty Hippies Mook",
            "Rancho Coronado", "Ecoterroristas armados com rifles de baixa qualidade.",
            quickStatNpc("gang_dirty_hippies", "Capanga Dirty Hippies", "Dirty Hippies Mook", NpcThreatTier.MOOK,
                "Guerrilheiro urbano com Rifle de Assalto PQ.", 30, 10, 4, 6, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Rifle de Assalto PQ", 5, WeaponQuality.POOR), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_dragula_racers", "Piloto Dragula Racers", "Dragula Racers Driver",
            "Watson Development", "Corredores noturnos motorizados (Nomad 2).",
            quickStatNpc("gang_dragula_racers", "Piloto Dragula Racers", "Dragula Racers Driver", NpcThreatTier.MOOK,
                "Piloto de corrida ilegal endurecido (Moto 2) com Pistola Pesada.", 35, 10, 7, 6, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada", 3), OfficialNpcWeaponSpec("Briga", 2)),
                "Moto 2")
        ),
        GangGuideEntry(
            "gang_enhanced", "Guarda GunMart [The Enhanced]", "GunMart Factory Guard",
            "Night City Sul, Heywood Industrial, Pacifica", "Guardas transumanos endurecidos (Solo 2).",
            quickStatNpc("gang_enhanced", "Guarda The Enhanced", "GunMart Factory Guard", NpcThreatTier.MOOK,
                "Segurança modificado (Solo 2) com armamento customizado GunMart.", 35, 12, 6, 5, 7, 7, "Equipamento Custom (PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Custom GunMart", 4)),
                "Combat Awareness 2")
        ),
        GangGuideEntry(
            "gang_eurotrashers", "Capanga Eurotrashers", "Eurotrashers Mook",
            "Pequena Europa", "Esquadrão de elite snob e violento de Little Europe.",
            quickStatNpc("gang_eurotrashers", "Capanga Eurotrashers", "Eurotrashers Mook", NpcThreatTier.MOOK,
                "Valentão de estilo europeu com Arma Branca Média PQ.", 30, 8, 4, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Média PQ", 2, WeaponQuality.POOR), OfficialNpcWeaponSpec("Briga", 1)))
        ),
        GangGuideEntry(
            "gang_fixies", "Mensageiro [Fixie's Couriers]", "Bicycle Courier",
            "Zona Industrial de Heywood", "Ciclistas mensageiros ágeis e armados.",
            quickStatNpc("gang_fixies", "Ciclista Fixie's Couriers", "Bicycle Courier", NpcThreatTier.MOOK,
                "Entregador tático de bicicleta com Pistola Pesada PQ.", 25, 8, 4, 3, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)), gearPt = listOf("Bicicleta"))
        ),
        GangGuideEntry(
            "gang_g3", "Capanga G3", "G3 Mook",
            "Watson Development, Kabuki", "Artistas marciais e espadachins de beco.",
            quickStatNpc("gang_g3", "Capanga G3", "G3 Mook", NpcThreatTier.MOOK,
                "Combatente de artes marciais com Arma Branca Muito Pesada.", 30, 8, 6, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Muito Pesada", 4, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Artes Marciais", 2)))
        ),
        GangGuideEntry(
            "gang_generation_red", "Jovem Generation Red", "Generation Red Kid",
            "Antiga Zona de Combate", "Militantes juvenis e saqueadores de rua.",
            quickStatNpc("gang_generation_red", "Jovem Generation Red", "Generation Red Kid", NpcThreatTier.MOOK,
                "Jovem rebelde armado com Pistola Média PQ.", 25, 6, 4, 3, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR), OfficialNpcWeaponSpec("Briga", 1)))
        ),
        GangGuideEntry(
            "gang_gold_dragons", "Capanga Gold Dragons", "Gold Dragons Mook",
            "Pequena China", "Tríade protetora que policia Little China.",
            quickStatNpc("gang_gold_dragons", "Capanga Gold Dragons", "Gold Dragons Mook", NpcThreatTier.MOOK,
                "Soldado Tríade endurecido (Solo 2) com SMG e Arma Branca Pesada.", 35, 10, 4, 6, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("SMG", 2)),
                "Combat Awareness 2")
        ),
        GangGuideEntry(
            "gang_inquisitors", "Capanga Inquisitors", "Inquisitors Mook",
            "Heywood Norte", "Culto anti-cyberware que arranca implantes das vítimas.",
            quickStatNpc("gang_inquisitors", "Capanga Inquisitors", "Inquisitors Mook", NpcThreatTier.MOOK,
                "Fanático armado com Arma Branca Muito Pesada e Microwaver.", 30, 10, 6, 6, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Muito Pesada", 4, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Microwaver", 0, specialNotesPt = "Desliga cyberware")))
        ),
        GangGuideEntry(
            "gang_iron_sights", "Capanga Iron Sights", "Iron Sights Mook",
            "Velha Japantown, Antiga Zona de Combate", "Mercenários brutais de zona de guerra.",
            quickStatNpc("gang_iron_sights", "Capanga Iron Sights", "Iron Sights Mook", NpcThreatTier.MOOK,
                "Guerreiro endurecido de Kevlar com armas PQ pesadas.", 35, 10, 4, 5, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_julliard", "Músico Julliard Guild", "Julliard Guild Busker",
            "Centro Cultural / Ruas", "Guilda de músicos armados (Rockerboy 2).",
            quickStatNpc("gang_julliard", "Músico Julliard Guild", "Julliard Guild Busker", NpcThreatTier.MOOK,
                "Artista de rua endurecido (Charismatic Impact 2).", 30, 8, 6, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Arma Branca Leve PQ", 1, WeaponQuality.POOR)),
                "Charismatic Impact 2")
        ),
        GangGuideEntry(
            "gang_kill_krashers", "Capanga Kill Krashers", "Kill Krashers Mook",
            "The Glen, Sul, Santo Domingo", "Vândalos e demolidores violentos.",
            quickStatNpc("gang_kill_krashers", "Capanga Kill Krashers", "Kill Krashers Mook", NpcThreatTier.MOOK,
                "Agressor armado com Rippers e VHP de baixa qualidade.", 30, 8, 4, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Rippers", 2, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Pistola Muito Pesada PQ", 4, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_lightning_cats", "Capanga Lightning Cats", "Lightning Cats Mook",
            "Zona Quente", "Predadores de emboscada na Zona Quente (Solo 2).",
            quickStatNpc("gang_lightning_cats", "Capanga Lightning Cats", "Lightning Cats Mook", NpcThreatTier.MOOK,
                "Emboscador ágil endurecido com Artes Marciais e Rippers.", 35, 10, 4, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Artes Marciais", 2), OfficialNpcWeaponSpec("Rippers", 2, specialNotesPt = "Ignora ½ PA")),
                "Combat Awareness 2")
        ),
        GangGuideEntry(
            "gang_maelstrom", "Capanga Maelstrom", "Maelstrom Mook",
            "Zona Quente, Little Europe, Upper Marina, Old Japantown", "Fanáticos por cromo extremo com Wolvers.",
            quickStatNpc("gang_maelstrom", "Capanga Maelstrom", "Maelstrom Mook", NpcThreatTier.MOOK,
                "Psicopata endurecido de Kevlar equipado com Wolvers (3d6).", 35, 10, 3, 5, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Wolvers", 3, specialNotesPt = "ROF 2, ignora ½ PA"), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_mudang_gumi", "Ladrão de Dados Mudang Gumi", "Mudang Gumi Data Thief",
            "Pacifica Playground", "Netrunners místicos coreanos (Interface 4).",
            quickStatNpc("gang_mudang_gumi", "Ladrão de Dados Mudang Gumi", "Mudang Gumi Data Thief", NpcThreatTier.LIEUTENANT,
                "Tenente Netrunner (Interface 4) equipado com deck e Kevlar.", 35, 11, 6, 5, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada", 3), OfficialNpcWeaponSpec("Arma Branca Leve", 1)),
                "Interface 4")
        ),
        GangGuideEntry(
            "gang_muses", "Jammer das Muses", "Muses Jammer",
            "Heywood Norte", "Combatentes artísticos de elite (Solo 4).",
            quickStatNpc("gang_muses", "Jammer das Muses", "Muses Jammer", NpcThreatTier.LIEUTENANT,
                "Tenente endurecida (Combat Awareness 4) ágil e letal.", 35, 14, 6, 6, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Briga", 2)),
                "Combat Awareness 4")
        ),
        GangGuideEntry(
            "gang_philharmonic", "Capanga Philharmonic Vampyre", "Philharmonic Vampyre Mook",
            "Distrito Universitário", "Pranksters teatrais vampíricos (Rockerboy 2).",
            quickStatNpc("gang_philharmonic", "Philharmonic Vampyre", "Philharmonic Vampyre Mook", NpcThreatTier.MOOK,
                "Vampiro urbano com presas Vampyres e Artes Marciais.", 30, 8, 6, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Vampyres", 1, WeaponQuality.EXCELLENT), OfficialNpcWeaponSpec("Artes Marciais", 1)),
                "Charismatic Impact 2")
        ),
        GangGuideEntry(
            "gang_piranhas", "Capanga Piranhas", "Piranhas Mook",
            "Pacifica Playground", "Festeiros imprudentes equipados com Cybersnake.",
            quickStatNpc("gang_piranhas", "Capanga Piranhas", "Piranhas Mook", NpcThreatTier.MOOK,
                "Festeiro violento com Cybersnake oculta (4d6) e SMG PQ.", 30, 8, 4, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Cybersnake", 4, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("SMG PQ", 2, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_prime_time", "Capanga Prime-Time Players", "Prime-Time Players Mook",
            "Upper Marina, Nova Westbrook, Charter Hill", "Posergangue inspirada em estrelas da TV.",
            quickStatNpc("gang_prime_time", "Capanga Prime-Time Players", "Prime-Time Players Mook", NpcThreatTier.MOOK,
                "Poserganger dramático com estilo e armas customizadas.", 30, 8, 6, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Arma Temática TV", 3)))
        ),
        GangGuideEntry(
            "gang_princesses", "Princesa da Justiça", "Princesses of Justice Mook",
            "Distrito Universitário", "Vigilantes fantasiadas armadas até os dentes.",
            quickStatNpc("gang_princesses", "Princesa da Justiça", "Princesses of Justice Mook", NpcThreatTier.MOOK,
                "Justiceira robusta (40 PV) com Arma Branca Muito Pesada.", 40, 10, 5, 5, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Muito Pesada", 4, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Briga", 2)))
        ),
        GangGuideEntry(
            "gang_rat_kings", "Capanga Rat Kings", "Rat Kings Mook",
            "Santo Domingo", "Catadores dos bueiros e vielas escuras.",
            quickStatNpc("gang_rat_kings", "Capanga Rat Kings", "Rat Kings Mook", NpcThreatTier.MOOK,
                "Rato de túnel equipado com armas de baixa qualidade.", 30, 8, 4, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Arma Branca Média PQ", 2, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_reckoners", "Capanga Reckoners", "Reckoners Mook",
            "Zona Quente, The Glen, Night City Sul", "Niilistas armados com bastões de choque.",
            quickStatNpc("gang_reckoners", "Capanga Reckoners", "Reckoners Mook", NpcThreatTier.MOOK,
                "Profeta do fim dos tempos com Stun Baton e Pistola PQ.", 30, 8, 4, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Stun Baton", 2), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_red_chrome", "Legionario Red Chrome", "Red Chrome Legionnaire",
            "Pequena China, Velha Japantown", "Extremistas violentos em armadura mista.",
            quickStatNpc("gang_red_chrome", "Legionario Red Chrome", "Red Chrome Legionnaire", NpcThreatTier.MOOK,
                "Extremista agressivo com Arma Branca Média e Pistola PQ.", 35, 11, 5, 4, 4, 7, "Couro/Kevlar (4/7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Média", 2), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_scavvers", "Capanga Scavver", "Scavver Mook",
            "Zona Quente, Escombros", "Ladrões de implantes e sucateiros desesperados.",
            quickStatNpc("gang_scavvers", "Capanga Scavver", "Scavver Mook", NpcThreatTier.MOOK,
                "Saqueador de cromo com Arma Branca Pesada PQ.", 30, 8, 4, 4, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Briga", 1)))
        ),
        GangGuideEntry(
            "gang_shroomers", "Batedor/Mercador Shroomer", "Shroomer Scout/Trader",
            "Velha Japantown, Antiga Zona de Combate", "Cultivadores de fungos e negociantes armados (Fixer 2).",
            quickStatNpc("gang_shroomers", "Batedor Shroomer", "Shroomer Scout/Trader", NpcThreatTier.MOOK,
                "Mercador endurecido (Operator 2) armado com Espingarda.", 30, 11, 4, 4, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Espingarda (Shotgun)", 5), OfficialNpcWeaponSpec("Arma Branca Pesada", 3)),
                "Operator 2")
        ),
        GangGuideEntry(
            "gang_sinful_adams", "Capanga Sinful Adams", "Sinful Adams Mook",
            "Night City Sul", "Lutadores fanáticos por combate corpo a corpo.",
            quickStatNpc("gang_sinful_adams", "Capanga Sinful Adams", "Sinful Adams Mook", NpcThreatTier.MOOK,
                "Brigão de rua usando Artes Marciais e Arma Branca Pesada.", 30, 8, 6, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Pesada", 3), OfficialNpcWeaponSpec("Artes Marciais", 1)))
        ),
        GangGuideEntry(
            "gang_steel_vaqueros", "Piloto Steel Vaqueros", "Steel Vaqueros Driver",
            "Santo Domingo, Rancho Coronado", "Cavaleiros do asfalto em Light Armorjack (Nomad 4).",
            quickStatNpc("gang_steel_vaqueros", "Piloto Steel Vaqueros", "Steel Vaqueros Driver", NpcThreatTier.LIEUTENANT,
                "Tenente Nômade endurecido (Moto 4) com Rifle de Assalto EQ.", 35, 14, 6, 4, 11, 11, "Light Armorjack (PA 11)",
                listOf(OfficialNpcWeaponSpec("Rifle de Assalto Exc.", 5, WeaponQuality.EXCELLENT), OfficialNpcWeaponSpec("Arma Branca Pesada", 3)),
                "Moto 4")
        ),
        GangGuideEntry(
            "gang_street_queens", "Capanga Street Queens", "Street Queens Mook",
            "Marina Superior, Nova Westbrook", "Gangue urbana estilosa armada com Rippers.",
            quickStatNpc("gang_street_queens", "Capanga Street Queens", "Street Queens Mook", NpcThreatTier.MOOK,
                "Lutadora rápida com Rippers e Pistola Pesada PQ.", 30, 8, 6, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Rippers", 2, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_toecutters", "Capanga Toecutters", "Toecutters Mook",
            "Heywood Norte", "Motociclistas selvagens (Nomad 2) com Espingardas.",
            quickStatNpc("gang_toecutters", "Capanga Toecutters", "Toecutters Mook", NpcThreatTier.MOOK,
                "Motociclista endurecido (Moto 2) com Arma Branca Muito Pesada e Espingarda.", 35, 12, 4, 5, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Espingarda (Shotgun)", 5), OfficialNpcWeaponSpec("Arma Branca Muito Pesada", 4)),
                "Moto 2")
        ),
        GangGuideEntry(
            "gang_tombstone", "Guarda Tombstone Preservers", "Tombstone Preserver Guard",
            "Zona de Recuperação, Westbrook, Charter Hill", "Guardiões soturnos de cemitérios e bairros (Solo 2).",
            quickStatNpc("gang_tombstone", "Guarda Tombstone Preservers", "Tombstone Preserver Guard", NpcThreatTier.MOOK,
                "Sentinela endurecido (45 PV, Solo 2) com VHP e Artes Marciais.", 45, 12, 5, 5, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Pistola Muito Pesada", 4), OfficialNpcWeaponSpec("Artes Marciais", 3)),
                "Combat Awareness 2")
        ),
        GangGuideEntry(
            "gang_tyger_claws", "Capanga Tyger Claws", "Tyger Claws Mook",
            "Japantown, Watson, Kabuki, Sul, Charter Hill", "Membros ágeis de sindicato Yakuza (MOV 7).",
            quickStatNpc("gang_tyger_claws", "Capanga Tyger Claws", "Tyger Claws Mook", NpcThreatTier.MOOK,
                "Assaltante veloz (MOV 7) com Artes Marciais e Katana/Branca Pesada.", 35, 11, 5, 7, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Pesada", 3), OfficialNpcWeaponSpec("Artes Marciais", 2)))
        ),
        GangGuideEntry(
            "gang_undertow", "Capanga Undertow", "Undertow Mook",
            "Pequena Europa, Centro", "Contrabandistas anfíbios e de bueiros.",
            quickStatNpc("gang_undertow", "Capanga Undertow", "Undertow Mook", NpcThreatTier.MOOK,
                "Combatente de cais armado com Pistola Média PQ.", 30, 8, 4, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR), OfficialNpcWeaponSpec("Briga", 1)))
        ),
        GangGuideEntry(
            "gang_voodoo_boys", "Capanga Voodoo Boys", "Voodoo Boys Mook",
            "Pacifica, Rancho Coronado", "Místicos urbanos temidos com machetes e magia tecno.",
            quickStatNpc("gang_voodoo_boys", "Capanga Voodoo Boys", "Voodoo Boys Mook", NpcThreatTier.MOOK,
                "Guerreiro territorial com Facão Muito Pesado (4d6).", 30, 8, 4, 4, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Muito Pesada", 4, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        GangGuideEntry(
            "gang_wild_things", "Gladiador Wild Things", "Wild Things Gladiator",
            "Watson Development", "Gladiadores cibernéticos de arena (Solo 4, Inic +10!).",
            quickStatNpc("gang_wild_things", "Gladiador Wild Things", "Wild Things Gladiator", NpcThreatTier.LIEUTENANT,
                "Tenente endurecido de arena com Armadura Subdérmica PA 11 e Inic +10.", 40, 14, 10, 6, 11, 11, "Armadura Subdérmica (PA 11)",
                listOf(OfficialNpcWeaponSpec("Artes Marciais", 3), OfficialNpcWeaponSpec("Briga", 3)),
                "Combat Awareness 4")
        ),
        GangGuideEntry(
            "gang_willows", "Recruta Willows", "Willows Newbie",
            "Night City (Circuito Subterrâneo)", "Duelistas letais velozes (Solo 4, Inic +10).",
            quickStatNpc("gang_willows", "Recruta Willows", "Willows Newbie", NpcThreatTier.LIEUTENANT,
                "Tenente duelista endurecida (Inic +10, Combate 14) com equipamento customizado.", 40, 14, 10, 6, 11, 11, "Equipamento Custom (PA 11)",
                listOf(OfficialNpcWeaponSpec("Arma Branca / Duelo Custom", 4)),
                "Combat Awareness 4")
        ),
        GangGuideEntry(
            "gang_yellow_brick", "Homem de Lata [Yellow Brick Road]", "Tin Man [Yellow Brick Road]",
            "Night City", "Fanáticos cromados temáticos do Mágico de Oz.",
            quickStatNpc("gang_yellow_brick", "Homem de Lata [Yellow Brick]", "Tin Man [Yellow Brick Road Gang]", NpcThreatTier.MOOK,
                "Combate blindado em Kevlar com machado pesado (4d6).", 35, 10, 4, 4, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Machado (VH Melee)", 4, specialNotesPt = "Ignora ½ PA"), OfficialNpcWeaponSpec("Briga", 2)))
        ),
        GangGuideEntry(
            "gang_zoners", "Capanga Zoners", "Zoners Mook",
            "Night City Sul", "Vigilantes desconfiados das zonas residenciais abandonadas.",
            quickStatNpc("gang_zoners", "Capanga Zoners", "Zoners Mook", NpcThreatTier.MOOK,
                "Defensor de quarteirão com armas de baixa qualidade.", 30, 10, 3, 3, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR)))
        )
    )

    val everydayPeopleGuidelinesPt: List<String> = listOf(
        "Cidadãos comuns usam a estrutura simplificada de Capanga (Mook): Nº de Combate (Combat Number), Iniciativa, MOV, PA e Armas.",
        "Para testes sociais ou profissionais fora de combate, considere Base 10–12 na perícia principal do ofício e Base 6–8 em secundárias.",
        "Qualquer um dos 20 Cidadãos possui 2 a 3 Variantes rápidas para variar cenas de rua, bares, prédios e hospitais."
    )

    val everydayCitizens: List<EverydayCitizenEntry> = listOf(
        EverydayCitizenEntry(
            "cit_bartender", "Bartender", "Bartender",
            listOf("Bar Podre (Dive Bar): +Briga", "Sala VIP Corporativa: +Percepção Humana & Suborno"),
            quickStatNpc("cit_bartender", "Bartender", "Bartender", NpcThreatTier.MOOK, "Atendente de balcão experiente em apaziguar ou encerrar brigas.", 30, 6, 4, 5, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR), OfficialNpcWeaponSpec("Arma Branca Leve PQ", 1, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_bouncer", "Segurança de Balada (Bouncer)", "Bouncer",
            listOf("Festa da Alta Sociedade: Light Armorjack", "Brutamontes Contratado: +1 Arma Branca Média"),
            quickStatNpc("cit_bouncer", "Bouncer (Segurança)", "Bouncer", NpcThreatTier.MOOK, "Segurança de porta (Solo 2) treinado com Bastão de Choque.", 35, 10, 6, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Bastão de Choque", 2)), "Combat Awareness 2")
        ),
        EverydayCitizenEntry(
            "cit_busker", "Artista de Rua (Busker)", "Busker",
            listOf("Mímico (+Furtividade)", "Mágico de Rua (+Ocultar/Revelar)", "Poeta de Rua (+Composição)"),
            quickStatNpc("cit_busker", "Artista de Rua (Busker)", "Busker", NpcThreatTier.MOOK, "Músico/artista urbano (Rockerboy 2) atento às fofocas do bairro.", 30, 8, 6, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Arma Branca Leve PQ", 1, WeaponQuality.POOR)), "Charismatic Impact 2")
        ),
        EverydayCitizenEntry(
            "cit_construction", "Operário de Construção", "Construction Worker",
            listOf("Operador de Drone", "Nômade Reconstructor", "Especialista em Demolição"),
            quickStatNpc("cit_construction", "Operário de Construção", "Construction Worker", NpcThreatTier.MOOK, "Trabalhador robusto de capacete (Cabeça PA 7) e ferramenta pesada.", 35, 8, 3, 4, 7, 4, "Capacete Kevlar 7 / Couro 4",
                listOf(OfficialNpcWeaponSpec("Briga", 3), OfficialNpcWeaponSpec("Arma Branca Média PQ", 2, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_researcher", "Pesquisador Corporativo", "Corporate Researcher",
            listOf("Teórico da Conspiração", "Contador Forense (Criminologia/Contabilidade)"),
            quickStatNpc("cit_researcher", "Pesquisador Corporativo", "Corporate Researcher", NpcThreatTier.MOOK, "Cientista ou analista técnico (Netrunner 2) pouco apto ao combate.", 20, 6, 3, 3, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Briga", 1), OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR)), "Interface 2")
        ),
        EverydayCitizenEntry(
            "cit_deal_maker", "Negociador de Rua (Deal Maker)", "Deal Maker",
            listOf("Joytoy Freelancer", "Agiota/Bookie Local", "Informante (Snitch)"),
            quickStatNpc("cit_deal_maker", "Negociador (Deal Maker)", "Deal Maker", NpcThreatTier.MOOK, "Pequeno Fixer (Operator 2) sempre pronto para intermediar negócios.", 30, 8, 5, 4, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Briga", 1), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)), "Operator 2")
        ),
        EverydayCitizenEntry(
            "cit_glitterkid", "Glitterkid (Playboy/Patricinha)", "Glitterkid",
            listOf("Herdeiro Deserdado", "Recruta dos Inquisitors", "Universitário Rico"),
            quickStatNpc("cit_glitterkid", "Glitterkid", "Glitterkid", NpcThreatTier.MOOK, "Jovem de alta classe estiloso (MOV 6) buscando emoções nas ruas.", 25, 6, 4, 6, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Arma Branca Média", 2)))
        ),
        EverydayCitizenEntry(
            "cit_graffiti", "Grafiteiro (Graffiti Artist)", "Graffiti Artist",
            listOf("Grafiteiro Digital (AR/Hologramas)", "Explorador Urbano (Atletismo/Furtividade)"),
            quickStatNpc("cit_graffiti", "Grafiteiro", "Graffiti Artist", NpcThreatTier.MOOK, "Artista de beco ágil que conhece cada atalho e telhado do distrito.", 25, 8, 4, 4, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_gardener", "Jardineiro de Guerrilha", "Guerrilla Gardener",
            listOf("Criador de Galinhas Urbanas", "Trabalhador Rural dos Arredores"),
            quickStatNpc("cit_gardener", "Jardineiro de Guerrilha", "Guerrilla Gardener", NpcThreatTier.MOOK, "Cultivador hidropônico de telhados e terrenos baldios.", 25, 6, 3, 4, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_laborer", "Trabalhador Braçal (Laborer)", "Laborer",
            listOf("Estivador das Docas (Deckhand)", "Operador de Empilhadeira"),
            quickStatNpc("cit_laborer", "Trabalhador Braçal", "Laborer", NpcThreatTier.MOOK, "Operário forte acostumado a carregar contêineres.", 30, 8, 4, 5, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 3), OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_line_cook", "Cozinheiro de Chapa (Line Cook)", "Line Cook",
            listOf("Chef / Influencer do Data Pool", "Dono de Food Truck"),
            quickStatNpc("cit_line_cook", "Cozinheiro de Chapa", "Line Cook", NpcThreatTier.MOOK, "Cozinheiro rápido e habilidoso com facões de cozinha.", 25, 6, 6, 6, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 3), OfficialNpcWeaponSpec("Arma Branca Pesada (Cutelo)", 3)))
        ),
        EverydayCitizenEntry(
            "cit_tech", "Técnico de Bairro", "Neighborhood Tech",
            listOf("Mecânico de Rua", "Técnico Reclaimer"),
            quickStatNpc("cit_tech", "Técnico de Bairro", "Neighborhood Tech", NpcThreatTier.MOOK, "Faz-tudo eletrônico local (Maker 2).", 25, 6, 3, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR)), "Maker 2")
        ),
        EverydayCitizenEntry(
            "cit_paramedic", "Paramédico", "Paramedic",
            listOf("Assistente de Farmácia (Fármacos)", "Artista de Fashionware"),
            quickStatNpc("cit_paramedic", "Paramédico", "Paramedic", NpcThreatTier.MOOK, "Socorrista treinado (Medicina 2: Cirurgia 1 / Fármacos 1) com Kevlar.", 30, 6, 5, 4, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Arma Branca Leve PQ", 1, WeaponQuality.POOR), OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR)), "Medicina 2")
        ),
        EverydayCitizenEntry(
            "cit_scavver", "Catador (Scavver Civil)", "Scavver",
            listOf("Brutamontes", "Solitário Cauteloso", "Rato de Túnel (Tunnel Rat)"),
            quickStatNpc("cit_scavver", "Catador (Scavver)", "Scavver", NpcThreatTier.MOOK, "Explorador de ruínas da Zona Quente armado com pé de cabra.", 30, 8, 4, 4, 4, 4, "Couro (PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_screamsheet", "Redator de Screamsheet", "Screamsheet Writer",
            listOf("Livecaster em Campo", "Repórter de Rádio Nômade"),
            quickStatNpc("cit_screamsheet", "Redator de Screamsheet", "Screamsheet Writer", NpcThreatTier.MOOK, "Jornalista independente (Credibilidade 2) caçando furos.", 25, 8, 5, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR)), "Credibilidade 2")
        ),
        EverydayCitizenEntry(
            "cit_security_guard", "Guarda de Segurança Civil", "Security Guard",
            listOf("Vigia de Câmeras (+Percepção)", "Policial Fazendo Bico (Moonlighting Beat Cop)"),
            quickStatNpc("cit_security_guard", "Guarda de Segurança", "Security Guard", NpcThreatTier.MOOK, "Agente vigilante (Backup 2) com uniforme Kevlar.", 35, 10, 4, 4, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Pistola Pesada PQ", 3, WeaponQuality.POOR), OfficialNpcWeaponSpec("Arma Branca Pesada PQ", 3, WeaponQuality.POOR)), "Backup 2")
        ),
        EverydayCitizenEntry(
            "cit_store_clerk", "Balconista de Loja", "Store Clerk",
            listOf("Frentista de Garagem", "Caixa de Bodega Pequena", "Atendente de Loja Especializada"),
            quickStatNpc("cit_store_clerk", "Balconista (Store Clerk)", "Store Clerk", NpcThreatTier.MOOK, "Funcionário cansado de turno noturno atrás de vidro blindado.", 25, 6, 4, 4, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 1), OfficialNpcWeaponSpec("Arma Branca Leve PQ", 1, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_streetrat", "Rato de Rua (Streetrat)", "Streetrat",
            listOf("Edgerunner Fracassado", "Ladrão Oportunista", "Aspirante a Fixer"),
            quickStatNpc("cit_streetrat", "Rato de Rua (Streetrat)", "Streetrat", NpcThreatTier.MOOK, "Sobrevivente ágil (MOV 6) sem recursos confiáveis.", 20, 6, 6, 6, 0, 4, "Couro (Corpo PA 4)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Arma Branca Média PQ", 2, WeaponQuality.POOR)))
        ),
        EverydayCitizenEntry(
            "cit_taxi_driver", "Taxista (Taxi Driver)", "Taxi Driver",
            listOf("Motorista Combat Cabb (armado)", "Condutor de Pedicab"),
            quickStatNpc("cit_taxi_driver", "Taxista", "Taxi Driver", NpcThreatTier.MOOK, "Condutor experiente (Moto 2) carregando pistola pesada sob o banco.", 30, 8, 6, 4, 7, 7, "Kevlar (PA 7)",
                listOf(OfficialNpcWeaponSpec("Briga", 2), OfficialNpcWeaponSpec("Pistola Muito Pesada PQ (5d6 cartão)", 5, WeaponQuality.POOR)), "Moto 2")
        ),
        EverydayCitizenEntry(
            "cit_wage_slave", "Escravo Corporativo (Wage Slave)", "Wage Slave",
            listOf("Dono de Microempresa", "Guerreiro de Fim de Semana"),
            quickStatNpc("cit_wage_slave", "Escravo Corporativo", "Wage Slave", NpcThreatTier.MOOK, "Funcionário de baixo escalão (Teamwork 2) sob estresse crônico.", 25, 6, 4, 4, 0, 7, "Kevlar (Corpo PA 7)",
                listOf(OfficialNpcWeaponSpec("Briga", 1), OfficialNpcWeaponSpec("Pistola Média PQ", 2, WeaponQuality.POOR)), "Teamwork 2")
        )
    )
}
