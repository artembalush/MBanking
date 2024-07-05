package com.jeizard.mbanking.utils.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jeizard.mbanking.presentation.ui.screenshot.AllTransactionsScreen.AllTransactionsScreen
import com.jeizard.mbanking.presentation.ui.screenshot.general.models.TransactionsModels
import com.jeizard.mbanking.presentation.ui.screenshot.mainscreen.MainScreen
import com.jeizard.mbanking.presentation.ui.screenshot.transaction.TransactionScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Main.route,
    transactionViewModel: TransactionsModels
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Main.route) {
            MainScreen(navController = navController, viewModel = transactionViewModel)
        }
        composable(NavigationItem.AllTransactions.route) {
            AllTransactionsScreen(navController = navController, viewModel = transactionViewModel)
        }
        composable(NavigationItem.Transaction.route) {
            TransactionScreen(navController = navController, viewModel = transactionViewModel)
        }
    }
}