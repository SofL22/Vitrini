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
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.components.VitriniButton
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.components.VitriniTextField
import com.vitrini.app.ui.theme.VitriniCream
import com.vitrini.app.ui.theme.VitriniText

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
        modifier = Modifier
            .fillMaxSize()
            .background(VitriniCream)
            .padding(horizontal = 32.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Text(text = "Log In", fontSize = 34.sp, color = VitriniText)
            Spacer(modifier = Modifier.height(32.dp))

            VitriniTextField(value = email, onValueChange = { email = it; emailError = null; loginError = null }, placeholder = "Email", errorMessage = emailError)

            Spacer(modifier = Modifier.height(16.dp))

            VitriniTextField(value = password, onValueChange = { password = it; passwordError = null; loginError = null }, placeholder = "Password", errorMessage = passwordError, visualTransformation = PasswordVisualTransformation())

            loginError?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
            }

            Spacer(modifier = Modifier.height(28.dp))

            VitriniButton(text = "Log In", onClick = {
                emailError = validateEmail(email)
                passwordError = if (password.isBlank()) "Password is required" else null

                if (emailError == null && passwordError == null) {
                    loginError = onLoginClick(email.trim(), password)
                }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Don’t have an account?")
                TextButton(onClick = onRegisterClick, modifier = Modifier.wrapContentWidth()) {
                    Text(text = "Register")
                }
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