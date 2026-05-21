package com.vitrini.app.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun RegisterBusinessScreen(
    onRegister: (String, String, String, String, String) -> String?,
    onBackClick: () -> Unit
) {
    var ownerName by remember { mutableStateOf("") }
    var businessName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf<String?>(null) }
    var ownerNameError by remember { mutableStateOf<String?>(null) }
    var businessNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().background(VitriniCream).padding(horizontal = 32.dp, vertical = 36.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(text = "Register Business", fontSize = 32.sp, color = VitriniText)

            Spacer(modifier = Modifier.height(24.dp))

            VitriniTextField(value = ownerName, onValueChange = { ownerName = it; ownerNameError = null; generalError = null }, placeholder = "Owner Full Name", errorMessage = ownerNameError)

            Spacer(modifier = Modifier.height(14.dp))

            VitriniTextField(value = businessName, onValueChange = { businessName = it; businessNameError = null; generalError = null }, placeholder = "Business Name", errorMessage = businessNameError)

            Spacer(modifier = Modifier.height(14.dp))

            VitriniTextField(value = email, onValueChange = { email = it; emailError = null; generalError = null }, placeholder = "Email", errorMessage = emailError)

            Spacer(modifier = Modifier.height(14.dp))

            VitriniTextField(value = password, onValueChange = { password = it; passwordError = null; generalError = null }, placeholder = "Password", errorMessage = passwordError, visualTransformation = PasswordVisualTransformation())

            Spacer(modifier = Modifier.height(14.dp))

            VitriniTextField(value = confirmPassword, onValueChange = { confirmPassword = it; confirmPasswordError = null; generalError = null }, placeholder = "Confirm Password", errorMessage = confirmPasswordError, visualTransformation = PasswordVisualTransformation())
            generalError?.let { Spacer(modifier = Modifier.height(12.dp)); Text(text = it, color = androidx.compose.ui.graphics.Color.Red) }

            Spacer(modifier = Modifier.height(24.dp))

            VitriniButton(text = "Register", onClick = {
                ownerNameError = if (ownerName.isBlank()) "Owner full name is required" else null
                businessNameError =
                    if (businessName.isBlank()) "Business name is required" else null
                emailError = validateEmail(email)
                passwordError = if (password.isBlank()) "Password is required" else null
                confirmPasswordError = when {
                    confirmPassword.isBlank() -> "Confirm password is required"; password != confirmPassword -> "Passwords do not match"; else -> null
                }
                if (ownerNameError == null && businessNameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
                    generalError = onRegister(
                        ownerName.trim(),
                        businessName.trim(),
                        email.trim(),
                        password,
                        confirmPassword
                    )
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