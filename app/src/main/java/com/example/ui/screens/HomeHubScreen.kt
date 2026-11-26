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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.core.rules.OfficialNightCityAtlasCatalog
import com.example.core.rules.RedStat
import com.example.core.rules.WoundState
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.EmptyCyberState
import com.example.ui.components.SectionHeader
import com.example.ui.components.TelemetryGaugeBar
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.CyberVoid
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeHubScreen(
    uiState: MainUiState,
    onOpenCampaigns: () -> Unit,
    onOpenCharacter: () -> Unit,
    onOpenPlay: () -> Unit,
    onOpenSystems: () -> Unit,
    onOpenCompendium: () -> Unit,
    onOpenAtlas: () -> Unit,
    onOpenDiceRoller: (RedStat, String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val active = uiState.activeCampaign
    val character = uiState.primaryCharacter
    val derived = uiState.derivedMetrics

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp)
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
                                listOf(CyberVoid.copy(alpha = 0.25f), CyberVoid.copy(alpha = 0.94f))
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
                        CyberBadge(text = "NIGHT CITY // 2045", color = RedlineCrimson)
                        if (active != null) {
                            CyberBadge(
                                text = "Dia ${active.inGameDay} • ${active.inGameHour.toString().padStart(2, '0')}:00",
                                color = CyberAmber
                            )
                        }
                    }
                    Column {
                        Text(
                            text = active?.title ?: "BEM-VINDO A NIGHT CITY",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (character != null && active != null) {
                                "${character.handle} • ${character.role.displayNamePt} (Rank ${character.roleRank}) • ${active.eurodollars} eb"
                            } else {
                                "Sistema solo determinístico pronto para sua campanha."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        if (active == null || character == null || derived == null) {
            item {
                EmptyCyberState(
                    icon = Icons.Filled.Campaign,
                    title = "Nenhuma campanha ativa",
                    description = "Crie sua campanha solo ou carregue um arquétipo do Single Shot Pack para começar a jogar.",
                    action = {
                        Button(
                            onClick = onOpenCampaigns,
                            colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                            modifier = Modifier.testTag("home_go_campaigns_button")
                        ) {
                            Icon(Icons.Filled.Campaign, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("IR PARA CAMPANHAS")
                        }
                    }
                )
            }
        } else {
            item {
                val district = remember(active.currentDistrictCode) {
                    OfficialNightCityAtlasCatalog.findDistrictByCode(active.currentDistrictCode)
                }
                val woundColor = when (derived.woundState) {
                    WoundState.UNINJURED -> CyberEmerald
                    WoundState.LIGHTLY_WOUNDED -> CyberCyan
                    WoundState.SERIOUSLY_WOUNDED -> CyberAmber
                    WoundState.MORTALLY_WOUNDED, WoundState.DEAD -> RedlineCrimson
                }
                SectionHeader(
                    title = "Status do Edgerunner",
                    subtitle = district?.let { "${it.code} — ${it.namePt} (${it.zone.shortLabelPt})" } ?: active.settingNotes,
                    accentColor = woundColor
                )
                Spacer(Modifier.height(8.dp))
                CyberPanel(accentColor = woundColor) {
                    TelemetryGaugeBar(
                        label = "PONTOS DE VIDA (${derived.woundState.labelPt})",
                        current = character.currentHp,
                        max = derived.maxHp,
                        accentColor = woundColor
                    )
                    Spacer(Modifier.height(10.dp))
                    TelemetryGaugeBar(
                        label = "HUMANIDADE • EMP Efetiva ${derived.currentEmpathy}/${character.empathyMax}",
                        current = derived.currentHumanity,
                        max = derived.maxHumanity,
                        accentColor = CyberCyan
                    )
                    Spacer(Modifier.height(12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        CyberBadge(text = "PA Cab ${character.headArmorSp} / Crp ${character.bodyArmorSp}", color = CyberCyan)
                        CyberBadge(text = "SOR ${character.luckCurrent}/${character.luckMax}", color = CyberAmber)
                        CyberBadge(text = "${character.improvementPoints} I.P.", color = CyberEmerald)
                        CyberBadge(text = "${active.eurodollars} eb", color = CyberAmber)
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onOpenPlay,
                            colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_continue_play_button")
                        ) {
                            Icon(Icons.Filled.Forum, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Continuar Sessão")
                        }
                        OutlinedButton(
                            onClick = { onOpenDiceRoller(RedStat.REF, "Ação Rápida", 4) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_quick_roll_button")
                        ) {
                            Icon(Icons.Filled.Casino, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Rolar 1d10")
                        }
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = "Acesso Rápido",
                subtitle = "Navegação direta para os módulos centrais e ferramentas",
                accentColor = CyberCyan
            )
            Spacer(Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 2
            ) {
                QuickLaunchTile(
                    title = "Personagem",
                    subtitle = "Ficha, atributos, perícias e cromo",
                    icon = Icons.Filled.Badge,
                    accent = RedlineCrimson,
                    onClick = onOpenCharacter,
                    modifier = Modifier.weight(1f)
                )
                QuickLaunchTile(
                    title = "Sistemas",
                    subtitle = "Arsenal, Netrunning, NPCs e Missões",
                    icon = Icons.Filled.Terminal,
                    accent = CyberCyan,
                    onClick = onOpenSystems,
                    modifier = Modifier.weight(1f)
                )
                QuickLaunchTile(
                    title = "Atlas NC",
                    subtitle = "24 distritos e 156 locais oficiais",
                    icon = Icons.Filled.Explore,
                    accent = CyberEmerald,
                    onClick = onOpenAtlas,
                    modifier = Modifier.weight(1f)
                )
                QuickLaunchTile(
                    title = "Compêndio",
                    subtitle = "Livro Mestre, DLCs e PDFs",
                    icon = Icons.Filled.MenuBook,
                    accent = CyberAmber,
                    onClick = onOpenCompendium,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        val lastEntry = uiState.journal.firstOrNull()
        if (lastEntry != null) {
            item {
                SectionHeader(
                    title = "Último Registro da Sessão",
                    subtitle = lastEntry.inGameTimestamp,
                    accentColor = CyberAmber
                )
                Spacer(Modifier.height(6.dp))
                CyberPanel(containerColor = CyberSurfaceElevated) {
                    CyberBadge(text = lastEntry.speakerLabel, color = CyberAmber)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = lastEntry.bodyText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (!lastEntry.deterministicDetail.isNullOrBlank()) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = lastEntry.deterministicDetail,
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberCyan
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickLaunchTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CyberPanel(
        modifier = modifier.clickable(onClick = onClick),
        accentColor = accent,
        containerColor = CyberSurfaceElevated
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = accent,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2
        )
    }
}
