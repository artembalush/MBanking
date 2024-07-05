package com.jeizard.mbanking.presentation.ui.screenshot.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeizard.mbanking.utils.models.Transaction
import com.jeizard.mbanking.utils.models.TransactionStatus
import com.jeizard.mbanking.presentation.ui.theme.DarkGrey
import com.jeizard.mbanking.presentation.ui.theme.ExecutedColor
import com.jeizard.mbanking.presentation.ui.theme.DeclinedColor
import com.jeizard.mbanking.presentation.ui.theme.InProgressColor
import com.jeizard.mbanking.presentation.ui.theme.MBankingTheme

@Composable
fun TransactionItem(transaction: Transaction, onClick: () -> Unit) {
    val statusColor = when (transaction.status) {
        TransactionStatus.Executed -> ExecutedColor
        TransactionStatus.Declined -> DeclinedColor
        TransactionStatus.InProgress -> InProgressColor
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ onClick() },
        colors = CardDefaults.cardColors(containerColor = DarkGrey),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.company,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
                Text(
                    text = transaction.date,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
                Text(
                    text = transaction.status.name,
                    color = statusColor,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }
            Text(
                text = "$" + transaction.amount,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.Top)
            )
            Image(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "ArrowRightIcon",
                contentScale = ContentScale.None,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.tertiary),
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(20.dp)
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.tertiary,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionItemPreview() {
    MBankingTheme {
        TransactionItem(
            Transaction(
                accountId = 1,
                company = "OOO \"Company\"",
                number = "f4345jfshjek3454",
                date = "06.06.2024",
                amount = "$10.09",
                status = TransactionStatus.Executed
            ),
            {}
        )
    }
}