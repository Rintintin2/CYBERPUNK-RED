package com.example.core.rules

import com.example.data.local.entity.InventoryItemType

enum class NightMarketCategory(
    val roll1d6: Int,
    val labelPt: String,
    val descriptionPt: String
) {
    FOOD_AND_DRUGS(1, "Comida e Drogas", "Ração, vegetais frescos, animais vivos e drogas de rua."),
    PERSONAL_ELECTRONICS(2, "Eletrônicos Pessoais", "Aparelhos, cyberdecks, programas e até Black ICE."),
    WEAPONS_AND_ARMOR(3, "Armas e Armaduras", "Tudo para sobreviver ou causar caos nas ruas."),
    CYBERWARE(4, "Cyberware", "Implantes de ponta (inclui fundacionais e instalação grátis no Ripperdoc parceiro)."),
    CLOTHING_AND_FASHIONWARE(5, "Roupas e Fashionware", "Moda de rua e cromo estético instalável na hora."),
    SURVIVAL_GEAR(6, "Equipamento de Sobrevivência", "Todo o equipamento prático para a Era do Vermelho.")
}

data class NightMarketOffer(
    val d100Roll: Int,
    val category: NightMarketCategory,
    val labelPt: String,
    val costEb: Int,
    val priceCategory: PriceCategory,
    val itemType: InventoryItemType
)

data class GeneratedNightMarket(
    val categories: List<NightMarketCategory>,
    val offers: List<NightMarketOffer>
)

data class ScreamsheetHeadline(
    val categoryPt: String,
    val subjectPt: String,
    val verbPt: String,
    val objectPt: String
) {
    val headlineTextPt: String get() = "[$categoryPt] $subjectPt $verbPt $objectPt"
}

data class VenditRollResult(
    val typePt: String,
    val contentsPt: String
)

data class BodegaSceneResult(
    val roll1d6: Int,
    val cashierPt: String,
    val character1Pt: String,
    val character2Pt: String
)

/**
 * Canonical Part 2 Random Street Generators (Core Rulebook pp. 326, 331–332, 338–339):
 * - Night Market Generator (2x 1d6 categories, 1d10 items each via d100 across exact 20 buckets)
 * - Screamsheet Headline Generator
 * - Vendit Generator
 * - Bodega Clientele Generator
 */
object OfficialStreetGenerators {

    private data class NmBucket(
        val range: IntRange,
        val labelPt: String,
        val costEb: Int,
        val priceCategory: PriceCategory,
        val itemType: InventoryItemType
    )

