# CHANGELOG — CYBERPUNK RED (Solo RPG Android)

## [Ciclo 2 — Etapa 2] - 2026-11-26
### Adicionado
- **Validador Oficial de Cyberware (`CyberwareValidator.kt`)**: Imposição de peças base (`Neural Link`, `Cybereye`, `Cyberaudio Suite`, `Cyberarm`, `Cyberleg`, `Chipware Socket`), tetos de Option Slots por categoria, limite de 1 `Speedware`, pré-requisitos para `Linear Frame` e classificação confiável de `Borgware`.
- **Escala Automática de `COR` (`BODY`) e `Editor de Dor`**: `CharacterCalculator` e `CharacterRepository` agora recalculam `COR` efetivo, `PV Máx`, `Limite Grave` e `Teste de Morte` ao instalar/alternar `Músculos e Ossos Enxertados` e `Linear Frames` (`Σ`, `β`, `Ω`, Fūma, SWAT, Vermilion), além de anular a penalidade de Ferimento Grave (`-2`) quando `Editor de Dor` está ativo.
- **Motor & Diálogo de Terapia (`TherapyEngine.kt` + `CharacterSheetScreen.kt`)**: Terapia de Humanidade Padrão (2d6), Extrema (4d6) e Terapia de Dependência integradas ao modificador persistido do app `Brainwasher` (`±1d6`), avanço de 7 dias e piso permanente de redução por implantes.

### Corrigido
- **Penalidades de Armadura em Testes (`MainViewModel` + `DiceRollerSheet`)**: Armaduras Média (`-2`) e Pesada (`-4`) agora incidem automaticamente sobre testes de `REF`, `DES` e `MOV`.
- **Comparação `> DV` e `Going Quiet`**: `OfficialSinglePlayerPlusCatalog.askSoloOracleYesNo` corrigido para `roll > targetDv` (tornando `DV 5` exatamente 50/50 em 1d10); `NetrunningEngine.contestWatcherVsStealth` padronizado para distinguir busca ativa do Watcher (empate mantém furtividade) de encontro passivo.
- **Room v4 (`MIGRATION_3_4`)**: Adicionada coluna `pendingTherapyDiceDelta` em `characters` sem perda de dados.

## [Ciclo 1 — Etapa 2] - 2026-11-26
### Adicionado
- **Seção 6.1 — Tela de Introdução / Abertura (`IntroSplashScreen.kt`)**: Sequência rápida inspirada em Cyberpunk 2077 com tela preta, scanlines e ruídos digitais em `Canvas`, logotipo `CYBERPUNK RED` com glitch cibernético e estabilização, subtítulo `BEM-VINDO A NIGHT CITY`, indicação pulsante `TOQUE NA TELA PARA ENTRAR` e área de toque em tela cheia sem espera obrigatória.
- **Navegação Híbrida (`RedlineNavHost.kt` + `RightNavigationDrawer.kt`)**:
  - Barra inferior (`NavigationBar`/`NavigationRail`) reorganizada em 4 áreas: `Início`, `Campanhas`, `Personagem`, `Jogar`.
  - Gaveta lateral direita acessível por gesto da borda direita ou botão na barra superior com: `Sistemas`, `Compêndio`, `Atlas de Night City`, `Diário / Histórico`, `Inteligência Artificial` e `Configurações`.
- **Novas Telas**: `HomeHubScreen.kt`, `JournalHistoryScreen.kt`, `AiSettingsScreen.kt` e `AppSettingsScreen.kt`.
- **Documentação de Continuidade**: Criados `PROJECT_STATUS.md`, `PROJECT_INSTRUCTIONS.md`, `MECHANICS_COVERAGE.md` e `CHANGELOG.md`.

### Alterado
- **Rebranding**: Nome atualizado de `Redline Solo` para `CYBERPUNK RED` em `metadata.json`, `settings.gradle.kts`, `strings.xml` e `CampaignHubScreen.kt`.
- **Paleta HÍBRIDO RED//NEON**: `Color.kt` e `colors.xml` ajustados para `#090B10` (preto profundo), `#171B25` (grafite), `#FF303F` (vermelho neon), `#00C8FF` (azul elétrico), `#F5F5F5` (branco frio) e `#F5C542` (amarelo cyberpunk).
