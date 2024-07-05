package com.jeizard.mbanking.presentation.ui.screenshot.general.models
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeizard.mbanking.utils.models.Account
import com.jeizard.mbanking.utils.models.Transaction
import com.jeizard.mbanking.utils.models.TransactionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
class TransactionsModels : ViewModel() {
    private val allTransactionsByAccount = mutableMapOf<Long, MutableList<Transaction>>()
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()
    private val _selectedTransaction = MutableStateFlow<Transaction?>(null)
    val selectedTransaction: StateFlow<Transaction?> = _selectedTransaction.asStateFlow()
    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()
    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()
    private val sampleTransactions = mutableListOf(
        Transaction(
            accountId = 1,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3454",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 1,
            company = "OOO \"Company2\"",
            number = "f4345jfshjek3453",
            date = "02.06.2024",
            amount = "10.09",
            status = TransactionStatus.Declined
        ),
        Transaction(
            accountId = 1,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3452",
            date = "06.06.2024",
            amount = "10.09",
            status = TransactionStatus.InProgress
        ),
        Transaction(
            accountId = 1,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3451",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 1,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 1,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 1,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 2,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 2,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 2,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 3,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 3,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        ),
        Transaction(
            accountId = 3,
            company = "OOO \"Company\"",
            number = "f4345jfshjek3450",
            date = "01.06.2024",
            amount = "10.09",
            status = TransactionStatus.Executed
        )
    )
    init {
        loadAccounts()
    }
    private fun loadAccounts() {
        viewModelScope.launch {
            val sampleAccounts = listOf(
                Account(
                    id = 1,
                    name = "My first account",
                    number = "912112192291221",
                    card = "•••• 1234"
                ),
                Account(
                    id = 2,
                    name = "For travelling",
                    number = "912112192291221",
                    card = "•••• 1234"
                ),
                Account(
                    id = 3,
                    name = "Saving Account",
                    number = "912112192291221",
                    card = "•••• 1234"
                )
            )
            _accounts.emit(sampleAccounts)
            selectAccount(sampleAccounts.first())
        }
    }
    private fun loadTransactions(accountId: Long) {
        viewModelScope.launch {
            sampleTransactions
                .groupBy { it.accountId }
                .forEach { (accountId, transactions) ->
                    allTransactionsByAccount[accountId] = transactions.toMutableList()
                }
            _transactions.emit(allTransactionsByAccount[accountId] ?: emptyList())
            sortTransactionsByDate()
        }
    }
    fun filterTransactions(startDate: String, endDate: String) {
        viewModelScope.launch {
            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val startDateParsed = dateFormat.parse(startDate)?.time
                val endDateParsed = dateFormat.parse(endDate)?.time

                _transactions.update {
                    val accountTransactions = allTransactionsByAccount[selectedAccount.value?.id] ?: emptyList()
                    accountTransactions.filter { transaction ->
                        val transactionDate = dateFormat.parse(transaction.date)?.time
                        transactionDate != null && startDateParsed != null && endDateParsed != null &&
                                transactionDate in startDateParsed..endDateParsed
                    }
                }
            } else {
                _transactions.update { allTransactionsByAccount[selectedAccount.value?.id] ?: emptyList() }
            }
        }
    }
    fun selectAccount(account: Account?) {
        viewModelScope.launch {
            _selectedAccount.update { account }
            loadTransactions(account?.id ?: 0)
        }
    }
    fun selectTransaction(transaction: Transaction?) {
        viewModelScope.launch {
            _selectedTransaction.emit(transaction)
        }
    }
    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            sampleTransactions.add(transaction)
            val accountTransactions = allTransactionsByAccount[transaction.accountId] ?: mutableListOf()
            accountTransactions.add(transaction)
            sortTransactionsByDate()
            allTransactionsByAccount[transaction.accountId] = accountTransactions
            if (transaction.accountId == selectedAccount.value?.id) {
                _transactions.update { accountTransactions.toList() }
            }
        }
    }
    private fun sortTransactionsByDate() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        allTransactionsByAccount.forEach { (_, transactions) ->
            transactions.sortByDescending {
                dateFormat.parse(it.date)?.time ?: 0
            }
        }
    }
}
