package com.example.core.rules

enum class EncounterTimeOfDay(val labelPt: String, val subtitlePt: String) {
    DAYTIME("Dia", "Ruas corporativas, trânsito comercial e vigília diurna"),
    EVENING("Noite", "Clubes, becos iluminados por neon e gangues em patrulha"),
    MIDNIGHT("Madrugada", "Combate pesado, guerra de gangues e Ciberpsicopatas")
}

data class OfficialEncounterEntry(
    val range: IntRange,
    val titlePt: String,
    val descriptionPt: String,
    val suggestedNpcId: String? = null,
    val suggestedNpcCount: Int = 1
)

data class EncounterRollResult(
    val timeOfDay: EncounterTimeOfDay,
    val d100Roll: Int,
    val entry: OfficialEncounterEntry,
    val resolvedNpc: OfficialNpcSpec?
)

data class NightCityZoneSpec(
    val namePt: String,
    val threatRatingPt: String,
    val descriptionPt: String,
    val neighborhoodsPt: String
)

data class GangSpec(
    val name: String,
    val categoryPt: String,
    val summaryPt: String
)

data class CorpSpec(
    val name: String,
    val tierPt: String,
    val focusPt: String
)

data class BeatChartStep(
    val beatNamePt: String,
    val rulePt: String
)

/**
 * Canonical Part 2 Random Encounters (pp. 417–424) & Night City 2045 Lore (pp. 267–318, 395).
 */
object OfficialEncountersAndLoreCatalog {

    val daytimeEncounters: List<OfficialEncounterEntry> = listOf(
        OfficialEncounterEntry(1..5, "Polícia Local (NCPD)", "Patrulha diurna checando identidades e ocorrências locais.", "security_operative", 2),
        OfficialEncounterEntry(6..11, "Guardas Corporativos", "Equipe tática de segurança escoltando carga ou instalação Corp.", "security_operative", 2),
        OfficialEncounterEntry(12..13, "Técnicos (Techs)", "Equipe de manutenção reparando linhas elétricas, antenas ou megaprédios."),
        OfficialEncounterEntry(14..17, "Detetive Particular", "Investigador em campana observando movimento ou seguindo um alvo."),
        OfficialEncounterEntry(18..20, "Executivos Corporativos", "Execs com escolta discreta atravessando a rua em direção a reunião.", "bodyguard", 2),
        OfficialEncounterEntry(21..27, "Moradores Locais / Red Chrome Legion / Inquisitors", "Multidão local agitada ou membros disfarçados de cultos/gangues.", "boosterganger", 2),
        OfficialEncounterEntry(28..32, "Reclaimers", "Grupo organizado recuperando sucata e reconstruindo habitações.", "reclaimer_chief", 1),
        OfficialEncounterEntry(33..37, "Mídias (Repórteres)", "Jornalista independente com câmera de bolso buscando furo nas ruas."),
        OfficialEncounterEntry(38..41, "Detetive Particular (Investigação Ativa)", "PI interceptando suspeitos ou colhendo depoimentos com dinheiro."),
        OfficialEncounterEntry(42..46, "Trauma Team", "AV-4 blindado descendo em zona de risco para extração médica de cliente."),
        OfficialEncounterEntry(47..57, "Scavvers (Catadores)", "Desabrigados desesperados vasculhando escombros em busca de metal/cromo.", "boosterganger", 2),
        OfficialEncounterEntry(58..63, "Nômades", "Motoristas de comboio negociando peças ou reabastecendo suprimentos.", "road_ganger", 2),
        OfficialEncounterEntry(64..70, "Gangue Festeira (Piranhas)", "Bando barulhento bebendo Smash, procurando briga ou alvo fácil.", "boosterganger", 2),
        OfficialEncounterEntry(71..76, "Punks de Rua", "Jovens armados testando limites ou cobrando pedágio no quarteirão.", "boosterganger", 2),
        OfficialEncounterEntry(77..82, "Cultistas (Reckoners)", "Pregadores apocalípticos tentando recrutar ou colher 'doações' à força.", "boosterganger", 2),
        OfficialEncounterEntry(83..88, "Caminhão Nômade (Steel Vaqueros)", "Veículo pesado blindado escoltado por batedores armados.", "outrider", 1),
        OfficialEncounterEntry(89..94, "Boostergang (Iron Sights)", "Membros bem equipados e fortemente cromados patrulhando território.", "boosterganger", 3),
        OfficialEncounterEntry(95..100, "Grande Criminoso (Sindicato Vilshenko)", "Executor profissional e escolta cobrando dívidas ou eliminando alvos.", "security_officer", 1)
    )

