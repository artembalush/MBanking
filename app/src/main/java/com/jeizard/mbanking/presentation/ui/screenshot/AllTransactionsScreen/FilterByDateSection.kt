package com.jeizard.mbanking.presentation.ui.screenshot.AllTransactionsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeizard.mbanking.presentation.ui.screenshot.general.DateTextBox
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilterByDateSection(
    initialStartDate: String,
    initialEndDate: String,
    onSubmit: (String, String) -> Unit
) {
    var startDate by remember { mutableStateOf(initialStartDate) }
    var endDate by remember { mutableStateOf(initialEndDate) }
    var startDateError by remember { mutableStateOf(false) }
    var endDateError by remember { mutableStateOf(false) }
    var startDateCalendarVisible by remember { mutableStateOf(false) }
    var endDateCalendarVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Filter by date",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        DateTextBox(
            label = "Start date",
            placeholder = "Select start date",
            value = startDate,
            onValueChange = { startDate = it },
            onDateSelected = {
                date -> startDate = date
                startDateError = false
            },
            error = startDateError,
            onCalendarVisibilityChanged = { startDateCalendarVisible = it },
            enabled = true
        )
        DateTextBox(
            label = "End date",
            placeholder = "Select end date",
            value = endDate,
            onValueChange = { endDate = it },
            onDateSelected = {
                date -> endDate = date
                endDateError = false
            },
            error = endDateError,
            onCalendarVisibilityChanged = { endDateCalendarVisible = it },
            enabled = true
        )
        if (startDateCalendarVisible) {
            Spacer(modifier = Modifier.height(220.dp))
        }
        if (endDateCalendarVisible) {
            Spacer(modifier = Modifier.height(320.dp))
        }
        Divider(
            color = MaterialTheme.colorScheme.tertiary,
            thickness = 0.5.dp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                startDateError = startDate.isEmpty()
                endDateError = endDate.isEmpty() ||
                        (endDate.isNotEmpty() &&
                                startDate.isNotEmpty() &&
                                (SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(
                                    endDate
                                )
                                ?.time ?: 0) <
                                (SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(
                                    startDate
                                )
                                ?.time ?: 0))
                if(!startDateError && !endDateError){
                    onSubmit(startDate, endDate)
                }
            },
            enabled = true,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Submit",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

