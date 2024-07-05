package com.jeizard.mbanking.utils.navigation

enum class Screen {
    MAIN,
    ALLTRANSACTIONS,
    TRANSACTION
}
sealed class NavigationItem(val route: String) {
    data object Main : NavigationItem(Screen.MAIN.name)
    data object AllTransactions : NavigationItem(Screen.ALLTRANSACTIONS.name)
    data object Transaction : NavigationItem(Screen.TRANSACTION.name)
}