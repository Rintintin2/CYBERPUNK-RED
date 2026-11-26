package com.example.core.rules

enum class WeatherSeason(val labelPt: String) {
    WINTER_DEC_FEB("Dezembro–Fevereiro (Inverno)"),
    SPRING_MAR_MAY("Março–Maio (Primavera)"),
    SUMMER_JUN_AUG("Junho–Agosto (Verão)"),
    AUTUMN_SEP_NOV("Setembro–Novembro (Outono)")
}

data class WeatherRollResult(
    val season: WeatherSeason,
    val seasonRoll1d6: Int,
    val conditionNamePt: String,
    val strangeRoll1d10: Int? = null,
    val strangeConditionNamePt: String? = null,
    val mechanicalEffectPt: String
)

enum class SalvageCategory(val d6LabelPt: String, val labelPt: String) {
    CYBERWARE("1", "Cyberware"),
    WEAPONS("2", "Armas"),
    GENERAL_GEAR("3–4", "Equipamento Geral"),
    ARMOR("5", "Armadura"),
    CYBERDECKS_PROGRAMS("6", "Cyberdecks & Programas")
}

enum class SalvageWorthTier(
    val costEb: Int,
    val labelPt: String,
    val randomFindDv: Int,
    val specificFindDv: Int?
) {
    EB_10(10, "Barato (10eb)", 9, 13),
    EB_20(20, "Comum (20eb)", 13, 15),
    EB_50(50, "Custoso (50eb)", 15, 17),
    EB_100(100, "Premium (100eb)", 17, 21),
    EB_500(500, "Caro (500eb)", 24, 29),
    EB_1000(1000, "Muito Caro (1.000eb)", 29, null)
}

data class SalvageAttemptResult(
    val basicTechBase: Int,
    val rollTotal: Int,
    val dv: Int,
    val succeeded: Boolean,
    val isCriticalFailure: Boolean,
    val worthTier: SalvageWorthTier,
    val category: SalvageCategory,
    val perilTextPt: String? = null
)

data class DatepathRollResult(
    val keyword1Pt: String,
    val keyword2Pt: String,
    val locationNamePt: String,
    val activityPt: String,
    val outcomeLabelPt: String,
    val twistPt: String,
    val aftermathPt: String
)

/**
 * Interface RED Vol. 2–3 Solo World & Downtime Systems:
 * - Night City Weather (Seasonal 1d6 + Strange Weather 1d10)
 * - Salvaging Night City (1 week downtime, Basic Tech DVs + Critical Perils)
 * - The Datepath (Digital Dating generator)
 * - Cube Hotels & Cargo Containers 1d10, Bicycles & Riding Fatigue, ELO Compendium
 */
object OfficialDlcSoloSystems {

    val strangeWeather1d10: List<Pair<String, String>> = listOf(
        "Tempestade de Vento Radioativa (Radioactive Windstorm)" to "Sem Traje Anti-Radiação: 1d6 dano direto/hora; visibilidade -4.",
        "Tempestade de Cinzas (Ash Storm)" to "Sem Máscara Anti-Smog: testa Resistir Tortura/Drogas DV13 ou tosse (-2 ações).",
        "Alagamento (Flooding)" to "Ruas baixas alagadas (MOV pela metade a pé; risco elétrico em bueiros).",
        "Chuva de Sangue (Blood Rain)" to "Precipitação vermelha tóxica da Era do Vermelho; mancha roupas e corrói pintura.",
        "Chuva Ácida (Acid Rain)" to "Corrói 1 PA a cada 10 min exposto sem guarda-chuva/forro impermeável; 1d6 dano direto na pele.",
        "Tempestade Elétrica Letal" to "Ventos fortes (-2 à distância) + risco de queda de raio/curto em antenas.",
        "Smog de Inversão (Inversion Smog)" to "Neblina tóxica densa: visibilidade máx 12m (-4 testes visuais) sem Máscara Anti-Smog.",
        "Onda de Frio / Calor Extremo" to "Requer Forro Térmico adequado ou sofre 1 nível de fadiga/Resistir DV13 a cada 2h.",
        "Tempestade de Poeira (Dust Storm)" to "Areia abrasiva da Badlands: -4 em Percepção e dirige DV13 para manter controle.",
        "Apagão Geral (Blackout)" to "Rede elétrica cai no distrito por 1d6 horas; elevadores e postes desligam."
    )

