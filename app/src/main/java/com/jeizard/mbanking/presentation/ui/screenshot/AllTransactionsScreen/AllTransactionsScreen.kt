package com.jeizard.mbanking.presentation.ui.screenshot.AllTransactionsScreen

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jeizard.mbanking.R
import com.jeizard.mbanking.presentation.ui.screenshot.general.TransactionItem
import com.jeizard.mbanking.presentation.ui.screenshot.general.models.TransactionsModels
import com.jeizard.mbanking.presentation.ui.theme.DarkGrey
import com.jeizard.mbanking.presentation.ui.theme.MBankingTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeizard.mbanking.utils.navigation.NavigationItem
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTransactionsScreen(navController: NavHostController, viewModel: TransactionsModels = viewModel()) {
    val transactions by viewModel.transactions.collectAsState()
    var filterStartDate: String  = ""
    var filterEndDate: String  = ""

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = true,
            initialValue = SheetValue.Hidden
        )
    )
    val scope = rememberCoroutineScope()

    BackHandler(onBack = {
        if (scaffoldState.bottomSheetState.isVisible) {
            scope.launch {
                scaffoldState.bottomSheetState.hide()
            }
        } else {
            navController.navigateUp()
        }
    })

    MBankingTheme {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "All Transactions",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Rounded.KeyboardArrowLeft, "ArrowLeftIcon")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                if (scaffoldState.bottomSheetState.isVisible) {
                                    scaffoldState.bottomSheetState.hide()
                                } else {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        }) {
                            Icon(painter = painterResource(R.drawable.filter), "FilterIcon")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                )
            },
            sheetContent = {
                FilterByDateSection(
                    initialStartDate = filterStartDate,
                    initialEndDate = filterEndDate,
                    onSubmit = { startDate, endDate ->
                        filterStartDate = startDate
                        filterEndDate = endDate
                        viewModel.filterTransactions(filterStartDate, filterEndDate)
                        scope.launch {
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                )
            },
            sheetContainerColor = MaterialTheme.colorScheme.background,
            content = { paddingValues ->
                Box(modifier = Modifier.fillMaxSize()){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues)
                            .padding(start = 16.dp, end = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkGrey)
                    ) {
                        LazyColumn {
                            items(transactions) { transaction ->
                                TransactionItem(transaction, onClick = {
                                    viewModel.selectTransaction(transaction)
                                    navController.navigate(NavigationItem.Transaction.route)
                                })
                            }
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun AllTransactionsScreenPreview() {
    MBankingTheme {
        AllTransactionsScreen(rememberNavController())
    }
}