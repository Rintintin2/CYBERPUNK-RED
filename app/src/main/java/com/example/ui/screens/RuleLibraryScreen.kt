package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.MenuBook
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.rules.OfficialCombatTables
import com.example.core.rules.OfficialCriticalInjuries
import com.example.core.rules.OfficialCyberwareCatalog
import com.example.core.rules.OfficialDifficulty
import com.example.core.rules.OfficialGearCatalog
import com.example.core.rules.OfficialNetArchitectures
import com.example.core.rules.RangeBracket
import com.example.core.rules.RangeWeaponCategory
import com.example.data.local.entity.RuleDocumentEntity
import com.example.ui.components.ATLAS_GANGS_DGD_TABS
import com.example.ui.components.BLACK_CHROME_TABS
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.EmptyCyberState
import com.example.ui.components.INTERFACE_DLC_TABS
import com.example.ui.components.PART2_COMPENDIUM_TABS
import com.example.ui.components.SOLO_STEALTH_AGENTS_TABS
import com.example.ui.components.SectionHeader
import com.example.ui.components.compendiumAtlasGangsDgdItems
import com.example.ui.components.compendiumBlackChromeItems
import com.example.ui.components.compendiumInterfaceDlcItems
import com.example.ui.components.compendiumPart2Items
import com.example.ui.components.compendiumSoloStealthAgentsItems
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@Composable
fun RuleLibraryScreen(
    uiState: MainUiState,
    onImportPdfUri: (Uri) -> Unit,
    onAddRuleExcerpt: (Long, String, String, String) -> Unit,
    onDeleteDocument: (Long) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var excerptTargetDoc by remember { mutableStateOf<RuleDocumentEntity?>(null) }
    var compendiumVolume by remember { mutableIntStateOf(0) } // 0..5
    var compendiumTab by remember { mutableIntStateOf(0) }
    var part2Tab by remember { mutableIntStateOf(0) }
    var dlcTab by remember { mutableIntStateOf(0) }
    var bcTab by remember { mutableIntStateOf(0) }
    var soloStealthTab by remember { mutableIntStateOf(0) }
    var atlasDgdTab by remember { mutableIntStateOf(0) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // Alguns provedores SAF não suportam permissão persistente; o registro local continua válido.
            }
            onImportPdfUri(uri)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Biblioteca Oficial de PDFs",
                subtitle = "Importação via Storage Access Framework (sem permissões invasivas)",
                accentColor = CyberCyan,
                trailing = {
                    Button(
                        onClick = { pdfPickerLauncher.launch(arrayOf("application/pdf")) },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("import_pdf_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PictureAsPdf,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Importar PDF", color = MaterialTheme.colorScheme.onSecondary)
                    }
                }
            )
        }

        if (uiState.ruleDocuments.isEmpty()) {
            item {
                EmptyCyberState(
                    icon = Icons.Outlined.MenuBook,
                    title = "Nenhum PDF oficial importado",
                    description = "Vincule os documentos PDF oficiais de Cyberpunk RED para catalogar capítulos, tabelas de armas, cyberware e regras sem homebrew.",
                    action = {
                        OutlinedButton(
                            onClick = { pdfPickerLauncher.launch(arrayOf("application/pdf")) }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Selecionar PDF no dispositivo")
                        }
                    }
                )
            }
        } else {
            items(uiState.ruleDocuments, key = { "doc_${it.id}" }) { doc ->
                CyberPanel(accentColor = CyberCyan) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(doc.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(
                                text = "${doc.fileSizeLabel} • ${doc.indexedStatus}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Row {
                            TextButton(onClick = { excerptTargetDoc = doc }) {
                                Text("+ Trecho")
                            }
                            IconButton(onClick = { onDeleteDocument(doc.id) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Remover PDF")
                            }
                        }
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = "Consulta Rápida de Regras",
                subtitle = "Busca local em seções indexadas dos PDFs",
                accentColor = CyberAmber
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.ruleSearchQuery,
                onValueChange = onSearchQueryChange,
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                placeholder = { Text("Buscar por DV, Netrunning, Cyberware, Ferimento...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("rule_search_input")
            )
        }

        if (uiState.ruleSections.isNotEmpty()) {
            items(uiState.ruleSections, key = { "sec_${it.id}" }) { section ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(section.sectionTitle, style = MaterialTheme.typography.titleSmall)
                        CyberBadge(text = section.pageRef, color = CyberAmber)
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(section.contentText, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        item {
            SectionHeader(
                title = "Compêndio Oficial — Livro + DLCs + Atlas & DGD",
                subtitle = "Core + Interface 1–3 + Black Chrome + Solo+ + Going Quiet + Agents + Atlas + DGD",
                accentColor = CyberEmerald
            )
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = compendiumVolume == 0,
                        onClick = { compendiumVolume = 0 },
                        label = { Text("Livro • Parte 1") },
                        modifier = Modifier.testTag("compendium_part1_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = compendiumVolume == 1,
                        onClick = { compendiumVolume = 1 },
                        label = { Text("Livro • Parte 2") },
                        modifier = Modifier.testTag("compendium_part2_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = compendiumVolume == 2,
                        onClick = { compendiumVolume = 2 },
                        label = { Text("Interface RED 1–3") },
                        modifier = Modifier.testTag("compendium_interface_dlc_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = compendiumVolume == 3,
                        onClick = { compendiumVolume = 3 },
                        label = { Text("Black Chrome & Ganchos") },
                        modifier = Modifier.testTag("compendium_black_chrome_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = compendiumVolume == 4,
                        onClick = { compendiumVolume = 4 },
                        label = { Text("Solo+, Furtivo & Agents") },
                        modifier = Modifier.testTag("compendium_solo_stealth_chip")
                    )
                }
                item {
                    FilterChip(
                        selected = compendiumVolume == 5,
                        onClick = { compendiumVolume = 5 },
                        label = { Text("Atlas, Gangues & DGD") },
                        modifier = Modifier.testTag("compendium_atlas_dgd_chip")
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            when (compendiumVolume) {
                0 -> {
                    val sections = listOf("DVs & Alcance", "Críticos", "Armas & PA", "Cyberware", "Netrun", "Drogas & Terapia")
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(sections.indices.toList()) { idx ->
                            FilterChip(
                                selected = compendiumTab == idx,
                                onClick = { compendiumTab = idx },
                                label = { Text(sections[idx]) }
                            )
                        }
                    }
                }
                1 -> {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(PART2_COMPENDIUM_TABS.indices.toList()) { idx ->
                            FilterChip(
                                selected = part2Tab == idx,
                                onClick = { part2Tab = idx },
                                label = { Text(PART2_COMPENDIUM_TABS[idx]) }
                            )
                        }
                    }
                }
                2 -> {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(INTERFACE_DLC_TABS.indices.toList()) { idx ->
                            FilterChip(
                                selected = dlcTab == idx,
                                onClick = { dlcTab = idx },
                                label = { Text(INTERFACE_DLC_TABS[idx]) }
                            )
                        }
                    }
                }
                3 -> {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(BLACK_CHROME_TABS.indices.toList()) { idx ->
                            FilterChip(
                                selected = bcTab == idx,
                                onClick = { bcTab = idx },
                                label = { Text(BLACK_CHROME_TABS[idx]) }
                            )
                        }
                    }
                }
                4 -> {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(SOLO_STEALTH_AGENTS_TABS.indices.toList()) { idx ->
                            FilterChip(
                                selected = soloStealthTab == idx,
                                onClick = { soloStealthTab = idx },
                                label = { Text(SOLO_STEALTH_AGENTS_TABS[idx]) }
                            )
                        }
                    }
                }
                else -> {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(ATLAS_GANGS_DGD_TABS.indices.toList()) { idx ->
                            FilterChip(
                                selected = atlasDgdTab == idx,
                                onClick = { atlasDgdTab = idx },
                                label = { Text(ATLAS_GANGS_DGD_TABS[idx]) }
                            )
                        }
                    }
                }
            }
        }

        if (compendiumVolume == 1) {
            compendiumPart2Items(selectedPart2Tab = part2Tab)
        } else if (compendiumVolume == 2) {
            compendiumInterfaceDlcItems(selectedDlcTab = dlcTab)
        } else if (compendiumVolume == 3) {
            compendiumBlackChromeItems(selectedBcTab = bcTab)
        } else if (compendiumVolume == 4) {
            compendiumSoloStealthAgentsItems(selectedTab = soloStealthTab)
        } else if (compendiumVolume == 5) {
            compendiumAtlasGangsDgdItems(selectedTab = atlasDgdTab)
        } else when (compendiumTab) {
            0 -> item {
                CyberPanel(accentColor = CyberEmerald) {
                    Text(
                        text = "VALORES DE DIFICULDADE (DV)",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberEmerald
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = OfficialDifficulty.entries.joinToString(" | ") { "${it.labelPt}: ${it.dv}" },
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "ALCANCE DE ARMAS À DISTÂNCIA (DV POR METROS)",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                    Spacer(Modifier.height(6.dp))
                    RangeWeaponCategory.entries.forEach { cat ->
                        val row = RangeBracket.entries.joinToString(" / ") { b ->
                            OfficialCombatTables.getSingleShotDv(cat, b)?.toString() ?: "—"
                        }
                        Text(
                            text = "${cat.labelPt}: $row",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Faixas: ${RangeBracket.entries.joinToString(" | ") { it.labelMeters }}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberAmber
                    )
                }
            }
            1 -> {
                items(OfficialCriticalInjuries.bodyInjuries, key = { "crit_body_${it.roll2d6}" }) { ci ->
                    CyberPanel(accentColor = RedlineCrimson) {
                        Text("Corpo [2d6=${ci.roll2d6}] ${ci.namePt}", style = MaterialTheme.typography.titleSmall, color = RedlineCrimson)
                        Text(ci.effectPt, style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "Reparo Rápido: ${ci.quickFixSkill?.let { "$it DV${ci.quickFixDv}" } ?: "N/A"} • Tratamento: ${ci.treatmentSkill} DV${ci.treatmentDv}",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberAmber
                        )
                    }
                }
                items(OfficialCriticalInjuries.headInjuries, key = { "crit_head_${it.roll2d6}" }) { ci ->
                    CyberPanel(accentColor = RedlineCrimson) {
                        Text("Cabeça [2d6=${ci.roll2d6}] ${ci.namePt}", style = MaterialTheme.typography.titleSmall, color = RedlineCrimson)
                        Text(ci.effectPt, style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "Reparo Rápido: ${ci.quickFixSkill?.let { "$it DV${ci.quickFixDv}" } ?: "N/A"} • Tratamento: ${ci.treatmentSkill} DV${ci.treatmentDv}",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberAmber
                        )
                    }
                }
            }
            2 -> item {
                CyberPanel(accentColor = CyberAmber) {
                    Text("ARMADURAS & ESCUDO", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialGearCatalog.armorAndShields.forEach {
                        Text("• ${it.namePt} (${it.costEb}eb) — ${it.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("ARMAS BRANCAS & À DISTÂNCIA", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    (OfficialGearCatalog.meleeWeapons + OfficialGearCatalog.rangedWeapons).forEach {
                        Text("• ${it.namePt} (${it.costEb}eb) — ${it.notesPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            3 -> items(OfficialCyberwareCatalog.allCyberware, key = { "cw_comp_${it.category}_${it.nameEn}" }) { cw ->
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    Text(
                        text = "${cw.namePt} [${cw.category.labelPt}]",
                        style = MaterialTheme.typography.titleSmall,
                        color = CyberCyan
                    )
                    Text(
                        text = "${cw.installLocation} • ${cw.costEb}eb • HL: ${cw.presetHl} (${cw.hlDiceFormula}) — ${cw.notesPt}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            4 -> item {
                CyberPanel(accentColor = CyberCyan) {
                    Text("PROGRAMAS DE CYBERDECK", style = MaterialTheme.typography.labelLarge, color = CyberCyan)
                    Spacer(Modifier.height(4.dp))
                    OfficialNetArchitectures.programs.forEach { p ->
                        Text("• ${p.name} [${p.programClassPt}] (${p.costEb}eb): ${p.effectPt}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("BLACK ICE & DEMÔNIOS", style = MaterialTheme.typography.labelLarge, color = RedlineCrimson)
                    Spacer(Modifier.height(4.dp))
                    OfficialNetArchitectures.blackIce.forEach { ice ->
                        Text("• ${ice.name} (${ice.iceClassPt}, REZ ${ice.rez}, SPD ${ice.spd}, ATK ${ice.atk}): ${ice.effectPt}", style = MaterialTheme.typography.bodySmall)
                    }
                    OfficialNetArchitectures.demons.forEach { d ->
                        Text("• Demônio ${d.name}: REZ ${d.rez} | Interface ${d.interfaceRank} | ${d.netActions} ações | Combate ${d.combatNumber}", style = MaterialTheme.typography.bodySmall, color = CyberAmber)
                    }
                }
            }
            else -> item {
                CyberPanel(accentColor = CyberEmerald) {
                    Text("DROGAS DE RUA", style = MaterialTheme.typography.labelLarge, color = CyberAmber)
                    Spacer(Modifier.height(4.dp))
                    OfficialGearCatalog.streetDrugs.forEach { drug ->
                        Text("• ${drug.name} (${drug.costEb}eb / ${drug.durationHours}h): ${drug.primaryEffectPt} Vício DV${drug.secondaryDv}: ${drug.secondaryEffectPt}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("TERAPIAS (MEDTECH)", style = MaterialTheme.typography.labelLarge, color = CyberEmerald)
                    Spacer(Modifier.height(4.dp))
                    OfficialGearCatalog.therapies.forEach { th ->
                        Text("• ${th.namePt} (${th.costEb}eb, MedTech DV${th.medicalTechDv}): ${th.effectPt}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        item {
            CyberPanel(accentColor = CyberEmerald) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.VerifiedUser,
                        contentDescription = null,
                        tint = CyberEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "REGRAS DETERMINÍSTICAS ATIVAS (SEM HOMEBREW)",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberEmerald
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "• Teste de Perícia: Atributo + Perícia + Sorte + 1d10 (10 explode +1d10; 1 implode -1d10) > DV\n" +
                        "• Pontos de Vida Máximos: 10 + 5 × ⌈(COR + VON) / 2⌉\n" +
                        "• Ferimento Grave: PV ≤ ⌈PV Máx / 2⌉ (-2 em ações)\n" +
                        "• Mortalmente Ferido: PV = 0 (-4 em ações + Teste de Morte 1d10 < COR)\n" +
                        "• Humanidade: Máx = (EMP × 10) - (2×Cyberware / 4×Borgware c/ HL) • EMP = ⌊Humanidade / 10⌋\n" +
                        "• Arma Branca / Artes Marciais ignora ½ PA (cima); 2+ seis geram Crítico (+5 dano) mesmo sem penetrar PA\n" +
                        "• Esquiva de Balas requer REF ≥ 8 (DES + Evasão + 1d10)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    excerptTargetDoc?.let { doc ->
        AddRuleExcerptDialog(
            documentTitle = doc.title,
            onDismiss = { excerptTargetDoc = null },
            onConfirm = { title, page, content ->
                onAddRuleExcerpt(doc.id, title, page, content)
                excerptTargetDoc = null
            }
        )
    }
}

@Composable
private fun AddRuleExcerptDialog(
    documentTitle: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var pageRef by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Indexar Trecho de $documentTitle") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título da Regra / Tabela") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pageRef,
                    onValueChange = { pageRef = it },
                    label = { Text("Página (ex: p. 169)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Texto / Fórmula Oficial") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, pageRef, content) },
                enabled = title.isNotBlank() && content.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson)
            ) { Text("Indexar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