    fun rollWeather(season: WeatherSeason, diceEngine: DiceEngine = DiceEngine()): WeatherRollResult {
        val r6 = diceEngine.rollNd6(1).total
        val baseLabel = when (season) {
            WeatherSeason.WINTER_DEC_FEB -> when (r6) {
                1 -> "Clima Estranho!"
                2 -> "Frio Cortante & Neblina"
                3 -> "Chuva Gelada (Cold Rain)"
                4 -> "Nublado Pesado (Overcast)"
                5 -> "Céu Limpo & Frio"
                else -> "Sol Frio de Inverno"
            }
            WeatherSeason.SPRING_MAR_MAY -> when (r6) {
                1 -> "Clima Estranho!"
                2 -> "Chuva Típica de Primavera"
                3 -> "Nublado Úmido"
                4 -> "Vento Forte do Pacífico"
                5 -> "Ensolarado Ameno"
                else -> "Céu Claro & Limpo"
            }
            WeatherSeason.SUMMER_JUN_AUG -> when (r6) {
                1 -> "Clima Estranho!"
                2 -> "Calor Abafado & Smog"
                3 -> "Sol Escaldante"
                4 -> "Tempestade Rápida de Verão"
                5 -> "Quente & Ventania"
                else -> "Céu Claro Quente"
            }
            WeatherSeason.AUTUMN_SEP_NOV -> when (r6) {
                1 -> "Clima Estranho!"
                2 -> "Chuva Ácida Leve / Garoa"
                3 -> "Neblina Densa da Baía"
                4 -> "Vento Frio de Outono"
                5 -> "Nublado Cinza"
                else -> "Fresco & Ensolarado"
            }
        }
        if (r6 == 1) {
            val r10 = diceEngine.rollPlainD10()
            val (strangeName, effect) = strangeWeather1d10[(r10 - 1).coerceIn(0, 9)]
            return WeatherRollResult(
                season = season,
                seasonRoll1d6 = r6,
                conditionNamePt = baseLabel,
                strangeRoll1d10 = r10,
                strangeConditionNamePt = strangeName,
                mechanicalEffectPt = effect
            )
        }
        val standardEffect = when {
            baseLabel.contains("Neblina") || baseLabel.contains("Smog") -> "-2 em Percepção além de 50m."
            baseLabel.contains("Chuva") -> "Piso escorregadio (-2 Atletismo/Dirigir em alta velocidade)."
            baseLabel.contains("Vento") -> "-2 em disparos além de 100m."
            else -> "Sem penalidades climáticas."
        }
        return WeatherRollResult(
            season = season,
            seasonRoll1d6 = r6,
            conditionNamePt = baseLabel,
            mechanicalEffectPt = standardEffect
        )
    }

    val perilsOfSalvaging1d6: List<String> = listOf(
        "1: Estrutura desaba nos escombros — sofre 3d6 dano corporal + perda da semana.",
        "2: Emboscada de catadores ou Boostergangers reivindicando a sucata.",
        "3: Armadilha explosiva / gás antigo — Resistir Tortura/Drogas DV15 ou 3d6 direto.",
        "4: Radiação ou toxina concentrada — sofre envenenamento e equipamento corrompido.",
        "5: Patrulha corporativa / NCPD flagra invasão de zona restrita.",
        "6: Cibercão ou Drone Adormecido desperta hostil no perímetro."
    )

