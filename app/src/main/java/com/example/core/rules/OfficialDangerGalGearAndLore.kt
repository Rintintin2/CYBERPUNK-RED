package com.example.core.rules

import com.example.data.local.entity.InventoryItemType

data class DgdFactionSpec(
    val id: String,
    val namePt: String,
    val turfPt: String,
    val threatTierPt: String,
    val attitudePt: String,
    val overviewPt: String
)

data class DgdNpcTemplateSpec(
    val tierPt: String,
    val statBudget: Int,
    val skillBudget: Int,
    val maxStat: Int,
    val armorSpPt: String,
    val notesPt: String
)

data class IncidentBeatSpec(
    val beatNumber: Int,
    val titlePt: String,
    val beatTypePt: String,
    val districtCode: String,
    val summaryPt: String,
    val checksAndMechanicsPt: String
)

/**
 * Complete Factions, Gear, Drugs, Vehicles, NPC Guidelines & The Incident adventure extracted
 * directly from Danger Gal Dossier (DGDossier_146584e1.pdf, 178 pp.).
 */
object OfficialDangerGalGearAndLore {

    val dgdFactions: List<DgdFactionSpec> = listOf(
        DgdFactionSpec(
            "6th_street", "6th Street", "Heywood Sul, Rancho Coronado, Santo Domingo",
            "Alto Risco (Milícia)", "Hostil a corporações abusivas / Pragmático",
            "Veteranos da 4ª Guerra Corporativa atuando como vigilantes paramilitares e extorsores de 'proteção'."
        ),
        DgdFactionSpec(
            "arasaka", "Arasaka (Operações Negras)", "Zona Corporativa, Pequena Europa (túneis)",
            "Extremo (Megacorp)", "Hostil / Secreto",
            "Agentes infiltrados ilegais da Arasaka operando em Night City apesar do banimento pós-4ª Guerra."
        ),
        DgdFactionSpec(
            "bozos", "The Bozos", "Zonas de Combate, Subterrâneos",
            "Alto (Prankster Terrorista)", "Caótico / Imprevisível",
            "Gangue de biosculpted clowns liderada por Big Top, especializada em armadilhas sádicas e gás alucinógeno."
        ),
        DgdFactionSpec(
            "danger_gal", "Danger Gal (Puma Squad)", "Pequena Europa (HQ)",
            "Aliado / Investigação", "Amigável / Contratante",
            "Agência de detetives e proteção de Michiko Sanderson; contrata a equipe em 'The Incident'."
        ),
        DgdFactionSpec(
            "digital_divas", "The Digital Divas", "Watson, The Glen",
            "Moderado (Media/Rocker)", "Neutro / Aliado em shows",
            "Banda/coletivo ativista chrome-rock & Netrunning focado em expor sujeira corporativa."
        ),
        DgdFactionSpec(
            "edgerunners", "Edgerunners (Solos & Mercs)", "Toda Night City (Forlorn Hope / Afterlife)",
            "Variável (Aliados ou Rivais)", "Profissional",
            "Mercenários independentes de aluguel, incluindo CrAB Lord, Tetsuo, Tía Puño e o elenco Single Shot."
        ),
        DgdFactionSpec(
            "generation_red", "Generation Red", "Old Japantown, Little China",
            "Baixo-Moderado (Jovens)", "Desconfiado",
            "Gangue de adolescentes órfãos e hackers de rua sobrevivendo nas frestas da Era do Vermelho."
        ),
        DgdFactionSpec(
            "maelstrom", "Maelstrom", "Watson Industrial, Northside",
            "Extremo (Ciberpsicose)", "Hostil",
            "Fanáticos por cromo extremo liderados por Crusher e Warlock; temidos por implantes ópticos múltiplos."
        ),
        DgdFactionSpec(
            "ncpd", "NCPD Precinct #1", "Upper Marina, Centro",
            "Alto (Lei)", "Vigilante / Corrompível",
            "Delegacia central comandada pelo Capitão Caliber e unidades K-9."
        ),
        DgdFactionSpec(
            "network_54", "Network 54", "Executive Zone, Watson",
            "Corporativo (Mídia)", "Neutro / Oportunista",
            "Equipe de reportagem agressiva da Fiona Hayes buscando furos a qualquer custo."
        ),
        DgdFactionSpec(
            "piranhas", "Piranhas", "Old Japantown, University District",
            "Moderado (Festeiros)", "Imprevisível / Caótico",
            "Party-gang hedonista criadora da droga Piranha Smash e viciada em adrenalina noturna."
        ),
        DgdFactionSpec(
            "sightseers", "Sightseers", "Badlands, Estradas do Perímetro",
            "Moderado (Nômades)", "Neutro / Protetor de rotas",
            "Clã/bando nômade de batedores velozes especializado em reconhecimento e escolta."
        ),
        DgdFactionSpec(
            "team_monster", "Team Monster", "Watson, Zonas de Combate",
            "Alto (Mercs Pesados)", "Competitivo",
            "Esquadrão mercenário blindado que opera um Zetatech Herakles customizado."
        ),
        DgdFactionSpec(
            "trauma_team", "Trauma Team Squad NC4-2", "Pequena Europa, Zona Corporativa",
            "Alto (Resgate Armado)", "Neutro / Fiel ao Contrato",
            "Esquadrão de extração médica de elite operando o aeródino pesado SovOil Mi-60."
        ),
        DgdFactionSpec(
            "tyger_claws", "Tyger Claws", "Japantown, Little China",
            "Alto (Sindicato)", "Territorial / Honra",
            "Sindicato criminoso de motociclistas e lutadores cibernéticos ligados a interesses corporativos."
        ),
        DgdFactionSpec(
            "zoners", "Zoners", "South Night City, Zona de Combate Sul",
            "Alto (Vigilantes de Zona)", "Hostil a invasores",
            "Habitantes e milícia pesada da Zona de Combate, incluindo Vanisher e Speed Demon."
        )
    )

