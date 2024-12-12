package com.example.inspend.data

data class Transaction(
    val id: String = "",
    val amount: String = "",
    val paymentMethod: String = "",
    val isCredit: Boolean = false,
    val name: String = "",
    val type: String = "",
    val category: String = "",
    val createdAt: Long = 0L
) 