package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Terminal
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
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.core.rules.NetArchDifficulty
import com.example.core.rules.NightMarketOffer
import com.example.core.rules.OfficialDlcNpcsAndNets
import com.example.core.rules.OfficialGearCatalog
import com.example.core.rules.OfficialNetArchitectures
import com.example.core.rules.PremadeNetArchSpec
import com.example.core.rules.PriceCategory
import com.example.core.rules.RedRole
import com.example.data.local.entity.InventoryItemEntity
import com.example.data.local.entity.InventoryItemType
import com.example.data.local.entity.MissionEntity
import com.example.data.local.entity.MissionStatus
import com.example.data.local.entity.NetNodeEntity
import com.example.data.local.entity.NetNodeType
import com.example.data.local.entity.NpcFactionEntity
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.EmptyCyberState
import com.example.ui.components.HustleAndLifestyleDialog
import com.example.ui.components.NightMarketGeneratorDialog
import com.example.ui.components.SectionHeader
import com.example.ui.components.TelemetryGaugeBar
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemsTerminalScreen(
    uiState: MainUiState,
    onBuyOrAddItem: (String, InventoryItemType, PriceCategory, Int, Int, Int, Int, Boolean, String) -> Unit,
    onToggleEquipItem: (InventoryItemEntity) -> Unit,
    onAdjustItemQuantity: (InventoryItemEntity, Int) -> Unit,
    onAddNetNode: (String, Int, NetNodeType, String, Int) -> Unit,
    onGenerateNetArchitecture: (String, NetArchDifficulty) -> Unit,
    onDeployPremadeNetArchitecture: (PremadeNetArchSpec) -> Unit = {},
    onRunInterfaceCheck: (NetNodeEntity) -> Unit,
    onDamageIce: (NetNodeEntity, Int) -> Unit,
    onToggleNetNodeCleared: (NetNodeEntity) -> Unit,
    onDeleteNetNode: (Long) -> Unit,
    onAddNpcFaction: (String, Boolean, String, String, String) -> Unit,
    onDeleteNpcFaction: (Long) -> Unit,
    onAddMission: (String, String, Int, String) -> Unit,
    onSetMissionStatus: (MissionEntity, MissionStatus) -> Unit,
    onDeleteMission: (Long) -> Unit,
    onRunHustle: (Boolean) -> Unit = {},
    onPayLifestyleOrHousing: (String, Int, String?, Boolean) -> Unit = { _, _, _, _ -> },
    onGenerateNightMarket: () -> Unit = {},
    onBuyNightMarketOffer: (NightMarketOffer) -> Unit = {},
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
                icon = Icons.Outlined.Terminal,
                title = "Terminal sem campanha",
                description = "Crie ou selecione uma campanha para gerenciar Arsenal, Netrunning, Contatos e Contratos."
            )
        }
        return
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Arsenal", "Netrunning", "NPCs", "Missões")

    Column(modifier = modifier.fillMaxSize()) {
        SecondaryTabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, maxLines = 1) },
                    modifier = Modifier.testTag("systems_tab_$index")
                )
            }
        }

        when (selectedTab) {
            0 -> InventoryAndEconomyTab(
                uiState = uiState,
                eurodollars = activeCampaign.eurodollars,
                items = uiState.inventory,
                onBuyOrAddItem = onBuyOrAddItem,
                onToggleEquip = onToggleEquipItem,
                onAdjustQuantity = onAdjustItemQuantity,
                onRunHustle = onRunHustle,
                onPayLifestyleOrHousing = onPayLifestyleOrHousing,
                onGenerateNightMarket = onGenerateNightMarket,
                onBuyNightMarketOffer = onBuyNightMarketOffer
            )
            1 -> NetrunningTab(
                nodes = uiState.netNodes,
                onAddNode = onAddNetNode,
                onGenerateNetArchitecture = onGenerateNetArchitecture,
                onDeployPremadeNetArchitecture = onDeployPremadeNetArchitecture,
                onRunInterfaceCheck = onRunInterfaceCheck,
                onDamageIce = onDamageIce,
                onToggleCleared = onToggleNetNodeCleared,
                onDeleteNode = onDeleteNetNode
            )
            2 -> NpcsAndFactionsTab(
                entries = uiState.npcsAndFactions,
                onAdd = onAddNpcFaction,
                onDelete = onDeleteNpcFaction
            )
            else -> MissionsTab(
                missions = uiState.missions,
                onAddMission = onAddMission,
                onSetStatus = onSetMissionStatus,
                onDeleteMission = onDeleteMission
            )
        }
    }
}

