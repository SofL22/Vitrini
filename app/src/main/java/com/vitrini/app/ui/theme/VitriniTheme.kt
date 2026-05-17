package com.vitrini.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val VitriniCream = Color(0xFFFFFBF6)
val VitriniMauve = Color(0xFF8B5E66)
val VitriniSoftPink = Color(0xFFE8C8C4)
val VitriniText = Color(0xFF3D2C2E)
val VitriniFieldBackground = Color(0xFFF4E8E3)

private val VitriniLightColors = lightColorScheme(
    primary = VitriniMauve,
    secondary = VitriniSoftPink,
    background = VitriniCream,
    surface = VitriniCream,
    onPrimary = Color.White,
    onSecondary = VitriniText,
    onBackground = VitriniText,
    onSurface = VitriniText
)

@Composable
fun VitriniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = VitriniLightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}