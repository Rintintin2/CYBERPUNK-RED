package com.example.core.rules

data class PlotHookSpec(
    val districtPt: String,
    val titlePt: String,
    val summaryPt: String
)

/**
 * Complete Night City Hook Book (130 Plot Hooks & Playland by the Sea Rules).
 */
object OfficialHookBookData {

    val allPlotHooks: List<PlotHookSpec> = listOf(
        // Charter Hill (5)
        PlotHookSpec("Charter Hill", "Cinema Verity", "Verity Lee quer invadir o escritório de Magretta Cippolina para deixar cópia de seu BD indie na mesa."),
        PlotHookSpec("Charter Hill", "Cooking the Books", "Symon Featherstonehaugh busca podres sobre um auditor do NCPD — ou o auditor paga para ver os livros."),
        PlotHookSpec("Charter Hill", "Find Gavin", "Gavin, o anfitrião mais popular de Anjelika, foi sequestrado; o clube paga alto pelo resgate."),
        PlotHookSpec("Charter Hill", "Making a Connection", "Mídia contrata equipe para infiltrar a Many Hands como peões e invadir arquivos provando corrupção."),
        PlotHookSpec("Charter Hill", "Seize the Light", "Homem aplicou economias na Mega-Igreja Flow e mudou de personalidade; parceiro quer saber o que ocorre lá dentro."),
        // Downtown (5)
        PlotHookSpec("Downtown", "Be a Shame…", "Família Skiv quer o terreno do Café Bouchon, mas a gerente Emilia Ortega ama o local; guerra de pressão."),
        PlotHookSpec("Downtown", "Chrome Shine Crimes", "Após polir o cromo em Downtown, um membro descobre microescuta transmitindo para a CitiNet."),
        PlotHookSpec("Downtown", "Good Dirt", "Valian Violet chantageia um político poderoso para custear seu corpo FBC steampunk."),
        PlotHookSpec("Downtown", "Fortune Telling", "Madame Folio contrata equipe para garantir que um executivo da Continental Brands apareça onde sua 'previsão' indicou."),
        PlotHookSpec("Downtown", "The Leak", "Tríade Eastern Tigers roubou carga do Midnight Market de Mister K; equipe deve achar o traidor interno."),
        // Executive Zone (5)
        PlotHookSpec("Executive Zone", "Custody Dispute", "Rogue contrata equipe para roubar a guitarra DeLuze Orphean de Silverhand da mansão de Kerry Eurodyne sem violência."),
        PlotHookSpec("Executive Zone", "Life's a Birch", "Exec da Petrochem quer remoção ilegal de árvores aprovadas pela HOA na divisa com rival da SovOil."),
        PlotHookSpec("Executive Zone", "Losing to Lazarus", "Lazarus testa sua segurança contratando Edgerunners para invadir mansão — os guardas não foram avisados."),
        PlotHookSpec("Executive Zone", "Humiliation", "Duas candidatas rivais da HOA executiva pagam por sabotagens de imagem não letais na eleição."),
        PlotHookSpec("Executive Zone", "Valuable Garbage", "Chefe de limpeza executiva Audrey Sanchez descobriu segredo letal no lixo e paga por fuga de Night City."),
        // The Glen (5)
        PlotHookSpec("The Glen", "Best Served Cold", "Rex Royale quer apagar a existência civil de um inimigo hackeando o banco de dados do DMV."),
        PlotHookSpec("The Glen", "Corp on Corp Action", "Raven Microcybernetics quer extrair tanto o protótipo da Rocklin Augmentics quanto a Dra. Emily Cross."),
        PlotHookSpec("The Glen", "Frame Job", "Pacote entregue antes do Dia dos Namorados no Consulado é na verdade uma bomba armada para culpar a equipe."),
        PlotHookSpec("The Glen", "Killer Clowns", "Circo Bozo ataca mímicos Julliard na Plaza; circo, guilda ou prefeitura pagam por lados opostos."),
        PlotHookSpec("The Glen", "Reckoning Day", "Fação dissidente dos Reckoners quer detonar bomba suja no Glen; o sumo-sacerdote pede intervenção discreta."),
        // Heywood Docks (5)
        PlotHookSpec("Heywood Docks", "Cube Life", "Operativo da Arasaka escondido nas Greenbox Security Units; Danger Gal contrata mercs discretos para espionar."),
        PlotHookSpec("Heywood Docks", "Hunting for Hunter", "Estivador Toby Hunter pede ajuda para achar pacote valioso extraviado no cais."),
        PlotHookSpec("Heywood Docks", "Something Toxic", "Contêiner sem dono rompeu com gosma ácida que dissolveu guarda; prefeita Andrea Lee investiga origem."),
        PlotHookSpec("Heywood Docks", "Submarine Heist", "Sucateiro bêbado revela localização de submarino pré-4ª Guerra Corporativa enterrado no canal."),
        PlotHookSpec("Heywood Docks", "Tension on High Seas", "Conflito entre nômades Thelas e DT&R pelo primeiro navio de carga Dynalar rumo a Seattle."),
        // Heywood Industrial Zone (5)
        PlotHookSpec("Heywood Ind.", "Diva Detail", "Após incêndios criminosos, as Digital Divas contratam escolta armada para show no Old Ironworks."),
        PlotHookSpec("Heywood Ind.", "Extraction", "Rocklin Augmentics quer 'sequestrar' um ciber-técnico estrela de fábrica da Raven para facilitar deserção."),
        PlotHookSpec("Heywood Ind.", "Dognapping", "Toecutters sequestraram Craig, o cão do chefe de estação Calvert, para extorquir desvios de carga."),
        PlotHookSpec("Heywood Ind.", "Missing Links", "Correntes de bicicletas da Yang's Wheels têm quebrado em acidentes; Gabriel Yang suspeita da Americar."),
        PlotHookSpec("Heywood Ind.", "Skid Zone", "Time de roller derby recrutando substitutos duros às pressas contra os Exec Zone Unicorns."),
        // Hot Zone (5)
        PlotHookSpec("Hot Zone", "700 9th Street", "Lucia Zskremsky paga por registros antigos de escrituras imobiliárias pré-bomba da Plaza Realty."),
        PlotHookSpec("Hot Zone", "A Brand New Car!", "Subsolo de reboque dominado por Maelstrom guarda coleção de carros restaurados na última década."),
        PlotHookSpec("Hot Zone", "Found Footage", "Livecaster TSpooks perdeu colega filmando lenda urbana do 'Hot Zone Lurker'; resgate ou recupere o vídeo."),
        PlotHookSpec("Hot Zone", "The Holy Land", "Reckoners e Maelstrom Diggers disputam os subsolos das antigas Torres Arasaka."),
        PlotHookSpec("Hot Zone", "Hot Zone Sherpas", "Filhinhos de papai ricos pagam mercenários para guiá-los em turismo perigoso pela Zona Quente."),
        // Kabuki (5)
        PlotHookSpec("Kabuki", "Deliver with Tyger Eats", "Entregas rápidas com pagamento imediato — sabotadas constantemente pela Família Kanzaki."),
        PlotHookSpec("Kabuki", "Grand Theft Automated", "Roubar um supercarro trancado no estacionamento automatizado da Nakagawa Garage contra Kimen-Gumi."),
        PlotHookSpec("Kabuki", "Kibble Wars", "Continental Brands quer sabotar o único Oasis independente em Kabuki protegido pelos Tyger Claws."),
        PlotHookSpec("Kabuki", "RefleKuma Bears", "20 ursinhos de pelúcia colecionáveis chegaram em Kabuki valendo 5.000eb cada para Fixers e gangues."),
        PlotHookSpec("Kabuki", "Treason", "Shinobu II suspeita que a facção Tyger Phoenix quer desertar para a Família Kanzaki."),
        // Little China (5)
        PlotHookSpec("Little China", "Chrome Crossed Out", "Cliente exige obter a escritura da Chrome Cross e demolir a igreja-fortaleza por completo."),
        PlotHookSpec("Little China", "Finding the Finder", "Ginkgo desapareceu procurando uma criança para o Reverendo Ānxīn; comerciantes contratam busca."),
        PlotHookSpec("Little China", "It Belongs in a Library", "Exec comprou manuscrito raro Lunyu Shuzhu só para provocar David Ling Po; ambos contratam."),
        PlotHookSpec("Little China", "Seep Hunt", "Gold Dragon ciberpsicótico caiu no esgoto químico Seep e precisa ser parado antes da MAX-TAC intervir."),
        PlotHookSpec("Little China", "Virtex's Grand Opening", "Após incêndio, fliperama Virtex contrata equipe para 'adquirir' novas máquinas de reposição."),
        // Little Europe (5)
        PlotHookSpec("Little Europe", "Agents in Black", "Padre Kevin acolheu fugitiva de laboratório da Night Corp e precisa forjar a morte dela."),
        PlotHookSpec("Little Europe", "Drive Off the Clowns", "Bozos estão atacando táxis Red Cab; empresa contrata Edgerunners para dirigir e proteger turnos."),
        PlotHookSpec("Little Europe", "An Offer They Can't Refuse", "Lynn Skiv quer o restaurante Soprano's destruído por terceiros manipulados sem rastro."),
        PlotHookSpec("Little Europe", "Rumors About Paradise", "Chef Calvano contrata equipe para investigar quem espalha boatos de intoxicação alimentar."),
        PlotHookSpec("Little Europe", "Wolfnapping", "Capturar vivo um Cyberwolf geneticamente modificado da Biotechnica na Floresta RF-14."),
        // New Westbrook (5)
        PlotHookSpec("New Westbrook", "Blood Will Tell", "Vazamento de testes de DNA do motel Dilly's ameaça expor vínculo secreto da estrela N54 Fiona Hayes."),
        PlotHookSpec("New Westbrook", "Burning Down District", "Onda suspeita de incêndios criminosos assola New Westbrook; prefeita e bombeiros buscam culpados."),
        PlotHookSpec("New Westbrook", "Retrieve the AV", "AV da Aerocab foi roubado; pagamento proporcional ao estado da lataria devolvida."),
        PlotHookSpec("New Westbrook", "Tomb Raiders", "Rockerboy foi enterrado em Seral Grove com chip de músicas inéditas cobiçado por herdeiros e mídia."),
        PlotHookSpec("New Westbrook", "With Friends Like These", "Mortes por parada cardíaca ligadas a novo modelo SAAI de braindance da Virtual Variety."),
        // NorCal Military Base (5)
        PlotHookSpec("NorCal Base", "Helping Out Henshaw", "Cabo precisa de caixa de Emerald City da gangue Yellow Brick Road ou será entregue à PM."),
        PlotHookSpec("NorCal Base", "Sneaky Salvage", "Fixer localizou destróier afundado NCS Alaska cheio de tecnologia Militech antes da Estero Bay COG."),
        PlotHookSpec("NorCal Base", "Surplus Sale", "Durante feira de excedentes militares da Militech no estacionamento, brecha para infiltração."),
        PlotHookSpec("NorCal Base", "Two Crew Job", "Segunda equipe paga por distração nos elevadores da base para roubar aeronave ATF-37V Thunderhawk."),
        PlotHookSpec("NorCal Base", "The Very Model", "General Ash Giovanni quer roubar sigilosamente protótipo de cibercérebro da Rocklin Augmentics."),
        // North Heywood (5)
        PlotHookSpec("North Heywood", "The Haywire Conspiracy", "Robôs de treino de academia estão sendo usados para contrabandear dados criptografados."),
        PlotHookSpec("North Heywood", "Mini-Corporate War", "Execs rivais contratam incidentes para derrubar o valor de mercado da Dynalar durante aquisição."),
        PlotHookSpec("North Heywood", "Nomad Trouble", "Acampamento nômade Zolletta sofre assédio coordenado; ajudá-los rende aliados fiéis."),
        PlotHookSpec("North Heywood", "Secrets of Haven", "Cliente VIP desapareceu após sessão neural de rotina no Haven Spa & Recovery."),
        PlotHookSpec("North Heywood", "Strings Attached", "Prefeito Barry Delvecchio precisa de serviços escusos fora dos livros contra 6th Street."),
        // Old Combat Zone (5)
        PlotHookSpec("Old Combat Zone", "Assault on Substation 12", "Iron Sights querem destruir a última subestação funcional elétrica defendida pela ERI."),
        PlotHookSpec("Old Combat Zone", "District Beautification", "Professora Slee troca aulas de artes marciais e eb por entrega de grama real da Biotechnica."),
        PlotHookSpec("Old Combat Zone", "Feeding the Ashes", "Clínica From the Ashes troca atendimento de ripperdoc por suprimentos e medicamentos."),
        PlotHookSpec("Old Combat Zone", "Hunt Security", "Kill Krashers planejam sabotar caçada do Mule Deer 2000; moradores contratam segurança."),
        PlotHookSpec("Old Combat Zone", "Kibble Addiction", "Seita no Kibble Kirkle está batizando pacotes com variante Tech de Black Lace (Ultraviolet)."),
        // Old Japantown (5)
        PlotHookSpec("Old Japantown", "Boosting the Body Lotto", "Baixa taxa de homicídios prejudica a Loteria da Vida da MA&F — que contrata aumento de conflito."),
        PlotHookSpec("Old Japantown", "Highway Robbery", "Falta de guardas Kimen-Gumi abre comboios da Segotari e Sanroo para assalto ou escolta."),
        PlotHookSpec("Old Japantown", "Golden Week", "Sra. Suzuki contrata Edgerunners para atender e proteger sua bodega durante feriado."),
        PlotHookSpec("Old Japantown", "Old Secrets", "Night Corp quer destruir manuscrito fotográfico embaraçoso sobre o legado de Richard Night."),
        PlotHookSpec("Old Japantown", "Rigging the Game", "Torneio de ELO TCG na Neo Galaxy Comics: impedir rivais ou vencer pelo prêmio raro."),
        // Outskirts (5)
        PlotHookSpec("Outskirts", "Escort Job", "Escoltar caminhão-tanque de CHOOH² das Docas de Ebunike contra Raffen Shiv e rivais."),
        PlotHookSpec("Outskirts", "Farm to Table", "Contrabandear produtos orgânicos da Fazenda Ingalls para restaurantes VIP driblando Biotechnica."),
        PlotHookSpec("Outskirts", "Grandmother's Ring", "Anel de noivado foi parar no Lixão Municipal; corrida contra sucateiros antes que suma."),
        PlotHookSpec("Outskirts", "Highrider Lost", "Highrider perdeu passagem espacial no pôquer e paga para ser contrabandeado em delta orbital."),
        PlotHookSpec("Outskirts", "Tunnel Snake", "Algo caça trabalhadores nas profundezas da Mina Pol-Bud — ciberpsicopata ou criatura."),
        // Pacifica Playground (5)
        PlotHookSpec("Pacifica", "Extra Flaire", "Clínica Flaire está implantando chips secretos instáveis nos cérebros de novos FBC Gemini."),
        PlotHookSpec("Pacifica", "Gang Warfare", "Piranhas e Voodoo Boys oferecem dinheiro e favores para fechar laboratórios do rival."),
        PlotHookSpec("Pacifica", "Haunted House", "Senhorio forja 'assombração' no Playhouse para expulsar inquilinos e cobrar multa contratual."),
        PlotHookSpec("Pacifica", "Ticket to Paradise", "Drone de reality show 'What Would You Do?!?' oferece desafios humilhantes por ingressos de Playland."),
        PlotHookSpec("Pacifica", "Virus Delivery System", "Militech quer infectar drones da Protective Lightning em Playland para assumir o contrato."),
        // Playland by the Sea Rules (5)
        PlotHookSpec("Playland by the Sea", "Roda-Gigante (The Big Wheel)", "Regra: Topo 100m ótimo p/ sniper; descer exige Atletismo DV13/rodada; salto paraquedas DV15."),
        PlotHookSpec("Playland by the Sea", "Bate-Bate (Bumper Pandamonium)", "Regra: MOV 10; Dirigir Terrestre contestado faz rival Perder Controle (p.192); 3 batidas param carro."),
        PlotHookSpec("Playland by the Sea", "Montanha-Russa (Eco-Coaster)", "Regra: 3x Téc. Básica DV13 (Base 10). 1 falha: Resistir DV9; 2: DV13 ou Whiplash; 3: descarrila (6d6)."),
        PlotHookSpec("Playland by the Sea", "Sala dos Espelhos (Hall of Nightmares)", "Regra: Mover Percepção DV13 / Atacar DV15 ou acerta espelho. Sonar/Radar anula; 10 espelhos quebrados limpam."),
        PlotHookSpec("Playland by the Sea", "Máquina de Garra Segotari", "Regra: 5eb/jogada. 1d10 < Sorte atual ganha prêmio 5eb; se rolar 1 rerrola (Par=10eb, Ímpar=20eb)."),
        // Port of Night City (5)
        PlotHookSpec("Port of NC", "Amazing Transport", "William Maze precisa cruzar a cidade levando material nuclear resgatado no mar no Doca 13."),
        PlotHookSpec("Port of NC", "Bar Fight", "Briga de bar clássica no Rusty's Dive Shack ideal para quebrar o gelo em campanha."),
        PlotHookSpec("Port of NC", "High Value Target", "Infiltrar o cargueiro Flotsam (OGV Lund) controlado pelos Thelas para recuperar item Super Luxo."),
        PlotHookSpec("Port of NC", "Salvage Claim", "Corrida submarina contra nômades e Hydrosubsidium por navio de carga afundado da 4CW."),
        PlotHookSpec("Port of NC", "Train Robbery", "Assalto ou defesa em trens de carga que partem do Porto contra cowboys DeadWoods e Snake Nation."),
        // Rancho Coronado (5)
        PlotHookSpec("Rancho Coronado", "Blockage", "Desviar água subterrânea de volta para o Lava-Jato Albino Alligator sem alertar a Tanson Group."),
        PlotHookSpec("Rancho Coronado", "Converting the Culms", "Biotechnica quer o bambu mutante dos Culms longe da Weng Fang Tong usando mercenários."),
        PlotHookSpec("Rancho Coronado", "Factory Direct", "Fábricas maquiladoras em guerra de facções pagam para sabotar ou capturar rivais."),
        PlotHookSpec("Rancho Coronado", "The Old NET", "Nostradamus paga expedição nas ruínas subterrâneas Digital Divinity atrás de nó ativo da Velha NET."),
        PlotHookSpec("Rancho Coronado", "Steel Salvation", "Corporativos saquearam remédios vitais de uma caravana dos Steel Vaqueros."),
        // Reclamation Zone (5)
        PlotHookSpec("Reclamation Zone", "Boosters MIA", "Ex-gangers que trabalham no Booster Bistro estão sendo recrutados ou sumindo misteriosamente."),
        PlotHookSpec("Reclamation Zone", "Election Season", "Disputa política acirrada entre Santos Dorado, Torin Seanoa e pupilo de Garven Haakensen."),
        PlotHookSpec("Reclamation Zone", "My Kingdom for a Horse", "Ataques misteriosos a entregas de peças travam os reparos do metrô NCART."),
        PlotHookSpec("Reclamation Zone", "NCART Fever", "Quatro passageiros entraram em combustão espontânea na linha vermelha do NCART."),
        PlotHookSpec("Reclamation Zone", "Random Violence", "Mob dos Kill Krashers invade a Avenida Benko a bordo de um blindado Punknaught."),
        // Santo Domingo (5)
        PlotHookSpec("Santo Domingo", "False Wolf", "Impostor está aceitando trabalhos usando a reputação da elite Aldecaldo Lobo; Izzy Lobo exige fim disso."),
        PlotHookSpec("Santo Domingo", "Metal Murder", "Cliente FBC morre envenenado/hackeado em silêncio dentro do bar MetalStorm."),
        PlotHookSpec("Santo Domingo", "Sinking Ship", "Irmão caçula de líder dos Rat Kings roubou drogas do Cartel El Norte em Megabuilding H4."),
        PlotHookSpec("Santo Domingo", "Snakes on the Train", "Cartel quer chantagear nômade da Snake Nation para liberar contrabando ferroviário."),
        PlotHookSpec("Santo Domingo", "Woodchipper's Block Party", "Woodchipper precisa de segurança extra no Night Market enquanto os Lobos estão ausentes."),
        // South Night City (5)
        PlotHookSpec("South NC", "Bookmobile", "Consertar e escoltar ônibus-biblioteca antigo de Atascadero até Night City para Nostradamus."),
        PlotHookSpec("South NC", "Cops vs Zoners", "NCPD tenta infiltrar o movimento Zoner — ou os Zoners contratam caça ao espião."),
        PlotHookSpec("South NC", "Finding Joy", "Prefeito Garven Haakensen teve sua protegida Melinda Joy sequestrada por rival político."),
        PlotHookSpec("South NC", "Missing Persons", "Detetive amador Baker Street investiga desaparecimentos com pistas da GunMart e grafite de circo."),
        PlotHookSpec("South NC", "The Slam-a-thon", "Torneio vale-tudo de luta livre no Slammer desperta rivalidade e apostas com o Redline."),
        // University District (5)
        PlotHookSpec("University Dist.", "5 Minutes and 29 Seconds", "Philharmonic Vampyres querem sequestrar todas as rádios por exatamente 5m29s."),
        PlotHookSpec("University Dist.", "Broadcast Interrupted", "Sinal pirata estilo 1987 invade aleatoriamente a Rádio NCU."),
        PlotHookSpec("University Dist.", "Creeping Vines", "Vinhas mutantes da Biotechnica rompem concreto do campus NCU; universidade quer solução discreta."),
        PlotHookSpec("University Dist.", "Out for Justice", "Membro das Princesses of Justice desapareceu em vingança suicida solitária."),
        PlotHookSpec("University Dist.", "Protesting the Kosmos", "Clínica comunitária incita protesto estudantil na NCU como cobertura para roubar aparelho médico."),
        // Upper Marina (5)
        PlotHookSpec("Upper Marina", "Animal Collector", "Colecionador excêntrico paga pelo drone Zhirafa GRAF3 histórico da AllNations Construction."),
        PlotHookSpec("Upper Marina", "Bird Hunt", "Massagista oferece favores pela rara estatueta Series 2 Kerry Eurodyne Goosetopia em escultura."),
        PlotHookSpec("Upper Marina", "Going Up/Going Down", "Obras no Megabuilding H8 dividem moradores pró e contra habitação acessível em confronto pago."),
        PlotHookSpec("Upper Marina", "A Roof With a View", "Torcedores constroem arquibancadas precárias em contêineres acima dos outdoors do estádio."),
        PlotHookSpec("Upper Marina", "Water Wars", "Família Skiv comprou Kraken Line e está destruindo táxis aquáticos rivais WaveMo."),
        // Watson Development (5)
        PlotHookSpec("Watson Dev.", "Chicken Tonight", "Jantar exclusivo de frango real e amendoim no Divines Tastes vira palco de reserva VIP ou assassinato."),
        PlotHookSpec("Watson Dev.", "Do You Accept the Quest?", "Jogadores extremos pagam para invadir servidores da Segotari e turbinar seu personagem em ELO."),
        PlotHookSpec("Watson Dev.", "The Grand Prix", "Corrida ilegal sem regras em Watson com rota enviada 5 minutos antes da largada."),
        PlotHookSpec("Watson Dev.", "The Oil War Never Changes", "Execs rivais da Petrochem e SovOil pagam para arruinar reputação (sem matar) o oponente."),
        PlotHookSpec("Watson Dev.", "Tryst Pics", "Capitão da Polícia do 3º Distrito usa Mídia de fachada para conseguir fotos comprometedoras do prefeito.")
    )

    val districtsPt: List<String> = allPlotHooks.map { it.districtPt }.distinct()
}
