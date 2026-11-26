package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.core.rules.EncounterTimeOfDay
import com.example.core.rules.OfficialNpcSpec
import com.example.core.rules.PlotHookSpec
import com.example.core.rules.RedStat
import com.example.core.rules.SalvageCategory
import com.example.core.rules.SalvageWorthTier
import com.example.core.rules.WeatherSeason
import com.example.data.local.entity.CombatParticipantEntity
import com.example.data.local.entity.JournalEntryType
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.EmptyCyberState
import com.example.ui.components.SectionHeader
import com.example.ui.components.SoloGeneratorsDialog
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@Composable
fun PlaySessionScreen(
    uiState: MainUiState,
    onSubmitAction: (String) -> Unit,
    onOpenDiceRoller: (RedStat, String, Int) -> Unit,
    onApplyDamage: (diceCount: Int, isHeadshot: Boolean, ignoreHalfArmor: Boolean) -> Unit,
    onAddCombatant: (name: String, isPlayer: Boolean, reflexes: Int, maxHp: Int, armorSp: Int) -> Unit,
    onNextTurn: () -> Unit,
    onAdjustCombatantHp: (CombatParticipantEntity, Int) -> Unit,
    onClearCombat: () -> Unit,
    onRollEncounter: (EncounterTimeOfDay) -> Unit = {},
    onSpawnNpc: (OfficialNpcSpec, Int) -> Unit = { _, _ -> },
    onRollStreetPrompt: (String) -> Unit = {},
    onRollWeather: (WeatherSeason) -> Unit = {},
    onRunSalvaging: (SalvageWorthTier, SalvageCategory?) -> Unit = { _, _ -> },
    onRollDatepath: () -> Unit = {},
    onRollPlotHook: (String?) -> Unit = {},
    onRollRcVendor: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val activeCampaign = uiState.activeCampaign
    if (activeCampaign == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            EmptyCyberState(
                icon = Icons.Outlined.Forum,
                title = "Sessão indisponível",
                description = "Selecione ou crie uma campanha na aba Campanha para iniciar o diário solo e o rastreador de combate."
            )
        }
        return
    }

    var actionInput by remember { mutableStateOf("") }
    var showDamageDialog by remember { mutableStateOf(false) }
    var showAddCombatantDialog by remember { mutableStateOf(false) }
    var showSoloGeneratorsDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        // Top GM & Deterministic Controls Strip
        CyberPanel(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            accentColor = CyberCyan
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.SmartToy,
                        contentDescription = null,
                        tint = CyberCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = uiState.aiProviderMode.labelPt,
                        style = MaterialTheme.typography.labelMedium,
                        color = CyberCyan
                    )
                }
                CyberBadge(
                    text = "Dia ${activeCampaign.inGameDay} • ${activeCampaign.inGameHour.toString().padStart(2, '0')}:00",
                    color = CyberAmber
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onOpenDiceRoller(RedStat.REF, "Teste de Ação", 4) },
                    colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("session_roll_button")
                ) {
                    Icon(Icons.Filled.Casino, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Rolar 1d10")
                }
                OutlinedButton(
                    onClick = { showDamageDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("session_damage_button")
                ) {
                    Icon(Icons.Filled.LocalHospital, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Aplicar Dano")
                }
            }
            Spacer(Modifier.height(6.dp))
            OutlinedButton(
                onClick = { showSoloGeneratorsDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("open_solo_generators_button")
            ) {
                Text("Gerador Solo: Encontros d100 • Hostis • Rua")
            }
        }

        // Scrollable Combat Tracker + Chronological Journal
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                val round = uiState.combatParticipants.firstOrNull()?.roundNumber ?: 1
                SectionHeader(
                    title = if (uiState.combatParticipants.isEmpty()) "Combate & Iniciativa" else "Combate • Rodada $round",
                    subtitle = "Ordem automática: REF + 1d10",
                    accentColor = RedlineCrimson,
                    trailing = {
                        Row {
                            IconButton(
                                onClick = { showAddCombatantDialog = true },
                                modifier = Modifier.testTag("add_combatant_button")
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = "Adicionar combatente", tint = CyberCyan)
                            }
                            if (uiState.combatParticipants.isNotEmpty()) {
                                IconButton(onClick = onNextTurn) {
                                    Icon(Icons.Filled.PlayArrow, contentDescription = "Próximo turno", tint = CyberEmerald)
                                }
                                IconButton(onClick = onClearCombat) {
                                    Icon(Icons.Filled.ClearAll, contentDescription = "Limpar combate")
                                }
                            }
                        }
                    }
                )
            }

            if (uiState.combatParticipants.isNotEmpty()) {
                items(uiState.combatParticipants, key = { "combat_${it.id}" }) { p ->
                    CyberPanel(
                        accentColor = if (p.isActiveTurn) CyberEmerald else MaterialTheme.colorScheme.outline,
                        containerColor = if (p.isActiveTurn) CyberSurfaceElevated else MaterialTheme.colorScheme.surface
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = p.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (p.isActiveTurn) {
                                        CyberBadge(text = "TURNO", color = CyberEmerald)
                                    }
                                }
                                Text(
                                    text = "Inic: ${p.initiativeTotal} • PV ${p.currentHp}/${p.maxHp} • PA ${p.armorSp}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Row {
                                TextButton(
                                    onClick = { onAdjustCombatantHp(p, -5) },
                                    contentPadding = PaddingValues(horizontal = 6.dp)
                                ) { Text("-5") }
                                TextButton(
                                    onClick = { onAdjustCombatantHp(p, 5) },
                                    contentPadding = PaddingValues(horizontal = 6.dp)
                                ) { Text("+5") }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(4.dp))
                SectionHeader(
                    title = "Diário de Sessão & Auditoria",
                    subtitle = "Histórico imutável de ações, narração e rolagens",
                    accentColor = CyberCyan
                )
            }

            items(uiState.journal, key = { "journal_${it.id}" }) { entry ->
                val accent = when (entry.entryType) {
                    JournalEntryType.PLAYER_ACTION -> CyberCyan
                    JournalEntryType.NARRATOR_GM -> RedlineCrimson
                    JournalEntryType.DETERMINISTIC_ROLL -> CyberAmber
                    JournalEntryType.SYSTEM_EVENT -> CyberEmerald
                }
                CyberPanel(accentColor = accent.copy(alpha = 0.7f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CyberBadge(text = entry.speakerLabel, color = accent)
                        Text(
                            text = entry.inGameTimestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = entry.bodyText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (!entry.deterministicDetail.isNullOrBlank()) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = entry.deterministicDetail,
                            style = MaterialTheme.typography.labelMedium,
                            color = accent
                        )
                    }
                }
            }
        }

        // Player Action Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = actionInput,
                onValueChange = { actionInput = it },
                placeholder = { Text("Descreva sua ação ou diálogo em Night City...") },
                maxLines = 3,
                modifier = Modifier
                    .weight(1f)
                    .testTag("session_action_input")
            )
            Button(
                onClick = {
                    onSubmitAction(actionInput)
                    actionInput = ""
                },
                enabled = actionInput.isNotBlank() && !uiState.isProcessingAction,
                colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                modifier = Modifier
                    .height(52.dp)
                    .testTag("session_send_action_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar ação",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }

    if (showDamageDialog) {
        ApplyDamageDialog(
            onDismiss = { showDamageDialog = false },
            onConfirm = { dice, headshot, halfSp ->
                onApplyDamage(dice, headshot, halfSp)
                showDamageDialog = false
            }
        )
    }

    if (showAddCombatantDialog) {
        AddCombatantDialog(
            defaultPlayerName = uiState.primaryCharacter?.handle ?: "Edgerunner",
            defaultPlayerRef = uiState.primaryCharacter?.reflexes ?: 6,
            defaultPlayerHp = uiState.derivedMetrics?.maxHp ?: 40,
            defaultPlayerSp = uiState.primaryCharacter?.bodyArmorSp ?: 11,
            onDismiss = { showAddCombatantDialog = false },
            onConfirm = { name, isPlayer, ref, hp, sp ->
                onAddCombatant(name, isPlayer, ref, hp, sp)
                showAddCombatantDialog = false
            }
        )
    }

    if (showSoloGeneratorsDialog) {
        SoloGeneratorsDialog(
            lastEncounter = uiState.lastEncounter,
            lastStreetPrompt = uiState.lastStreetPrompt,
            lastWeatherResult = uiState.lastWeatherResult,
            lastSalvageResult = uiState.lastSalvageResult,
            lastDatepathResult = uiState.lastDatepathResult,
            lastPlotHook = uiState.lastPlotHook,
            lastRcVendorPrompt = uiState.lastRcVendorPrompt,
            onRollEncounter = onRollEncounter,
            onSpawnNpc = onSpawnNpc,
            onRollStreetPrompt = onRollStreetPrompt,
            onRollWeather = onRollWeather,
            onRunSalvaging = onRunSalvaging,
            onRollDatepath = onRollDatepath,
            onRollPlotHook = onRollPlotHook,
            onRollRcVendor = onRollRcVendor,
            onDismiss = { showSoloGeneratorsDialog = false }
        )
    }
}

