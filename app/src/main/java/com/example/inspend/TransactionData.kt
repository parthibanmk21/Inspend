package com.example.inspend

data class TransactionData(
    val type: String = "",            // "Opening Balance" or "Other Transaction"
    val category: String? = null,     // Only for "Other Transaction"
    val name: String = "",
    val dateTime: String = "",
    val amount: String = "",
    val paymentMethod: String = "",
    val isCredit: Boolean = true,
    val transactionType: String = "",  // "INCOME" or "EXPENSE"
    val categoryType: String = ""      // Category type for icon display
) 