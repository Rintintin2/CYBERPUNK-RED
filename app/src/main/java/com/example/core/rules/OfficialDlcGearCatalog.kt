package com.example.core.rules

import com.example.data.local.entity.InventoryItemType

data class OfficialCyberdeckSpec(
    val name: String,
    val manufacturerPt: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val slotsSummaryPt: String,
    val specialRulePt: String
)

data class OfficialCyberdeckHardwareSpec(
    val name: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val slots: Int,
    val effectPt: String
)

data class OfficialDroneSpec(
    val name: String,
    val categoryPt: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val move: Int,
    val sp: Int,
    val hp: Int,
    val counterDv: Int,
    val loadoutPt: String
)

/**
 * Interface RED Vol. 1–3 Official Equipment Catalogs:
 * - Vol. 1: Cyberchairs (2), Active/Personal Drones (6), ELO Kit
 * - Vol. 2: 12 Days of Gunmas (12 weapons), Weather Gear (5), Containers & Home Gear (8), Emerald City Drug
 * - Vol. 3: Woodchipper's Garage (12 weapons), Midnight with the Upload (15 Cyberdecks + 11 Hardware),
 *           Bicycles + 14 Upgrades + Skates/Skateboards, ELO TCG + 3 Merch Weapons, CO2 Grenade & LF Omega
 */
object OfficialDlcGearCatalog {

