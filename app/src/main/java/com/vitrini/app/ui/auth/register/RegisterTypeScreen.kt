package com.vitrini.app.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.components.VitriniButton
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.theme.VitriniBackgroundSecondary
import com.vitrini.app.ui.theme.VitriniCard
import com.vitrini.app.ui.theme.VitriniLink
import com.vitrini.app.ui.theme.VitriniTitle

@Composable
fun RegisterTypeScreen(onUserClick: () -> Unit, onBusinessClick: () -> Unit, onBackClick: () -> Unit, onLoginClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(VitriniBackgroundSecondary).padding(horizontal = 28.dp, vertical = 56.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = "Register", fontSize = 44.sp, color = VitriniTitle)
            Spacer(modifier = Modifier.height(24.dp))
            Card(shape = RoundedCornerShape(36.dp), colors = CardDefaults.cardColors(containerColor = VitriniCard), elevation = CardDefaults.cardElevation(defaultElevation = 14.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    VitriniButton("User", onUserClick, modifier = Modifier.width(300.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    VitriniButton("Business", onBusinessClick, modifier = Modifier.width(300.dp))
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Already have an account? ", color = VitriniLink)
                TextButton(onClick = onLoginClick) { Text("Login", color = VitriniLink) }
            }
        }

        VitriniLogoFooter()
    }
}