    val eveningEncounters: List<OfficialEncounterEntry> = listOf(
        OfficialEncounterEntry(1..5, "Polícia da Cidade (NCPD)", "Viatura armada em ronda noturna com holofote e drones.", "security_officer", 1),
        OfficialEncounterEntry(6..11, "Guardas Corporativos", "Guarnição noturna reforçada patrulhando perímetro de alta segurança.", "security_operative", 2),
        OfficialEncounterEntry(12..13, "Técnicos Corporativos", "Especialistas em reparo noturno acompanhados por escolta.", "security_operative", 1),
        OfficialEncounterEntry(14..17, "Detetive Particular", "Investigador operando nas sombras atrás de pistas e subornos."),
        OfficialEncounterEntry(18..20, "Executivos Corporativos", "Execs saindo de clubes exclusivos sob proteção de guarda-costas.", "bodyguard", 2),
        OfficialEncounterEntry(21..25, "Rockerboys", "Artista rebelde cercado de fãs após show improvisado na rua."),
        OfficialEncounterEntry(26..30, "Mídias", "Equipe noturna cobrindo incêndio, protesto ou tiroteio recente."),
        OfficialEncounterEntry(31..33, "Philharmonic Vampyres", "Gangue de pregadores de peça e capas noturnas aprontando confusão.", "boosterganger", 2),
        OfficialEncounterEntry(34..40, "Locais / Piranhas / Inquisitors / Fixer", "Movimentação noturna intensa em torno de bares, bodegas e pontos de encontro."),
        OfficialEncounterEntry(41..46, "Netrunner Errante", "Hacker usando óculos de virtualidade tentando acesso clandestino em ponto local.", "netrunner_npc", 1),
        OfficialEncounterEntry(47..52, "Nômades (Roadrunners)", "Motoqueiros e batedores em parada noturna ou entrega rápida.", "road_ganger", 2),
        OfficialEncounterEntry(53..58, "Punks de Rua", "Bando agitado após cair a noite em becos sem iluminação.", "boosterganger", 2),
        OfficialEncounterEntry(59..63, "Trauma Team", "Equipe médica tática evacuação urgente sob fogo cruzado."),
        OfficialEncounterEntry(64..69, "Chromers", "Fanáticos por implantes exibindo cromo brilhante e buscando confusão.", "boosterganger", 2),
        OfficialEncounterEntry(70..71, "Equipe de Assassinos Solo", "Profissionais silenciosos caçando contrato específico na região.", "outrider", 1),
        OfficialEncounterEntry(72..77, "Boostergang (Iron Sights)", "Patrulha agressiva fechando a rua com rifles e veículos modificados.", "boosterganger", 3),
        OfficialEncounterEntry(78..83, "Equipe de Solos", "Mercenários veteranos prontos para emboscada ou extração tática.", "outrider", 1),
        OfficialEncounterEntry(84..90, "Boostergang (Piranhas c/ Kerenzikov)", "Festeiros hiperativos sob efeito de drogas e reflexos ampliados.", "boosterganger", 3),
        OfficialEncounterEntry(91..93, "Grande Criminoso (Scagattalia)", "Capo do crime organizado com tenentes armados fechando negócio.", "security_officer", 1),
        OfficialEncounterEntry(94..100, "Tiroteio Aberto (Maelstrom vs Red Chrome Legion)", "Guerra de rua sangrenta entre gangues rivais bloqueando a via.", "pyro", 1)
    )