    val gunmasWeapons: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Arasaka WAA Bullpup", "Arasaka WAA Bullpup",
            InventoryItemType.WEAPON, "Gunmas (Rifle Exótico)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 5, rof = 1, magazine = 30, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Longas / Tiro Automático",
            notesPt = "5d6, Autofire (4), Smartgun Link embutido; aceita munição não-básica."
        ),
        OfficialGearItem(
            "Constitutional Arms Multi-Ammo Pistol", "Constitutional Arms Multi-Ammo Pistol",
            InventoryItemType.WEAPON, "Gunmas (Pistola Exótica)", PriceCategory.EXPENSIVE, 500,
            damageDice = 4, rof = 1, magazine = 5, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Curtas",
            notesPt = "Pistola Muito Pesada 4d6, pente de 5 tambores misturando munições VHP."
        ),
        OfficialGearItem(
            "IMI Chainknife", "IMI Chainknife",
            InventoryItemType.WEAPON, "Gunmas (Branca Exótica)", PriceCategory.EXPENSIVE, 500,
            damageDice = 2, rof = 2, concealable = true,
            linkedSkillPt = "Armas Brancas",
            notesPt = "Qualidade Excelente (+1 Atk). Desligada: 2d6 ROF 2. Acionada (Ação): Arma Branca Muito Pesada 4d6."
        ),
        OfficialGearItem(
            "Kendachi Dragon Flamethrower", "Kendachi Dragon Flamethrower",
            InventoryItemType.WEAPON, "Gunmas (Pesada Exótica)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 3, rof = 1, magazine = 2, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Lança-chamas de 1 mão (2 cartuchos de espingarda); incendeia alvos (4 dano/turno)."
        ),
        OfficialGearItem(
            "Magnum Opus Hellbringer", "Magnum Opus Hellbringer",
            InventoryItemType.WEAPON, "Gunmas (Pistola Exótica)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 5, rof = 1, magazine = 3, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Curtas",
            notesPt = "5d6, 3 tiros. Trava se usuário tiver COR < 10 (além de exigir Ação p/ destravar)."
        ),
        OfficialGearItem(
            "Malorian Arms Sub-Flechette Gun", "Malorian Arms Sub-Flechette Gun",
            InventoryItemType.WEAPON, "Gunmas (SMG Exótica)", PriceCategory.LUXURY, 5000,
            damageDice = 3, rof = 1, magazine = 25, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Curtas / Tiro Automático",
            notesPt = "SMG Pesada Exc. Qualidade (+1 Atk), Autofire (4), Smartgun Link; ablata PA em 4 pontos!"
        ),
        OfficialGearItem(
            "Militech Crusher", "Militech Crusher",
            InventoryItemType.WEAPON, "Gunmas (Pistola Exótica)", PriceCategory.EXPENSIVE, 500,
            damageDice = 3, rof = 1, magazine = 6, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Curtas",
            notesPt = "Pistola Muito Pesada que dispara exclusivamente cartuchos básicos de Espingarda (6 tiros)."
        ),
        OfficialGearItem(
            "Mustang Arms ARS-5 SMG", "Mustang Arms ARS-5 Submachine Gun",
            InventoryItemType.WEAPON, "Gunmas (SMG Exótica)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 3, rof = 1, magazine = 40, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Curtas / Tiro Automático",
            notesPt = "SMG Pesada com Smartgun Link, Mira IV Noturna e Mira Telescópica integrados."
        ),
        OfficialGearItem(
            "Nomad Pneumatic Bolt Gun", "Nomad Pneumatic Bolt Gun",
            InventoryItemType.WEAPON, "Gunmas (Rifle Exótico)", PriceCategory.EXPENSIVE, 500,
            damageDice = 4, rof = 1, magazine = 8, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Longas",
            notesPt = "Usa tabela de alcance de Sniper; dispara 8 flechas (aceita munição não-básica)."
        ),
        OfficialGearItem(
            "Nova Model 757 Cityhunter", "Nova Model 757 Cityhunter",
            InventoryItemType.WEAPON, "Gunmas (Pistola Exótica)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 3, rof = 2, magazine = 18, handsRequired = 1, concealable = true,
            linkedSkillPt = "Armas Curtas",
            notesPt = "Pistola Pesada ocultável (18 trounds, ROF 2), Smartgun Link integrado; aceita munição Smart."
        ),
        OfficialGearItem(
            "Stolbovoy ST-5 Assault Rifle", "Stolbovoy ST-5 Assault Rifle",
            InventoryItemType.WEAPON, "Gunmas (Rifle Exótico)", PriceCategory.PREMIUM, 100,
            damageDice = 5, rof = 1, magazine = 20, handsRequired = 2, concealable = false,
            linkedSkillPt = "Armas Longas / Tiro Automático",
            notesPt = "Baixa Qualidade (5d6, 20 tiros, Autofire 4); 50% de chance de disparar travado e destravar junto."
        ),
        OfficialGearItem(
            "Teen Dreem", "Teen Dreem",
            InventoryItemType.WEAPON, "Gunmas (SMG Descartável)", PriceCategory.EVERYDAY, 20,
            damageDice = 2, rof = 1, magazine = 10, handsRequired = 1, concealable = true,
            linkedSkillPt = "Armas Curtas / Tiro Automático",
            notesPt = "SMG Baixa Qualidade (2d6, 10 tiros). Autofire(3) ou Fogo Supressivo esvazia pente e derrete o cano."
        )
    )

    val woodchipperWeapons: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Biotechnica Enviro-launcher", "Biotechnica Enviro-launcher",
            InventoryItemType.WEAPON, "Woodchipper (Pesada)", PriceCategory.EXPENSIVE, 500,
            damageDice = 8, rof = 1, magazine = 1, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Lança-Foguetes biodegradável (1 tiro); dissolve em 1 min após o disparo sem deixar rastros."
        ),
        OfficialGearItem(
            "BudgetArms Triple Threat", "BudgetArms Triple Threat",
            InventoryItemType.WEAPON, "Woodchipper (Pesada)", PriceCategory.EXPENSIVE, 500,
            damageDice = 8, rof = 1, magazine = 2, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Baixa Qualidade: 2 foguetes (8d6) + Lança-Granadas acoplado PQ de 1 granada."
        ),
        OfficialGearItem(
            "Pistola Sinalizadora (Flare Gun)", "Flare Gun",
            InventoryItemType.WEAPON, "Woodchipper (Pesada)", PriceCategory.PREMIUM, 100,
            damageDice = 6, rof = 1, magazine = 1, handsRequired = 1, concealable = false,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Lança-Granadas de 1 mão (1 granada ou sinalizador Road Flare incendário)."
        ),
        OfficialGearItem(
            "Midnight Arms SDF-45", "Midnight Arms SDF-45",
            InventoryItemType.WEAPON, "Woodchipper (Pesada)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 8, rof = 1, magazine = 4, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Dispara 2 foguetes (8d6) simultâneos em trajetória parabólica DV15 para evadir."
        ),
        OfficialGearItem(
            "Militech Aegis", "Militech Aegis",
            InventoryItemType.WEAPON, "Woodchipper (Menos Letal)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 4, rof = 1, magazine = 8, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Baixa Qualidade; disparo concussivo de bateria (8 cargas) com alcance de cartucho, deixa alvo c/ min 1 PV e inconsciente."
        ),
        OfficialGearItem(
            "Militech Archimedes", "Militech Archimedes",
            InventoryItemType.WEAPON, "Woodchipper (Smart Pesada)", PriceCategory.LUXURY, 5000,
            damageDice = 8, rof = 1, magazine = 1, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Qualidade Excelente (+1 Atk); foguete inteligente contra alvos aéreos/veículos (requer Targeting Scope)."
        ),
        OfficialGearItem(
            "Nomad Air Cannon", "Nomad Air Cannon",
            InventoryItemType.WEAPON, "Woodchipper (Pesada)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 0, rof = 1, magazine = 3, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Canhão pneumático de líquidos (alcance cartucho): ácido (-1 PA por alvo) ou 3 frascos de Veneno/Biotoxina."
        ),
        OfficialGearItem(
            "Pursuit Security Inc. TearJerker", "Pursuit Security Inc. TearJerker",
            InventoryItemType.WEAPON, "Woodchipper (Controle)", PriceCategory.EXPENSIVE, 500,
            damageDice = 0, rof = 1, magazine = 3, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Qualidade Excelente (+1 Atk); Lança-Granadas de 3 tiros exclusivo para Fumaça ou Gás Lacrimogêneo."
        ),
        OfficialGearItem(
            "SlamDance Ballistic Harpoon", "SlamDance Ballistic Harpoon",
            InventoryItemType.WEAPON, "Woodchipper (Híbrida)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 4, rof = 1, magazine = 1, handsRequired = 2,
            linkedSkillPt = "Armas Brancas / Armas Pesadas",
            notesPt = "Arma Branca Muito Pesada 2 mãos (4d6) OU disparada como arpão (tabela Arco/Besta, ignora ½ PA)."
        ),
        OfficialGearItem(
            "Sternmeyer M-02 Heavy Rifle", "Sternmeyer M-02 Heavy Rifle",
            InventoryItemType.WEAPON, "Woodchipper (Pesada)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 5, rof = 1, magazine = 80, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Metralhadora pesada 5d6 com tambor de 80 trounds AP (500eb). Só disparo único."
        ),
        OfficialGearItem(
            "Towa Pocket Launcher", "Towa Pocket Launcher",
            InventoryItemType.WEAPON, "Woodchipper (Compacta)", PriceCategory.EXPENSIVE, 500,
            damageDice = 8, rof = 1, magazine = 1, handsRequired = 2, concealable = true,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Baixa Qualidade; Lança-Foguetes dobrável (ocultável enquanto descarregado)."
        ),
        OfficialGearItem(
            "UrbanTech Burst Flamethrower", "UrbanTech Burst Flamethrower",
            InventoryItemType.WEAPON, "Woodchipper (Pesada)", PriceCategory.VERY_EXPENSIVE, 1000,
            damageDice = 3, rof = 1, magazine = 4, handsRequired = 2,
            linkedSkillPt = "Armas Pesadas",
            notesPt = "Lança-Chamas 3d6 (4 dano fogo) ou rajada total estilo granada incendiária (incendeia o usuário também)."
        )
    )

    val dronesAndCyberchairs: List<OfficialDroneSpec> = listOf(
        OfficialDroneSpec("GRAF3 (Ativo)", "Drone Ativo (NET)", 5000, PriceCategory.LUXURY, 2, 11, 60, 17, "Arma Branca Muito Pesada + Câmera (alcance 5m)"),
        OfficialDroneSpec("Savannah Eagle", "Drone Aéreo Ativo (NET)", 15000, PriceCategory.SUPER_LUXURY, 6, 7, 30, 21, "Shrieker + SMG (30) + Câmera + Camuflagem DV17 Percepção"),
        OfficialDroneSpec("Savannah Panther", "Drone Terrestre Ativo (NET)", 15000, PriceCategory.SUPER_LUXURY, 8, 11, 40, 21, "Stun Baton + Branca Pesada + Lança-Granadas (Gás/Inc.) + Câmera"),
        OfficialDroneSpec("My First GRAF3", "Drone Pessoal (Agent)", 500, PriceCategory.EXPENSIVE, 1, 0, 5, 9, "Sem armas; controle via Agent a 6m (bateria 3h)"),
        OfficialDroneSpec("The Observer", "Drone Pessoal (Agent)", 1000, PriceCategory.VERY_EXPENSIVE, 6, 0, 15, 17, "Câmera 1h em Memory Chip via Agent (bateria 48h)"),
        OfficialDroneSpec("The Transporter", "Drone Pessoal (CitiNet)", 5000, PriceCategory.LUXURY, 6, 0, 20, 21, "Câmera + braços p/ 13,5kg (30lbs), conexão CitiNet (48h)")
    )

    val dlcCyberdecks: List<OfficialCyberdeckSpec> = listOf(
        OfficialCyberdeckSpec("Kirama Advanced", "Kirama", 500, PriceCategory.EXPENSIVE, "5 slots universais", "Efeito padrão confiável e compacto."),
        OfficialCyberdeckSpec("Kirama Entry", "Kirama", 100, PriceCategory.PREMIUM, "5 slots (só Programas)", "Não aceita Hardware."),
        OfficialCyberdeckSpec("Kirama Training", "Kirama", 20, PriceCategory.EVERYDAY, "5 slots (só Programas)", "Baixa qualidade; não instala atacantes anti-pessoal."),
        OfficialCyberdeckSpec("Microtech Assault", "Microtech", 500, PriceCategory.EXPENSIVE, "4 Prog + 5 Hardware", "Focado em sobrevivência e hardware tático."),
        OfficialCyberdeckSpec("Microtech Scout", "Microtech", 500, PriceCategory.EXPENSIVE, "5 slots universais", "+1 Pathfinder enquanto conectado."),
        OfficialCyberdeckSpec("Microtech Warrior", "Microtech", 1000, PriceCategory.VERY_EXPENSIVE, "7 slots (só Programas)", "Sem slots de Hardware; otimizado p/ combate."),
        OfficialCyberdeckSpec("Raven Microcyb Hummingbird", "Raven", 1000, PriceCategory.VERY_EXPENSIVE, "2 slots Hardware", "Concede +1 Ação NET por turno (sem slots de Programa)!"),
        OfficialCyberdeckSpec("Raven Microcyb Kestrel 2", "Raven", 1000, PriceCategory.VERY_EXPENSIVE, "7 slots (só Programas)", "Permite ativar/rezzar todos os Speed Boosters numa única Ação."),
        OfficialCyberdeckSpec("Raven Microcyb Phoenix", "Raven", 1000, PriceCategory.VERY_EXPENSIVE, "6 slots universais", "Reduz dano de fogo de Hellhound/Hellbolt."),
        OfficialCyberdeckSpec("SGI Kerberos", "SGI", 1000, PriceCategory.VERY_EXPENSIVE, "6 Hellhound + 5 Hardware", "Exclusivo para hospedar programas/rotinas defensivas Hellhound."),
        OfficialCyberdeckSpec("SGI Verdant Knight", "SGI", 500, PriceCategory.EXPENSIVE, "9 slots (só Sword/Shield)", "Comporta até 9 cópias exclusivas de Sword e Shield."),
        OfficialCyberdeckSpec("SGI Warlock's Book", "SGI", 500, PriceCategory.EXPENSIVE, "9 slots universais", "Não instala Programas Atacantes nem Black ICE."),
        OfficialCyberdeckSpec("Zetatech Kaliya", "Zetatech", 500, PriceCategory.EXPENSIVE, "3 Flak + 6 universais", "Vem com 3 slots dedicados para Defesa Flak."),
        OfficialCyberdeckSpec("Zetatech MicroMate", "Zetatech", 500, PriceCategory.EXPENSIVE, "9 slots universais", "Dobra o dano cerebral sofrido pelo Netrunner!"),
        OfficialCyberdeckSpec("Zetatech Parraline 6000", "Zetatech", 500, PriceCategory.EXPENSIVE, "3 Prog + 6 Hardware", "Ideal para montar estações com muito Hardware.")
    )

    val dlcCyberdeckHardware: List<OfficialCyberdeckHardwareSpec> = listOf(
        OfficialCyberdeckHardwareSpec("Aerie", 500, PriceCategory.EXPENSIVE, 1, "Reduz para 0 o dano ao ciberespaço ao escapar de Black ICE em movimento."),
        OfficialCyberdeckHardwareSpec("Bushido Accelerator", 500, PriceCategory.EXPENSIVE, 2, "Requer 2 Ações de Carne p/ acoplar; +1 em todas rolagens de Ataque de Programa."),
        OfficialCyberdeckHardwareSpec("Combat Recorder", 500, PriceCategory.EXPENSIVE, 1, "Grava telemetria de Netrun diretamente em Memory Chip."),
        OfficialCyberdeckHardwareSpec("Defense Sequencer", 500, PriceCategory.EXPENSIVE, 2, "Permite rodar automaticamente uma cópia extra de Defender em sequência."),
        OfficialCyberdeckHardwareSpec("Feline Instinct", 500, PriceCategory.EXPENSIVE, 1, "+2 em testes de Slide enquanto conectado."),
        OfficialCyberdeckHardwareSpec("Hangry Hangry Dragon", 500, PriceCategory.EXPENSIVE, 2, "Potencializa ataque contra Black ICE em +1d6 REZ (consome carga/calor)."),
        OfficialCyberdeckHardwareSpec("Perfume Shoppe", 500, PriceCategory.EXPENSIVE, 1, "Mascara assinatura do Netrunner (+2 Cloak no acesso)."),
        OfficialCyberdeckHardwareSpec("Smithy", 500, PriceCategory.EXPENSIVE, 1, "Recupera REZ de programas desativados fora de combate em 1 min."),
        OfficialCyberdeckHardwareSpec("Snaketrap", 500, PriceCategory.EXPENSIVE, 1, "Retarda o rastreamento automático de Black ICE que ataca Programas."),
        OfficialCyberdeckHardwareSpec("Swamp Mist", 500, PriceCategory.EXPENSIVE, 1, "Cria névoa de dados; dificulta detecção do caminho pelo Demon."),
        OfficialCyberdeckHardwareSpec("Swifty Clean", 500, PriceCategory.EXPENSIVE, 1, "Limpa todos os arquivos temporários e rastros ao executar Jack Out.")
    )

    val bicycleUpgrades: List<Pair<String, Pair<Int, String>>> = listOf(
        "Dínamo de Garrafa (Bottle Dynamo)" to (20 to "Carrega 1 bateria de dispositivo pequeno após 1h de pedal."),
        "Vidro à Prova de Balas (Bulletproof Glass)" to (50 to "Com Enclosure, oferece cobertura (PV 15)."),
        "Blindagem de Ciclo (Cycle Armor)" to (100 to "Concede PA 7 à bicicleta."),
        "Assistência Elétrica de Pedal" to (100 to "Evita teste de Fadiga nas primeiras 4h (bateria 8h)."),
        "Cabine Fechada (Enclosure)" to (100 to "Protege o ciclista de chuva e clima."),
        "Assento Estendido (Extended Seat)" to (20 to "+1 passageiro (sem pedalar)."),
        "Quadro Dobrável (Folding Frame)" to (100 to "Dobra a bicicleta p/ carregar em mochila grande."),
        "Suporte de Arma (Gun Mount)" to (100 to "Monta 1 arma no guidão p/ disparo rápido."),
        "Iluminação Neon (Neon Lighting)" to (20 to "Estilo +1 noturno ou brilho personalizável."),
        "Quadro Reforçado (Reinforced Frame)" to (100 to "+5 SDP na bicicleta (total 20 SDP)."),
        "Sistema de Segurança (Security System)" to (500 to "Alarme + trava eletrônica DV17 Eletrônica/Segurança."),
        "Compartimento Oculto" to (50 to "Esconde arma pequena/itens; DV17 Ocultar/Revelar."),
        "Quadro de Manobras (Stunt Frame)" to (100 to "+2 em testes de Atletismo para truques e saltos."),
        "Reboque (Trailer)" to (50 to "Transporta carga adicional (reduz velocidade narrativa à metade).")
    )

    val emeraldCityDrug = OfficialStreetDrug(
        name = "Emerald City",
        costEb = 100,
        priceCategory = PriceCategory.PREMIUM,
        durationHours = 8,
        primaryEffectPt = "Ignora penalidade de Ferimento Grave por 8h; provoca alucinações surreais temáticas.",
        secondaryDv = 15,
        secondaryEffectPt = "Vicia; enquanto sem efeito primário, sofre -2 em Concentração e Percepção por alucinações residuais."
    )

    val miscellaneousDlcGear: List<OfficialGearItem> = listOf(
        // Vol 1 Cyberchairs & Drones
        OfficialGearItem("Ciber-Cadeira Mercurius", "Mercurius Cyberchair", InventoryItemType.GEAR, "Mobilidade (Vol 1)", PriceCategory.PREMIUM, 100, notesPt = "MOV 5 (-penalidades de armadura); ignora escadas, imune a EMP e absorve Críticos de MOV."),
        OfficialGearItem("Ciber-Cadeira Spider", "Spider Cyberchair", InventoryItemType.GEAR, "Mobilidade (Vol 1)", PriceCategory.VERY_EXPENSIVE, 1000, notesPt = "MOV 5, 4 slots p/ opções de Cyberarm/Cyberleg/Cyberlimb (contam pareadas); imune a EMP."),
        // Vol 1/3 Elflines Online & TCG
        OfficialGearItem("Elflines Online (Pacote + Headset RUSH)", "Elflines Online RUSH Kit", InventoryItemType.GEAR, "Entretenimento (Vol 1)", PriceCategory.EXPENSIVE, 500, notesPt = "RUSH Virtuality Headset (500eb) + Cópia ELO (50eb) + Assinatura mensal (20eb)."),
        OfficialGearItem("ELO TCG: Booster Pack", "ELO TCG Booster Pack", InventoryItemType.GEAR, "ELO TCG (Vol 3)", PriceCategory.CHEAP, 10, notesPt = "Pacote colecionável (10eb). Caixa fechada: 100eb. Cartas avulsas Mixaria: 5eb."),
        OfficialGearItem("Duel Caster (TCG)", "Duel Caster", InventoryItemType.WEAPON, "ELO TCG (Vol 3)", PriceCategory.PREMIUM, 100, damageDice = 1, rof = 2, magazine = 26, handsRequired = 1, linkedSkillPt = "Armas Curtas / Briga", notesPt = "Lança cartas do deck: 1d6 ROF 2 (ou ROF 3 a ≤6m), 26 cargas."),
        OfficialGearItem("Scarlet Blackbow (Merch)", "Scarlet Blackbow", InventoryItemType.WEAPON, "ELO TCG (Vol 3)", PriceCategory.COSTLY, 50, damageDice = 4, rof = 1, handsRequired = 2, linkedSkillPt = "Arquearia", notesPt = "Arco Baixa Qualidade temático de ELO (4d6 flechas)."),
        OfficialGearItem("Sword of Arvish (Merch)", "Sword of Arvish", InventoryItemType.WEAPON, "ELO TCG (Vol 3)", PriceCategory.COSTLY, 50, damageDice = 3, rof = 2, handsRequired = 1, linkedSkillPt = "Armas Brancas", notesPt = "Arma Branca Pesada Baixa Qualidade temática de ELO (3d6)."),
        // Vol 2 Weather Gear
        OfficialGearItem("Forro Térmico Frio (Jacket Lining)", "Cold-Weather Jacket Lining", InventoryItemType.GEAR, "Clima (Vol 2)", PriceCategory.EXPENSIVE, 500, notesPt = "Evita dano/fadiga em Ondas de Frio extremo."),
        OfficialGearItem("Forro Térmico Calor (Jacket Lining)", "Hot-Weather Jacket Lining", InventoryItemType.GEAR, "Clima (Vol 2)", PriceCategory.EXPENSIVE, 500, notesPt = "Evita fadiga em Ondas de Calor extremo."),
        OfficialGearItem("Forro Impermeável (Waterproof Lining)", "Waterproof Jacket Lining", InventoryItemType.GEAR, "Clima (Vol 2)", PriceCategory.EXPENSIVE, 500, notesPt = "Protege usuário e eletrônicos de Chuva Ácida/Tempestades."),
        OfficialGearItem("Guarda-Chuva Comum", "Umbrella", InventoryItemType.GEAR, "Clima (Vol 2)", PriceCategory.CHEAP, 10, notesPt = "Protege de chuva comum e chuva ácida leve (5 PV)."),
        OfficialGearItem("Militech Tactical Umbrella", "Militech Tactical Umbrella", InventoryItemType.WEAPON, "Clima / Arma (Vol 2)", PriceCategory.VERY_EXPENSIVE, 1000, damageDice = 3, rof = 2, magazine = 2, handsRequired = 1, linkedSkillPt = "Armas Brancas / Curtas", notesPt = "Arma Branca Pesada Exc. Qualidade (3d6) + Pistola Pesada PQ (3d6, 2 tiros) + escudo chuva."),
        // Vol 2 Home & Containers
        OfficialGearItem("Koff Popper + 10 Koff Pops", "Koff Popper", InventoryItemType.GEAR, "Casa (Vol 2)", PriceCategory.COSTLY, 50, notesPt = "Cafeteira compacta de container (50eb; recarga 10 cápsulas por 10eb)."),
        OfficialGearItem("Cofre Antichamas (Fire Safe)", "Fire Safe", InventoryItemType.GEAR, "Casa (Vol 2)", PriceCategory.PREMIUM, 100, notesPt = "Cofre resistente a incêndios e arrombamento (DV17)."),
        OfficialGearItem("Compartimento Oculto Residencial", "Hidden Compartment", InventoryItemType.GEAR, "Casa (Vol 2)", PriceCategory.PREMIUM, 100, notesPt = "Fundo falso p/ container ou cubo (DV17 Percepção)."),
        OfficialGearItem("Espelho Smart Vanity", "Smart Vanity", InventoryItemType.GEAR, "Casa (Vol 2)", PriceCategory.EXPENSIVE, 500, notesPt = "+1 em Aparência Pessoal e Estilo ao se arrumar."),
        OfficialGearItem("Arte de Parede (Wall Art)", "Wall Art", InventoryItemType.GEAR, "Casa (Vol 2)", PriceCategory.EVERYDAY, 20, notesPt = "Pôster luminoso/holográfico p/ container ou cubo."),
        // Vol 3 Wheels & Going Metal Gear
        OfficialGearItem("Bicicleta (Spinning Your Wheels)", "Bicycle", InventoryItemType.GEAR, "Veículo / Rodas (Vol 3)", PriceCategory.PREMIUM, 100, stoppingPower = 0, notesPt = "15 SDP, 1 lugar; perícia Atletismo. Velocidade por COR: <4 (MOV 8), 4-7 (MOV 10), 8+ (MOV 15)."),
        OfficialGearItem("Patins Inline (Inline Skates)", "Inline Skates", InventoryItemType.GEAR, "Rodas (Vol 3)", PriceCategory.COSTLY, 50, notesPt = "+4m ao usar Ação Correr em terreno plano; requer Atletismo para manobras."),
        OfficialGearItem("Skate (Skateboard)", "Skateboard", InventoryItemType.GEAR, "Rodas (Vol 3)", PriceCategory.COSTLY, 50, notesPt = "+4m ao usar Ação Correr em plano/descida."),
        OfficialGearItem("Granada de CO₂", "CO2 Grenade", InventoryItemType.AMMO, "Going Metal (Vol 3)", PriceCategory.COSTLY, 50, notesPt = "Apaga incêndios em área de 10×10m e dispersa gases leves."),
        OfficialGearItem("Armação Linear Ω (Omega Externa)", "External Linear Frame Omega", InventoryItemType.GEAR, "Going Metal (Vol 3)", PriceCategory.SUPER_LUXURY, 10000, notesPt = "Exoesqueleto massivo: eleva COR para 16 enquanto conectado via 2x Interface Plugs.")
    )

    val allDlcPurchaseGear: List<OfficialGearItem> =
        gunmasWeapons +
            woodchipperWeapons +
            miscellaneousDlcGear +
            dronesAndCyberchairs.map { d ->
                OfficialGearItem(
                    namePt = d.name,
                    nameEn = d.name,
                    itemType = InventoryItemType.GEAR,
                    subCategoryPt = d.categoryPt,
                    priceCategory = d.priceCategory,
                    costEb = d.costEb,
                    stoppingPower = d.sp,
                    notesPt = "MOV ${d.move} | PV ${d.hp} | PA ${d.sp} | Contra-medida DV${d.counterDv} — ${d.loadoutPt}"
                )
            } +
            dlcCyberdecks.map { deck ->
                OfficialGearItem(
                    namePt = "Deck ${deck.name}",
                    nameEn = deck.name,
                    itemType = InventoryItemType.CYBERDECK,
                    subCategoryPt = "Netrunning (Vol 3)",
                    priceCategory = deck.priceCategory,
                    costEb = deck.costEb,
                    notesPt = "${deck.slotsSummaryPt} • ${deck.specialRulePt}"
                )
            } +
            dlcCyberdeckHardware.map { hw ->
                OfficialGearItem(
                    namePt = "Hardware: ${hw.name}",
                    nameEn = hw.name,
                    itemType = InventoryItemType.GEAR,
                    subCategoryPt = "Hardware Deck (Vol 3)",
                    priceCategory = hw.priceCategory,
                    costEb = hw.costEb,
                    notesPt = "${hw.slots} slot(s) • ${hw.effectPt}"
                )
            }
}