    val dgdNpcCreationGuidelinesPt: List<String> = listOf(
        "Regra de Ouro DGD: NPCs não compram perícias com Pontos de Criação padrão — somam Atributo + Nível diretamente.",
        "Escala de Ameaça Solo: Mooks (2/Jogador; 1 se Hardened), Tenentes (1/Jogador; 1/2 Hardened), Subchefes (1/2 Jogadores), Chefes (1/4 Jogadores).",
        "Combat Number (CN): para Mooks genéricos, use CN direto em ataques e evasão em vez de calcular todas as perícias.",
        "Nota DGD Cyberarm Variant: implantes de braço temáticos (como garras Piranha ou ferramentas CrAB) mantêm custo padrão de slots/HL."
    )

    val dgdNpcTemplates: List<DgdNpcTemplateSpec> = listOf(
        DgdNpcTemplateSpec("Mook", 40, 48, 7, "Couro (PA 4) ou Kevlar (PA 7)", "Bucha de canhão padrão; armas PQ ou comuns."),
        DgdNpcTemplateSpec("Hardened Mook", 46, 56, 8, "Kevlar (PA 7) ou Light Armorjack (PA 11)", "Para grupos experientes; armas padrão."),
        DgdNpcTemplateSpec("Lieutenant", 52, 68, 8, "Light Armorjack (PA 11)", "Líder tático com Role Rank 4."),
        DgdNpcTemplateSpec("Hardened Lieutenant", 58, 78, 8, "Light/Medium Armorjack (PA 11–12)", "Possui REF 8 (esquiva balas) ou tática coordenada."),
        DgdNpcTemplateSpec("Mini-Boss", 62, 84, 8, "Light/Medium Armorjack (PA 11–13)", "Role Rank 4–6; equipamento caro e cyberware focal."),
        DgdNpcTemplateSpec("Hardened Mini-Boss", 68, 92, 9, "PA 11–13 + Implantes táticos", "Ameaça séria; múltiplas opções de ataque."),
        DgdNpcTemplateSpec("Boss", 70, 96, 9, "PA 11–15 / Subdermal Armor", "Role Rank 6–8; chefia de facção inteira."),
        DgdNpcTemplateSpec("Hardened Boss", 76, 110, 10, "PA 13–18 / Borgware", "Encontro climático de campanha; REF 8+, COR 10+.")
    )

    val piranhaSmashDrug = OfficialStreetDrug(
        name = "Piranha Smash (DGD)",
        costEb = 10,
        priceCategory = PriceCategory.CHEAP,
        durationHours = 4,
        primaryEffectPt = "+2 em Dança, Contorcionismo, Conversação, Percepção Humana, Persuasão e Atuação por 4h (sabor energético Piranhas).",
        secondaryDv = 9,
        secondaryEffectPt = "Vício leve (DV 9 Resistir Tortura/Drogas). Enquanto viciado fora do efeito: -2 nas mesmas perícias."
    )