    val midnightEncounters: List<OfficialEncounterEntry> = listOf(
        OfficialEncounterEntry(1..10, "Polícia da Cidade (NCPD)", "Unidades pesadas respondendo a código vermelho na madrugada.", "security_officer", 1),
        OfficialEncounterEntry(11..22, "Guardas Corporativos", "Ronda letal em modo alerta máximo com tolerância zero.", "security_operative", 3),
        OfficialEncounterEntry(23..24, "Detetive Particular", "Investigação arriscada de madrugada procurando testemunhas ou corpos."),
        OfficialEncounterEntry(25..25, "Mídias", "Repórter solitário gravando atividade ilegal no Combat Zone."),
        OfficialEncounterEntry(26..29, "Chromers", "Gangue pesadamente cibernética à caça de peças e vítimas.", "boosterganger", 3),
        OfficialEncounterEntry(30..39, "Equipe de Edgerunners", "Grupo rival executando missão paralela na mesma área.", "outrider", 1),
        OfficialEncounterEntry(40..42, "Trauma Team", "Resgate de alto risco na calada da noite com cobertura de metralhadora."),
        OfficialEncounterEntry(43..45, "Ranger (Homem da Lei)", "Oficial solitário ou patrulha de perímetro em terreno hostil.", "security_officer", 1),
        OfficialEncounterEntry(46..58, "Nômades (Wildman Pack)", "Comboio feroz cruzando a madrugada em alta velocidade.", "road_ganger", 3),
        OfficialEncounterEntry(59..63, "Cultistas (Inquisitors)", "Fanáticos anti-cromo realizando expurgo noturno violento.", "boosterganger", 3),
        OfficialEncounterEntry(64..73, "Punks (Viciados em Black Lace)", "Usuários em surto violento imunes ao medo sob efeito de Black Lace.", "boosterganger", 3),
        OfficialEncounterEntry(74..74, "Grande Criminoso (Scagattalia)", "Execução silenciosa ou transporte ilícito na madrugada.", "security_officer", 1),
        OfficialEncounterEntry(75..79, "Guerra de Gangues I (Tyger Claws / 6th St / Piranhas / Iron Sights / NCPD)", "Confronto múltiplo pelo controle do quarteirão.", "security_officer", 1),
        OfficialEncounterEntry(80..87, "Incendiários (Pyro + Boosters)", "Pyro liderando boostergangers incendiando prédio ou bloqueio.", "pyro", 1),
        OfficialEncounterEntry(88..92, "Guerra de Gangues II (Bozos / Maelstrom / Prime-Time / Voodoo Boys / Red Chrome)", "Combate brutal entre facções fanáticas na zona de guerra.", "boosterganger", 4),
        OfficialEncounterEntry(93..99, "Grande Criminoso (6 Solos Scagattalia)", "Esquadrão de elite do sindicato em missão de extermínio.", "outrider", 2),
        OfficialEncounterEntry(100..100, "FÚRIA DE CIBERPSICOPATA", "Ciberpsicopata totalmente fora de controle devastando tudo ao redor!", "cyberpsycho", 1)
    )

    fun rollEncounter(timeOfDay: EncounterTimeOfDay, diceEngine: DiceEngine): EncounterRollResult {
        val d100 = diceEngine.rollD100().coerceIn(1, 100)
        val table = when (timeOfDay) {
            EncounterTimeOfDay.DAYTIME -> daytimeEncounters
            EncounterTimeOfDay.EVENING -> eveningEncounters
            EncounterTimeOfDay.MIDNIGHT -> midnightEncounters
        }
        val entry = table.first { d100 in it.range }
        val npc = entry.suggestedNpcId?.let { OfficialNpcCatalog.findByEnglishName(it) }
        return EncounterRollResult(timeOfDay, d100, entry, npc)
    }

