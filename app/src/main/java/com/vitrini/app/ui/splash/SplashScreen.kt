package com.vitrini.app.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.theme.VitriniCream
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2500)
        onSplashFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VitriniCream),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VitriniLogoFooter(showSlogan = true)
    }
}