package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.local.entity.JournalEntryType
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.EmptyCyberState
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@Composable
fun JournalHistoryScreen(
    uiState: MainUiState,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onNavigateBack)

    var selectedFilter by remember { mutableStateOf<JournalEntryType?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredEntries = remember(uiState.journal, selectedFilter, searchQuery) {
        val q = searchQuery.trim()
        uiState.journal.filter { entry ->
            val matchesType = selectedFilter == null || entry.entryType == selectedFilter
            val matchesQuery = q.isEmpty() ||
                entry.bodyText.contains(q, ignoreCase = true) ||
                entry.speakerLabel.contains(q, ignoreCase = true) ||
                (entry.deterministicDetail?.contains(q, ignoreCase = true) == true)
            matchesType && matchesQuery
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("journal_history_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(Modifier.width(4.dp))
                SectionHeader(
                    title = "Diário & Histórico (${filteredEntries.size})",
                    subtitle = "Registro imutável de ações, eventos e rolagens",
                    accentColor = RedlineCrimson
                )
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                placeholder = { Text("Filtrar por evento, local, dado ou personagem...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = selectedFilter == null,
                        onClick = { selectedFilter = null },
                        label = { Text("Todos") }
                    )
                }
                items(JournalEntryType.entries) { type ->
                    val label = when (type) {
                        JournalEntryType.PLAYER_ACTION -> "Jogador"
                        JournalEntryType.NARRATOR_GM -> "Narrador"
                        JournalEntryType.DETERMINISTIC_ROLL -> "Rolagens"
                        JournalEntryType.SYSTEM_EVENT -> "Sistema"
                    }
                    FilterChip(
                        selected = selectedFilter == type,
                        onClick = { selectedFilter = type },
                        label = { Text(label) }
                    )
                }
            }
        }

        if (filteredEntries.isEmpty()) {
            item {
                EmptyCyberState(
                    icon = Icons.Filled.History,
                    title = "Sem registros encontrados",
                    description = "Execute ações ou rolagens na aba Jogar para preencher a linha do tempo da campanha."
                )
            }
        } else {
            items(filteredEntries, key = { it.id }) { entry ->
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
                    Text(entry.bodyText, style = MaterialTheme.typography.bodyMedium)
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
    }
}
