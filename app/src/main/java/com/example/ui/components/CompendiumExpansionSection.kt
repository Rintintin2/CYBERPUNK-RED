package com.example.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.rules.OfficialDangerGalGearAndLore
import com.example.core.rules.OfficialDangerGalNpcs
import com.example.core.rules.OfficialGangsAndCitizensCatalog
import com.example.core.rules.OfficialNightCityAtlasCatalog
import com.example.core.rules.OfficialSinglePlayerPlusCatalog
import com.example.core.rules.OfficialStealthAndAgentsCatalog
import com.example.core.rules.OfficialStreetratPregensCatalog
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson

val SOLO_STEALTH_AGENTS_TABS = listOf(
    "Modo Solo+ (22 Tab.)",
    "Going Quiet (Flash & Gear)",
    "All About Agents (Apps)"
)

val ATLAS_GANGS_DGD_TABS = listOf(
    "Atlas 24 Distritos",
    "Gangues (44)",
    "Pessoas Comuns (20)",
    "Facções DGD (16)",
    "The Incident (10 Beats)",
    "Single Shot (10)"
)

fun LazyListScope.compendiumSoloStealthAgentsItems(selectedTab: Int) {
    when (selectedTab) {
        0 -> {
            item {
                CyberPanel(accentColor = CyberEmerald) {
                    Text(
                        "SINGLE PLAYER MODE+ (REGRAS & ORÁCULO 1d10)",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberEmerald
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialSinglePlayerPlusCatalog.soloTweaksGuidePt.forEach { rule ->
                        Text("• $rule", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            items(OfficialSinglePlayerPlusCatalog.allTables, key = { "solo_tab_${it.id}" }) { table ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text(
                        "${table.titlePt} [${table.dieLabel} • ${table.category.labelPt}]",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = CyberCyan
                    )
                    Spacer(Modifier.height(4.dp))
                    table.rows.take(5).forEach { r ->
                        Text(r, style = MaterialTheme.typography.bodySmall)
                    }
                    if (table.rows.size > 5) {
                        Text(
                            "... +${table.rows.size - 5} entradas (role no Gerador Solo da Sessão).",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberAmber
                        )
                    }
                }
            }
        }
        1 -> {
            item {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text(
                        "FLASH OF LUCK & STEALTH NETRUNNING (GOING QUIET)",
                        style = MaterialTheme.typography.labelLarge,
                        color = RedlineCrimson
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialStealthAndAgentsCatalog.flashOfLuckRulesPt.forEach { r ->
                        Text("• $r", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    OfficialStealthAndAgentsCatalog.stealthNetrunningRulesPt.forEach { r ->
                        Text("• $r", style = MaterialTheme.typography.bodySmall, color = CyberCyan)
                    }
                }
            }
            items(OfficialStealthAndAgentsCatalog.goingQuietGear, key = { "gq_comp_${it.nameEn}" }) { item ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("${item.namePt} (${item.costEb}eb)", style = MaterialTheme.typography.titleSmall, color = CyberAmber)
                    Text(item.notesPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        else -> {
            item {
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        "ALL ABOUT AGENTS — HACKING, BREACHER & CRUNCH WHISTLE",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialStealthAndAgentsCatalog.agentHackingRulesPt.forEach { r ->
                        Text("• $r", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            items(OfficialStealthAndAgentsCatalog.agentsGearAndApps, key = { "agent_comp_${it.nameEn}" }) { item ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text(
                        "${item.namePt} [${item.subCategoryPt}] • ${item.costEb}eb",
                        style = MaterialTheme.typography.titleSmall,
                        color = CyberEmerald
                    )
                    Text(item.notesPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

fun LazyListScope.compendiumAtlasGangsDgdItems(selectedTab: Int) {
    when (selectedTab) {
        0 -> {
            items(OfficialNightCityAtlasCatalog.districts, key = { "atlas_comp_${it.code}" }) { d ->
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        "${d.code} — ${d.namePt} (${d.zone.labelPt})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = CyberCyan
                    )
                    Text(
                        "Segurança: ${d.securityProvider} • Locais: ${d.locations.size} • Gangues: ${d.gangsPresent.joinToString(", ")}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(d.summaryPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        1 -> {
            items(OfficialGangsAndCitizensCatalog.gangGuideEntries, key = { "gang_comp_${it.id}" }) { gang ->
                CyberPanel(accentColor = RedlineCrimson) {
                    Text(
                        "${gang.gangNamePt} • ${gang.statCard.hp} PV (${gang.statCard.armorLabelPt})",
                        style = MaterialTheme.typography.titleSmall,
                        color = RedlineCrimson
                    )
                    Text("Território: ${gang.districtsPt}", style = MaterialTheme.typography.labelSmall, color = CyberAmber)
                    Text("${gang.affiliationThemePt} | ${gang.statCard.keySkillBasesPt}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        2 -> {
            items(OfficialGangsAndCitizensCatalog.everydayCitizens, key = { "cit_comp_${it.id}" }) { c ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text(
                        "${c.namePt} • ${c.statCard.hp} PV (${c.statCard.armorLabelPt})",
                        style = MaterialTheme.typography.titleSmall,
                        color = CyberEmerald
                    )
                    Text("Variantes: ${c.variantsPt.joinToString(", ")}", style = MaterialTheme.typography.labelSmall, color = CyberAmber)
                    Text("${c.statCard.roleSummaryPt} • ${c.statCard.keySkillBasesPt}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        3 -> {
            item {
                CyberPanel(accentColor = CyberAmber) {
                    Text(
                        "DGD: DIRETRIZES & TEMPLATES DE NPCS",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialDangerGalGearAndLore.dgdNpcCreationGuidelinesPt.forEach { g ->
                        Text("• $g", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(6.dp))
                    OfficialDangerGalGearAndLore.dgdCustomVehicles.forEach { v ->
                        Text("• Veículo DGD: ${v.namePt} (${v.costEb}eb, SDP ${v.sdp}) — ${v.notesPt}", style = MaterialTheme.typography.bodySmall, color = CyberCyan)
                    }
                }
            }
            items(OfficialDangerGalGearAndLore.dgdFactions, key = { "dgd_fac_${it.id}" }) { f ->
                val count = OfficialDangerGalNpcs.byFaction(f.id).size
                CyberPanel(accentColor = RedlineCrimson) {
                    Text(
                        "${f.namePt} ($count NPCs) • ${f.threatTierPt}",
                        style = MaterialTheme.typography.titleSmall,
                        color = RedlineCrimson
                    )
                    Text("Território: ${f.turfPt} • Atitude: ${f.attitudePt}", style = MaterialTheme.typography.labelSmall, color = CyberAmber)
                    Text(f.overviewPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        4 -> {
            item {
                CyberPanel(accentColor = CyberEmerald) {
                    Text("THE INCIDENT (AVENTURA DANGER GAL DOSSIER)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    Text(OfficialDangerGalGearAndLore.theIncidentSummary, style = MaterialTheme.typography.bodySmall)
                }
            }
            items(OfficialDangerGalGearAndLore.theIncidentBeats, key = { "inc_beat_${it.beatNumber}" }) { b ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text(
                        "Beat ${b.beatNumber}: ${b.titlePt} [${b.beatTypePt} • Distrito ${b.districtCode}]",
                        style = MaterialTheme.typography.titleSmall,
                        color = CyberCyan
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(b.summaryPt, style = MaterialTheme.typography.bodySmall)
                    Text(b.checksAndMechanicsPt, style = MaterialTheme.typography.labelSmall, color = CyberAmber)
                }
            }
        }
        else -> {
            items(OfficialStreetratPregensCatalog.allPregens, key = { "pregen_comp_${it.id}" }) { p ->
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        "${p.handle} — ${p.role.displayNamePt} (Rank ${p.roleRank})",
                        style = MaterialTheme.typography.titleSmall,
                        color = CyberCyan
                    )
                    Text(
                        "HUM ${p.currentHumanity}/${p.maxHumanity} • PA ${p.bodySp} • ${p.startingCashEb}eb | ${p.roleSpecialDetailPt}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(p.bioPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
