package com.example.core.rules

/**
 * Tales of the RED+ Bonus Content + Black Chrome Fixer Bosses:
 * - 12 Canonical Named NPCs (6 Black Chrome Night Market Bosses + 6 Tales of the RED+ NPCs)
 * - 1d6 More Vendors at the RC Night Market
 * - Upgraded Megabite (Lost Kids Blood Drain Rule)
 * - Screamsheet Prompts & Random Plot Hook Roller
 */
object OfficialTalesAndHookBookCatalog {

    val namedDlcNpcs: List<OfficialNpcSpec> = listOf(
        // 6 Black Chrome Night Market Fixers
        OfficialNpcSpec(
            id = "bc_3_piece", namePt = "3-Piece (Fixer Tech)", nameEn = "3-Piece",
            tier = NpcThreatTier.MINI_BOSS, roleSummaryPt = "Dono do Short Circuit e 3-Piece's Joint; especialista em tech e Netrunning.",
            intelligence = 7, reflexes = 5, dexterity = 6, technique = 6, cool = 8,
            willpower = 6, move = 4, body = 4, empathy = 5, hp = 35, seriouslyWounded = 18, deathSave = 4,
            headArmorSp = 0, bodyArmorSp = 11, armorLabelPt = "T&C Executive Armor (PA 11 • Rep 5)",
            roleAbilityLabelPt = "Operator 5 / Credibility 2",
            weapons = listOf(OfficialNpcWeaponSpec("Pistola Pesada Exc.", 3, WeaponQuality.EXCELLENT), OfficialNpcWeaponSpec("Arma Branca Leve", 1)),
            gearAndCyberwarePt = listOf("Biotoxina TechUpG DV17, 2x Skill Chip, AudioVox, Neural Link + Chipware Socket"),
            keySkillBasesPt = "Trading 14 • Persuasion 12 • Business 12 • Streetwise 12 • Bribery 12"
        ),
        OfficialNpcSpec(
            id = "bc_woodchipper", namePt = "Woodchipper (Fixer Nômade)", nameEn = "Woodchipper",
            tier = NpcThreatTier.BOSS, roleSummaryPt = "Mediadora Aldecaldo com Linear Frame Beta COR 15 e 70 PV.",
            intelligence = 5, reflexes = 4, dexterity = 8, technique = 6, cool = 7,
            willpower = 8, move = 5, body = 15, empathy = 6, hp = 70, seriouslyWounded = 35, deathSave = 15,
            headArmorSp = 12, bodyArmorSp = 12, armorLabelPt = "Subdermal TechUpG (PA 12 • Rep 7)",
            roleAbilityLabelPt = "Operator 5 / Moto 4",
            weapons = listOf(OfficialNpcWeaponSpec("Artes Marciais (Aikido)", 4)),
            gearAndCyberwarePt = listOf("Linear Frame Beta TechUpG (COR 15) + EMP Shielding, Reflex Co-Processor"),
            keySkillBasesPt = "Brawling 16 • Martial Arts 16 • Evasion 14 • Human Perception 13 • Persuasion 13"
        ),
        OfficialNpcSpec(
            id = "bc_molly_anderson", namePt = "Molly Anderson (Zona Combate)", nameEn = "Molly Anderson",
            tier = NpcThreatTier.LIEUTENANT, roleSummaryPt = "Líder da gangue Anderson na Zona de Combate Antiga.",
            intelligence = 7, reflexes = 5, dexterity = 8, technique = 5, cool = 6,
            willpower = 7, move = 6, body = 4, empathy = 4, hp = 40, seriouslyWounded = 20, deathSave = 4,
            headArmorSp = 0, bodyArmorSp = 12, armorLabelPt = "Laser Light TechUpG (PA 12 • Rep 5)",
            roleAbilityLabelPt = "Operator 6",
            weapons = listOf(OfficialNpcWeaponSpec("Granada Shuriken Tornado", 6), OfficialNpcWeaponSpec("Briga", 2)),
            gearAndCyberwarePt = listOf("Granada Arachnid, Shuriken x3, TR-4, Reflex Co-Processor, Extra-Jointed Arm"),
            keySkillBasesPt = "Local Expert 15 • Athletics/Brawling/Evasion/Contortionist/Streetwise 14"
        ),
        OfficialNpcSpec(
            id = "bc_mister_kernighan", namePt = "Mister Kernighan (Midnight Mkt)", nameEn = "Mister Kernighan",
            tier = NpcThreatTier.MINI_BOSS, roleSummaryPt = "Principal Fixer de luxo de Night City; anfitrião do Midnight Market.",
            intelligence = 8, reflexes = 5, dexterity = 6, technique = 7, cool = 8,
            willpower = 8, move = 5, body = 6, empathy = 4, hp = 45, seriouslyWounded = 23, deathSave = 6,
            headArmorSp = 0, bodyArmorSp = 11, armorLabelPt = "T&C Executive Armor (PA 11 • Rep 5)",
            roleAbilityLabelPt = "Operator 9",
            weapons = listOf(OfficialNpcWeaponSpec("Militech Fox Dual Ammo Exc.", 3, WeaponQuality.EXCELLENT)),
            gearAndCyberwarePt = listOf("Street King Vehicle, Trauma Team Exec, Sandevistan, Smart Glasses Image Enhance"),
            keySkillBasesPt = "Bureaucracy/Local Expert/Persuasion 16 • Trading/Bribery/Business/Streetwise 14"
        ),
        OfficialNpcSpec(
            id = "bc_ms_mynah", namePt = "Ms. Mynah (Moda / RC)", nameEn = "Ms. Mynah",
            tier = NpcThreatTier.LIEUTENANT, roleSummaryPt = "Fixer estilista do RC Night Market em Minimallism.",
            intelligence = 6, reflexes = 5, dexterity = 5, technique = 3, cool = 7,
            willpower = 8, move = 8, body = 5, empathy = 6, hp = 40, seriouslyWounded = 20, deathSave = 5,
            headArmorSp = 0, bodyArmorSp = 12, armorLabelPt = "Montage Variable TechUpG (PA 12 • Rep 6)",
            roleAbilityLabelPt = "Operator 6",
            weapons = listOf(OfficialNpcWeaponSpec("Artes Marciais (Taekwondo)", 2)),
            gearAndCyberwarePt = listOf("Cybereye x2 (Chyron, Low Light/IV/UV), Scrambler"),
            keySkillBasesPt = "Trading 15 • Local Expert RC 15 • Bribery/Business 13 • Martial Arts 12"
        ),
        OfficialNpcSpec(
            id = "bc_rex_royale", namePt = "Rex Royale (Cassino One Block)", nameEn = "Rex Royale",
            tier = NpcThreatTier.MINI_BOSS, roleSummaryPt = "Chefão de proteção do One Block e dono do Night Market Cassino.",
            intelligence = 7, reflexes = 7, dexterity = 7, technique = 4, cool = 8,
            willpower = 6, move = 4, body = 6, empathy = 4, hp = 40, seriouslyWounded = 20, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 12, armorLabelPt = "Subdermal Cabeça 11 / Corp Isle TechUpG 12 (Rep 7)",
            roleAbilityLabelPt = "Operator 8",
            weapons = listOf(OfficialNpcWeaponSpec("Kendachi Mono-Three", 4), OfficialNpcWeaponSpec("Westwood Exc. TechUpG", 4, WeaponQuality.EXCELLENT)),
            gearAndCyberwarePt = listOf("Badger Corporate Bus, Cybereyes Hardened Low Light/IV/UV, Subdermal Armor"),
            keySkillBasesPt = "Gamble 17 • Handgun 16 • Melee 16 • Trading 16 • Conceal 15 • Streetwise/Persuasion 14"
        ),
        // 6 Tales of the RED+ Bonus NPCs
        OfficialNpcSpec(
            id = "totr_etan_sim", namePt = "Etan Sim (Astro BD)", nameEn = "Etan Sim",
            tier = NpcThreatTier.MOOK, roleSummaryPt = "Astro de mídia e Exec secreto de Night City (Rep 8).",
            intelligence = 5, reflexes = 6, dexterity = 6, technique = 4, cool = 8,
            willpower = 5, move = 6, body = 6, empathy = 8, hp = 40, seriouslyWounded = 20, deathSave = 6,
            headArmorSp = 0, bodyArmorSp = 11, armorLabelPt = "Light Armorjack Social (PA 11 • Rep 8)",
            roleAbilityLabelPt = "Teamwork 4 (Covert Operative)",
            weapons = emptyList(),
            gearAndCyberwarePt = listOf("Conapt de luxo, Enhanced Antibodies, guarda-roupa completo"),
            keySkillBasesPt = "Acting/Conversation/Human Perception/Personal Grooming/Wardrobe 14"
        ),
        OfficialNpcSpec(
            id = "totr_fixie", namePt = "Fixie (Rede de Entregadores)", nameEn = "Fixie",
            tier = NpcThreatTier.LIEUTENANT, roleSummaryPt = "Líder da maior rede clandestina de mensageiros de Night City (Rep 5).",
            intelligence = 8, reflexes = 6, dexterity = 6, technique = 3, cool = 6,
            willpower = 5, move = 7, body = 6, empathy = 5, hp = 40, seriouslyWounded = 20, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11 • Rep 5)",
            roleAbilityLabelPt = "Operator 6",
            weapons = listOf(OfficialNpcWeaponSpec("Pistola Pesada", 3), OfficialNpcWeaponSpec("Arma Branca Leve", 1)),
            gearAndCyberwarePt = listOf("Enhanced Antibodies, Agent, suprimentos de rota"),
            keySkillBasesPt = "Athletics/Brawling/Business/Perception 14 • Local Expert (Todos Distritos) 12"
        ),
        OfficialNpcSpec(
            id = "totr_lotus_lemarch", namePt = "Lotus LeMarch (Tech Maker 8)", nameEn = "Lotus LeMarch",
            tier = NpcThreatTier.LIEUTENANT, roleSummaryPt = "Mestre inventora e fabricante de Little Europe (Maker 8, Rep 2).",
            intelligence = 8, reflexes = 4, dexterity = 4, technique = 8, cool = 4,
            willpower = 4, move = 5, body = 5, empathy = 4, hp = 35, seriouslyWounded = 18, deathSave = 5,
            headArmorSp = 0, bodyArmorSp = 0, armorLabelPt = "Sem Armadura (Rep 2)",
            roleAbilityLabelPt = "Maker 8 (Inv 6, Fab 4, Upg 4)",
            weapons = listOf(OfficialNpcWeaponSpec("Popup Very Heavy Pistol", 4)),
            gearAndCyberwarePt = listOf("2× Cyberarms (Tool Hands + Popup VHP), Interface Plugs, Image Enhance + MicroOptics"),
            keySkillBasesPt = "Basic Tech 17 • Cybertech 17 • Electronics/Security 17 • Weapons Tech 17"
        ),
        OfficialNpcSpec(
            id = "totr_veronica_stiles", namePt = "Major Veronica Stiles (Exec/Solo)", nameEn = "Major Veronica Stiles",
            tier = NpcThreatTier.MINI_BOSS, roleSummaryPt = "Comandante militar corporativa em NorCal (Exec 4 / Solo 4, Rep 7).",
            intelligence = 6, reflexes = 7, dexterity = 8, technique = 3, cool = 5,
            willpower = 7, move = 5, body = 6, empathy = 4, hp = 45, seriouslyWounded = 23, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11 • Rep 7)",
            roleAbilityLabelPt = "Teamwork 4 / Combat Awareness 4",
            weapons = listOf(OfficialNpcWeaponSpec("Pistola Pesada Exc.", 3, WeaponQuality.EXCELLENT), OfficialNpcWeaponSpec("Arma Branca Leve", 1)),
            gearAndCyberwarePt = listOf("Sandevistan, Subdermal Grip, Smart Glasses (Targeting Scope/Image Enhance)"),
            keySkillBasesPt = "Brawling 14 • Evasion 14 • Handgun 13 • Shoulder Arms 13 • Stealth 13"
        ),
        OfficialNpcSpec(
            id = "totr_michael_deckard", namePt = "Michael Deckard (Exec 7)", nameEn = "Michael Deckard",
            tier = NpcThreatTier.LIEUTENANT, roleSummaryPt = "Executivo corporativo sênior c/ Agente Secreto e Motorista (Rep 4).",
            intelligence = 6, reflexes = 6, dexterity = 6, technique = 3, cool = 8,
            willpower = 7, move = 5, body = 6, empathy = 4, hp = 45, seriouslyWounded = 23, deathSave = 6,
            headArmorSp = 0, bodyArmorSp = 7, armorLabelPt = "Kevlar Corpo (PA 7 • Rep 4)",
            roleAbilityLabelPt = "Teamwork 7",
            weapons = listOf(OfficialNpcWeaponSpec("Pistola Média Exc.", 2, WeaponQuality.EXCELLENT)),
            gearAndCyberwarePt = listOf("Internal Agent, Cybereye Chyron, Science (Biochemistry) 10"),
            keySkillBasesPt = "Persuasion 14 • Accounting/Bureaucracy/Business/Deduction/Education/Lip Reading 12"
        ),
        OfficialNpcSpec(
            id = "totr_trace_santiago", namePt = "Trace Santiago (Mídia 6)", nameEn = "Trace Santiago",
            tier = NpcThreatTier.LIEUTENANT, roleSummaryPt = "Jornalista investigativo icônico da Era do Vermelho (Credibility 6, Rep 6).",
            intelligence = 7, reflexes = 5, dexterity = 5, technique = 4, cool = 8,
            willpower = 7, move = 7, body = 5, empathy = 5, hp = 40, seriouslyWounded = 20, deathSave = 5,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11 • Rep 6)",
            roleAbilityLabelPt = "Credibility 6",
            weapons = listOf(OfficialNpcWeaponSpec("Pistola Pesada", 3)),
            gearAndCyberwarePt = listOf("Shoulder Cam, Cybereye (Chyron/MicroVideo/TeleOptics), Camera Drone, Compact Car"),
            keySkillBasesPt = "Persuasion 14 • Bribery 14 • Composition/Conversation/Deduction/Perception/Local Badlands 13"
        )
    )

    val rcNightMarketVendors1d6: List<String> = listOf(
        "1 • Anh & Long: Barraca de Pho. Pague 10eb para acessar armas descartáveis (polymer-one-shots); taxa perdoada após sucesso.",
        "2 • The Five Fathom Crew: Sucateiros subaquáticos; emprestam equipamento completo de mergulho (traje seco, nadadeiras, respiradores) para 1 serviço.",
        "3 • Beni's Discount Bentos: Marmitas quase vencidas de Oasis; ajudá-la reduz o custo mensal de Estilo de Vida em 100eb.",
        "4 • Emengarde's Escorts: Acompanhantes de alto nível; fornece fofoca quente (+5 de bônus único para achar podres de um Exec).",
        "5 • Madame Mimeux's: Chips piratas de filmes antigos — e falsificadora de elite (forja 1 documento de identidade único como recompensa).",
        "6 • Carl's Car Rentals: Ex-piloto; ajude com peças para ganhar 1 semana grátis de Carro Compacto (reparos por sua conta)."
    )

    val upgradedMegabiteRulePt: String =
        "Megabite Aprimorado (Lost Kids • TOTR+): função de sucção em combate funciona como Estrangular — após Agarrar, usa Ação (sem teste) nas rodadas seguintes para morder o pescoço e causar seu atributo COR diretamente nos PV do alvo, ignorando toda Armadura (letal)."

    val totrScreamsheetsSummaryPt: List<String> = listOf(
        "NCT 4:45 PM: Diretoras de Addiswood (Teresa & Fanta Senait) filmando 'The Parisian Hostess' c/ Etan Sim; divórcio Etan Sim x Darrel Marchant.",
        "NCT 1:03 AM (Drummer and the Whale): Vazamento de microbiota industrial Continental Brands no esgoto; reabertura do Trackside Apartments.",
        "NCT 12:26 PM (Haven't Got a Stitch to Wear): Pico histórico de casamentos; Torrell & Chiang busca aprendizes; entrega da Fixie paralisada após tiroteio.",
        "NCT 2:19 AM (Bathed in Red): Clube VR Delirium acusado de abrigar menores de rua; água Cactus Water sob suspeita de contaminação.",
        "NCT 1:45 PM (One Red Night): ConApts neoludita Pleasant Valley; Rocklin substitui linha Megabite pelo futuro Fangtastik."
    )

    fun rollPlotHook(districtFilterPt: String?, diceEngine: DiceEngine = DiceEngine()): PlotHookSpec {
        val pool = if (districtFilterPt.isNullOrBlank()) {
            OfficialHookBookData.allPlotHooks
        } else {
            OfficialHookBookData.allPlotHooks.filter { it.districtPt.equals(districtFilterPt, ignoreCase = true) }
                .ifEmpty { OfficialHookBookData.allPlotHooks }
        }
        val rawRoll = if (pool.size > 100) {
            (diceEngine.rollD100() - 1) + (diceEngine.rollPlainD10() - 1) * 10
        } else {
            diceEngine.rollD100() - 1
        }
        val idx = rawRoll % pool.size
        return pool[idx]
    }

    fun rollRcNightMarketVendor(diceEngine: DiceEngine = DiceEngine()): String {
        val r6 = diceEngine.rollNd6(1).total
        return rcNightMarketVendors1d6[(r6 - 1).coerceIn(0, 5)]
    }
}
