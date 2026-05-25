package com.vitrini.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vitrini.app.ui.theme.VitriniFieldBackground
import com.vitrini.app.ui.theme.VitriniMauve
import com.vitrini.app.ui.theme.VitriniPlaceholder

@Composable
fun VitriniTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = VitriniPlaceholder) },
        isError = errorMessage != null,
        supportingText = { if (errorMessage != null) Text(text = errorMessage) },
        visualTransformation = visualTransformation,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .background(VitriniFieldBackground, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = VitriniFieldBackground,
            unfocusedContainerColor = VitriniFieldBackground,
            errorContainerColor = VitriniFieldBackground,
            focusedIndicatorColor = VitriniMauve,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedTextColor = VitriniMauve,
            focusedTextColor = VitriniMauve,
            cursorColor = VitriniMauve

        )
    )
}