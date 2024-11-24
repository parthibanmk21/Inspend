package com.example.inspend.data

import com.google.firebase.Timestamp

data class Transaction(
    val type: String = "",            
    val category: String? = null,     
    val amount: String = "",          
    val paymentMethod: String = "",   
    val createdAt: Timestamp = Timestamp.now(), 
    val isCredit: Boolean = true,    
    val name: String = "",           
    val transactionType: String = "" 
) 