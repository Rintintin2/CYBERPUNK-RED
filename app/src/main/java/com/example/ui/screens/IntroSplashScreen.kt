package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.CyberVoid
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.RedlineCrimson
import com.example.ui.theme.SpaceGroteskFontFamily
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * Seção 6.1 — Tela de Introdução / Abertura CYBERPUNK RED.
 * Sequência rápida inspirada na estética de Cyberpunk 2077:
 * 1. Tela preta (#090B10)
 * 2. Ruídos digitais, scanlines e interferências leves
 * 3. Logotipo CYBERPUNK RED com efeito glitch cibernético
 * 4. Estabilização breve
 * 5. "BEM-VINDO A NIGHT CITY"
 * 6. Indicação "TOQUE NA TELA PARA ENTRAR"
 * Toda a tela funciona como área de toque imediata, sem bloquear o usuário.
 */
@Composable
fun IntroSplashScreen(
    onEnterApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var bootStage by remember { mutableStateOf(0) }
    var entering by remember { mutableStateOf(false) }

    val logoReveal = remember { Animatable(0f) }
    val glitchStrength = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        delay(120)
        bootStage = 1 // Ruído digital + boot
        logoReveal.animateTo(1f, animationSpec = tween(durationMillis = 360, easing = FastOutSlowInEasing))
        bootStage = 2 // Glitch no logotipo
        glitchStrength.animateTo(0.15f, animationSpec = tween(durationMillis = 480, easing = FastOutSlowInEasing))
        bootStage = 3 // Estabilizado + BEM-VINDO A NIGHT CITY + toque para entrar
    }

    val infiniteTransition = rememberInfiniteTransition(label = "intro_glitch_loop")
    val scanPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanline_phase"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.42f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tap_pulse"
    )
    val microGlitch by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 190, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "micro_glitch"
    )

    val triggerEnter = {
        if (!entering) {
            entering = true
            onEnterApp()
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CyberVoid)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = triggerEnter
            )
            .semantics {
                contentDescription = "Tela de abertura Cyberpunk RED. Toque em qualquer ponto para entrar."
            }
            .testTag("intro_splash_screen")
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        // Camada de scanlines, ruídos digitais e fragmentos de interferência
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Scanlines finas horizontais
            val step = 12f
            var y = (scanPhase * step) % step
            while (y < h) {
                drawLine(
                    color = Color.White.copy(alpha = 0.035f),
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1f
                )
                y += step
            }

            // Faixa de varredura sutil
            val sweepY = scanPhase * h
            drawRect(
                color = RedlineCrimson.copy(alpha = 0.06f),
                topLeft = Offset(0f, sweepY),
                size = Size(w, 18f)
            )

            // Barras de ruído/interferência cibernética
            if (bootStage >= 1) {
                val activeGlitch = (glitchStrength.value * 0.85f + 0.15f)
                val glitchBars = listOf(
                    0.18f to 0.34f,
                    0.37f to 0.52f,
                    0.61f to 0.28f,
                    0.79f to 0.44f
                )
                glitchBars.forEachIndexed { idx, (yRatio, widthRatio) ->
                    val barY = h * yRatio + microGlitch * (idx + 2) * 3f * activeGlitch
                    val barX = if (idx % 2 == 0) w * 0.06f else w * (0.94f - widthRatio)
                    drawRect(
                        color = if (idx % 2 == 0) {
                            RedlineCrimson.copy(alpha = 0.18f * activeGlitch)
                        } else {
                            CyberCyan.copy(alpha = 0.14f * activeGlitch)
                        },
                        topLeft = Offset(barX, barY),
                        size = Size(w * widthRatio, if (idx == 1) 4f else 2f)
                    )
                }
            }
        }

        // Cabeçalho discreto de terminal Night City
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .alpha(if (bootStage >= 1) 0.9f else 0f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NC_OS // BOOT.2045",
                fontFamily = JetBrainsMonoFontFamily,
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 1.4.sp
            )
            Box(
                modifier = Modifier
                    .background(CyberSurfaceElevated, CutCornerShape(topStart = 4.dp, bottomEnd = 4.dp))
                    .border(1.dp, CyberAmber.copy(alpha = 0.45f), CutCornerShape(topStart = 4.dp, bottomEnd = 4.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "DATATERM LINK",
                    fontFamily = JetBrainsMonoFontFamily,
                    fontSize = 10.sp,
                    color = CyberAmber,
                    letterSpacing = 1.sp
                )
            }
        }

        // Logotipo CYBERPUNK RED com glitch cromático e estabilização
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .alpha(logoReveal.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val glitchOffsetPx = (microGlitch * 8f * glitchStrength.value).roundToInt()

            Box(contentAlignment = Alignment.Center) {
                // Camada fantasma ciano (aberração cromática do glitch)
                if (glitchStrength.value > 0.2f) {
                    Text(
                        text = "CYBERPUNK",
                        fontFamily = SpaceGroteskFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        letterSpacing = 4.sp,
                        color = CyberCyan.copy(alpha = 0.55f * glitchStrength.value),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.offset { IntOffset(-glitchOffsetPx - 2, 1) }
                    )
                }
                // Camada fantasma vermelha
                if (glitchStrength.value > 0.2f) {
                    Text(
                        text = "CYBERPUNK",
                        fontFamily = SpaceGroteskFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        letterSpacing = 4.sp,
                        color = RedlineCrimson.copy(alpha = 0.65f * glitchStrength.value),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.offset { IntOffset(glitchOffsetPx + 2, -1) }
                    )
                }
                // Logotipo principal legível
                Text(
                    text = "CYBERPUNK",
                    fontFamily = SpaceGroteskFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    letterSpacing = 4.sp,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(6.dp))

            // Bloco oficial "RED" em destaque com detalhes amarelos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(26.dp)
                        .height(3.dp)
                        .background(CyberAmber)
                )
                Spacer(Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = RedlineCrimson,
                            shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = CyberAmber.copy(alpha = 0.7f),
                            shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                        .offset {
                            IntOffset((microGlitch * 3f * glitchStrength.value).roundToInt(), 0)
                        }
                ) {
                    Text(
                        text = "RED",
                        fontFamily = SpaceGroteskFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        letterSpacing = 8.sp,
                        color = TextPrimary
                    )
                }
                Spacer(Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .width(26.dp)
                        .height(3.dp)
                        .background(CyberAmber)
                )
            }

            Spacer(Modifier.height(22.dp))

            AnimatedVisibility(
                visible = bootStage >= 2,
                enter = fadeIn(tween(260)) + slideInVertically(tween(260)) { it / 3 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "BEM-VINDO A NIGHT CITY",
                        fontFamily = JetBrainsMonoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 2.6.sp,
                        color = CyberAmber,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "EDGERUNNER SOLO SYSTEM // 2045",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        letterSpacing = 1.6.sp
                    )
                }
            }
        }

        // Indicação discreta inferior para toque
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
                .alpha(if (bootStage >= 1) pulseAlpha else 0f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(2.dp)
                    .background(RedlineCrimson.copy(alpha = 0.8f))
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "TOQUE NA TELA PARA ENTRAR",
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 2.2.sp,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("intro_tap_prompt")
            )
        }
    }
}
