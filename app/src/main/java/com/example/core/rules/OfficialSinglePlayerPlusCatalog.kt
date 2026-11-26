package com.example.core.rules

enum class SoloPlusTableCategory(val labelPt: String) {
    CHARACTER("Personagem & Carreira"),
    ENCOUNTER("Encontros & Viagem"),
    THING("Objetos & Escutas"),
    ATMOSPHERE("Clima & Atmosfera"),
    MECHANICAL("Mecânica & Combate"),
    MEDICAL("Médico & Cirurgia")
}

data class SoloPlusTableSpec(
    val id: String,
    val titlePt: String,
    val dieSides: Int,
    val dieLabel: String,
    val category: SoloPlusTableCategory,
    val rows: List<String>
)

data class SoloPlusRollResult(
    val tableId: String,
    val tableTitlePt: String,
    val dieLabel: String,
    val rollValue: Int,
    val resultTextPt: String
)

data class SoloNpcPersonalityBundle(
    val motivationRoll: Int,
    val motivationPt: String,
    val competenceRoll: Int,
    val competencePt: String,
    val aggressionRoll: Int,
    val aggressionPt: String
)

data class SoloOracleYesNoResult(
    val questionPt: String,
    val oddsLabelPt: String,
    val targetDv: Int,
    val roll1d10: Int,
    val answerPt: String,
    val twistPt: String?
)

/**
 * Complete Single Player Mode+ (RTG-CPR-DLC-SinglePlayerModePlus v1.01, 6 pp.):
 * All 22 Solo Random Tables + Highrider 1d100 Careers + Solo Closed-Question Oracle!
 */
object OfficialSinglePlayerPlusCatalog {

