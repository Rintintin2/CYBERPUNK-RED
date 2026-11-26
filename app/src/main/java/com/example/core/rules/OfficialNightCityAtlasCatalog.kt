package com.example.core.rules

enum class AtlasZoneId(val labelPt: String, val shortLabelPt: String) {
    ISLAND("A Ilha (The Island)", "Ilha"),
    NORTHSIDE("Zona Norte (The Northside)", "Norte"),
    MAINLAND("Continente (The Mainland)", "Continente"),
    SOUTHSIDE("Zona Sul (The Southside)", "Sul")
}

data class AtlasLocationSpec(
    val code: String,
    val name: String,
    val notePt: String = ""
)

data class AtlasDistrictSpec(
    val code: String,
    val namePt: String,
    val nameEn: String,
    val zone: AtlasZoneId,
    val normX: Float,
    val normY: Float,
    val securityProvider: String,
    val cityManager: String?,
    val gangsPresent: List<String>,
    val neighborCodes: List<String>,
    val hookDistrictMatch: String?,
    val summaryPt: String,
    val locations: List<AtlasLocationSpec>
)

data class AtlasBridgeSpec(
    val namePt: String,
    val fromCode: String,
    val toCode: String,
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float
)

data class AtlasWaterLandmark(
    val namePt: String,
    val normX: Float,
    val normY: Float
)

data class AtlasBriefGangSpec(
    val name: String,
    val descriptionPt: String
)

/**
 * Complete data extracted from RTG-CPR-DLC-NightCityAtlas v1.01 (15 pp.):
 * - 4 Major Zones, 24 Districts (A–X), 156 Coded Locations (A1–X5)
 * - Water bodies, Bridges, City Managers, Security Providers & 43 Gangs Summary
 */
object OfficialNightCityAtlasCatalog {

    val waterLandmarks: List<AtlasWaterLandmark> = listOf(
        AtlasWaterLandmark("Estero Bay", 0.25f, 0.08f),
        AtlasWaterLandmark("Del Coronado Bay", 0.14f, 0.42f),
        AtlasWaterLandmark("Morro Rock", 0.09f, 0.34f),
        AtlasWaterLandmark("San Morro Bay", 0.14f, 0.72f),
        AtlasWaterLandmark("Morro Canal", 0.48f, 0.47f),
        AtlasWaterLandmark("Laguna Reservoir", 0.90f, 0.72f)
    )

    val bridges: List<AtlasBridgeSpec> = listOf(
        AtlasBridgeSpec("Coronado Bay Bridge", "N", "B", 0.43f, 0.21f, 0.38f, 0.28f),
        AtlasBridgeSpec("San Morro Bridge", "E", "S", 0.46f, 0.42f, 0.56f, 0.42f),
        AtlasBridgeSpec("Pacifica Bridge", "I", "W", 0.43f, 0.76f, 0.50f, 0.82f)
    )

