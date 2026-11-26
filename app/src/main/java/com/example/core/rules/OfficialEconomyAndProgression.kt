package com.example.core.rules

data class HustleTableRow(
    val roll1d6: Int,
    val activityPt: String,
    val payRank1To4: Int,
    val payRank5To7: Int,
    val payRank8To10: Int
)

data class HustleRollResult(
    val role: RedRole,
    val roleRank: Int,
    val roll1d6: Int,
    val activityPt: String,
    val earnedEb: Int
)

data class JobPayTier(
    val labelPt: String,
    val payPerPersonEb: Int,
    val descriptionPt: String
)

data class LifestyleSpec(
    val namePt: String,
    val monthlyCostEb: Int,
    val descriptionPt: String
)

data class HousingSpec(
    val namePt: String,
    val monthlyRentEb: Int?,
    val buyPriceEb: Int?,
    val notesPt: String
)

data class ServiceSpec(
    val namePt: String,
    val costEb: Int,
    val priceCategory: PriceCategory
)

enum class FashionSlot(val labelPt: String) {
    BOTTOMS("Calças/Inferior"),
    TOP("Camisa/Top"),
    JACKET("Jaqueta"),
    FOOTWEAR("Calçados"),
    JEWELRY("Joias"),
    MIRRORSHADES("Óculos Espelhados"),
    GLASSES("Óculos"),
    CONTACT_LENSES("Lentes de Contato"),
    HATS("Chapéus/Capacetes")
}

data class FashionStyleSpec(
    val namePt: String,
    val vibePt: String,
    val pricesBySlotEb: Map<FashionSlot, Int>
)

data class PlaystyleIpTier(
    val ipAwarded: Int,
    val groupPt: String,
    val warriorPt: String,
    val socializerPt: String,
    val explorerPt: String,
    val roleplayerPt: String
)

/**
 * Canonical Part 2 Progression & Street Economy (Core Rulebook pp. 356, 376–385, 408–411):
 * - Improvement Points (I.P.) costs & Playstyle award tiers
 * - All 10-Role 7-day Hustle tables
 * - Job Pay tiers, Lifestyle, Housing, Fatigue, Services & Entertainment, and Fashion matrix.
 */
object OfficialEconomyAndProgression {

    /**
     * Core Rulebook p. 411:
     * Typical Skill next level N costs N * 20 I.P. (Level 1 = 20 .. Level 10 = 200).
     * Difficult (x2) Skill next level N costs N * 40 I.P. (Level 1 = 40 .. Level 10 = 400).
     * Skipping levels is forbidden.
     */
    fun skillIpCostForNextLevel(nextLevel: Int, isDoubleCost: Boolean): Int {
        require(nextLevel in 1..10) { "Nível alvo deve ser entre 1 e 10." }
        return nextLevel * if (isDoubleCost) 40 else 20
    }

    /**
     * Core Rulebook p. 411:
     * Role Ability Rank next rank R costs R * 60 I.P. (Rank 1 = 60 .. Rank 10 = 600).
     */
    fun roleAbilityIpCostForNextRank(nextRank: Int): Int {
        require(nextRank in 1..10) { "Rank alvo deve ser entre 1 e 10." }
        return nextRank * 60
    }

