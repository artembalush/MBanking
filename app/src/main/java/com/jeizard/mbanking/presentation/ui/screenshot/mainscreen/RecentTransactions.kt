package com.jeizard.mbanking.presentation.ui.screenshot.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jeizard.mbanking.utils.navigation.NavigationItem
import com.jeizard.mbanking.presentation.ui.screenshot.general.TransactionItem
import com.jeizard.mbanking.presentation.ui.screenshot.general.models.TransactionsModels
import com.jeizard.mbanking.presentation.ui.theme.DarkGrey
import com.jeizard.mbanking.presentation.ui.theme.MBankingTheme

@Composable
fun RecentTransactions(navController: NavHostController, viewModel: TransactionsModels = viewModel()) {
    val transactions by viewModel.transactions.collectAsState()

    viewModel.filterTransactions("", "")

    MBankingTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "VIEW ALL",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { navController.navigate(NavigationItem.AllTransactions.route) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkGrey)
            ) {
                LazyColumn {
                    items(transactions.take(5)) { transaction ->
                        TransactionItem(transaction, onClick = {
                            viewModel.selectTransaction(transaction)
                            navController.navigate(NavigationItem.Transaction.route)
                        })
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RecentTransactionsSectionPreview() {
    MBankingTheme {
        RecentTransactions(rememberNavController())
    }
}