    fun rollSalvageAttempt(
        basicTechBase: Int,
        worthTier: SalvageWorthTier,
        specificCategory: SalvageCategory?,
        luckSpent: Int = 0,
        diceEngine: DiceEngine = DiceEngine()
    ): SalvageAttemptResult {
        val targetDv = if (specificCategory != null) {
            worthTier.specificFindDv ?: worthTier.randomFindDv
        } else {
            worthTier.randomFindDv
        }
        val roll = diceEngine.rollD10Exploding()
        val total = basicTechBase + luckSpent.coerceAtLeast(0) + roll.netDieTotal
        val critFail = roll.marker == CriticalMarker.CRITICAL_FAILURE
        val succeeded = !critFail && total > targetDv

        val resolvedCategory = specificCategory ?: when (diceEngine.rollNd6(1).total) {
            1 -> SalvageCategory.CYBERWARE
            2 -> SalvageCategory.WEAPONS
            3, 4 -> SalvageCategory.GENERAL_GEAR
            5 -> SalvageCategory.ARMOR
            else -> SalvageCategory.CYBERDECKS_PROGRAMS
        }

        val peril = if (critFail) {
            val r6 = diceEngine.rollNd6(1).total
            perilsOfSalvaging1d6[(r6 - 1).coerceIn(0, 5)]
        } else null

        return SalvageAttemptResult(
            basicTechBase = basicTechBase,
            rollTotal = total,
            dv = targetDv,
            succeeded = succeeded,
            isCriticalFailure = critFail,
            worthTier = worthTier,
            category = resolvedCategory,
            perilTextPt = peril
        )
    }

    private val dateKeywords = listOf(
        "Cromo Fresco", "Fã de Braindance", "Nômade Viajante", "Netrunner Noturno",
        "Colecionador Kibble", "Viciado em ELO", "Executivo Cínico", "Rockerboy Indie",
        "Médico de Rua", "Motociclista", "Ex-Corporativo", "Amante de Neon",
        "Sucateiro Tech", "Jornalista Investigativo", "Guarda-Costas", "Bioexótico",
        "DJ Subterrâneo", "Tatuado a Laser", "Chef de Bodega", "Corredor de Telhado"
    )

    private val dateLocationsAndActivities = listOf(
        "Totentanz (Maelstrom)" to "Competir na pista industrial ou fugir de uma briga no bar",
        "Afterlife (Watson)" to "Pedir drinque de lenda e trocar contratos sob olhares de Solos",
        "Short Circuit (Heywood)" to "Jogar fliperamas retrô e Elflines Online até tarde",
        "Bodega da Esquina" to "Dividir Kibble gourmet aquecido na calçada observando o trânsito",
        "Píer de Morro Rock" to "Caminhar à beira-mar fugindo do smog corporativo",
        "Night Market Clandestino" to "Caçar achados estranhos e pechinchar com Fixers",
        "Mercado de Japantown" to "Provar macarrão sintético sob lanternas de papel neon",
        "Telhado de Cargo Container" to "Assistir à chuva ácida ou fogos sobre a Zona Executiva",
        "Clube Atlantis Reconstruído" to "Dançar synthwave ao vivo no mezanino",
        "Autódromo das Badlands" to "Apostar corrida com veículos CHOOH² ou bicicletas modificadas"
    )

    private val dateTwists = listOf(
        "1: Um ex furioso aparece armado na entrada do local.",
        "2: O Agent do par toca sem parar com chamados de um Fixer desesperado.",
        "3: Chuva súbita ou blecaute corta a luz do quarteirão.",
        "4: O par é idêntico a um procurado no Screamsheet de hoje.",
        "5: Um Drone Observer segue vocês discretamente à distância.",
        "6: A conta sai o triplo porque o lugar aceitou só cartão corporativo.",
        "7: O par traz um bichinho biogenético ou Cyber-rato no ombro.",
        "8: Alguém reconhece você de um contrato passado.",
        "9: Conversa profunda inesperada sobre o futuro fora de Night City.",
        "10: Convite espontâneo para uma missão ou segunda etapa secreta."
    )

    private val dateAftermaths = listOf(
        "Novo contato confiável adicionado à rede social.",
        "Favor mútuo prometido para a próxima semana.",
        "Boato quente de Night Market compartilhado.",
        "Bloqueio repentino no Datepath sem explicação (Ghosting tardio).",
        "Convite para jogar Elflines Online juntos amanhã.",
        "Presente excêntrico (10eb) deixado no bolso.",
        "Rivais de gangue notam o encontro (complicação leve).",
        "Outro encontro já marcado na agenda."
    )

