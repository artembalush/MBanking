package com.jeizard.mbanking.presentation.ui.screenshot.transaction

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeizard.mbanking.utils.models.TransactionStatus

@Composable
fun StatusDropdownMenu(
    label: String,
    status: TransactionStatus,
    onStatusSelected: (TransactionStatus) -> Unit,
    enabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(status.name) }

    val color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.tertiary

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, color, RoundedCornerShape(8.dp))
            .clickable(enabled) { expanded = !expanded }
        ) {
            Text(
                text = selectedText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineSmall,
                color = color
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = "ArrowRightIcon",
                tint = color,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            TransactionStatus.entries.forEach { status ->
                DropdownMenuItem(
                    text = { Text(text = status.name) },
                    onClick = {
                        selectedText = status.name
                        onStatusSelected(status)
                        expanded = false
                    })
            }
        }
    }
}