    val allTables: List<SoloPlusTableSpec> = listOf(
        // === CHARACTER (6 tables) ===
        SoloPlusTableSpec(
            id = "npc_motivation",
            titlePt = "Motivação do NPC",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.CHARACTER,
            rows = listOf(
                "1: Dinheiro acima de tudo — aceitará riscos ou traições por eurodólares.",
                "2: Sobrevivência imediata — quer apenas escapar inteiro da cena.",
                "3: Lealdade à facção, gangue ou empregador corporativo.",
                "4: Vingança pessoal contra um rival, gangue ou corporação.",
                "5: Ganância por tecnologia, cyberware ou informação sigilosa.",
                "6: Medo ou chantagem — está agindo contra a vontade sob ameaça.",
                "7: Reputação e ego — quer provar superioridade nas ruas.",
                "8: Proteger alguém querido (família, amante ou aliado próximo).",
                "9: Curiosidade ou tédio — busca adrenalina e caos.",
                "10: Fanatismo ideológico, religioso ou político."
            )
        ),
        SoloPlusTableSpec(
            id = "npc_competence",
            titlePt = "Competência do NPC",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.CHARACTER,
            rows = listOf(
                "1–2: Incompetente / Novato — comete erros táticos e subestima perigos.",
                "3–5: Padrão das Ruas — competente no básico, sem brilho especial.",
                "6–8: Profissional Treinado — coordena ações, usa cobertura e recarrega certo.",
                "9: Veterano Endurecido — aplica flanqueamento, granadas e alvos prioritários.",
                "10: Elite / Lenda Local — implacável, otimizado (use estatísticas Hardened/Boss)."
            ).let { summary ->
                listOf(
                    summary[0], summary[0],
                    summary[1], summary[1], summary[1],
                    summary[2], summary[2], summary[2],
                    summary[3], summary[4]
                )
            }
        ),
        SoloPlusTableSpec(
            id = "npc_aggression",
            titlePt = "Agressividade do NPC",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.CHARACTER,
            rows = listOf(
                "1–2: Passivo / Evasivo — evita confronto e se rende sob pressão.",
                "3–4: Cauteloso — negocia ou ameaça antes de sacar arma.",
                "5–6: Reativo — mantém distância e só atira se provocado.",
                "7–8: Hostil — busca vantagem rápida e abre fogo sem aviso.",
                "9–10: Feroz / Ciberpsicótico — ataca até a morte ou destruição total."
            ).let { s ->
                listOf(s[0], s[0], s[1], s[1], s[2], s[2], s[3], s[3], s[4], s[4])
            }
        ),
        SoloPlusTableSpec(
            id = "hate_organization",
            titlePt = "Por Que Odeiam a Organização?",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.CHARACTER,
            rows = listOf(
                "1: Demissão injusta ou queima de arquivo após anos de serviço.",
                "2: Parente ou parceiro morto/desaparecido em experimento ou operação deles.",
                "3: Dívida impagável ou contrato abusivo de cyberware/empréstimo.",
                "4: Roubaram sua pesquisa, patente, propriedade ou território.",
                "5: Foi usado como bode expiatório em um crime que não cometeu.",
                "6: Razão ideológica — a organização destruiu sua vizinhança ou comunidade."
            )
        ),
        SoloPlusTableSpec(
            id = "highrider_careers",
            titlePt = "Carreiras Orbitais (Highrider 1d100)",
            dieSides = 100,
            dieLabel = "1d100",
            category = SoloPlusTableCategory.CHARACTER,
            rows = listOf(
                "01–10: Técnico de Suporte de Vida / Reciclagem de Ar e Água.",
                "11–20: Operador de Mineração de Asteroides / Massa Zero.",
                "21–30: Piloto de Ônibus Orbital / Transferência Terra-Órbita.",
                "31–40: Trabalhador de Construção em Gravidade Zero (Zero-G Rigger).",
                "41–50: Cientista Corporativo de Laboratório Orbital (Biotech/Nanotech).",
                "51–60: Segurança de Estação / Fuzileiro Espacial Corporativo.",
                "61–70: Contrabandista Orbital / Despachante do Mercado Negro Espacial.",
                "71–80: Médico Espacial (Medtech especializado em perda óssea e descompressão).",
                "81–90: Netrunner de Rede Orbital / Operador de Satélites WorldSat.",
                "91–100: Executivo de Alto Escalão da Highrider Confederation ou Orbital Air."
            )
        ),
        SoloPlusTableSpec(
            id = "ai_intelligence",
            titlePt = "Níveis de Inteligência de IA",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.CHARACTER,
            rows = listOf(
                "1–2: IA Restrita (Burra) — segue apenas scripts rígidos e comandos literais.",
                "3–4: IA Especializada — brilhante numa tarefa (tráfego, banco), cega ao resto.",
                "5–6: Pseudo-Intelecto Corporativo — simula personalidade humana convincente.",
                "7–8: Inteligência Emergente — demonstra iniciativa própria, curiosidade e sigilo.",
                "9: IA Transcendental Fragmentada — fragmento da Velha Rede além da Blackwall.",
                "10: IA Transcendental Completa — entidade semi-divina capaz de manipular redes inteiras."
            ).let { s ->
                listOf(s[0], s[0], s[1], s[1], s[2], s[2], s[3], s[3], s[4], s[5])
            }
        ),

        // === ENCOUNTER (5 tables) ===
        SoloPlusTableSpec(
            id = "enc_subway",
            titlePt = "Encontros no Metrô (NCART)",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.ENCOUNTER,
            rows = listOf(
                "1: Batedor de carteiras tenta furtar um passageiro distraído ou o Edgerunner.",
                "2: Gangue jovem pichando o vagão e cobrando 'pedágio' de passagem.",
                "3: Pregador cibernético gritando profecias do apocalipse da Velha Rede.",
                "4: Falha de energia apaga as luzes e trava as portas por 1d6 minutos.",
                "5: Patrulha do NCPD realiza revista aleatória por armas e drogas.",
                "6: Casal corporativo discutindo em voz alta segredo de projeto sigiloso.",
                "7: Vendedor ambulante oferece Memory Chips falsificados ou Kibble quente.",
                "8: Ciberpsicopata em início de surto começa a rasgar bancos do vagão.",
                "9: Correio Nômade carregando maleta trancada com guarda-costas discreto.",
                "10: Viagem estranhamente silenciosa — um drone de vigilância paira no corredor."
            )
        ),
        SoloPlusTableSpec(
            id = "enc_transport",
            titlePt = "Encontros em Transporte Coletivo",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.ENCOUNTER,
            rows = listOf(
                "1: Atraso mecânico exige transferência num ponto perigoso controlado por gangue.",
                "2: Passageiro ao lado está gravemente ferido tentando disfarçar o sangramento.",
                "3: Controle corporativo bloqueia a via para passagem de comboio VIP.",
                "4: Alguém esqueceu uma mochila suspeita apitando levemente sob o assento.",
                "5: Briga violenta explode entre passageiros por um assento vazio.",
                "6: Olheiro de Fixer reconhece o Edgerunner e oferece informação rápida."
            )
        ),
        SoloPlusTableSpec(
            id = "enc_road",
            titlePt = "Encontros na Estrada Aberta",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.ENCOUNTER,
            rows = listOf(
                "1: Bloqueio improvisado de Gangers de Estrada exigindo combustível ou armas.",
                "2: Comboio Nômade acampado reparando um veículo blindado avariado.",
                "3: Carcaça incendiada de carro corporativo com sobrevivente pedindo socorro.",
                "4: Tempestade de poeira vermelha reduz visibilidade para poucos metros.",
                "5: Patrulha de segurança da Militech / Petrochem escoltando caminhão-tanque.",
                "6: Corrida ilegal em alta velocidade cruza a pista causando caos."
            )
        ),
        SoloPlusTableSpec(
            id = "enc_corp_building",
            titlePt = "Encontros em Prédio Corporativo",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.ENCOUNTER,
            rows = listOf(
                "1: Equipe de faxina noturna empurrando carrinhos bloqueia o corredor.",
                "2: Executivo atrasado no telefone quase esbarra ao virar a esquina.",
                "3: Técnico de manutenção reiniciando um terminal próximo ao ponto de acesso NET.",
                "4: Guarda de segurança conferindo crachás biométricos na saída do elevador.",
                "5: Drone de patrulha varrendo o andar com câmera infravermelha.",
                "6: Alarme silencioso de incêndio ou teste de evacuação gera confusão breve."
            )
        ),
        SoloPlusTableSpec(
            id = "enc_high_seas",
            titlePt = "Clima em Alto Mar",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.ENCOUNTER,
            rows = listOf(
                "1: Mar calmo e neblina densa tóxica (visibilidade reduzida à metade).",
                "2: Ventos fortes e ondas de 3m (-2 em tiros à distância e Pilotar Aquático).",
                "3: Chuva ácida corrosiva obriga tripulação a buscar abrigo ou vestir proteção.",
                "4: Maré vermelha de algas bioluminescentes da Biotechnica bloqueia hélice.",
                "5: Tempestade elétrica violenta interfere em rádios e sensores de longo alcance.",
                "6: Céu limpo e ensolarado raro com brisa constante e visibilidade perfeita."
            )
        ),

        // === THING (2 tables) ===
        SoloPlusTableSpec(
            id = "random_junk",
            titlePt = "Lixo & Bugigangas Aleatórias",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.THING,
            rows = listOf(
                "1: Embalagem vazia de Kibble sabor carne sintética e lata de Smash.",
                "2: Memory Chip rachado com 1d6 fragmentos de áudio corrompidos.",
                "3: Celular descartável com bateria em 3% e uma única chamada recente.",
                "4: Ciberdedo ou prótese barata quebrada suja de óleo CHOOH².",
                "5: Folheto ensanguentado de clínica de Ripperdoc com desconto.",
                "6: Pente de pistola vazio de baixa qualidade.",
                "7: Crachá corporativo vencido com foto raspada.",
                "8: Isqueiro de plástico com logo de uma banda Chrome Rock esquecida.",
                "9: Chave mecânica enferrujada ligada a um chaveiro de motel de contêiner.",
                "10: Nota escrita às pressas com um código de porta de 4 dígitos."
            )
        ),
        SoloPlusTableSpec(
            id = "listening_device",
            titlePt = "Onde Está a Escuta Oculta?",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.THING,
            rows = listOf(
                "1: Colada na parte inferior da mesa ou cadeira principal.",
                "2: Dentro da moldura do espelho ou quadro da parede.",
                "3: No filtro da máscara, bocal do rádio ou corpo do Agent do alvo.",
                "4: Escondida dentro da grelha de ventilação ou ar-condicionado.",
                "5: Dentro de um vaso de planta sintética ou objeto decorativo.",
                "6: Disfarçada como parafuso na dobradiça da porta.",
                "7: Dentro do adaptador elétrico ou luminária de LED.",
                "8: Implantada em um animal morto/robótico ou drone miniaturizado.",
                "9: No forro da jaqueta ou sola do sapato de alguém na sala.",
                "10: Microfone a laser apontado da janela do prédio vizinho!"
            )
        ),

        // === ATMOSPHERE (3 tables) ===
        SoloPlusTableSpec(
            id = "cyberpunk_life",
            titlePt = "Elementos da Vida Cyberpunk",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.ATMOSPHERE,
            rows = listOf(
                "1: Outdoors holográficos piscando anúncios de Mr. Studd e Continental Brands.",
                "2: Vapor químico escapando de bueiros sob luz neon rosa e ciano.",
                "3: Ambulância da Trauma Team cortando o céu com sirenes estridentes.",
                "4: Barraca de macarrão sintético fervendo sob chuva leve alaranjada.",
                "5: Músico de rua usando braço cibernético como sintetizador improvisado.",
                "6: Jovens conectados em Óculos de Virtualidade ignorando o mundo ao redor.",
                "7: Bando de pombos mutantes ou drones-limpeza revirando o lixo.",
                "8: Becos bloqueados por fitas holográficas de cena de crime do NCPD.",
                "9: Propaganda sonora de recrutamento corporativo ecoando em alto-falantes.",
                "10: Cheiro forte de ozônio, óleo CHOOH² e comida frita saturando o ar."
            )
        ),
        SoloPlusTableSpec(
            id = "hot_night",
            titlePt = "Uma Noite Quente na Cidade",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.ATMOSPHERE,
            rows = listOf(
                "1: Blefe elétrico deixa metade do quarteirão às escuras exceto geradores.",
                "2: Hidrante quebrado vira festa improvisada com música alta na calçada.",
                "3: Calor úmido sufocante faz jaquetas blindadas parecerem fornos (-1 conforto).",
                "4: Enxame de drones publicitários disputa espaço iluminando o céu.",
                "5: Polícia faz vista grossa enquanto fogos e disparos celebram algo distante.",
                "6: Racha noturno atravessa a avenida levantando faíscas no asfalto.",
                "7: Clínica de Ripperdoc mantém fila até a esquina sob luz vermelha.",
                "8: Mercado Noturno relâmpago abre por apenas duas horas num galpão.",
                "9: Vento quente traz cinzas e fumaça de incêndio na Zona de Combate.",
                "10: Todos os bares estão lotados e informações circulam por bebidas baratas."
            )
        ),
        SoloPlusTableSpec(
            id = "sounds_night",
            titlePt = "Sons na Noite",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.ATMOSPHERE,
            rows = listOf(
                "1: Rajada curta de SMG seguida pelo silêncio imediato de passos correndo.",
                "2: Zumbido grave de motores AV-4 passando baixo sobre os telhados.",
                "3: Sirene distante do NCPD ou alarme de loja arrombada.",
                "4: Graves distorcidos de Chrome Rock vazando de uma boate subterrânea.",
                "5: Pingos pesados de chuva ácida batendo em metal corrugado.",
                "6: Gritos em Gíria das Ruas durante uma discussão acalorada num beco.",
                "7: Ronco metálico de motos passando em alta rotação na ponte.",
                "8: Estalos elétricos de um poste em curto-circuito soltando faíscas.",
                "9: Risada aguda e mecânica vinda da escuridão próxima.",
                "10: Voz robótica de um Agent lembrando alguém de pagar o aluguel."
            )
        ),

        // === MECHANICAL (4 tables) ===
        SoloPlusTableSpec(
            id = "scatter_table",
            titlePt = "Tabela de Dispersão (Granadas/Arremesso)",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.MECHANICAL,
            rows = listOf(
                "1: Cai 1d6 metros curto (antes do alvo).",
                "2: Cai 1d6 metros curto (antes do alvo).",
                "3: Passa 1d6 metros atrás do alvo.",
                "4: Passa 1d6 metros atrás do alvo.",
                "5: Desvia 1d6 metros para a direita do alvo.",
                "6: Desvia 1d6 metros para a esquerda do alvo."
            )
        ),
        SoloPlusTableSpec(
            id = "hollywood_overacting",
            titlePt = "Efeitos Dramáticos (Hollywood Overacting)",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.MECHANICAL,
            rows = listOf(
                "1: Queda cinematográfica — alvo derruba mobília/vidro ao cair.",
                "2: Discurso final — NPC solta uma última pista ou maldição antes de apagar.",
                "3: Explosão estética — faíscas, fumaça ou tanque próximo explode em cena.",
                "4: Olhar de choque em câmera lenta antes da cobertura desabar.",
                "5: Desarme dramático — arma desliza longe pelo chão sob luz neon.",
                "6: Reviravolta heroica — capa/jaqueta voa e revela cicatriz ou distintivo oculto."
            )
        ),
        SoloPlusTableSpec(
            id = "attackers_aim_person",
            titlePt = "Mira Aleatória do Atacante (Pessoa)",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.MECHANICAL,
            rows = listOf(
                "1: Cabeça (aplica regra de Tiro na Cabeça / PA da Cabeça).",
                "2: Tronco / Corpo.",
                "3: Tronco / Corpo.",
                "4: Tronco / Corpo.",
                "5: Mão / Braço (pode afetar item segurado).",
                "6: Perna / Pé."
            )
        ),
        SoloPlusTableSpec(
            id = "attackers_aim_vehicle",
            titlePt = "Mira Aleatória do Atacante (Veículo)",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.MECHANICAL,
            rows = listOf(
                "1: Ponto Fraco (dano dobrado após blindagem do veículo).",
                "2: Carroceria Principal.",
                "3: Carroceria Principal.",
                "4: Carroceria Principal.",
                "5: Motorista / Piloto (atravessa vidro se exposto).",
                "6: Passageiro."
            )
        ),

        // === MEDICAL (2 tables) ===
        SoloPlusTableSpec(
            id = "surgical_complications",
            titlePt = "Complicações Cirúrgicas",
            dieSides = 10,
            dieLabel = "1d10",
            category = SoloPlusTableCategory.MEDICAL,
            rows = listOf(
                "1: Nenhuma complicação — recuperação limpa.",
                "2: Nenhuma complicação — recuperação limpa.",
                "3: Nenhuma complicação — recuperação limpa.",
                "4: Nenhuma complicação — recuperação limpa.",
                "5: Nenhuma complicação — recuperação limpa.",
                "6: Infecção leve — exige +1 dia adicional de descanso absoluto.",
                "7: Cicatrização evidente — marca permanente visível (sem perda mecânica).",
                "8: Coma temporário por 1d6 dias devido a choque neural/anestésico.",
                "9: Perda temporária de -1 em um Atributo físico por 1d6 dias.",
                "10: Dependência de analgésicos / fármaco pós-operatório."
            )
        ),
        SoloPlusTableSpec(
            id = "medical_symptoms",
            titlePt = "Sintomas Médicos",
            dieSides = 6,
            dieLabel = "1d6",
            category = SoloPlusTableCategory.MEDICAL,
            rows = listOf(
                "1: Dor de cabeça intensa e tontura (-1 em testes de Concentração).",
                "2: Febre alta e calafrios.",
                "3: Garganta inflamada e tosse constante.",
                "4: Desarranjo gastrointestinal agudo.",
                "5: Náusea persistente e episódios de vômito.",
                "6: Erupção cutânea avermelhada ou irritação ao redor de implantes."
            )
        )
    )

