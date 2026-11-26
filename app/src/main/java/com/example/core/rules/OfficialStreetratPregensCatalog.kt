package com.example.core.rules

import com.example.data.local.entity.InventoryItemType

data class PregenCyberwareEntry(
    val namePt: String,
    val slotArea: String,
    val humanityCost: Int,
    val notesPt: String
)

data class PregenGearEntry(
    val namePt: String,
    val itemType: InventoryItemType,
    val priceCategory: PriceCategory,
    val costEb: Int,
    val quantity: Int = 1,
    val damageDice: Int = 0,
    val stoppingPower: Int = 0,
    val isEquipped: Boolean = false,
    val notesPt: String = ""
)

data class StreetratPregenSpec(
    val id: String,
    val handle: String,
    val role: RedRole,
    val roleRank: Int = 4,
    val roleSpecialDetailPt: String,
    val bioPt: String,
    val stats: CharacterStats,
    val currentHumanity: Int,
    val maxHumanity: Int,
    val headSp: Int = 11,
    val bodySp: Int = 11,
    val startingCashEb: Int = 500,
    val skills: Map<String, Int>,
    val cyberware: List<PregenCyberwareEntry>,
    val inventory: List<PregenGearEntry>,
    val weaponsSummaryPt: List<OfficialNpcWeaponSpec>,
    val programs: List<String> = emptyList()
) {
    fun toNpcSpec(): OfficialNpcSpec {
        val hp = CharacterCalculator.calculateMaxHp(stats.body, stats.willpower)
        val sw = CharacterCalculator.calculateSeriouslyWoundedThreshold(hp)
        return OfficialNpcSpec(
            id = "pregen_$id",
            namePt = "$handle (${role.displayNamePt})",
            nameEn = handle,
            tier = NpcThreatTier.LIEUTENANT,
            roleSummaryPt = bioPt,
            intelligence = stats.intelligence,
            reflexes = stats.reflexes,
            dexterity = stats.dexterity,
            technique = stats.technique,
            cool = stats.cool,
            willpower = stats.willpower,
            move = stats.move,
            body = stats.body,
            empathy = currentHumanity / 10,
            hp = hp,
            seriouslyWounded = sw,
            deathSave = stats.body,
            headArmorSp = headSp,
            bodyArmorSp = bodySp,
            armorLabelPt = "Light Armorjack (PA $bodySp)",
            roleAbilityLabelPt = "${role.roleAbilityPt} $roleRank • $roleSpecialDetailPt",
            weapons = weaponsSummaryPt,
            programs = programs,
            gearAndCyberwarePt = cyberware.map { it.namePt } + inventory.map { it.namePt },
            keySkillBasesPt = skills.entries
                .filter { it.value >= 4 }
                .take(8)
                .joinToString(" • ") { (k, v) -> "$k ($v)" }
        )
    }
}

/**
 * Complete Single Shot Pack (Npcs_e_Arquiteturas_de_rede.pdf, 28 pp.):
 * All 10 official Streetrat Pregens with exact stats, 20 skills, cyberware, weapons, armor & 500eb.
 * (Also usable as Lieutenants/Allies via toNpcSpec().)
 */
object OfficialStreetratPregensCatalog {