    private val nightMarketBuckets: Map<NightMarketCategory, List<NmBucket>> = mapOf(
        NightMarketCategory.FOOD_AND_DRUGS to listOf(
            NmBucket(1..5, "Enlatados (Canned Goods)", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(6..10, "Produtos Embalados", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(11..15, "Congelados", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(16..20, "Sacos de Grãos", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(21..25, "Pacote de Kibble", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(26..30, "Sacos de Prepak", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(31..35, "Drogas de Rua (até 20eb)", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(36..40, "Álcool de Baixa Qualidade", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(41..45, "Álcool Padrão", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(46..50, "Álcool de Qualidade Excelente", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(51..55, "MRE (Ração Autoaquecida)", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(56..60, "Galinhas Vivas", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(61..65, "Peixes Vivos", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(66..70, "Frutas Frescas", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(71..75, "Vegetais Frescos", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(76..80, "Raízes Comestíveis", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(81..85, "Porcos Vivos", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(86..90, "Frutas Exóticas", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(91..95, "Vegetais Exóticos", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(96..100, "Drogas de Rua (Black Lace / Boost 50eb)", 50, PriceCategory.COSTLY, InventoryItemType.GEAR)
        ),
        NightMarketCategory.PERSONAL_ELECTRONICS to listOf(
            NmBucket(1..5, "Agent (Smartphone IA)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(6..10, "Programas ou Hardware (até 100eb)", 100, PriceCategory.PREMIUM, InventoryItemType.PROGRAM),
            NmBucket(11..15, "Gravador de Áudio", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(16..20, "Detector de Escutas (Bug Detector)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(21..25, "Analisador Químico", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(26..30, "Computador", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(31..35, "Cyberdeck (Qualidade Padrão)", 500, PriceCategory.EXPENSIVE, InventoryItemType.CYBERDECK),
            NmBucket(36..40, "Celular Descartável", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(41..45, "Guitarra Elétrica / Instrumento", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(46..50, "Programas ou Hardware (500eb)", 500, PriceCategory.EXPENSIVE, InventoryItemType.PROGRAM),
            NmBucket(51..55, "Medscanner", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(56..60, "Rastreador (Homing Tracer)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(61..65, "Comunicador de Rádio", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(66..70, "Techscanner", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(71..75, "Óculos Smart (Smart Glasses)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(76..80, "Detector de Radar", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(81..85, "Codificador/Decodificador", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(86..90, "Scanner de Rádio / Player", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(91..95, "Visor Braindance", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(96..100, "Óculos de Virtualidade", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR)
        ),
        NightMarketCategory.WEAPONS_AND_ARMOR to listOf(
            NmBucket(1..5, "Pistola Média (todas qualidades)", 50, PriceCategory.COSTLY, InventoryItemType.WEAPON),
            NmBucket(6..10, "Pistola Pesada ou Muito Pesada", 100, PriceCategory.PREMIUM, InventoryItemType.WEAPON),
            NmBucket(11..15, "SMG (todas qualidades)", 100, PriceCategory.PREMIUM, InventoryItemType.WEAPON),
            NmBucket(16..20, "SMG Pesada (todas qualidades)", 100, PriceCategory.PREMIUM, InventoryItemType.WEAPON),
            NmBucket(21..25, "Espingarda / Shotgun", 500, PriceCategory.EXPENSIVE, InventoryItemType.WEAPON),
            NmBucket(26..30, "Rifle de Assalto", 500, PriceCategory.EXPENSIVE, InventoryItemType.WEAPON),
            NmBucket(31..35, "Rifle de Precisão (Sniper)", 500, PriceCategory.EXPENSIVE, InventoryItemType.WEAPON),
            NmBucket(36..40, "Arco ou Besta", 100, PriceCategory.PREMIUM, InventoryItemType.WEAPON),
            NmBucket(41..45, "Lança-Granadas ou Lança-Foguetes", 500, PriceCategory.EXPENSIVE, InventoryItemType.WEAPON),
            NmBucket(46..50, "Munição Especial (até 500eb)", 100, PriceCategory.PREMIUM, InventoryItemType.AMMO),
            NmBucket(51..55, "Arma Exótica (escolha do Mestre)", 500, PriceCategory.EXPENSIVE, InventoryItemType.WEAPON),
            NmBucket(56..60, "Arma Branca Leve", 50, PriceCategory.COSTLY, InventoryItemType.WEAPON),
            NmBucket(61..65, "Arma Branca Média", 50, PriceCategory.COSTLY, InventoryItemType.WEAPON),
            NmBucket(66..70, "Arma Branca Pesada", 100, PriceCategory.PREMIUM, InventoryItemType.WEAPON),
            NmBucket(71..75, "Arma Branca Muito Pesada", 500, PriceCategory.EXPENSIVE, InventoryItemType.WEAPON),
            NmBucket(76..80, "Armadura de até 100eb (Couros/Kevlar/LAJ)", 100, PriceCategory.PREMIUM, InventoryItemType.ARMOR),
            NmBucket(81..85, "Armadura de 500eb (HAJ / Flak)", 500, PriceCategory.EXPENSIVE, InventoryItemType.ARMOR),
            NmBucket(86..90, "Armadura de 1.000eb (Bodyweight Suit)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.ARMOR),
            NmBucket(91..95, "Acessórios de Arma (até 100eb)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(96..100, "Acessórios de Arma (500eb+)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR)
        ),
        NightMarketCategory.CYBERWARE to listOf(
            NmBucket(1..5, "Ciberóptico (Cybereye)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(6..10, "Suíte Cyberaudio", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(11..15, "Neural Link (Link Neural)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(16..20, "Ciberbraço (Cyberarm)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(21..25, "Ciberperna (Cyberleg)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(26..30, "Cyberware Externo (1.000eb)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(31..35, "Cyberware Externo (até 500eb)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(36..40, "Cyberware Interno (1.000eb)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(41..45, "Cyberware Interno (até 500eb)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(46..50, "Opção de Ciberóptico (1.000eb)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(51..55, "Opção de Ciberóptico (até 500eb)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(56..60, "Opção de Cyberaudio (1.000eb)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(61..65, "Opção de Cyberaudio (até 500eb)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(66..70, "Opção de Neuralware (1.000eb)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(71..75, "Opção de Neuralware (até 500eb)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(76..80, "Opção de Cibermembro (1.000eb)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(81..85, "Opção de Cibermembro (até 500eb)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(86..90, "Fashionware (à escolha)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(91..95, "Borgware (à escolha)", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(96..100, "Qualquer Cyberware (à escolha)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR)
        ),
        NightMarketCategory.CLOTHING_AND_FASHIONWARE to listOf(
            NmBucket(1..5, "Roupas Bag Lady Chic", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(6..10, "Roupas Gang Colors", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(11..15, "Roupas Generic Chic", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(16..20, "Roupas Bohemian", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(21..25, "Roupas Leisurewear", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(26..30, "Roupas Nomad Leathers", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(31..35, "Roupas Asia Pop", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(36..40, "Roupas Urban Flash", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(41..45, "Roupas Businesswear", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(46..50, "Roupas High Fashion", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(51..55, "Biomonitor (Fashionware)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(56..60, "Chemskin (Fashionware)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(61..65, "EMP Threading (Fashionware)", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(66..70, "Light Tattoo (Fashionware)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(71..75, "Shift Tacts (Fashionware)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(76..80, "Skinwatch (Fashionware)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(81..85, "Techhair (Fashionware)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(86..90, "Roupas Generic Chic (lote)", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(91..95, "Roupas Leisurewear (lote)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(96..100, "Roupas Gang Colors (lote)", 50, PriceCategory.COSTLY, InventoryItemType.GEAR)
        ),
        NightMarketCategory.SURVIVAL_GEAR to listOf(
            NmBucket(1..5, "Máscara Anti-Smog", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(6..10, "Protetores Auriculares Auto-Level", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(11..15, "Binóculos", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(16..20, "Mochila (Carryall)", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(21..25, "Lanterna", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(26..30, "Fita Adesiva (Duct Tape)", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(31..35, "Colchão Inflável & Saco de Dormir", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(36..40, "Kit de Gazua (Lock Picking Set)", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(41..45, "Algemas", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(46..50, "Bolsa Médica (Medtech Bag)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(51..55, "Barraca & Camping", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(56..60, "Corda (60m)", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(61..65, "Techtool", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(66..70, "Personal CarePak", 20, PriceCategory.EVERYDAY, InventoryItemType.GEAR),
            NmBucket(71..75, "Traje Anti-Radiação", 1000, PriceCategory.VERY_EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(76..80, "Sinalizador Rodoviário (Road Flare)", 10, PriceCategory.CHEAP, InventoryItemType.GEAR),
            NmBucket(81..85, "Pistola de Gancho (Grapple Gun)", 100, PriceCategory.PREMIUM, InventoryItemType.GEAR),
            NmBucket(86..90, "Bolsa Técnica (Tech Bag)", 500, PriceCategory.EXPENSIVE, InventoryItemType.GEAR),
            NmBucket(91..95, "Pá ou Machado", 50, PriceCategory.COSTLY, InventoryItemType.GEAR),
            NmBucket(96..100, "Airhypo", 50, PriceCategory.COSTLY, InventoryItemType.GEAR)
        )
    )

    fun generateNightMarket(diceEngine: DiceEngine): GeneratedNightMarket {
        val cat1Roll = diceEngine.rollD6().coerceIn(1, 6)
        var cat2Roll = diceEngine.rollD6().coerceIn(1, 6)
        var guard = 0
        while (cat2Roll == cat1Roll && guard < 12) {
            cat2Roll = diceEngine.rollD6().coerceIn(1, 6)
            guard++
        }
        if (cat2Roll == cat1Roll) {
            cat2Roll = (cat1Roll % 6) + 1
        }

        val selectedCategories = listOf(
            NightMarketCategory.entries.first { it.roll1d6 == cat1Roll },
            NightMarketCategory.entries.first { it.roll1d6 == cat2Roll }
        )

        val allOffers = mutableListOf<NightMarketOffer>()
        selectedCategories.forEach { category ->
            val count = diceEngine.rollPlainD10().coerceIn(1, 10)
            val buckets = nightMarketBuckets.getValue(category)
            val usedLabels = mutableSetOf<String>()
            var generatedForCat = 0
            var attempts = 0
            while (generatedForCat < count && attempts < 40) {
                val r = diceEngine.rollD100().coerceIn(1, 100)
                val match = buckets.first { r in it.range }
                if (usedLabels.add(match.labelPt)) {
                    allOffers.add(
                        NightMarketOffer(
                            d100Roll = r,
                            category = category,
                            labelPt = match.labelPt,
                            costEb = match.costEb,
                            priceCategory = match.priceCategory,
                            itemType = match.itemType
                        )
                    )
                    generatedForCat++
                }
                attempts++
            }
        }
        return GeneratedNightMarket(selectedCategories, allOffers)
    }

    private val screamsheetCategories = listOf("Internacional", "Nacional", "Estadual", "Local", "Financeiro", "Fofoca")
    private val screamsheetCol1 = listOf("Megacorp", "Senador", "Presidente", "Corporações", "Câmara Municipal", "Ciberpsicopata", "Assassino", "Matador", "Trágico", "Investigadores")
    private val screamsheetCol2 = listOf("Oferece", "Ameaça", "Compromete", "Assassina", "Morto por", "Morre em", "Elogia", "Anuncia", "Revela", "Continua")
    private val screamsheetCol3 = listOf("Corporações", "Cidade", "Acordo", "Alerta", "Plano", "Escândalo", "Mulher", "Homem", "Acidente", "Esperança")

    fun rollScreamsheetHeadline(diceEngine: DiceEngine): ScreamsheetHeadline {
        val cat = screamsheetCategories[(diceEngine.rollD6() - 1).coerceIn(0, 5)]
        val s = screamsheetCol1[(diceEngine.rollPlainD10() - 1).coerceIn(0, 9)]
        val v = screamsheetCol2[(diceEngine.rollPlainD10() - 1).coerceIn(0, 9)]
        val o = screamsheetCol3[(diceEngine.rollPlainD10() - 1).coerceIn(0, 9)]
        return ScreamsheetHeadline(cat, s, v, o)
    }

    private val venditFood = listOf("Lámen instantâneo", "Pizza", "Hambúrguer", "Smash", "Sushi", "Carne Quente", "Saco 1kg de Kibble", "Café enlatado", "Suco enlatado", "Refrigerante em lata")
    private val venditPersonal = listOf("Camiseta enlatada", "Brinquedo erótico", "Guarda-chuva", "Gravata", "Máscara cirúrgica", "Roupa íntima", "Arranjo floral", "Armas & Munição", "Chips de Braindance", "Videogame")
    private val venditWeird = listOf("Peruca para pets", "Salgadinho de insetos", "Bola de alface", "Sopa de peixe-voador", "Frutos do mar de tanque", "Cheiro enlatado", "Besouro-rinoceronte vivo", "Cosplay de papel", "Cápsula bizarra", "Item usado suspeito")

    fun rollVendit(diceEngine: DiceEngine): VenditRollResult {
        val typeRoll = diceEngine.rollD6().coerceIn(1, 6)
        val itemIdx = (diceEngine.rollPlainD10() - 1).coerceIn(0, 9)
        return when (typeRoll) {
            in 1..3 -> VenditRollResult("Vendit de Comida", venditFood[itemIdx])
            in 4..5 -> VenditRollResult("Vendit Pessoal", venditPersonal[itemIdx])
            else -> VenditRollResult("Vendit Bizarro", venditWeird[itemIdx])
        }
    }

    private val bodegaScenes: List<BodegaSceneResult> = listOf(
        BodegaSceneResult(1, "Vovó maternal que trata você como neto", "Cliente murmurando sozinho que para e te encara", "Yo-ganger sondando a loja tentando criar coragem para assaltar"),
        BodegaSceneResult(2, "Aposentado rabugento que odeia todo mundo", "Cliente bêbado e inconveniente", "Ganger que vai quebrar a loja em 1d6 turnos"),
        BodegaSceneResult(3, "Yo-ganger entediado preso ao caixa pelos pais", "Cliente chapado espantando alucinações no ar", "Ganger pé-de-chinelo cobrando 'taxa de proteção' do dono"),
        BodegaSceneResult(4, "Sujeito exageradamente amigável dando em cima de você", "Joygirl/boy sem grana oferecendo 'favor' por um Smash", "Criança de 4 anos fugida procurando a mãe"),
        BodegaSceneResult(5, "Caixa completamente chapado dizendo 'Tanto faz, cara...'", "Homem da Lei de folga comendo rápido e sem paciência", "Casal discutindo aos berros ao entrar na loja"),
        BodegaSceneResult(6, "Pessoa de meia-idade incompreensível discutindo contigo", "Cliente apavorado na rua prestes a sacar arma em quem chegar perto", "Casal rico ostentando caro comprando bebida após festa")
    )

    fun rollBodegaScene(diceEngine: DiceEngine): BodegaSceneResult {
        val r = diceEngine.rollD6().coerceIn(1, 6)
        return bodegaScenes[r - 1]
    }
}
