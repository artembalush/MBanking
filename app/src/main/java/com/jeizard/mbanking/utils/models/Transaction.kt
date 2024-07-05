package com.jeizard.mbanking.utils.models

data class Transaction(
    val company: String,
    val number: String,
    val date: String,
    val amount: String,
    val status: TransactionStatus,
    val accountId: Long
)
