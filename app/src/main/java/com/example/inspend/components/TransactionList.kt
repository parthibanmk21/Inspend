package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.TransactionData
import com.example.inspend.ui.theme.*

@Composable
fun TransactionList(
    date: String,
    transactions: List<TransactionData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E2EB),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Title
        Text(
            text = "Transaction",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF394371),
            lineHeight = 18.sp
        )

        // Transaction List Column
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Date
            Text(
                text = date,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Grey300,
                lineHeight = 14.sp
            )

            // Transaction Cards
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                transactions.forEach { transaction ->
                    TransactionListCard(
                        title = transaction.name,
                        time = transaction.dateTime,
                        amount = transaction.amount,
                        isIncome = transaction.isCredit,
                        bankType = transaction.paymentMethod,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionListPreview() {
    val sampleTransactions = listOf(
        TransactionData(
            type = "Other Transaction",
            name = "Salary",
            dateTime = "10:00 AM",
            amount = "5,000",
            paymentMethod = "TRUST",
            isCredit = true,
            transactionType = "INCOME"
        ),
        TransactionData(
            type = "Other Transaction",
            name = "Groceries",
            dateTime = "2:30 PM",
            amount = "150",
            paymentMethod = "REVOLUT",
            isCredit = false,
            transactionType = "EXPENSE"
        )
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E2EB),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        TransactionList(
            date = "Today",
            transactions = sampleTransactions
        )
    }
} 