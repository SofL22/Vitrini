package com.vitrini.app.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.components.VitriniButton
import com.vitrini.app.ui.components.VitriniLogoFooter
import com.vitrini.app.ui.components.VitriniTextField
import com.vitrini.app.ui.theme.VitriniBackgroundSecondary
import com.vitrini.app.ui.theme.VitriniCard
import com.vitrini.app.ui.theme.VitriniTitle

@Composable
fun RegisterUserScreen(onRegister: (String, String, String, String) -> String?, onBackClick: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf<String?>(null) }
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().background(VitriniBackgroundSecondary).padding(horizontal = 28.dp, vertical = 40.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text("Register User", fontSize = 42.sp, color = VitriniTitle)
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                shape = RoundedCornerShape(36.dp),
                colors = CardDefaults.cardColors(containerColor = VitriniCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(18.dp)) {
                    VitriniTextField(
                        fullName,
                        { fullName = it; fullNameError = null; generalError = null },
                        "Username",
                        errorMessage = fullNameError
                    )
                    Spacer(Modifier.height(12.dp))
                    VitriniTextField(
                        email,
                        { email = it; emailError = null; generalError = null },
                        "Email",
                        errorMessage = emailError
                    )
                    Spacer(Modifier.height(12.dp))
                    VitriniTextField(
                        password,
                        { password = it; passwordError = null; generalError = null },
                        "Password",
                        errorMessage = passwordError,
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(Modifier.height(12.dp))
                    VitriniTextField(
                        confirmPassword,
                        { confirmPassword = it; confirmPasswordError = null; generalError = null },
                        "Confirm Password",
                        errorMessage = confirmPasswordError,
                        visualTransformation = PasswordVisualTransformation()
                    )
                    generalError?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Spacer(Modifier.height(18.dp))
                    VitriniButton("Register", onClick = {
                        fullNameError = if (fullName.isBlank()) "Full name is required" else null
                        emailError = validateEmail(email)
                        passwordError = if (password.isBlank()) "Password is required" else null
                        confirmPasswordError = when {
                            confirmPassword.isBlank() -> "Confirm password is required"
                            password != confirmPassword -> "Passwords do not match"
                            else -> null
                        }
                        if (fullNameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
                            generalError =
                                onRegister(fullName.trim(), email.trim(), password, confirmPassword)
                        }
                    }, modifier = Modifier.align(Alignment.CenterHorizontally).width(220.dp))
                }
            }
        }

    Spacer(modifier = Modifier.height(36.dp))
    VitriniLogoFooter()
    }
}

private fun validateEmail(email: String): String? = when {
    email.isBlank() -> "Email is required"
    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
    else -> null
}