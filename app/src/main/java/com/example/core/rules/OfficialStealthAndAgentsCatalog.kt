package com.example.core.rules

import com.example.data.local.entity.InventoryItemType

enum class FlashOfLuckTier(
    val labelPt: String,
    val maxItemEb: Int,
    val luckCost: Int,
    val summaryPt: String
) {
    SIMPLE("Simples (até 100eb)", 100, 2, "Pequeno ajuste na cena ou item comum no bolso (até 100eb)."),
    UNLIKELY("Improvável (até 500eb)", 500, 4, "Vantagem tática relevante, distração ou item Caro (até 500eb)."),
    IMPROBABLE("Improvável Extremo (até 1.000eb)", 1000, 8, "Coincidência cinematográfica ou peça Muito Cara (até 1.000eb).")
}

enum class AgentAccessMode(val labelPt: String) {
    REMOTE_BREACHER("Remoto via Breacher (20m LOS)"),
    PHYSICAL_ACCESS("Acesso Físico Direto (sem Breacher)")
}

enum class AgentQualityTier(
    val labelPt: String,
    val remoteDv: Int,
    val physicalDv: Int
) {
    POOR("Baixa Qualidade (PQ)", 17, 15),
    STANDARD("Qualidade Padrão (SQ)", 21, 17),
    EXCELLENT("Qualidade Excelente (EQ)", 24, 21);

    fun dvFor(mode: AgentAccessMode): Int =
        if (mode == AgentAccessMode.REMOTE_BREACHER) remoteDv else physicalDv
}

enum class AgentImplantCommandTier(
    val labelPt: String,
    val durationTextPt: String
) {
    BASIC("Função Básica (ligar lanterna, tocar alarme)", "3 segundos (1 rodada)"),
    CORE("Função Central (chamar táxi, enviar mensagem/pagamento)", "1 minuto (20 rodadas)"),
    SECURE("Função Segura (alterar permissões, apagar logs)", "5 minutos (100 rodadas)")
}

/**
 * Complete rules, gear, apps, cyberware and hacking tables from:
 * - Going Quiet (Cyber_red-going_quiet, 7 pp.): Flash of Luck, Stealth Netrunning, Harry the Shrimp's 14 items
 * - All About Agents (Upgrade_do_Agente, 11 pp.): Agent Hacking (Breacher, Crunch Whistle, DVs, Commands), 11 Apps, 4 External + 4 Internal Models
 */
object OfficialStealthAndAgentsCatalog {

    val flashOfLuckRulesPt: List<String> = listOf(
        "Limite oficial: máximo 1 Flash of Luck por Beat de missão e 3 por Missão completa.",
        "Em combate, acionar um Flash of Luck consome 1 Ação Padrão.",
        "Gaste Sorte (2/4/8 pts) para declarar que já possuía um item (até 100/500/1.000eb) ou preparou um detalhe na cena.",
        "Se vetado pelo Mestre/Oráculo, a Sorte é reembolsada. Se exigir teste e falhar, o Flash conta como gasto e a Sorte não volta."
    )

    val stealthNetrunningRulesPt: List<String> = listOf(
        "Quietly Jack In: gaste +1 Ação NET extra ao conectar e role Interface + 1d10 contra Interface + 1d10 de todos os Watchers ativos.",
        "Quebra de Furtividade: assumir Controle de um Nó ou atacar Black ICE / Watcher revela sua presença imediatamente.",
        "Permitido em Furtividade: copiar Arquivos (Files) ou implantar Vírus silenciosamente sem revelar sua posição.",
        "Encontrar Black ICE em Furtividade: role Interface + Bônus Cloak + 1d10 vs PER do Black ICE + 1d10 (no lugar de Speed). ICE que não detecta não entra na Fila de Iniciativa.",
        "Watcher vs Stealth: se um Watcher gastar 1 Ação NET para procurar (1x/Turno), role Interface + Cloak + 1d10 vs Interface + Pathfinder + 1d10 do Watcher. Reestabelecer Stealth exige Jack Out + Quietly Jack In."
    )

    val agentHackingRulesPt: List<String> = listOf(
        "Hacking Remoto exige Breacher (500eb, alcance 20m / 10 quadras em Linha de Visão; não afeta Agents Internos nem celulares descartáveis).",
        "Tempo de invasão: 1 minuto usando Eletrônica/Segurança (+2 com informações obtidas por engenharia social). Falha alerta o alvo após 30 segundos.",
        "DVs Remotos (Breacher): PQ DV17 • Padrão DV21 • Excelente DV24. Acesso Físico (sem Breacher): PQ DV15 • Padrão DV17 • Excelente DV21.",
        "Crunch Whistle (100eb Hardware): soma seu Rank de Interface ao teste de Eletrônica/Segurança ao hackear Agents.",
        "Implantar Comandos (1 por invasão, dura até 24h; reiniciar por 5 min limpa): Básica (3s/1 rod.), Central (1m/20 rod.), Segura (5m/100 rod.)."
    )

