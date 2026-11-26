package com.example.core.rules

data class OfficialSkillDefinition(
    val nameEn: String,
    val namePt: String,
    val stat: RedStat,
    val categoryPt: String,
    val isBasic: Boolean = false,
    val isDoubleCost: Boolean = false
)

object OfficialSkillCatalog {
    val allSkills: List<OfficialSkillDefinition> = listOf(
        // Consciência (Awareness)
        OfficialSkillDefinition("Concentration", "Concentração", RedStat.WILL, "Consciência", isBasic = true),
        OfficialSkillDefinition("Conceal/Reveal Object", "Ocultar/Revelar Objeto", RedStat.INT, "Consciência"),
        OfficialSkillDefinition("Lip Reading", "Leitura Labial", RedStat.INT, "Consciência"),
        OfficialSkillDefinition("Perception", "Percepção", RedStat.INT, "Consciência", isBasic = true),
        OfficialSkillDefinition("Tracking", "Rastreamento", RedStat.INT, "Consciência"),

        // Corpo (Body)
        OfficialSkillDefinition("Athletics", "Atletismo", RedStat.DEX, "Corpo", isBasic = true),
        OfficialSkillDefinition("Contortionist", "Contorcionismo", RedStat.DEX, "Corpo"),
        OfficialSkillDefinition("Dance", "Dança", RedStat.DEX, "Corpo"),
        OfficialSkillDefinition("Endurance", "Resistência", RedStat.WILL, "Corpo"),
        OfficialSkillDefinition("Resist Torture/Drugs", "Resistir a Tortura/Drogas", RedStat.WILL, "Corpo"),
        OfficialSkillDefinition("Stealth", "Furtividade", RedStat.DEX, "Corpo", isBasic = true),

        // Controle (Control)
        OfficialSkillDefinition("Drive Land Vehicle", "Dirigir Veículo Terrestre", RedStat.REF, "Controle"),
        OfficialSkillDefinition("Pilot Air Vehicle", "Pilotar Veículo Aéreo", RedStat.REF, "Controle", isDoubleCost = true),
        OfficialSkillDefinition("Pilot Sea Vehicle", "Pilotar Veículo Aquático", RedStat.REF, "Controle"),
        OfficialSkillDefinition("Riding", "Montaria", RedStat.REF, "Controle"),

        // Educação (Education)
        OfficialSkillDefinition("Accounting", "Contabilidade", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Animal Handling", "Lidar com Animais", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Bureaucracy", "Burocracia", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Business", "Negócios", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Composition", "Composição", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Criminology", "Criminologia", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Cryptography", "Criptografia", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Deduction", "Dedução", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Education", "Educação", RedStat.INT, "Educação", isBasic = true),
        OfficialSkillDefinition("Gamble", "Jogos de Azar", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Language (Streetslang)", "Idioma (Gíria das Ruas)", RedStat.INT, "Educação", isBasic = true),
        OfficialSkillDefinition("Language (Cultural)", "Idioma (Origem Cultural)", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Library Search", "Pesquisa em Arquivos", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Local Expert (Your Home)", "Especialista Local (Seu Bairro)", RedStat.INT, "Educação", isBasic = true),
        OfficialSkillDefinition("Science", "Ciência", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Tactics", "Tática", RedStat.INT, "Educação"),
        OfficialSkillDefinition("Wilderness Survival", "Sobrevivência", RedStat.INT, "Educação"),

        // Luta (Fighting)
        OfficialSkillDefinition("Brawling", "Briga", RedStat.DEX, "Luta", isBasic = true),
        OfficialSkillDefinition("Evasion", "Evasão", RedStat.DEX, "Luta", isBasic = true),
        OfficialSkillDefinition("Martial Arts", "Artes Marciais", RedStat.DEX, "Luta", isDoubleCost = true),
        OfficialSkillDefinition("Melee Weapon", "Armas Brancas", RedStat.DEX, "Luta"),

        // Performance
        OfficialSkillDefinition("Acting", "Atuação", RedStat.COOL, "Performance"),
        OfficialSkillDefinition("Play Instrument", "Tocar Instrumento", RedStat.TECH, "Performance"),

        // Combate à Distância (Ranged Weapon)
        OfficialSkillDefinition("Archery", "Arquearia", RedStat.REF, "Combate à Distância"),
        OfficialSkillDefinition("Autofire", "Tiro Automático", RedStat.REF, "Combate à Distância", isDoubleCost = true),
        OfficialSkillDefinition("Handgun", "Armas Curtas", RedStat.REF, "Combate à Distância"),
        OfficialSkillDefinition("Heavy Weapons", "Armas Pesadas", RedStat.REF, "Combate à Distância", isDoubleCost = true),
        OfficialSkillDefinition("Shoulder Arms", "Armas Longas", RedStat.REF, "Combate à Distância"),

        // Social
        OfficialSkillDefinition("Bribery", "Suborno", RedStat.COOL, "Social"),
        OfficialSkillDefinition("Conversation", "Conversação", RedStat.EMP, "Social", isBasic = true),
        OfficialSkillDefinition("Human Perception", "Percepção Humana", RedStat.EMP, "Social", isBasic = true),
        OfficialSkillDefinition("Interrogation", "Interrogatório", RedStat.COOL, "Social"),
        OfficialSkillDefinition("Persuasion", "Persuasão", RedStat.COOL, "Social", isBasic = true),
        OfficialSkillDefinition("Personal Grooming", "Aparência Pessoal", RedStat.COOL, "Social"),
        OfficialSkillDefinition("Streetwise", "Manha (Streetwise)", RedStat.COOL, "Social"),
        OfficialSkillDefinition("Trading", "Negociação", RedStat.COOL, "Social"),
        OfficialSkillDefinition("Wardrobe & Style", "Estilo & Vestuário", RedStat.COOL, "Social"),

        // Técnica (Technique)
        OfficialSkillDefinition("Air Vehicle Tech", "Mecânica Aérea", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Basic Tech", "Tecnologia Básica", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Cybertech", "Cibertecnologia", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Demolitions", "Demolições", RedStat.TECH, "Técnica", isDoubleCost = true),
        OfficialSkillDefinition("Electronics/Security Tech", "Eletrônica/Segurança", RedStat.TECH, "Técnica", isDoubleCost = true),
        OfficialSkillDefinition("First Aid", "Primeiros Socorros", RedStat.TECH, "Técnica", isBasic = true),
        OfficialSkillDefinition("Forgery", "Falsificação", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Land Vehicle Tech", "Mecânica Terrestre", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Paint/Draw/Sculpt", "Arte Visual", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Paramedic", "Paramédico", RedStat.TECH, "Técnica", isDoubleCost = true),
        OfficialSkillDefinition("Photography/Film", "Fotografia/Filmagem", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Pick Lock", "Abrir Fechaduras", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Pick Pocket", "Furtar Bolsos", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Sea Vehicle Tech", "Mecânica Aquática", RedStat.TECH, "Técnica"),
        OfficialSkillDefinition("Weaponstech", "Armeiro", RedStat.TECH, "Técnica")
    )

    /**
     * Official Streetrat / Edgerunner 10-row STAT template tables (pages 74–78).
     * Order: INT, REF, DEX, TECH, COOL, WILL, LUCK, MOVE, BODY, EMP
     */
    private val statTemplateRows: Map<RedRole, List<CharacterStats>> = mapOf(
        RedRole.ROCKERBOY to listOf(
            CharacterStats(7, 6, 6, 5, 6, 8, 7, 7, 3, 8),
            CharacterStats(3, 7, 7, 7, 7, 6, 7, 7, 5, 8),
            CharacterStats(4, 5, 7, 7, 6, 6, 7, 7, 5, 8),
            CharacterStats(4, 5, 7, 7, 6, 8, 7, 6, 3, 8),
            CharacterStats(3, 7, 7, 7, 6, 8, 6, 5, 4, 7),
            CharacterStats(5, 6, 7, 5, 7, 8, 5, 7, 3, 7),
            CharacterStats(5, 6, 6, 7, 7, 8, 7, 6, 3, 6),
            CharacterStats(5, 7, 7, 5, 6, 6, 6, 6, 4, 8),
            CharacterStats(3, 5, 5, 6, 7, 8, 7, 5, 5, 7),
            CharacterStats(4, 5, 6, 5, 8, 8, 7, 6, 4, 7)
        ),
        RedRole.SOLO to listOf(
            CharacterStats(6, 7, 7, 3, 8, 6, 5, 5, 6, 5),
            CharacterStats(7, 8, 6, 3, 6, 6, 7, 5, 6, 6),
            CharacterStats(5, 8, 7, 4, 7, 7, 6, 7, 8, 5),
            CharacterStats(5, 8, 6, 4, 6, 7, 6, 5, 7, 6),
            CharacterStats(6, 6, 7, 5, 7, 6, 7, 6, 8, 4),
            CharacterStats(7, 7, 6, 5, 7, 6, 6, 7, 7, 5),
            CharacterStats(7, 7, 6, 5, 6, 7, 7, 6, 6, 6),
            CharacterStats(7, 8, 7, 5, 6, 6, 5, 6, 8, 4),
            CharacterStats(7, 7, 6, 4, 6, 6, 6, 5, 6, 5),
            CharacterStats(6, 6, 8, 5, 6, 6, 5, 6, 6, 5)
        ),
        RedRole.NETRUNNER to listOf(
            CharacterStats(5, 8, 7, 7, 7, 4, 8, 7, 7, 4),
            CharacterStats(5, 6, 7, 5, 8, 3, 8, 7, 5, 5),
            CharacterStats(5, 6, 8, 6, 6, 4, 7, 6, 7, 4),
            CharacterStats(5, 7, 7, 7, 7, 5, 8, 6, 5, 5),
            CharacterStats(5, 8, 8, 5, 7, 3, 7, 5, 5, 6),
            CharacterStats(6, 6, 6, 7, 8, 4, 7, 7, 6, 6),
            CharacterStats(6, 6, 6, 7, 6, 5, 7, 7, 7, 6),
            CharacterStats(5, 7, 8, 6, 8, 4, 8, 5, 7, 4),
            CharacterStats(7, 6, 7, 7, 6, 3, 6, 5, 6, 5),
            CharacterStats(7, 8, 6, 6, 6, 4, 7, 7, 5, 6)
        ),
        RedRole.TECH to listOf(
            CharacterStats(6, 7, 7, 8, 4, 4, 5, 5, 7, 6),
            CharacterStats(7, 6, 6, 7, 5, 3, 7, 7, 5, 5),
            CharacterStats(8, 6, 5, 7, 5, 4, 7, 7, 5, 7),
            CharacterStats(7, 8, 7, 8, 4, 4, 6, 5, 6, 7),
            CharacterStats(6, 6, 7, 6, 4, 3, 7, 7, 6, 6),
            CharacterStats(8, 7, 5, 6, 3, 3, 7, 6, 6, 7),
            CharacterStats(8, 6, 7, 8, 4, 4, 7, 6, 7, 6),
            CharacterStats(8, 8, 7, 8, 5, 4, 6, 5, 6, 6),
            CharacterStats(6, 6, 7, 8, 3, 3, 5, 7, 7, 7),
            CharacterStats(8, 8, 5, 6, 4, 4, 6, 5, 6, 6)
        ),
        RedRole.MEDTECH to listOf(
            CharacterStats(7, 5, 6, 7, 5, 3, 8, 5, 5, 7),
            CharacterStats(6, 7, 7, 7, 4, 4, 6, 7, 7, 7),
            CharacterStats(6, 5, 5, 8, 5, 3, 8, 5, 7, 8),
            CharacterStats(8, 7, 6, 8, 3, 5, 6, 6, 5, 7),
            CharacterStats(6, 7, 5, 7, 5, 5, 8, 7, 6, 8),
            CharacterStats(8, 5, 5, 8, 5, 5, 6, 6, 5, 6),
            CharacterStats(8, 6, 5, 8, 5, 4, 8, 5, 7, 7),
            CharacterStats(6, 5, 7, 7, 3, 5, 8, 5, 5, 8),
            CharacterStats(6, 6, 7, 7, 5, 4, 6, 6, 5, 6),
            CharacterStats(8, 7, 6, 6, 3, 4, 8, 7, 6, 7)
        ),
        RedRole.MEDIA to listOf(
            CharacterStats(6, 6, 5, 5, 8, 7, 5, 7, 5, 7),
            CharacterStats(8, 7, 7, 3, 6, 6, 6, 5, 6, 8),
            CharacterStats(6, 7, 7, 5, 6, 8, 5, 5, 5, 7),
            CharacterStats(6, 5, 7, 5, 6, 7, 5, 5, 6, 6),
            CharacterStats(6, 6, 7, 4, 8, 7, 6, 7, 5, 8),
            CharacterStats(7, 5, 5, 4, 8, 7, 6, 7, 5, 8),
            CharacterStats(8, 5, 6, 3, 7, 6, 6, 5, 6, 7),
            CharacterStats(6, 5, 6, 5, 6, 8, 6, 6, 7, 8),
            CharacterStats(7, 7, 5, 4, 6, 7, 6, 5, 6, 7),
            CharacterStats(7, 6, 6, 3, 7, 6, 7, 6, 7, 6)
        ),
        RedRole.LAWMAN to listOf(
            CharacterStats(5, 6, 7, 5, 7, 8, 5, 6, 5, 6),
            CharacterStats(6, 6, 6, 5, 6, 8, 5, 7, 5, 5),
            CharacterStats(5, 7, 7, 7, 6, 7, 5, 5, 7, 6),
            CharacterStats(6, 6, 7, 6, 6, 8, 5, 7, 7, 6),
            CharacterStats(6, 6, 7, 6, 7, 7, 6, 5, 5, 6),
            CharacterStats(7, 6, 5, 5, 7, 8, 5, 6, 7, 4),
            CharacterStats(7, 8, 7, 5, 6, 8, 7, 6, 5, 4),
            CharacterStats(5, 6, 6, 5, 6, 8, 5, 7, 6, 4),
            CharacterStats(7, 7, 5, 5, 7, 7, 6, 5, 5, 6),
            CharacterStats(6, 6, 5, 6, 8, 7, 5, 7, 6, 6)
        ),
        RedRole.EXEC to listOf(
            CharacterStats(8, 5, 5, 3, 8, 6, 6, 5, 5, 7),
            CharacterStats(8, 6, 6, 4, 7, 6, 7, 7, 5, 7),
            CharacterStats(8, 7, 6, 3, 8, 6, 7, 6, 4, 5),
            CharacterStats(8, 5, 7, 5, 6, 5, 6, 5, 5, 7),
            CharacterStats(7, 7, 6, 5, 8, 5, 7, 7, 5, 6),
            CharacterStats(5, 7, 7, 3, 6, 7, 6, 5, 5, 7),
            CharacterStats(6, 6, 7, 5, 8, 7, 6, 7, 4, 6),
            CharacterStats(6, 7, 7, 3, 7, 5, 7, 5, 5, 7),
            CharacterStats(7, 6, 7, 5, 7, 5, 7, 6, 5, 5),
            CharacterStats(7, 7, 5, 5, 8, 6, 6, 7, 4, 7)
        ),
        RedRole.FIXER to listOf(
            CharacterStats(8, 5, 7, 4, 6, 5, 8, 5, 5, 8),
            CharacterStats(8, 5, 5, 5, 6, 7, 8, 7, 5, 7),
            CharacterStats(6, 6, 6, 4, 5, 6, 8, 6, 3, 8),
            CharacterStats(7, 7, 5, 5, 7, 6, 7, 7, 5, 8),
            CharacterStats(8, 6, 6, 3, 6, 5, 8, 7, 5, 6),
            CharacterStats(8, 7, 5, 5, 6, 7, 7, 5, 3, 6),
            CharacterStats(8, 6, 6, 5, 6, 5, 6, 7, 5, 8),
            CharacterStats(6, 6, 7, 4, 7, 6, 7, 7, 4, 7),
            CharacterStats(8, 7, 7, 5, 5, 5, 7, 6, 5, 7),
            CharacterStats(6, 5, 6, 5, 5, 6, 8, 6, 4, 7)
        ),
        RedRole.NOMAD to listOf(
            CharacterStats(6, 6, 8, 3, 6, 7, 6, 6, 6, 4),
            CharacterStats(5, 7, 6, 5, 8, 8, 8, 7, 5, 4),
            CharacterStats(5, 8, 6, 3, 8, 7, 6, 5, 6, 5),
            CharacterStats(5, 8, 7, 4, 8, 6, 7, 7, 7, 5),
            CharacterStats(6, 6, 6, 3, 6, 7, 6, 7, 7, 4),
            CharacterStats(7, 6, 8, 4, 6, 7, 6, 5, 6, 5),
            CharacterStats(6, 7, 8, 4, 6, 6, 7, 5, 7, 5),
            CharacterStats(5, 7, 8, 3, 8, 6, 7, 5, 5, 5),
            CharacterStats(6, 7, 6, 4, 8, 6, 6, 6, 6, 6),
            CharacterStats(5, 6, 7, 4, 7, 8, 7, 7, 7, 4)
        )
    )

    fun streetratStats(role: RedRole, roll1d10: Int): CharacterStats {
        val rows = statTemplateRows.getValue(role)
        return rows[(roll1d10 - 1).coerceIn(0, rows.lastIndex)]
    }

    fun rollEdgerunnerStats(role: RedRole, diceEngine: DiceEngine): CharacterStats {
        val rows = statTemplateRows.getValue(role)
        fun pick(selector: (CharacterStats) -> Int): Int {
            val row = rows[(diceEngine.rollPlainD10() - 1).coerceIn(0, 9)]
            return selector(row)
        }
        return CharacterStats(
            intelligence = pick { it.intelligence },
            reflexes = pick { it.reflexes },
            dexterity = pick { it.dexterity },
            technique = pick { it.technique },
            cool = pick { it.cool },
            willpower = pick { it.willpower },
            luckMax = pick { it.luckMax },
            move = pick { it.move },
            body = pick { it.body },
            empathyMax = pick { it.empathyMax }
        )
    }

    /**
     * Official Streetrat Skill Templates (pp. 86–87) for all 10 Roles.
     */
    fun streetratSkillTemplate(role: RedRole): List<Pair<String, Int>> {
        val commonBasic = mapOf(
            "Atletismo" to 2, "Briga" to 2, "Concentração" to 2, "Conversação" to 2,
            "Educação" to 2, "Evasão" to 6, "Primeiros Socorros" to 2, "Percepção Humana" to 2,
            "Idioma (Gíria das Ruas)" to 2, "Especialista Local (Seu Bairro)" to 2,
            "Percepção" to 2, "Persuasão" to 2, "Furtividade" to 2
        ).toMutableMap()

        val extras: Map<String, Int> = when (role) {
            RedRole.ROCKERBOY -> mapOf(
                "Briga" to 6, "Primeiros Socorros" to 6, "Percepção Humana" to 6,
                "Especialista Local (Seu Bairro)" to 4, "Persuasão" to 6,
                "Composição" to 6, "Armas Curtas" to 6, "Armas Brancas" to 6,
                "Aparência Pessoal" to 4, "Tocar Instrumento" to 6, "Manha (Streetwise)" to 6,
                "Estilo & Vestuário" to 4
            )
            RedRole.SOLO -> mapOf(
                "Primeiros Socorros" to 6, "Percepção" to 6,
                "Tiro Automático" to 6, "Armas Curtas" to 6, "Interrogatório" to 6,
                "Armas Brancas" to 6, "Resistir a Tortura/Drogas" to 6, "Armas Longas" to 6, "Tática" to 6
            )
            RedRole.NETRUNNER -> mapOf(
                "Educação" to 6, "Furtividade" to 6, "Tecnologia Básica" to 6,
                "Ocultar/Revelar Objeto" to 6, "Criptografia" to 6, "Cibertecnologia" to 6,
                "Eletrônica/Segurança" to 6, "Armas Curtas" to 6, "Pesquisa em Arquivos" to 6
            )
            RedRole.TECH -> mapOf(
                "Educação" to 6, "Primeiros Socorros" to 6, "Tecnologia Básica" to 6,
                "Cibertecnologia" to 6, "Eletrônica/Segurança" to 6, "Mecânica Terrestre" to 6,
                "Armas Longas" to 6, "Ciência" to 6, "Armeiro" to 6
            )
            RedRole.MEDTECH -> mapOf(
                "Conversação" to 6, "Educação" to 6, "Percepção Humana" to 6,
                "Tecnologia Básica" to 6, "Cibertecnologia" to 4, "Dedução" to 6,
                "Paramédico" to 6, "Resistir a Tortura/Drogas" to 4, "Ciência" to 6, "Armas Longas" to 6
            )
            RedRole.MEDIA -> mapOf(
                "Conversação" to 6, "Percepção Humana" to 6, "Especialista Local (Seu Bairro)" to 6,
                "Percepção" to 6, "Persuasão" to 6, "Suborno" to 6, "Composição" to 6,
                "Dedução" to 6, "Armas Curtas" to 6, "Pesquisa em Arquivos" to 4,
                "Leitura Labial" to 4, "Fotografia/Filmagem" to 4
            )
            RedRole.LAWMAN -> mapOf(
                "Briga" to 6, "Conversação" to 6, "Tiro Automático" to 6,
                "Criminologia" to 6, "Dedução" to 6, "Armas Curtas" to 6,
                "Interrogatório" to 6, "Armas Longas" to 6, "Rastreamento" to 6
            )
            RedRole.EXEC -> mapOf(
                "Educação" to 6, "Percepção Humana" to 6, "Persuasão" to 6,
                "Contabilidade" to 6, "Burocracia" to 6, "Negócios" to 6,
                "Dedução" to 6, "Armas Curtas" to 6, "Leitura Labial" to 6, "Aparência Pessoal" to 4
            )
            RedRole.FIXER -> mapOf(
                "Conversação" to 6, "Percepção Humana" to 6, "Idioma (Gíria das Ruas)" to 4,
                "Especialista Local (Seu Bairro)" to 6, "Persuasão" to 4, "Suborno" to 6,
                "Negócios" to 6, "Falsificação" to 6, "Armas Curtas" to 6,
                "Abrir Fechaduras" to 4, "Manha (Streetwise)" to 6, "Negociação" to 6
            )
            RedRole.NOMAD -> mapOf(
                "Briga" to 6, "Primeiros Socorros" to 6, "Percepção" to 4, "Furtividade" to 6,
                "Lidar com Animais" to 6, "Dirigir Veículo Terrestre" to 6, "Armas Curtas" to 6,
                "Armas Brancas" to 6, "Rastreamento" to 6, "Negociação" to 6, "Sobrevivência" to 6
            )
        }

        commonBasic.putAll(extras)
        return commonBasic.entries.map { it.key to it.value }
    }
}