    val playstyleIpTable: List<PlaystyleIpTier> = listOf(
        PlaystyleIpTier(10, "Não concluiu, mas tentou com bravura", "Usou combate com frequência", "Ajudou e apoiou o grupo", "Tentou investigar/explorar", "Interpretou com frequência"),
        PlaystyleIpTier(20, "Cumpriu objetivos por pouco", "Combate frequente e eficaz", "Manteve coesão do grupo", "Explorou de forma eficaz", "Interpretou o tempo todo com eficácia"),
        PlaystyleIpTier(30, "Bom trabalho em equipe na maioria dos objetivos", "Derrotou os inimigos mais perigosos", "Atividades de apoio constantes", "Investigação frequente e eficaz", "Interpretou para cumprir objetivo sem rolar"),
        PlaystyleIpTier(40, "Forte cooperação na maioria dos objetivos", "Feito incomum de combate", "Ajudou de forma excepcional", "Descobriu algo fora do comum", "Interpretação memorável (discurso/cena)"),
        PlaystyleIpTier(50, "Objetivos muito bem cumpridos", "Derrotou vários inimigos de modo brilhante", "Apoio muito inteligente ao grupo", "Desvendou pista/pessoa/local chave", "Momento decisivo de interpretação"),
        PlaystyleIpTier(60, "Sucesso sólido em todos os objetivos", "Derrotou chefe/nêmesis sozinho", "Participação vital para união", "Descoberta crítica para si na sessão", "Interpretação mudou seu destino"),
        PlaystyleIpTier(70, "Sucesso retumbante + objetivos secundários", "Sem sua ajuda o grupo teria caído", "Vital para impedir derrota", "Descoberta salvou o grupo inteiro", "Interpretação mudou o rumo do grupo"),
        PlaystyleIpTier(80, "Sessão lendária — 100% objetivos e extras", "Feito incrível (tiro impossível/salvou todos)", "Suporte heroico inolvidável", "Descoberta lendária na sessão", "Cena épica com grande impacto na história")
    )

    val jobPayTiers: List<JobPayTier> = listOf(
        JobPayTier("Trabalho Fácil", 500, "Resistência armada inesperada (ex.: escolta simples de VIP)."),
        JobPayTier("Trabalho Típico", 1000, "Resistência armada esperada, mas contornável com preparo."),
        JobPayTier("Trabalho Perigoso", 2000, "Resistência esmagadora (ex.: infiltrar base militar e explodir arsenal).")
    )