@Composable
private fun ApplyDamageDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Boolean, Boolean) -> Unit
) {
    var diceCount by remember { mutableIntStateOf(3) }
    var isHeadshot by remember { mutableStateOf(false) }
    var ignoreHalfArmor by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Aplicar Dano Determinístico") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Rola Nd6 contra a Armadura (PA). Se penetrar, reduz PA em 1. 2+ seis causam Ferimento Crítico (+5 dano direto) mesmo sem penetrar PA.",
                    style = MaterialTheme.typography.bodySmall
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(2, 3, 4, 5, 6).forEach { d ->
                        FilterChip(
                            selected = diceCount == d,
                            onClick = { diceCount = d },
                            label = { Text("${d}d6") }
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isHeadshot, onCheckedChange = { isHeadshot = it })
                    Text("Tiro na Cabeça (dano restante ×2)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = ignoreHalfArmor, onCheckedChange = { ignoreHalfArmor = it })
                    Text("Arma Branca / Artes Marciais (ignora ½ PA)")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(diceCount, isHeadshot, ignoreHalfArmor) },
                colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson)
            ) { Text("Rolar Dano") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
private fun AddCombatantDialog(
    defaultPlayerName: String,
    defaultPlayerRef: Int,
    defaultPlayerHp: Int,
    defaultPlayerSp: Int,
    onDismiss: () -> Unit,
    onConfirm: (String, Boolean, Int, Int, Int) -> Unit
) {
    var isPlayer by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Boosterganger") }
    var refText by remember { mutableStateOf("6") }
    var hpText by remember { mutableStateOf("30") }
    var spText by remember { mutableStateOf("7") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Combatente") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = !isPlayer,
                        onClick = {
                            isPlayer = false
                            name = "Hostil"
                        },
                        label = { Text("NPC / Hostil") }
                    )
                    FilterChip(
                        selected = isPlayer,
                        onClick = {
                            isPlayer = true
                            name = defaultPlayerName
                            refText = defaultPlayerRef.toString()
                            hpText = defaultPlayerHp.toString()
                            spText = defaultPlayerSp.toString()
                        },
                        label = { Text("Meu Edgerunner") }
                    )
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = refText,
                        onValueChange = { refText = it.filter { c -> c.isDigit() } },
                        label = { Text("REF") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = hpText,
                        onValueChange = { hpText = it.filter { c -> c.isDigit() } },
                        label = { Text("PV") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = spText,
                        onValueChange = { spText = it.filter { c -> c.isDigit() } },
                        label = { Text("PA") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        name,
                        isPlayer,
                        refText.toIntOrNull() ?: 6,
                        hpText.toIntOrNull() ?: 30,
                        spText.toIntOrNull() ?: 7
                    )
                },
                enabled = name.isNotBlank()
            ) { Text("Rolar Inic.") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
