package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.rules.OfficialDlcCyberwareAndFbc
import com.example.core.rules.OfficialDlcGearCatalog
import com.example.core.rules.OfficialDlcNpcsAndNets
import com.example.core.rules.OfficialDlcSoloSystems
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson

val INTERFACE_DLC_TABS = listOf(
    "Armas DLC",
    "Cyberdecks & Impls.",
    "FBCs & Bioexóticos",
    "Drones, Rodas & Casa",
    "Clima, Salvage & Date",
    "Hardened, ELO & NET"
)

fun LazyListScope.compendiumInterfaceDlcItems(selectedDlcTab: Int) {
    when (selectedDlcTab) {
        0 -> {
            item {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text("12 DAYS OF GUNMAS (VOL. 2 • 12 ARMAS EXÓTICAS)", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcGearCatalog.gunmasWeapons.forEach { w ->
                        Text("• ${w.namePt} (${w.costEb}eb): ${w.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                CyberPanel(accentColor = CyberAmber) {
                    Text("WOODCHIPPER'S GARAGE (VOL. 3 • 12 ARMAS PESADAS)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcGearCatalog.woodchipperWeapons.forEach { w ->
                        Text("• ${w.namePt} (${w.costEb}eb): ${w.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("OLD GUNS NEVER DIE (CONVERSÃO CP2020 -> RED • VOL. 1)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcCyberwareAndFbc.oldGunsNeverDieStepsPt.forEach { step ->
                        Text("• $step", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        1 -> {
            item {
                CyberPanel(accentColor = CyberCyan) {
                    Text("MIDNIGHT WITH THE UPLOAD (15 CYBERDECKS • VOL. 3)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcGearCatalog.dlcCyberdecks.forEach { d ->
                        Text("• ${d.name} (${d.costEb}eb • ${d.slotsSummaryPt}): ${d.specialRulePt}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text("NOVOS HARDWARES DE CYBERDECK (11 × 500eb)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcGearCatalog.dlcCyberdeckHardware.forEach { hw ->
                        Text("• ${hw.name} (${hw.slots} slot): ${hw.effectPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            items(OfficialDlcCyberwareAndFbc.dlcCyberware, key = { "dlc_cw_${it.nameEn}" }) { cw ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("${cw.namePt} [${cw.category.labelPt}]", style = MaterialTheme.typography.titleSmall, color = CyberCyan)
                    Text("${cw.installLocation} • ${cw.costEb}eb • HL: ${cw.presetHl} (${cw.hlDiceFormula}) — ${cw.notesPt}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        2 -> {
            item {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text("GOING METAL: REGRAS DE FULL BODY CONVERSION (VOL. 3)", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcCyberwareAndFbc.fbcCoreRulesPt.forEach { r ->
                        Text("• $r", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            items(OfficialDlcCyberwareAndFbc.fbcBodies, key = { "fbc_${it.name}" }) { fbc ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("${fbc.name} [${fbc.roleFocusPt}]", style = MaterialTheme.typography.titleSmall, color = CyberAmber)
                    Text("Custo: ${fbc.costEb}eb • HL Total: ${fbc.humanityLossTotal} • COR ${fbc.bodyStat} | MOV ${fbc.moveStat} | PA ${fbc.sp}", style = MaterialTheme.typography.labelMedium, color = CyberCyan)
                    Text(fbc.highlightsPt, style = MaterialTheme.typography.bodySmall)
                }
            }
            item {
                CyberPanel(accentColor = CyberEmerald) {
                    Text("EXOTICS OF 2045 (17 PACOTES BIOEXÓTICOS • VOL. 2)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcCyberwareAndFbc.bioexoticPackages.forEach { ex ->
                        Text("• ${ex.name} (${ex.costEb}eb • ${ex.humanityLossTotal} HL): ${ex.coreComponentsPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        3 -> item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CyberPanel(accentColor = CyberCyan) {
                    Text("DRONES ATIVOS & PESSOAIS + CIBER-CADEIRAS (VOL. 1)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcGearCatalog.dronesAndCyberchairs.forEach { d ->
                        Text("• ${d.name} [${d.categoryPt}] (${d.costEb}eb): MOV ${d.move}, PV ${d.hp}, PA ${d.sp}, DV${d.counterDv} — ${d.loadoutPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(accentColor = CyberAmber) {
                    Text("SPINNING YOUR WHEELS: BICICLETAS & UPGRADES (VOL. 3)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    Text("Bicicleta (100eb • 15 SDP • Atletismo): COR<4 -> MOV 8 (12 MPH) | COR 4–7 -> MOV 10 (20 MPH) | COR 8+ -> MOV 15 (30 MPH). Fadiga: Resistência DV13/15/17/21 (1/3/5/6+h).", style = MaterialTheme.typography.bodySmall, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcGearCatalog.bicycleUpgrades.forEach { (name, info) ->
                        Text("• $name (${info.first}eb): ${info.second}", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("CUBE HOTELS (500eb) & CARGO CONTAINERS (1.000eb) — VOL. 2", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcSoloSystems.cubeHotels1d10.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }
                    Spacer(Modifier.height(6.dp))
                    OfficialDlcSoloSystems.cargoContainers1d10.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }
                }
            }
        }
        4 -> item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CyberPanel(accentColor = CyberAmber) {
                    Text("CLIMA DE NIGHT CITY (1d10 CLIMA ESTRANHO • VOL. 2)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcSoloSystems.strangeWeather1d10.forEachIndexed { idx, (name, eff) ->
                        Text("• ${idx + 1}: $name — $eff", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(accentColor = CyberCyan) {
                    Text("SALVAGING NIGHT CITY (1 SEMANA • TECNOLOGIA BÁSICA • VOL. 3)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    Text("DVs (Aleatório / Categoria Específica): 10eb (9/13) | 20eb (13/15) | 50eb (15/17) | 100eb (17/21) | 500eb (24/29) | 1.000eb (29/N/A). Falha Crítica (1) rola Perigo 1d6:", style = MaterialTheme.typography.bodySmall)
                    OfficialDlcSoloSystems.perilsOfSalvaging1d6.forEach { p ->
                        Text("• $p", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("DATEPATH (ENCONTROS DIGITAIS) & EQUIP. CLIMÁTICO", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Text("Use o Gerador Solo na aba Sessão para rolar Clima Sazonal, Salvaging e Datepath diretamente no diário.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        else -> item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text("OPONENTES ENDURECIDOS (16 HARDENED • VOL. 2–3)", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Text("Critérios de Edgerunner Hardened: REF8+Evasão6, Base 15+, VON+COR≥16, Arma Luxo+, DES8&MOV8, Autofire/Artes Marcias 6+ ou Solo 4+.", style = MaterialTheme.typography.bodySmall, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcNpcsAndNets.hardenedNpcs.forEach { npc ->
                        Text("• ${npc.namePt} [${npc.tier.labelPt}] — REF ${npc.reflexes} | PV ${npc.hp} | ${npc.armorLabelPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(accentColor = CyberCyan) {
                    Text("ARQUITETURAS NET PRONTAS (9 OFICIAIS • SINGLE SHOT / JSK)", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcNpcsAndNets.premadeNetArchitectures.forEach { arch ->
                        Text("• ${arch.namePt} (${arch.sourcePt} • ${arch.totalCostEb}eb): ${arch.summaryPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("ELFLINES ONLINE (ELO + TCG) & SINGLE SHOT PREGENS", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcSoloSystems.eloSummaryAndMonstersPt.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }
                    Spacer(Modifier.height(4.dp))
                    OfficialDlcNpcsAndNets.singleShotPregensPt.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }
                }
            }
        }
    }
}