    val threatRatings: List<Pair<String, String>> = listOf(
        "Executive (Executiva)" to "Áreas fechadas para altos executivos; segurança privada letal contra invasores.",
        "Corporate (Corporativa)" to "Patrulhada por segurança Corp; segura contra caos de rua, letal em escala corporativa.",
        "Moderate (Moderada)" to "Bairros ao redor das zonas Corp; segurança relativa, mas crimes das Combat Zones vazam.",
        "Combat (Zona de Combate)" to "Piores lugares da cidade; gangues mandam e não há polícia nem limpeza.",
        "Outskirts (Periferia / Estrada)" to "Cidades vizinhas e estradas abertas patroladas por Nômades e Reclaimers."
    )

    val nightCityZones: List<NightCityZoneSpec> = listOf(
        NightCityZoneSpec("Hot Zone (Zona Quente)", "Combat", "Cratera da bomba nuclear de 2023; prédios em ruínas, veículos queimados e Old Bank Block.", "The Old Bank Block, Hot Zone Wasteland"),
        NightCityZoneSpec("Centro Urbano em Reconstrução", "Corporate / Moderate", "Prédios governamentais, arranha-céus ativos e megaprédios em construção.", "The Glen, Little Europe, Upper Marina, University District, Watson Development"),
        NightCityZoneSpec("Zona Executiva", "Executive", "Condomínios fechados estilo 'Beaverville' ligados ao centro por maglev.", "New Westbrook Beaverville, Executive Estates"),
        NightCityZoneSpec("Combat Zones (Zonas de Combate)", "Combat", "Domínio absoluto das gangues pós-4ª Guerra Corporativa.", "Old Japantown, Little China, Old Combat Zone, South Night City"),
        NightCityZoneSpec("Subúrbios Superlotados", "Moderate / Combat", "Acampamentos de refugiados, zonas industriais e novos megaprédios.", "Heywood, Heywood Industrial Zone, New Westbrook, Rancho Coronado"),
        NightCityZoneSpec("Perímetro Reivindicado", "Outskirts", "Cidades vizinhas recolonizadas por Reclaimers com apoio de Nômades.", "Atascadero, Avila Beach, Cambria, Los Osos, Paso Robles, Pismo Beach, San Luis Obispo, San Simeon"),
        NightCityZoneSpec("A Estrada Aberta (The Open Road)", "Outskirts", "Rodovias interestaduais controladas por comboios Nômades contra gogangs.", "CA Routes 41, 46, 58, 99, 828; I-5; I-101")
    )

    val nightCityGangs: List<GangSpec> = listOf(
        GangSpec("6th Street", "Vigilante", "Veteranos da 4ª Guerra que protegem bairros cobrando taxas."),
        GangSpec("The Bozos", "Brincalhões Letais", "Palhaços biossultados cujo humor envolve armadilhas fatais."),
        GangSpec("Inquisitors", "Culto Anti-Cromo", "Fanáticos religiosos que odeiam implantes cibernéticos."),
        GangSpec("Iron Sights", "Combat Gang", "Gangue armada ligada ao tráfico e confronto direto em Combat Zones."),
        GangSpec("Maelstrom", "Combat Gang", "Fissurados em cromo extremo, rivais mortais dos Inquisitors."),
        GangSpec("Philharmonic Vampyres", "Pranksters", "Artistas excêntricos com presas e capas que atuam à noite."),
        GangSpec("Piranhas", "Party Gang", "Bebem Smash, traficam drogas de festa e assaltam por diversão."),
        GangSpec("The Prime-Time Players", "Poser Gang", "Biossultados como personagens de sitcoms antigas defendendo o bairro."),
        GangSpec("Reckoners", "Culto Apocalíptico", "Pregam o Juízo Final e coletam 'doações' de vítimas inconscientes."),
        GangSpec("Red Chrome Legion", "Grupo de Ódio", "Gangue militarista extremista uniformizada em vermelho."),
        GangSpec("Scavvers", "Catadores", "Desabrigados desesperados saqueando cobre, armas e implantes nos escombros."),
        GangSpec("Steel Vaqueros", "Pacote Nômade", "Comboios de Salinas que negociam suprimentos com Reclaimers."),
        GangSpec("Tyger Claws", "Protetora / Ásia", "Independentes pós-Arasaka em Japantown; motos rápidas e artes marciais."),
        GangSpec("Voodoo Boys", "Crime / Terror", "Gangue envolvida com drogas não-sintéticas, em choque com refugiados haitianos.")
    )

