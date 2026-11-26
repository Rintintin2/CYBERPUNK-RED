package com.example.core.rules

import com.example.data.local.entity.NetNodeType

enum class NetArchDifficulty(val labelPt: String, val dv: Int, val suggestedInterface: Int) {
    BASIC("Básica (DV 6)", 6, 2),
    STANDARD("Padrão (DV 8)", 8, 4),
    UNCOMMON("Incomum (DV 10)", 10, 6),
    ADVANCED("Avançada (DV 12)", 12, 8)
}

data class OfficialProgramSpec(
    val name: String,
    val programClassPt: String,
    val atk: Int,
    val def: Int,
    val rez: Int,
    val costEb: Int,
    val effectPt: String
)

data class OfficialBlackIceSpec(
    val name: String,
    val iceClassPt: String,
    val per: Int,
    val spd: Int,
    val atk: Int,
    val def: Int,
    val rez: Int,
    val costEb: Int,
    val effectPt: String,
    val damageDiceCount: Int = 0
)

data class OfficialDemonSpec(
    val name: String,
    val rez: Int,
    val interfaceRank: Int,
    val netActions: Int,
    val combatNumber: Int,
    val costEb: Int
)

data class GeneratedNetFloor(
    val floorNumber: Int,
    val nodeType: NetNodeType,
    val title: String,
    val dvOrRez: Int
)

/**
 * Canonical Part 1 Netrunning Programs, Black ICE, Hardware, Demons,
 * Step-by-Step NET Architecture Generator (pp. 197–217), and Role Ability Reference (pp. 144–166).
 */
object OfficialNetArchitectures {

    val programs: List<OfficialProgramSpec> = listOf(
        // Boosters
        OfficialProgramSpec("Eraser", "Booster", 0, 0, 7, 20, "+2 em testes de Cloak enquanto ativo (Rezzed)"),
        OfficialProgramSpec("See Ya", "Booster", 0, 0, 7, 20, "+2 em testes de Pathfinder enquanto ativo"),
        OfficialProgramSpec("Speedy Gonzalvez", "Booster", 0, 0, 7, 100, "+2 em Velocidade (Speed) contra Black ICE enquanto ativo"),
        OfficialProgramSpec("Worm", "Booster", 0, 0, 7, 50, "+2 em testes de Backdoor enquanto ativo"),
        // Defenders
        OfficialProgramSpec("Armor", "Defender", 0, 0, 7, 50, "Reduz dano cerebral sofrido em 4 (máx 1 cópia ativa, 1x/Netrun)"),
        OfficialProgramSpec("Flak", "Defender", 0, 0, 7, 50, "Reduz ATK de Atacantes não-Black ICE contra você a 0 (1x/Netrun)"),
        OfficialProgramSpec("Shield", "Defender", 0, 0, 7, 20, "Anula o 1º efeito cerebral de Programa não-Black ICE e desativa (1x/Netrun)"),
        // Attackers
        OfficialProgramSpec("Banhammer", "Anti-Program Attacker", 1, 0, 0, 50, "3d6 REZ em Programa normal ou 2d6 REZ em Black ICE"),
        OfficialProgramSpec("Sword", "Anti-Program Attacker", 1, 0, 0, 50, "3d6 REZ em Black ICE ou 2d6 REZ em Programa normal"),
        OfficialProgramSpec("DeckKRASH", "Anti-Personnel Attacker", 0, 0, 0, 100, "Força Jack Out inseguro do Netrunner inimigo"),
        OfficialProgramSpec("Hellbolt", "Anti-Personnel Attacker", 2, 0, 0, 100, "2d6 dano cerebral direto + incendeia Cyberdeck (2 dano/turno)"),
        OfficialProgramSpec("Nervescrub", "Anti-Personnel Attacker", 0, 0, 0, 100, "Reduz INT, REF e DES inimigos em 1d6 por 1 hora (mín 1)"),
        OfficialProgramSpec("Poison Flatline", "Anti-Personnel Attacker", 0, 0, 0, 100, "Destrói 1 Programa não-Black ICE aleatório no deck alvo"),
        OfficialProgramSpec("Superglue", "Anti-Personnel Attacker", 2, 0, 0, 100, "Impede avanço e Jack Out seguro por 1d6 rodadas (1x/Netrun)"),
        OfficialProgramSpec("Vrizzbolt", "Anti-Personnel Attacker", 1, 0, 0, 50, "1d6 dano cerebral + reduz Ações NET no próximo turno em 1 (mín 2)")
    )

