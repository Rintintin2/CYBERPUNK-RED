package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiSettingsScreen(
    uiState: MainUiState,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onNavigateBack)

    var selectedProvider by remember { mutableStateOf("OFFLINE") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("ai_settings_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
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
                    title = "Inteligência Artificial",
                    subtitle = "Camada narrativa separada do motor determinístico",
                    accentColor = CyberCyan
                )
            }
        }

        item {
            CyberPanel(accentColor = CyberEmerald) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.SmartToy, contentDescription = null, tint = CyberEmerald)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = uiState.aiProviderMode.labelPt,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = CyberEmerald
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Invariante Arquitetural:\n" +
                        "• A IA nunca rola dados, nunca altera PV, PA, Humanidade ou I.P. e nunca calcula DVs.\n" +
                        "• Todo cálculo de regra permanece 100% determinístico em Kotlin offline.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            SectionHeader(
                title = "Provedores de Narração",
                subtitle = "Seleção e cofre de chaves (Ciclo 6)",
                accentColor = CyberAmber
            )
            Spacer(Modifier.height(8.dp))
            CyberPanel(containerColor = CyberSurfaceElevated) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "OFFLINE" to "Offline Determinístico (Ativo)",
                        "GEMINI" to "Gemini (Ciclo 6)",
                        "OPENAI" to "OpenAI (Ciclo 6)",
                        "CLAUDE" to "Claude (Ciclo 6)"
                    ).forEach { (id, label) ->
                        FilterChip(
                            selected = selectedProvider == id,
                            onClick = { if (id == "OFFLINE") selectedProvider = id },
                            enabled = id == "OFFLINE",
                            label = { Text(label) }
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Lock, contentDescription = null, tint = CyberAmber)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "O armazenamento criptografado via Android Keystore e a Inteligência Visual licenciada serão ativados no Ciclo 6.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            CyberPanel(accentColor = RedlineCrimson) {
                CyberBadge(text = "SNAPSHOT DE CONTEXTO ATIVO", color = RedlineCrimson)
                Spacer(Modifier.height(8.dp))
                val c = uiState.activeCampaign
                val ch = uiState.primaryCharacter
                Text(
                    text = if (c != null && ch != null) {
                        "Campanha: ${c.title}\nOperativo: ${ch.handle} (${ch.role.displayNamePt})\n" +
                            "Estado: ${ch.currentHp} PV • ${c.eurodollars} eb • Distrito ${c.currentDistrictCode}\n" +
                            "Entradas no Diário: ${uiState.journal.size}"
                    } else {
                        "Nenhuma campanha selecionada no momento."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
