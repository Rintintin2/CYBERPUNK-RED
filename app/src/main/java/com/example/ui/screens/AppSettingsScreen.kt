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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun AppSettingsScreen(
    uiState: MainUiState,
    skipIntroOnLaunch: Boolean,
    onToggleSkipIntro: (Boolean) -> Unit,
    onReplayIntro: () -> Unit,
    onOpenAiSettings: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onNavigateBack)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("app_settings_screen"),
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
                    title = "Configurações",
                    subtitle = "Preferências locais do sistema CYBERPUNK RED",
                    accentColor = CyberAmber
                )
            }
        }

        item {
            CyberPanel(accentColor = RedlineCrimson) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Pular abertura automática",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Entra direto na aba Início sem exibir a sequência glitch de boot.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = skipIntroOnLaunch,
                        onCheckedChange = onToggleSkipIntro
                    )
                }
                Spacer(Modifier.height(10.dp))
                OutlinedButton(
                    onClick = onReplayIntro,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("replay_intro_button")
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Rever Tela de Abertura (Night City Boot)")
                }
            }
        }

        item {
            CyberPanel(accentColor = CyberCyan, containerColor = CyberSurfaceElevated) {
                Text(
                    text = "MÓDULO DE INTELIGÊNCIA ARTIFICIAL",
                    style = MaterialTheme.typography.labelLarge,
                    color = CyberCyan
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Modo atual: ${uiState.aiProviderMode.labelPt}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onOpenAiSettings,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.SmartToy, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Abrir Configurações de IA")
                }
            }
        }

        item {
            CyberPanel(accentColor = CyberEmerald) {
                CyberBadge(text = "OFFLINE FIRST • ROOM SQLITE v3", color = CyberEmerald)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "• Campanhas salvas: ${uiState.campaigns.size}\n" +
                        "• Documentos PDF vinculados: ${uiState.ruleDocuments.size}\n" +
                        "• Identidade visual: HÍBRIDO RED//NEON (#090B10 / #FF303F / #00C8FF / #F5C542)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
