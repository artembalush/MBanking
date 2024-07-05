package com.jeizard.mbanking.presentation.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val CustomDarkColorScheme = darkColorScheme(
    background = Color.Black,
    primary = Color.White,
    secondary = LightGrey60,
    tertiary = LightGrey30,
    primaryContainer = Blue
)
@Composable
fun MBankingTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CustomDarkColorScheme,
        typography = Typography,
        content = content
    )
}
