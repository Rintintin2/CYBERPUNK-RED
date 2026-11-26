package com.example.core.rules

/**
 * Official Black Chrome Cyberware (15 new items + 3 Internal Linear Frames),
 * 16 Dynalar Cyberfingers, and Black Chrome Economy / Keywords reference.
 */
object OfficialBlackChromeCyberwareCatalog {

    val blackChromeCyberware: List<OfficialCyberwareSpec> = listOf(
        OfficialCyberwareSpec("Blindagem EMP Borgware", "Borgware Hardened Shielding", CyberwareCategory.BORGWARE, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 0, false, "Protege 1 peça de Borgware contra EMP, Microwaver e Programas [F1]."),
        OfficialCyberwareSpec("Soquete Econômico (Budget)", "Budget Chipware Socket", CyberwareCategory.NEURALWARE, "Clinic", 100, PriceCategory.PREMIUM, 7, "2d6", 1, false, "Requer Neural Link; dano na cabeça ejeta o chip a 30m (1d10=1 destrói) [C3]."),
        OfficialCyberwareSpec("Cyberaudio Desconto", "Discount Cyberaudio Suite", CyberwareCategory.CYBERAUDIO, "Clinic", 100, PriceCategory.PREMIUM, 3, "1d6", 0, true, "Peça base aparente (1 slot de opção) [C3]."),
        OfficialCyberwareSpec("Cibermão Modular 8 Dedos (Dynalar)", "Dynalar Modular Finger Enthusiast Cyberhand", CyberwareCategory.BORGWARE, "Hospital", 500, PriceCategory.EXPENSIVE, 14, "4d6", 2, true, "2 slots Cyberarm; comporta 8 Cyberfingers (já inclui 8 padrão) [A5]."),
        OfficialCyberwareSpec("Estimulador de Memória (Chip)", "Explicit Memory Stimulator", CyberwareCategory.NEURALWARE, "N/A", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Chipware: +2 em Concentração para recordar informações [C3]."),
        OfficialCyberwareSpec("Flashbulb (Ciberbraço)", "Flashbulb", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 2, false, "2 slots Cyberarm; Ação cega alvo ≤25m (Resistir DV15 ou Olho Danificado 1 min) [A5/C3]."),
        OfficialCyberwareSpec("Carcaça Blindada Ciberolho", "Hardened Cybereye Casing", CyberwareCategory.CYBEROPTICS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, false, "Protege o Ciberolho e suas opções contra EMP/Microwaver [F1]."),
        OfficialCyberwareSpec("Blindagem EMP Corpo Interno", "Internal Body Cyberware Hardened Shielding", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 1, false, "Protege todo Cyberware Interno contra EMP e Microwaver [F1]."),
        OfficialCyberwareSpec("Cibermão Modular (5 Dedos)", "Modular Finger Cyberhand", CyberwareCategory.CYBERLIMBS, "Hospital", 100, PriceCategory.PREMIUM, 3, "1d6", 1, true, "Instalável até em braço orgânico; comporta 5 Cyberfingers [A5/C3]."),
        OfficialCyberwareSpec("Ciberbraço Neo-Soviético", "Neo-Soviet Cyberarm", CyberwareCategory.CYBERLIMBS, "Hospital", 100, PriceCategory.PREMIUM, 7, "2d6", 0, true, "3 slots; exige COR 8+ ou -1 em testes. Opções ficam visíveis [B6/C3]."),
        OfficialCyberwareSpec("Lança-Redes Popup", "Popup Net Launcher", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 3, false, "3 slots Cyberarm; dispara rede até 25m (15 PV, -2 ações, impede MOV) [B6]."),
        OfficialCyberwareSpec("Espingarda Popup", "Popup Shotgun", CyberwareCategory.CYBERLIMBS, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", 3, false, "3 slots Cyberarm; 2 tiros ocultáveis sem teste (aceita só Smartgun Link) [B6/F1]."),
        OfficialCyberwareSpec("RacerBracer (Coluna)", "RacerBracer", CyberwareCategory.INTERNAL, "Hospital", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, false, "Imunidade ao Ferimento Crítico Chicote (Whiplash) [B6]."),
        OfficialCyberwareSpec("Ciberolho Patrocinado", "Sponsored Cybereye", CyberwareCategory.CYBEROPTICS, "Clinic", 50, PriceCategory.COSTLY, 7, "2d6", 0, true, "2 slots + Chyron pré-instalado; exibe anúncios de hora em hora (-1 visão até fechar) [C3/E3]."),
        OfficialCyberwareSpec("Nanomatriz Resposta Trauma", "Trauma Response Nanomatrix", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", 1, false, "1x/dia (Ação) restaura Skinweave ou Armadura Subdérmica a PA total [F1]."),
        // 3 Internal Linear Frames (Fūma, SWAT, Vermilion)
        OfficialCyberwareSpec("Linear Frame Fūma Kotarō (Interno)", "Fūma Kotarō Linear Frame (Internal)", CyberwareCategory.BORGWARE, "Hospital", 5000, PriceCategory.LUXURY, 14, "4d6", 0, true, "Requer COR 8 + 2× Enxerto: COR 12, +2 Furtividade + lâminas EQ ocultas [D2]."),
        OfficialCyberwareSpec("Linear Frame LF-001 SWAT (Interno)", "LF-001 SWAT Linear Frame (Internal)", CyberwareCategory.BORGWARE, "Hospital", 5000, PriceCategory.LUXURY, 14, "4d6", 0, true, "Requer COR 8 + 2× Enxerto: COR 12, sem penalidade p/ escalar e nadar [B5]."),
        OfficialCyberwareSpec("Linear Frame Vermilion (Interno)", "Vermilion Linear Frame (Internal)", CyberwareCategory.BORGWARE, "Hospital", 5000, PriceCategory.LUXURY, 14, "4d6", 0, true, "Requer COR 8 + 2× Enxerto: COR 12, sem penalidade salto + Skate Feet [D2].")
    )

    val blackChromeCyberfingers: List<OfficialCyberwareSpec> = listOf(
        OfficialCyberwareSpec("Cyberfinger: Airhypo", "Airhypo Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Aplicador Airhypo oculto no dedo modular [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Caneta (Ballpoint)", "Ballpoint Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", 1, false, "Caneta esferográfica oculta no dedo [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Porta-Munição", "Bullet Storage Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Oculta 2 projéteis no dedo sem precisar de teste [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Pistola Dardos", "Dartgun Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, false, "Dartgun de 1 tiro no dedo; oculto automaticamente c/ luva [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Lanterna", "Flashlight Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Dedo lanterna portátil [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Rastreador (Tracer)", "Homing Tracer Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, false, "Rastreia unha vinculada (50eb) até 1 milha [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Ponteiro Laser", "Laser Pointer Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Com 4+ dedos (DV13) causa Olho Danificado 1 min; 8+ até tratar [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Isqueiro", "Lighter Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Isqueiro retrátil na ponta do dedo [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Gazua (Lockpick)", "Lockpick Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (baixo)", 1, false, "Ferramenta integrada para perícia Arrombamento [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Microfone", "Microphone Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (baixo)", 1, false, "Microfone vinculado ao Agent oculto no dedo [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Mini Suprimento Ar", "Mini Air Supply Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 500, PriceCategory.EXPENSIVE, 2, "1d6/2 (baixo)", 1, false, "Fornece 10 minutos de oxigênio (recarga 1h) [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Tiro Único (One Shot)", "One Shot Special Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 7, "2d6", 1, false, "Heavy Pistol de 1 tiro (aceita não-básica) oculta no dedo [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Spray Tinta", "Spray Paint Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Mini spray de grafite na ponta do dedo [A1]."),
        OfficialCyberwareSpec("Cyberfinger: Esguicho (Squirt)", "Squirt Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, false, "Espalha líquidos; se carregar ácido corrói o dedo [A1/E3]."),
        OfficialCyberwareSpec("Cyberfinger: Padrão (Standard)", "Standard Cyberfinger", CyberwareCategory.CYBERLIMBS, "Mall", 20, PriceCategory.EVERYDAY, 0, "0 (N/A)", 1, false, "Dedo modular repositor sem funções extras [A1/C3]."),
        OfficialCyberwareSpec("Cyberfinger: Tesoura / Alicate (Par)", "Wirecutter/Scissor Cyberfingers", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 2, false, "Corta fios ou atua como Arma Branca Leve PQ (1d6) [A1].")
    )

    val allBlackChromeCyberware: List<OfficialCyberwareSpec> =
        blackChromeCyberware + blackChromeCyberfingers

    val sixNightMarketsPt: List<Pair<String, String>> = listOf(
        "A • 3-Piece's Joint (Little Europe)" to "Fixer: 3-Piece (Op 5/Mídia 2). Mensal. Especialidade: Tech, decks, programas, Cyberfingers. Segura por drones Zhirafa e gás sonífero.",
        "B • Woodchipper's Block Party (Santo Domingo)" to "Fixer: Woodchipper (Op 5/Moto 4). Bimestral. Armas, veículos Nômades, Linear Frames e milho de rua. Protegido pelos Aldecaldo Lobos.",
        "C • Molly Anderson's Market (Old Combat Zone)" to "Fixer: Molly Anderson (Op 6). Mensal. Cyberware reciclado, armas e armaduras repintadas em garagem subterrânea fortificada.",
        "D • Mister K's Midnight Market (The Glen / Atlantis)" to "Fixer: Mister Kernighan (Op 9). Trimestral (100 convites). Leilão Super Luxo 10.000eb+. Segurança pesada Danger Gal.",
        "E • Ms. Mynah's Night Market (Rancho Coronado / RC)" to "Fixer: Ms. Mynah (Op 6). Todo dia 5. Alta costura sob medida e looks reciclados dentro do antigo shopping Minimallism.",
        "F • Rex Royale's Casino (One Block)" to "Fixer: Rex Royale (Op 8). Mensal (52 cartas de convite). Traje social obrigatório; vende armas e armaduras luxuosas entre mesas de pôquer."
    )

    val economyAndFixerDvsPt: List<String> = listOf(
        "Manha (Streetwise) p/ achar Fixer/Tech: 500eb DV15 | 1.000eb DV17 | 5.000eb DV21 | 10.000eb+ DV24.",
        "Nomad Access (Nova Regra): ao subir Moto, o Nômade escolhe veículo com Nomad Access ≤ Rank de Moto (inclui todos upgrades listados).",
        "Moda Disfarçada: Armaduras 'With Appearance of Fashion' exigem Estilo & Vestuário DV13 ou Percepção DV15 p/ notar.",
        "Detalhes de Estilo de Vida: Kibble (100eb: 1 táxi/mês, 20eb lazer) | Prepak Genérico (300eb: táxi/lazer semanal) | Bom Prepak (600eb: táxi diário + aluguel carro 1x/mês) | Comida Fresca (1.500eb: carro semanal + lazer 500eb/mês)."
    )
}
