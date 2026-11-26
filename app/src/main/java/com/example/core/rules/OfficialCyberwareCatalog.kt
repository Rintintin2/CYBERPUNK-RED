package com.example.core.rules

enum class CyberwareCategory(val labelPt: String, val maxSlotsInfo: String) {
    FASHIONWARE("Fashionware", "7 slots (sem peça base)"),
    NEURALWARE("Neuralware", "Requer Neural Link (5 slots)"),
    CYBEROPTICS("Ciberópticos", "Requer Cybereye (3 slots/olho)"),
    CYBERAUDIO("Ciberáudio", "Requer Cyberaudio Suite (3 slots)"),
    INTERNAL("Cyberware Interno", "7 slots (sem peça base)"),
    EXTERNAL("Cyberware Externo", "7 slots (sem peça base)"),
    CYBERLIMBS("Cibermembros", "Braço 4 slots / Perna 3 slots"),
    BORGWARE("Borgware", "Reduz Humanidade Máx em 4 (com HL > 0)")
}

data class OfficialCyberwareSpec(
    val namePt: String,
    val nameEn: String,
    val category: CyberwareCategory,
    val installLocation: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val presetHl: Int,
    val hlDiceFormula: String,
    val optionSlotsUsed: Int = 1,
    val isFoundational: Boolean = false,
    val notesPt: String
)

/**
 * Complete canonical Cyberware Tables from Cyberpunk RED Core Rulebook Part 1 (pp. 111–116).
 */