    val districts: List<AtlasDistrictSpec> = listOf(
        // === THE ISLAND (A–L) ===
        AtlasDistrictSpec(
            code = "A",
            namePt = "Pequena Europa (Little Europe)",
            nameEn = "Little Europe",
            zone = AtlasZoneId.ISLAND,
            normX = 0.27f,
            normY = 0.27f,
            securityProvider = "NCPD",
            cityManager = "Amanda Polishchuk",
            gangsPresent = listOf("6th Street", "Eurotrashers", "Maelstrom", "Undertow"),
            neighborCodes = listOf("B", "C", "D"),
            hookDistrictMatch = "Little Europe",
            summaryPt = "Bairro cosmopolita na ponta noroeste da Ilha; abriga a sede da Danger Gal, igrejas reconstruídas e condomínios verticais.",
            locations = listOf(
                AtlasLocationSpec("A1", "Camden Court"),
                AtlasLocationSpec("A2", "Chopper's"),
                AtlasLocationSpec("A3", "Continental Brands Vertical Neighborhood"),
                AtlasLocationSpec("A4", "Cube-A-Rama"),
                AtlasLocationSpec("A5", "Danger Gal Housing Facility"),
                AtlasLocationSpec("A6", "Danger Gal Offices", "Quartel-general da Danger Gal"),
                AtlasLocationSpec("A7", "Fiddler's Green"),
                AtlasLocationSpec("A8", "Greta's"),
                AtlasLocationSpec("A9", "Holy Angels Church"),
                AtlasLocationSpec("A10", "Paradiso Terrestre"),
                AtlasLocationSpec("A11", "Short Circuit"),
                AtlasLocationSpec("A12", "Soprano's"),
                AtlasLocationSpec("A13", "Torrell and Chiang's")
            )
        ),
        AtlasDistrictSpec(
            code = "B",
            namePt = "Marina Superior (Upper Marina)",
            nameEn = "Upper Marina",
            zone = AtlasZoneId.ISLAND,
            normX = 0.41f,
            normY = 0.30f,
            securityProvider = "NCPD",
            cityManager = "Kabria Chung",
            gangsPresent = listOf("Maelstrom", "Prime-Time Players", "Street Queens"),
            neighborCodes = listOf("A", "C", "D", "E", "N"),
            hookDistrictMatch = "Upper Marina",
            summaryPt = "Região portuária e comercial ligada à Zona Norte pela Ponte Coronado Bay; lar do lendário bar The Afterlife e da Ziggurat.",
            locations = listOf(
                AtlasLocationSpec("B1", "The Afterlife", "Bar clássico dos Edgerunners e solos de elite"),
                AtlasLocationSpec("B2", "Buffalo's"),
                AtlasLocationSpec("B3", "City Medical Center"),
                AtlasLocationSpec("B4", "The Garden of Earthly Delights"),
                AtlasLocationSpec("B5", "GraffitiX"),
                AtlasLocationSpec("B6", "La Lune Blue"),
                AtlasLocationSpec("B7", "Marina FloatHomes"),
                AtlasLocationSpec("B8", "McCartney Cubes"),
                AtlasLocationSpec("B9", "McCartney Field Stadium"),
                AtlasLocationSpec("B10", "Night City Bubbles"),
                AtlasLocationSpec("B11", "REO Meatwagon Offices"),
                AtlasLocationSpec("B12", "TravlStay CityCenter"),
                AtlasLocationSpec("B13", "Ziggurat Offices", "Sede da gigante de Data Pool e Agents")
            )
        ),
        AtlasDistrictSpec(
            code = "C",
            namePt = "Centro (Downtown)",
            nameEn = "Downtown",
            zone = AtlasZoneId.ISLAND,
            normX = 0.25f,
            normY = 0.38f,
            securityProvider = "Whitewater Security",
            cityManager = "Emilia Ortega",
            gangsPresent = listOf("Eastern Tigers Triad", "Skiv Family", "Undertow"),
            neighborCodes = listOf("A", "B", "D", "F", "G"),
            hookDistrictMatch = "Downtown",
            summaryPt = "Coração financeiro corporativo reconstituído; segurança privada rigorosa da Whitewater e escritórios da Continental Brands.",
            locations = listOf(
                AtlasLocationSpec("C1", "Acadia Way"),
                AtlasLocationSpec("C2", "Continental Brands Offices", "Inclui o Oasis Megamart"),
                AtlasLocationSpec("C3", "Cortex Complex"),
                AtlasLocationSpec("C4", "Delirium"),
                AtlasLocationSpec("C5", "Night City Firestation #2")
            )
        ),
        AtlasDistrictSpec(
            code = "D",
            namePt = "Zona Quente (The Hot Zone)",
            nameEn = "The Hot Zone",
            zone = AtlasZoneId.ISLAND,
            normX = 0.34f,
            normY = 0.39f,
            securityProvider = "NCPD (em teoria)",
            cityManager = null,
            gangsPresent = listOf("Lightning Cats", "Maelstrom", "Reckoners", "Scavvers"),
            neighborCodes = listOf("A", "B", "C", "E", "G"),
            hookDistrictMatch = "The Hot Zone",
            summaryPt = "Cicratriz radioativa do ataque nuclear de 2023 às Torres Arasaka; território de catadores, mutantes e a boate Totentanz.",
            locations = listOf(
                AtlasLocationSpec("D1", "The Ashcroft Hotel"),
                AtlasLocationSpec("D2", "The N54"),
                AtlasLocationSpec("D3", "Toggle's Temple"),
                AtlasLocationSpec("D4", "Totentanz", "Boate/fortaleza punk controlada pelo Maelstrom")
            )
        ),
        AtlasDistrictSpec(
            code = "E",
            namePt = "Pequena China (Little China)",
            nameEn = "Little China",
            zone = AtlasZoneId.ISLAND,
            normX = 0.44f,
            normY = 0.41f,
            securityProvider = "Gold Dragons",
            cityManager = "David Ling Po",
            gangsPresent = listOf("Gold Dragons", "Red Chrome Legion", "Weng Fang Tong"),
            neighborCodes = listOf("B", "D", "G", "H", "S"),
            hookDistrictMatch = "Little China",
            summaryPt = "Distrito denso, vibrante e multicultural; abriga o Forlorn Hope original, mercados noturnos e gangues Tríades.",
            locations = listOf(
                AtlasLocationSpec("E1", "Bridgetown"),
                AtlasLocationSpec("E2", "Chrome Cross"),
                AtlasLocationSpec("E3", "Forlorn Hope (pré-Hope Reborn)"),
                AtlasLocationSpec("E4", "Guăngbō Tower"),
                AtlasLocationSpec("E5", "Prosperity Gardens Tenements")
            )
        ),
        AtlasDistrictSpec(
            code = "F",
            namePt = "Distrito Universitário (University District)",
            nameEn = "University District",
            zone = AtlasZoneId.ISLAND,
            normX = 0.25f,
            normY = 0.48f,
            securityProvider = "NCU Campus Security",
            cityManager = "Doctor Edward Michaels",
            gangsPresent = listOf("Philharmonic Vampyres", "Princesses of Justice"),
            neighborCodes = listOf("C", "G", "I"),
            hookDistrictMatch = "University District",
            summaryPt = "Sede da Night City University, campus da Biotechnica e reduto das gangues artísticas/teatrais.",
            locations = listOf(
                AtlasLocationSpec("F1", "Biotechnica Campus"),
                AtlasLocationSpec("F2", "Biotechnica Habitation Sphere Alpha"),
                AtlasLocationSpec("F3", "Night City Symphony Hall"),
                AtlasLocationSpec("F4", "Night City University"),
                AtlasLocationSpec("F5", "Parkside Living"),
                AtlasLocationSpec("F6", "Princessland!"),
                AtlasLocationSpec("F7", "Stems & Seeds"),
                AtlasLocationSpec("F8", "University Cubes"),
                AtlasLocationSpec("F9", "Yewtree")
            )
        ),
        AtlasDistrictSpec(
            code = "G",
            namePt = "The Glen",
            nameEn = "The Glen",
            zone = AtlasZoneId.ISLAND,
            normX = 0.34f,
            normY = 0.50f,
            securityProvider = "NCPD",
            cityManager = "Zohara Freeman",
            gangsPresent = listOf("DeadWoods", "Kill Krashers", "The Reckoners", "Weng Fang Tong"),
            neighborCodes = listOf("C", "D", "E", "F", "H", "I", "K"),
            hookDistrictMatch = "The Glen",
            summaryPt = "Centro cívico e corporativo de Night City: Prefeitura, 1º Banco, Distrito Policial #1, Hall of Justice e Raven Microcybernetics.",
            locations = listOf(
                AtlasLocationSpec("G1", "1st Night City Bank"),
                AtlasLocationSpec("G2", "Air"),
                AtlasLocationSpec("G3", "Bear's"),
                AtlasLocationSpec("G4", "City Hall"),
                AtlasLocationSpec("G5", "City Police Precinct #1"),
                AtlasLocationSpec("G6", "Club Atlantis"),
                AtlasLocationSpec("G7", "Glenlife Perfected"),
                AtlasLocationSpec("G8", "Hall of Justice"),
                AtlasLocationSpec("G9", "Kasim's"),
                AtlasLocationSpec("G10", "Merrill Asukaga & Finch Offices"),
                AtlasLocationSpec("G11", "Night City Plaza"),
                AtlasLocationSpec("G12", "Raven Microcybernetics"),
                AtlasLocationSpec("G13", "Seafoam")
            )
        ),
        AtlasDistrictSpec(
            code = "H",
            namePt = "Velha Japantown (Old Japantown)",
            nameEn = "Old Japantown",
            zone = AtlasZoneId.ISLAND,
            normX = 0.44f,
            normY = 0.52f,
            securityProvider = "Kimen-Gumi",
            cityManager = "Adriane Casselle",
            gangsPresent = listOf("Iron Sights", "Maelstrom", "Red Chrome Legion", "The Shroomers", "Tyger Claws"),
            neighborCodes = listOf("E", "G", "K", "L"),
            hookDistrictMatch = "Old Japantown",
            summaryPt = "Antigo distrito comercial danificado nos anos 2020; lar da Crisis Medical Center e da Bodega da Sra. Suzuki.",
            locations = listOf(
                AtlasLocationSpec("H1", "Crisis Medical Center"),
                AtlasLocationSpec("H2", "Highcourt Plaza Hotel"),
                AtlasLocationSpec("H3", "Honest Hiro's Used Cars"),
                AtlasLocationSpec("H4", "Mrs. Suzuki's Bodega", "Alvo do incidente em Danger Gal Dossier"),
                AtlasLocationSpec("H5", "The Precipice"),
                AtlasLocationSpec("H6", "Unnamed Cube Hotel")
            )
        ),
        AtlasDistrictSpec(
            code = "I",
            namePt = "Night City Sul (South Night City)",
            nameEn = "South Night City",
            zone = AtlasZoneId.ISLAND,
            normX = 0.29f,
            normY = 0.64f,
            securityProvider = "Scythe Security",
            cityManager = "Garven Haakensen",
            gangsPresent = listOf("Bozos", "DeadWoods", "The Enhanced", "Kanzaki Family", "Kill Krashers", "The Reckoners", "The Sinful Adams", "Tyger Claws", "The Zoners"),
            neighborCodes = listOf("F", "G", "J", "K", "W"),
            hookDistrictMatch = "South Night City",
            summaryPt = "Zona portuária, industrial e altamente disputada com 9 gangues ativas, estúdios recuperados e acesso à Ponte Pacifica.",
            locations = listOf(
                AtlasLocationSpec("I1", "The Boneyard"),
                AtlasLocationSpec("I2", "MindNutz Lover"),
                AtlasLocationSpec("I3", "Savage Docs"),
                AtlasLocationSpec("I4", "Silverhand Studios"),
                AtlasLocationSpec("I5", "The Slammer"),
                AtlasLocationSpec("I6", "South Cargo Village"),
                AtlasLocationSpec("I7", "Union Chapel Building"),
                AtlasLocationSpec("I8", "University Cargo Bay")
            )
        ),
        AtlasDistrictSpec(
            code = "J",
            namePt = "Porto de Night City (Port of Night City)",
            nameEn = "Port of Night City",
            zone = AtlasZoneId.ISLAND,
            normX = 0.20f,
            normY = 0.59f,
            securityProvider = "Thelas Marines",
            cityManager = "Calypso",
            gangsPresent = listOf("Consortium", "DeadWoods"),
            neighborCodes = listOf("I"),
            hookDistrictMatch = "The Port",
            summaryPt = "Docas navais operadas pelos nômades aquáticos Thelas Marines; principal ponto de contrabando marítimo.",
            locations = listOf(
                AtlasLocationSpec("J1", "Dock 13"),
                AtlasLocationSpec("J2", "Dock 14 Studio Apartments"),
                AtlasLocationSpec("J3", "Dock Cargo Community"),
                AtlasLocationSpec("J4", "Flotsam"),
                AtlasLocationSpec("J5", "Medical Technologies"),
                AtlasLocationSpec("J6", "Rusty's Dive Shack"),
                AtlasLocationSpec("J7", "South Night City Reclaimed Studio")
            )
        ),
        AtlasDistrictSpec(
            code = "K",
            namePt = "Zona de Recuperação (Reclamation Zone)",
            nameEn = "Reclamation Zone",
            zone = AtlasZoneId.ISLAND,
            normX = 0.39f,
            normY = 0.62f,
            securityProvider = "Los Perros Guardianes",
            cityManager = "Santos Dorado",
            gangsPresent = listOf("Tombstone Preservers"),
            neighborCodes = listOf("G", "H", "I", "L"),
            hookDistrictMatch = "Reclamation Zone",
            summaryPt = "Distrito em reconstrução lenta entre The Glen e a Old Combat Zone, sob proteção de nômades e Tombstone Preservers.",
            locations = listOf(
                AtlasLocationSpec("K1", "Herschel's Crematorium")
            )
        ),
        AtlasDistrictSpec(
            code = "L",
            namePt = "Antiga Zona de Combate (Old Combat Zone)",
            nameEn = "Old Combat Zone",
            zone = AtlasZoneId.ISLAND,
            normX = 0.45f,
            normY = 0.61f,
            securityProvider = "Edgerunners Inc",
            cityManager = "Brick Coleman (Edgerunners Inc)",
            gangsPresent = listOf("Edgerunners Inc", "The Faded", "Generation Red", "Iron Sights", "The Shroomers"),
            neighborCodes = listOf("H", "K"),
            hookDistrictMatch = "Old Combat Zone",
            summaryPt = "Ruínas sem lei onde gangues extremas lutam por território e o sindicato Edgerunners Inc impõe sua própria ordem.",
            locations = listOf(
                AtlasLocationSpec("L1", "From the Ashes"),
                AtlasLocationSpec("L2", "Jesse James' Kosher Deli"),
                AtlasLocationSpec("L3", "NC Ionic Semiconductor Building"),
                AtlasLocationSpec("L4", "The Underground")
            )
        ),

        // === THE NORTHSIDE (M–O) ===
        AtlasDistrictSpec(
            code = "M",
            namePt = "Base Militar NorCal (NorCal Military Base)",
            nameEn = "NorCal Military Base",
            zone = AtlasZoneId.NORTHSIDE,
            normX = 0.30f,
            normY = 0.14f,
            securityProvider = "NorCal Military Police",
            cityManager = "General Ash Giovanni",
            gangsPresent = listOf("Culper Ring"),
            neighborCodes = listOf("N"),
            hookDistrictMatch = "NorCal Military Base",
            summaryPt = "Complexo militar altamente fortificado ao norte da Baía Estero com escritórios e alojamentos da Militech.",
            locations = listOf(
                AtlasLocationSpec("M1", "Militech Corporate Operatives Housing"),
                AtlasLocationSpec("M2", "Militech Offices")
            )
        ),
        AtlasDistrictSpec(
            code = "N",
            namePt = "Desenvolvimento Watson (Watson Development)",
            nameEn = "Watson Development",
            zone = AtlasZoneId.NORTHSIDE,
            normX = 0.48f,
            normY = 0.17f,
            securityProvider = "NCPD",
            cityManager = "Lucius Rhyne",
            gangsPresent = listOf("Arzin Tynon", "Dragula Racers", "G3", "Kanzaki Family", "Tyger Claws", "Wild Things"),
            neighborCodes = listOf("M", "O", "B", "P"),
            hookDistrictMatch = "Watson Development",
            summaryPt = "Corredor industrial e corporativo pesado no norte: Petrochem, SovOil, Trauma Team Tower e o bar Redline.",
            locations = listOf(
                AtlasLocationSpec("N1", "City Police Precinct #3"),
                AtlasLocationSpec("N2", "HTown"),
                AtlasLocationSpec("N3", "Petrochem & SovOil Joint Temp Housing"),
                AtlasLocationSpec("N4", "Petrochem Offices"),
                AtlasLocationSpec("N5", "Red Oktober"),
                AtlasLocationSpec("N6", "Redline", "Clube/base de corredores e Edgerunners"),
                AtlasLocationSpec("N7", "Sakura's"),
                AtlasLocationSpec("N8", "Smash/Cut"),
                AtlasLocationSpec("N9", "SovOil Offices"),
                AtlasLocationSpec("N10", "Trauma Team Corporate Living Center"),
                AtlasLocationSpec("N11", "Trauma Team Tower"),
                AtlasLocationSpec("N12", "Vargtimmen"),
                AtlasLocationSpec("N13", "Watson Central Cubelife")
            )
        ),
        AtlasDistrictSpec(
            code = "O",
            namePt = "Kabuki",
            nameEn = "Kabuki",
            zone = AtlasZoneId.NORTHSIDE,
            normX = 0.56f,
            normY = 0.14f,
            securityProvider = "Kimen-Gumi",
            cityManager = "Yoshiki Murakami",
            gangsPresent = listOf("G3", "Tyger Claws"),
            neighborCodes = listOf("N", "P"),
            hookDistrictMatch = "Kabuki",
            summaryPt = "Bairro de vielas neon, mercados ocultos, ripperdocs clandestinos e forte presença dos Tyger Claws.",
            locations = listOf(
                AtlasLocationSpec("O1", "Nakagawa Kabuki Theater"),
                AtlasLocationSpec("O2", "Yum Seng")
            )
        ),

        // === THE MAINLAND (P–V) ===
        AtlasDistrictSpec(
            code = "P",
            namePt = "Nova Westbrook (New Westbrook)",
            nameEn = "New Westbrook",
            zone = AtlasZoneId.MAINLAND,
            normX = 0.67f,
            normY = 0.25f,
            securityProvider = "NCPD",
            cityManager = "Belkis Abera",
            gangsPresent = listOf("Arzin Tynon", "Prime-Time Players", "Street Queens", "Tombstone Preservers"),
            neighborCodes = listOf("N", "O", "Q", "R", "S"),
            hookDistrictMatch = "New Westbrook",
            summaryPt = "Distrito residencial e corporativo próspero ao longo do Canal: Network 54, WorldSat e Rocklin Augmentics.",
            locations = listOf(
                AtlasLocationSpec("P1", "Canalside Plaza"),
                AtlasLocationSpec("P2", "Chatelaine's"),
                AtlasLocationSpec("P3", "Dilly's"),
                AtlasLocationSpec("P4", "Evergreen Apartments"),
                AtlasLocationSpec("P5", "Network 54 Offices"),
                AtlasLocationSpec("P6", "Network 54 Westbrook Private Acres"),
                AtlasLocationSpec("P7", "Night City Firestation #1"),
                AtlasLocationSpec("P8", "North Cargo Village"),
                AtlasLocationSpec("P9", "Rocklin Augmentics Campus"),
                AtlasLocationSpec("P10", "Rocklin Augmentics Innovation Hub"),
                AtlasLocationSpec("P11", "WorldSat Offices")
            )
        ),
        AtlasDistrictSpec(
            code = "Q",
            namePt = "Charter Hill",
            nameEn = "Charter Hill",
            zone = AtlasZoneId.MAINLAND,
            normX = 0.80f,
            normY = 0.30f,
            securityProvider = "Militech",
            cityManager = "Symon Featherstonehaugh",
            gangsPresent = listOf("Prime-Time Players", "Tombstone Preservers", "Tyger Claws"),
            neighborCodes = listOf("P", "R", "T"),
            hookDistrictMatch = "Charter Hill",
            summaryPt = "Enclave de classe alta e executivos médios vigiado por força contratada da Militech.",
            locations = listOf(
                AtlasLocationSpec("Q1", "Anjelika's"),
                AtlasLocationSpec("Q2", "Bella Mia"),
                AtlasLocationSpec("Q3", "L'Ermitage"),
                AtlasLocationSpec("Q4", "Seral Grove")
            )
        ),
        AtlasDistrictSpec(
            code = "R",
            namePt = "Zona Executiva (Exec Zone)",
            nameEn = "Exec Zone",
            zone = AtlasZoneId.MAINLAND,
            normX = 0.89f,
            normY = 0.42f,
            securityProvider = "Lazarus",
            cityManager = "Doctor Karen Davies",
            gangsPresent = emptyList(),
            neighborCodes = listOf("P", "Q", "T", "V"),
            hookDistrictMatch = "Executive Zone",
            summaryPt = "Comunidade murada e ultra-exclusiva de executivos corporativos de alto escalão, patrulhada pela Lazarus.",
            locations = emptyList()
        ),
        AtlasDistrictSpec(
            code = "S",
            namePt = "Docas de Heywood (Heywood Docks)",
            nameEn = "Heywood Docks",
            zone = AtlasZoneId.MAINLAND,
            normX = 0.56f,
            normY = 0.42f,
            securityProvider = "SK Security",
            cityManager = "Andrea Lee",
            gangsPresent = listOf("DeadWoods", "Skiv Family"),
            neighborCodes = listOf("E", "P", "T", "U"),
            hookDistrictMatch = "Heywood Docks",
            summaryPt = "Ponto de desembarque na margem leste da Ponte San Morro, tomado por contêineres Greenbox e docas industriais.",
            locations = listOf(
                AtlasLocationSpec("S1", "Greenbox Storage Units")
            )
        ),
        AtlasDistrictSpec(
            code = "T",
            namePt = "Heywood Norte (North Heywood)",
            nameEn = "North Heywood",
            zone = AtlasZoneId.MAINLAND,
            normX = 0.72f,
            normY = 0.44f,
            securityProvider = "6th Street",
            cityManager = "Barry \"Big Deal\" Delvecchio",
            gangsPresent = listOf("6th Street", "Inquisitors", "The Muses", "The Toecutters"),
            neighborCodes = listOf("Q", "R", "S", "U", "V"),
            hookDistrictMatch = "North Heywood",
            summaryPt = "Bastião da 6th Street; abriga o campus da Dynalar, Acorn Towers e o novo Forlorn Hope (pós-Hope Reborn).",
            locations = listOf(
                AtlasLocationSpec("T1", "Acorn Towers"),
                AtlasLocationSpec("T2", "The Armory"),
                AtlasLocationSpec("T3", "Biotechnica Palm Grove"),
                AtlasLocationSpec("T4", "Converted Motel Studio Apartments"),
                AtlasLocationSpec("T5", "Dynalar Campus"),
                AtlasLocationSpec("T6", "Forlorn Hope (pós-Hope Reborn)"),
                AtlasLocationSpec("T7", "Nana Meow's Nursery"),
                AtlasLocationSpec("T8", "Night City Animal Shelter"),
                AtlasLocationSpec("T9", "Woodland Park"),
                AtlasLocationSpec("T10", "Xanadu")
            )
        ),
        AtlasDistrictSpec(
            code = "U",
            namePt = "Zona Industrial de Heywood (Heywood Industrial)",
            nameEn = "Heywood Industrial Zone",
            zone = AtlasZoneId.MAINLAND,
            normX = 0.58f,
            normY = 0.56f,
            securityProvider = "NCPD",
            cityManager = "Theo Walker",
            gangsPresent = listOf("Consortium", "DeadWoods", "The Enhanced", "Fixie's Couriers"),
            neighborCodes = listOf("S", "T", "V"),
            hookDistrictMatch = "Heywood Industrial",
            summaryPt = "Complexos fabris, armazéns, parque de escritórios Zhirafa e a oficina Yang's Wheels.",
            locations = listOf(
                AtlasLocationSpec("U1", "D.V. Rambling Rose"),
                AtlasLocationSpec("U2", "Old Ironworks Building"),
                AtlasLocationSpec("U3", "Yang's Wheels"),
                AtlasLocationSpec("U4", "Zhirafa Office Park"),
                AtlasLocationSpec("U5", "Zhirafa Office Park MicroVillage")
            )
        ),
        AtlasDistrictSpec(
            code = "V",
            namePt = "Santo Domingo",
            nameEn = "Santo Domingo",
            zone = AtlasZoneId.MAINLAND,
            normX = 0.74f,
            normY = 0.64f,
            securityProvider = "Aldecaldo Peacekeepers",
            cityManager = "Theresa Valentino",
            gangsPresent = listOf("El Norte Cartel", "Kill Krashers", "Rat Kings", "Steel Vaqueros"),
            neighborCodes = listOf("R", "T", "U", "W", "X"),
            hookDistrictMatch = "Santo Domingo",
            summaryPt = "Distrito operário, industrial e de fronteira sul com forte presença Aldecaldo e da oficina Woodchipper's Garage.",
            locations = listOf(
                AtlasLocationSpec("V1", "Aldecaldo Camp"),
                AtlasLocationSpec("V2", "City Police Precinct #2"),
                AtlasLocationSpec("V3", "East Cargo Village"),
                AtlasLocationSpec("V4", "Heywood Suites"),
                AtlasLocationSpec("V5", "María's"),
                AtlasLocationSpec("V6", "MetalStorm"),
                AtlasLocationSpec("V7", "Woodchipper's Garage", "Oficina clássica de armas pesadas e veículos")
            )
        ),

        // === THE SOUTHSIDE (W–X) ===
        AtlasDistrictSpec(
            code = "W",
            namePt = "Pacifica Playground",
            nameEn = "Pacifica Playground",
            zone = AtlasZoneId.SOUTHSIDE,
            normX = 0.53f,
            normY = 0.83f,
            securityProvider = "Militech",
            cityManager = "Elliot Kane",
            gangsPresent = listOf("6th Street", "The Andersons", "The Enhanced", "Mudang Gumi", "Piranhas", "Voodoo Boys"),
            neighborCodes = listOf("I", "V", "X"),
            hookDistrictMatch = "Playland by the Sea",
            summaryPt = "Estância turística decadente ao sul da Ponte Pacifica; abriga Playland by the Sea, Bits'n'Bolts e os Voodoo Boys.",
            locations = listOf(
                AtlasLocationSpec("W1", "Bits'n'Bolts"),
                AtlasLocationSpec("W2", "Cubeland by the Sea"),
                AtlasLocationSpec("W3", "Mister Rice Guy"),
                AtlasLocationSpec("W4", "Playhouse"),
                AtlasLocationSpec("W5", "Playland by the Sea", "Parque de diversões e ponto de encontro clássico"),
                AtlasLocationSpec("W6", "Pleasant Valley Apartments"),
                AtlasLocationSpec("W7", "Scenic Cubes"),
                AtlasLocationSpec("W8", "The XX (The Twenty)")
            )
        ),
        AtlasDistrictSpec(
            code = "X",
            namePt = "Rancho Coronado",
            nameEn = "Rancho Coronado",
            zone = AtlasZoneId.SOUTHSIDE,
            normX = 0.72f,
            normY = 0.84f,
            securityProvider = "NCPD (em teoria)",
            cityManager = null,
            gangsPresent = listOf("6th Street", "Albino Alligators", "Dirty Hippies", "Steel Vaqueros", "Voodoo Boys"),
            neighborCodes = listOf("V", "W"),
            hookDistrictMatch = "Rancho Coronado",
            summaryPt = "Subúrbio industrial abandonado pela corporação; abriga o Rancho Coronado Night Market (Minimallism) e o Estádio Eagle Rock.",
            locations = listOf(
                AtlasLocationSpec("X1", "Albino Alligator Carwash"),
                AtlasLocationSpec("X2", "Coronado Heights"),
                AtlasLocationSpec("X3", "Eagle Rock Stadium"),
                AtlasLocationSpec("X4", "Jack 'N' the Green"),
                AtlasLocationSpec("X5", "Minimallism (Rancho Coronado Night Market)")
            )
        )
    )

