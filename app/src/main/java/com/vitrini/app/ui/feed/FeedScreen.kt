package com.vitrini.app.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.theme.VitriniCream
import com.vitrini.app.ui.theme.VitriniText

@Composable
fun FeedScreen(
    userName: String?,
    userEmailOrId: String?,
    businessName: String?,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VitriniCream)
            .padding(24.dp)
    ) {


        Text(
            text = "Me",
            fontSize = 26.sp,
            color = VitriniText
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Nombre: ${userName ?: "No disponible"}", color = VitriniText, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Correo / ID: ${userEmailOrId ?: "No disponible"}", color = VitriniText, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Emprendimiento: ${businessName ?: "Aún no registrado"}", color = VitriniText, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Cerrar sesión")
        }
    }
}