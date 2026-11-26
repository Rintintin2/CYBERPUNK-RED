package com.example.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.rules.OfficialBlackChromeCyberwareCatalog
import com.example.core.rules.OfficialBlackChromeGearCatalog
import com.example.core.rules.OfficialGearCatalog
import com.example.core.rules.OfficialHookBookData
import com.example.core.rules.OfficialTalesAndHookBookCatalog
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson

val BLACK_CHROME_TABS = listOf(
    "Armas BC",
    "Cyber & Fingers",
    "Moda, Gear & Frames",
    "Veículos (Nomad)",
    "Night Markets & Econ.",
    "Ganchos & TOTR+"
)

fun LazyListScope.compendiumBlackChromeItems(selectedBcTab: Int) {
    when (selectedBcTab) {
        0 -> {
            item {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text(
                        "BLACK CHROME: ARMAS DE FOGO (${OfficialBlackChromeGearCatalog.blackChromeFirearms.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = RedlineCrimson
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeGearCatalog.blackChromeFirearms.forEach { w ->
                        Text("• ${w.namePt} (${w.costEb}eb): ${w.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                CyberPanel(accentColor = CyberAmber) {
                    Text(
                        "BLACK CHROME: EXPLOSIVOS, ARMAS BRANCAS & ARREMESSO (${OfficialBlackChromeGearCatalog.blackChromeExplosivesAndMelee.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeGearCatalog.blackChromeExplosivesAndMelee.forEach { w ->
                        Text("• ${w.namePt} (${w.costEb}eb): ${w.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        1 -> {
            item {
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        "CYBERWARE & FRAMES INTERNOS BLACK CHROME (${OfficialBlackChromeCyberwareCatalog.blackChromeCyberware.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                }
            }
            items(OfficialBlackChromeCyberwareCatalog.blackChromeCyberware, key = { "bc_cw_${it.nameEn}" }) { cw ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("${cw.namePt} [${cw.category.labelPt}]", style = MaterialTheme.typography.titleSmall, color = CyberCyan)
                    Text("${cw.installLocation} • ${cw.costEb}eb • HL: ${cw.presetHl} (${cw.hlDiceFormula}) — ${cw.notesPt}", style = MaterialTheme.typography.bodySmall)
                }
            }
            item {
                CyberPanel(accentColor = CyberAmber) {
                    Text(
                        "DYNALAR CYBERFINGERS (${OfficialBlackChromeCyberwareCatalog.blackChromeCyberfingers.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeCyberwareCatalog.blackChromeCyberfingers.forEach { cf ->
                        Text("• ${cf.namePt} (${cf.costEb}eb, HL ${cf.hlDiceFormula}): ${cf.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        2 -> {
            item {
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        "APPS OFICIAIS BLACK CHROME (${OfficialBlackChromeGearCatalog.blackChromeApps.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeGearCatalog.blackChromeApps.forEach { app ->
                        Text("• ${app.namePt} (${app.costEb}eb): ${app.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                CyberPanel(accentColor = CyberEmerald) {
                    Text(
                        "MODA BLINDADA, ARMADURAS & TRAJES (${OfficialBlackChromeGearCatalog.blackChromeFashionAndArmor.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberEmerald
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeGearCatalog.blackChromeFashionAndArmor.forEach { item ->
                        Text("• ${item.namePt} (${item.costEb}eb): ${item.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                CyberPanel(accentColor = CyberAmber) {
                    Text(
                        "EQUIPAMENTOS & EXOESQUELETOS (LINEAR FRAMES EXTERNOS • ${OfficialBlackChromeGearCatalog.blackChromeGeneralGearAndFrames.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeGearCatalog.blackChromeGeneralGearAndFrames.forEach { gear ->
                        Text("• ${gear.namePt} (${gear.costEb}eb): ${gear.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        3 -> {
            item {
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        "FROTA COMPLETA DE VEÍCULOS (NOMAD ACCESS • ${OfficialGearCatalog.allVehicles.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                    Text(
                        "Regra Nomad Access: ao subir Moto, o Nômade escolhe veículo com NA ≤ Rank de Moto.",
                        style = MaterialTheme.typography.bodySmall,
                        color = CyberAmber
                    )
                }
            }
            items(OfficialGearCatalog.allVehicles, key = { "veh_${it.namePt}_${it.costEb}" }) { v ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("${v.namePt} [${v.domainPt}]", style = MaterialTheme.typography.titleSmall, color = CyberCyan)
                    Text(
                        "SDP ${v.sdp} • Assentos: ${v.seats} • MOV ${v.combatMove} (${v.narrativeSpeed}) • ${v.costEb}eb",
                        style = MaterialTheme.typography.labelMedium,
                        color = CyberAmber
                    )
                    Text(v.notesPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        4 -> {
            item {
                CyberPanel(accentColor = CyberEmerald) {
                    Text("6 NIGHT MARKETS & FIXERS (BLACK CHROME)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeCyberwareCatalog.sixNightMarketsPt.forEach { (title, desc) ->
                        Text("• $title: $desc", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(3.dp))
                    }
                }
            }
            item {
                CyberPanel(accentColor = CyberAmber) {
                    Text("ECONOMIA, MANHA (STREETWISE) & ESTILOS DE VIDA", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialBlackChromeCyberwareCatalog.economyAndFixerDvsPt.forEach { line ->
                        Text("• $line", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text(
                        "CHEFÕES FIXERS & NPCS NOMEADOS (12 • BC & TOTR+)",
                        style = MaterialTheme.typography.labelLarge,
                        color = RedlineCrimson
                    )
                    Spacer(Modifier.height(4.dp))
                    OfficialTalesAndHookBookCatalog.namedDlcNpcs.forEach { npc ->
                        Text(
                            "• ${npc.namePt} [${npc.tier.labelPt}] — ${npc.roleAbilityLabelPt ?: ""} | PV ${npc.hp} | ${npc.armorLabelPt}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        else -> {
            item {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text("TALES OF THE RED+ (MEGABITE, VENDEDORES & SCREAMSHEETS)", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Spacer(Modifier.height(4.dp))
                    Text(OfficialTalesAndHookBookCatalog.upgradedMegabiteRulePt, style = MaterialTheme.typography.bodySmall, color = CyberAmber)
                    Spacer(Modifier.height(6.dp))
                    Text("VENDEDORES EXTRAS RC NIGHT MARKET (1d6):", style = MaterialTheme.typography.labelMedium, color = CyberCyan)
                    OfficialTalesAndHookBookCatalog.rcNightMarketVendors1d6.forEach { v ->
                        Text("• $v", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(6.dp))
                    Text("5 SCREAMSHEETS DE NIGHT CITY TODAY:", style = MaterialTheme.typography.labelMedium, color = CyberEmerald)
                    OfficialTalesAndHookBookCatalog.totrScreamsheetsSummaryPt.forEach { s ->
                        Text("• $s", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        "NIGHT CITY HOOK BOOK (${OfficialHookBookData.allPlotHooks.size} GANCHOS EM ${OfficialHookBookData.districtsPt.size} DISTRITOS/ATRAÇÕES)",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                }
            }
            items(OfficialHookBookData.allPlotHooks, key = { "hook_${it.districtPt}_${it.titlePt}" }) { hook ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("[${hook.districtPt}] ${hook.titlePt}", style = MaterialTheme.typography.titleSmall, color = CyberAmber)
                    Text(hook.summaryPt, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
