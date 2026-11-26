package com.example.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val RedlineDarkColorScheme = darkColorScheme(
    primary = RedlineCrimson,
    onPrimary = Color.White,
    primaryContainer = RedlineCrimsonContainer,
    onPrimaryContainer = Color(0xFFFFD9DE),
    secondary = CyberCyan,
    onSecondary = Color(0xFF002026),
    secondaryContainer = CyberCyanContainer,
    onSecondaryContainer = Color(0xFFB8F5FF),
    tertiary = CyberAmber,
    onTertiary = Color(0xFF281A00),
    tertiaryContainer = CyberAmberContainer,
    onTertiaryContainer = Color(0xFFFFE09E),
    background = CyberVoid,
    onBackground = TextPrimary,
    surface = CyberSurface,
    onSurface = TextPrimary,
    surfaceVariant = CyberSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    surfaceContainerLowest = CyberVoid,
    surfaceContainerLow = CyberSurface,
    surfaceContainer = CyberSurfaceElevated,
    surfaceContainerHigh = CyberSurfaceHighlight,
    outline = CyberOutline,
    outlineVariant = CyberOutlineMuted,
    error = RedlineCrimson,
    onError = Color.White,
    errorContainer = RedlineCrimsonContainer,
    onErrorContainer = Color(0xFFFFD9DE)
)

val RedlineShapes = Shapes(
    extraSmall = CutCornerShape(topStart = 4.dp, bottomEnd = 4.dp),
    small = CutCornerShape(topStart = 6.dp, bottomEnd = 6.dp),
    medium = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
    large = CutCornerShape(topStart = 14.dp, bottomEnd = 14.dp),
    extraLarge = CutCornerShape(topStart = 18.dp, bottomEnd = 18.dp)
)

@Composable
fun RedlineTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RedlineDarkColorScheme,
        typography = RedlineTypography,
        shapes = RedlineShapes,
        content = content
    )
}
