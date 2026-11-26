package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.rules.EncounterTimeOfDay
import com.example.core.rules.OfficialEconomyAndProgression
import com.example.core.rules.OfficialEncountersAndLoreCatalog
import com.example.core.rules.OfficialNpcCatalog
import com.example.core.rules.OfficialWeaponsAndDefensesCatalog
import com.example.core.rules.RedRole
import com.example.core.rules.WeaponQuality
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson

val PART2_COMPENDIUM_TABS = listOf(
    "Qualidade & Munições",
    "Economia & Estilo",
    "Defesas NET",
    "NPCs & Encontros",
    "Night City 2045"
)

fun LazyListScope.compendiumPart2Items(selectedPart2Tab: Int) {
    when (selectedPart2Tab) {
        0 -> {
            item {
                CyberPanel(accentColor = CyberAmber) {
                    Text("QUALIDADE DE ARMAS (p. 342)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    WeaponQuality.entries.forEach { q ->
                        val note = when (q) {
                            WeaponQuality.POOR -> "1 categoria abaixo • Falha Crítica (1) trava arma (exige Ação p/ destravar)."
                            WeaponQuality.STANDARD -> "Preço padrão de catálogo • Confiável em combate."
                            WeaponQuality.EXCELLENT -> "1 categoria acima • Concede +1 no Teste de Ataque."
                        }
                        Text("• ${q.labelPt}: $note", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text("ACESSÓRIOS DE ARMA (8 OFICIAIS)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialWeaponsAndDefensesCatalog.weaponAttachments.forEach { att ->
                        Text(
                            "• ${att.namePt} (${att.costEb}eb • ${att.slotsRequired} slots): ${att.eligiblePt}. ${att.effectPt}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Text("TABELA DE PENTES (PADRÃO / ESTENDIDO / TAMBOR)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    OfficialWeaponsAndDefensesCatalog.clipChart.forEach { c ->
                        Text(
                            "• ${c.weaponTypePt}: ${c.standardShots} / ${c.extendedShots} / ${c.drumShots}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            items(OfficialWeaponsAndDefensesCatalog.specialAmmunition, key = { "ammo_${it.nameEn}" }) { ammo ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("${ammo.namePt} (${ammo.costEb}eb • ${ammo.priceCategory.labelPt})", style = MaterialTheme.typography.titleSmall, color = CyberAmber)
                    Text(ammo.effectPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        1 -> item {
            var selectedHustleRole by remember { mutableStateOf(RedRole.SOLO) }
            var selectedFashionIdx by remember { mutableIntStateOf(0) }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CyberPanel(accentColor = CyberAmber) {
                    Text("ESTILO DE VIDA (MENSAL)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialEconomyAndProgression.lifestyles.forEach { ls ->
                        Text("• ${ls.namePt} (${ls.monthlyCostEb}eb/mês): ${ls.descriptionPt}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text("MORADIA & ALUGUEL (p. 378)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialEconomyAndProgression.housings.forEach { h ->
                        val rentTxt = h.monthlyRentEb?.let { "${it}eb/mês" } ?: "N/A"
                        val buyTxt = h.buyPriceEb?.let { " | Compra: ${it}eb" } ?: ""
                        Text("• ${h.namePt} ($rentTxt$buyTxt): ${h.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text("PAGAMENTO DE CONTRATOS (JOB PAY)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    OfficialEconomyAndProgression.jobPayTiers.forEach { job ->
                        Text("• ${job.labelPt}: ${job.payPerPersonEb}eb/pessoa — ${job.descriptionPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                CyberPanel(accentColor = CyberCyan) {
                    Text("HUSTLE SEMANAL POR FUNÇÃO (7 DIAS)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(RedRole.entries) { role ->
                            FilterChip(
                                selected = selectedHustleRole == role,
                                onClick = { selectedHustleRole = role },
                                label = { Text(role.displayNamePt) }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    OfficialEconomyAndProgression.hustleTables.getValue(selectedHustleRole).forEach { row ->
                        Text(
                            "1d6=${row.roll1d6}: ${row.activityPt} [R1-4: ${row.payRank1To4}eb | R5-7: ${row.payRank5To7}eb | R8-10: ${row.payRank8To10}eb]",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("SERVIÇOS & ENTRETENIMENTO (p. 376)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialEconomyAndProgression.servicesAndEntertainment.forEach { s ->
                        Text("• ${s.namePt}: ${s.costEb}eb (${s.priceCategory.labelPt})", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text("MODA & PREÇOS POR PEÇA (10 ESTILOS)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(OfficialEconomyAndProgression.fashionStyles.indices.toList()) { idx ->
                            val st = OfficialEconomyAndProgression.fashionStyles[idx]
                            FilterChip(
                                selected = selectedFashionIdx == idx,
                                onClick = { selectedFashionIdx = idx },
                                label = { Text(st.namePt) }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    val fashion = OfficialEconomyAndProgression.fashionStyles[selectedFashionIdx]
                    Text("${fashion.namePt} — ${fashion.vibePt}", style = MaterialTheme.typography.labelMedium, color = CyberAmber)
                    fashion.pricesBySlotEb.entries.forEach { (slot, eb) ->
                        Text("• ${slot.labelPt}: ${eb}eb", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        2 -> {
            item {
                CyberPanel(accentColor = CyberCyan) {
                    Text("CUSTO DE ARQUITETURA & NÓS NET (p. 372)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialWeaponsAndDefensesCatalog.netFloorPricingTiers.forEach { (floors, spec, eb) ->
                        Text("• $floors ($spec): ${eb}eb", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(6.dp))
                    OfficialWeaponsAndDefensesCatalog.netNodeDvPricing.forEach { tier ->
                        Text("• ${tier.labelPt}: ${tier.dvOrCost}eb", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(6.dp))
                    OfficialWeaponsAndDefensesCatalog.netDefenseDvPricing.forEach { tier ->
                        Text("• ${tier.labelPt}: ${tier.dvOrCost}eb", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            items(OfficialWeaponsAndDefensesCatalog.netDefenses, key = { "ndef_${it.namePt}" }) { def ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("${def.namePt} [${def.category.labelPt}]", style = MaterialTheme.typography.titleSmall, color = RedlineCrimson)
                    val stats = buildString {
                        def.hp?.let { append("PV $it • ") }
                        def.move?.let { append("MOV $it • ") }
                        def.combatNumber?.let { append("Combate $it • ") }
                        append("Contramedida DV${def.counterDv} (${def.counterTimeMin} min)")
                    }
                    Text(stats, style = MaterialTheme.typography.labelMedium, color = CyberAmber)
                    Text("Gatilho: ${def.triggerPt} — ${def.effectPt}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        3 -> item {
            var encounterTime by remember { mutableStateOf(EncounterTimeOfDay.DAYTIME) }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text("ESCALA DE ENCONTROS & NPCs OFICIAIS (pp. 412–416)", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Text(
                        "Proporção oficial: 2 Capangas/Jogador • 1 Tenente/Jogador • 1 Subchefe/2 Jogadores • 1 Chefe/4 Jogadores.",
                        style = MaterialTheme.typography.bodySmall,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(8.dp))
                    OfficialNpcCatalog.allNpcs.forEach { npc ->
                        Text(
                            "• ${npc.namePt} [${npc.tier.labelPt}] — REF ${npc.reflexes} | PV ${npc.hp} | ${npc.armorLabelPt}",
                            style = MaterialTheme.typography.titleSmall,
                            color = CyberCyan
                        )
                        Text(
                            "Armas: ${npc.weapons.joinToString(", ") { "${it.namePt} (${it.damageDice}d6)" }} | ${npc.keySkillBasesPt}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(6.dp))
                    }
                }
                CyberPanel(accentColor = CyberAmber) {
                    Text("ENCONTROS ALEATÓRIOS NA RUA (d100 • pp. 417–424)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(EncounterTimeOfDay.entries) { tod ->
                            FilterChip(
                                selected = encounterTime == tod,
                                onClick = { encounterTime = tod },
                                label = { Text(tod.labelPt) }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    val table = when (encounterTime) {
                        EncounterTimeOfDay.DAYTIME -> OfficialEncountersAndLoreCatalog.daytimeEncounters
                        EncounterTimeOfDay.EVENING -> OfficialEncountersAndLoreCatalog.eveningEncounters
                        EncounterTimeOfDay.MIDNIGHT -> OfficialEncountersAndLoreCatalog.midnightEncounters
                    }
                    table.forEach { entry ->
                        val rStr = if (entry.range.first == entry.range.last) "${entry.range.first}" else "${entry.range.first}-${entry.range.last}"
                        Text("• d100 [$rStr] ${entry.titlePt}: ${entry.descriptionPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        else -> item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CyberPanel(accentColor = CyberEmerald) {
                    Text("ZONAS & NÍVEIS DE AMEAÇA DE NIGHT CITY (2045)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    OfficialEncountersAndLoreCatalog.threatRatings.forEach { (r, d) ->
                        Text("• $r: $d", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    OfficialEncountersAndLoreCatalog.nightCityZones.forEach { z ->
                        Text("• ${z.namePt} [${z.threatRatingPt}]: ${z.descriptionPt} (${z.neighborhoodsPt})", style = MaterialTheme.typography.bodySmall, color = CyberCyan)
                    }
                }
                CyberPanel(accentColor = RedlineCrimson) {
                    Text("GANGUES DE NIGHT CITY (14 OFICIAIS)", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Spacer(Modifier.height(4.dp))
                    OfficialEncountersAndLoreCatalog.nightCityGangs.forEach { g ->
                        Text("• ${g.name} (${g.categoryPt}): ${g.summaryPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(accentColor = CyberAmber) {
                    Text("MEGACORPORAÇÕES & 2º ESCALÃO", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    (OfficialEncountersAndLoreCatalog.majorCorporations + OfficialEncountersAndLoreCatalog.secondTierCorporations).forEach { c ->
                        Text("• ${c.name} [${c.tierPt}]: ${c.focusPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("BEAT CHARTS & TABELA DE I.P. POR ESTILO (pp. 395, 409)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialEncountersAndLoreCatalog.beatChartGuide.forEach { b ->
                        Text("• ${b.beatNamePt}: ${b.rulePt}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("CUSTOS DE I.P.: Perícia N×20 (Difícil ×2: N×40) | Habilidade de Função R×60", style = MaterialTheme.typography.labelMedium, color = CyberEmerald)
                    OfficialEconomyAndProgression.playstyleIpTable.forEach { row ->
                        Text("• ${row.ipAwarded} I.P. — Grupo: ${row.groupPt} | Guerreiro: ${row.warriorPt} | Explorador: ${row.explorerPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
