package com.example.core.rules

import com.example.data.local.entity.InventoryItemType

data class OfficialGearItem(
    val namePt: String,
    val nameEn: String,
    val itemType: InventoryItemType,
    val subCategoryPt: String,
    val priceCategory: PriceCategory,
    val costEb: Int,
    val damageDice: Int = 0,
    val stoppingPower: Int = 0,
    val armorPenalty: Int = 0,
    val rof: Int = 1,
    val magazine: Int = 0,
    val handsRequired: Int = 1,
    val concealable: Boolean = false,
    val linkedSkillPt: String = "",
    val notesPt: String = ""
)

data class OfficialStreetDrug(
    val name: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val durationHours: Int,
    val primaryEffectPt: String,
    val secondaryDv: Int,
    val secondaryEffectPt: String
)

data class OfficialTherapySpec(
    val namePt: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val materialsEb: Int,
    val medicalTechDv: Int,
    val effectPt: String
)

data class OfficialVehicleSpec(
    val namePt: String,
    val domainPt: String,
    val sdp: Int,
    val seats: String,
    val combatMove: Int,
    val narrativeSpeed: String,
    val costEb: Int,
    val notesPt: String
)

/**
 * Complete canonical Part 1 Catalogs (Core Rulebook pp. 91–102, 185, 190–191, 226–229):
 * Melee Weapons, Ranged Weapons, Exotic Weapons, Armor/Shields, General Gear, Street Drugs, Therapy, Vehicles.
 */
object OfficialGearCatalog {