    val hustleTables: Map<RedRole, List<HustleTableRow>> = mapOf(
        RedRole.ROCKERBOY to listOf(
            HustleTableRow(1, "Tocou em um show pequeno local.", 200, 300, 600),
            HustleTableRow(2, "Nenhum show ou bico nesta semana.", 0, 100, 300),
            HustleTableRow(3, "Show grande para Corporativo rico ou celebridade local.", 300, 500, 800),
            HustleTableRow(4, "Recebeu royalties do seu último download no Data Pool.", 300, 500, 800),
            HustleTableRow(5, "Ato de abertura para banda de grande porte.", 300, 500, 800),
            HustleTableRow(6, "Aparição pessoal rendeu cachê alto.", 200, 300, 600)
        ),
        RedRole.SOLO to listOf(
            HustleTableRow(1, "Trabalho de guarda-costas para cliente modesto.", 100, 200, 500),
            HustleTableRow(2, "Trabalho de guarda-costas para cliente de alto padrão.", 200, 300, 600),
            HustleTableRow(3, "Execução ou extração difícil.", 200, 300, 600),
            HustleTableRow(4, "Contratado como músculo para Fixer, Corp ou Gangue.", 100, 200, 500),
            HustleTableRow(5, "Chamou atenção indesejada e precisou sumir do mapa.", 0, 100, 300),
            HustleTableRow(6, "Serviço padrão de executor/capanga para Corp local.", 100, 200, 500)
        ),
        RedRole.NETRUNNER to listOf(
            HustleTableRow(1, "Invadiu um sistema pequeno e vendeu os dados.", 100, 200, 500),
            HustleTableRow(2, "Invadiu sistema Corporativo grande e vendeu os dados.", 200, 300, 600),
            HustleTableRow(3, "Distraiu-se e não hackeou nada esta semana.", 0, 100, 300),
            HustleTableRow(4, "Achou cache valioso de dados em sistema abandonado e vendeu.", 200, 300, 600),
            HustleTableRow(5, "Travou sistema grande com ransomware e cobrou para remover.", 200, 300, 600),
            HustleTableRow(6, "Sabotou/desativou sistema grande para cliente anônimo.", 200, 300, 600)
        ),
        RedRole.TECH to listOf(
            HustleTableRow(1, "Sem serviços nesta semana.", 0, 100, 300),
            HustleTableRow(2, "Reconstruiu sucata tecnológica coletada na Combat Zone.", 100, 200, 500),
            HustleTableRow(3, "Ajudou cliente a arrombar local ou instalou segurança.", 200, 300, 600),
            HustleTableRow(4, "Fez modificações ou reparos em cibertecnologia.", 100, 200, 500),
            HustleTableRow(5, "Fez modificações ou reparos em armas.", 100, 200, 500),
            HustleTableRow(6, "Sabotou ou desativou equipamento para um cliente.", 100, 200, 500)
        ),
        RedRole.MEDTECH to listOf(
            HustleTableRow(1, "Remendou alguém após um tiroteio.", 100, 200, 500),
            HustleTableRow(2, "Vendeu cyberware de um caso médico que 'não sobreviveu'.", 200, 300, 600),
            HustleTableRow(3, "Reforçou a Trauma Team quando estavam sobrecarregados.", 100, 200, 500),
            HustleTableRow(4, "Atendeu em clínica gratuita para locais (boa ação não paga contas).", 0, 100, 300),
            HustleTableRow(5, "Procedimento médico complexo para cliente abastado.", 200, 300, 600),
            HustleTableRow(6, "Formulou e entregou remédios ou drogas de rua a cliente.", 100, 200, 500)
        ),
        RedRole.MEDIA to listOf(
            HustleTableRow(1, "Escreveu denúncia sobre grande tema e vendeu bem.", 300, 500, 800),
            HustleTableRow(2, "Matéria leve popular rendeu visibilidade e dinheiro.", 200, 300, 600),
            HustleTableRow(3, "Redigiu textos publicitários chatos para pagar as contas.", 200, 300, 600),
            HustleTableRow(4, "Expôs grande caso que rendeu alguns inimigos e dinheiro.", 200, 300, 600),
            HustleTableRow(5, "Nenhuma boa pauta ou pista nesta semana.", 0, 100, 300),
            HustleTableRow(6, "Publicou dossiê explosivo que abalou um grande escândalo.", 300, 500, 800)
        ),
        RedRole.LAWMAN to listOf(
            HustleTableRow(1, "Fez algumas batidas menores, rotina normal.", 100, 200, 500),
            HustleTableRow(2, "Recebeu recompensa de cidadão grato. Ou foi propina?", 200, 300, 600),
            HustleTableRow(3, "Operação deu errado e descontaram do seu salário.", 0, 100, 300),
            HustleTableRow(4, "Semana tranquila. Apenas recebeu o contracheque.", 100, 200, 500),
            HustleTableRow(5, "Desmantelou grande esquema de drogas/contrabando com bônus.", 200, 300, 600),
            HustleTableRow(6, "Derrubou gangue grande e ficou com parte do 'confisco civil'.", 200, 300, 600)
        ),
        RedRole.EXEC to listOf(
            HustleTableRow(1, "Projeto com sucesso moderado gerou bônus de recompensa.", 300, 500, 800),
            HustleTableRow(2, "Nada relevante ocorreu; Corporação descontente cortou bônus.", 0, 100, 300),
            HustleTableRow(3, "Apenas coletou seu salário padrão da semana.", 200, 300, 600),
            HustleTableRow(4, "Conseguiu sujeira de um rival e usou para ganhar bônus.", 300, 500, 800),
            HustleTableRow(5, "Grande projeto bem-sucedido gerou bônus da Matriz.", 300, 500, 800),
            HustleTableRow(6, "Neutralizou alvo legítimo que ameaçava projeto e tomou a verba.", 200, 300, 600)
        ),
        RedRole.FIXER to listOf(
            HustleTableRow(1, "Passou informação a um Mídia em troca de boa propina.", 200, 300, 600),
            HustleTableRow(2, "Arranjou bom Gig para um Rocker e cobrou 12% de comissão.", 200, 300, 600),
            HustleTableRow(3, "Localizou item raro para cliente e pegou sua comissão.", 200, 300, 600),
            HustleTableRow(4, "Acordo melou; ficou escondido até a poeira baixar.", 0, 100, 300),
            HustleTableRow(5, "Arranjou trabalho lucrativo para Solo/Netrunner e cobrou taxa.", 200, 300, 600),
            HustleTableRow(6, "Importou item raro, ilegal ou muito difícil para cliente.", 300, 500, 800)
        ),
        RedRole.NOMAD to listOf(
            HustleTableRow(1, "Fez um frete legítimo.", 100, 200, 500),
            HustleTableRow(2, "Escoltou e protegeu uma carga.", 100, 200, 500),
            HustleTableRow(3, "Contrabaixou carga pequena ilegal.", 100, 200, 500),
            HustleTableRow(4, "Contrabaixou um carregamento enorme.", 200, 300, 600),
            HustleTableRow(5, "Transportou cliente com segurança até o destino.", 100, 200, 500),
            HustleTableRow(6, "Não achou trabalho nesta semana, nem legal nem ilegal.", 0, 100, 300)
        )
    )

