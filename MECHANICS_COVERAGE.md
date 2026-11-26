# MECHANICS_COVERAGE — Matriz de Cobertura das Regras Oficiais

Legenda de estado:
- **Implementado**: Catálogo + Motor Determinístico + UI jogável integrados.
- **Parcial**: Catálogo presente, mas motor ou UI ainda incompletos.
- **Estrutural**: Apenas modelo de dados/catálogo para consulta, sem execução em jogo.
- **Ausente**: Ainda não construído.

| Domínio / Fonte | Subsistema | Estado | Observações Técnicas |
| :--- | :--- | :--- | :--- |
| **Core Parte 1** | 10 Atributos (`INT..EMP`) + cálculo derivado (`PV`, `Grave`, `EMP`, `HUM`, `COR` cibernético) | **Implementado** | `CharacterCalculator` + `CyberwareValidator` recalculam `COR`, `PV Máx` e `Morte` ao instalar `Grafted Muscle`/`Linear Frame`. |
| **Core Parte 1** | 66 Perícias oficiais + custos `x1`/`x2` | **Implementado** | `OfficialSkillCatalog` + `CharacterSheetScreen`. |
| **Core Parte 1** | Criação guiada (14 etapas) + Lifepath + Foto | **Ausente** | Planejado para Ciclo 3; hoje há apenas criação rápida ou 10 Pregens. |
| **Core Parte 1** | Cyberware (Option Slots, peças base, limites) | **Implementado** | `CyberwareValidator` impõe peças base, Option Slots, limite de 1 Speedware e pré-requisitos de Frames/Borgware. |
| **Core Parte 2** | Testes 1d10 (explode 10 / implode 1 / `> DV` / armadura / Editor de Dor) | **Implementado** | `SkillCheckEngine` + `DerivedCharacterMetrics` aplicam penalidade de armadura (`REF`/`DES`/`MOV`) e `Editor de Dor`. |
| **Core Parte 2** | Combate (Iniciativa, Alcance DV, Autofire, Cobertura, Críticos, Morte) | **Parcial** | `CombatEngine` e `OfficialCombatTables` completos; UI ainda não seleciona arma/munição do inventário nem aciona cobertura ou cura. |
| **Core Parte 2** | Terapia & Recuperação de Humanidade | **Implementado** | `TherapyEngine` (Padrão, Extrema e Dependência) respeita redução permanente e consome bônus/penalidade do `Brainwasher`. |
| **Core Parte 2** | Netrunning (Deck, Slots, Programas, Ações NET, ICE) | **Parcial** | Nós/REZ/geração ok; falta Cyberdeck equipado com slots/programas e ações por turno (Ciclo 5). |
| **Core Parte 2** | Economia, Hustle (10 Roles), Lifestyle, Moradia, Mercado Noturno, I.P. | **Parcial** | Compra, Hustle, Aluguel, Mercado Noturno e I.P. ativos; falta venda, recarga e extrato (Ciclo 5). |
| **Single Shot Pack** | 10 Edgerunners prontos | **Implementado** | `OfficialStreetratPregensCatalog` + criação de campanha completa. |
| **Interface RED Vol. 1–3** | Clima, Salvaging, Datepath, Gunmas, Woodchipper, 15 Cyberdecks, Hardware, Drones | **Parcial** | Clima, Salvaging e Datepath jogáveis; Cyberdecks/Hardware e Drones pendentes de uso ativo no Ciclo 5. |
| **Black Chrome** | 170+ itens, armas, armaduras, frames externos, 31 veículos | **Parcial** | Compêndio e compra genérica; Veículos e regras especiais de equipamento nos Ciclos 4–5. |
| **Single Player Mode+** | Oráculo 1d10, 22 tabelas, Perfil NPC, Flash of Luck | **Implementado** | `OfficialSinglePlayerPlusCatalog` (`roll > targetDv`), `SoloGeneratorsDialog` e painel em `PlaySessionScreen`. |
| **Going Quiet** | Stealth Netrunning (Quiet Jack In, Evadir ICE, Watcher) | **Parcial** | Regra exata de empate em busca ativa do Watcher corrigida; falta persistência de stealth por nó na sessão NET (Ciclo 5). |
| **All About Agents** | Agent Hacking, HusTool, Brainwasher, Apps | **Implementado** | Rolagens em `NetrunningTab` e modificador do `Brainwasher` persistido para a próxima Terapia. |
| **Night City Atlas v1.01** | 4 Zonas, 24 Distritos, 156 Locais, Pontes, Águas | **Implementado** | `OfficialNightCityAtlasCatalog` + `NightCityAtlasScreen` + deslocamento de campanha. |
| **Danger Gal Dossier** | 118 NPCs, 16 Facções, *The Incident* (10 beats) | **Implementado** | `OfficialDangerGalNpcs`, `OfficialDangerGalGearAndLore`, deploy na campanha e combate. |
| **Gangues & Pessoas Aleatórias** | 44 Gangues + 20 Cidadãos | **Implementado** | `OfficialGangsAndCitizensCatalog` + invocação em combate. |
| **Tales of the Red & Hook Book** | 130 Ganchos, Vendedor RC, NPCs | **Implementado** | `OfficialHookBookData` + `OfficialTalesAndHookBookCatalog`. |
| **Intro 6.1 & Navegação Híbrida** | Abertura glitch + 4 abas inferiores + Gaveta Direita | **Implementado** | `IntroSplashScreen`, `RedlineNavHost`, `RightNavigationDrawer` (Ciclo 1). |
| **PDFs, IA Multi-Provedor & Inteligência Visual** | Parser PDF real, Keystore multi-IA e cenas ilustradas | **Ausente** | Planejado para Ciclo 6. |