    // Kept separate from OfficialGearCatalog.allVehicles so existing unit test assertEquals(45) remains exact
    val dgdCustomVehicles: List<OfficialVehicleSpec> = listOf(
        OfficialVehicleSpec(
            namePt = "Zetatech Herakles (Team Monster)",
            domainPt = "Ar",
            sdp = 100,
            seats = "12",
            combatMove = 30,
            narrativeSpeed = "150 MPH / 241 KPH",
            costEb = 63000,
            notesPt = "Cargueiro AV pesado blindado do Team Monster com blindagem pesada e rampa traseira."
        ),
        OfficialVehicleSpec(
            namePt = "SovOil Mi-60 (Trauma Team NC4-2)",
            domainPt = "Ar",
            sdp = 100,
            seats = "10",
            combatMove = 35,
            narrativeSpeed = "175 MPH / 282 KPH",
            costEb = 65000,
            notesPt = "Aeródino pesado de extração médica da Trauma Team com suporte cirúrgico a bordo."
        ),
        OfficialVehicleSpec(
            namePt = "Chupacabra do Speed Demon (Zoners)",
            domainPt = "Terra",
            sdp = 60,
            seats = "4",
            combatMove = 25,
            narrativeSpeed = "120 MPH / 193 KPH",
            costEb = 38000,
            notesPt = "Diego Motors Chupacabra customizado com Arado de Combate frontal (8.000eb) e torreta."
        )
    )