    val majorCorporations: List<CorpSpec> = listOf(
        CorpSpec("Arasaka", "MegaCorp (Oculta)", "Segurança militar e influência secreta no mercado americano."),
        CorpSpec("Biotechnica", "MegaCorp", "Detentora da patente do CHOOH2 e bioengenharia de espécies."),
        CorpSpec("Continental Brands", "MegaCorp", "Dona das lojas Oasis e do programa de fidelidade Kibble Queen."),
        CorpSpec("Danger Girl", "Inteligência/PI", "Agência de investigação de Michiko Sanderson operando contra células Arasaka."),
        CorpSpec("Militech", "MegaCorp", "Gigante bélica dominante nos EUA pós-4ª Guerra Corporativa."),
        CorpSpec("Network 54", "Mídia", "Maior rede de notícias e mídia de Night City."),
        CorpSpec("Petrochem", "MegaCorp", "Maior produtora de CHOOH2 e energia petroquímica."),
        CorpSpec("Rocklin Augmentics", "Cyberware", "Líder inovadora em membros e implantes cibernéticos de alta performance."),
        CorpSpec("SovOil", "Energia", "Gigante petrolífera neo-soviética e fornecedora de combustível."),
        CorpSpec("Trauma Team International", "Médica/Tática", "Extração médica armada por assinatura mensal em minutos."),
        CorpSpec("Zhirafa", "Robótica", "Drones e mecanoides pesados de construção e segurança (CEO Artyom Sokolov)."),
        CorpSpec("Ziggurat", "Telecom/NET", "Controla o CitiNet local e o Data Pool de Night City.")
    )

    val secondTierCorporations: List<CorpSpec> = listOf(
        CorpSpec("Constitution Arms", "2º Escalão", "Fabricante de armamento extremamente pesado."),
        CorpSpec("GunMart", "2º Escalão", "Armas baratas de baixa qualidade vendidas até em máquinas."),
        CorpSpec("Kendachi Arms", "2º Escalão", "Fabricante japonesa especialista em lâminas monomoleculares."),
        CorpSpec("Kiroshi Optics", "2º Escalão", "Líder mundial no design de ciberópticos e lentes táticas."),
        CorpSpec("Merrill, Asukaga & Finch", "2º Escalão", "Consultoria financeira e investimentos exclusivos."),
        CorpSpec("Raven Microcybernetics", "2º Escalão", "Antiga gigante de cibernética hoje desafiada pela Rocklin."),
        CorpSpec("Thornton Motor Company", "2º Escalão", "Veículos terrestres robustos e extremamente duráveis."),
        CorpSpec("WorldSat CommNet", "2º Escalão", "Comunicações internacionais A/V diretas entre cidades."),
        CorpSpec("Zetatech", "2º Escalão", "Software, ciberdecks e maquinário pesado industrial.")
    )

    val beatChartGuide: List<BeatChartStep> = listOf(
        BeatChartStep("1. Gancho (Hook)", "Cena inicial curta de ação ou suspense que fisga o protagonista imediatamente."),
        BeatChartStep("2. Desenvolvimento (Development)", "Pistas, encontros sociais ou investigações que revelam novas informações."),
        BeatChartStep("3. Cliffhanger", "Reviravolta, emboscada ou escolha urgente que mantém a tensão alta."),
        BeatChartStep("4. Clímax (Climax)", "Confronto decisivo final ou momento de verdade do contrato."),
        BeatChartStep("5. Resolução (Resolution)", "Consequência, pagamento (eb), I.P. concedido e ganchos futuros. Regra prática: 1 Beat ≈ 30 min.")
    )
}