    // Going Quiet — 13 non-cyberware gear/app items from Harry the Shrimp
    val goingQuietGear: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Composto Binário (Binary Compound)", "Binary Compound",
            InventoryItemType.AMMO, "Furtividade (Going Quiet)", PriceCategory.COSTLY, 50, damageDice = 6,
            notesPt = "Tubo 50eb; 2 rodadas p/ aplicar; causa 6d6/rodada só contra cobertura por 6 rodadas (18s)."
        ),
        OfficialGearItem(
            "Macacão Corporativo (Branded Jumpsuit)", "Branded Jumpsuit",
            InventoryItemType.GEAR, "Disfarce (Going Quiet)", PriceCategory.PREMIUM, 100,
            notesPt = "Uniforme autêntico: Baixo 50eb | Moderado 100eb | Alto 500eb | Extremo 1.000eb."
        ),
        OfficialGearItem(
            "Manga de Ocultação (Concealment Sleeve)", "Concealment Sleeve",
            InventoryItemType.GEAR, "Furtividade (Going Quiet)", PriceCategory.EXPENSIVE, 500,
            notesPt = "Revestimento sob medida que faz cibermembro passar por biológico em scanners comuns."
        ),
        OfficialGearItem(
            "Luva de Impressão Digital (Handprint Glove)", "Handprint Glove",
            InventoryItemType.GEAR, "Infiltração (Going Quiet)", PriceCategory.EXPENSIVE, 500,
            notesPt = "Engana leitor biométrico com padrão clonado e não deixa digitais próprias."
        ),
        OfficialGearItem(
            "Scanner de Digitais (App)", "Handprint Scanner",
            InventoryItemType.GEAR, "App (Going Quiet)", PriceCategory.EVERYDAY, 20,
            notesPt = "Captura digitais: 3 rodadas DV13 Briga ou 5 impressões DV13 Criminologia."
        ),
        OfficialGearItem(
            "Microfone a Laser", "Laser Microphone",
            InventoryItemType.GEAR, "Espionagem (Going Quiet)", PriceCategory.PREMIUM, 100,
            notesPt = "Escuta conversas através de vidros a até 300m (ou cobertura fina)."
        ),
        OfficialGearItem(
            "Traje Furtivo Militech M97 Ghostsuit", "Militech M97 Ghostsuit",
            InventoryItemType.ARMOR, "Furtividade (Going Quiet)", PriceCategory.LUXURY, 5000, stoppingPower = 0,
            notesPt = "Imóvel por 1 min ativa camuflagem óptica (Percepção DV17 p/ notar; quebra ao mover >2m/rodada)."
        ),
        OfficialGearItem(
            "Perfume Nymph", "Nymph Perfume",
            InventoryItemType.GEAR, "Social/Furtividade (Going Quiet)", PriceCategory.EXPENSIVE, 500,
            notesPt = "Frasco 500eb (50 borrifadas, dura 1h); >3 rodadas a ≤6m exige Resistir DV13 ou -2 Percepção Humana por 1h."
        ),
        OfficialGearItem(
            "Microfone Parabólico", "Parabolic Microphone",
            InventoryItemType.GEAR, "Espionagem (Going Quiet)", PriceCategory.PREMIUM, 100,
            notesPt = "Capta áudio direcional nítido a até 30m de distância."
        ),
        OfficialGearItem(
            "Kit Profissional de Disfarce", "Professional Disguise Kit",
            InventoryItemType.GEAR, "Disfarce (Going Quiet)", PriceCategory.PREMIUM, 100,
            notesPt = "Concede +2 em Aparência Pessoal e disfarces completos."
        ),
        OfficialGearItem(
            "Lente de Retina (Retina Lens)", "Retina Lens",
            InventoryItemType.GEAR, "Infiltração (Going Quiet)", PriceCategory.EXPENSIVE, 500,
            notesPt = "Lente de contato com padrão retinal falsificado para leitores ópticos."
        ),
        OfficialGearItem(
            "Scanner de Retina (App)", "Retina Scanner",
            InventoryItemType.GEAR, "App (Going Quiet)", PriceCategory.COSTLY, 50,
            notesPt = "Captura padrão ocular à força/proximidade em 5 rodadas com DV13 Briga."
        ),
        OfficialGearItem(
            "Headset Zetatech Tank Commander", "Zetatech Tank Commander Headset",
            InventoryItemType.GEAR, "Comunicação (Going Quiet)", PriceCategory.PREMIUM, 100,
            notesPt = "Microfone subvocal na garganta + fone ANC com cancelamento de ruído tático."
        )
    )

    // All About Agents — Hardware, 11 Apps & 4 External Agent Models
    val agentsGearAndApps: List<OfficialGearItem> = listOf(
        OfficialGearItem(
            "Breacher (Invasor de Agents)", "Breacher",
            InventoryItemType.GEAR, "Hacking Agent (Upgrade)", PriceCategory.EXPENSIVE, 500,
            notesPt = "Alcance 20m LOS (Agents externos). Permite invadir Agents remotamente em 1 min."
        ),
        OfficialGearItem(
            "Crunch Whistle (Hardware Deck)", "Crunch Whistle",
            InventoryItemType.GEAR, "Hardware Deck (Upgrade)", PriceCategory.PREMIUM, 100,
            notesPt = "Soma seu Rank de Interface ao teste de Eletrônica/Segurança ao hackear Agents."
        ),
        // 4 External Agent models
        OfficialGearItem(
            "Segotari Double Agent (Agent PQ)", "Segotari Double Agent",
            InventoryItemType.GEAR, "Agent Externo (PQ)", PriceCategory.COSTLY, 50,
            notesPt = "Agent Baixa Qualidade (+1/básico; DV17 remoto / DV15 físico p/ hackear)."
        ),
        OfficialGearItem(
            "Zetatech Grade A (Agent Padrão)", "Zetatech Grade A",
            InventoryItemType.GEAR, "Agent Externo (SQ)", PriceCategory.PREMIUM, 100,
            notesPt = "Agent Padrão confiável (+2 Arquivos/Estilo; DV21 remoto / DV17 físico)."
        ),
        OfficialGearItem(
            "Zetatech Grade A-Plus (Agent EQ Holo)", "Zetatech Grade A-Plus",
            InventoryItemType.GEAR, "Agent Externo (EQ)", PriceCategory.EXPENSIVE, 500,
            notesPt = "Agent Excelente c/ projeção holográfica (+2 Arquivos/Estilo; DV24 remoto / DV21 físico)."
        ),
        OfficialGearItem(
            "Wyzard Merlyn (Agent de Pulso EQ)", "Wyzard Technologies Merlyn",
            InventoryItemType.GEAR, "Agent Externo (EQ)", PriceCategory.VERY_EXPENSIVE, 1000,
            notesPt = "Agent Excelente de pulso (+1 extra em Estilo & Vestuário; DV24 remoto / DV21 físico)."
        ),
        // 11 Apps
        OfficialGearItem("Ask Alex Anything (App)", "Ask Alex Anything", InventoryItemType.GEAR, "App Agent (Upgrade)", PriceCategory.CHEAP, 0, notesPt = "Assistente de busca social/Data Pool gratuito (0eb)."),
        OfficialGearItem("BabelChat (App)", "BabelChat", InventoryItemType.GEAR, "App Agent (Upgrade)", PriceCategory.CHEAP, 0, notesPt = "Tradutor simultâneo de texto/áudio gratuito para idiomas comuns (0eb)."),
        OfficialGearItem("BangMe! (App)", "BangMe!", InventoryItemType.GEAR, "App Agent (Upgrade)", PriceCategory.CHEAP, 10, notesPt = "Aplicativo de encontros rápidos e contatos de rua."),
        OfficialGearItem("Big SAAI (App)", "Big SAAI", InventoryItemType.GEAR, "App Agent (Upgrade)", PriceCategory.PREMIUM, 100, notesPt = "Substitui o bônus +2 padrão do Agent por +3 em Pesquisa em Arquivos!"),
        OfficialGearItem("Brainwasher (App)", "Brainwasher", InventoryItemType.GEAR, "App Terapia (Upgrade)", PriceCategory.COSTLY, 50, notesPt = "50eb/mês; 1h/dia por 7 dias antes de Terapia -> 1d10: par = -1d6 cura, ímpar = +1d6 cura."),
        OfficialGearItem("Elflines Online Elftracker (App)", "Elflines Online Elftracker", InventoryItemType.GEAR, "App ELO (Upgrade)", PriceCategory.COSTLY, 50, notesPt = "Rastreia leilões, personagens e guildas no MMORPG Elflines Online."),
        OfficialGearItem("The Garden (App)", "The Garden", InventoryItemType.GEAR, "App Agent (Upgrade)", PriceCategory.CHEAP, 0, notesPt = "Rede social e marketplace comunitário local gratuito (0eb)."),
        OfficialGearItem("HusTool (App)", "HusTool", InventoryItemType.GEAR, "App Bicos (Upgrade)", PriceCategory.EVERYDAY, 20, notesPt = "Bicos de 30 min pagam 10eb (1d10: 1 falha sem paga; 10 gera encontro aleatório)."),
        OfficialGearItem("Roach Race (App)", "Roach Race", InventoryItemType.GEAR, "App Jogo (Upgrade)", PriceCategory.EVERYDAY, 20, notesPt = "Jogo viciante e apostas digitais de corridas de baratas mutantes."),
        OfficialGearItem("WatchBox (App)", "WatchBox", InventoryItemType.GEAR, "App Alarme (Upgrade)", PriceCategory.COSTLY, 50, notesPt = "5 min p/ configurar alarme em sala 20×20m (Agents externos; Furtividade DV13 p/ burlar)."),
        OfficialGearItem("ZPost (App)", "ZPost", InventoryItemType.GEAR, "App Agent (Upgrade)", PriceCategory.CHEAP, 0, notesPt = "Microblog e feed de notícias instantâneo da Ziggurat (0eb).")
    )

    val allStealthAndAgentPurchaseGear: List<OfficialGearItem> = goingQuietGear + agentsGearAndApps

    // Cyberware additions: Translation Chip (Going Quiet) + 4 Internal Agent models (All About Agents)
    val allStealthAndAgentCyberware: List<OfficialCyberwareSpec> = listOf(
        OfficialCyberwareSpec(
            namePt = "Chip de Tradução (Translation Chip)",
            nameEn = "Translation Chip",
            category = CyberwareCategory.NEURALWARE,
            installLocation = "N/A (Chipware)",
            costEb = 1000,
            priceCategory = PriceCategory.VERY_EXPENSIVE,
            presetHl = 7,
            hlDiceFormula = "2d6",
            optionSlotsUsed = 1,
            notesPt = "Chipware (Going Quiet; req Chyron): traduz idiomas desconhecidos ao vivo em texto na visão."
        ),
        OfficialCyberwareSpec(
            namePt = "Internal Agent: MediaWare BrainGen (PQ)",
            nameEn = "MediaWare BrainGen",
            category = CyberwareCategory.CYBERAUDIO,
            installLocation = "Mall",
            costEb = 50,
            priceCategory = PriceCategory.COSTLY,
            presetHl = 3,
            hlDiceFormula = "1d6",
            optionSlotsUsed = 1,
            notesPt = "Agent Interno Baixa Qualidade (requer Cyberaudio Suite)."
        ),
        OfficialCyberwareSpec(
            namePt = "Internal Agent: Raven Microcyb Drake (SQ)",
            nameEn = "Raven Microcybernetics Drake",
            category = CyberwareCategory.CYBERAUDIO,
            installLocation = "Mall",
            costEb = 100,
            priceCategory = PriceCategory.PREMIUM,
            presetHl = 3,
            hlDiceFormula = "1d6",
            optionSlotsUsed = 1,
            notesPt = "Agent Interno Qualidade Padrão (requer Cyberaudio Suite)."
        ),
        OfficialCyberwareSpec(
            namePt = "Internal Agent: EBM PiR2 (EQ)",
            nameEn = "EBM PiR2",
            category = CyberwareCategory.CYBERAUDIO,
            installLocation = "Mall",
            costEb = 500,
            priceCategory = PriceCategory.EXPENSIVE,
            presetHl = 3,
            hlDiceFormula = "1d6",
            optionSlotsUsed = 1,
            notesPt = "Agent Interno Excelente Qualidade (requer Cyberaudio Suite)."
        ),
        OfficialCyberwareSpec(
            namePt = "Internal Agent: Rocklin Neuron (EQ Óptico)",
            nameEn = "Rocklin Augmentics Neuron",
            category = CyberwareCategory.CYBERAUDIO,
            installLocation = "Mall",
            costEb = 1000,
            priceCategory = PriceCategory.VERY_EXPENSIVE,
            presetHl = 3,
            hlDiceFormula = "1d6",
            optionSlotsUsed = 1,
            notesPt = "Agent Interno Excelente conectado ao nervo óptico para tela visual sem Chyron."
        )
    )
}
