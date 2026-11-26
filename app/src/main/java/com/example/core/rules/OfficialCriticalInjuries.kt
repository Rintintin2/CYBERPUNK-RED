package com.example.core.rules

data class CriticalInjuryDefinition(
    val roll2d6: Int,
    val isHead: Boolean,
    val nameEn: String,
    val namePt: String,
    val effectPt: String,
    val quickFixDv: Int?,
    val quickFixSkill: String?,
    val treatmentDv: Int,
    val treatmentSkill: String,
    val deathSavePenaltyIncrease: Int = 0
)

/**
 * Official Cyberpunk RED Critical Injury Tables (pp. 187–188 / PDF pp. 188–189).
 */
object OfficialCriticalInjuries {
    val bodyInjuries: List<CriticalInjuryDefinition> = listOf(
        CriticalInjuryDefinition(2, false, "Dismembered Arm", "Braço Desmembrado",
            "O braço foi arrancado. Larga qualquer item na mão imediatamente. Penalidade base de Teste de Morte +1.",
            null, null, 17, "Cirurgia", 1),
        CriticalInjuryDefinition(3, false, "Dismembered Hand", "Mão Desmembrada",
            "A mão foi arrancada. Larga qualquer item na mão imediatamente. Penalidade base de Teste de Morte +1.",
            null, null, 17, "Cirurgia", 1),
        CriticalInjuryDefinition(4, false, "Collapsed Lung", "Pulmão Colapsado",
            "-2 em MOV (mínimo 1). Penalidade base de Teste de Morte +1.",
            15, "Paramédico", 15, "Cirurgia", 1),
        CriticalInjuryDefinition(5, false, "Broken Ribs", "Costelas Quebradas",
            "Ao fim de todo Turno em que andar mais de 4 m/yd a pé, sofre novamente +5 de dano direto nos PV.",
            13, "Paramédico", 15, "Paramédico DV15 ou Cirurgia DV13", 0),
        CriticalInjuryDefinition(6, false, "Broken Arm", "Braço Quebrado",
            "O braço quebrado não pode ser usado. Larga qualquer item segurado imediatamente.",
            13, "Paramédico", 15, "Paramédico DV15 ou Cirurgia DV13", 0),
        CriticalInjuryDefinition(7, false, "Foreign Object", "Objeto Estranho",
            "Ao fim de todo Turno em que andar mais de 4 m/yd a pé, sofre novamente +5 de dano direto nos PV.",
            13, "Primeiros Socorros / Paramédico", 13, "Reparo Rápido remove permanentemente", 0),
        CriticalInjuryDefinition(8, false, "Broken Leg", "Perna Quebrada",
            "-4 em MOV (mínimo 1).",
            13, "Paramédico", 15, "Paramédico DV15 ou Cirurgia DV13", 0),
        CriticalInjuryDefinition(9, false, "Torn Muscle", "Músculo Rompido",
            "-2 em Ataques Corpo a Corpo.",
            13, "Primeiros Socorros / Paramédico", 13, "Reparo Rápido remove permanentemente", 0),
        CriticalInjuryDefinition(10, false, "Spinal Injury", "Lesão na Coluna",
            "No próximo Turno não pode fazer Ação (apenas Ação de Movimento). Penalidade base de Teste de Morte +1.",
            15, "Paramédico", 15, "Cirurgia", 1),
        CriticalInjuryDefinition(11, false, "Crushed Fingers", "Dedos Esmagados",
            "-4 em todas as Ações envolvendo aquela mão.",
            13, "Paramédico", 15, "Cirurgia", 0),
        CriticalInjuryDefinition(12, false, "Dismembered Leg", "Perna Desmembrada",
            "A perna foi arrancada. -6 em MOV (mínimo 1). Não pode esquivar de ataques. Penalidade base de Teste de Morte +1.",
            null, null, 17, "Cirurgia", 1)
    )

    val headInjuries: List<CriticalInjuryDefinition> = listOf(
        CriticalInjuryDefinition(2, true, "Lost Eye", "Olho Perdido",
            "O olho foi perdido. -4 em Ataques à Distância e Percepção visual. Penalidade base de Teste de Morte +1.",
            null, null, 17, "Cirurgia", 1),
        CriticalInjuryDefinition(3, true, "Brain Injury", "Lesão Cerebral",
            "-2 em todas as Ações. Penalidade base de Teste de Morte +1.",
            null, null, 17, "Cirurgia", 1),
        CriticalInjuryDefinition(4, true, "Damaged Eye", "Olho Danificado",
            "-2 em Ataques à Distância e Percepção visual.",
            15, "Paramédico", 13, "Cirurgia", 0),
        CriticalInjuryDefinition(5, true, "Concussion", "Concussão",
            "-2 em todas as Ações.",
            13, "Primeiros Socorros / Paramédico", 13, "Reparo Rápido remove permanentemente", 0),
        CriticalInjuryDefinition(6, true, "Broken Jaw", "Mandíbula Quebrada",
            "-4 em todas as Ações envolvendo fala.",
            13, "Paramédico", 13, "Paramédico ou Cirurgia", 0),
        CriticalInjuryDefinition(7, true, "Foreign Object", "Objeto Estranho",
            "Ao fim de todo Turno em que andar mais de 4 m/yd a pé, sofre novamente +5 de dano direto nos PV.",
            13, "Primeiros Socorros / Paramédico", 13, "Reparo Rápido remove permanentemente", 0),
        CriticalInjuryDefinition(8, true, "Whiplash", "Chicote Cervical",
            "Penalidade base de Teste de Morte +1.",
            13, "Paramédico", 13, "Paramédico ou Cirurgia", 1),
        CriticalInjuryDefinition(9, true, "Cracked Skull", "Crânio Rachado",
            "Tiros mirados na cabeça multiplicam o dano que passa pela PA por 3 em vez de 2. Penalidade base de Teste de Morte +1.",
            15, "Paramédico", 15, "Paramédico ou Cirurgia", 1),
        CriticalInjuryDefinition(10, true, "Damaged Ear", "Ouvido Danificado",
            "Se andar > 4 m/yd a pé no Turno, perde Ação de Movimento no próximo Turno. -2 em Percepção auditiva.",
            13, "Paramédico", 13, "Cirurgia", 0),
        CriticalInjuryDefinition(11, true, "Crushed Windpipe", "Traqueia Esmagada",
            "Não consegue falar. Penalidade base de Teste de Morte +1.",
            null, null, 15, "Cirurgia", 1),
        CriticalInjuryDefinition(12, true, "Lost Ear", "Orelha Perdida",
            "A orelha foi arrancada. Se andar > 4 m/yd a pé, perde Movimento no próximo Turno. -4 em Percepção auditiva. Teste de Morte +1.",
            null, null, 17, "Cirurgia", 1)
    )

    fun rollCriticalInjury(diceEngine: DiceEngine, isHead: Boolean): CriticalInjuryDefinition {
        val roll = diceEngine.rollNd6(2).total
        val table = if (isHead) headInjuries else bodyInjuries
        return table.first { it.roll2d6 == roll }
    }
}
