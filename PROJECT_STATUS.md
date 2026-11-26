# PROJECT_STATUS — CYBERPUNK RED (Solo RPG Android)

**Última atualização**: 2026-11-26  
**Ciclo atual**: Ciclo 2 (Concluído — Motor de Regras, CyberwareValidator, Penalidades de Armadura, Pain Editor e Terapia)  
**Nome oficial do aplicativo**: `CYBERPUNK RED`  
**Pacote / Namespace**: `com.example` (`applicationId = com.aistudio.redlinesolo.kx9m2p`)

---

## 1. Estado Geral da Arquitetura
- **Motor de Regras (`core/rules/`)**: Kotlin puro, determinístico e 100% offline. 35 arquivos (incluindo `CyberwareValidator.kt` e `TherapyEngine.kt`).
- **Banco Local (`data/local/`)**: Room SQLite v4 (`redline_solo.db`, com `MIGRATION_1_2`, `MIGRATION_2_3`, `MIGRATION_3_4`) com 13 entidades, 4 DAOs e 4 repositórios (`CampaignRepository`, `CharacterRepository`, `SessionRepository`, `RuleLibraryRepository`).
- **Identidade Visual**: Paleta **HÍBRIDO RED//NEON** (`#090B10`, `#171B25`, `#FF303F`, `#00C8FF`, `#F5F5F5`, `#F5C542`), fontes locais `Space Grotesk`, `Inter` e `JetBrains Mono`.
- **Navegação Híbrida (`RedlineNavHost.kt`)**:
  - Tela de Introdução / Abertura (Seção 6.1): `IntroSplashScreen` com efeito glitch Cyberpunk RED e toque em tela cheia.
  - Barra Inferior (4 áreas): `Início`, `Campanhas`, `Personagem`, `Jogar`.
  - Gaveta Lateral Direita (`RightNavigationDrawer`): `Sistemas`, `Compêndio`, `Atlas de Night City`, `Diário / Histórico`, `Inteligência Artificial`, `Configurações` (com gesto da borda direita e botão dedicado na barra superior).

---

## 2. Progresso por Ciclo (Etapa 2)
- [x] **Auditoria Completa + Matriz de Cobertura + Plano por Ciclos**
- [x] **Ciclo 1**: Rebranding (`CYBERPUNK RED`), Paleta HÍBRIDO RED//NEON, Tela de Abertura (6.1), Navegação Híbrida (4 abas + Gaveta Direita), telas auxiliares (`Início`, `Diário`, `IA`, `Configurações`) e Documentação de Continuidade.
- [x] **Ciclo 2**: Correções de regras (`>` no Oráculo 1d10 e empate de Watcher no *Going Quiet*, penalidade de armadura `-2`/`-4` aplicada em testes de `REF`/`DES`/`MOV`, `Editor de Dor` suprimindo `-2` de Ferimento Grave, `CyberwareValidator` com pré-requisitos e Option Slots, escala de `COR`/`PV Máx`/`Morte` por implantes, `TherapyEngine` integrado ao `Brainwasher`).
- [ ] **Ciclo 3**: Migração Room v5, Wizard de Criação de Personagem em 14 etapas, seletor de foto via Photo Picker e Tela de Equipamentos estilo videogame com foto estática ao fundo.
- [ ] **Ciclo 4**: Combate tático completo (armas equipadas, munição/pentes, alcance automático, Autofire, esquiva de balas `REF >= 8`, cobertura destrutível, dano/críticos/Death Save para qualquer combatente, cura/estabilização).
- [ ] **Ciclo 5**: Netrunning completo (Cyberdeck equipado, slots de Programas/Hardware, ações NET por turno, combate com Black ICE), Economia avançada (venda, recarga, histórico), Garagem de Veículos e Calendário granular.
- [ ] **Ciclo 6**: Extração/indexação real de PDFs, `Configurações -> Inteligência Artificial` com provedores + cofre Android Keystore e infraestrutura de Inteligência Visual.
- [ ] **Ciclo 7**: Polimento visual, responsividade, testes Robolectric end-to-end completos e sincronização final.

---

## 3. Pendências Críticas Conhecidas (Para os Próximos Ciclos)
1. Wizard completo de criação em 14 etapas, Photo Picker e tela de equipamentos estilo videogame (Ciclo 3).
2. Combate tático com seleção de armas/munição do inventário e cobertura (Ciclo 4).
3. `RuleLibraryRepository` apenas vincula URI/metadados via SAF; extração automática de texto será tratada no Ciclo 6.
4. Diretório local ainda não possui `.git` configurado para push automático ao GitHub.
