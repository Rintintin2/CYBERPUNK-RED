package com.example.core.rules

import com.example.data.local.entity.InventoryItemType

data class OfficialWeaponAttachmentSpec(
    val namePt: String,
    val nameEn: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val slotsRequired: Int,
    val eligiblePt: String,
    val effectPt: String,
    val preventsConcealment: Boolean
)

data class OfficialClipChartEntry(
    val weaponTypePt: String,
    val standardShots: Int,
    val extendedShots: Int,
    val drumShots: Int
)

data class OfficialSpecialAmmoSpec(
    val namePt: String,
    val nameEn: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val ablateAmount: Int,
    val dealsStandardDamage: Boolean,
    val canCrit: Boolean,
    val nonLethalMinOneHp: Boolean,
    val effectPt: String
)

data class NetDefenseCostTier(
    val labelPt: String,
    val dvOrCost: Int,
    val priceCategory: PriceCategory
)

enum class NetDefenseCategory(val labelPt: String) {
    ACTIVE("Defesa Ativa (Drone)"),
    EMPLACED("Defesa Embutida"),
    ENVIRONMENTAL("Defesa Ambiental")
}

data class OfficialNetDefenseSpec(
    val namePt: String,
    val category: NetDefenseCategory,
    val hp: Int?,
    val move: Int?,
    val combatNumber: Int?,
    val counterDv: Int,
    val counterTimeMin: Int,
    val triggerPt: String,
    val effectPt: String
)

/**
 * Canonical Part 2 Gear Extensions (Core Rulebook pp. 342–355, 372–375):
 * Weapon Attachments, Clip Chart, 13 Ammunition Types, Additional Master Gear,
 * NET Architecture Pricing, and 18 Active / Emplaced / Environmental Defenses.
 */
object OfficialWeaponsAndDefensesCatalog {

    val weaponAttachments: List<OfficialWeaponAttachmentSpec> = listOf(
        OfficialWeaponAttachmentSpec(
            namePt = "Baioneta",
            nameEn = "Bayonet",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            slotsRequired = 1,
            eligiblePt = "Armas de Armas Longas (não exóticas)",
            effectPt = "Também funciona como Arma Branca Leve (1d6, ROF 2). Impede ocultar arma.",
            preventsConcealment = true
        ),
        OfficialWeaponAttachmentSpec(
            namePt = "Carregador Estendido",
            nameEn = "Extended Magazine",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            slotsRequired = 1,
            eligiblePt = "Armas à distância não exóticas (exceto Arco/Besta)",
            effectPt = "Aumenta capacidade para coluna Estendido na Tabela de Pentes. Impede ocultar.",
            preventsConcealment = true
        ),
        OfficialWeaponAttachmentSpec(
            namePt = "Carregador Tambor (Drum)",
            nameEn = "Drum Magazine",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            slotsRequired = 1,
            eligiblePt = "Armas à distância não exóticas (exceto Arco/Besta)",
            effectPt = "Aumenta capacidade para coluna Tambor na Tabela de Pentes. Impede ocultar.",
            preventsConcealment = true
        ),
        OfficialWeaponAttachmentSpec(
            namePt = "Lança-Granadas Acoplado",
            nameEn = "Grenade Launcher Underbarrel",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            slotsRequired = 2,
            eligiblePt = "Armas de Armas Longas (não exóticas)",
            effectPt = "Com 2 mãos, dispara como Lança-Granadas (1 granada no pente). Impede ocultar.",
            preventsConcealment = true
        ),
        OfficialWeaponAttachmentSpec(
            namePt = "Espingarda Acoplada",
            nameEn = "Shotgun Underbarrel",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            slotsRequired = 2,
            eligiblePt = "Armas de Armas Longas (não exóticas)",
            effectPt = "Com 2 mãos, dispara como Espingarda (2 cartuchos no pente). Impede ocultar.",
            preventsConcealment = true
        ),
        OfficialWeaponAttachmentSpec(
            namePt = "Mira Infravermelha Noturna",
            nameEn = "Infrared Nightvision Scope",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            slotsRequired = 1,
            eligiblePt = "Todas armas à distância não exóticas",
            effectPt = "Zera penalidades por escuridão, fumaça ou neblina.",
            preventsConcealment = false
        ),
        OfficialWeaponAttachmentSpec(
            namePt = "Smartgun Link",
            nameEn = "Smartgun Link",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            slotsRequired = 2,
            eligiblePt = "Todas armas à distância não exóticas",
            effectPt = "+1 no Teste de Ataque quando conectado via Interface Plugs ou Subdermal Grip (Neural Link).",
            preventsConcealment = false
        ),
        OfficialWeaponAttachmentSpec(
            namePt = "Mira Telescópica (Sniping Scope)",
            nameEn = "Sniping Scope",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            slotsRequired = 1,
            eligiblePt = "Todas armas à distância não exóticas",
            effectPt = "Visão detalhada até 800m; +1 em Tiro Único ou Mirado a 51m+. Não acumula com TeleOptics.",
            preventsConcealment = false
        )
    )

