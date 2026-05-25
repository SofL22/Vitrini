package com.vitrini.app.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.theme.VitriniBackgroundMain
import com.vitrini.app.ui.theme.VitriniMauve


private enum class MainSection {
    FEED,
    ME
}
@Composable
fun FeedScreen(
    userName: String?,
    userEmailOrId: String?,
    businessName: String?,
    onLogout: () -> Unit
) {
    var selectedSection by remember { mutableStateOf(MainSection.FEED) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VitriniBackgroundMain)
            .padding(24.dp)
    ) {
        when (selectedSection) {
            MainSection.FEED -> FeedContent()
            MainSection.ME -> MeContent(
                userName = userName,
                userEmailOrId = userEmailOrId,
                businessName = businessName,
                onLogout = onLogout
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        MainNavigationBar(
            selectedSection = selectedSection,
            onFeedClick = { selectedSection = MainSection.FEED },
            onMeClick = { selectedSection = MainSection.ME }
        )
    }
}

@Composable
private fun FeedContent() {
    Text(
        text = "Feed",
        fontSize = 26.sp,
        color = VitriniMauve
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Bienvenido al feed principal de Vitrini.",
        color = VitriniMauve,
        fontSize = 18.sp
    )
}

@Composable
private fun MeContent(
    userName: String?,
    userEmailOrId: String?,
    businessName: String?,
    onLogout: () -> Unit
) {
    Text(
        text = "Me",
        fontSize = 26.sp,
        color = VitriniMauve
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(text = "Nombre: ${userName ?: "No disponible"}", color = VitriniMauve, fontSize = 18.sp)
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = "Correo / ID: ${userEmailOrId ?: "No disponible"}", color = VitriniMauve, fontSize = 18.sp)
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = "Emprendimiento: ${businessName ?: "Aún no registrado"}", color = VitriniMauve, fontSize = 18.sp)
    Spacer(modifier = Modifier.height(24.dp))

    Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
        Text("Cerrar sesión")
    }
}

@Composable
private fun MainNavigationBar(
    selectedSection: MainSection,
    onFeedClick: () -> Unit,
    onMeClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onFeedClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedSection == MainSection.FEED) VitriniMauve else VitriniMauve.copy(
                    alpha = 0.5f
                )
            )
        ) {
            Text("Feed")
        }
        Button(
            onClick = onMeClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedSection == MainSection.ME) VitriniMauve else VitriniMauve.copy(
                    alpha = 0.5f
                )
            )
        ) {
            Text("Me")
        }
    }
}