package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.core.rules.CharacterCalculator
import com.example.core.rules.OfficialNightCityAtlasCatalog
import com.example.core.rules.OfficialStreetratPregensCatalog
import com.example.core.rules.RedRole
import com.example.core.rules.StreetratPregenSpec
import com.example.data.local.entity.CampaignEntity
import com.example.data.local.entity.CampaignSaveEntity
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.EmptyCyberState
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.CyberVoid
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@Composable
fun CampaignHubScreen(
    uiState: MainUiState,
    onCreateCampaign: (title: String, setting: String, handle: String, role: RedRole, eb: Int) -> Unit,
    onSelectCampaign: (Long) -> Unit,
    onDeleteCampaign: (Long) -> Unit,
    onAdvanceTime: (Int) -> Unit,
    onCreateSave: (String) -> Unit,
    onRestoreSave: (CampaignSaveEntity) -> Unit,
    onDeleteSave: (Long) -> Unit,
    onCreateCampaignFromPregen: (title: String, setting: String, pregen: StreetratPregenSpec) -> Unit = { _, _, _ -> },
    onOpenAtlas: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CampaignHeroBanner(
                activeCampaign = uiState.activeCampaign,
                onNewCampaignClick = { showCreateDialog = true }
            )
        }

        val active = uiState.activeCampaign
        if (active == null) {
            item {
                EmptyCyberState(
                    icon = Icons.Outlined.Campaign,
                    title = "Nenhuma campanha ativa",
                    description = "Crie sua primeira campanha solo e gere seu Edgerunner com base determinística em Room.",
                    action = {
                        Button(
                            onClick = { showCreateDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                            modifier = Modifier.testTag("empty_create_campaign_button")
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text("INICIAR CAMPANHA")
                        }
                    }
                )
            }
        } else {
            item {
                val activeDistrict = remember(active.currentDistrictCode) {
                    OfficialNightCityAtlasCatalog.findDistrictByCode(active.currentDistrictCode)
                }
                SectionHeader(
                    title = "Relógio & Economia de Night City",
                    subtitle = active.settingNotes,
                    accentColor = CyberCyan
                )
                Spacer(Modifier.height(8.dp))
                CyberPanel(accentColor = CyberCyan) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DIA ${active.inGameDay} • ${active.inGameHour.toString().padStart(2, '0')}:00",
                                style = MaterialTheme.typography.headlineSmall,
                                color = CyberCyan
                            )
                            Text(
                                text = "Estilo: ${active.lifestyleLabel} • Aluguel em ${active.rentDueInDays}d",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (activeDistrict != null) {
                                Text(
                                    text = "Distrito: ${activeDistrict.code} • ${activeDistrict.namePt} (${activeDistrict.zone.shortLabelPt})",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = CyberEmerald
                                )
                            }
                        }
                        CyberBadge(
                            text = "${active.eurodollars} eb",
                            color = CyberAmber
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(1 to "+1h", 4 to "+4h", 8 to "+8h").forEach { (hrs, label) ->
                            OutlinedButton(
                                onClick = { onAdvanceTime(hrs) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("advance_time_${hrs}h")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Schedule,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(label)
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onOpenAtlas,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("open_atlas_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Explore,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("Mapa Atlas de Night City (24 Distritos)")
                    }
                }
            }

            item {
                SectionHeader(
                    title = "Snapshots & Salvamentos",
                    subtitle = "Preservação determinística no SQLite local",
                    accentColor = CyberEmerald,
                    trailing = {
                        TextButton(
                            onClick = { showSaveDialog = true },
                            modifier = Modifier.testTag("create_snapshot_button")
                        ) {
                            Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Salvar")
                        }
                    }
                )
                Spacer(Modifier.height(6.dp))
                if (uiState.saves.isEmpty()) {
                    CyberPanel(containerColor = CyberSurfaceElevated) {
                        Text(
                            text = "Nenhum snapshot salvo ainda. Crie checkpoints manuais antes de invasões ou combates letais.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        uiState.saves.forEach { save ->
                            CyberPanel(accentColor = CyberEmerald.copy(alpha = 0.6f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = save.label,
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                        Text(
                                            text = "Checkpoint local #${save.id}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Row {
                                        IconButton(onClick = { onRestoreSave(save) }) {
                                            Icon(
                                                imageVector = Icons.Filled.Restore,
                                                contentDescription = "Restaurar snapshot",
                                                tint = CyberEmerald
                                            )
                                        }
                                        IconButton(onClick = { onDeleteSave(save.id) }) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Excluir snapshot",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (uiState.campaigns.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Campanhas Salvas (${uiState.campaigns.size})",
                    subtitle = "Toque para alternar a campanha ativa",
                    accentColor = RedlineCrimson
                )
            }
            items(uiState.campaigns, key = { it.id }) { campaign ->
                CyberPanel(
                    modifier = Modifier.clickable { onSelectCampaign(campaign.id) },
                    accentColor = if (campaign.isActive) RedlineCrimson else MaterialTheme.colorScheme.outline
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = campaign.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (campaign.isActive) {
                                    CyberBadge(text = "ATIVA", color = RedlineCrimson)
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Dia ${campaign.inGameDay} • ${campaign.eurodollars} eb",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = { onDeleteCampaign(campaign.id) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Remover campanha",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        item {
            ArchitectureReadinessCard(uiState = uiState)
        }
    }

    if (showCreateDialog) {
        CreateCampaignDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { title, setting, handle, role, eb ->
                onCreateCampaign(title, setting, handle, role, eb)
                showCreateDialog = false
            },
            onConfirmPregen = { title, setting, pregen ->
                onCreateCampaignFromPregen(title, setting, pregen)
                showCreateDialog = false
            }
        )
    }

    if (showSaveDialog) {
        CreateSaveDialog(
            defaultLabel = "Dia ${uiState.activeCampaign?.inGameDay ?: 1} • ${uiState.primaryCharacter?.handle ?: "Check"}",
            onDismiss = { showSaveDialog = false },
            onConfirm = { label ->
                onCreateSave(label)
                showSaveDialog = false
            }
        )
    }
}

@Composable
private fun CampaignHeroBanner(
    activeCampaign: CampaignEntity?,
    onNewCampaignClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp)
            .clip(CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_cyber_city_banner),
            contentDescription = stringResource(id = R.string.cd_cyber_banner),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            CyberVoid.copy(alpha = 0.25f),
                            CyberVoid.copy(alpha = 0.92f)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CyberBadge(text = "CYBERPUNK RED • SOLO", color = RedlineCrimson)
                Button(
                    onClick = onNewCampaignClick,
                    colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("new_campaign_button")
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Nova", style = MaterialTheme.typography.labelLarge)
                }
            }
            Column {
                Text(
                    text = activeCampaign?.title ?: "Central de Campanhas Solo",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = if (activeCampaign != null) {
                        "Motor determinístico ativo • Dados locais preservados"
                    } else {
                        "Arquitetura modular pronta para campanhas Cyberpunk RED"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ArchitectureReadinessCard(uiState: MainUiState) {
    CyberPanel(accentColor = MaterialTheme.colorScheme.outline) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.History,
                contentDescription = null,
                tint = CyberCyan,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "STATUS DA ARQUITETURA MODULAR",
                style = MaterialTheme.typography.labelLarge,
                color = CyberCyan
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "• Regras determinísticas: Kotlin puro (1d10 crítico/falha, PV, PA, Humanidade, REZ)\n" +
                "• Persistência Room: 13 tabelas relacionais ativas offline\n" +
                "• Mestre de Jogo IA: ${uiState.aiProviderMode.labelPt}\n" +
                "• Biblioteca PDF: ${uiState.ruleDocuments.size} documento(s) oficial(is) vinculado(s)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CreateCampaignDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, RedRole, Int) -> Unit,
    onConfirmPregen: (String, String, StreetratPregenSpec) -> Unit
) {
    var usePregen by remember { mutableStateOf(false) }
    var selectedPregen by remember { mutableStateOf(OfficialStreetratPregensCatalog.allPregens.first()) }
    var title by remember { mutableStateOf("") }
    var setting by remember { mutableStateOf("Night City — Era do Vermelho (2045)") }
    var handle by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(RedRole.SOLO) }
    var startingEb by remember { mutableStateOf("2550") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova Campanha Solo") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = !usePregen,
                        onClick = { usePregen = false },
                        label = { Text("Personalizado") },
                        modifier = Modifier.testTag("campaign_mode_custom")
                    )
                    FilterChip(
                        selected = usePregen,
                        onClick = { usePregen = true },
                        label = { Text("Single Shot (10)") },
                        modifier = Modifier.testTag("campaign_mode_pregen")
                    )
                }
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Nome da Campanha") },
                    placeholder = { Text("Ex: Sombras de Watson") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("campaign_title_input")
                )
                OutlinedTextField(
                    value = setting,
                    onValueChange = { setting = it },
                    label = { Text("Cenário / Distrito") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!usePregen) {
                    OutlinedTextField(
                        value = handle,
                        onValueChange = { handle = it },
                        label = { Text("Codinome (Handle) do Edgerunner") },
                        placeholder = { Text("Ex: Vex-9") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edgerunner_handle_input")
                    )
                    OutlinedTextField(
                        value = startingEb,
                        onValueChange = { startingEb = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Eurodólares Iniciais (eb)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Função Oficial (Role):",
                        style = MaterialTheme.typography.labelMedium
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        RedRole.entries.forEach { role ->
                            FilterChip(
                                selected = selectedRole == role,
                                onClick = { selectedRole = role },
                                label = { Text(role.displayNamePt) }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Edgerunner Pronto (Single Shot Pack):",
                        style = MaterialTheme.typography.labelMedium,
                        color = CyberCyan
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(OfficialStreetratPregensCatalog.allPregens, key = { it.id }) { p ->
                            FilterChip(
                                selected = selectedPregen.id == p.id,
                                onClick = { selectedPregen = p },
                                label = { Text("${p.handle} (${p.role.displayNamePt})") }
                            )
                        }
                    }
                    val maxHp = CharacterCalculator.calculateMaxHp(selectedPregen.stats.body, selectedPregen.stats.willpower)
                    CyberPanel(containerColor = CyberSurfaceElevated) {
                        Text(
                            text = "${selectedPregen.handle} • ${selectedPregen.role.displayNamePt} (Rank ${selectedPregen.roleRank})",
                            style = MaterialTheme.typography.titleSmall,
                            color = CyberAmber
                        )
                        Text(
                            text = "PV $maxHp • HUM ${selectedPregen.currentHumanity}/${selectedPregen.maxHumanity} • PA ${selectedPregen.bodySp} • ${selectedPregen.startingCashEb}eb",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberCyan
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(selectedPregen.bioPt, style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "Inclui ${selectedPregen.skills.size} perícias, ${selectedPregen.cyberware.size} cyberwares e ${selectedPregen.inventory.size} itens oficiais.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (usePregen) {
                        onConfirmPregen(title, setting, selectedPregen)
                    } else {
                        onConfirm(
                            title,
                            setting,
                            handle,
                            selectedRole,
                            startingEb.toIntOrNull() ?: 2550
                        )
                    }
                },
                enabled = title.isNotBlank() && (usePregen || handle.isNotBlank()),
                colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                modifier = Modifier.testTag("confirm_create_campaign_button")
            ) {
                Text("Criar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
private fun CreateSaveDialog(
    defaultLabel: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var label by remember { mutableStateOf(defaultLabel) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Salvar Snapshot") },
        text = {
            OutlinedTextField(
                value = label,
                onValueChange = { label = it },
                label = { Text("Rótulo do salvamento") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(label) },
                colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald)
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
