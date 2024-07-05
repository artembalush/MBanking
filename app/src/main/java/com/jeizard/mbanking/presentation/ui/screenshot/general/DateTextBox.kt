package com.jeizard.mbanking.presentation.ui.screenshot.general

import android.os.Build
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import com.jeizard.mbanking.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTextBox(
    label: String,
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    onDateSelected: (String) -> Unit,
    error: Boolean = false,
    onCalendarVisibilityChanged: (Boolean) -> Unit,
    enabled: Boolean
) {
    var calendarVisible by remember { mutableStateOf(false) }
    var columnSize by remember { mutableStateOf(IntSize.Zero) }

    val borderColor by animateColorAsState(
        targetValue = if (error) Color.Red else MaterialTheme.colorScheme.onSurface,
        animationSpec = TweenSpec(durationMillis = 300), label = "BorderColorAnimation"
    )

    LaunchedEffect(calendarVisible) {
        onCalendarVisibilityChanged(calendarVisible)
    }

    val shakeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    if (error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                shakeOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 50)
                )
                for (i in 0 until 3) {
                    shakeOffset.animateTo(
                        targetValue = 5f,
                        animationSpec = tween(durationMillis = 50)
                    )
                    shakeOffset.animateTo(
                        targetValue = -5f,
                        animationSpec = tween(durationMillis = 50)
                    )
                }
                shakeOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 50)
                )
            }
        }
    }

    Column(
        modifier = Modifier.onGloballyPositioned {
            columnSize = it.size
        }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .offset { IntOffset(shakeOffset.value.toInt(), 0) },
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = "CalendarIcon") },
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                calendarVisible = true
                            }
                        }
                    }
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                disabledBorderColor = MaterialTheme.colorScheme.tertiary,
                disabledTextColor = MaterialTheme.colorScheme.tertiary
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled
        )

        if (calendarVisible) {
            BackHandler(onBack = {
                calendarVisible = false
            })
            Popup(
                onDismissRequest = { calendarVisible = false },
                offset = IntOffset(0, y = columnSize.height + 16)
                ) {
                AndroidView(
                    { CalendarView(ContextThemeWrapper(it, R.style.CustomCalendarViewStyle)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    update = { views ->
                        views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(year, month, dayOfMonth)
                            onDateSelected(
                                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                                    .format(selectedDate.time)
                            )
                            calendarVisible = false
                        }
                        views.maxDate = Calendar.getInstance().timeInMillis
                    }
                )
            }
        }
    }
}