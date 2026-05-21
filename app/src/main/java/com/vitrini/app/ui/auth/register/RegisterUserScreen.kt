package com.vitrini.app.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.components.VitriniButton
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.components.VitriniTextField
import com.vitrini.app.ui.theme.VitriniCream
import com.vitrini.app.ui.theme.VitriniText

@Composable
fun RegisterUserScreen(onRegister: (String, String, String, String) -> String?) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf<String?>(null) }
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().background(VitriniCream).padding(horizontal = 32.dp, vertical = 36.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = "Register User", fontSize = 32.sp, color = VitriniText)

            Spacer(modifier = Modifier.height(24.dp))

            VitriniTextField(value = fullName, onValueChange = { fullName = it; fullNameError = null; generalError = null }, placeholder = "Full Name", errorMessage = fullNameError)

            Spacer(modifier = Modifier.height(14.dp))

            VitriniTextField(value = email, onValueChange = { email = it; emailError = null; generalError = null }, placeholder = "Email", errorMessage = emailError)

            Spacer(modifier = Modifier.height(14.dp))

            VitriniTextField(value = password, onValueChange = { password = it; passwordError = null; generalError = null }, placeholder = "Password", errorMessage = passwordError, visualTransformation = PasswordVisualTransformation())

            Spacer(modifier = Modifier.height(14.dp))

            VitriniTextField(value = confirmPassword, onValueChange = { confirmPassword = it; confirmPasswordError = null; generalError = null }, placeholder = "Confirm Password", errorMessage = confirmPasswordError, visualTransformation = PasswordVisualTransformation())
            generalError?.let { Spacer(modifier = Modifier.height(12.dp)); Text(text = it, color = androidx.compose.ui.graphics.Color.Red) }

            Spacer(modifier = Modifier.height(24.dp))

            VitriniButton(text = "Register", onClick = {
                fullNameError = if (fullName.isBlank()) "Full name is required" else null
                emailError = validateEmail(email)
                passwordError = if (password.isBlank()) "Password is required" else null
                confirmPasswordError = when {
                    confirmPassword.isBlank() -> "Confirm password is required"; password != confirmPassword -> "Passwords do not match"; else -> null
                }
                if (fullNameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
                    generalError =
                        onRegister(fullName.trim(), email.trim(), password, confirmPassword)
                }
            })
        }

        Spacer(modifier = Modifier.height(40.dp)); VitriniLogoFooter()
    }
}

private fun validateEmail(email: String): String? = when {
    email.isBlank() -> "Email is required"
    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
    else -> null
}