package com.vitrini.app.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.components.VitriniButton
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.theme.VitriniCream
import com.vitrini.app.ui.theme.VitriniText

@Composable
fun RegisterTypeScreen(
    onUserClick: () -> Unit,
    onBusinessClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VitriniCream)
            .padding(horizontal = 32.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Register",
                fontSize = 34.sp,
                color = VitriniText
            )

            Spacer(modifier = Modifier.height(40.dp))

            VitriniButton(
                text = "User",
                onClick = onUserClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            VitriniButton(
                text = "Business",
                onClick = onBusinessClick
            )
        }

        VitriniLogoFooter()
    }
}