@Composable
private fun InventoryAndEconomyTab(
    uiState: MainUiState,
    eurodollars: Int,
    items: List<InventoryItemEntity>,
    onBuyOrAddItem: (String, InventoryItemType, PriceCategory, Int, Int, Int, Int, Boolean, String) -> Unit,
    onToggleEquip: (InventoryItemEntity) -> Unit,
    onAdjustQuantity: (InventoryItemEntity, Int) -> Unit,
    onRunHustle: (Boolean) -> Unit,
    onPayLifestyleOrHousing: (String, Int, String?, Boolean) -> Unit,
    onGenerateNightMarket: () -> Unit,
    onBuyNightMarketOffer: (NightMarketOffer) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showNightMarketDialog by remember { mutableStateOf(false) }
    var showHustleDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(
                title = "Arsenal, Equipamentos & Mercado",
                subtitle = "Saldo atual: $eurodollars eb",
                accentColor = CyberAmber,
                trailing = {
                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberAmber),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("add_inventory_button")
                    ) {
                        Icon(
                            Icons.Filled.ShoppingBag,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Adquirir", color = MaterialTheme.colorScheme.onTertiary)
                    }
                }
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showHustleDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("open_hustle_button")
                ) {
                    Text("Hustle & Aluguel")
                }
                OutlinedButton(
                    onClick = { showNightMarketDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("open_night_market_button")
                ) {
                    Text("Mercado Noturno")
                }
            }
        }

        if (items.isEmpty()) {
            item {
                CyberPanel {
                    Text(
                        text = "Inventário vazio. Adquira armas, munições, armaduras, cyberdecks ou programas pelo preço oficial (eb).",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(items, key = { it.id }) { item ->
                CyberPanel(accentColor = if (item.isEquipped) CyberCyan else MaterialTheme.colorScheme.outline) {
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
                                Text("${item.quantity}x ${item.name}", style = MaterialTheme.typography.titleSmall)
                                CyberBadge(text = item.itemType.labelPt, color = CyberCyan)
                            }
                            Spacer(Modifier.height(4.dp))
                            val specs = buildString {
                                append("${item.priceCategory.labelPt} (${item.costEb} eb)")
                                if (item.damageDice > 0) append(" • Dano ${item.damageDice}d6")
                                if (item.stoppingPower > 0) append(" • PA ${item.stoppingPower}")
                            }
                            Text(
                                text = specs,
                                style = MaterialTheme.typography.bodySmall,
                                color = CyberAmber
                            )
                            if (item.notes.isNotBlank()) {
                                Text(
                                    text = item.notes,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(
                                onClick = { onAdjustQuantity(item, -1) },
                                contentPadding = PaddingValues(horizontal = 6.dp)
                            ) { Text("-") }
                            TextButton(
                                onClick = { onAdjustQuantity(item, 1) },
                                contentPadding = PaddingValues(horizontal = 6.dp)
                            ) { Text("+") }
                            Switch(
                                checked = item.isEquipped,
                                onCheckedChange = { onToggleEquip(item) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddInventoryDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, type, cat, cost, qty, dmg, sp, pay, notes ->
                onBuyOrAddItem(name, type, cat, cost, qty, dmg, sp, pay, notes)
                showAddDialog = false
            }
        )
    }

    if (showNightMarketDialog) {
        NightMarketGeneratorDialog(
            market = uiState.lastNightMarket,
            eurodollars = eurodollars,
            onGenerate = onGenerateNightMarket,
            onBuyOffer = onBuyNightMarketOffer,
            onDismiss = { showNightMarketDialog = false }
        )
    }

    if (showHustleDialog) {
        val campaign = uiState.activeCampaign
        val primary = uiState.primaryCharacter
        HustleAndLifestyleDialog(
            role = primary?.role ?: RedRole.SOLO,
            roleRank = primary?.roleRank ?: 4,
            currentLifestyle = campaign?.lifestyleLabel ?: "Kibble",
            rentDueInDays = campaign?.rentDueInDays ?: 30,
            eurodollars = eurodollars,
            lastHustle = uiState.lastHustleResult,
            onRunHustle = onRunHustle,
            onPayLifestyleOrHousing = onPayLifestyleOrHousing,
            onDismiss = { showHustleDialog = false }
        )
    }
}

@Composable
private fun NetrunningTab(
    nodes: List<NetNodeEntity>,
    onAddNode: (String, Int, NetNodeType, String, Int) -> Unit,
    onGenerateNetArchitecture: (String, NetArchDifficulty) -> Unit,
    onDeployPremadeNetArchitecture: (PremadeNetArchSpec) -> Unit,
    onRunInterfaceCheck: (NetNodeEntity) -> Unit,
    onDamageIce: (NetNodeEntity, Int) -> Unit,
    onToggleCleared: (NetNodeEntity) -> Unit,
    onDeleteNode: (Long) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showGenerateDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(
                title = "Arquitetura NET, ICE & REZ",
                subtitle = "Testes de Interface + 1d10 e dano em REZ",
                accentColor = CyberCyan,
                trailing = {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(
                            onClick = { showGenerateDialog = true },
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("generate_net_arch_button")
                        ) {
                            Icon(
                                Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Gerar NET")
                        }
                        Button(
                            onClick = { showAddDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("add_net_node_button")
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Nó / ICE", color = MaterialTheme.colorScheme.onSecondary)
                        }
                    }
                }
            )
        }

        if (nodes.isEmpty()) {
            item {
                CyberPanel {
                    Text(
                        text = "Nenhuma arquitetura NET ativa. Adicione andares com Senhas, Arquivos, Nós de Controle ou Black ICE com REZ.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(nodes, key = { it.id }) { node ->
                val isIce = node.nodeType == NetNodeType.BLACK_ICE || node.nodeType == NetNodeType.DEMON
                CyberPanel(accentColor = if (node.isCleared) CyberEmerald else CyberCyan) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${node.architectureName} • Andar ${node.floorNumber}",
                                style = MaterialTheme.typography.labelMedium,
                                color = CyberCyan
                            )
                            Text(
                                text = node.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CyberBadge(
                                text = if (node.isCleared) "SUPERADO" else node.nodeType.labelPt,
                                color = if (node.isCleared) CyberEmerald else RedlineCrimson
                            )
                            IconButton(onClick = { onDeleteNode(node.id) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Remover nó")
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    if (isIce && node.maxRez > 0) {
                        TelemetryGaugeBar(
                            label = "REZ DO PROGRAMA",
                            current = node.currentRez,
                            max = node.maxRez,
                            accentColor = RedlineCrimson
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { onDamageIce(node, 2) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.FlashOn, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Atacar 2d6")
                            }
                            OutlinedButton(
                                onClick = { onDamageIce(node, 3) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Atacar 3d6")
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Dificuldade: DV ${node.difficultyValue}",
                                style = MaterialTheme.typography.labelLarge,
                                color = CyberAmber
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(onClick = { onRunInterfaceCheck(node) }) {
                                    Text("Interface 1d10")
                                }
                                TextButton(onClick = { onToggleCleared(node) }) {
                                    Text(if (node.isCleared) "Reabrir" else "Marcar OK")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddNetNodeDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { arch, floor, type, title, dvRez ->
                onAddNode(arch, floor, type, title, dvRez)
                showAddDialog = false
            }
        )
    }

    if (showGenerateDialog) {
        GenerateNetArchDialog(
            onDismiss = { showGenerateDialog = false },
            onConfirm = { archName, diff ->
                onGenerateNetArchitecture(archName, diff)
                showGenerateDialog = false
            },
            onDeployPremade = { premade ->
                onDeployPremadeNetArchitecture(premade)
                showGenerateDialog = false
            }
        )
    }
}

@Composable
private fun NpcsAndFactionsTab(
    entries: List<NpcFactionEntity>,
    onAdd: (String, Boolean, String, String, String) -> Unit,
    onDelete: (Long) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(
                title = "NPCs, Contatos & Facções",
                subtitle = "Rede de contatos e reputação em Night City",
                accentColor = CyberCyan,
                trailing = {
                    TextButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Adicionar")
                    }
                }
            )
        }

        if (entries.isEmpty()) {
            item {
                CyberPanel {
                    Text(
                        text = "Nenhum contato ou facção registrado ainda.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(entries, key = { it.id }) { npc ->
                CyberPanel {
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
                                Text(npc.name, style = MaterialTheme.typography.titleSmall)
                                CyberBadge(
                                    text = if (npc.isFaction) "Facção" else npc.attitude,
                                    color = if (npc.isFaction) CyberAmber else CyberCyan
                                )
                            }
                            Text(
                                text = "Afiliação: ${npc.affiliation}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (npc.notes.isNotBlank()) {
                                Text(npc.notes, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        IconButton(onClick = { onDelete(npc.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remover")
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddNpcFactionDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, isFaction, aff, att, notes ->
                onAdd(name, isFaction, aff, att, notes)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun MissionsTab(
    missions: List<MissionEntity>,
    onAddMission: (String, String, Int, String) -> Unit,
    onSetStatus: (MissionEntity, MissionStatus) -> Unit,
    onDeleteMission: (Long) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(
                title = "Missões & Contratos (Gigs)",
                subtitle = "Concluir missão credita recompensa (eb) automaticamente",
                accentColor = CyberEmerald,
                trailing = {
                    TextButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Novo Gig")
                    }
                }
            )
        }

        if (missions.isEmpty()) {
            item {
                CyberPanel {
                    Text(
                        text = "Nenhum contrato registrado. Cadastre missões com Fixer, recompensa e objetivos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(missions, key = { it.id }) { mission ->
                val color = when (mission.status) {
                    MissionStatus.ACTIVE -> CyberAmber
                    MissionStatus.COMPLETED -> CyberEmerald
                    MissionStatus.FAILED -> RedlineCrimson
                }
                CyberPanel(accentColor = color) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(mission.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(
                                text = "Fixer: ${mission.fixerName.ifBlank { "Anônimo" }} • Pagamento: ${mission.rewardEb} eb",
                                style = MaterialTheme.typography.bodySmall,
                                color = CyberAmber
                            )
                        }
                        CyberBadge(text = mission.status.labelPt, color = color)
                    }
                    if (mission.objectivesSummary.isNotBlank()) {
                        Spacer(Modifier.height(6.dp))
                        Text(mission.objectivesSummary, style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (mission.status == MissionStatus.ACTIVE) {
                            TextButton(onClick = { onSetStatus(mission, MissionStatus.COMPLETED) }) {
                                Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Concluir (+eb)")
                            }
                            TextButton(onClick = { onSetStatus(mission, MissionStatus.FAILED) }) {
                                Text("Falhar")
                            }
                        }
                        IconButton(onClick = { onDeleteMission(mission.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Excluir missão")
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddMissionDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, fixer, reward, obj ->
                onAddMission(title, fixer, reward, obj)
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddInventoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, InventoryItemType, PriceCategory, Int, Int, Int, Int, Boolean, String) -> Unit
) {
    var catalogSearch by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(InventoryItemType.WEAPON) }
    var category by remember { mutableStateOf(PriceCategory.PREMIUM) }
    var costText by remember { mutableStateOf("100") }
    var qtyText by remember { mutableStateOf("1") }
    var dmgText by remember { mutableStateOf("3") }
    var spText by remember { mutableStateOf("0") }
    var payWithEb by remember { mutableStateOf(true) }
    var notes by remember { mutableStateOf("") }

    val officialMatches = remember(catalogSearch, type) {
        val q = catalogSearch.trim()
        val base = if (q.isEmpty()) {
            OfficialGearCatalog.quickPurchaseCatalog.filter { it.itemType == type }
        } else {
            OfficialGearCatalog.quickPurchaseCatalog.filter {
                it.namePt.contains(q, ignoreCase = true) ||
                    it.nameEn.contains(q, ignoreCase = true) ||
                    it.subCategoryPt.contains(q, ignoreCase = true)
            }
        }
        base.take(30)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adquirir Equipamento") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = catalogSearch,
                    onValueChange = { catalogSearch = it },
                    label = { Text("Buscar Catálogo Oficial (Armas/PA/Gear)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (officialMatches.isNotEmpty()) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(officialMatches, key = { it.nameEn }) { preset ->
                            FilterChip(
                                selected = name == preset.namePt,
                                onClick = {
                                    name = preset.namePt
                                    type = preset.itemType
                                    category = preset.priceCategory
                                    costText = preset.costEb.toString()
                                    dmgText = preset.damageDice.toString()
                                    spText = preset.stoppingPower.toString()
                                    notes = preset.notesPt
                                },
                                label = { Text("${preset.namePt} (${preset.costEb}eb)") }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Item") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    InventoryItemType.entries.forEach { t ->
                        FilterChip(
                            selected = type == t,
                            onClick = { type = t },
                            label = { Text(t.labelPt) }
                        )
                    }
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    PriceCategory.entries.forEach { c ->
                        FilterChip(
                            selected = category == c,
                            onClick = {
                                category = c
                                costText = c.referenceCostEb.toString()
                            },
                            label = { Text("${c.labelPt} (${c.referenceCostEb})") }
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = costText,
                        onValueChange = { costText = it.filter { c -> c.isDigit() } },
                        label = { Text("Custo eb") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = qtyText,
                        onValueChange = { qtyText = it.filter { c -> c.isDigit() } },
                        label = { Text("Qtd") },
                        singleLine = true,
                        modifier = Modifier.weight(0.7f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = dmgText,
                        onValueChange = { dmgText = it.filter { c -> c.isDigit() } },
                        label = { Text("Dados d6") },
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = payWithEb, onCheckedChange = { payWithEb = it })
                    Text("Descontar Eurodólares (eb) do saldo")
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas / Tipo de Munição") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        name,
                        type,
                        category,
                        costText.toIntOrNull() ?: 0,
                        qtyText.toIntOrNull() ?: 1,
                        dmgText.toIntOrNull() ?: 0,
                        spText.toIntOrNull() ?: 0,
                        payWithEb,
                        notes
                    )
                },
                enabled = name.isNotBlank()
            ) { Text("Confirmar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddNetNodeDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int, NetNodeType, String, Int) -> Unit
) {
    var arch by remember { mutableStateOf("Torre Corporativa") }
    var floorText by remember { mutableStateOf("1") }
    var type by remember { mutableStateOf(NetNodeType.BLACK_ICE) }
    var title by remember { mutableStateOf("") }
    var dvOrRezText by remember { mutableStateOf("20") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Nó / ICE") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = arch,
                    onValueChange = { arch = it },
                    label = { Text("Arquitetura NET") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NetNodeType.entries.forEach { t ->
                        FilterChip(
                            selected = type == t,
                            onClick = {
                                type = t
                                if (t != NetNodeType.BLACK_ICE && t != NetNodeType.DEMON) {
                                    dvOrRezText = "8"
                                }
                            },
                            label = { Text(t.labelPt) }
                        )
                    }
                }
                if (type == NetNodeType.BLACK_ICE) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(OfficialNetArchitectures.blackIce, key = { it.name }) { ice ->
                            FilterChip(
                                selected = title == ice.name,
                                onClick = {
                                    title = "${ice.name} (${ice.iceClassPt})"
                                    dvOrRezText = ice.rez.toString()
                                },
                                label = { Text("${ice.name} (${ice.rez} REZ)") }
                            )
                        }
                    }
                } else if (type == NetNodeType.DEMON) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(OfficialNetArchitectures.demons, key = { it.name }) { demon ->
                            FilterChip(
                                selected = title.startsWith(demon.name),
                                onClick = {
                                    title = "${demon.name} (Int ${demon.interfaceRank}, ${demon.netActions} ações)"
                                    dvOrRezText = demon.rez.toString()
                                },
                                label = { Text("${demon.name} (${demon.rez} REZ)") }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Nome do Nó ou ICE") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = floorText,
                        onValueChange = { floorText = it.filter { c -> c.isDigit() } },
                        label = { Text("Andar") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = dvOrRezText,
                        onValueChange = { dvOrRezText = it.filter { c -> c.isDigit() } },
                        label = {
                            Text(
                                if (type == NetNodeType.BLACK_ICE || type == NetNodeType.DEMON) "REZ" else "DV"
                            )
                        },
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
                        arch,
                        floorText.toIntOrNull() ?: 1,
                        type,
                        title,
                        dvOrRezText.toIntOrNull() ?: 6
                    )
                },
                enabled = arch.isNotBlank() && title.isNotBlank()
            ) { Text("Adicionar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
private fun GenerateNetArchDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, NetArchDifficulty) -> Unit,
    onDeployPremade: (PremadeNetArchSpec) -> Unit
) {
    var archName by remember { mutableStateOf("Servidor Corporativo") }
    var difficulty by remember { mutableStateOf(NetArchDifficulty.STANDARD) }
    var selectedPremade by remember { mutableStateOf(OfficialDlcNpcsAndNets.premadeNetArchitectures.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Gerador Oficial de Arquitetura NET") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Rola 3d6 andares usando as tabelas oficiais de Lobby (1d6) e Corpo (3d6) por dificuldade (p. 209–211).",
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedTextField(
                    value = archName,
                    onValueChange = { archName = it },
                    label = { Text("Nome da Arquitetura") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    NetArchDifficulty.entries.forEach { d ->
                        FilterChip(
                            selected = difficulty == d,
                            onClick = { difficulty = d },
                            label = { Text("${d.labelPt} • Interface sug. ${d.suggestedInterface}") }
                        )
                    }
                }
                Text(
                    text = "ARQUITETURAS PRONTAS (INTERFACE RED VOL. 1–2)",
                    style = MaterialTheme.typography.labelLarge,
                    color = CyberCyan
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(OfficialDlcNpcsAndNets.premadeNetArchitectures, key = { it.namePt }) { p ->
                        FilterChip(
                            selected = selectedPremade.namePt == p.namePt,
                            onClick = { selectedPremade = p },
                            label = { Text("${p.namePt} (${p.floors.size})") }
                        )
                    }
                }
                Text(
                    text = "${selectedPremade.sourcePt} • ${selectedPremade.totalCostEb}eb — ${selectedPremade.summaryPt}",
                    style = MaterialTheme.typography.bodySmall,
                    color = CyberAmber
                )
                OutlinedButton(
                    onClick = { onDeployPremade(selectedPremade) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("deploy_premade_net_button")
                ) {
                    Text("Implantar Pronta: ${selectedPremade.namePt}")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(archName, difficulty) },
                colors = ButtonDefaults.buttonColors(containerColor = CyberCyan)
            ) { Text("Gerar Aleatória") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
private fun AddNpcFactionDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Boolean, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var isFaction by remember { mutableStateOf(false) }
    var affiliation by remember { mutableStateOf("") }
    var attitude by remember { mutableStateOf("Neutro") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Novo Contato / Facção") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = !isFaction, onClick = { isFaction = false }, label = { Text("NPC / Contato") })
                    FilterChip(selected = isFaction, onClick = { isFaction = true }, label = { Text("Facção") })
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = affiliation,
                    onValueChange = { affiliation = it },
                    label = { Text("Afiliação / Corporação / Gangue") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = attitude,
                    onValueChange = { attitude = it },
                    label = { Text("Atitude (Aliado, Neutro, Hostil)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, isFaction, affiliation, attitude, notes) }, enabled = name.isNotBlank()) {
                Text("Salvar")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
private fun AddMissionDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var fixer by remember { mutableStateOf("") }
    var reward by remember { mutableStateOf("1000") }
    var objectives by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar Missão (Gig)") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título do Contrato") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fixer,
                    onValueChange = { fixer = it },
                    label = { Text("Fixer / Contratante") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = reward,
                    onValueChange = { reward = it.filter { c -> c.isDigit() } },
                    label = { Text("Recompensa (eb)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = objectives,
                    onValueChange = { objectives = it },
                    label = { Text("Objetivos") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, fixer, reward.toIntOrNull() ?: 0, objectives) },
                enabled = title.isNotBlank()
            ) { Text("Criar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