    val meleeWeapons: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Arma Branca Leve (Faca de Combate / Tomahawk)", "Light Melee Weapon",
            InventoryItemType.WEAPON, "Arma Branca", PriceCategory.COSTLY, 50,
            damageDice = 1, rof = 2, concealable = true, linkedSkillPt = "Armas Brancas",
            notesPt = "1d6, ROF 2, ocultável, ignora metade da PA (arred. p/ cima)"
        ),
        OfficialGearItem(
            "Arma Branca Média (Taco / Pé-de-Cabra / Facão)", "Medium Melee Weapon",
            InventoryItemType.WEAPON, "Arma Branca", PriceCategory.COSTLY, 50,
            damageDice = 2, rof = 2, concealable = false, linkedSkillPt = "Armas Brancas",
            notesPt = "2d6, ROF 2, ignora metade da PA (arred. p/ cima)"
        ),
        OfficialGearItem(
            "Arma Branca Pesada (Espada / Cano / Taco c/ Pontas)", "Heavy Melee Weapon",
            InventoryItemType.WEAPON, "Arma Branca", PriceCategory.PREMIUM, 100,
            damageDice = 3, rof = 2, concealable = false, linkedSkillPt = "Armas Brancas",
            notesPt = "3d6, ROF 2, ignora metade da PA (arred. p/ cima)"
        ),
        OfficialGearItem(
            "Arma Branca Muito Pesada (Motosserra / Marreta / Naginata)", "Very Heavy Melee Weapon",
            InventoryItemType.WEAPON, "Arma Branca", PriceCategory.EXPENSIVE, 500,
            damageDice = 4, rof = 1, concealable = false, linkedSkillPt = "Armas Brancas",
            notesPt = "4d6, ROF 1, ignora metade da PA (arred. p/ cima)"
        )
    )

    val rangedWeapons: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Pistola Média", "Medium Pistol",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.COSTLY, 50,
            damageDice = 2, rof = 2, magazine = 12, handsRequired = 1, concealable = true,
            linkedSkillPt = "Armas Curtas", notesPt = "2d6, Pente 12 (M Pistol), ROF 2, 1 mão, ocultável"
        ),
        OfficialGearItem(
            "Pistola Pesada", "Heavy Pistol",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.PREMIUM, 100,
            damageDice = 3, rof = 2, magazine = 8, handsRequired = 1, concealable = true,
            linkedSkillPt = "Armas Curtas", notesPt = "3d6, Pente 8 (H Pistol), ROF 2, 1 mão, ocultável"
        ),
        OfficialGearItem(
            "Pistola Muito Pesada", "Very Heavy Pistol",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.PREMIUM, 100,
            damageDice = 4, rof = 1, magazine = 8, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Curtas", notesPt = "4d6, Pente 8 (VH Pistol), ROF 1, 1 mão"
        ),
        OfficialGearItem(
            "Submetralhadora (SMG)", "SMG",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.PREMIUM, 100,
            damageDice = 2, rof = 1, magazine = 30, handsRequired = 1, concealable = true,
            linkedSkillPt = "Armas Curtas", notesPt = "2d6, Pente 30 (M Pistol), Autofire (3), Fogo Supressivo"
        ),
        OfficialGearItem(
            "Submetralhadora Pesada (Heavy SMG)", "Heavy SMG",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.PREMIUM, 100,
            damageDice = 3, rof = 1, magazine = 40, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Curtas", notesPt = "3d6, Pente 40 (H Pistol), Autofire (3), Fogo Supressivo"
        ),
        OfficialGearItem(
            "Espingarda (Shotgun)", "Shotgun",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.EXPENSIVE, 500,
            damageDice = 5, rof = 1, magazine = 4, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Longas", notesPt = "5d6 (Balote) / Cartucho, Pente 4, ROF 1, 2 mãos"
        ),
        OfficialGearItem(
            "Rifle de Assalto", "Assault Rifle",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.EXPENSIVE, 500,
            damageDice = 5, rof = 1, magazine = 25, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Longas", notesPt = "5d6, Pente 25 (Rifle), Autofire (4), Fogo Supressivo"
        ),
        OfficialGearItem(
            "Rifle de Precisão (Sniper)", "Sniper Rifle",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.EXPENSIVE, 500,
            damageDice = 5, rof = 1, magazine = 4, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Longas", notesPt = "5d6, Pente 4 (Rifle), ROF 1, 2 mãos"
        ),
        OfficialGearItem(
            "Arco / Besta", "Bows & Crossbows",
            InventoryItemType.WEAPON, "Arma à Distância", PriceCategory.PREMIUM, 100,
            damageDice = 4, rof = 1, magazine = 0, handsRequired = 2, concealable = false,
            linkedSkillPt = "Arquearia", notesPt = "4d6, Flechas, ROF 1, 2 mãos"
        ),
        OfficialGearItem(
            "Lança-Granadas", "Grenade Launcher",
            InventoryItemType.WEAPON, "Arma Pesada", PriceCategory.EXPENSIVE, 500,
            damageDice = 6, rof = 1, magazine = 2, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Pesadas", notesPt = "6d6 Explosivo (10x10m), Pente 2, 2 mãos"
        ),
        OfficialGearItem(
            "Lança-Foguetes", "Rocket Launcher",
            InventoryItemType.WEAPON, "Arma Pesada", PriceCategory.EXPENSIVE, 500,
            damageDice = 8, rof = 1, magazine = 1, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Pesadas", notesPt = "8d6 Explosivo (10x10m), Pente 1, 2 mãos"
        ),
        OfficialGearItem(
            "Munição Básica (10 unid.)", "Basic Ammunition x10",
            InventoryItemType.AMMO, "Munição", PriceCategory.CHEAP, 10,
            notesPt = "10eb por 10 unidades (Pistola, Balote, Rifle, Cartucho ou Flecha)"
        )
    )

    val exoticWeapons: List<OfficialGearItem> = listOf(
        OfficialGearItem("Pistola de Ar (Air Pistol)", "Air Pistol", InventoryItemType.WEAPON, "Exótica", PriceCategory.PREMIUM, 100, 2, rof = 2, magazine = 12, concealable = true, notesPt = "Pistola Média que dispara projéteis de tinta/ácido"),
        OfficialGearItem("Luva de Batalha (Battleglove)", "Battleglove", InventoryItemType.WEAPON, "Exótica", PriceCategory.VERY_EXPENSIVE, 1000, 0, notesPt = "Manopla pesada com 3 slots de opção de Ciberbraço"),
        OfficialGearItem("Constitution Arms Hurricane", "Constitution Arms Hurricane", InventoryItemType.WEAPON, "Exótica", PriceCategory.LUXURY, 5000, 5, rof = 2, handsRequired = 2, notesPt = "Espingarda de assalto ROF 2. Requer COR 11+"),
        OfficialGearItem("Pistola de Dardos (Dartgun)", "Dartgun", InventoryItemType.WEAPON, "Exótica", PriceCategory.PREMIUM, 100, 4, rof = 1, magazine = 8, notesPt = "Pistola Muito Pesada que dispara flechas especiais"),
        OfficialGearItem("Lança-Chamas (Flamethrower)", "Flamethrower", InventoryItemType.WEAPON, "Exótica", PriceCategory.EXPENSIVE, 500, 5, rof = 1, magazine = 4, handsRequired = 2, notesPt = "Espingarda incendiária usada com Armas Pesadas"),
        OfficialGearItem("Kendachi Mono-Three", "Kendachi Mono-Three", InventoryItemType.WEAPON, "Exótica", PriceCategory.LUXURY, 5000, 4, rof = 1, handsRequired = 2, notesPt = "Arma Branca Muito Pesada 2 mãos; ignora armadura < PA 11"),
        OfficialGearItem("Malorian Arms 3516", "Malorian Arms 3516", InventoryItemType.WEAPON, "Exótica", PriceCategory.SUPER_LUXURY, 10000, 5, rof = 1, magazine = 4, notesPt = "Pistola Muito Pesada Qualidade Excelente (5d6 dano)"),
        OfficialGearItem("Microwaver", "Microwaver", InventoryItemType.WEAPON, "Exótica", PriceCategory.EXPENSIVE, 500, 0, rof = 1, notesPt = "Pistola Muito Pesada que desliga cyberware e eletrônicos"),
        OfficialGearItem("Militech \"Cowboy\" U-56", "Militech Cowboy U-56", InventoryItemType.WEAPON, "Exótica", PriceCategory.LUXURY, 5000, 6, rof = 2, handsRequired = 2, notesPt = "Lança-Granadas ROF 2. Requer COR 11+"),
        OfficialGearItem("Railgun Rhinemetall EMG-86", "Rhinemetall EMG-86 Railgun", InventoryItemType.WEAPON, "Exótica", PriceCategory.LUXURY, 5000, 5, rof = 1, handsRequired = 2, notesPt = "Rifle via Armas Pesadas; ignora PA < 11. Requer COR 11+"),
        OfficialGearItem("Shrieker", "Shrieker", InventoryItemType.WEAPON, "Exótica", PriceCategory.EXPENSIVE, 500, 0, rof = 1, notesPt = "Pistola Muito Pesada que causa Ferimento Crítico Ouvido Danificado"),
        OfficialGearItem("Bastão de Choque (Stun Baton)", "Stun Baton", InventoryItemType.WEAPON, "Exótica", PriceCategory.PREMIUM, 100, 2, rof = 2, notesPt = "Arma Branca Média menos letal"),
        OfficialGearItem("Arma de Choque (Stun Gun)", "Stun Gun", InventoryItemType.WEAPON, "Exótica", PriceCategory.PREMIUM, 100, 3, rof = 2, concealable = true, notesPt = "Pistola Pesada menos letal"),
        OfficialGearItem("Tsunami Arms Helix", "Tsunami Arms Helix", InventoryItemType.WEAPON, "Exótica", PriceCategory.LUXURY, 5000, 5, rof = 1, handsRequired = 2, notesPt = "Rifle de Assalto só Autofire (multiplicador superior). Requer COR 11+")
    )

    val armorAndShields: List<OfficialGearItem> = listOf(
        OfficialGearItem("Couros (Leathers)", "Leathers", InventoryItemType.ARMOR, "Armadura", PriceCategory.EVERYDAY, 20, stoppingPower = 4, armorPenalty = 0, notesPt = "PA 4 • Sem penalidade"),
        OfficialGearItem("Kevlar", "Kevlar", InventoryItemType.ARMOR, "Armadura", PriceCategory.COSTLY, 50, stoppingPower = 7, armorPenalty = 0, notesPt = "PA 7 • Sem penalidade"),
        OfficialGearItem("Jaqueta Blindada Leve (Light Armorjack)", "Light Armorjack", InventoryItemType.ARMOR, "Armadura", PriceCategory.PREMIUM, 100, stoppingPower = 11, armorPenalty = 0, notesPt = "PA 11 • Sem penalidade"),
        OfficialGearItem("Traje Bodyweight (Bodyweight Suit)", "Bodyweight Suit", InventoryItemType.ARMOR, "Armadura Netrunner", PriceCategory.VERY_EXPENSIVE, 1000, stoppingPower = 11, armorPenalty = 0, notesPt = "PA 11 (Corpo+Cabeça) + 1 slot só Hardware para Cyberdeck"),
        OfficialGearItem("Jaqueta Blindada Média (Medium Armorjack)", "Medium Armorjack", InventoryItemType.ARMOR, "Armadura", PriceCategory.PREMIUM, 100, stoppingPower = 12, armorPenalty = -2, notesPt = "PA 12 • -2 REF, DES e MOV"),
        OfficialGearItem("Jaqueta Blindada Pesada (Heavy Armorjack)", "Heavy Armorjack", InventoryItemType.ARMOR, "Armadura", PriceCategory.EXPENSIVE, 500, stoppingPower = 13, armorPenalty = -2, notesPt = "PA 13 • -2 REF, DES e MOV"),
        OfficialGearItem("Flak", "Flak", InventoryItemType.ARMOR, "Armadura", PriceCategory.EXPENSIVE, 500, stoppingPower = 15, armorPenalty = -4, notesPt = "PA 15 • -4 REF, DES e MOV"),
        OfficialGearItem("Metalgear", "Metalgear", InventoryItemType.ARMOR, "Armadura", PriceCategory.LUXURY, 5000, stoppingPower = 18, armorPenalty = -4, notesPt = "PA 18 • -4 REF, DES e MOV"),
        OfficialGearItem("Escudo à Prova de Balas", "Bulletproof Shield", InventoryItemType.ARMOR, "Escudo", PriceCategory.PREMIUM, 100, stoppingPower = 10, armorPenalty = 0, notesPt = "10 PV de cobertura portátil; ocupa 1 braço")
    )

    val generalGear: List<OfficialGearItem> = listOf(
        OfficialGearItem("Agent (Smartphone IA)", "Agent", InventoryItemType.GEAR, "Eletrônicos", PriceCategory.PREMIUM, 100, notesPt = "+2 em Pesquisa em Arquivos e Estilo & Vestuário"),
        OfficialGearItem("Airhypo", "Airhypo", InventoryItemType.GEAR, "Médico", PriceCategory.COSTLY, 50, notesPt = "Aplicador de fármacos/drogas"),
        OfficialGearItem("Máscara Anti-Smog", "Anti-Smog Breathing Mask", InventoryItemType.GEAR, "Sobrevivência", PriceCategory.EVERYDAY, 20, notesPt = "Filtra toxinas transportadas pelo ar"),
        OfficialGearItem("Gravador de Áudio", "Audio Recorder", InventoryItemType.GEAR, "Eletrônicos", PriceCategory.PREMIUM, 100, notesPt = "Grava até 24h de áudio em Memory Chip"),
        OfficialGearItem("Protetores Auriculares Auto-Level", "Auto Level Dampening Ear Protectors", InventoryItemType.GEAR, "Proteção", PriceCategory.VERY_EXPENSIVE, 1000, notesPt = "Imunidade a sons perigosamente altos"),
        OfficialGearItem("Binóculos", "Binoculars", InventoryItemType.GEAR, "Óptico", PriceCategory.COSTLY, 50, notesPt = "Ampliação 2x ou 3x"),
        OfficialGearItem("Visor Braindance", "Braindance Viewer", InventoryItemType.GEAR, "Eletrônicos", PriceCategory.VERY_EXPENSIVE, 1000, notesPt = "Permite assistir gravações braindance"),
        OfficialGearItem("Detector de Escutas (Bug Detector)", "Bug Detector", InventoryItemType.GEAR, "Segurança", PriceCategory.EXPENSIVE, 500, notesPt = "Apita a até 2m de dispositivos de escuta"),
        OfficialGearItem("Mochila (Carryall)", "Carryall", InventoryItemType.GEAR, "Utilidade", PriceCategory.EVERYDAY, 20, notesPt = "Bolsa de transporte padrão"),
        OfficialGearItem("Analisador Químico", "Chemical Analyzer", InventoryItemType.GEAR, "Ciência", PriceCategory.VERY_EXPENSIVE, 1000, notesPt = "Analisa composição química de substâncias"),
        OfficialGearItem("Computador", "Computer", InventoryItemType.GEAR, "Eletrônicos", PriceCategory.COSTLY, 50, notesPt = "Laptop ou desktop"),
        OfficialGearItem("Cyberdeck (7 Slots)", "Cyberdeck", InventoryItemType.CYBERDECK, "Netrunning", PriceCategory.EXPENSIVE, 500, notesPt = "Deck básico de Netrunning com 7 slots de Programa/Hardware"),
        OfficialGearItem("Celular Descartável", "Disposable Cell Phone", InventoryItemType.GEAR, "Comunicação", PriceCategory.COSTLY, 50, notesPt = "Faz chamadas, fácil de descartar"),
        OfficialGearItem("Sintetizador de Bateria", "Drum Synthesizer", InventoryItemType.GEAR, "Música", PriceCategory.EXPENSIVE, 500, notesPt = "Simula bateria (precisa de amplificador)"),
        OfficialGearItem("Fita Adesiva (Duct Tape)", "Duct Tape", InventoryItemType.GEAR, "Utilidade", PriceCategory.EVERYDAY, 20, notesPt = "Várias cores, inclusive neon"),
        OfficialGearItem("Guitarra Elétrica / Instrumento", "Electric Guitar/Other Instrument", InventoryItemType.GEAR, "Música", PriceCategory.EXPENSIVE, 500, notesPt = "Instrumento para Performance"),
        OfficialGearItem("Lanterna", "Flashlight", InventoryItemType.GEAR, "Utilidade", PriceCategory.EVERYDAY, 20, notesPt = "Feixe de 100m, dura 10h por carga"),
        OfficialGearItem("Barra de Ração (Food Stick)", "Food Stick", InventoryItemType.GEAR, "Ração", PriceCategory.CHEAP, 10, notesPt = "1 refeição horrível em barra"),
        OfficialGearItem("Tinta Fluorescente (Glow Paint)", "Glow Paint", InventoryItemType.GEAR, "Utilidade", PriceCategory.EVERYDAY, 20, notesPt = "Spray que brilha no escuro"),
        OfficialGearItem("Bastão Luminoso (Glow Stick)", "Glow Stick", InventoryItemType.GEAR, "Utilidade", PriceCategory.CHEAP, 10, notesPt = "Ilumina 4m por 10h"),
        OfficialGearItem("Pistola de Gancho (Grapple Gun)", "Grapple Gun", InventoryItemType.GEAR, "Mobilidade", PriceCategory.PREMIUM, 100, notesPt = "Dispara cabo de até 30m (não é arma)"),
        OfficialGearItem("Algemas", "Handcuffs", InventoryItemType.GEAR, "Contenção", PriceCategory.COSTLY, 50, notesPt = "Requer COR > 10 para quebrar"),
        OfficialGearItem("Rastreador (Homing Tracer)", "Homing Tracer", InventoryItemType.GEAR, "Rastreamento", PriceCategory.EXPENSIVE, 500, notesPt = "Rastreia marcador vinculado a até 1 milha"),
        OfficialGearItem("Colchão Inflável & Saco de Dormir", "Inflatable Bed & Sleep-bag", InventoryItemType.GEAR, "Sobrevivência", PriceCategory.EVERYDAY, 20, notesPt = "Cama portátil dobrável"),
        OfficialGearItem("Pacote de Kibble", "Kibble Pack", InventoryItemType.GEAR, "Ração", PriceCategory.CHEAP, 10, notesPt = "1 refeição de Kibble"),
        OfficialGearItem("Kit de Gazua (Lock Picking Set)", "Lock Picking Set", InventoryItemType.GEAR, "Infiltração", PriceCategory.EVERYDAY, 20, notesPt = "Para arrombar fechaduras mecânicas"),
        OfficialGearItem("Medscanner", "Medscanner", InventoryItemType.GEAR, "Médico", PriceCategory.VERY_EXPENSIVE, 1000, notesPt = "+2 em Primeiros Socorros e Paramédico"),
        OfficialGearItem("Bolsa Médica (Medtech Bag)", "Medtech Bag", InventoryItemType.GEAR, "Médico", PriceCategory.PREMIUM, 100, notesPt = "Kit completo médico de campo"),
        OfficialGearItem("Memory Chip", "Memory Chip", InventoryItemType.GEAR, "Eletrônicos", PriceCategory.CHEAP, 10, notesPt = "Armazenamento padrão de dados"),
        OfficialGearItem("MRE (Ração Autoaquecida)", "MRE", InventoryItemType.GEAR, "Ração", PriceCategory.CHEAP, 10, notesPt = "1 refeição autoaquecida"),
        OfficialGearItem("Personal CarePak", "Personal CarePak", InventoryItemType.GEAR, "Higiene", PriceCategory.EVERYDAY, 20, notesPt = "Itens de higiene pessoal"),
        OfficialGearItem("Amplificador de Bolso", "Pocket Amplifier", InventoryItemType.GEAR, "Música", PriceCategory.COSTLY, 50, notesPt = "Suporta até 2 instrumentos, 6h por carga"),
        OfficialGearItem("Detector de Radar", "Radar Detector", InventoryItemType.GEAR, "Segurança", PriceCategory.EXPENSIVE, 500, notesPt = "Apita se feixe de radar ativo a 100m"),
        OfficialGearItem("Traje Anti-Radiação", "Radiation Suit", InventoryItemType.GEAR, "Proteção", PriceCategory.VERY_EXPENSIVE, 1000, notesPt = "Protege contra radiação"),
        OfficialGearItem("Comunicador de Rádio", "Radio Communicator", InventoryItemType.GEAR, "Comunicação", PriceCategory.PREMIUM, 100, notesPt = "Ponto auricular, alcance 1 milha"),
        OfficialGearItem("Scanner de Rádio / Player", "Radio Scanner/Music Player", InventoryItemType.GEAR, "Comunicação", PriceCategory.COSTLY, 50, notesPt = "Sintoniza bandas de rádio até 1 milha"),
        OfficialGearItem("Sinalizador Rodoviário (Road Flare)", "Road Flare", InventoryItemType.GEAR, "Utilidade", PriceCategory.CHEAP, 10, notesPt = "Ilumina área de 100m por 1h"),
        OfficialGearItem("Corda (60m)", "Rope (60m/yd)", InventoryItemType.GEAR, "Utilidade", PriceCategory.EVERYDAY, 20, notesPt = "Corda de nylon de 60m"),
        OfficialGearItem("Codificador/Decodificador", "Scrambler/Descrambler", InventoryItemType.GEAR, "Comunicação", PriceCategory.EXPENSIVE, 500, notesPt = "Criptografa e descriptografa comunicações"),
        OfficialGearItem("Óculos Smart (Smart Glasses)", "Smart Glasses", InventoryItemType.GEAR, "Óptico", PriceCategory.EXPENSIVE, 500, notesPt = "Possui 2 slots para opções de Ciberóptico"),
        OfficialGearItem("Bolsa Técnica (Tech Bag)", "Tech Bag", InventoryItemType.GEAR, "Técnico", PriceCategory.EXPENSIVE, 500, notesPt = "Kit pequeno de ferramentas"),
        OfficialGearItem("Techscanner", "Techscanner", InventoryItemType.GEAR, "Técnico", PriceCategory.VERY_EXPENSIVE, 1000, notesPt = "+2 em múltiplas perícias de Técnica (TEC)"),
        OfficialGearItem("Techtool", "Techtool", InventoryItemType.GEAR, "Técnico", PriceCategory.PREMIUM, 100, notesPt = "Multiferramenta tudo-em-um"),
        OfficialGearItem("Barraca & Camping", "Tent and Camping Equipment", InventoryItemType.GEAR, "Sobrevivência", PriceCategory.COSTLY, 50, notesPt = "Equipamento de acampamento para 1 pessoa"),
        OfficialGearItem("Frasco de Biotoxina", "Vial of Biotoxin", InventoryItemType.GEAR, "Toxina", PriceCategory.EXPENSIVE, 500, damageDice = 3, notesPt = "3d6 dano (ignora PA); resistido por Resistir Tortura/Drogas"),
        OfficialGearItem("Frasco de Veneno", "Vial of Poison", InventoryItemType.GEAR, "Toxina", PriceCategory.PREMIUM, 100, damageDice = 2, notesPt = "2d6 dano (ignora PA); resistido por Resistir Tortura/Drogas"),
        OfficialGearItem("Câmera de Vídeo", "Video Camera", InventoryItemType.GEAR, "Eletrônicos", PriceCategory.PREMIUM, 100, notesPt = "Grava até 12h áudio/vídeo em Memory Chip"),
        OfficialGearItem("Óculos de Virtualidade", "Virtuality Goggles", InventoryItemType.GEAR, "Netrunning", PriceCategory.PREMIUM, 100, notesPt = "Sobrepõe ciberespaço na visão real durante Netrun")
    )

    val streetDrugs: List<OfficialStreetDrug> = listOf(
        OfficialStreetDrug(
            name = "Black Lace",
            costEb = 50,
            priceCategory = PriceCategory.COSTLY,
            durationHours = 24,
            primaryEffectPt = "Sofre 2d6 HL imediato (devolvido se resistir ao vício). Ignora penalidades de Ferimento Grave.",
            secondaryDv = 17,
            secondaryEffectPt = "HL não devolvido; vicia (fora do efeito primário, REF reduzido em -2)."
        ),
        OfficialStreetDrug(
            name = "Blue Glass",
            costEb = 20,
            priceCategory = PriceCategory.EVERYDAY,
            durationHours = 4,
            primaryEffectPt = "Alucinações vívidas ('flash out') fazendo perder a Ação em alguns turnos. Viciados tomam para estabilizar.",
            secondaryDv = 15,
            secondaryEffectPt = "Vicia; sofre episódios periódicos de 'flash out' perdendo Ação no turno."
        ),
        OfficialStreetDrug(
            name = "Boost",
            costEb = 50,
            priceCategory = PriceCategory.COSTLY,
            durationHours = 24,
            primaryEffectPt = "Aumenta INT em +2 por 24h (pode passar de 8).",
            secondaryDv = 17,
            secondaryEffectPt = "Vicia; enquanto viciado, INT reduzido em -2."
        ),
        OfficialStreetDrug(
            name = "Smash",
            costEb = 10,
            priceCategory = PriceCategory.CHEAP,
            durationHours = 4,
            primaryEffectPt = "+2 em Dança, Contorcionismo, Conversação, Percepção Humana, Persuasão e Atuação.",
            secondaryDv = 15,
            secondaryEffectPt = "Vicia; -2 nas mesmas perícias sociais/artísticas por apatia."
        ),
        OfficialStreetDrug(
            name = "Synthcoke",
            costEb = 20,
            priceCategory = PriceCategory.EVERYDAY,
            durationHours = 4,
            primaryEffectPt = "+1 em REF (pode passar de 8), com tendência à paranoia.",
            secondaryDv = 15,
            secondaryEffectPt = "Vicia; enquanto fora do efeito primário, REF reduzido em -2."
        ),
        OfficialDlcGearCatalog.emeraldCityDrug,
        OfficialDangerGalGearAndLore.piranhaSmashDrug
    )

    val therapies: List<OfficialTherapySpec> = listOf(
        OfficialTherapySpec(
            namePt = "Terapia de Dependência",
            costEb = 1000,
            priceCategory = PriceCategory.VERY_EXPENSIVE,
            materialsEb = 500,
            medicalTechDv = 15,
            effectPt = "1 semana; remove 1 vício (por 1 ano, falha automático em Resistir se usar a droga)."
        ),
        OfficialTherapySpec(
            namePt = "Terapia de Humanidade Padrão",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            materialsEb = 100,
            medicalTechDv = 15,
            effectPt = "1 semana; recupera 2d6 Humanidade (máximo reduzido em -2 por cyberware / -4 por borgware com HL)."
        ),
        OfficialTherapySpec(
            namePt = "Terapia de Humanidade Extrema",
            costEb = 1000,
            priceCategory = PriceCategory.VERY_EXPENSIVE,
            materialsEb = 500,
            medicalTechDv = 17,
            effectPt = "1 semana; recupera 4d6 Humanidade (respeitando o teto máximo reduzido por implantes)."
        )
    )

    val vehicles: List<OfficialVehicleSpec> = listOf(
        OfficialVehicleSpec("Moto Urbana (Roadbike)", "Terra", 35, "2", 20, "100 MPH / 161 KPH", 20000, "Moto comum a CHOOH²"),
        OfficialVehicleSpec("Superbike", "Terra", 35, "2", 60, "300 MPH / 483 KPH", 100000, "Moto exótica de altíssima velocidade"),
        OfficialVehicleSpec("Carro Compacto (Compact Groundcar)", "Terra", 50, "4", 20, "100 MPH / 161 KPH", 30000, "Carro comum a CHOOH²"),
        OfficialVehicleSpec("Esportivo de Alta Performance", "Terra", 50, "4", 40, "200 MPH / 322 KPH", 50000, "Esportivo a CHOOH²"),
        OfficialVehicleSpec("Super Carro Terrestre", "Terra", 50, "2", 60, "300 MPH / 483 KPH", 100000, "Superesportivo exótico"),
        OfficialVehicleSpec("Jetski", "Mar", 35, "2", 20, "60 MPH / 97 KPH", 20000, "Veículo aquático pessoal"),
        OfficialVehicleSpec("Lancha (Speedboat)", "Mar", 50, "4", 20, "60 MPH / 97 KPH", 30000, "Lancha a CHOOH²"),
        OfficialVehicleSpec("Cruzeiro de Cabine", "Mar", 60, "2/quarto (mín 2)", 10, "15 MPH / 24 KPH", 30000, "30.000eb por quarto"),
        OfficialVehicleSpec("Iate de Luxo", "Mar", 100, "4/quarto (mín 4)", 10, "15 MPH / 24 KPH", 50000, "50.000eb por quarto"),
        OfficialVehicleSpec("Girocóptero", "Ar", 35, "2", 20, "100 MPH / 161 KPH", 20000, "Aeronave leve de rotor"),
        OfficialVehicleSpec("Helicóptero", "Ar", 60, "4", 40, "200 MPH / 322 KPH", 40000, "Helicóptero completo a CHOOH²"),
        OfficialVehicleSpec("AV-4 Aerodyne", "Ar", 100, "6", 40, "200 MPH / 322 KPH", 50000, "Aeródino VTOL multipropósito"),
        OfficialVehicleSpec("AV-9 Super Aerodyne", "Ar", 60, "2", 60, "300 MPH / 483 KPH", 100000, "Aeródino super exótico"),
        OfficialVehicleSpec("Aerozep", "Ar", 100, "2/quarto (mín 2)", 20, "100 MPH / 161 KPH", 30000, "Dirigível de carga; 30.000eb/quarto")
    )

    val allVehicles: List<OfficialVehicleSpec> =
        vehicles + OfficialBlackChromeGearCatalog.blackChromeVehicles

    val quickPurchaseCatalog: List<OfficialGearItem> =
        armorAndShields + rangedWeapons + meleeWeapons + exoticWeapons + generalGear +
            OfficialWeaponsAndDefensesCatalog.masterGearAdditions +
            OfficialDlcGearCatalog.allDlcPurchaseGear +
            OfficialBlackChromeGearCatalog.allBlackChromePurchaseGear +
            OfficialStealthAndAgentsCatalog.allStealthAndAgentPurchaseGear +
            OfficialDangerGalGearAndLore.dgdPurchaseGear
}
