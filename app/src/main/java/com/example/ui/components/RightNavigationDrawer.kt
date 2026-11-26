package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberOutline
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.CyberVoid
import com.example.ui.theme.RedlineCrimson

data class DrawerMenuEntry(
    val route: String,
    val label: String,
    val subtitle: String,
    val icon: ImageVector,
    val accent: Color,
    val testTag: String
)

@Composable
fun RightNavigationDrawerContent(
    currentRoute: String,
    activeCampaignTitle: String?,
    activeDistrictCode: String?,
    onSelectRoute: (String) -> Unit,
    onCloseDrawer: () -> Unit,
    routes: DrawerRoutes,
    modifier: Modifier = Modifier
) {
    val entries = listOf(
        DrawerMenuEntry(
            route = routes.systems,
            label = "Sistemas",
            subtitle = "Arsenal, Mercado, Netrunning, NPCs e Missões",
            icon = Icons.Filled.Terminal,
            accent = CyberCyan,
            testTag = "drawer_nav_systems"
        ),
        DrawerMenuEntry(
            route = routes.compendium,
            label = "Compêndio",
            subtitle = "PDFs oficiais, tabelas e regras de referência",
            icon = Icons.Filled.MenuBook,
            accent = CyberAmber,
            testTag = "drawer_nav_compendium"
        ),
        DrawerMenuEntry(
            route = routes.atlas,
            label = "Atlas de Night City",
            subtitle = "24 distritos, 156 locais e zonas de perigo",
            icon = Icons.Filled.Explore,
            accent = CyberCyan,
            testTag = "drawer_nav_atlas"
        ),
        DrawerMenuEntry(
            route = routes.journal,
            label = "Diário / Histórico",
            subtitle = "Auditoria completa de cenas, falas e rolagens",
            icon = Icons.Filled.History,
            accent = RedlineCrimson,
            testTag = "drawer_nav_journal"
        ),
        DrawerMenuEntry(
            route = routes.aiSettings,
            label = "Inteligência Artificial",
            subtitle = "Provedor narrativo e diretrizes do Mestre",
            icon = Icons.Filled.SmartToy,
            accent = CyberCyan,
            testTag = "drawer_nav_ai"
        ),
        DrawerMenuEntry(
            route = routes.settings,
            label = "Configurações",
            subtitle = "Preferências do sistema, intro e persistência",
            icon = Icons.Filled.Settings,
            accent = CyberAmber,
            testTag = "drawer_nav_settings"
        )
    )

    ModalDrawerSheet(
        modifier = modifier
            .width(310.dp)
            .fillMaxHeight(),
        drawerContainerColor = CyberSurface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface,
        drawerShape = CutCornerShape(topStart = 18.dp, bottomStart = 18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CYBERPUNK RED",
                        style = MaterialTheme.typography.titleLarge,
                        color = RedlineCrimson,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "TERMINAL AUXILIAR",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberCyan
                    )
                }
                IconButton(
                    onClick = onCloseDrawer,
                    modifier = Modifier.testTag("close_right_drawer_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Fechar menu lateral"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CyberSurfaceElevated, CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp))
                    .border(1.dp, CyberOutline, CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp))
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = activeCampaignTitle ?: "Sem campanha ativa",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (activeDistrictCode != null) {
                            "Distrito atual: $activeDistrictCode • Motor 100% offline"
                        } else {
                            "Selecione ou crie uma campanha em Campanhas"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(color = CyberOutline)

            entries.forEach { entry ->
                val selected = currentRoute == entry.route
                NavigationDrawerItem(
                    selected = selected,
                    onClick = {
                        onCloseDrawer()
                        onSelectRoute(entry.route)
                    },
                    icon = {
                        Icon(
                            imageVector = entry.icon,
                            contentDescription = entry.label,
                            tint = if (selected) entry.accent else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Column {
                            Text(
                                text = entry.label,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            )
                            Text(
                                text = entry.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2
                            )
                        }
                    },
                    shape = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = entry.accent.copy(alpha = 0.14f),
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedContainerColor = CyberVoid.copy(alpha = 0.35f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(entry.testTag)
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Regras determinísticas oficiais • Nenhum resultado é alterado por IA.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

data class DrawerRoutes(
    val systems: String,
    val compendium: String,
    val atlas: String,
    val journal: String,
    val aiSettings: String,
    val settings: String
)