    val soloTweaksGuidePt: List<String> = listOf(
        "Modo Solo — Escala de Ameaça: Em combates 1v1, use 1–2 Mooks padrões (ou 1 Endurecido) para equilibrar ações.",
        "Perguntas Fechadas (Oráculo 1d10): Improvável (DV 8), 50/50 (DV 5), Provável (DV 3). Resultados 1 ou 10 geram reviravolta narrativa!",
        "Orçamento & Tempo: Avance 1 semana para Hustle, Terapia ou Salvaging e role 1 encontro na Rua ou Metrô por deslocamento.",
        "NPCs Instantâneos: Role Motivação + Competência + Agressividade para gerar qualquer contato ou antagonista em segundos."
    )

    fun rollTable(tableId: String, diceEngine: DiceEngine = DiceEngine()): SoloPlusRollResult {
        val table = allTables.firstOrNull { it.id == tableId } ?: allTables.first()
        val roll = when (table.dieSides) {
            100 -> diceEngine.rollD100()
            6 -> diceEngine.rollD6()
            else -> diceEngine.rollPlainD10()
        }
        val text = if (table.dieSides == 100) {
            val idx = ((roll - 1) / 10).coerceIn(0, table.rows.lastIndex)
            table.rows[idx]
        } else {
            table.rows[(roll - 1).coerceIn(0, table.rows.lastIndex)]
        }
        return SoloPlusRollResult(
            tableId = table.id,
            tableTitlePt = table.titlePt,
            dieLabel = table.dieLabel,
            rollValue = roll,
            resultTextPt = text
        )
    }

