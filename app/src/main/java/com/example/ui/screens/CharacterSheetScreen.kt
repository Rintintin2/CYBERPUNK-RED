package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.core.rules.CyberwareCategory
import com.example.core.rules.OfficialCyberwareCatalog
import com.example.core.rules.OfficialEconomyAndProgression
import com.example.core.rules.OfficialNetArchitectures
import com.example.core.rules.OfficialSkillCatalog
import com.example.core.rules.RedStat
import com.example.core.rules.TherapyType
import com.example.core.rules.WoundState
import com.example.data.local.entity.CharacterSkillEntity
import com.example.data.local.entity.CyberwareEntity
import com.example.ui.components.AwardIpDialog
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.EmptyCyberState
import com.example.ui.components.SectionHeader
import com.example.ui.components.TelemetryGaugeBar
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterSheetScreen(
    uiState: MainUiState,
    onAdjustStat: (RedStat, Int) -> Unit,
    onAdjustHp: (Int) -> Unit,
    onAdjustArmorSp: (isHead: Boolean, delta: Int) -> Unit,
    onRestoreLuck: () -> Unit,
    onRollDeathSave: () -> Unit,
    onAddSkill: (name: String, stat: RedStat, level: Int, category: String) -> Unit,
    onUpdateSkillLevel: (CharacterSkillEntity, Int) -> Unit,
    onDeleteSkill: (Long) -> Unit,
    onInstallCyberware: (name: String, slotArea: String, humanityCost: Int, notes: String) -> Unit,
    onToggleCyberware: (CyberwareEntity) -> Unit,
    onDeleteCyberware: (CyberwareEntity) -> Unit,
    onOpenDiceRoller: (stat: RedStat, skillName: String, skillLevel: Int) -> Unit,
    onAwardIp: (Int) -> Unit = {},
    onUpgradeSkillWithIp: (CharacterSkillEntity) -> Unit = {},
    onUpgradeRoleRankWithIp: () -> Unit = {},
    onRunTherapy: (TherapyType, useClinicService: Boolean, payWithEb: Boolean) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier
) {
    val character = uiState.primaryCharacter
    val derived = uiState.derivedMetrics

    if (character == null || derived == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            EmptyCyberState(
                icon = Icons.Filled.PersonOutline,
                title = "Nenhum Edgerunner ativo",
                description = "Crie uma campanha na aba Campanha para inicializar a ficha completa de Cyberpunk RED."
            )
        }
        return
    }

    var showAddSkillDialog by remember { mutableStateOf(false) }
    var showAddCyberwareDialog by remember { mutableStateOf(false) }
    var showAwardIpDialog by remember { mutableStateOf(false) }
    var showTherapyDialog by remember { mutableStateOf(false) }

    val woundColor = when (derived.woundState) {
        WoundState.UNINJURED -> CyberEmerald
        WoundState.LIGHTLY_WOUNDED -> CyberCyan
        WoundState.SERIOUSLY_WOUNDED -> CyberAmber
        WoundState.MORTALLY_WOUNDED, WoundState.DEAD -> RedlineCrimson
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CyberPanel(accentColor = RedlineCrimson) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_edgerunner_avatar),
                        contentDescription = stringResource(id = R.string.cd_edgerunner_avatar),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp))
                            .border(1.dp, RedlineCrimson, CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp))
                    )
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = character.handle,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${character.role.displayNamePt} • ${character.role.roleAbilityPt} (Nv ${character.roleRank})",
                            style = MaterialTheme.typography.bodySmall,
                            color = CyberCyan
                        )
                        Spacer(Modifier.height(6.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            CyberBadge(text = derived.woundState.labelPt, color = woundColor)
                            if (derived.effectiveWoundPenalty != 0) {
                                CyberBadge(text = "Ferimento ${derived.effectiveWoundPenalty}", color = RedlineCrimson)
                            } else if (derived.painEditorActive && derived.woundState == WoundState.SERIOUSLY_WOUNDED) {
                                CyberBadge(text = "Editor de Dor Ativo", color = CyberCyan)
                            }
                            if (derived.armorPenalty != 0) {
                                val tier = if (derived.armorPenalty <= -4) "Pesada" else "Média"
                                CyberBadge(text = "PA $tier (${derived.armorPenalty} REF/DES/MOV)", color = CyberAmber)
                            }
                            if (derived.effectiveBody != character.body) {
                                CyberBadge(text = "COR ${derived.effectiveBody} (Base ${character.body})", color = CyberCyan)
                            }
                            if (character.pendingTherapyDiceDelta != 0) {
                                val sign = if (character.pendingTherapyDiceDelta > 0) "+" else ""
                                CyberBadge(text = "Brainwasher ${sign}${character.pendingTherapyDiceDelta}d6 Terapia", color = CyberAmber)
                            }
                            CyberBadge(text = "${character.improvementPoints} I.P.", color = CyberEmerald)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showAwardIpDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("award_ip_button")
                    ) {
                        Text("+ Ganhar I.P.")
                    }
                    if (character.roleRank < 10) {
                        val roleCost = OfficialEconomyAndProgression.roleAbilityIpCostForNextRank(character.roleRank + 1)
                        OutlinedButton(
                            onClick = onUpgradeRoleRankWithIp,
                            enabled = character.improvementPoints >= roleCost,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("upgrade_role_rank_button")
                        ) {
                            Text("Rank ${character.roleRank + 1} ($roleCost I.P.)")
                        }
                    }
                }
                OfficialNetArchitectures.roleAbilitySummaryPt[character.role]?.let { roleDesc ->
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = roleDesc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            SectionHeader(
                title = "Sinais Vitais & Blindagem",
                subtitle = "Fórmulas oficiais: PV = 10 + 5×⌈(COR+VON)/2⌉ • Ferim. Grave = ⌈PV/2⌉",
                accentColor = woundColor
            )
            Spacer(Modifier.height(8.dp))
            CyberPanel(accentColor = woundColor) {
                TelemetryGaugeBar(
                    label = "PONTOS DE VIDA (PV) • Limite Grave: ${derived.seriouslyWoundedThreshold}",
                    current = character.currentHp,
                    max = derived.maxHp,
                    accentColor = woundColor
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(-5 to "-5", -1 to "-1", 1 to "+1", 5 to "+5").forEach { (delta, lbl) ->
                        OutlinedButton(
                            onClick = { onAdjustHp(delta) },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 6.dp)
                        ) {
                            Text(lbl, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ArmorSpBox(
                        label = "PA CABEÇA",
                        sp = character.headArmorSp,
                        onMinus = { onAdjustArmorSp(true, -1) },
                        onPlus = { onAdjustArmorSp(true, 1) },
                        modifier = Modifier.weight(1f)
                    )
                    ArmorSpBox(
                        label = "PA CORPO",
                        sp = character.bodyArmorSp,
                        onMinus = { onAdjustArmorSp(false, -1) },
                        onPlus = { onAdjustArmorSp(false, 1) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(14.dp))
                TelemetryGaugeBar(
                    label = "HUMANIDADE • EMP Efetiva: ${derived.currentEmpathy}/${character.empathyMax}",
                    current = derived.currentHumanity,
                    max = derived.maxHumanity,
                    accentColor = CyberCyan
                )

                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "SORTE: ${character.luckCurrent}/${character.luckMax}",
                            style = MaterialTheme.typography.labelLarge,
                            color = CyberAmber
                        )
                        IconButton(onClick = onRestoreLuck) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Restaurar sorte",
                                tint = CyberAmber
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = onRollDeathSave,
                        modifier = Modifier.testTag("death_save_button")
                    ) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Morte (< ${derived.deathSaveBase})")
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = "Atributos (10 Stats)",
                subtitle = "Toque no nome do atributo para testar 1d10",
                accentColor = CyberCyan
            )
            Spacer(Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 2
            ) {
                val stats = character.toCharacterStats()
                RedStat.entries.forEach { stat ->
                    val displayValue = if (stat == RedStat.BODY) {
                        derived.effectiveBody
                    } else {
                        stats.getStat(
                            stat = stat,
                            currentEmpathy = derived.currentEmpathy,
                            currentLuck = character.luckCurrent
                        )
                    }
                    CyberPanel(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onOpenDiceRoller(stat, stat.labelPt, 0) },
                        containerColor = CyberSurfaceElevated
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = stat.code,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = CyberCyan
                                )
                                Text(
                                    text = if (stat == RedStat.BODY && derived.effectiveBody != character.body) {
                                        "${stat.labelPt} (Base ${character.body})"
                                    } else {
                                        stat.labelPt
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = displayValue.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { onAdjustStat(stat, -1) },
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) { Text("-") }
                            TextButton(
                                onClick = { onAdjustStat(stat, 1) },
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) { Text("+") }
                        }
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = "Perícias (${uiState.skills.size})",
                subtitle = "Base = Atributo + Perícia + 1d10 vs DV",
                accentColor = RedlineCrimson,
                trailing = {
                    TextButton(
                        onClick = { showAddSkillDialog = true },
                        modifier = Modifier.testTag("add_skill_button")
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Nova")
                    }
                }
            )
        }

        items(uiState.skills, key = { it.id }) { skill ->
            val baseStat = character.toCharacterStats().getStat(
                stat = skill.stat,
                currentEmpathy = derived.currentEmpathy,
                currentLuck = character.luckCurrent
            )
            CyberPanel {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = skill.name,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "${skill.category} • ${skill.stat.code}($baseStat) + Nv(${skill.level}) = Base ${baseStat + skill.level}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(
                            onClick = { onUpdateSkillLevel(skill, -1) },
                            contentPadding = PaddingValues(horizontal = 6.dp)
                        ) { Text("-") }
                        TextButton(
                            onClick = { onUpdateSkillLevel(skill, 1) },
                            contentPadding = PaddingValues(horizontal = 6.dp)
                        ) { Text("+") }
                        if (skill.level < 10) {
                            val isDouble = OfficialSkillCatalog.allSkills
                                .firstOrNull { it.namePt.equals(skill.name, ignoreCase = true) }
                                ?.isDoubleCost == true
                            val ipCost = OfficialEconomyAndProgression.skillIpCostForNextLevel(skill.level + 1, isDouble)
                            TextButton(
                                onClick = { onUpgradeSkillWithIp(skill) },
                                enabled = character.improvementPoints >= ipCost,
                                contentPadding = PaddingValues(horizontal = 6.dp)
                            ) {
                                Text("${ipCost}IP", color = if (character.improvementPoints >= ipCost) CyberEmerald else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        IconButton(
                            onClick = { onOpenDiceRoller(skill.stat, skill.name, skill.level) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Casino,
                                contentDescription = "Rolar ${skill.name}",
                                tint = RedlineCrimson
                            )
                        }
                        IconButton(onClick = { onDeleteSkill(skill.id) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Remover perícia",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        item {
            val slots = uiState.cyberwareSlotSummary
            SectionHeader(
                title = "Cyberware & Perda de Humanidade",
                subtitle = "Custo total ativo: ${character.humanityLoss} HL • Teto máx: ${derived.maxHumanity}",
                accentColor = CyberCyan,
                trailing = {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        TextButton(
                            onClick = { showTherapyDialog = true },
                            modifier = Modifier.testTag("open_therapy_button")
                        ) {
                            Text("Terapia", color = CyberEmerald)
                        }
                        TextButton(
                            onClick = { showAddCyberwareDialog = true },
                            modifier = Modifier.testTag("add_cyberware_button")
                        ) {
                            Icon(Icons.Filled.Memory, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Instalar")
                        }
                    }
                }
            )
            Spacer(Modifier.height(6.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                CyberBadge(text = "Fashion ${slots.fashionwareUsed}/${slots.fashionwareMax}", color = CyberCyan)
                CyberBadge(text = "Interno ${slots.internalUsed}/${slots.internalMax}", color = CyberCyan)
                CyberBadge(text = "Externo ${slots.externalUsed}/${slots.externalMax}", color = CyberCyan)
                if (slots.neuralMax > 0) CyberBadge(text = "Neural ${slots.neuralUsed}/${slots.neuralMax}", color = CyberAmber)
                if (slots.cyberopticMax > 0) CyberBadge(text = "Óptico ${slots.cyberopticUsed}/${slots.cyberopticMax}", color = CyberAmber)
                if (slots.cyberaudioMax > 0) CyberBadge(text = "Áudio ${slots.cyberaudioUsed}/${slots.cyberaudioMax}", color = CyberAmber)
                if (slots.cyberarmMax > 0) CyberBadge(text = "Braço ${slots.cyberarmUsed}/${slots.cyberarmMax}", color = CyberAmber)
                if (slots.cyberlegMax > 0) CyberBadge(text = "Perna ${slots.cyberlegUsed}/${slots.cyberlegMax}", color = CyberAmber)
            }
            uiState.lastTherapyOutcome?.let { therapy ->
                Spacer(Modifier.height(6.dp))
                CyberPanel(accentColor = CyberEmerald, containerColor = CyberSurfaceElevated) {
                    Text(
                        text = therapy.summaryPt,
                        style = MaterialTheme.typography.bodySmall,
                        color = CyberEmerald
                    )
                }
            }
        }

        if (uiState.cyberware.isEmpty()) {
            item {
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text(
                        text = "Nenhum implante cibernético instalado. Adicione cyberware manualmente ou após importar os PDFs oficiais.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(uiState.cyberware, key = { it.id }) { cw ->
                CyberPanel(accentColor = if (cw.installed) CyberCyan else MaterialTheme.colorScheme.outline) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(cw.name, style = MaterialTheme.typography.titleSmall)
                            Text(
                                text = "${cw.slotArea} • Custo Humanidade: ${cw.humanityCost} HL",
                                style = MaterialTheme.typography.bodySmall,
                                color = CyberAmber
                            )
                            if (cw.notes.isNotBlank()) {
                                Text(
                                    text = cw.notes,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Switch(
                                checked = cw.installed,
                                onCheckedChange = { onToggleCyberware(cw) }
                            )
                            IconButton(onClick = { onDeleteCyberware(cw) }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Remover cyberware",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddSkillDialog) {
        AddSkillDialog(
            onDismiss = { showAddSkillDialog = false },
            onConfirm = { name, stat, level, cat ->
                onAddSkill(name, stat, level, cat)
                showAddSkillDialog = false
            }
        )
    }

    if (showAddCyberwareDialog) {
        AddCyberwareDialog(
            onDismiss = { showAddCyberwareDialog = false },
            onConfirm = { name, area, hl, notes ->
                onInstallCyberware(name, area, hl, notes)
                showAddCyberwareDialog = false
            }
        )
    }

    if (showAwardIpDialog) {
        AwardIpDialog(
            currentIp = character.improvementPoints,
            onAwardIp = onAwardIp,
            onDismiss = { showAwardIpDialog = false }
        )
    }

    if (showTherapyDialog) {
        TherapyDialog(
            currentHumanity = derived.currentHumanity,
            maxHumanity = derived.maxHumanity,
            pendingBrainwasherDelta = character.pendingTherapyDiceDelta,
            availableEurodollars = uiState.activeCampaign?.eurodollars ?: 0,
            onDismiss = { showTherapyDialog = false },
            onConfirm = { type, useClinic, payEb ->
                onRunTherapy(type, useClinic, payEb)
                showTherapyDialog = false
            }
        )
    }
}

@Composable
private fun ArmorSpBox(
    label: String,
    sp: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
    modifier: Modifier = Modifier
) {
    CyberPanel(modifier = modifier, containerColor = CyberSurfaceElevated) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                    tint = CyberCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(label, style = MaterialTheme.typography.labelMedium)
            }
            Text(
                text = "PA $sp",
                style = MaterialTheme.typography.titleMedium,
                color = CyberCyan,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onMinus, contentPadding = PaddingValues(horizontal = 8.dp)) { Text("-") }
            TextButton(onClick = onPlus, contentPadding = PaddingValues(horizontal = 8.dp)) { Text("+") }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddSkillDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, RedStat, Int, String) -> Unit
) {
    var search by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Combate") }
    var stat by remember { mutableStateOf(RedStat.REF) }
    var levelText by remember { mutableStateOf("4") }

    val matchingOfficial = remember(search) {
        val q = search.trim()
        val list = if (q.isEmpty()) OfficialSkillCatalog.allSkills else {
            OfficialSkillCatalog.allSkills.filter {
                it.namePt.contains(q, ignoreCase = true) ||
                    it.nameEn.contains(q, ignoreCase = true) ||
                    it.categoryPt.contains(q, ignoreCase = true)
            }
        }
        list.take(18)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Perícia") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text("Filtrar Catálogo Oficial (66 perícias)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(matchingOfficial, key = { it.nameEn }) { official ->
                        val selected = name == official.namePt
                        FilterChip(
                            selected = selected,
                            onClick = {
                                name = official.namePt
                                category = official.categoryPt
                                stat = official.stat
                            },
                            label = {
                                val x2 = if (official.isDoubleCost) " (x2)" else ""
                                Text("${official.namePt}$x2")
                            }
                        )
                    }
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome da Perícia") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoria") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = levelText,
                    onValueChange = { levelText = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Nível (0 a 10)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    RedStat.entries.forEach { s ->
                        FilterChip(
                            selected = stat == s,
                            onClick = { stat = s },
                            label = { Text(s.code) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, stat, levelText.toIntOrNull() ?: 2, category) },
                enabled = name.isNotBlank()
            ) { Text("Salvar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
private fun AddCyberwareDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int, String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(CyberwareCategory.NEURALWARE) }
    var name by remember { mutableStateOf("") }
    var slotArea by remember { mutableStateOf(CyberwareCategory.NEURALWARE.labelPt) }
    var hlCost by remember { mutableIntStateOf(7) }
    var notes by remember { mutableStateOf("") }

    val catalogInCat = remember(selectedCategory) {
        OfficialCyberwareCatalog.allCyberware.filter { it.category == selectedCategory }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Instalar Cyberware") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(CyberwareCategory.entries) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = {
                                selectedCategory = cat
                                slotArea = cat.labelPt
                            },
                            label = { Text(cat.labelPt) }
                        )
                    }
                }
                Text(
                    text = selectedCategory.maxSlotsInfo,
                    style = MaterialTheme.typography.labelSmall,
                    color = CyberCyan
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(catalogInCat, key = { "${it.category.name}_${it.nameEn}" }) { cw ->
                        FilterChip(
                            selected = name == cw.namePt,
                            onClick = {
                                name = cw.namePt
                                slotArea = cw.category.labelPt
                                hlCost = cw.presetHl
                                notes = "${cw.installLocation} • ${cw.costEb}eb • HL ${cw.hlDiceFormula} — ${cw.notesPt}"
                            },
                            label = { Text("${cw.namePt} (${cw.presetHl} HL)") }
                        )
                    }
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Implante") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = slotArea,
                    onValueChange = { slotArea = it },
                    label = { Text("Área (Neuralware, Borgware, etc.)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = hlCost.toString(),
                    onValueChange = { hlCost = it.toIntOrNull()?.coerceIn(0, 80) ?: 0 },
                    label = { Text("Perda de Humanidade (HL)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas / Referência") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, slotArea, hlCost, notes) },
                enabled = name.isNotBlank()
            ) { Text("Instalar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
private fun TherapyDialog(
    currentHumanity: Int,
    maxHumanity: Int,
    pendingBrainwasherDelta: Int,
    availableEurodollars: Int,
    onDismiss: () -> Unit,
    onConfirm: (TherapyType, useClinicService: Boolean, payWithEb: Boolean) -> Unit
) {
    var selectedType by remember { mutableStateOf(TherapyType.STANDARD_HUMANITY) }
    var useClinicService by remember { mutableStateOf(true) }
    var payWithEb by remember { mutableStateOf(true) }

    val cost = if (useClinicService) selectedType.clinicCostEb else selectedType.selfMaterialsEb

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Terapia Médica (7 dias)") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Humanidade Atual: $currentHumanity / $maxHumanity (teto reduzido por implantes ativos).",
                    style = MaterialTheme.typography.bodySmall,
                    color = CyberCyan
                )
                if (pendingBrainwasherDelta != 0) {
                    val sign = if (pendingBrainwasherDelta > 0) "+" else ""
                    Text(
                        text = "Brainwasher ativo: ${sign}${pendingBrainwasherDelta}d6 na próxima Terapia de Humanidade.",
                        style = MaterialTheme.typography.bodySmall,
                        color = CyberAmber
                    )
                }
                TherapyType.entries.forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type.labelPt) }
                    )
                }
                Text(
                    text = selectedType.descriptionPt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (useClinicService) "Clínica (Sucesso Auto)" else "Medtech Próprio (TEC+MedTech)",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = useClinicService,
                        onCheckedChange = { useClinicService = it }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Debitar ${cost}eb (Saldo: ${availableEurodollars}eb)",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = payWithEb,
                        onCheckedChange = { payWithEb = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(selectedType, useClinicService, payWithEb) },
                modifier = Modifier.testTag("confirm_therapy_button")
            ) { Text("Executar Terapia") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