    val clipChart: List<OfficialClipChartEntry> = listOf(
        OfficialClipChartEntry("Pistola Média", 12, 18, 36),
        OfficialClipChartEntry("Pistola Pesada", 8, 14, 28),
        OfficialClipChartEntry("Pistola Muito Pesada", 8, 14, 28),
        OfficialClipChartEntry("SMG", 30, 40, 50),
        OfficialClipChartEntry("SMG Pesada", 40, 50, 60),
        OfficialClipChartEntry("Espingarda", 4, 8, 16),
        OfficialClipChartEntry("Rifle de Assalto", 25, 35, 45),
        OfficialClipChartEntry("Rifle de Precisão", 4, 8, 12),
        OfficialClipChartEntry("Lança-Granadas", 2, 4, 6),
        OfficialClipChartEntry("Lança-Foguetes", 1, 2, 3)
    )

    val specialAmmunition: List<OfficialSpecialAmmoSpec> = listOf(
        OfficialSpecialAmmoSpec(
            namePt = "Munição Básica (10 unid.)",
            nameEn = "Basic Ammunition",
            costEb = 10,
            priceCategory = PriceCategory.CHEAP,
            ablateAmount = 1,
            dealsStandardDamage = true,
            canCrit = true,
            nonLethalMinOneHp = false,
            effectPt = "Munição padrão (10 unid. ou 1 Granada/Foguete). Ablata 1 PA ao penetrar."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Perfurante (Armor-Piercing)",
            nameEn = "Armor-Piercing Ammunition",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            ablateAmount = 2,
            dealsStandardDamage = true,
            canCrit = true,
            nonLethalMinOneHp = false,
            effectPt = "Ablata armadura em 2 pontos (em vez de 1) sempre que ablatar PA."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição de Biotoxina",
            nameEn = "Biotoxin Ammunition",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            ablateAmount = 0,
            dealsStandardDamage = false,
            canCrit = false,
            nonLethalMinOneHp = false,
            effectPt = "Sem dano direto do projétil. Alvo vivo faz Resistir Tortura/Drogas DV15 ou sofre 3d6 direto nos PV."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição PEM (EMP)",
            nameEn = "EMP Ammunition",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            ablateAmount = 0,
            dealsStandardDamage = false,
            canCrit = false,
            nonLethalMinOneHp = false,
            effectPt = "Sem dano físico. Alvo faz Cibertecnologia DV15 ou perde 2 cyberwares/eletrônicos por 1 minuto."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Expansiva",
            nameEn = "Expansive Ammunition",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            ablateAmount = 1,
            dealsStandardDamage = true,
            canCrit = true,
            nonLethalMinOneHp = false,
            effectPt = "Ao causar Ferimento Crítico 'Objeto Estranho', a vítima rola um Ferimento Crítico adicional."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Flashbang",
            nameEn = "Flashbang Ammunition",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            ablateAmount = 0,
            dealsStandardDamage = false,
            canCrit = false,
            nonLethalMinOneHp = false,
            effectPt = "Sem dano de PV. Alvo na área faz Resistir Tortura/Drogas DV15 ou sofre Olho e Ouvido Danificados por 1 min (sem +5 dano)."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Incendiária",
            nameEn = "Incendiary Ammunition",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            ablateAmount = 1,
            dealsStandardDamage = true,
            canCrit = true,
            nonLethalMinOneHp = false,
            effectPt = "Ao causar dano através da armadura, incendeia o alvo (2 de dano direto no fim de cada turno até apagar com Ação)."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Envenenada (Poison)",
            nameEn = "Poison Ammunition",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            ablateAmount = 0,
            dealsStandardDamage = false,
            canCrit = false,
            nonLethalMinOneHp = false,
            effectPt = "Sem dano do projétil. Alvo vivo faz Resistir Tortura/Drogas DV13 ou sofre 2d6 direto nos PV."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição de Borracha (Rubber)",
            nameEn = "Rubber Ammunition",
            costEb = 10,
            priceCategory = PriceCategory.CHEAP,
            ablateAmount = 0,
            dealsStandardDamage = true,
            canCrit = false,
            nonLethalMinOneHp = true,
            effectPt = "Não causa Crítico e não ablata PA. Se reduzir alvo com >1 PV abaixo de 1, deixa-o com 1 PV."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Sonífera (Sleep)",
            nameEn = "Sleep Ammunition",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            ablateAmount = 0,
            dealsStandardDamage = false,
            canCrit = false,
            nonLethalMinOneHp = false,
            effectPt = "Sem dano de PV. Alvo vivo faz Resistir Tortura/Drogas DV13 ou cai Prostrado e Inconsciente por 1 min."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Inteligente (Smart)",
            nameEn = "Smart Ammunition",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            ablateAmount = 1,
            dealsStandardDamage = true,
            canCrit = true,
            nonLethalMinOneHp = false,
            effectPt = "Exige Mira de Alvo (Targeting Scope). Se errar Tiro Único por ≤4, rerrola 1d10 + 10 + SOR contra a DV."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição de Fumaça (Smoke)",
            nameEn = "Smoke Ammunition",
            costEb = 50,
            priceCategory = PriceCategory.COSTLY,
            ablateAmount = 0,
            dealsStandardDamage = false,
            canCrit = false,
            nonLethalMinOneHp = false,
            effectPt = "Sem dano de PV. Obscurece área de 10m × 10m (-4 em testes através da fumaça) por 1 minuto."
        ),
        OfficialSpecialAmmoSpec(
            namePt = "Munição Lacrimogênea (Teargas)",
            nameEn = "Teargas Ammunition",
            costEb = 50,
            priceCategory = PriceCategory.COSTLY,
            ablateAmount = 0,
            dealsStandardDamage = false,
            canCrit = false,
            nonLethalMinOneHp = false,
            effectPt = "Sem dano de PV. Alvo com olhos naturais faz Resistir Tortura/Drogas DV13 ou sofre Olho Danificado por 1 min (sem +5 dano)."
        )
    )

