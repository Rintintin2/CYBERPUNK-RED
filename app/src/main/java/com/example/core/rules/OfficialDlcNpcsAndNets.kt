package com.example.core.rules

import com.example.data.local.entity.NetNodeType

data class PremadeNetFloorSpec(
    val floorLabel: String,
    val nodeType: NetNodeType,
    val title: String,
    val dvOrRez: Int
)

data class PremadeNetArchSpec(
    val namePt: String,
    val sourcePt: String,
    val totalCostEb: Int,
    val summaryPt: String,
    val floors: List<PremadeNetFloorSpec>
)

/**
 * Interface RED Vol. 1–3 Hardened Opponents (4 Mooks + 6 Lieutenants + 6 Mini-Bosses = 16),
 * Hardened Crew evaluation rules, Encounter Complications/Tactics/Promotions,
 * and 9 Premade NET Architectures (Vol 1 Single Shot + Red Chrome Cargo + Vol 2 JSK).
 */
object OfficialDlcNpcsAndNets {

    fun isHardenedEdgerunner(
        reflexes: Int,
        evasionSkill: Int,
        dexterity: Int,
        move: Int,
        willpower: Int,
        body: Int,
        highestAttackBase: Int,
        autofireOrMartialArtsSkill: Int,
        soloRank: Int,
        hasLuxuryOrBetterWeapon: Boolean
    ): Boolean {
        val dodger = reflexes >= 8 && evasionSkill >= 6
        val accurate = highestAttackBase >= 15
        val tank = (willpower + body) >= 16
        val rich = hasLuxuryOrBetterWeapon
        val agile = dexterity >= 8 && move >= 8
        val brawlerOrAuto = autofireOrMartialArtsSkill >= 6
        val solo = soloRank >= 4
        return dodger || accurate || tank || rich || agile || brawlerOrAuto || solo
    }