    fun rollHustle(role: RedRole, roleRank: Int, diceEngine: DiceEngine): HustleRollResult {
        val roll = diceEngine.rollD6().coerceIn(1, 6)
        val row = hustleTables.getValue(role).first { it.roll1d6 == roll }
        val rank = roleRank.coerceIn(1, 10)
        val pay = when {
            rank <= 4 -> row.payRank1To4
            rank <= 7 -> row.payRank5To7
            else -> row.payRank8To10
        }
        return HustleRollResult(role, rank, roll, row.activityPt, pay)
    }

    val lifestyles: List<LifestyleSpec> = listOf(
        LifestyleSpec("Kibble", 100, "Ração sintética básica. 1 cinema ou braindance por mês."),
        LifestyleSpec("Prepak Genérico", 300, "Comida pré-embalada razoável. Bar bom ou restaurante nos fins de semana."),
        LifestyleSpec("Prepak Bom", 600, "Sabor próximo de comida real. Bares excelentes e 1 show/evento por mês."),
        LifestyleSpec("Comida Fresca", 1500, "Alimentos reais, bares executivos, 1 hotel de emergência e 1 restaurante classe mundial/mês.")
    )

    val housings: List<HousingSpec> = listOf(
        HousingSpec("Morar na Rua", null, null, "Resistência DV15 toda noite ou fica Fatigado (-2 em tudo)."),
        HousingSpec("Morar na Rua (Veículo)", null, null, "Resistência DV15 ou Fatigado (-2) se apertado."),
        HousingSpec("Hotel Cápsula (Cube Hotel)", 500, null, "1 pessoa; mais de 1 pessoa sempre gera Fadiga (-2)."),
        HousingSpec("Contêiner de Carga", 1000, 15000, "Padrão edgerunner fora do centro."),
        HousingSpec("Apartamento Estúdio", 1500, 25000, "1 quarto."),
        HousingSpec("Apartamento 2 Quartos", 2500, 35000, "Comporta até 3 moradores sem aperto."),
        HousingSpec("Conapt Corporativo", null, null, "Fornecido pela Corporação (Exec)."),
        HousingSpec("Conapt de Alto Padrão", 7500, 85000, "Segurança reforçada e luxo urbano."),
        HousingSpec("Cobertura de Luxo (Penthouse)", 15000, 150000, "Topo dos arranha-céus (150.000eb compra — erro gráfico 1.500.000eb no PDF original p.378)."),
        HousingSpec("Casa Beaverville Corporativa", null, 200000, "Fornecida por Corp ou 200.000eb."),
        HousingSpec("Mansão Beaverville Corporativa", null, 500000, "Fornecida por Corp ou 500.000eb.")
    )

