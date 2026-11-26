package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.rules.AtlasDistrictSpec
import com.example.core.rules.AtlasZoneId
import com.example.core.rules.OfficialNightCityAtlasCatalog
import com.example.ui.components.CyberBadge
import com.example.ui.components.CyberPanel
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberVoid
import com.example.ui.theme.RedlineCrimson
import com.example.ui.viewmodel.MainUiState
import kotlin.math.hypot

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NightCityAtlasScreen(
    uiState: MainUiState,
    onMoveCampaignToDistrict: (String) -> Unit,
    onRollDistrictHook: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onNavigateBack() }

    val activeDistrictCode = uiState.activeCampaign?.currentDistrictCode ?: "E"
    var selectedCode by remember(activeDistrictCode) { mutableStateOf(activeDistrictCode) }
    var selectedZone by remember { mutableStateOf<AtlasZoneId?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val selectedDistrict = remember(selectedCode) {
        OfficialNightCityAtlasCatalog.findDistrictByCode(selectedCode)
            ?: OfficialNightCityAtlasCatalog.districts.first()
    }

    val searchMatches = remember(searchQuery) {
        OfficialNightCityAtlasCatalog.searchLocations(searchQuery).take(30)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "atlas_pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 16f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("night_city_atlas_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onNavigateBack, modifier = Modifier.testTag("atlas_back_button")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = CyberCyan
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Atlas Interativo de Night City",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "4 Zonas • 24 Distritos (A–X) • ${OfficialNightCityAtlasCatalog.allLocationsCount} Locais Codificados",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                CyberBadge(text = "ATUAL: $activeDistrictCode", color = RedlineCrimson)
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                item {
                    FilterChip(
                        selected = selectedZone == null,
                        onClick = { selectedZone = null },
                        label = { Text("Todas as Zonas (24)") }
                    )
                }
                items(AtlasZoneId.entries) { zone ->
                    FilterChip(
                        selected = selectedZone == zone,
                        onClick = { selectedZone = zone },
                        label = { Text(zone.shortLabelPt) }
                    )
                }
            }
        }

        item {
            InteractiveNightCityMapCanvas(
                districts = OfficialNightCityAtlasCatalog.districts,
                selectedCode = selectedDistrict.code,
                activeCampaignCode = activeDistrictCode,
                zoneFilter = selectedZone,
                pulseRadius = pulseRadius,
                onSelectDistrict = { selectedCode = it.code }
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                placeholder = { Text("Buscar nos ${OfficialNightCityAtlasCatalog.allLocationsCount} locais (ex: Afterlife, Forlorn Hope, H4, Japantown)...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("atlas_location_search")
            )
        }

        if (searchQuery.isNotBlank()) {
            item {
                SectionHeader(
                    title = "Resultados da Busca (${searchMatches.size})",
                    subtitle = "Toque em qualquer local para focar o distrito correspondente",
                    accentColor = CyberAmber
                )
            }
            if (searchMatches.isEmpty()) {
                item {
                    CyberPanel {
                        Text(
                            "Nenhum local encontrado para \"$searchQuery\".",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            } else {
                items(searchMatches, key = { "${it.first.code}_${it.second.code}" }) { (dist, loc) ->
                    CyberPanel(
                        accentColor = zoneColor(dist.zone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(dist.code) {
                                detectTapGestures { selectedCode = dist.code }
                            }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "${loc.code} — ${loc.name}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${dist.code} • ${dist.namePt}" +
                                        if (loc.notePt.isNotBlank()) " — ${loc.notePt}" else "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            OutlinedButton(onClick = { selectedCode = dist.code }) {
                                Text("Abrir ${dist.code}")
                            }
                        }
                    }
                }
            }
        }

        item {
            DistrictDossierCard(
                district = selectedDistrict,
                isCampaignHere = selectedDistrict.code == activeDistrictCode,
                hasCampaign = uiState.activeCampaign != null,
                onSelectNeighbor = { selectedCode = it },
                onMoveHere = { onMoveCampaignToDistrict(selectedDistrict.code) },
                onRollHook = { onRollDistrictHook(selectedDistrict.code) }
            )
        }
    }
}

@Composable
private fun InteractiveNightCityMapCanvas(
    districts: List<AtlasDistrictSpec>,
    selectedCode: String,
    activeCampaignCode: String,
    zoneFilter: AtlasZoneId?,
    pulseRadius: Float,
    onSelectDistrict: (AtlasDistrictSpec) -> Unit
) {
    val textMeasurer = rememberTextMeasurer()
    val filteredDistricts = remember(districts, zoneFilter) {
        if (zoneFilter == null) districts else districts.filter { it.zone == zoneFilter }
    }

    CyberPanel(accentColor = CyberCyan) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.95f)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFF0A192F), CyberVoid)
                    )
                )
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("atlas_map_canvas")
                    .pointerInput(filteredDistricts) {
                        detectTapGestures { tapOffset ->
                            val w = size.width.toFloat()
                            val h = size.height.toFloat()
                            var best: AtlasDistrictSpec? = null
                            var bestDist = Float.MAX_VALUE
                            filteredDistricts.forEach { d ->
                                val dx = d.normX * w - tapOffset.x
                                val dy = d.normY * h - tapOffset.y
                                val dist = hypot(dx, dy)
                                if (dist < bestDist) {
                                    bestDist = dist
                                    best = d
                                }
                            }
                            val chosen = best
                            if (chosen != null && bestDist <= 96f) {
                                onSelectDistrict(chosen)
                            }
                        }
                    }
            ) {
                val w = size.width
                val h = size.height
                val byCode = districts.associateBy { it.code }

                // Subtle cyber grid
                val gridStep = w / 10f
                for (i in 1..9) {
                    drawLine(
                        color = CyberCyan.copy(alpha = 0.08f),
                        start = Offset(i * gridStep, 0f),
                        end = Offset(i * gridStep, h),
                        strokeWidth = 1f
                    )
                    drawLine(
                        color = CyberCyan.copy(alpha = 0.08f),
                        start = Offset(0f, i * (h / 10f)),
                        end = Offset(w, i * (h / 10f)),
                        strokeWidth = 1f
                    )
                }

                // Water landmarks
                OfficialNightCityAtlasCatalog.waterLandmarks.forEach { water ->
                    val label = textMeasurer.measure(
                        text = water.namePt,
                        style = TextStyle(color = CyberCyan.copy(alpha = 0.45f), fontSize = 9.sp)
                    )
                    drawText(
                        textLayoutResult = label,
                        topLeft = Offset(
                            (water.normX * w - label.size.width / 2f).coerceIn(4f, w - label.size.width - 4f),
                            (water.normY * h - label.size.height / 2f).coerceIn(4f, h - label.size.height - 4f)
                        )
                    )
                }

                // Neighbor links
                val drawnEdges = HashSet<String>()
                districts.forEach { d ->
                    d.neighborCodes.forEach { nCode ->
                        val edgeKey = if (d.code < nCode) "${d.code}-$nCode" else "$nCode-${d.code}"
                        if (drawnEdges.add(edgeKey)) {
                            val neighbor = byCode[nCode]
                            if (neighbor != null) {
                                drawLine(
                                    color = Color.White.copy(alpha = 0.16f),
                                    start = Offset(d.normX * w, d.normY * h),
                                    end = Offset(neighbor.normX * w, neighbor.normY * h),
                                    strokeWidth = 1.8f,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
                                )
                            }
                        }
                    }
                }

                // Bridges
                OfficialNightCityAtlasCatalog.bridges.forEach { b ->
                    drawLine(
                        color = CyberAmber.copy(alpha = 0.85f),
                        start = Offset(b.x1 * w, b.y1 * h),
                        end = Offset(b.x2 * w, b.y2 * h),
                        strokeWidth = 5f
                    )
                }

                // District nodes
                districts.forEach { d ->
                    val inFilter = zoneFilter == null || d.zone == zoneFilter
                    val center = Offset(d.normX * w, d.normY * h)
                    val baseColor = zoneColor(d.zone)
                    val isSelected = d.code == selectedCode
                    val isCampaignHere = d.code == activeCampaignCode

                    if (isCampaignHere) {
                        drawCircle(
                            color = RedlineCrimson.copy(alpha = 0.35f),
                            radius = pulseRadius,
                            center = center
                        )
                        drawCircle(
                            color = RedlineCrimson,
                            radius = pulseRadius * 0.85f,
                            center = center,
                            style = Stroke(width = 2.5f)
                        )
                    }

                    val nodeRadius = if (isSelected) 19f else 14f
                    drawCircle(
                        color = if (inFilter) baseColor.copy(alpha = 0.88f) else baseColor.copy(alpha = 0.22f),
                        radius = nodeRadius,
                        center = center
                    )
                    if (isSelected) {
                        drawCircle(
                            color = Color.White,
                            radius = nodeRadius + 4f,
                            center = center,
                            style = Stroke(width = 3f)
                        )
                    }

                    val codeText = textMeasurer.measure(
                        text = d.code,
                        style = TextStyle(
                            color = if (inFilter) Color.Black else Color.Gray,
                            fontSize = if (isSelected) 12.sp else 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    drawText(
                        textLayoutResult = codeText,
                        topLeft = Offset(
                            center.x - codeText.size.width / 2f,
                            center.y - codeText.size.height / 2f
                        )
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Toque em qualquer código A–X no mapa para inspecionar locais, gangues e segurança. Pontes em âmbar • Pulso vermelho = campanha atual.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DistrictDossierCard(
    district: AtlasDistrictSpec,
    isCampaignHere: Boolean,
    hasCampaign: Boolean,
    onSelectNeighbor: (String) -> Unit,
    onMoveHere: () -> Unit,
    onRollHook: () -> Unit
) {
    val accent = zoneColor(district.zone)
    CyberPanel(accentColor = accent) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${district.code} • ${district.namePt}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = accent
                )
                Text(
                    text = district.zone.labelPt,
                    style = MaterialTheme.typography.labelMedium,
                    color = CyberAmber
                )
            }
            if (isCampaignHere) {
                CyberBadge(text = "CAMPANHA AQUI", color = RedlineCrimson)
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(district.summaryPt, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Segurança: ${district.securityProvider}" +
                (district.cityManager?.let { " • Gestor: $it" } ?: " • Sem gestor municipal"),
            style = MaterialTheme.typography.labelMedium,
            color = CyberCyan
        )
        if (district.gangsPresent.isNotEmpty()) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Gangues: ${district.gangsPresent.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = RedlineCrimson
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onMoveHere,
                enabled = hasCampaign && !isCampaignHere,
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                modifier = Modifier
                    .weight(1f)
                    .testTag("atlas_move_campaign_button")
            ) {
                Icon(Icons.Filled.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(if (isCampaignHere) "Aqui" else "Mover p/ ${district.code}", color = Color.Black)
            }
            OutlinedButton(
                onClick = onRollHook,
                enabled = hasCampaign,
                modifier = Modifier
                    .weight(1f)
                    .testTag("atlas_roll_hook_button")
            ) {
                Icon(Icons.Filled.Explore, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Rolar Local")
            }
        }
        if (district.neighborCodes.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text("Distritos Vizinhos:", style = MaterialTheme.typography.labelSmall)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                district.neighborCodes.forEach { nCode ->
                    FilterChip(
                        selected = false,
                        onClick = { onSelectNeighbor(nCode) },
                        label = { Text("Ir p/ $nCode") }
                    )
                }
            }
        }
        if (district.locations.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "LOCAIS CODIFICADOS (${district.locations.size})",
                style = MaterialTheme.typography.labelLarge,
                color = accent
            )
            Spacer(Modifier.height(4.dp))
            district.locations.forEach { loc ->
                Text(
                    text = "• ${loc.code} — ${loc.name}" + if (loc.notePt.isNotBlank()) ": ${loc.notePt}" else "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private fun zoneColor(zone: AtlasZoneId): Color = when (zone) {
    AtlasZoneId.ISLAND -> CyberCyan
    AtlasZoneId.NORTHSIDE -> CyberEmerald
    AtlasZoneId.MAINLAND -> CyberAmber
    AtlasZoneId.SOUTHSIDE -> RedlineCrimson
}