    val masterGearAdditions: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Criobomba (Cryopump)", "Cryopump",
            InventoryItemType.GEAR, "Médico", PriceCategory.LUXURY, 5000,
            notesPt = "Maleta médica de estase (1 carga, refil 50eb). Mantém ferido mortalmente estável; exige Medtech."
        ),
        OfficialGearItem(
            "Criotanque (Cryotank)", "Cryotank",
            InventoryItemType.GEAR, "Médico", PriceCategory.LUXURY, 5000,
            notesPt = "Tanque de estase (1 pessoa). Mantém inconsciente e estabilizado enquanto ligado; exige Medtech."
        ),
        OfficialGearItem(
            "Cyberdeck (Baixa Qualidade • 5 Slots)", "Cyberdeck (Poor Quality)",
            InventoryItemType.CYBERDECK, "Netrunning", PriceCategory.PREMIUM, 100,
            notesPt = "Cyberdeck econômico com 5 slots de Programa/Hardware."
        ),
        OfficialGearItem(
            "Cyberdeck (Qualidade Excelente • 9 Slots)", "Cyberdeck (Excellent Quality)",
            InventoryItemType.CYBERDECK, "Netrunning", PriceCategory.VERY_EXPENSIVE, 1000,
            notesPt = "Cyberdeck avançado com 9 slots de Programa/Hardware."
        ),
        OfficialGearItem(
            "Armação Linear ∑ (Sigma)", "Linear Frame Sigma",
            InventoryItemType.GEAR, "Exoesqueleto", PriceCategory.VERY_EXPENSIVE, 1000,
            notesPt = "Exoesqueleto externo: eleva COR para 12 enquanto conectado via Interface Plugs (não aumenta PV)."
        ),
        OfficialGearItem(
            "Armação Linear ß (Beta)", "Linear Frame Beta",
            InventoryItemType.GEAR, "Exoesqueleto", PriceCategory.LUXURY, 5000,
            notesPt = "Exoesqueleto pesado: eleva COR para 14 enquanto conectado via 2x Interface Plugs."
        ),
        OfficialGearItem(
            "Pá ou Machado", "Shovel or Axe",
            InventoryItemType.GEAR, "Sobrevivência", PriceCategory.COSTLY, 50,
            notesPt = "Ferramenta resistente para escavação ou arrombamento em campo."
        )
    )

    val netFloorPricingTiers: List<Triple<String, String, Int>> = listOf(
        Triple("3 a 6 andares", "Portátil • Máx 2 Nós de Controle", 1000),
        Triple("7 a 12 andares", "Fixa • Máx 3 Nós de Controle", 5000),
        Triple("13 a 18 andares", "Fixa • Sem limite de Nós", 10000)
    )

    val netNodeDvPricing: List<NetDefenseCostTier> = listOf(
        NetDefenseCostTier("Senha / Arquivo / Nó Controle DV6", 500, PriceCategory.EXPENSIVE),
        NetDefenseCostTier("Senha / Arquivo / Nó Controle DV8", 1000, PriceCategory.VERY_EXPENSIVE),
        NetDefenseCostTier("Senha / Arquivo / Nó Controle DV10", 5000, PriceCategory.LUXURY),
        NetDefenseCostTier("Senha / Arquivo / Nó Controle DV12", 10000, PriceCategory.SUPER_LUXURY)
    )

    val netDefenseDvPricing: List<NetDefenseCostTier> = listOf(
        NetDefenseCostTier("Contramedida Eletrônica/Segurança DV9", 500, PriceCategory.EXPENSIVE),
        NetDefenseCostTier("Contramedida Eletrônica/Segurança DV13", 1000, PriceCategory.VERY_EXPENSIVE),
        NetDefenseCostTier("Contramedida Eletrônica/Segurança DV17", 5000, PriceCategory.LUXURY),
        NetDefenseCostTier("Contramedida Eletrônica/Segurança DV21", 10000, PriceCategory.SUPER_LUXURY)
    )

    val netDefenses: List<OfficialNetDefenseSpec> = listOf(
        // Active Defenses (Drones)
        OfficialNetDefenseSpec("Air Swarm Drone Cloud", NetDefenseCategory.ACTIVE, 15, 8, 14, 17, 5, "Entra na área sem passe", "Enxame micro-drones; ataca como Arma Branca Muito Pesada."),
        OfficialNetDefenseSpec("Ground Drone", NetDefenseCategory.ACTIVE, 30, 4, 14, 21, 5, "Entra na área sem passe", "2 opções: Pistola Muito Pesada (8 AP), SMG (30 Básicas) ou Câmera."),
        OfficialNetDefenseSpec("Large Air Drone", NetDefenseCategory.ACTIVE, 20, 6, 14, 21, 5, "Entra na área sem passe", "2 opções: Pistola de Dardos (8 Veneno), Pistola Muito Pesada (8 AP) ou Câmera."),
        OfficialNetDefenseSpec("Mini Air Drone", NetDefenseCategory.ACTIVE, 15, 6, 14, 17, 5, "Entra na área sem passe", "1 opção: Pistola de Dardos (8 Veneno), Pistola Muito Pesada (8 AP) ou Câmera."),
        OfficialNetDefenseSpec("Spider Walking Drone", NetDefenseCategory.ACTIVE, 40, 4, 14, 21, 5, "Entra na área sem passe", "2 opções: Lança-Granadas (2 Gás), Arma Branca Muito Pesada, SMG Pesada (40) ou Câmera."),
        // Emplaced Defenses
        OfficialNetDefenseSpec("Automated Blood Swarm", NetDefenseCategory.EMPLACED, null, null, 14, 21, 5, "Entra na sala sem passe", "Névoa de nanites (bloqueada por filtros de gás): DV15 Resistir Tortura/Drogas ou 3d6 direto nos PV (ignora PA)."),
        OfficialNetDefenseSpec("Automated Melee Weapon", NetDefenseCategory.EMPLACED, 25, null, 14, 17, 5, "Entra na sala sem passe", "Corte monofilamento automático (Arma Branca Muito Pesada)."),
        OfficialNetDefenseSpec("Automated Turret", NetDefenseCategory.EMPLACED, 25, null, 14, 17, 5, "Entra na sala sem passe", "Torreta de teto: Rifle Assalto (25), Lança-Chamas (4), Dardos (8 Veneno), Pistola Muito Pesada (8 AP) ou SMG Pesada (40)."),
        // Environmental Defenses
        OfficialNetDefenseSpec("Observation Cameras", NetDefenseCategory.ENVIRONMENTAL, 5, null, null, 9, 1, "Entra na sala (Percepção DV17)", "Visão Baixa Luz, IV e UV para Demônio/segurança."),
        OfficialNetDefenseSpec("Tanglefoot Flooring", NetDefenseCategory.ENVIRONMENTAL, 20, null, null, 13, 1, "Pisa no carpete (Percepção DV17)", "Reduz MOV em 1d6 até destruir (20 PV) ou sair da área."),
        OfficialNetDefenseSpec("Electrical Flooring", NetDefenseCategory.ENVIRONMENTAL, 20, null, null, 13, 1, "Pisa na grade (Percepção DV17)", "6d6 de dano corporal por turno (reduzido por PA, sem ablatar)."),
        OfficialNetDefenseSpec("Laser Grid", NetDefenseCategory.ENVIRONMENTAL, null, null, null, 17, 5, "Entra ou move 2m (Percepção DV17)", "Dano de Arma Branca Muito Pesada ao tocar; Contorcionismo DV17 atravessa."),
        OfficialNetDefenseSpec("Tip-floor (Alçapão)", NetDefenseCategory.ENVIRONMENTAL, null, null, null, 13, 1, "Pisa no alçapão (Percepção DV17)", "Atletismo DV15 ou cai em fosso com nanofios/espinhos (6d6 corporal)."),
        OfficialNetDefenseSpec("Goop (Espuma Adesiva)", NetDefenseCategory.ENVIRONMENTAL, 10, null, null, 13, 1, "Entra na zona (Percepção DV17)", "Reduz MOV em 2d6 até destruir (10 PV) ou escapar."),
        OfficialNetDefenseSpec("Ceiling/Wall Punchers", NetDefenseCategory.ENVIRONMENTAL, 20, null, null, 13, 5, "Pisa na grade (Percepção DV17)", "Pistões de aço causam 6d6 corporal (reduzido por PA)."),
        OfficialNetDefenseSpec("Slip-floor", NetDefenseCategory.ENVIRONMENTAL, 10, null, null, 13, 1, "Move na área (Percepção DV17)", "Ação de Movimento exige Atletismo DV15 ou cai Prostrado."),
        OfficialNetDefenseSpec("Stun Panels", NetDefenseCategory.ENVIRONMENTAL, 5, null, null, 13, 1, "A 2m do painel (Percepção DV17)", "Área 10×10m: Resistir Tortura/Drogas DV15 ou Olho e Ouvido Danificados por 1 min."),
        OfficialNetDefenseSpec("Sleep Gas Elevator", NetDefenseCategory.ENVIRONMENTAL, 60, null, null, 17, 5, "Entra no elevador (Percepção DV17)", "Sela portas; DV13 Resistir Tortura/Drogas por turno ou fica Inconsciente.")
    )
}