    val servicesAndEntertainment: List<ServiceSpec> = listOf(
        ServiceSpec("Bebida em Bar Pé-Sujo / Bom", 10, PriceCategory.CHEAP),
        ServiceSpec("Refeição Fast Food", 10, PriceCategory.CHEAP),
        ServiceSpec("Bebida em Bar Excelente", 20, PriceCategory.EVERYDAY),
        ServiceSpec("Braindance / Cinema / Táxi", 20, PriceCategory.EVERYDAY),
        ServiceSpec("Refeição em Restaurante Bom", 20, PriceCategory.EVERYDAY),
        ServiceSpec("Bebida em Bar Executivo", 50, PriceCategory.COSTLY),
        ServiceSpec("Braindance Interativo / Videogame", 50, PriceCategory.COSTLY),
        ServiceSpec("Refeição em Restaurante Excelente", 50, PriceCategory.COSTLY),
        ServiceSpec("Tratamento Hospitalar (DV10)", 50, PriceCategory.COSTLY),
        ServiceSpec("Instalação de Cyberware (Shopping)", 100, PriceCategory.PREMIUM),
        ServiceSpec("Tratamento Hospitalar (DV13)", 100, PriceCategory.PREMIUM),
        ServiceSpec("Hotel por Noite / Show / Evento", 100, PriceCategory.PREMIUM),
        ServiceSpec("Serviços Profissionais Bons (por hora)", 100, PriceCategory.PREMIUM),
        ServiceSpec("Bioescultura Padrão", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Instalação de Cyberware (Clínica)", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Tratamento Hospitalar (DV15)", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Hotel de Luxo (por noite)", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Serviços Profissionais Excelentes (por hora)", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Restaurante Classe Mundial", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Terapia Humanidade Padrão", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Trauma Team Prata (mensal)", 500, PriceCategory.EXPENSIVE),
        ServiceSpec("Bioescultura Exótica", 1000, PriceCategory.VERY_EXPENSIVE),
        ServiceSpec("Instalação de Cyberware (Hospital)", 1000, PriceCategory.VERY_EXPENSIVE),
        ServiceSpec("Tratamento Hospitalar (DV17+)", 1000, PriceCategory.VERY_EXPENSIVE),
        ServiceSpec("Terapia Humanidade Extrema / Dependência", 1000, PriceCategory.VERY_EXPENSIVE),
        ServiceSpec("Trauma Team Executivo (mensal)", 1000, PriceCategory.VERY_EXPENSIVE),
        ServiceSpec("Serviços Profissionais Classe Mundial (por trabalho)", 5000, PriceCategory.LUXURY)
    )

    val fashionStyles: List<FashionStyleSpec> = listOf(
        FashionStyleSpec("Bag Lady Chic", "Mendigo / Esfarrapado", mapOf(
            FashionSlot.BOTTOMS to 20, FashionSlot.TOP to 10, FashionSlot.JACKET to 20, FashionSlot.FOOTWEAR to 20,
            FashionSlot.JEWELRY to 20, FashionSlot.MIRRORSHADES to 20, FashionSlot.GLASSES to 10, FashionSlot.CONTACT_LENSES to 10, FashionSlot.HATS to 10
        )),
        FashionStyleSpec("Gang Colors", "Perigoso / Violento / Rebelde", mapOf(
            FashionSlot.BOTTOMS to 50, FashionSlot.TOP to 20, FashionSlot.JACKET to 50, FashionSlot.FOOTWEAR to 20,
            FashionSlot.JEWELRY to 50, FashionSlot.MIRRORSHADES to 20, FashionSlot.GLASSES to 20, FashionSlot.CONTACT_LENSES to 10, FashionSlot.HATS to 10
        )),
        FashionStyleSpec("Generic Chic", "Padrão / Colorido / Modular", mapOf(
            FashionSlot.BOTTOMS to 50, FashionSlot.TOP to 20, FashionSlot.JACKET to 50, FashionSlot.FOOTWEAR to 20,
            FashionSlot.JEWELRY to 50, FashionSlot.MIRRORSHADES to 20, FashionSlot.GLASSES to 20, FashionSlot.CONTACT_LENSES to 10, FashionSlot.HATS to 10
        )),
        FashionStyleSpec("Bohemian", "Folclórico / Retrô / Espírito Livre", mapOf(
            FashionSlot.BOTTOMS to 50, FashionSlot.TOP to 20, FashionSlot.JACKET to 50, FashionSlot.FOOTWEAR to 50,
            FashionSlot.JEWELRY to 100, FashionSlot.MIRRORSHADES to 50, FashionSlot.GLASSES to 50, FashionSlot.CONTACT_LENSES to 10, FashionSlot.HATS to 10
        )),
        FashionStyleSpec("Leisurewear", "Conforto / Agilidade / Esportivo", mapOf(
            FashionSlot.BOTTOMS to 100, FashionSlot.TOP to 20, FashionSlot.JACKET to 100, FashionSlot.FOOTWEAR to 50,
            FashionSlot.JEWELRY to 100, FashionSlot.MIRRORSHADES to 50, FashionSlot.GLASSES to 50, FashionSlot.CONTACT_LENSES to 20, FashionSlot.HATS to 50
        )),
        FashionStyleSpec("Nomad Leathers", "Ocidental / Rústico / Tribal", mapOf(
            FashionSlot.BOTTOMS to 100, FashionSlot.TOP to 20, FashionSlot.JACKET to 100, FashionSlot.FOOTWEAR to 100,
            FashionSlot.JEWELRY to 100, FashionSlot.MIRRORSHADES to 50, FashionSlot.GLASSES to 50, FashionSlot.CONTACT_LENSES to 20, FashionSlot.HATS to 100
        )),
        FashionStyleSpec("Asia Pop", "Brilhante / Fantasia / Jovem", mapOf(
            FashionSlot.BOTTOMS to 100, FashionSlot.TOP to 20, FashionSlot.JACKET to 100, FashionSlot.FOOTWEAR to 100,
            FashionSlot.JEWELRY to 100, FashionSlot.MIRRORSHADES to 100, FashionSlot.GLASSES to 100, FashionSlot.CONTACT_LENSES to 100, FashionSlot.HATS to 100
        )),
        FashionStyleSpec("Urban Flash", "Chique / Tecnológico / Streetwear", mapOf(
            FashionSlot.BOTTOMS to 100, FashionSlot.TOP to 20, FashionSlot.JACKET to 100, FashionSlot.FOOTWEAR to 100,
            FashionSlot.JEWELRY to 100, FashionSlot.MIRRORSHADES to 100, FashionSlot.GLASSES to 100, FashionSlot.CONTACT_LENSES to 100, FashionSlot.HATS to 100
        )),
        FashionStyleSpec("Businesswear", "Liderança / Presença / Autoridade", mapOf(
            FashionSlot.BOTTOMS to 500, FashionSlot.TOP to 50, FashionSlot.JACKET to 500, FashionSlot.FOOTWEAR to 500,
            FashionSlot.JEWELRY to 5000, FashionSlot.MIRRORSHADES to 500, FashionSlot.GLASSES to 500, FashionSlot.CONTACT_LENSES to 100, FashionSlot.HATS to 500
        )),
        FashionStyleSpec("High Fashion", "Exclusivo / Grife / Alta Costura", mapOf(
            FashionSlot.BOTTOMS to 1000, FashionSlot.TOP to 500, FashionSlot.JACKET to 1000, FashionSlot.FOOTWEAR to 5000,
            FashionSlot.JEWELRY to 50000, FashionSlot.MIRRORSHADES to 1000, FashionSlot.GLASSES to 1000, FashionSlot.CONTACT_LENSES to 1000, FashionSlot.HATS to 5000
        ))
    )
}
