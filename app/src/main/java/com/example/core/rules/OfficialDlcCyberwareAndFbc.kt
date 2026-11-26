package com.example.core.rules

data class OfficialBioexoticSpec(
    val name: String,
    val costEb: Int,
    val humanityLossTotal: Int,
    val coreComponentsPt: String
)

data class OfficialFbcBodySpec(
    val name: String,
    val roleFocusPt: String,
    val costEb: Int,
    val humanityLossTotal: Int,
    val bodyStat: Int,
    val moveStat: Int,
    val sp: Int,
    val highlightsPt: String
)

/**
 * Interface RED Vol. 2–3 Cyberware, Exotics of 2045 (17 packages),
 * Going Metal Full Body Conversions (13 FBC bodies + core rules),
 * and Old Guns Never Die conversion guide (Vol. 1).
 */
object OfficialDlcCyberwareAndFbc {

    val dlcCyberware: List<OfficialCyberwareSpec> = listOf(
        // Vol 2: Exotics of 2045 + Vol 3: Must Have Cyberware Deals + 12 Days of Cybermas + Going Metal
        OfficialCyberwareSpec("Controlador de Apetite", "Appetite Controller", CyberwareCategory.INTERNAL, "Mall", 500, PriceCategory.EXPENSIVE, 7, "2d6", notesPt = "Suprime sensação de fome; evita fadiga de refeição perdida por dias."),
        OfficialCyberwareSpec("Mandíbula de Combate (Combat Jaw)", "Combat Jaw", CyberwareCategory.EXTERNAL, "Hospital", 500, PriceCategory.EXPENSIVE, 14, "4d6", notesPt = "Arma Branca Muito Pesada oculta (4d6 mordida); parte de pacotes Exóticos."),
        OfficialCyberwareSpec("Cauda de Combate (Combat Tail)", "Combat Tail", CyberwareCategory.BORGWARE, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", notesPt = "Arma Branca Pesada (3d6 ROF 2) + membro prensil adicional."),
        OfficialCyberwareSpec("Cibermembro Extra-Articulado", "Extra-Jointed Cyberlimb Upgrade", CyberwareCategory.CYBERLIMBS, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", notesPt = "+2 em Contorcionismo por membro (até +8)."),
        OfficialCyberwareSpec("Sopro de Fogo (Firebreather)", "Firebreather", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", notesPt = "Espingarda oculta na garganta (1 cartucho incendiário)."),
        OfficialCyberwareSpec("Monitor Heurístico de Saúde", "Heuristic Health Monitor", CyberwareCategory.FASHIONWARE, "Mall", 500, PriceCategory.EXPENSIVE, 0, "0 (N/A)", notesPt = "Exibe PV exatos, status de ferimento e toxinas via Biomonitor/Agent."),
        OfficialCyberwareSpec("NeuLíngua (NeuTongue)", "NeuTongue", CyberwareCategory.INTERNAL, "Mall", 100, PriceCategory.PREMIUM, 7, "2d6", notesPt = "Modula sabor e textura de comidas/Kibble à escolha do usuário."),
        OfficialCyberwareSpec("Coprocessador de Reflexos", "Reflex Co-Processor", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 14, "4d6", 1, notesPt = "Requer Neural Link. Permite Esquiva de Balas mesmo com REF < 8!"),

        // Vol 3: Must Have Cyberware Deals
        OfficialCyberwareSpec("Cybertravesseiro (Cyberpillow)", "Cyberpillow", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Almofada inflável oculta no ombro/braço para descanso imediato."),
        OfficialCyberwareSpec("Tela Externa (External Vidscreen)", "External Vidscreen", CyberwareCategory.EXTERNAL, "Clinic", 100, PriceCategory.PREMIUM, 7, "2d6", notesPt = "Tela LED flexível implantada na pele para vídeos e interfaces."),
        OfficialCyberwareSpec("Palma Projetora Holo", "Holo Projector Palm", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", notesPt = "Projeta hologramas e chamadas de vídeo diretamente da palma."),
        OfficialCyberwareSpec("Display de Abates (Kill Display)", "Kill Display", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Contador luminoso subdérmico personalizável."),
        OfficialCyberwareSpec("Unhas Turn-On-Show-Off", "Lead's Turn-On-Show-Off Nails", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Unhas retráteis brilhantes com controle de cor/padrão."),
        OfficialCyberwareSpec("Olho de Humor (Mood Eye)", "Mood Eye", CyberwareCategory.FASHIONWARE, "Mall", 100, PriceCategory.PREMIUM, 0, "0 (N/A)", notesPt = "Muda cor da íris conforme emoção ou programação."),
        OfficialCyberwareSpec("Ciberpé Ajuste Perfeito", "PerfectFit Cyberfoot", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 2, "1d6/2 (cima)", notesPt = "Adapta formato do pé para calçados, escalada leve e conforto."),
        OfficialCyberwareSpec("Aquecedor Pessoal de Kibble", "PersonalPak KibbleWarmer", CyberwareCategory.INTERNAL, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", notesPt = "Esquenta ração e água na mão/antebraço a qualquer hora."),
        OfficialCyberwareSpec("Triturador Pessoal PSI", "Pursuit Security Inc. Personal Shredder", CyberwareCategory.CYBERLIMBS, "Clinic", 100, PriceCategory.PREMIUM, 3, "1d6", notesPt = "Destrói chips, papel e cartões com segurança no ciberbraço."),
        OfficialCyberwareSpec("Revestimento Patrocinado", "Sponsored Covering", CyberwareCategory.FASHIONWARE, "Mall", 50, PriceCategory.COSTLY, 0, "0 (N/A)", notesPt = "Exibe logotipo corporativo no corpo; paga 20eb por mês ao usuário."),

        // Vol 3: 12 Days of Cybermas
        OfficialCyberwareSpec("ChainRipp", "ChainRipp", CyberwareCategory.CYBERLIMBS, "Hospital", 500, PriceCategory.EXPENSIVE, 14, "4d6", 4, notesPt = "Motosserra retrátil (4 slots no Cyberarm): Arma Branca Muito Pesada 4d6 Exc. Qualidade quando ligada."),
        OfficialCyberwareSpec("Cybermatrix Gang Jazzler", "Cybermatrix Gang Jazzler", CyberwareCategory.EXTERNAL, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", notesPt = "Dispara choque atordoante corpo a corpo (como Stun Baton oculto)."),
        OfficialCyberwareSpec("Cyclops International Bug Eye", "Cyclops International Bug Eye", CyberwareCategory.BORGWARE, "Hospital", 500, PriceCategory.EXPENSIVE, 14, "4d6", 0, true, "Olho único grande com 5 slots de Ciberópticos (-2 Aparência salvo camuflado)."),
        OfficialCyberwareSpec("DeathTrance", "DeathTrance", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 3, "1d6", 1, notesPt = "Simula morte clínica indetectável a exames superficiais (DV17 Medicina/Paramédico)."),
        OfficialCyberwareSpec("Kiroshi MonoVision", "Kiroshi MonoVision", CyberwareCategory.BORGWARE, "Hospital", 500, PriceCategory.EXPENSIVE, 14, "4d6", 0, true, "Visor óptico contínuo com 3 slots de opções; bloqueia flashbang parcial."),
        OfficialCyberwareSpec("Kiroshi OptiShield", "Kiroshi OptiShield", CyberwareCategory.CYBEROPTICS, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", 1, notesPt = "Escudo ocular blindado retrátil; protege contra flashbang e estilhaços."),
        OfficialCyberwareSpec("Psiberstuff Watch-Man", "Psiberstuff Watch-Man", CyberwareCategory.CYBEROPTICS, "Mall", 100, PriceCategory.PREMIUM, 3, "1d6", 1, notesPt = "Exibe interface tática e radar de áudio/proximidade básica no canto do olho."),
        OfficialCyberwareSpec("Poser Chip", "Poser Chip", CyberwareCategory.NEURALWARE, "Clinic", 500, PriceCategory.EXPENSIVE, 14, "4d6", 1, notesPt = "500eb padrão / 1000eb celebs; imita trejeitos, voz e biometria de uma persona (+4 Atuação/Disfarce)."),
        OfficialCyberwareSpec("Radline Blitzkrieg Arc-Thrower", "Radline Blitzkrieg Arc-Thrower Cyberarm", CyberwareCategory.CYBERLIMBS, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 4, notesPt = "Cyberarm com canhão de arco elétrico (3d6 ignora PA metálica leve / EMP local)."),
        OfficialCyberwareSpec("Raven MicroWaldo", "Raven Microcybernetics MicroWaldo", CyberwareCategory.CYBERLIMBS, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", 2, notesPt = "+1 permanente em Cirurgia e micro-eletrônica (2 slots no Cyberarm)."),
        OfficialCyberwareSpec("Sycust FleshWeave", "Sycust FleshWeave", CyberwareCategory.EXTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", notesPt = "Nanotrama PA 7 (Corpo/Cabeça) que regenera PA completa em apenas 10 minutos!"),
        OfficialCyberwareSpec("Wyzard Romanova Cyberlegs (Par)", "Wyzard Technologies Romanova Cyberlegs", CyberwareCategory.CYBERLIMBS, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 14, "4d6", 0, true, "Par elegante de Cyberlegs (+2 Estilo) já pareadas com Talon Foot."),

        // Vol 3: Going Metal (FBC Cyberware & Upgrades)
        OfficialCyberwareSpec("Biosistema (Biosystem • FBC Base)", "Biosystem", CyberwareCategory.BORGWARE, "Hospital", 10000, PriceCategory.SUPER_LUXURY, 14, "4d6", 0, true, "Peça obrigatória que preserva cérebro e órgãos em solução nutritiva (100eb/mês)."),
        OfficialCyberwareSpec("Cibercrânio (Cyberskull)", "Cyberskull", CyberwareCategory.BORGWARE, "Hospital", 5000, PriceCategory.LUXURY, 14, "4d6", 0, true, "Cabeça metálica FBC com espaço p/ Ciberópticos/áudio; dobra slots em FBC."),
        OfficialCyberwareSpec("Dragoon Plating (PA 15)", "Dragoon Plating", CyberwareCategory.EXTERNAL, "Hospital", 5000, PriceCategory.LUXURY, 14, "4d6", notesPt = "FBC Externo: PA 15 Corpo e Cabeça (-2 penalidade armadura)."),
        OfficialCyberwareSpec("Dragoon Plating Metalgear (PA 18)", "Dragoon Plating Metalgear", CyberwareCategory.EXTERNAL, "Hospital", 15000, PriceCategory.SUPER_LUXURY, 14, "4d6", notesPt = "FBC Externo pesado: PA 18 Corpo e Cabeça (-3 penalidade armadura)."),
        OfficialCyberwareSpec("Revestimento FBC Brimstone", "FBC Brimstone Coating", CyberwareCategory.EXTERNAL, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", notesPt = "FBC: imunidade a fogo intenso, gás lacrimogêneo e chamas comuns."),
        OfficialCyberwareSpec("Revestimento FBC Camaleão", "FBC Chameleon Coating", CyberwareCategory.EXTERNAL, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", notesPt = "FBC: camuflagem óptica adaptativa (+4 Furtividade imóvel)."),
        OfficialCyberwareSpec("Revestimento FBC EMP", "FBC EMP Coating", CyberwareCategory.EXTERNAL, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", notesPt = "FBC: blinda todo o chassi contra EMP e Microwaver."),
        OfficialCyberwareSpec("Escultura Gemini (FBC)", "FBC Gemini Sculpt", CyberwareCategory.FASHIONWARE, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 0, "0 (N/A)", notesPt = "FBC: revestimento RealSkinn hiper-realista indistinguível de humano."),
        OfficialCyberwareSpec("Revestimento FBC Bodyweight", "FBC Integrated Bodyweight Coating", CyberwareCategory.EXTERNAL, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 7, "2d6", notesPt = "FBC: integra slots de Hardware de Cyberdeck diretamente no chassi."),
        OfficialCyberwareSpec("Subdermal Pesado (PA 13)", "Heavy Subdermal Plating", CyberwareCategory.EXTERNAL, "Hospital", 5000, PriceCategory.LUXURY, 14, "4d6", notesPt = "PA 13 Corpo e Cabeça sem penalidade de armadura."),
        OfficialCyberwareSpec("Hidráulica Interna (Mín 4)", "Internal Hydraulics Upgrade", CyberwareCategory.INTERNAL, "Hospital", 1000, PriceCategory.VERY_EXPENSIVE, 3, "1d6", notesPt = "FBC: define REF/DES/MOV mínimos em 4 (Tuned: 5k eb = 6; Perfected: 20k eb = 8)."),
        OfficialCyberwareSpec("Armação Linear Ω (Omega Interna)", "Linear Frame Omega Internal", CyberwareCategory.BORGWARE, "Hospital", 10000, PriceCategory.SUPER_LUXURY, 28, "8d6", 0, true, "2x slots Borgware. Eleva COR p/ 16! Usa tabela de PV até 75/80 PV."),
        OfficialCyberwareSpec("Ciberpé Preênsil", "Prehensile Cyberfoot", CyberwareCategory.CYBERLIMBS, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 3, "1d6", notesPt = "Permite segurar e manipular armas ou objetos com o pé."),
        OfficialCyberwareSpec("Propulsores Gravidade Zero", "Zero Gravity Thrusters", CyberwareCategory.CYBERLIMBS, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 3, "1d6", notesPt = "Manobra completa em microgravidade/órbita (MOV completo no vácuo)."),
        OfficialCyberwareSpec("Upgrade Cyberdeck Integrado", "Integrated Cyberdeck Upgrade", CyberwareCategory.INTERNAL, "Clinic", 500, PriceCategory.EXPENSIVE, 7, "2d6", notesPt = "Integra slots de Cyberdeck no Biosystem/FBC."),
        OfficialCyberwareSpec("Cibermembro Reforçado", "Reinforced Cyberlimb Upgrade", CyberwareCategory.CYBERLIMBS, "Clinic", 1000, PriceCategory.VERY_EXPENSIVE, 3, "1d6", notesPt = "Aumenta resistência estrutural de Cyberarm/Cyberleg (+PV/PA ao membro).")
    )

    val bioexoticPackages: List<OfficialBioexoticSpec> = listOf(
        OfficialBioexoticSpec("AquaForm", 4500, 22, "Guelras, Web Foot, pele aquática, Olhos Baixa Luz (+31 dias no Zoo cobrem Estilo de Vida)."),
        OfficialBioexoticSpec("BigKatt", 7000, 36, "Garras (Rippers), Combat Jaw, patas ágeis, Audição/Olfato felinos e pelagem Exótica."),
        OfficialBioexoticSpec("Bughouse", 6600, 48, "Múltiplos membros/olhos MultiOptic, exoesqueleto PA, sensores químicos e antenas."),
        OfficialBioexoticSpec("Demonique", 2100, 14, "Chifres, Shift Tacts vermelhos, Vampyres, pele escamada/avermelhada e cauda cosmética."),
        OfficialBioexoticSpec("Draconic FantaForm", 11000, 34, "Escamas PA, Firebreather na garganta, Vampyres/garras e feições dracônicas."),
        OfficialBioexoticSpec("Dwarvish FantaForm", 2100, 12, "Estrutura robusta reforçada, Low Light Vision e Resistência Exótica."),
        OfficialBioexoticSpec("Elvish FantaForm", 2100, 16, "Orelhas alongadas, Audição Amplificada, Image Enhance e proporções esguias."),
        OfficialBioexoticSpec("Embrace Rodentia", 2100, 16, "Dentes incisivos, Olfactory Boost, reflexos ágeis e cauda/orelhas de roedor."),
        OfficialBioexoticSpec("KatNip", 2200, 18, "Scratchers retráteis, visão noturna, orelhas/cauda felinas e agilidade."),
        OfficialBioexoticSpec("LagoForm (Coelho)", 2100, 16, "Orelhas amplificadas, Jump Boosters e pelagem ágil."),
        OfficialBioexoticSpec("LupaForm (Lobo)", 2200, 20, "Olfactory Boost, Audio Amplified, Vampyres/garras e feições lupinas."),
        OfficialBioexoticSpec("Merish FantaForm (Sereia)", 3600, 24, "Guelras, propulsão aquática/Web Foot e escamas iridescentes."),
        OfficialBioexoticSpec("NeuHuman", 4000, 32, "Reconfiguração completa pós-humana de traços faciais, pele sintética e sentidos."),
        OfficialBioexoticSpec("Ogrish FantaForm", 4000, 12, "Enxerto muscular maciço (+COR), presas inferiores e porte colossal."),
        OfficialBioexoticSpec("Orcish FantaForm", 2500, 12, "Presas Vampyres/reforçadas, musculatura densa e visão noturna."),
        OfficialBioexoticSpec("Serpentize Yourself", 2100, 16, "Vampyres com veneno, pele reptiliana, Infrared Vision e NeuTongue bífida."),
        OfficialBioexoticSpec("UrsaForm (Urso)", 8200, 52, "Wolvers pesadas, COR reforçado, subdermal/pelagem grossa e mandíbula de urso.")
    )

    val fbcBodies: List<OfficialFbcBodySpec> = listOf(
        OfficialFbcBodySpec("Rocklin Alpha Class", "Corpo FBC Básico", 9900, 21, 12, 6, 7, "Modelo de entrada: Biosystem + Linear Sigma + SkinWeave PA 7; ótimo custo-benefício."),
        OfficialFbcBodySpec("Raven Microcyb Gemini", "Infiltração / Social", 10200, 14, 10, 6, 7, "Gemini Sculpt RealSkinn indetectável como ciborgue completo em público."),
        OfficialFbcBodySpec("Trauma Team Kildare", "Resgate Médico", 12200, 39, 12, 6, 11, "Medscanner integrado, suprimento aéreo e blindagem PA 11 p/ zona quente."),
        OfficialFbcBodySpec("Dynalar Aquarius", "Operações Aquáticas", 13000, 37, 12, 6, 11, "Guelras, Web Foot e vedação profunda p/ combate e salvamento submarino."),
        OfficialFbcBodySpec("Militech Enforcer", "Segurança Tática", 16800, 53, 14, 6, 13, "Heavy Subdermal PA 13 + Linear Beta COR 14 para linhas de frente."),
        OfficialFbcBodySpec("Cybermatrix Copernicus", "Órbita / Gravidade Zero", 17300, 52, 12, 6, 11, "Zero Gravity Thrusters, radiação/oxigênio selado p/ Highriders."),
        OfficialFbcBodySpec("Dynalar Brimstone", "Risco Ambiental / Fogo", 17600, 53, 12, 6, 13, "Brimstone Coating imune a fogo e gases tóxicos; combate em incêndios."),
        OfficialFbcBodySpec("Moore Tech Sheol", "Demolição / Engenharia", 19600, 32, 14, 5, 13, "Tool Hand duplo, resistência térmica e suporte estrutural de alto torque."),
        OfficialFbcBodySpec("NovelTech Samson", "Carga Pesada Industrial", 20500, 39, 16, 4, 13, "Linear Frame Omega COR 16; levanta cargas industriais e suporta dano massivo."),
        OfficialFbcBodySpec("Militech Spyder 2.0", "Furtividade & Recon", 24900, 73, 12, 8, 11, "Chameleon Coating, Extra-Jointed + ótica avançada de infiltração."),
        OfficialFbcBodySpec("Raven Microcyb Wiseman", "Netrunner Full-Borg", 27480, 70, 10, 6, 11, "Integrated Bodyweight + múltiplos slots Neural/Deck + blindagem EMP."),
        OfficialFbcBodySpec("Militech Eclipse", "Operações Especiais", 29200, 67, 14, 8, 13, "Perfected Hydraulics, Chameleon + supressão de assinatura térmica/sônica."),
        OfficialFbcBodySpec("Militech Dragoon Revised", "Tanque de Guerra Pesado", 35000, 64, 16, 6, 18, "Dragoon Metalgear PA 18 + Linear Frame Omega COR 16 + chassi militar.")
    )

    val fbcCoreRulesPt: List<String> = listOf(
        "Atributos Mentais intactos: INT, CAR, TEC, VON, SOR e EMP permanecem os originais; COR, HP e Teste de Morte vêm do corpo FBC.",
        "Zero HL extra em peças padrão de chassi: Cyberarm, Cyberaudio, Cybereye, Cyberleg, Cyberskull, Internal Frame e Neural Link custam 0 HL no FBC (slots dobrados).",
        "Imunidades: FBC é imune a 9 Ferimentos Críticos orgânicos (ex: Pulmão Perfurado, Objeto Estranho, Desmembramentos biológicos padrão) e ignora penalidades de Ferimento comuns conforme Biosystem.",
        "Manutenção: exige Solução Cerebral Rica em Glicose (100eb/mês, grátis se tiver Estilo de Vida Good Prepack+). Reparo do chassi: Cybertech DV15 (4h). Troca de corpo: Cybertech/Cirurgia DV17 (4h).",
        "Tabela PV Linear Frame Omega: COR 16 = 55 a 75 PV (10 + 5×⌈(16+VON)/2⌉) | COR 17 = 60 a 80 PV."
    )

    val oldGunsNeverDieStepsPt: List<String> = listOf(
        "Passo 0: Se a arma CP2020 usa munição exótica fora de Pistola/Rifle/Cartucho/Flecha, vira Arma Exótica no RED.",
        "Passo 1: Combine a categoria de arma (ex: Médio/Pesado/Muito Pesado, SMG, Rifle, Espingarda) à equivalente de Cyberpunk RED.",
        "Passo 2 (WA): WA ≤ 0 vira Qualidade Padrão (0 mod); WA ≥ +1 vira Qualidade Excelente (+1 no Ataque).",
        "Passo 3 (UR): Armas Unreliable (UR) ganham 'Baixa Confiabilidade' (travam em resultado natural 1 no 1d10).",
        "Passo 4 (Preço): O maior modificador define a categoria de preço — é possível ter arma Excelente que trava em 1 se tiver +1 WA e UR juntas!",
        "Passo 5–6: Substitua miras/acessórios por equivalentes do RED (máx 3 slots) e defina custo pelo padrão de mercado."
    )
}