    val allLocationsCount: Int = districts.sumOf { it.locations.size } // 156

    /**
     * 43 Gangs summarized at the back of Night City Atlas (pp. 14–15).
     */
    val briefGangs: List<AtlasBriefGangSpec> = listOf(
        AtlasBriefGangSpec("6th Street", "Veteranos patrióticos de guerra atuando como milícia comunitária e extorsão."),
        AtlasBriefGangSpec("Albino Alligators", "Gangue agressiva e bizarra ligada a Rancho Coronado e jacarés de esgoto."),
        AtlasBriefGangSpec("The Andersons", "Gangue familiar excêntrica e perigosa sediada em Pacifica Playground."),
        AtlasBriefGangSpec("Arzin Tynon", "Gangue mercenária implacável atuando em Watson e New Westbrook."),
        AtlasBriefGangSpec("Bozos", "Palhaços biossimulados sádicos que espalham terror e piadas mortais no Sul."),
        AtlasBriefGangSpec("Consortium", "Sindicato de contrabandistas e força armada ligada ao Porto e Heywood Industrial."),
        AtlasBriefGangSpec("Culper Ring", "Célula secreta de espionagem e infiltração na Base Militar NorCal."),
        AtlasBriefGangSpec("DeadWoods", "Gangue pesada com atuação no Glen, Sul, Porto e Docas de Heywood."),
        AtlasBriefGangSpec("Dirty Hippies", "Ecoterroristas nômades/suburbanos de Rancho Coronado."),
        AtlasBriefGangSpec("Dragula Racers", "Corredores de rua turbinados e obcecados por carros de combate em Watson."),
        AtlasBriefGangSpec("Eastern Tigers Triad", "Tríade tradicional operando negócios ilícitos e segurança em Downtown."),
        AtlasBriefGangSpec("Edgerunners Inc", "Sindicato organizado que provê segurança e governa a Old Combat Zone."),
        AtlasBriefGangSpec("El Norte Cartel", "Cartel fronteiriço ativo em Santo Domingo."),
        AtlasBriefGangSpec("The Enhanced", "Transumanos armados e modificados presentes no Sul, Heywood Industrial e Pacifica."),
        AtlasBriefGangSpec("Eurotrashers", "Gangue elitista de moda e violência em Little Europe."),
        AtlasBriefGangSpec("The Faded", "Catadores furtivos e ex-soldados traumatizados da Old Combat Zone."),
        AtlasBriefGangSpec("Fixie's Couriers", "Mensageiros ciclistas armados e ágeis da Zona Industrial de Heywood."),
        AtlasBriefGangSpec("G3", "Gangue ágil de artes marciais espalhada por Watson e Kabuki."),
        AtlasBriefGangSpec("Generation Red", "Jovens rebeldes e revolucionários armados na Old Combat Zone."),
        AtlasBriefGangSpec("Gold Dragons", "Tríade protetora e força policial de facto de Little China."),
        AtlasBriefGangSpec("Inquisitors", "Fanáticos anticiências/anti-cromo que arrancam cyberware à força."),
        AtlasBriefGangSpec("Iron Sights", "Mercenários cibernéticos brutais em Old Japantown e Old Combat Zone."),
        AtlasBriefGangSpec("Kanzaki Family", "Facção Yakuza operando entre Watson e South Night City."),
        AtlasBriefGangSpec("Kill Krashers", "Demolidores e pirômanos violentos em The Glen, Sul e Santo Domingo."),
        AtlasBriefGangSpec("Lightning Cats", "Especialistas em emboscadas ágeis dentro da Zona Quente."),
        AtlasBriefGangSpec("Maelstrom", "Gangue ciberpsicótica fanática por implantes pesados e Totentanz."),
        AtlasBriefGangSpec("Mudang Gumi", "Gangue mística/Netrunner coreana com forte presença em Pacifica."),
        AtlasBriefGangSpec("The Muses", "Artistas armados e hackers de interferência sonora em North Heywood."),
        AtlasBriefGangSpec("Philharmonic Vampyres", "Músicos excêntricos com Vampyres rondando o Distrito Universitário."),
        AtlasBriefGangSpec("Piranhas", "Festeiros hedonistas e armados com Cybersnakes em Pacifica."),
        AtlasBriefGangSpec("Prime-Time Players", "Posergangue que imita celebridades clássicas da TV."),
        AtlasBriefGangSpec("Princesses of Justice", "Justiçadoras temáticas do Distrito Universitário com armas brancas pesadas."),
        AtlasBriefGangSpec("Rat Kings", "Moradores dos túneis, esgotos e catadores de Santo Domingo."),
        AtlasBriefGangSpec("Reckoners", "Profetas niilistas armados atuando na Zona Quente, Glen e Sul."),
        AtlasBriefGangSpec("Red Chrome Legion", "Extremistas fascistas neonazistas armados em Little China e Japantown."),
        AtlasBriefGangSpec("Scavvers", "Catadores oportunistas e ladrões de órgãos cibernéticos na Zona Quente."),
        AtlasBriefGangSpec("The Shroomers", "Traficantes e batedores micologistas das áreas degradadas."),
        AtlasBriefGangSpec("The Sinful Adams", "Gangue de combatentes corpo a corpo em South Night City."),
        AtlasBriefGangSpec("Skiv Family", "Família criminosa atuando em Downtown e Heywood Docks."),
        AtlasBriefGangSpec("Steel Vaqueros", "Gangue nômade/motorizada armada pesadamente em Santo Domingo e Rancho Coronado."),
        AtlasBriefGangSpec("Street Queens", "Gangue estilosa de rua operando em Upper Marina e New Westbrook."),
        AtlasBriefGangSpec("The Toecutters", "Motociclistas brutais e traficantes de North Heywood."),
        AtlasBriefGangSpec("Tombstone Preservers", "Guardiões sombrios armados na Reclamation Zone, Westbrook e Charter Hill."),
        AtlasBriefGangSpec("Tyger Claws", "Poderosa gangue de influência Yakuza forte em Japantown, Watson e Kabuki."),
        AtlasBriefGangSpec("Undertow", "Gangue costeira e contrabandista entre Little Europe e Downtown."),
        AtlasBriefGangSpec("Voodoo Boys", "Místicos Netrunners haitianos de Pacifica e Rancho Coronado."),
        AtlasBriefGangSpec("Weng Fang Tong", "Antiga sociedade secreta chinesa em Little China e The Glen."),
        AtlasBriefGangSpec("Wild Things", "Gladiadores cibernéticos brutais da arena de Watson."),
        AtlasBriefGangSpec("The Zoners", "Defensores territoriais ferozes dos becos de South Night City.")
    )

    fun findDistrictByCode(code: String): AtlasDistrictSpec? =
        districts.firstOrNull { it.code.equals(code.trim(), ignoreCase = true) }

    fun searchLocations(query: String): List<Pair<AtlasDistrictSpec, AtlasLocationSpec>> {
        val q = query.trim()
        if (q.isEmpty()) return emptyList()
        return districts.flatMap { d ->
            d.locations
                .filter {
                    it.name.contains(q, ignoreCase = true) ||
                        it.code.contains(q, ignoreCase = true) ||
                        it.notePt.contains(q, ignoreCase = true) ||
                        d.namePt.contains(q, ignoreCase = true)
                }
                .map { d to it }
        }
    }
}
