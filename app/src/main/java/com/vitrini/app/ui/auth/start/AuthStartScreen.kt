package com.vitrini.app.ui.auth.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitrini.app.ui.components.VitriniButton
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.theme.VitriniCream

@Composable
fun AuthStartScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VitriniCream)
            .padding(horizontal = 32.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Column {
            VitriniButton(
                text = "Log In",
                onClick = onLoginClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            VitriniButton(
                text = "Register",
                onClick = onRegisterClick
            )
        }

        VitriniLogoFooter()
    }
}