    val hardenedNpcs: List<OfficialNpcSpec> = listOf(
        // 4 Hardened Mooks (Vol. 2) — proporção: 1 por Jogador
        OfficialNpcSpec(
            id = "hardened_bodyguard",
            namePt = "Guarda-Costas Endurecido",
            nameEn = "Hardened Bodyguard",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Escolta veterana com Light Armorjack, Espingarda + Smartgun e Neural Link.",
            intelligence = 4, reflexes = 7, dexterity = 6, technique = 4, cool = 5,
            willpower = 5, move = 5, body = 7, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 7,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Espingarda + Smartgun", 5, WeaponQuality.EXCELLENT),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)
            ),
            gearAndCyberwarePt = listOf("Munição Balote ×25", "Subdermal Grip / Neural Link", "Comunicador de Rádio"),
            keySkillBasesPt = "Armas Longas 13 • Armas Curtas 12 • Briga 12 • Percepção 11 • Evasão 10"
        ),
        OfficialNpcSpec(
            id = "hardened_boosterganger",
            namePt = "Boosterganger Endurecido",
            nameEn = "Hardened Boosterganger",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Psicopata de gangue turbinado em Black Lace, Wolvers e SMG pesada.",
            intelligence = 3, reflexes = 7, dexterity = 7, technique = 3, cool = 4,
            willpower = 3, move = 6, body = 6, empathy = 2,
            hp = 30, seriouslyWounded = 15, deathSave = 6,
            headArmorSp = 4, bodyArmorSp = 4, armorLabelPt = "Couro (PA 4) • Sob Black Lace (ignora grav.)",
            weapons = listOf(
                OfficialNpcWeaponSpec("SMG Pesada", 3, WeaponQuality.STANDARD, "Autofire ×3"),
                OfficialNpcWeaponSpec("Wolvers (Garras 3d6)", 3, WeaponQuality.STANDARD, "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Dose de Black Lace ativa", "Wolvers no Cyberarm", "Munição H. Pistol ×40"),
            keySkillBasesPt = "Tiro Automático 12 • Armas Brancas 13 • Armas Curtas 12 • Evasão 11"
        ),
        OfficialNpcSpec(
            id = "hardened_road_ganger",
            namePt = "Ganger de Estrada Endurecido",
            nameEn = "Hardened Road Ganger",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Saqueador de rodovias com Kevlar, rifle de precisão/besta e Reflexes altos.",
            intelligence = 4, reflexes = 7, dexterity = 6, technique = 5, cool = 4,
            willpower = 4, move = 5, body = 5, empathy = 4,
            hp = 30, seriouslyWounded = 15, deathSave = 5,
            headArmorSp = 7, bodyArmorSp = 7, armorLabelPt = "Kevlar (PA 7)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto", 5),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)
            ),
            gearAndCyberwarePt = listOf("Interface Plugs + Neural Link", "Moto Urbana", "Munição Rifle ×30"),
            keySkillBasesPt = "Dirigir Terrestre 14 • Armas Longas 12 • Tiro Automático 11 • Evasão 10"
        ),
        OfficialNpcSpec(
            id = "hardened_security_operative",
            namePt = "Operativo de Segurança Endurecido",
            nameEn = "Hardened Security Operative",
            tier = NpcThreatTier.MOOK,
            roleSummaryPt = "Segurança corporativo com Light Armorjack (PA 11) e treinamento tático.",
            intelligence = 4, reflexes = 7, dexterity = 6, technique = 4, cool = 4,
            willpower = 4, move = 5, body = 6, empathy = 4,
            hp = 30, seriouslyWounded = 15, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto", 5),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)
            ),
            gearAndCyberwarePt = listOf("Rifle ×40 (AP)", "Algemas, Rádio, Lanterna"),
            keySkillBasesPt = "Armas Longas 13 • Tiro Automático 12 • Armas Curtas 12 • Percepção 11"
        ),

        // 6 Hardened Lieutenants (Vol. 2) — proporção: 1 por 2 Jogadores
        OfficialNpcSpec(
            id = "hardened_netrunner_ap",
            namePt = "Netrunner Endurecido (Anti-Pessoal)",
            nameEn = "Hardened Netrunner (Anti-Personnel)",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Especialista em fritar sinapses inimigas com Hellbolt, DeckKRASH e Nervescrub.",
            intelligence = 8, reflexes = 6, dexterity = 6, technique = 7, cool = 5,
            willpower = 6, move = 6, body = 5, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 5,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Bodyweight Suit (PA 11)",
            roleAbilityLabelPt = "Interface 6 (3 Ações NET)",
            weapons = listOf(OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)),
            programs = listOf("Hellbolt", "DeckKRASH", "Nervescrub", "Vrizzbolt", "Armor", "Shield", "Flak"),
            gearAndCyberwarePt = listOf("Cyberdeck Exc. (9 slots)", "Óculos de Virtualidade", "Interface Plugs"),
            keySkillBasesPt = "Interface 6 • Eletrônica/Seg. 14 • Criptografia 14 • Armas Curtas 12 • Evasão 12"
        ),
        OfficialNpcSpec(
            id = "hardened_netrunner_prog",
            namePt = "Netrunner Endurecido (Anti-Programa)",
            nameEn = "Hardened Netrunner (Anti-Program)",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Caçador de Black ICE e Decks com múltiplas cópias de Sword e Banhammer.",
            intelligence = 8, reflexes = 6, dexterity = 6, technique = 7, cool = 5,
            willpower = 6, move = 6, body = 5, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 5,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Bodyweight Suit (PA 11)",
            roleAbilityLabelPt = "Interface 6 (3 Ações NET)",
            weapons = listOf(OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)),
            programs = listOf("Sword ×2", "Banhammer ×2", "Speedy Gonzalvez", "See Ya", "Worm", "Shield"),
            gearAndCyberwarePt = listOf("Cyberdeck Exc. (9 slots)", "KRASH Barrier", "Interface Plugs"),
            keySkillBasesPt = "Interface 6 • Tecnologia Básica 14 • Dedução 14 • Armas Curtas 12"
        ),
        OfficialNpcSpec(
            id = "hardened_reclaimer_lightning",
            namePt = "Chefe Reclaimer (Lightning)",
            nameEn = "Hardened Reclaimer Chief (Lightning)",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Variante veloz com REF 8 (Esquiva de Balas), Kerenzikov e armas de precisão.",
            intelligence = 4, reflexes = 8, dexterity = 7, technique = 5, cool = 5,
            willpower = 5, move = 6, body = 6, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Precisão", 5),
                OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "ROF 2, ignora ½ PA")
            ),
            gearAndCyberwarePt = listOf("Kerenzikov (+2 Iniciativa)", "Targeting Scope", "Grapple Gun"),
            keySkillBasesPt = "REF 8 Esquiva Balas • Armas Longas 14 • Evasão 14 • Atletismo 13"
        ),
        OfficialNpcSpec(
            id = "hardened_reclaimer_thunder",
            namePt = "Chefe Reclaimer (Thunder)",
            nameEn = "Hardened Reclaimer Chief (Thunder)",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Variante pesada com Espingarda Exc., Lança-Granadas e COR reforçado.",
            intelligence = 4, reflexes = 7, dexterity = 6, technique = 6, cool = 5,
            willpower = 6, move = 5, body = 8, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 8,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Espingarda + Smartgun", 5, WeaponQuality.EXCELLENT),
                OfficialNpcWeaponSpec("Lança-Granadas (AP)", 6)
            ),
            gearAndCyberwarePt = listOf("Subdermal Grip", "Granadas AP ×2", "Balotes AP ×25"),
            keySkillBasesPt = "Armas Pesadas 14 • Armas Longas 14 • Demolições 13 • Resistência 13"
        ),
        OfficialNpcSpec(
            id = "hardened_security_raid",
            namePt = "Oficial de Segurança Endurecido (Raid)",
            nameEn = "Hardened Security Officer (Raid)",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Líder de assalto ágil com SMG/Rifle e escudo balístico para avanço rápido.",
            intelligence = 5, reflexes = 8, dexterity = 7, technique = 5, cool = 6,
            willpower = 5, move = 6, body = 7, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 7,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11) + Escudo (10 PV)",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto Exc.", 5, WeaponQuality.EXCELLENT),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)
            ),
            gearAndCyberwarePt = listOf("Kerenzikov (+2 Inic.)", "Granada Flashbang ×2", "Rádio tático"),
            keySkillBasesPt = "REF 8 Esquiva Balas • Tiro Automático 14 • Armas Longas 14 • Evasão 14"
        ),
        OfficialNpcSpec(
            id = "hardened_security_seige",
            namePt = "Oficial de Segurança Endurecido (Cerco)",
            nameEn = "Hardened Security Officer (Siege)",
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = "Tanque de contenção em Heavy Armorjack (PA 13) e Escudo Balístico.",
            intelligence = 5, reflexes = 7, dexterity = 6, technique = 5, cool = 6,
            willpower = 6, move = 5, body = 8, empathy = 4,
            hp = 40, seriouslyWounded = 20, deathSave = 8,
            headArmorSp = 13, bodyArmorSp = 13, armorLabelPt = "Heavy Armorjack (PA 13, -2 aplicado) + Escudo",
            armorPenaltyActive = true,
            weapons = listOf(
                OfficialNpcWeaponSpec("Espingarda + Balote AP", 5),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)
            ),
            gearAndCyberwarePt = listOf("Heavy Armorjack (PA 13)", "Escudo à Prova de Balas", "Gás Lacrimogêneo ×2"),
            keySkillBasesPt = "Armas Longas 13 (15 s/PA) • Armas Curtas 13 • Táticas 12 • Briga 12"
        ),

        // 6 Hardened Mini-Bosses (Vol. 3) — proporção: 1 por 3 Jogadores
        OfficialNpcSpec(
            id = "hardened_arasaka_assassin",
            namePt = "Assassino Arasaka Endurecido",
            nameEn = "Hardened Arasaka Assassin",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Ninja corporativo silencioso com lâmina monofilamento, camuflagem e REF 8.",
            intelligence = 7, reflexes = 8, dexterity = 8, technique = 6, cool = 7,
            willpower = 6, move = 8, body = 7, empathy = 3,
            hp = 50, seriouslyWounded = 25, deathSave = 7,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11) • Reputação 5",
            roleAbilityLabelPt = "Combat Awareness 6",
            weapons = listOf(
                OfficialNpcWeaponSpec("Kendachi Mono-Three / Espada Exc.", 4, WeaponQuality.EXCELLENT, "Ignora PA < 11"),
                OfficialNpcWeaponSpec("Pistola Pesada Silenciada", 3)
            ),
            gearAndCyberwarePt = listOf("Kerenzikov (+2 Inic.)", "Cyberoptics IV/Baixa Luz", "Grapple Hand, Reputação 5"),
            keySkillBasesPt = "REF 8 Esquiva Balas • Furtividade 16 • Armas Brancas 16 • Artes Marciais 15 • Evasão 16"
        ),
        OfficialNpcSpec(
            id = "hardened_exec_protection",
            namePt = "Proteção Executiva Endurecida",
            nameEn = "Hardened Executive Protection",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Colosso com Linear Frame Sigma (COR 12), 60 PV, Smart SMG e escudo VIP.",
            intelligence = 6, reflexes = 8, dexterity = 7, technique = 5, cool = 6,
            willpower = 7, move = 6, body = 12, empathy = 4,
            hp = 60, seriouslyWounded = 30, deathSave = 12,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11) • COR 12 • Rep 3",
            roleAbilityLabelPt = "Combat Awareness 5",
            weapons = listOf(
                OfficialNpcWeaponSpec("Malorian Sub-Flechette / SMG Pesada", 3, WeaponQuality.EXCELLENT, "Ablata PA"),
                OfficialNpcWeaponSpec("Big Knucks / Briga COR 12", 4)
            ),
            gearAndCyberwarePt = listOf("Linear Frame Sigma (COR 12)", "Pain Editor", "Subdermal Grip, Rep 3"),
            keySkillBasesPt = "REF 8 • Briga 16 • Tiro Automático 15 • Percepção 15 • Evasão 14"
        ),
        OfficialNpcSpec(
            id = "hardened_militech_veteran",
            namePt = "Veterano Militech Endurecido",
            nameEn = "Hardened Militech Veteran",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Soldado de elite com Rifle Bullpup Smart, granadas AP e foco tático.",
            intelligence = 6, reflexes = 8, dexterity = 8, technique = 6, cool = 6,
            willpower = 7, move = 6, body = 8, empathy = 4,
            hp = 55, seriouslyWounded = 28, deathSave = 8,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11) • Rep 3",
            roleAbilityLabelPt = "Combat Awareness 5",
            weapons = listOf(
                OfficialNpcWeaponSpec("Arasaka/Militech Rifle Bullpup Smart", 5, WeaponQuality.EXCELLENT),
                OfficialNpcWeaponSpec("Lança-Granadas Acoplado (AP)", 6)
            ),
            gearAndCyberwarePt = listOf("Smartgun Link + Targeting Scope", "Granadas AP ×2", "Munição AP Rifle ×50"),
            keySkillBasesPt = "REF 8 Esquiva Balas • Armas Longas 16 • Tiro Automático 16 • Táticas 14"
        ),
        OfficialNpcSpec(
            id = "hardened_outrider",
            namePt = "Outrider Endurecido",
            nameEn = "Hardened Outrider",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Comandante Nômade (Moto 6) com veia de caçador e mira óptica longa.",
            intelligence = 6, reflexes = 8, dexterity = 8, technique = 5, cool = 6,
            willpower = 6, move = 7, body = 8, empathy = 5,
            hp = 50, seriouslyWounded = 25, deathSave = 8,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11) • Moto 6 • Rep 3",
            roleAbilityLabelPt = "Moto (Família Nômade) 6",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto + Smartgun", 5, WeaponQuality.EXCELLENT),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)
            ),
            gearAndCyberwarePt = listOf("Cybereye (Targeting Scope + TeleOptics)", "Interface Plugs", "Moto Blindada"),
            keySkillBasesPt = "Moto 6 • REF 8 Esquiva Balas • Dirigir 16 • Armas Longas 16 • Evasão 15"
        ),
        OfficialNpcSpec(
            id = "hardened_pyro",
            namePt = "Pyro Endurecido",
            nameEn = "Hardened Pyro",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Especialista incendiário de Reputação 6 com Lança-Chamas + munição incendiária.",
            intelligence = 5, reflexes = 8, dexterity = 7, technique = 8, cool = 5,
            willpower = 6, move = 6, body = 8, empathy = 2,
            hp = 50, seriouslyWounded = 25, deathSave = 8,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11) • Rep 6",
            roleAbilityLabelPt = "Combat Awareness 6",
            weapons = listOf(
                OfficialNpcWeaponSpec("UrbanTech Burst Flamethrower", 3, specialNotesPt = "Incendeia alvos (4 dano/turno) ou rajada explosiva"),
                OfficialNpcWeaponSpec("Kendachi Dragon 1H", 3)
            ),
            gearAndCyberwarePt = listOf("Granadas Incendiárias ×3", "Pain Editor / Anti-Dazzle", "Traje Ignífugo"),
            keySkillBasesPt = "REF 8 • Armas Pesadas 16 • Demolições 15 • Armeiro 16 • Evasão 14"
        ),
        OfficialNpcSpec(
            id = "hardened_sniper",
            namePt = "Atirador de Elite Endurecido",
            nameEn = "Hardened Sniper",
            tier = NpcThreatTier.MINI_BOSS,
            roleSummaryPt = "Caçador de longo alcance com TeleOptics, mira IV e disparos mirados à cabeça.",
            intelligence = 7, reflexes = 8, dexterity = 8, technique = 6, cool = 7,
            willpower = 6, move = 6, body = 6, empathy = 4,
            hp = 45, seriouslyWounded = 23, deathSave = 6,
            headArmorSp = 11, bodyArmorSp = 11, armorLabelPt = "Light Armorjack (PA 11) • Rep 5",
            roleAbilityLabelPt = "Combat Awareness 5",
            weapons = listOf(
                OfficialNpcWeaponSpec("Rifle de Precisão Exc. (Smart)", 5, WeaponQuality.EXCELLENT, "+1 Atk +1 Mirado"),
                OfficialNpcWeaponSpec("SMG Pesada", 3)
            ),
            gearAndCyberwarePt = listOf("TeleOptics + Low Light/IR/UV + Targeting Scope", "Munição AP Rifle ×20"),
            keySkillBasesPt = "REF 8 • Armas Longas 17 (+Tiro Mirado) • Furtividade 16 • Percepção 16"
        )
    )

    val mookComplications1d10: List<String> = listOf(
        "1: Capanga está usando droga de combate (Black Lace ou Synthcoke).",
        "2: Carregam uma Granada Flashbang extra pronta para recuar.",
        "3: Possuem comunicador direto com reforços a 1d6 rodadas de distância.",
        "4: Um deles empunha arma Baixa Qualidade carregada com munição Perfurante (AP).",
        "5: Área com cobertura tática favorecendo emboscada (-2 em ataques à distância).",
        "6: Capanga tem um Ciberolho gravando tudo ao vivo para o contratante.",
        "7: Civil inocente no meio da linha de tiro exige cuidado no fogo cruzado.",
        "8: Estão escoltando um drone pessoal The Observer em patrulha.",
        "9: Moral abalada: recuam se metade do esquadrão sofrer Ferimento Grave.",
        "10: Um Capanga secreto é na verdade um informante disposto a negociar."
    )

    val lieutenantTactics1d10: List<String> = listOf(
        "1: Foco em Fogo Supressivo para forçar os Edgerunners a perder cobertura.",
        "2: Coordena flanqueamento em pinça com Capangas usando rádio criptografado.",
        "3: Dispara Tiro Mirado na arma ou perna do Solo mais perigoso.",
        "4: Usa Gás Lacrimogêneo ou Fumaça enquanto recua para posição vantajosa.",
        "5: Ordena uso de Granadas AP contra alvos com armadura pesada.",
        "6: Toma um refém ou usa cobertura móvel pesada antes de avançar.",
        "7: Prioriza Netrunner inimigo ou quebra linha de visão contra Sniper.",
        "8: Aplica Stim/Airhypo para ignorar Ferimento Grave e continuar lutando.",
        "9: Tenta desarmar ou imobilizar corpo a corpo para capturar vivo.",
        "10: Prepara rota de fuga motorizada caso os Subchefes caiam."
    )

    val miniBossPromotions1d10: List<String> = listOf(
        "1: Equipa Blindagem EMP e Munição Inteligente (Smart) na arma principal.",
        "2: Carrega um Pain Editor ativo (ignora penalidade de Ferimento Grave).",
        "3: Acompanhado de um Demônio Imp conectado à defesa local.",
        "4: Recebe apoio de um Drone Ativo GRAF3 na área.",
        "5: Possui Reputação +2 superior e intimida capangas aliados a não recuar.",
        "6: Arma principal é Qualidade Excelente e usa pentes estendidos.",
        "7: Tem Coprocessador de Reflexos / REF 8 com Esquiva de Balas maximizada.",
        "8: Traz 2 frascos extras de Rapidetox e Speedheal.",
        "9: Implantado com SkinWeave regenerativo sobre Light Armorjack.",
        "10: Promessa de bônus duplo pela cabeça dos Edgerunners (sem rendição)."
    )

    val premadeNetArchitectures: List<PremadeNetArchSpec> = listOf(
        PremadeNetArchSpec(
            namePt = "Starter Drone Rig",
            sourcePt = "Single Shot Pack (Vol 1)",
            totalCostEb = 10050,
            summaryPt = "3 andares • Demônio Imp + Black ICE Raven + Mini Air Drone.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Senha de Acesso", 6),
                PremadeNetFloorSpec("2", NetNodeType.BLACK_ICE, "Raven (Black ICE)", 15),
                PremadeNetFloorSpec("3", NetNodeType.CONTROL_NODE, "Nó Controle: Mini Air Drone (+Imp)", 6)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Conapt Security",
            sourcePt = "Single Shot Pack (Vol 1)",
            totalCostEb = 15050,
            summaryPt = "3 andares • Imp + Black ICE Wisp + Ground Drone.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Senha de Acesso", 6),
                PremadeNetFloorSpec("2", NetNodeType.BLACK_ICE, "Wisp (Black ICE)", 15),
                PremadeNetFloorSpec("3", NetNodeType.CONTROL_NODE, "Nó Controle: Ground Drone (+Imp)", 6)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Oasis Security",
            sourcePt = "Single Shot Pack (Vol 1)",
            totalCostEb = 16000,
            summaryPt = "5 andares • Imp + Hellhound + Câmeras + Torreta Automatizada.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Senha Balcão", 6),
                PremadeNetFloorSpec("2", NetNodeType.CONTROL_NODE, "Câmeras de Observação", 8),
                PremadeNetFloorSpec("3", NetNodeType.PASSWORD, "Trava Interna", 8),
                PremadeNetFloorSpec("4", NetNodeType.BLACK_ICE, "Hellhound (Black ICE)", 20),
                PremadeNetFloorSpec("5", NetNodeType.CONTROL_NODE, "Automated Turret (+Imp)", 8)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Clinic Security",
            sourcePt = "Single Shot Pack (Vol 1)",
            totalCostEb = 16050,
            summaryPt = "6 andares • Imp + Wisp + Câmeras + Agenda + Elevador Sleep Gas.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Recepção Clínica", 6),
                PremadeNetFloorSpec("2", NetNodeType.CONTROL_NODE, "Câmeras de Observação", 6),
                PremadeNetFloorSpec("3", NetNodeType.FILE, "Agenda de Consultas", 6),
                PremadeNetFloorSpec("4", NetNodeType.PASSWORD, "Ala Cirúrgica", 6),
                PremadeNetFloorSpec("5", NetNodeType.BLACK_ICE, "Wisp (Black ICE)", 15),
                PremadeNetFloorSpec("6", NetNodeType.CONTROL_NODE, "Elevador Sleep Gas (+Imp)", 8)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Small Corp Facility",
            sourcePt = "Single Shot Pack (Vol 1)",
            totalCostEb = 88550,
            summaryPt = "10 andares (Ramos A/B) • Imp + Efreet + Raven/Hellhound/Kraken + Drones.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Firewall Lobby", 8),
                PremadeNetFloorSpec("2", NetNodeType.FILE, "Agenda de Reuniões", 8),
                PremadeNetFloorSpec("3", NetNodeType.BLACK_ICE, "Raven (Black ICE)", 15),
                PremadeNetFloorSpec("1A", NetNodeType.PASSWORD, "Ramo A: Senha Manutenção", 8),
                PremadeNetFloorSpec("2A", NetNodeType.BLACK_ICE, "Hellhound (Black ICE)", 20),
                PremadeNetFloorSpec("3A", NetNodeType.CONTROL_NODE, "Drones de Limpeza (+Imp)", 6),
                PremadeNetFloorSpec("1B", NetNodeType.PASSWORD, "Ramo B: Trava Executiva", 10),
                PremadeNetFloorSpec("2B", NetNodeType.BLACK_ICE, "Kraken (Black ICE)", 30),
                PremadeNetFloorSpec("3B", NetNodeType.CONTROL_NODE, "Câmeras Internas (+Efreet)", 10),
                PremadeNetFloorSpec("4B", NetNodeType.CONTROL_NODE, "Large Air Drone", 10)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Vault (Cofre Máximo)",
            sourcePt = "Single Shot Pack (Vol 1)",
            totalCostEb = 100700,
            summaryPt = "9 andares • Demônio Balron + Hellhound×2 + Killer + Scorpion + Blood Swarm.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Portão Blindado", 10),
                PremadeNetFloorSpec("2", NetNodeType.FILE, "Manifesto do Cofre", 6),
                PremadeNetFloorSpec("3", NetNodeType.CONTROL_NODE, "Câmeras de Observação", 8),
                PremadeNetFloorSpec("4", NetNodeType.BLACK_ICE, "Hellhound + Killer (Black ICE)", 20),
                PremadeNetFloorSpec("5", NetNodeType.CONTROL_NODE, "Porta do Cofre (+Balron)", 10),
                PremadeNetFloorSpec("6", NetNodeType.CONTROL_NODE, "Automated Blood Swarm", 10),
                PremadeNetFloorSpec("7", NetNodeType.BLACK_ICE, "Hellhound + Scorpion (Black ICE)", 20),
                PremadeNetFloorSpec("8", NetNodeType.FILE, "Dados Sigilosos", 10),
                PremadeNetFloorSpec("9", NetNodeType.FILE, "Arquivos Ultra-Secretos", 12)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Trem Hammerhead (Red Chrome Cargo)",
            sourcePt = "Red Chrome Cargo (Vol 1)",
            totalCostEb = 14000,
            summaryPt = "5 andares • Mainframe do trem blindado (Pag. 2.000eb/Edgerunner).",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Acesso Vagoneta", 6),
                PremadeNetFloorSpec("2", NetNodeType.FILE, "Manifesto da Carga", 8),
                PremadeNetFloorSpec("3", NetNodeType.PASSWORD, "Controle de Segurança", 8),
                PremadeNetFloorSpec("4", NetNodeType.BLACK_ICE, "Hellhound (Black ICE)", 20),
                PremadeNetFloorSpec("5", NetNodeType.CONTROL_NODE, "Torreta Automática do Teto", 8)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Apartment Building (JSK)",
            sourcePt = "Jumpstart Kit Conv. (Vol 2)",
            totalCostEb = 4500,
            summaryPt = "4 andares • Prédio residencial (DV 8).",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Portaria Digital", 6),
                PremadeNetFloorSpec("2", NetNodeType.FILE, "Registro de Inquilinos", 8),
                PremadeNetFloorSpec("3", NetNodeType.CONTROL_NODE, "Câmeras & Trancas", 8),
                PremadeNetFloorSpec("4", NetNodeType.FILE, "Logs de Entrada", 8)
            )
        ),
        PremadeNetArchSpec(
            namePt = "Dorothy's Private NET (JSK)",
            sourcePt = "Jumpstart Kit Conv. (Vol 2)",
            totalCostEb = 18500,
            summaryPt = "7 andares • Arquitetura privada com Hellhound ×2.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Acesso Privado", 8),
                PremadeNetFloorSpec("2", NetNodeType.BLACK_ICE, "Hellhound #1", 20),
                PremadeNetFloorSpec("3", NetNodeType.CONTROL_NODE, "Torretas Internas", 8),
                PremadeNetFloorSpec("4", NetNodeType.FILE, "Diário de Pesquisa", 8),
                PremadeNetFloorSpec("5", NetNodeType.BLACK_ICE, "Hellhound #2", 20),
                PremadeNetFloorSpec("6", NetNodeType.CONTROL_NODE, "Elevador & Portas", 10),
                PremadeNetFloorSpec("7", NetNodeType.FILE, "Cofre Pessoal", 10)
            )
        )
    )

    val dgdNetArchitectures: List<PremadeNetArchSpec> = listOf(
        PremadeNetArchSpec(
            namePt = "Bodega da Sra. Suzuki (The Incident)",
            sourcePt = "Danger Gal Dossier (p. 164)",
            totalCostEb = 4800,
            summaryPt = "5 andares • Demons: Nenhum • Old Japantown (H4): Câmera, Persianas & Agenda.",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.PASSWORD, "Password da Bodega", 8),
                PremadeNetFloorSpec("2", NetNodeType.BLACK_ICE, "Hellhound (Black ICE)", 20),
                PremadeNetFloorSpec("3", NetNodeType.CONTROL_NODE, "Câmera de Observação", 6),
                PremadeNetFloorSpec("4", NetNodeType.CONTROL_NODE, "Persianas de Segurança", 6),
                PremadeNetFloorSpec("5", NetNodeType.FILE, "Agenda da Sra. Suzuki", 6)
            )
        ),
        PremadeNetArchSpec(
            namePt = "NET Portátil do CrAB Lord (The Incident)",
            sourcePt = "Danger Gal Dossier (p. 42)",
            totalCostEb = 18500,
            summaryPt = "4 andares • Demon: Rock Lobster (Balron • REZ 30 • INT 7 • 4 ações • Combate 14).",
            floors = listOf(
                PremadeNetFloorSpec("1", NetNodeType.BLACK_ICE, "Giant (Black ICE)", 25),
                PremadeNetFloorSpec("2", NetNodeType.PASSWORD, "Password do Chassi Móvel", 10),
                PremadeNetFloorSpec("3", NetNodeType.CONTROL_NODE, "CrAB Drone #1 (Balron Rock Lobster)", 10),
                PremadeNetFloorSpec("4", NetNodeType.CONTROL_NODE, "CrAB Drone #2 (Balron Rock Lobster)", 10)
            )
        )
    )

    val singleShotPregensPt: List<String> = listOf(
        "Forty (Rockerboy) • Grease (Fixer) • Mover (Solo) • Racer (Nomad) • Redeye (Netrunner)",
        "Torch (Tech) • Redtail (Medtech) • 24/7 (Media) • Suri 'Cavalry' Navarro (Lawman) • Chanda Mishra (Exec)",
        "Quando usados como NPCs, todos os 10 personagens do Single Shot Pack contam como Tenentes (Lieutenants)."
    )
}
