package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.rules.DatepathRollResult
import com.example.core.rules.EncounterRollResult
import com.example.core.rules.EncounterTimeOfDay
import com.example.core.rules.OfficialDlcNpcsAndNets
import com.example.core.rules.OfficialEconomyAndProgression
import com.example.core.rules.OfficialHookBookData
import com.example.core.rules.OfficialNpcCatalog
import com.example.core.rules.OfficialNpcSpec
import com.example.core.rules.OfficialTalesAndHookBookCatalog
import com.example.core.rules.PlotHookSpec
import com.example.core.rules.SalvageAttemptResult
import com.example.core.rules.SalvageCategory
import com.example.core.rules.SalvageWorthTier
import com.example.core.rules.WeatherRollResult
import com.example.core.rules.WeatherSeason
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson

@Composable
fun SoloGeneratorsDialog(
    lastEncounter: EncounterRollResult?,
    lastStreetPrompt: String?,
    lastWeatherResult: WeatherRollResult? = null,
    lastSalvageResult: SalvageAttemptResult? = null,
    lastDatepathResult: DatepathRollResult? = null,
    lastPlotHook: PlotHookSpec? = null,
    lastRcVendorPrompt: String? = null,
    onRollEncounter: (EncounterTimeOfDay) -> Unit,
    onSpawnNpc: (OfficialNpcSpec, Int) -> Unit,
    onRollStreetPrompt: (String) -> Unit,
    onRollWeather: (WeatherSeason) -> Unit = {},
    onRunSalvaging: (SalvageWorthTier, SalvageCategory?) -> Unit = { _, _ -> },
    onRollDatepath: () -> Unit = {},
    onRollPlotHook: (String?) -> Unit = {},
    onRollRcVendor: () -> Unit = {},
    onDismiss: () -> Unit
) {
    var selectedTime by remember { mutableStateOf(EncounterTimeOfDay.EVENING) }
    var npcPoolIndex by remember { mutableIntStateOf(0) } // 0=Padrão(10), 1=Hardened(16), 2=Fixers&TOTR+(12)
    val npcPool = when (npcPoolIndex) {
        1 -> OfficialDlcNpcsAndNets.hardenedNpcs
        2 -> OfficialTalesAndHookBookCatalog.namedDlcNpcs
        else -> OfficialNpcCatalog.allNpcs
    }
    var selectedNpc by remember(npcPoolIndex) { mutableStateOf(npcPool.first()) }
    var npcCount by remember { mutableIntStateOf(2) }

    var selectedSeason by remember { mutableStateOf(WeatherSeason.WINTER_DEC_FEB) }
    var selectedSalvageWorth by remember { mutableStateOf(SalvageWorthTier.EB_50) }
    var selectedSalvageCategory by remember { mutableStateOf<SalvageCategory?>(null) }
    var selectedHookDistrict by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Geradores Solo, Clima & Hostis") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CyberPanel(accentColor = RedlineCrimson) {
                    Text("ENCONTRO ALEATÓRIO (d100 • pp. 417–424)", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(EncounterTimeOfDay.entries) { tod ->
                            FilterChip(
                                selected = selectedTime == tod,
                                onClick = { selectedTime = tod },
                                label = { Text(tod.labelPt) }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Button(
                        onClick = { onRollEncounter(selectedTime) },
                        colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("roll_encounter_button")
                    ) {
                        Text("Rolar Encontro (${selectedTime.labelPt})")
                    }
                    if (lastEncounter != null) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "[d100=${lastEncounter.d100Roll}] ${lastEncounter.entry.titlePt}",
                            style = MaterialTheme.typography.titleSmall,
                            color = CyberAmber,
                            fontWeight = FontWeight.Bold
                        )
                        Text(lastEncounter.entry.descriptionPt, style = MaterialTheme.typography.bodySmall)
                        lastEncounter.resolvedNpc?.let { suggested ->
                            Spacer(Modifier.height(6.dp))
                            OutlinedButton(
                                onClick = {
                                    onSpawnNpc(suggested, lastEncounter.entry.suggestedNpcCount)
                                    onDismiss()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Adicionar ${lastEncounter.entry.suggestedNpcCount}x ${suggested.namePt} ao Combate")
                            }
                        }
                    }
                }

                CyberPanel(accentColor = CyberCyan) {
                    Text("INVOCAR NPC / HARDENED / NOMEADOS", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        item {
                            FilterChip(
                                selected = npcPoolIndex == 0,
                                onClick = { npcPoolIndex = 0 },
                                label = { Text("Padrão (10)") }
                            )
                        }
                        item {
                            FilterChip(
                                selected = npcPoolIndex == 1,
                                onClick = { npcPoolIndex = 1 },
                                label = { Text("Endurecidos (16)") },
                                modifier = Modifier.testTag("npc_hardened_toggle")
                            )
                        }
                        item {
                            FilterChip(
                                selected = npcPoolIndex == 2,
                                onClick = { npcPoolIndex = 2 },
                                label = { Text("Fixers & TOTR+ (12)") },
                                modifier = Modifier.testTag("npc_named_dlc_toggle")
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(npcPool, key = { it.id }) { npc ->
                            FilterChip(
                                selected = selectedNpc.id == npc.id,
                                onClick = {
                                    selectedNpc = npc
                                    npcCount = if (npcPoolIndex == 1) {
                                        OfficialNpcCatalog.hardenedRecommendedCount(npc.tier, partySize = 1)
                                    } else {
                                        OfficialNpcCatalog.recommendedCount(npc.tier, partySize = 1)
                                    }
                                },
                                label = { Text("${npc.namePt} (${npc.hp} PV)") }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "${selectedNpc.tier.labelPt} • REF ${selectedNpc.reflexes} | ${selectedNpc.armorLabelPt}",
                        style = MaterialTheme.typography.bodySmall,
                        color = CyberAmber
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(1, 2, 3, 4).forEach { qty ->
                            FilterChip(
                                selected = npcCount == qty,
                                onClick = { npcCount = qty },
                                label = { Text("${qty}x") }
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                        Button(
                            onClick = {
                                onSpawnNpc(selectedNpc, npcCount)
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Combate", color = MaterialTheme.colorScheme.onSecondary)
                        }
                    }
                }

                CyberPanel(accentColor = CyberAmber) {
                    Text("CLIMA DE NIGHT CITY (VOL. 2)", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(WeatherSeason.entries) { season ->
                            FilterChip(
                                selected = selectedSeason == season,
                                onClick = { selectedSeason = season },
                                label = { Text(season.labelPt.substringBefore(" ")) }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    OutlinedButton(
                        onClick = { onRollWeather(selectedSeason) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("roll_weather_button")
                    ) {
                        Text("Rolar Clima (${selectedSeason.labelPt})")
                    }
                    lastWeatherResult?.let { w ->
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "${w.strangeConditionNamePt ?: w.conditionNamePt}: ${w.mechanicalEffectPt}",
                            style = MaterialTheme.typography.bodySmall,
                            color = CyberAmber
                        )
                    }
                }

                CyberPanel(accentColor = CyberEmerald) {
                    Text("SALVAGING (1 SEMANA • VOL. 3)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(SalvageWorthTier.entries) { worth ->
                            FilterChip(
                                selected = selectedSalvageWorth == worth,
                                onClick = { selectedSalvageWorth = worth },
                                label = { Text("${worth.costEb}eb") }
                            )
                        }
                    }
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        item {
                            FilterChip(
                                selected = selectedSalvageCategory == null,
                                onClick = { selectedSalvageCategory = null },
                                label = { Text("Aleatório") }
                            )
                        }
                        items(SalvageCategory.entries) { cat ->
                            FilterChip(
                                selected = selectedSalvageCategory == cat,
                                onClick = { selectedSalvageCategory = cat },
                                label = { Text(cat.labelPt) }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    OutlinedButton(
                        onClick = { onRunSalvaging(selectedSalvageWorth, selectedSalvageCategory) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("run_salvaging_button")
                    ) {
                        Text("Vasculhar Escombros (+7 dias)")
                    }
                    lastSalvageResult?.let { s ->
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = if (s.isCriticalFailure) {
                                "Falha Crítica: ${s.perilTextPt}"
                            } else if (s.succeeded) {
                                "Encontrado: ${s.category.labelPt} (${s.worthTier.costEb}eb) [${s.rollTotal} > DV ${s.dv}]"
                            } else {
                                "Nada útil encontrado (${s.rollTotal} vs DV ${s.dv})."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = CyberCyan
                        )
                    }
                }

                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text("RUA, BODEGA & THE DATEPATH", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { onRollStreetPrompt("screamsheet") }, modifier = Modifier.weight(1f)) {
                            Text("Jornal")
                        }
                        OutlinedButton(onClick = { onRollStreetPrompt("vendit") }, modifier = Modifier.weight(1f)) {
                            Text("Vendit")
                        }
                        OutlinedButton(onClick = { onRollStreetPrompt("bodega") }, modifier = Modifier.weight(1f)) {
                            Text("Bodega")
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    OutlinedButton(
                        onClick = onRollDatepath,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("roll_datepath_button")
                    ) {
                        Text("The Datepath (Gerar Encontro Digital • Vol. 3)")
                    }
                    if (!lastStreetPrompt.isNullOrBlank()) {
                        Spacer(Modifier.height(6.dp))
                        Text(lastStreetPrompt, style = MaterialTheme.typography.bodySmall, color = CyberAmber)
                    }
                    lastDatepathResult?.let { d ->
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "Datepath [${d.keyword1Pt} / ${d.keyword2Pt}] em ${d.locationNamePt}: ${d.activityPt} — ${d.outcomeLabelPt}. ${d.twistPt}",
                            style = MaterialTheme.typography.bodySmall,
                            color = CyberCyan
                        )
                    }
                }

                CyberPanel(accentColor = CyberAmber) {
                    Text("HOOK BOOK (130 GANCHOS) & VENDEDOR RC", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        item {
                            FilterChip(
                                selected = selectedHookDistrict == null,
                                onClick = { selectedHookDistrict = null },
                                label = { Text("Todos (130)") }
                            )
                        }
                        items(OfficialHookBookData.districtsPt) { dist ->
                            FilterChip(
                                selected = selectedHookDistrict == dist,
                                onClick = { selectedHookDistrict = dist },
                                label = { Text(dist) }
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { onRollPlotHook(selectedHookDistrict) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("roll_plot_hook_button")
                        ) {
                            Text("Rolar Gancho")
                        }
                        OutlinedButton(
                            onClick = onRollRcVendor,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("roll_rc_vendor_button")
                        ) {
                            Text("Vendedor RC 1d6")
                        }
                    }
                    lastPlotHook?.let { hook ->
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "[${hook.districtPt}] ${hook.titlePt}: ${hook.summaryPt}",
                            style = MaterialTheme.typography.bodySmall,
                            color = CyberCyan
                        )
                    }
                    if (!lastRcVendorPrompt.isNullOrBlank()) {
                        Spacer(Modifier.height(4.dp))
                        Text(lastRcVendorPrompt, style = MaterialTheme.typography.bodySmall, color = CyberAmber)
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Fechar") } }
    )
}

@Composable
fun AwardIpDialog(
    currentIp: Int,
    onAwardIp: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedAmount by remember { mutableStateOf("30") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Conceder Pontos de Melhoria (I.P.)") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Saldo atual: $currentIp I.P. • Custos oficiais (p. 411): Perícia = N×20 (Difícil ×2: N×40) | Função = R×60.",
                    style = MaterialTheme.typography.bodySmall,
                    color = CyberAmber
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(OfficialEconomyAndProgression.playstyleIpTable) { tier ->
                        FilterChip(
                            selected = selectedAmount == tier.ipAwarded.toString(),
                            onClick = { selectedAmount = tier.ipAwarded.toString() },
                            label = { Text("+${tier.ipAwarded} I.P.") }
                        )
                    }
                }
                val activeTier = OfficialEconomyAndProgression.playstyleIpTable.firstOrNull {
                    it.ipAwarded == selectedAmount.toIntOrNull()
                }
                if (activeTier != null) {
                    CyberPanel(containerColor = CyberSurfaceElevated) {
                        Text(
                            text = "Critério (${activeTier.ipAwarded} I.P.): ${activeTier.groupPt} • ${activeTier.warriorPt} • ${activeTier.explorerPt}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                OutlinedTextField(
                    value = selectedAmount,
                    onValueChange = { selectedAmount = it.filter { c -> c.isDigit() } },
                    label = { Text("Quantidade de I.P.") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amt = selectedAmount.toIntOrNull() ?: 0
                    if (amt > 0) onAwardIp(amt)
                    onDismiss()
                },
                enabled = (selectedAmount.toIntOrNull() ?: 0) > 0,
                colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald)
            ) {
                Text("Creditar I.P.", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
