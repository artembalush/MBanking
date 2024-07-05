package com.jeizard.mbanking.presentation.ui.screenshot.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun LabeledTextField(
    label: String,
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int = 20
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength && isValidInput(newValue, keyboardType)) {
                    onValueChange(newValue)
                }
            },
            placeholder = { Text(text = placeholder) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                disabledBorderColor = Color.Gray,
                disabledTextColor = Color.Gray
            ),
            visualTransformation = visualTransformation,
            singleLine = true
        )
    }
}

private fun isValidInput(input: String, keyboardType: KeyboardType): Boolean {
    return when (keyboardType) {
        KeyboardType.Number, KeyboardType.Decimal -> {
            val decimalCount = input.count { it == '.' }
            decimalCount <= 1 && input.replace(".", "").all { it.isDigit() }
        }
        else -> true
    }
}