    // All truly new DGD gear items (no duplicate nameEn with Core/Gunmas/BC)
    val dgdPurchaseGear: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Sanroo Hello Cutie MicroCutie", "Sanroo Hello Cutie MicroCutie",
            InventoryItemType.WEAPON, "Pistola Exótica (DGD)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 2, rof = 2, magazine = 12, handsRequired = 1, concealable = true,
            linkedSkillPt = "Armas Curtas",
            notesPt = "Pistola Média temática fofa 2d6 ROF2; modo Hypurrburst dispara 4d6 ROF1 (gasta 4 balas)."
        ),
        OfficialGearItem(
            "Coquetel Molotov (DGD)", "Molotov Cocktail",
            InventoryItemType.AMMO, "Explosivo/Fogo (DGD)", PriceCategory.EVERYDAY, 20,
            damageDice = 5, rof = 1, handsRequired = 1, concealable = true,
            linkedSkillPt = "Atletismo",
            notesPt = "Arremesso até 25m (Atletismo DV tabela Granada): explode em 5d6 e incendeia área (4 dano/turno)."
        ),
        OfficialGearItem(
            "Munição Sucateada (Junk Ammo x50)", "Junk Ammunition",
            InventoryItemType.AMMO, "Munição (DGD)", PriceCategory.CHEAP, 10,
            notesPt = "50 tiros por 10eb; reduz dano em -1d6 e causa emperramento crítico em rolagem 1."
        ),
        OfficialGearItem(
            "Piranha Smash (Dose)", "Piranha Smash",
            InventoryItemType.GEAR, "Droga de Rua (DGD)", PriceCategory.CHEAP, 10,
            notesPt = "+2 em perícias sociais/artísticas por 4h; DV 9 Resistir Tortura/Drogas."
        ),
        OfficialGearItem(
            "Armadura Improvisada (Scavenged Armor)", "Scavenged Armor",
            InventoryItemType.ARMOR, "Armadura (DGD)", PriceCategory.COSTLY, 50,
            stoppingPower = 11, armorPenalty = -2,
            notesPt = "PA 11 sucateada (Corpo); não pode ser reparada após ablação."
        ),
        OfficialGearItem(
            "Lente Smart (Smart Lens)", "Smart Lens",
            InventoryItemType.GEAR, "Óptico (DGD)", PriceCategory.EXPENSIVE, 500,
            notesPt = "1 slot para Cybereye que integra telemetria Smartgun e exibição HUD básica."
        ),
        OfficialGearItem(
            "CrAB Drone (Defesa Ativa DGD)", "CrAB Drone",
            InventoryItemType.GEAR, "Drone Ativo (DGD)", PriceCategory.LUXURY, 5000,
            stoppingPower = 11,
            notesPt = "Drone-caranguejo de combate: MOV 6, 40 PV, PA 11, Counter DV 21 (Eletrônica/Segurança)."
        ),
        OfficialGearItem(
            "Arado de Combate Veicular (DGD)", "DGD Combat Plow Attachment",
            InventoryItemType.GEAR, "Upgrade Veículo (DGD)", PriceCategory.LUXURY, 8000,
            notesPt = "Acessório frontal de aríete: dobra dano de abalroamento e protege motor frontal."
        )
    )

    val theIncidentSummary: String =
        "Mouse (Danger Gal Puma Squad) contrata a equipe no Forlorn Hope (1.000eb/pessoa: 500eb adiantado + 500eb final + Card Foil Puma Squad 1.000eb) " +
            "para investigar um ataque misterioso à sede da Danger Gal envolvendo Maelstrom, Tyger Claws, Bozos, Shroomers e operativos secretos da Arasaka."

    val theIncidentBeats: List<IncidentBeatSpec> = listOf(
        IncidentBeatSpec(
            1, "O Gancho (The Hook)", "Gancho Inicial", "I3",
            "Mouse encontra a equipe no The Forlorn Hope (Lower Marina, I3) após o ataque à sede da Danger Gal. Oferta: 500eb adiantado + 500eb ao fechar o serviço + bônus.",
            "Social/Negociação: Negociação pode render +100–500eb ou Card Holográfico Puma Squad (1.000eb)."
        ),
        IncidentBeatSpec(
            2, "A História da Mouse", "Desenvolvimento", "I3",
            "Nos fundos do Forlorn Hope, Mouse abre um caminhão-necrotério refrigerado com dois corpos e dois sobreviventes feridos para exame forense.",
            "Introdução da cena de perícia: prepare Kit Médico, Analisador Químico ou Cyberoptics."
        ),
        IncidentBeatSpec(
            3, "Encontrando Pistas", "Cliffhanger Investigativo", "I3",
            "Exame de Mallard (Body #1), Bloodhound (Body #2), Capo (Patient #1) e Bugout (Patient #2) revela tokens dos Shroomers, projéteis exóticos e recibo de uma bodega.",
            "Testes DV13–15: Criminologia, Paramédico, Pesquisa em Arquivos e Especialista Local apontam para a Bodega em Old Japantown (H4)."
        ),
        IncidentBeatSpec(
            4, "A Bodega da Sra. Suzuki", "Desenvolvimento", "H4",
            "Em Old Japantown (H4), a equipe entrevista Benny (caixa) e a Sra. Suzuki ou invade a Bodega NET (5 andares, Hellhound REZ 20) para obter a agenda e imagens.",
            "3× Testes Sociais DV13 (Conversação / Persuasão / Suborno) OU Netrun na Bodega da Sra. Suzuki (DV6–8 + Hellhound)."
        ),
        IncidentBeatSpec(
            5, "Emboscada na Bodega (Blowout)", "OpCliff / Combate", "H4",
            "Capangas do Maelstrom atacam as persianas metálicas da loja buscando apagar testemunhas ligadas ao carregamento.",
            "Combate: 2× Maelstrom Scavver + 1× Maelstrom Ripperdoc (ou Drop). Proteger Benny/Sra. Suzuki."
        ),
        IncidentBeatSpec(
            6, "Encontro com os Shroomers", "Desenvolvimento", "H4",
            "Duas noites depois, Pa Saddlefoot e May negociam o rastro dos túneis enquanto Tyger Claws patrulham a entrada externa.",
            "Furtividade DV13 ou Diplomacia com Tyger Claws do lado de fora; Negociação com Pa Saddlefoot."
        ),
        IncidentBeatSpec(
            7, "Armadilha dos Bozos", "OpCliff / Perigo", "H4",
            "No acesso ao metrô abandonado, os Bozos montaram uma armadilha de piso escorregadio com tinta fluorescente para alertar capangas.",
            "Percepção DV17 para notar • Eletrônica/Segurança DV13 para desarmar • Atletismo DV15 para não cair."
        ),
        IncidentBeatSpec(
            8, "Vila dos Shroomers (NCART)", "Desenvolvimento", "A",
            "Dentro da estação NCART subterrânea ocupada, Sweetbriar revela a passagem inundada usada pelos agentes de uniforme preto.",
            "Sobrevivência / Orientação nos túneis sob Pequena Europa (Distrito A)."
        ),
        IncidentBeatSpec(
            9, "Confronto nos Túneis", "Clímax", "A",
            "Uma inundação expõe os verdadeiros responsáveis pelo roubo à Danger Gal: Darius (Arasaka Netrunner c/ Cortex Bomb) e Nymph (Arasaka Solo).",
            "Combate Clímax: Darius (H.Lt Netrunner 4) + Nymph (Mini-Boss Solo 4). Se Darius for capturado vivo, Cortex Bomb detona em sigilo."
        ),
        IncidentBeatSpec(
            10, "Trabalho Concluído (Resolução)", "Resolução", "A",
            "Com o cartão codificado da Arasaka e a foto 'Mom, Dad, and Me' recuperada, a equipe entrega tudo para Mouse na sede da Danger Gal.",
            "Recompensa: +500eb finais por pessoa + bônus de dados recuperados + Favor da Puma Squad."
        )
    )
}