    val blackIce: List<OfficialBlackIceSpec> = listOf(
        OfficialBlackIceSpec("Asp", "Anti-Personnel", 4, 6, 2, 2, 15, 100, "Destrói 1 Programa aleatório no Cyberdeck do Netrunner", 0),
        OfficialBlackIceSpec("Giant", "Anti-Personnel", 2, 2, 8, 4, 25, 1000, "3d6 dano cerebral + Jack Out inseguro forçado", 3),
        OfficialBlackIceSpec("Hellhound", "Anti-Personnel", 6, 6, 6, 2, 20, 500, "2d6 dano cerebral + incendeia deck/roupas (2 dano/turno)", 2),
        OfficialBlackIceSpec("Kraken", "Anti-Personnel", 6, 2, 8, 4, 30, 1000, "3d6 dano cerebral + bloqueia avanço e Jack Out seguro", 3),
        OfficialBlackIceSpec("Liche", "Anti-Personnel", 8, 2, 6, 2, 25, 500, "Reduz INT, REF e DES em 1d6 por 1 hora (mín 1)", 0),
        OfficialBlackIceSpec("Raven", "Anti-Personnel", 6, 4, 4, 2, 15, 50, "Desativa 1 Defender aleatório + 1d6 dano cerebral", 1),
        OfficialBlackIceSpec("Scorpion", "Anti-Personnel", 2, 6, 2, 2, 15, 100, "Reduz MOV em 1d6 por 1 hora (mín 1)", 0),
        OfficialBlackIceSpec("Skunk", "Anti-Personnel", 2, 4, 4, 2, 10, 500, "Impõe -2 em todos os testes de Slide enquanto ativo", 0),
        OfficialBlackIceSpec("Wisp", "Anti-Personnel", 4, 4, 4, 2, 15, 50, "1d6 dano cerebral + reduz Ações NET em 1 (mín 2)", 1),
        OfficialBlackIceSpec("Dragon", "Anti-Program", 6, 4, 6, 6, 30, 1000, "6d6 REZ contra Programa; se zerar, DESTRÓI permanentemente", 6),
        OfficialBlackIceSpec("Killer", "Anti-Program", 4, 8, 6, 2, 20, 500, "4d6 REZ contra Programa; se zerar, DESTRÓI permanentemente", 4),
        OfficialBlackIceSpec("Sabertooth", "Anti-Program", 8, 6, 6, 2, 25, 1000, "6d6 REZ contra Programa; se zerar, DESTRÓI permanentemente", 6)
    )

    val hardwareUpgrades: List<Pair<String, String>> = listOf(
        "Backup Drive (100eb, 2 slots)" to "Salva Programas destruídos para reinstalação com Ação de Carne",
        "DNA Lock (100eb, 2 slots)" to "Trava biométrica no Cyberdeck (DV 17 Eletrônica/Segurança para burlar)",
        "Hardened Circuitry (100eb, 1 slot)" to "Imune a EMP (Microwaver) e efeitos de Programas não-Black ICE",
        "Insulated Wiring (100eb, 1 slot)" to "Impede Cyberdeck e roupas de incendiarem por Programas",
        "KRASH Barrier (100eb, 2 slots)" to "Imune a efeitos de Programas que forçam Jack Out",
        "Range Upgrade (100eb, 1 slot)" to "Permite conectar a pontos de acesso a até 8m de distância"
    )

    val demons: List<OfficialDemonSpec> = listOf(
        OfficialDemonSpec("Imp", 15, 3, 2, 14, 1000),
        OfficialDemonSpec("Efreet", 25, 4, 3, 14, 5000),
        OfficialDemonSpec("Balron", 30, 7, 4, 14, 10000)
    )

    fun interfaceActionsForRank(interfaceRank: Int): Int = when {
        interfaceRank <= 3 -> 2
        interfaceRank <= 6 -> 3
        interfaceRank <= 9 -> 4
        else -> 5
    }

    /**
     * Generates a canonical Cyberpunk RED NET Architecture (Steps 0–2, p. 209–211):
     * - Total floors = 3d6 (clamped 3..18)
     * - First 2 floors = Lobby Table (1d6)
     * - Remaining floors = Body Table (3d6) by Difficulty Rating
     */
    fun generateArchitecture(
        diceEngine: DiceEngine,
        difficulty: NetArchDifficulty
    ): List<GeneratedNetFloor> {
        val totalFloors = diceEngine.rollNd6(3).total.coerceIn(3, 18)
        val dv = difficulty.dv
        val result = mutableListOf<GeneratedNetFloor>()

        for (floor in 1..totalFloors) {
            if (floor <= 2) {
                val r = diceEngine.rollNd6(1).total
                val (type, title, valNum) = when (r) {
                    1 -> Triple(NetNodeType.FILE, "Arquivo (Eye-Dee)", dv)
                    2 -> Triple(NetNodeType.PASSWORD, "Senha (Backdoor)", 6)
                    3 -> Triple(NetNodeType.PASSWORD, "Senha Reforçada (Backdoor)", 8)
                    4 -> Triple(NetNodeType.BLACK_ICE, "Skunk (Black ICE)", 10)
                    5 -> Triple(NetNodeType.BLACK_ICE, "Wisp (Black ICE)", 15)
                    else -> Triple(NetNodeType.BLACK_ICE, "Killer (Black ICE)", 20)
                }
                result.add(GeneratedNetFloor(floor, type, title, valNum))
            } else {
                val r = diceEngine.rollNd6(3).total
                val entry = bodyFloorEntry(difficulty, r, dv)
                result.add(GeneratedNetFloor(floor, entry.first, entry.second, entry.third))
            }
        }
        return result
    }

