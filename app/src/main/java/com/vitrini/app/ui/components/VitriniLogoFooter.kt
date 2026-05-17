package com.vitrini.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.theme.VitriniMauve

@Composable
fun VitriniLogoFooter(
    modifier: Modifier = Modifier,
    showSlogan: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "VITRINI",
            color = VitriniMauve,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        if (showSlogan) {
            Text(
                text = "conectando usuarios con emprendimientos locales",
                color = VitriniMauve,
                fontSize = 13.sp
            )
        }
    }
}