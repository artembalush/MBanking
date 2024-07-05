package com.jeizard.mbanking.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jeizard.mbanking.utils.navigation.AppNavHost
import com.jeizard.mbanking.presentation.ui.theme.MBankingTheme
import com.jeizard.mbanking.presentation.ui.screenshot.general.models.TransactionsModels

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val transactionViewModel: TransactionsModels by viewModels()
        setContent {
            MBankingTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(navController = rememberNavController(), transactionViewModel = transactionViewModel)
                }
            }
        }
    }
}
