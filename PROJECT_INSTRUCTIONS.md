# PROJECT_INSTRUCTIONS — Diretrizes de Desenvolvimento Contínuo

Este arquivo consolida as regras operacionais invioláveis do projeto **CYBERPUNK RED (Solo RPG Android)**.

---

## 1. Regras de Engenharia & Fidelidade
1. **Sem Homebrew Silencioso**: Todas as mecânicas devem seguir os PDFs oficiais (Core Rulebook Partes 1 e 2, Interface RED Vol. 1–3, Black Chrome, Single Player Mode+, Going Quiet, All About Agents, Night City Atlas, Danger Gal Dossier, Gangues de Night City, Pessoas Aleatórias, Tales of the Red / Hook Book). Em caso de ambiguidade ou errata, documentar explicitamente.
2. **Separação Determinística vs IA**:
   - Todo cálculo de dado (`1d10` explode/implode, `Nd6`, `1d100`), PV, PA, Humanidade, EMP, I.P., dano, críticos, Death Save, Netrunning e economia ocorre exclusivamente em Kotlin determinístico (`core/rules/`).
   - A camada de IA (`core/ai/`) **nunca** altera números ou inventa resultados de rolagem.
3. **Offline First**: Toda campanha, ficha, diário, combate, inventário e compêndio deve operar 100% offline via Room SQLite.
4. **Build & Testes**:
   - Sempre usar `gradle` (nunca `./gradlew`).
   - Executar `compile_applet` e `gradle :app:testDebugUnitTest` ao final de cada ciclo.
   - Nunca alterar `signingConfigs`, `debug.keystore` ou criar `local.properties`.
   - Nunca inserir chaves de API no código-fonte ou no `metadata.json`.

---

## 2. Identidade Visual & UX (HÍBRIDO RED//NEON)
- **Paleta oficial**:
  - Preto profundo: `#090B10` (`CyberVoid`)
  - Grafite escuro: `#171B25` (`CyberSurfaceElevated`)
  - Vermelho neon: `#FF303F` (`RedlineCrimson`)
  - Azul elétrico: `#00C8FF` (`CyberCyan`)
  - Branco frio: `#F5F5F5` (`TextPrimary`)
  - Amarelo destaque: `#F5C542` (`CyberAmber`)
- **Navegação Híbrida**:
  - Tela inicial de abertura (Seção 6.1) rápida com toque em tela cheia.
  - Barra inferior (4 áreas): `Início`, `Campanhas`, `Personagem`, `Jogar`.
  - Gaveta lateral direita (gesto da borda direita + botão na TopAppBar): `Sistemas`, `Compêndio`, `Atlas NC`, `Diário / Histórico`, `Inteligência Artificial`, `Configurações`.
- **Foto do Personagem & Equipamentos**:
  - Seleção via Android Photo Picker sem permissões invasivas de armazenamento.
  - Na tela de Equipamentos estilo videogame, a foto do usuário permanece **intacta e estática ao fundo** (sem recorte ou edição por IA), com painéis semitransparentes ao redor.

---

## 3. Manutenção Obrigatória da Documentação
Ao concluir cada ciclo, atualizar:
- `PROJECT_STATUS.md`
- `MECHANICS_COVERAGE.md`
- `CHANGELOG.md`