    fun rollDatepath(diceEngine: DiceEngine = DiceEngine()): DatepathRollResult {
        val kw1 = dateKeywords[(diceEngine.rollPlainD10() + diceEngine.rollPlainD10() - 2).coerceIn(0, dateKeywords.lastIndex)]
        val kw2 = dateKeywords[(diceEngine.rollPlainD10() + diceEngine.rollPlainD10() - 2).coerceIn(0, dateKeywords.lastIndex)]
        val (loc, act) = dateLocationsAndActivities[(diceEngine.rollPlainD10() - 1).coerceIn(0, dateLocationsAndActivities.lastIndex)]
        val outRoll = diceEngine.rollPlainD10()
        val outcome = when (outRoll) {
            in 1..4 -> "Bom Encontro (Química & Boa Avaliação)"
            in 5..8 -> "Encontro Estranho (Complicação Bizarra)"
            else -> "Ghosted / Desapareceu antes da conta!"
        }
        val twist = dateTwists[(diceEngine.rollPlainD10() - 1).coerceIn(0, dateTwists.lastIndex)]
        val aftermath = dateAftermaths[(diceEngine.rollNd6(1).total - 1).coerceIn(0, dateAftermaths.lastIndex)]
        return DatepathRollResult(kw1, kw2, loc, act, outcome, twist, aftermath)
    }

    fun bicycleSpeedForBody(body: Int): Pair<Int, Int> = when {
        body < 4 -> 8 to 12
        body <= 7 -> 10 to 20
        else -> 15 to 30
    }

    val cubeHotels1d10: List<String> = listOf(
        "CH1: Cápsula mínima em bloco superlotado (500eb/mês).",
        "CH2: Cubo sem janela perto do metrô elevado (barulho constante).",
        "CH3: Quarto plástico higiênico com fechadura Agent.",
        "CH4: Cubo cápsula em Japantown com Vendit no corredor.",
        "CH5: Bloco modular compartilhado com banheiros coletivos.",
        "CH6: Hotel cápsula temático retro-anni 2020.",
        "CH7: Cubo apertado porém bem isolado de chuva ácida.",
        "CH8: Cápsula com tela Braindance embutida (corta sinal à meia-noite).",
        "CH9: Unidade próxima ao porto — umidade e cheiro de óleo.",
        "CH10: Cubo premium com Wi-Fi CitiNet estável."
    )

    val cargoContainers1d10: List<String> = listOf(
        "CC1: Container padrão cinza na Zona de Combate (1.000eb/mês).",
        "CC2: Duplo empilhado com escada externa e vista p/ smog.",
        "CC3: Antigo módulo frigorífico com ótimo isolamento térmico.",
        "CC4: Container grafitado por Maelstrom; vizinhos barulhentos.",
        "CC5: Unidade reforçada perto de oficina Nômade.",
        "CC6: Container com gerador solar improvisado no teto.",
        "CC7: Módulo de carga Continental Brands adaptado.",
        "CC8: Container próximo a mercado noturno frequente.",
        "CC9: Unidade discreta num pátio protegido por câmeras.",
        "CC10: Container luxuoso com mobília Chic e Smart Vanity."
    )

    val eloSummaryAndMonstersPt: List<String> = listOf(
        "Elflines Online (ELO): MMO de fantasia dentro do CitiNet (50 pts Atributo / 60 pts Perícia, sem Sorte). Ranks 0–10; morte cobra 2.000gp ou Revive Sickness. 1eb = 100gp.",
        "10 Títulos ELO: Sábio, Mestre do Arco, Dançarino da Lâmina, Mãos Ágeis, Coração Quente, Sangue Selvagem, Filho do Vento, Escudo de Casca, Druida, Andarilho.",
        "Monstros (9): Bearwolf (40 PV/PA5), Cursed Head (10 PV), Draglin (25 PV/PA7), Dragon (70 PV/PA10), Golem (70 PV/PA7), Gremlin (25 PV/PA5), Restless Spirit (50 PV), Slime (30 PV), Zazzolif (35 PV/PA10 imune ablação).",
        "TCG Física (Vol 3): Booster 10eb | Box 100eb | Cartas Avulsas Mixaria (5eb) | Arma Duel Caster dispara cartas reais/TCG."
    )
}