    private fun bodyFloorEntry(
        difficulty: NetArchDifficulty,
        roll3d6: Int,
        dv: Int
    ): Triple<NetNodeType, String, Int> {
        return when (roll3d6) {
            9, 12 -> Triple(NetNodeType.PASSWORD, "Senha de Rede (Backdoor)", dv)
            10 -> Triple(NetNodeType.FILE, "Arquivo Confidencial (Eye-Dee)", dv)
            11 -> Triple(NetNodeType.CONTROL_NODE, "Nó de Controle (Torreta/Câmera)", dv)
            else -> when (difficulty) {
                NetArchDifficulty.BASIC -> when (roll3d6) {
                    3, 6 -> Triple(NetNodeType.BLACK_ICE, "Hellhound (Black ICE)", 20)
                    4 -> Triple(NetNodeType.BLACK_ICE, "Sabertooth (Black ICE)", 25)
                    5 -> Triple(NetNodeType.BLACK_ICE, "Raven x2 (Black ICE)", 30)
                    7 -> Triple(NetNodeType.BLACK_ICE, "Wisp (Black ICE)", 15)
                    8 -> Triple(NetNodeType.BLACK_ICE, "Raven (Black ICE)", 15)
                    13 -> Triple(NetNodeType.BLACK_ICE, "Skunk (Black ICE)", 10)
                    14 -> Triple(NetNodeType.BLACK_ICE, "Asp (Black ICE)", 15)
                    15 -> Triple(NetNodeType.BLACK_ICE, "Scorpion (Black ICE)", 15)
                    16 -> Triple(NetNodeType.BLACK_ICE, "Killer + Skunk (Black ICE)", 30)
                    17 -> Triple(NetNodeType.BLACK_ICE, "Wisp x3 (Black ICE)", 45)
                    else -> Triple(NetNodeType.BLACK_ICE, "Liche (Black ICE)", 25)
                }
                NetArchDifficulty.STANDARD -> when (roll3d6) {
                    3 -> Triple(NetNodeType.BLACK_ICE, "Hellhound x2 (Black ICE)", 40)
                    4 -> Triple(NetNodeType.BLACK_ICE, "Hellhound + Killer (Black ICE)", 40)
                    5 -> Triple(NetNodeType.BLACK_ICE, "Skunk x2 (Black ICE)", 20)
                    6 -> Triple(NetNodeType.BLACK_ICE, "Sabertooth (Black ICE)", 25)
                    7 -> Triple(NetNodeType.BLACK_ICE, "Scorpion (Black ICE)", 15)
                    8 -> Triple(NetNodeType.BLACK_ICE, "Hellhound (Black ICE)", 20)
                    13, 16 -> Triple(NetNodeType.BLACK_ICE, "Asp (Black ICE)", 15)
                    14 -> Triple(NetNodeType.BLACK_ICE, "Killer (Black ICE)", 20)
                    15 -> Triple(NetNodeType.BLACK_ICE, "Liche (Black ICE)", 25)
                    17 -> Triple(NetNodeType.BLACK_ICE, "Raven x3 (Black ICE)", 45)
                    else -> Triple(NetNodeType.BLACK_ICE, "Liche + Raven (Black ICE)", 40)
                }
                NetArchDifficulty.UNCOMMON -> when (roll3d6) {
                    3 -> Triple(NetNodeType.BLACK_ICE, "Kraken (Black ICE)", 30)
                    4 -> Triple(NetNodeType.BLACK_ICE, "Hellhound + Scorpion (Black ICE)", 35)
                    5 -> Triple(NetNodeType.BLACK_ICE, "Hellhound + Killer (Black ICE)", 40)
                    6 -> Triple(NetNodeType.BLACK_ICE, "Raven x2 (Black ICE)", 30)
                    7 -> Triple(NetNodeType.BLACK_ICE, "Sabertooth (Black ICE)", 25)
                    8 -> Triple(NetNodeType.BLACK_ICE, "Hellhound (Black ICE)", 20)
                    13 -> Triple(NetNodeType.BLACK_ICE, "Killer (Black ICE)", 20)
                    14 -> Triple(NetNodeType.BLACK_ICE, "Liche (Black ICE)", 25)
                    15 -> Triple(NetNodeType.BLACK_ICE, "Dragon (Black ICE)", 30)
                    16 -> Triple(NetNodeType.BLACK_ICE, "Asp + Raven (Black ICE)", 30)
                    17 -> Triple(NetNodeType.BLACK_ICE, "Dragon + Wisp (Black ICE)", 45)
                    else -> Triple(NetNodeType.BLACK_ICE, "Giant (Black ICE)", 25)
                }
                NetArchDifficulty.ADVANCED -> when (roll3d6) {
                    3 -> Triple(NetNodeType.BLACK_ICE, "Hellhound x3 (Black ICE)", 60)
                    4 -> Triple(NetNodeType.BLACK_ICE, "Asp x2 (Black ICE)", 30)
                    5 -> Triple(NetNodeType.BLACK_ICE, "Hellhound + Liche (Black ICE)", 45)
                    6 -> Triple(NetNodeType.BLACK_ICE, "Wisp x3 (Black ICE)", 45)
                    7 -> Triple(NetNodeType.BLACK_ICE, "Hellhound + Sabertooth (Black ICE)", 45)
                    8, 16 -> Triple(NetNodeType.BLACK_ICE, "Kraken (Black ICE)", 30)
                    13 -> Triple(NetNodeType.BLACK_ICE, "Giant (Black ICE)", 25)
                    14 -> Triple(NetNodeType.BLACK_ICE, "Dragon (Black ICE)", 30)
                    15 -> Triple(NetNodeType.BLACK_ICE, "Killer + Scorpion (Black ICE)", 35)
                    17 -> Triple(NetNodeType.BLACK_ICE, "Raven + Wisp + Hellhound", 50)
                    else -> Triple(NetNodeType.BLACK_ICE, "Dragon x2 (Black ICE)", 60)
                }
            }
        }
    }

