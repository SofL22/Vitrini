package com.vitrini.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val VitriniBackgroundMain = Color(0xFFFEF9F9)
val VitriniBackgroundSecondary = Color(0xFFFAF0EF)
val VitriniCard = Color(0xFFFFFFFF)
val VitriniCardShadow = Color(0xFFD8C4C2)
val VitriniFieldBackground = Color(0xFFF3EFED)
val VitriniPlaceholder = Color(0xFFAAA3A4)
val VitriniTitle = Color(0xFF9C686F)
val VitriniMauve = Color(0xFF97656D)
val VitriniLink = Color(0xFF7EA7B0)
val VitriniLogo = Color(0xFFFBB0C2)

private val VitriniLightColors = lightColorScheme(
    primary = VitriniMauve,
    secondary = VitriniLink,
    background = VitriniBackgroundMain,
    surface = VitriniCard,
    onPrimary = Color.White,
    onSecondary = VitriniTitle,
    onBackground = VitriniTitle,
    onSurface = VitriniTitle
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