    val allPregens: List<StreetratPregenSpec> = listOf(
        StreetratPregenSpec(
            id = "forty",
            handle = "Forty",
            role = RedRole.ROCKERBOY,
            roleSpecialDetailPt = "Impacto Carismático 4 (Influencia multidões até ~100 fãs)",
            bioPt = "Estrela rebelde do circuito Chrome Rock capaz de inflamar ruas inteiras com um riff.",
            stats = CharacterStats(5, 6, 7, 5, 7, 8, 5, 7, 3, 7),
            currentHumanity = 61,
            maxHumanity = 70,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.ROCKERBOY).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Cyberaudio Suite + Audio Recorder", "Ciberáudio", 9, "Grava faixas e conversas em Memory Chip"),
                PregenCyberwareEntry("Chemskin & Techhair", "Fashionware", 0, "+2 em Aparência Pessoal")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Pistola Muito Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 4, isEquipped = true, notesPt = "4d6 ROF 1 + 50 munições"),
                PregenGearEntry("Arma Branca Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 3, isEquipped = true, notesPt = "3d6 ROF 2 (ignora ½ PA)"),
                PregenGearEntry("Granada Gás Lacrimogêneo ×2", InventoryItemType.AMMO, PriceCategory.COSTLY, 50, 2, notesPt = "10×10m Resistir DV13 ou -4 vis."),
                PregenGearEntry("Guitarra Elétrica & Agent", InventoryItemType.GEAR, PriceCategory.EXPENSIVE, 500, 1, notesPt = "Equipamento de palco e Agent")
            ),
            weaponsSummaryPt = listOf(
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "ROF 2, ignora ½ PA")
            )
        ),
        StreetratPregenSpec(
            id = "grease",
            handle = "Grease",
            role = RedRole.FIXER,
            roleSpecialDetailPt = "Operador 4 (Acesso a itens Caros 500eb + 2 idiomas extras)",
            bioPt = "Negociadora astuta de corredores obscuros com Agent interno e detector vocal de mentiras.",
            stats = CharacterStats(8, 7, 5, 5, 6, 7, 7, 5, 3, 6),
            currentHumanity = 44,
            maxHumanity = 60,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.FIXER).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Cyberaudio Suite + Internal Agent + Voice Stress Analyzer", "Ciberáudio", 13, "Agent oculto e +2 Percepção Humana/Interrogatório"),
                PregenCyberwareEntry("Bolso Subdérmico (Subdermal Pocket)", "Cyberware Externo", 3, "Compartimento oculto 5×10cm")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Pistola Muito Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 4, isEquipped = true, notesPt = "4d6 ROF 1"),
                PregenGearEntry("Pistola Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 3, notesPt = "3d6 ROF 2 ocultável"),
                PregenGearEntry("Arma Branca Leve", InventoryItemType.WEAPON, PriceCategory.COSTLY, 50, 1, damageDice = 1, notesPt = "1d6 ROF 2"),
                PregenGearEntry("Celular Descartável & Bug Detector", InventoryItemType.GEAR, PriceCategory.EXPENSIVE, 500, 1, notesPt = "Kit de contrainteligência")
            ),
            weaponsSummaryPt = listOf(
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Pistola Pesada", 3)
            )
        ),
        StreetratPregenSpec(
            id = "mover",
            handle = "Mover",
            role = RedRole.SOLO,
            roleSpecialDetailPt = "Percepção de Combate 4 (+4 pts flexíveis em Iniciativa/Ataque/Dano/Evasão)",
            bioPt = "Especialista tático frio sob fogo cruzado com Sandevistan e Rifle de Assalto.",
            stats = CharacterStats(7, 7, 6, 5, 7, 6, 6, 7, 7, 5),
            currentHumanity = 36,
            maxHumanity = 50,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.SOLO).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Neural Link + Sandevistan", "Neuralware", 14, "Ação: +3 Iniciativa por 1 min"),
                PregenCyberwareEntry("Biomonitor", "Fashionware", 0, "Telemetria vital")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Rifle de Assalto", InventoryItemType.WEAPON, PriceCategory.EXPENSIVE, 500, 1, damageDice = 5, isEquipped = true, notesPt = "5d6 + Autofire(4) + 70 balas"),
                PregenGearEntry("Pistola Muito Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 4, notesPt = "4d6 ROF 1 + 30 balas"),
                PregenGearEntry("Arma Branca Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 3, notesPt = "3d6 ROF 2 (ignora ½ PA)"),
                PregenGearEntry("Agent", InventoryItemType.GEAR, PriceCategory.PREMIUM, 100, 1, notesPt = "+2 Arquivos e Estilo")
            ),
            weaponsSummaryPt = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto", 5),
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "ROF 2, ignora ½ PA")
            )
        ),
        StreetratPregenSpec(
            id = "racer",
            handle = "Racer",
            role = RedRole.NOMAD,
            roleSpecialDetailPt = "Moto 4 (+4 testes de veículo + Carro Compacto blindado da Família: 6 lugares, 70 SDP, Heavy Chassis)",
            bioPt = "Piloto nômade com Interface Plugs conectado ao próprio carro blindado modificado.",
            stats = CharacterStats(7, 6, 8, 4, 6, 6, 6, 5, 6, 5),
            currentHumanity = 36,
            maxHumanity = 50,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.NOMAD).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Neural Link + Interface Plugs", "Neuralware", 14, "Direção neural sem mãos (+Veículos/Smartguns)")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Carro Compacto (Família Moto 4)", InventoryItemType.GEAR, PriceCategory.SUPER_LUXURY, 30000, 1, stoppingPower = 0, isEquipped = true, notesPt = "70 SDP, 6 assentos, Heavy Chassis + Assentos + Moradia"),
                PregenGearEntry("Pistola Muito Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 4, isEquipped = true, notesPt = "4d6 ROF 1"),
                PregenGearEntry("Arma Branca Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 3, notesPt = "3d6 ROF 2")
            ),
            weaponsSummaryPt = listOf(
                OfficialNpcWeaponSpec("Pistola Muito Pesada", 4),
                OfficialNpcWeaponSpec("Arma Branca Pesada", 3, specialNotesPt = "ROF 2, ignora ½ PA")
            )
        ),
        StreetratPregenSpec(
            id = "redeye",
            handle = "Redeye",
            role = RedRole.NETRUNNER,
            roleSpecialDetailPt = "Interface 4 (3 Ações NET) + Cyberdeck 7 slots: Armor, Sword, See Ya, Vrizzbolt, Worm",
            bioPt = "Invasora digital cirúrgica com Virtuality Óptica direta e deck carregado.",
            stats = CharacterStats(6, 6, 6, 7, 8, 4, 7, 7, 6, 6),
            currentHumanity = 46,
            maxHumanity = 60,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.NETRUNNER).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Neural Link + Interface Plugs", "Neuralware", 14, "Conexão NET direta"),
                PregenCyberwareEntry("Shift Tacts", "Fashionware", 0, "Íris cromáticas mutáveis")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Cyberdeck (7 Slots)", InventoryItemType.CYBERDECK, PriceCategory.EXPENSIVE, 500, 1, isEquipped = true, notesPt = "Programas: Armor, Sword, See Ya, Vrizzbolt, Worm"),
                PregenGearEntry("Óculos de Virtualidade", InventoryItemType.GEAR, PriceCategory.PREMIUM, 100, 1, isEquipped = true, notesPt = "Projeta ciberespaço"),
                PregenGearEntry("Pistola Muito Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 4, isEquipped = true, notesPt = "4d6 ROF 1")
            ),
            weaponsSummaryPt = listOf(OfficialNpcWeaponSpec("Pistola Muito Pesada", 4)),
            programs = listOf("Armor", "Sword", "See Ya", "Vrizzbolt", "Worm")
        ),
        StreetratPregenSpec(
            id = "torch",
            handle = "Torch",
            role = RedRole.TECH,
            roleSpecialDetailPt = "Inventor (Maker) 4: Campo 4 / Aprimoramento 2 / Fabricação 2 (sem Inv. 0)",
            bioPt = "Engenheira de campo pragmática equipada com MicroOptics, Tool Hand e Espingarda.",
            stats = CharacterStats(8, 7, 5, 6, 3, 3, 7, 6, 6, 7),
            currentHumanity = 58,
            maxHumanity = 70,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.TECH).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Cybereye + MicroOptics", "Ciberópticos", 9, "Zoom microscópico 400× p/ microeletrônica"),
                PregenCyberwareEntry("Cyberarm + Tool Hand", "Cibermembros", 10, "Ferramentas integradas nos dedos"),
                PregenCyberwareEntry("Skinwatch", "Fashionware", 0, "Cronômetro subcutâneo")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Espingarda (Shotgun)", InventoryItemType.WEAPON, PriceCategory.EXPENSIVE, 500, 1, damageDice = 5, isEquipped = true, notesPt = "5d6 Balote/Cartucho"),
                PregenGearEntry("Granada Flashbang ×1", InventoryItemType.AMMO, PriceCategory.PREMIUM, 100, 1, notesPt = "10×10m cegueira/surdez"),
                PregenGearEntry("Tech Bag & Techtool", InventoryItemType.GEAR, PriceCategory.EXPENSIVE, 500, 1, notesPt = "Kit completo de reparos")
            ),
            weaponsSummaryPt = listOf(OfficialNpcWeaponSpec("Espingarda (Shotgun)", 5))
        ),
        StreetratPregenSpec(
            id = "redtail",
            handle = "Redtail",
            role = RedRole.MEDTECH,
            roleSpecialDetailPt = "Medicina 4: Cirurgia 1 (Nv 2) / Fármacos 2 (Nv 3) / Crio 1 (Cryopump) (+3 MedTech)",
            bioPt = "Médico de combate completo com Cryopump portátil, TeleOptics e Rifle de Assalto.",
            stats = CharacterStats(8, 5, 5, 8, 5, 5, 6, 6, 5, 6),
            currentHumanity = 48,
            maxHumanity = 60,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.MEDTECH).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Cybereye + TeleOptics", "Ciberópticos", 10, "Alcance 800m; +1 Mirado ≥51m"),
                PregenCyberwareEntry("Filtros Nasais (Nasal Filters)", "Cyberware Interno", 2, "Imune a gases tóxicos e fumaça"),
                PregenCyberwareEntry("Biomonitor", "Fashionware", 0, "Sinais vitais ao vivo")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Rifle de Assalto", InventoryItemType.WEAPON, PriceCategory.EXPENSIVE, 500, 1, damageDice = 5, isEquipped = true, notesPt = "5d6 + Autofire(4)"),
                PregenGearEntry("Cryopump & Medtech Bag", InventoryItemType.GEAR, PriceCategory.EXPENSIVE, 500, 1, isEquipped = true, notesPt = "Estase criogênica portátil e fármacos"),
                PregenGearEntry("Granada de Fumaça ×2", InventoryItemType.AMMO, PriceCategory.COSTLY, 50, 2, notesPt = "Cobertura de fumaça 10×10m")
            ),
            weaponsSummaryPt = listOf(OfficialNpcWeaponSpec("Rifle de Assalto", 5))
        ),
        StreetratPregenSpec(
            id = "24_7",
            handle = "24/7",
            role = RedRole.MEDIA,
            roleSpecialDetailPt = "Credibilidade 4 (Rede de rumores, fontes e impacto público na Cidade)",
            bioPt = "Jornalista investigativo implacável com audição amplificada sempre pronto para gravar furos.",
            stats = CharacterStats(7, 5, 5, 4, 8, 7, 6, 7, 5, 8),
            currentHumanity = 70,
            maxHumanity = 80,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.MEDIA).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Cyberaudio Suite + Amplified Hearing", "Ciberáudio", 10, "+2 em Percepção auditiva"),
                PregenCyberwareEntry("Tatuagem Luminosa (Light Tattoo)", "Fashionware", 0, "Estilo urbano")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Pistola Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 3, isEquipped = true, notesPt = "3d6 ROF 2 ocultável"),
                PregenGearEntry("Câmera de Vídeo & Gravador de Áudio", InventoryItemType.GEAR, PriceCategory.PREMIUM, 100, 1, notesPt = "Kit de reportagem")
            ),
            weaponsSummaryPt = listOf(OfficialNpcWeaponSpec("Pistola Pesada", 3))
        ),
        StreetratPregenSpec(
            id = "suri",
            handle = "Suri \"Cavalry\" Navarro",
            role = RedRole.LAWMAN,
            roleSpecialDetailPt = "Reforço (Backup) 4: chama até 4 Beat Cops armados em 1d6 rodadas",
            bioPt = "Agente tática de linha de frente equipada com Escudo Balístico, Rifle e coldre oculto.",
            stats = CharacterStats(7, 6, 5, 5, 7, 8, 5, 6, 7, 4),
            currentHumanity = 30,
            maxHumanity = 40,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.LAWMAN).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Coldre Oculto (Hidden Holster)", "Cyberware Externo", 7, "Oculta Pistola Pesada no corpo"),
                PregenCyberwareEntry("Bolso Subdérmico (Subdermal Pocket)", "Cyberware Externo", 3, "Espaço oculto 5×10cm")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Escudo à Prova de Balas", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 10, isEquipped = true, notesPt = "10 PV de cobertura portátil"),
                PregenGearEntry("Rifle de Assalto", InventoryItemType.WEAPON, PriceCategory.EXPENSIVE, 500, 1, damageDice = 5, isEquipped = true, notesPt = "5d6 + Autofire(4)"),
                PregenGearEntry("Pistola Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 3, notesPt = "3d6 ROF 2")
            ),
            weaponsSummaryPt = listOf(
                OfficialNpcWeaponSpec("Rifle de Assalto", 5),
                OfficialNpcWeaponSpec("Pistola Pesada", 3)
            )
        ),
        StreetratPregenSpec(
            id = "chanda",
            handle = "Chanda Mishra",
            role = RedRole.EXEC,
            roleSpecialDetailPt = "Trabalho em Equipe (Teamwork) 4: recruta Motorista OU Netrunner corporativo leal + Habitação Corp.",
            bioPt = "Executiva corporativa pragmática protegida por filtros antitoxina, Biomonitor e Agent interno.",
            stats = CharacterStats(5, 7, 7, 3, 6, 7, 6, 5, 5, 7),
            currentHumanity = 58,
            maxHumanity = 70,
            skills = OfficialSkillCatalog.streetratSkillTemplate(RedRole.EXEC).toMap(),
            cyberware = listOf(
                PregenCyberwareEntry("Cyberaudio Suite + Internal Agent", "Ciberáudio", 10, "Agent corporativo invisível"),
                PregenCyberwareEntry("Toxin Binders", "Cyberware Interno", 2, "+2 Resistir a Tortura/Drogas"),
                PregenCyberwareEntry("Biomonitor", "Fashionware", 0, "Sinais vitais")
            ),
            inventory = listOf(
                PregenGearEntry("Jaqueta Blindada Leve (Light Armorjack)", InventoryItemType.ARMOR, PriceCategory.PREMIUM, 100, 1, stoppingPower = 11, isEquipped = true, notesPt = "Corpo + Cabeça PA 11"),
                PregenGearEntry("Pistola Muito Pesada", InventoryItemType.WEAPON, PriceCategory.PREMIUM, 100, 1, damageDice = 4, isEquipped = true, notesPt = "4d6 ROF 1"),
                PregenGearEntry("Traje Businesswear Corporativo", InventoryItemType.GEAR, PriceCategory.EXPENSIVE, 500, 1, notesPt = "Traje de alto padrão")
            ),
            weaponsSummaryPt = listOf(OfficialNpcWeaponSpec("Pistola Muito Pesada", 4))
        )
    )

    fun findById(id: String): StreetratPregenSpec? =
        allPregens.firstOrNull { it.id.equals(id, ignoreCase = true) || it.handle.equals(id, ignoreCase = true) }
}