object OfficialCyberwareCatalog {
    val allCyberware: List<OfficialCyberwareSpec> = listOf(
        // Fashionware (p. 111)
        OfficialCyberwareSpec("Biomonitor", "Biomonitor", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Exibe sinais vitais; pode conectar ao Agent"),
        OfficialCyberwareSpec("Chemskin", "Chemskin", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Altera tom de pele; +2 Aparência Pessoal com Techhair"),
        OfficialCyberwareSpec("EMP Threading", "EMP Threading", CyberwareCategory.FASHIONWARE, "Mall", 10, PriceCategory.CHEAP, 0, "0 (N/A)", notesPt = "Linhas prateadas em padrão de circuito pelo corpo"),
        OfficialCyberwareSpec("Tatuagem Luminosa (Light Tattoo)", "Light Tattoo", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "+2 Estilo & Vestuário com 3+ tatuagens"),
        OfficialCyberwareSpec("Shift Tacts", "Shift Tacts", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Lentes subdérmicas oculares que mudam de cor"),
        OfficialCyberwareSpec("Skinwatch", "Skinwatch", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Relógio LED implantado sob a pele"),
        OfficialCyberwareSpec("Techhair", "Techhair", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Cabelo artificial luminoso; +2 Aparência Pessoal c/ Chemskin"),

        // Neuralware (p. 112)
        OfficialCyberwareSpec("Neural Link", "Neural Link", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 0, true, "Peça base obrigatória p/ Neuralware e Subdermal Grip (5 slots)"),
        OfficialCyberwareSpec("Gravador de Braindance", "Braindance Recorder", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, notesPt = "Requer Neural Link. Grava experiências em Memory Chip"),
        OfficialCyberwareSpec("Soquete de Chipware", "Chipware Socket", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, notesPt = "Requer Neural Link. Permite inserir 1 Chipware"),
        OfficialCyberwareSpec("Interface Plugs", "Interface Plugs", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, notesPt = "Requer Neural Link. Conecta a Cyberdecks, veículos e armas Smart"),
        OfficialCyberwareSpec("Kerenzikov", "Kerenzikov", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 14, "4d6", 1, notesPt = "Speedware (máx 1). +2 permanente em Iniciativa. Requer Neural Link"),
        OfficialCyberwareSpec("Sandevistan", "Sandevistan", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, notesPt = "Speedware (máx 1). Ação: +3 Iniciativa por 1 min (recarga 1h). Requer Neural Link"),
        OfficialCyberwareSpec("Analisador Químico (Chip)", "Chemical Analyzer", CyberwareCategory.NEURALWARE, "N/A", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Chipware (requer Chipware Socket). Analisa composição química"),
        OfficialCyberwareSpec("Memory Chip", "Memory Chip", CyberwareCategory.NEURALWARE, "N/A", 10, PriceCategory.CHEAP, 0, "0 (N/A)", 0, notesPt = "Armazena dados p/ cyberware"),
        OfficialCyberwareSpec("Olfactory Boost (Chip)", "Olfactory Boost", CyberwareCategory.NEURALWARE, "N/A", 100, PriceCategory.PREMIUM, 7, "2d6", 1, notesPt = "Chipware. Permite usar Rastreamento pelo olfato"),
        OfficialCyberwareSpec("Editor de Dor (Pain Editor)", "Pain Editor", CyberwareCategory.NEURALWARE, "N/A", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 1, notesPt = "Chipware. Ignora penalidades de Ferimento Grave"),
        OfficialCyberwareSpec("Skill Chip (Nível 3)", "Skill Chip", CyberwareCategory.NEURALWARE, "N/A", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, notesPt = "Chipware (500eb ou 1000eb se x2). Concede perícia no Nv 3"),
        OfficialCyberwareSpec("Tactile Boost (Chip)", "Tactile Boost", CyberwareCategory.NEURALWARE, "N/A", 100, PriceCategory.PREMIUM, 7, "2d6", 1, notesPt = "Chipware. Detecta movimento em superfícies a até 20m"),

        // Cyberoptics (pp. 112–113)
        OfficialCyberwareSpec("Cybereye (Ciberolho)", "Cybereye", CyberwareCategory.CYBEROPTICS, "Clinic", 100, PriceCategory.PREMIUM, 7, "2d6", 0, true, "Peça base. Olho artificial com 3 Option Slots"),
        OfficialCyberwareSpec("Anti-Dazzle (Par)", "Anti-Dazzle", CyberwareCategory.CYBEROPTICS, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Imune a clarões de luz. Deve ser pareado em 2 Ciberolhos"),
        OfficialCyberwareSpec("Chyron", "Chyron", CyberwareCategory.CYBEROPTICS, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Subtela HUD na visão. Requer Cybereye"),
        OfficialCyberwareSpec("Color Shift", "Color Shift", CyberwareCategory.CYBEROPTICS, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Muda cor e padrão da íris livremente. Requer Cybereye"),
        OfficialCyberwareSpec("Dartgun Ocular", "Dartgun", CyberwareCategory.CYBEROPTICS, "Clinic", 500, PriceCategory.EXPENSIVE, 2, "1d6/2 (cima)", 3, notesPt = "Arma Exótica oculta de 1 tiro no olho (3 slots)"),
        OfficialCyberwareSpec("Image Enhance (Par)", "Image Enhance", CyberwareCategory.CYBEROPTICS, "Mall", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "+2 Percepção, Leitura Labial e Ocultar/Revelar Objeto (pareado)"),
        OfficialCyberwareSpec("Low Light / Infrared / UV (Par)", "Low Light / Infrared / UV", CyberwareCategory.CYBEROPTICS, "Mall", 500, PriceCategory.EXPENSIVE, 3, "1d6", 2, notesPt = "Ignora escuridão, fumaça e neblina (pareado, 2 slots/olho)"),
        OfficialCyberwareSpec("MicroOptics", "MicroOptics", CyberwareCategory.CYBEROPTICS, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Zoom microscópico 400x. Requer Cybereye"),
        OfficialCyberwareSpec("MicroVideo", "MicroVideo", CyberwareCategory.CYBEROPTICS, "Clinic", 500, PriceCategory.EXPENSIVE, 2, "1d6/2 (cima)", 2, notesPt = "Câmera ocular que grava áudio e vídeo (2 slots)"),
        OfficialCyberwareSpec("Detector de Radiação", "Radiation Detector", CyberwareCategory.CYBEROPTICS, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 3, "1d6", 1, notesPt = "Exibe radiação num raio de 100m em brilho azul"),
        OfficialCyberwareSpec("Targeting Scope", "Targeting Scope", CyberwareCategory.CYBEROPTICS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "+1 em Tiros Mirados (Aimed Shot). Requer Cybereye"),
        OfficialCyberwareSpec("TeleOptics", "TeleOptics", CyberwareCategory.CYBEROPTICS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Enxerga detalhes até 800m; +1 em Tiros Mirados ≥ 51m"),
        OfficialCyberwareSpec("Virtuality (Par)", "Virtuality", CyberwareCategory.CYBEROPTICS, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Projeta ciberespaço na visão (dispensa óculos). Pareado"),

        // Cyberaudio (p. 113)
        OfficialCyberwareSpec("Cyberaudio Suite", "Cyberaudio Suite", CyberwareCategory.CYBERAUDIO, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 0, true, "Peça base (máx 1 por corpo). Possui 3 Option Slots"),
        OfficialCyberwareSpec("Amplified Hearing", "Amplified Hearing", CyberwareCategory.CYBERAUDIO, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "+2 em Percepção auditiva"),
        OfficialCyberwareSpec("Audio Recorder", "Audio Recorder", CyberwareCategory.CYBERAUDIO, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Grava áudio em Memory Chip ou Agent"),
        OfficialCyberwareSpec("Bug Detector (Implante)", "Bug Detector", CyberwareCategory.CYBERAUDIO, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Detecta escutas num raio de 2m"),
        OfficialCyberwareSpec("Homing Tracer (Implante)", "Homing Tracer", CyberwareCategory.CYBERAUDIO, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Segue rastreador vinculado a até 1 milha"),
        OfficialCyberwareSpec("Internal Agent", "Internal Agent", CyberwareCategory.CYBERAUDIO, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "Agent completo interno; exibe tela se tiver Chyron"),
        OfficialCyberwareSpec("Level Damper", "Level Damper", CyberwareCategory.CYBERAUDIO, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Imune a efeitos de barulhos altos"),
        OfficialCyberwareSpec("Radio Communicator", "Radio Communicator", CyberwareCategory.CYBERAUDIO, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Comunicação por rádio até 1 milha"),
        OfficialCyberwareSpec("Radio Scanner / Music Player", "Radio Scanner / Music Player", CyberwareCategory.CYBERAUDIO, "Clinic", 50, PriceCategory.COSTLY, 2, "1d6/2 (cima)", 1, notesPt = "Rádio/player interno alcance 1 milha"),
        OfficialCyberwareSpec("Radar Detector (Implante)", "Radar Detector", CyberwareCategory.CYBERAUDIO, "Clinic", 500, PriceCategory.EXPENSIVE, 2, "1d6/2 (cima)", 1, notesPt = "Detecta feixes de radar num raio de 100m"),
        OfficialCyberwareSpec("Scrambler / Descrambler", "Scrambler / Descrambler", CyberwareCategory.CYBERAUDIO, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Criptografa e descriptografa comunicações"),
        OfficialCyberwareSpec("Voice Stress Analyzer", "Voice Stress Analyzer", CyberwareCategory.CYBERAUDIO, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "+2 em Percepção Humana e Interrogatório"),

        // Internal Cyberware (p. 114)
        OfficialCyberwareSpec("AudioVox", "AudioVox", CyberwareCategory.INTERNAL, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", notesPt = "Sintetizador vocal: +2 Atuação e Tocar Instrumento (canto)"),
        OfficialCyberwareSpec("Implante Contraceptivo", "Contraceptive Implant", CyberwareCategory.INTERNAL, "Mall", 10, PriceCategory.CHEAP, 0, "0 (N/A)", notesPt = "Previne gravidez indesejada"),
        OfficialCyberwareSpec("Anticorpos Aprimorados", "Enhanced Antibodies", CyberwareCategory.INTERNAL, "Mall", 500, PriceCategory.EXPENSIVE, 2, "1d6/2 (cima)", notesPt = "Após estabilização, cura COR × 2 PV por dia de descanso"),
        OfficialCyberwareSpec("Cybersnake", "Cybersnake", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", notesPt = "Arma Branca Muito Pesada oculta no esôfago (4d6)"),
        OfficialCyberwareSpec("Guelras (Gills)", "Gills", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", notesPt = "Permite respirar embaixo d'água"),
        OfficialCyberwareSpec("Músculos e Ossos Enxertados", "Grafted Muscle and Bone Lace", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", notesPt = "+2 em COR (até 10); recalcula PV Máx, Grave e Teste de Morte"),
        OfficialCyberwareSpec("Suprimento de Ar Independente", "Independent Air Supply", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 2, "1d6/2 (cima)", notesPt = "30 min de oxigênio interno (recarrega em 1h)"),
        OfficialCyberwareSpec("Midnight Lady", "Midnight Lady", CyberwareCategory.INTERNAL, "Clinic", 100, PriceCategory.PREMIUM, 7, "2d6", notesPt = "Implante íntimo"),
        OfficialCyberwareSpec("Mr. Studd", "Mr. Studd", CyberwareCategory.INTERNAL, "Clinic", 100, PriceCategory.PREMIUM, 7, "2d6", notesPt = "Implante íntimo"),
        OfficialCyberwareSpec("Filtros Nasais (Nasal Filters)", "Nasal Filters", CyberwareCategory.INTERNAL, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", notesPt = "Imune a gases tóxicos e fumaça"),
        OfficialCyberwareSpec("Implante Radar / Sonar", "Radar / Sonar Implant", CyberwareCategory.INTERNAL, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", notesPt = "Escaneia terreno num raio de 50m (não atravessa cobertura)"),
        OfficialCyberwareSpec("Toxin Binders", "Toxin Binders", CyberwareCategory.INTERNAL, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", notesPt = "+2 em Resistir a Tortura/Drogas"),
        OfficialCyberwareSpec("Vampyres", "Vampyres", CyberwareCategory.INTERNAL, "Clinic", 500, PriceCategory.EXPENSIVE, 14, "4d6", notesPt = "Arma Branca Leve Exc. Qualidade oculta na boca; aceita veneno"),

        // External Cyberware (p. 114)
        OfficialCyberwareSpec("Coldre Oculto (Hidden Holster)", "Hidden Holster", CyberwareCategory.EXTERNAL, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", notesPt = "Armazena arma ocultável dentro do corpo"),
        OfficialCyberwareSpec("Skin Weave (PA 7)", "Skin Weave", CyberwareCategory.EXTERNAL, "Hospital", 500, PriceCategory.EXPENSIVE, 7, "2d6", notesPt = "Corpo e Cabeça com PA 7 (não acumula c/ armadura); regenera 1 PA/dia"),
        OfficialCyberwareSpec("Armadura Subdérmica (PA 11)", "Subdermal Armor", CyberwareCategory.EXTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", notesPt = "Corpo e Cabeça com PA 11 (não acumula); regenera 1 PA/dia"),
        OfficialCyberwareSpec("Bolso Subdérmico", "Subdermal Pocket", CyberwareCategory.EXTERNAL, "Clinic", 100, PriceCategory.PREMIUM, 3, "1d6", notesPt = "Espaço 5×10 cm sob a pele com zíper RealSkinn"),

        // Cyberlimbs (pp. 115–116)
        OfficialCyberwareSpec("Cyberarm (Ciberbraço)", "Cyberarm", CyberwareCategory.CYBERLIMBS, "Hospital", 500, PriceCategory.EXPENSIVE, 7, "2d6", 0, true, "Braço biônico c/ 4 Option Slots + Mão Padrão gratuita"),
        OfficialCyberwareSpec("Standard Hand", "Standard Hand", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Mão cibernética padrão (instalável em braço de carne)"),
        OfficialCyberwareSpec("Big Knucks", "Big Knucks", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "Arma Branca Média ocultável (2d6)"),
        OfficialCyberwareSpec("Cyberdeck no Ciberbraço", "Cyberdeck (Cyberarm)", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 3, notesPt = "Requer Cyberarm (3 slots); +1 slot de Programa/Hardware"),
        OfficialCyberwareSpec("Grapple Hand", "Grapple Hand", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "Dispara mão com cabo até 30m. Requer Cyberarm"),
        OfficialCyberwareSpec("Medscanner (Ciberbraço)", "Medscanner (Cyberarm)", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 2, notesPt = "+2 Primeiros Socorros e Paramédico (2 slots)"),
        OfficialCyberwareSpec("Lança-Granadas Popup", "Popup Grenade Launcher", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 2, notesPt = "Lança-Granadas ocultável de 1 tiro no Cyberarm (2 slots)"),
        OfficialCyberwareSpec("Arma Branca Popup", "Popup Melee Weapon", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 2, notesPt = "Arma Branca Leve/Média/Pesada ocultável no Cyberarm (2 slots)"),
        OfficialCyberwareSpec("Escudo Popup", "Popup Shield", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 3, notesPt = "Escudo à Prova de Balas ocultável no Cyberarm (3 slots)"),
        OfficialCyberwareSpec("Arma à Distância Popup", "Popup Ranged Weapon", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 2, notesPt = "Qualquer arma à distância 1 mão ocultável no Cyberarm (2 slots)"),
        OfficialCyberwareSpec("Quick Change Mount", "Quick Change Mount", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 7, "2d6", 1, notesPt = "Permite acoplar/remover Cyberarm com 1 Ação"),
        OfficialCyberwareSpec("Rippers", "Rippers", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Garras de carbovidro (Arma Branca Média 2d6 ocultável)"),
        OfficialCyberwareSpec("Scratchers", "Scratchers", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Unhas de carbovidro (Arma Branca Leve 1d6 ocultável)"),
        OfficialCyberwareSpec("Shoulder Cam", "Shoulder Cam", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 2, notesPt = "Câmera de vídeo no ombro/Cyberarm (2 slots)"),
        OfficialCyberwareSpec("Slice 'N Dice", "Slice 'N Dice", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Chicote monofilamento no polegar (Arma Branca Média 2d6)"),
        OfficialCyberwareSpec("Subdermal Grip", "Subdermal Grip", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "Requer Neural Link. Usa Smartgun sem Interface Plug"),
        OfficialCyberwareSpec("Techscanner (Ciberbraço)", "Techscanner (Cyberarm)", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 2, notesPt = "+2 em perícias de TEC. Requer Cyberarm (2 slots)"),
        OfficialCyberwareSpec("Tool Hand", "Tool Hand", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "Dedos com ferramentas integradas"),
        OfficialCyberwareSpec("Wolvers", "Wolvers", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, notesPt = "Garras longas nos nós dos dedos (Arma Branca Pesada 3d6 ocultável)"),
        OfficialCyberwareSpec("Cyberleg (Ciberperna)", "Cyberleg", CyberwareCategory.CYBERLIMBS, "Hospital", 100, PriceCategory.PREMIUM, 3, "1d6", 0, true, "Perna biônica c/ 3 Option Slots + Pé Padrão gratuito"),
        OfficialCyberwareSpec("Standard Foot", "Standard Foot", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", 1, notesPt = "Pé cibernético padrão"),
        OfficialCyberwareSpec("Grip Foot (Par)", "Grip Foot", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Anula penalidade de movimento ao escalar (pareado)"),
        OfficialCyberwareSpec("Jump Booster (Par)", "Jump Booster", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 2, notesPt = "Anula penalidade ao saltar (pareado, 2 slots/perna)"),
        OfficialCyberwareSpec("Skate Foot (Par)", "Skate Foot", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Patins ocultáveis: +6m ao usar Ação Correr (pareado)"),
        OfficialCyberwareSpec("Talon Foot", "Talon Foot", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Lâmina no pé (Arma Branca Leve 1d6 ocultável)"),
        OfficialCyberwareSpec("Web Foot (Par)", "Web Foot", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Anula penalidade de movimento ao nadar (pareado)"),
        OfficialCyberwareSpec("Blindagem EMP (Hardened Shielding)", "Hardened Shielding", CyberwareCategory.CYBERLIMBS, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 3, "1d6", 1, notesPt = "Impede desativação do cibermembro por EMP"),
        OfficialCyberwareSpec("Revestimento Plástico / RealSkinn / Superchrome", "Limb Covering", CyberwareCategory.CYBERLIMBS, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", 0, notesPt = "Plástico 100eb | RealSkinn 500eb | Superchrome 1000eb (+2 Estilo)"),

        // Borgware (p. 116)
        OfficialCyberwareSpec("Artificial Shoulder Mount", "Artificial Shoulder Mount", CyberwareCategory.BORGWARE, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 0, true, "Permite instalar 2 Cyberarms extras abaixo dos ombros"),
        OfficialCyberwareSpec("Linear Frame Σ (Sigma)", "Implanted Linear Frame Sigma", CyberwareCategory.BORGWARE, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 0, true, "Requer COR 6 + Grafted Muscle. Aumenta COR p/ 12"),
        OfficialCyberwareSpec("Linear Frame β (Beta)", "Implanted Linear Frame Beta", CyberwareCategory.BORGWARE, "Hospital", 5000, PriceCategory.LUXURY, 14, "4d6", 0, true, "Requer COR 8 + 2x Grafted Muscle. Aumenta COR p/ 14"),
        OfficialCyberwareSpec("MultiOptic Mount", "MultiOptic Mount", CyberwareCategory.BORGWARE, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 0, true, "Permite montar até 5 Ciberolhos adicionais"),
        OfficialCyberwareSpec("Sensor Array", "Sensor Array", CyberwareCategory.BORGWARE, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 0, true, "Requer Cyberaudio Suite. +5 slots adicionais de Ciberáudio")
    ) + OfficialDlcCyberwareAndFbc.dlcCyberware + OfficialBlackChromeCyberwareCatalog.allBlackChromeCyberware
}