    fun rollNpcPersonalityBundle(diceEngine: DiceEngine = DiceEngine()): SoloNpcPersonalityBundle {
        val mot = rollTable("npc_motivation", diceEngine)
        val comp = rollTable("npc_competence", diceEngine)
        val agg = rollTable("npc_aggression", diceEngine)
        return SoloNpcPersonalityBundle(
            motivationRoll = mot.rollValue,
            motivationPt = mot.resultTextPt,
            competenceRoll = comp.rollValue,
            competencePt = comp.resultTextPt,
            aggressionRoll = agg.rollValue,
            aggressionPt = agg.resultTextPt
        )
    }

    fun askSoloOracleYesNo(
        questionPt: String,
        oddsLabelPt: String,
        targetDv: Int,
        diceEngine: DiceEngine = DiceEngine()
    ): SoloOracleYesNoResult {
        val roll = diceEngine.rollPlainD10()
        val yes = roll > targetDv
        val twist = when (roll) {
            10 -> "Reviravolta Extrema (10): acontece com vantagem inesperada ou escalada dramática!"
            1 -> "Complicação Crítica (1): surge um imprevisto ou custo adicional imediato!"
            else -> null
        }
        return SoloOracleYesNoResult(
            questionPt = questionPt.ifBlank { "Situação na cena" },
            oddsLabelPt = oddsLabelPt,
            targetDv = targetDv,
            roll1d10 = roll,
            answerPt = if (yes) "SIM" else "NÃO",
            twistPt = twist
        )
    }
}
