package com.vitrini.app.ui.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.components.VitriniButton
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.components.VitriniTextField
import com.vitrini.app.ui.theme.VitriniBackgroundSecondary
import com.vitrini.app.ui.theme.VitriniCard
import com.vitrini.app.ui.theme.VitriniCardShadow
import com.vitrini.app.ui.theme.VitriniLink
import com.vitrini.app.ui.theme.VitriniTitle

@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> String?,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var loginError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().background(VitriniBackgroundSecondary).padding(horizontal = 28.dp, vertical = 56.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Log In", fontSize = 44.sp, color = VitriniTitle)
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                shape = RoundedCornerShape(36.dp),
                colors = CardDefaults.cardColors(containerColor = VitriniCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(18.dp)) {
                    VitriniTextField(value = email, onValueChange = { email = it; emailError = null; loginError = null }, placeholder = "Username", errorMessage = emailError)
                    Spacer(modifier = Modifier.height(14.dp))
                    VitriniTextField(value = password, onValueChange = { password = it; passwordError = null; loginError = null }, placeholder = "Password", errorMessage = passwordError, visualTransformation = PasswordVisualTransformation())
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = {}, content = { Text("Forgot your password?", color = VitriniLink) })
                    }
                    loginError?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp)) }
                    VitriniButton(text = "Log In", onClick = {
                        emailError = validateEmail(email)
                        passwordError = if (password.isBlank()) "Password is required" else null
                        if (emailError == null && passwordError == null) loginError = onLoginClick(email.trim(), password)
                    }, modifier = Modifier.align(Alignment.CenterHorizontally).width(220.dp))
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text("Don’t have an account? ", color = VitriniLink)
                TextButton(onClick = onRegisterClick) { Text("Register", color = VitriniLink) }
            }
        }

        VitriniLogoFooter()
    }
}

private fun validateEmail(email: String): String? = when {
    email.isBlank() -> "Email is required"
    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
    else -> null
}