    val roleAbilitySummaryPt: Map<RedRole, String> = mapOf(
        RedRole.SOLO to "Percepção de Combate: divide pontos entre Redução de Dano (2–10 pts = -1 a -5 dano/rodada), Recuperação de Falha (4 pts), Reação de Iniciativa (+1/pt), Ataque Preciso (3/6/9 pts = +1/+2/+3), Ponto Fraco (+1 dano/pt) e Detecção de Ameaça (+1 Percepção/pt).",
        RedRole.NETRUNNER to "Interface: Ações NET/turno (Nv 1-3: 2 | Nv 4-6: 3 | Nv 7-9: 4 | Nv 10: 5). Habilidades: Scanner, Backdoor, Pathfinder, Cloak, Control, Eye-Dee, Slide, Virus, Zap.",
        RedRole.TECH to "Maker (Campo, Upgrade, Fabricação, Invenção): DVs/Tempo por preço — Barato/Comum DV9 (1h) | Caro DV13 (6h) | Premium DV17 (1d) | Dispendioso DV21 (1sem) | Muito Dispendioso DV24 (2sem) | Luxo DV29 (1mês) | Super Luxo DV29 (1mês/10k eb).",
        RedRole.MEDTECH to "Medicina (Cirurgia +2 pts/rank; Fármacos ou Criooperação): Fármacos DV13 (200eb): Antibiótico (+2 PV/dia), Rapidetox (corta drogas/toxinas), Speedheal (cura COR+VON imediato), Stim (ignora Ferimento Grave 1h).",
        RedRole.MEDIA to "Credibilidade: testes passivos de Rumores (2x/semana) e ativos de convencimento/exposição pública conforme alcance da matéria.",
        RedRole.EXEC to "Trabalho em Equipe: concede Traje Executivo (Nv1), Carro (Nv2), Membros de Equipe (Nv3/5/9 até 3), Cobertura Trauma Team Prata (Nv6) e Ouro (Nv8), e moradia corporativa.",
        RedRole.LAWMAN to "Reforço (Backup): 1 Ação rolando 1d10 ≤ Nível de Reforço chama reforços armados do escalão policial/corporativo.",
        RedRole.FIXER to "Operador: negocia preços acima/abaixo do mercado, acessa Night Markets por categoria de preço e garante contatos/idiomas.",
        RedRole.NOMAD to "Moto: adiciona Nível em Dirigir/Pilotar e libera veículos ou upgrades exclusivos da Frota da Família.",
        RedRole.ROCKERBOY to "Impacto Carismático: converte público em fãs e pede favores (Individual DV8, Grupo até 6 DV10, Multidão DV12)."
    )
}
