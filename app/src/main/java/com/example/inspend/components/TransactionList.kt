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
import com.example.inspend.ui.theme.*

@Composable
fun TransactionList(
    date: String,
    transactions: List<TransactionData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .background(Color.White)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Title
        Text(
            text = "Transaction",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF394371),
            lineHeight = 24.sp
        )

        // Transaction List Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Date
            Text(
                text = date,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF666A87),
                lineHeight = 14.sp
            )

            // Transaction Card
            transactions.forEach { transaction ->
                TransactionListCard(
                    transaction = transaction
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionListPreview() {
    val sampleTransactions = listOf(
        TransactionData(
            type = CategoryType.INCOME,
            name = "Salary",
            dateTime = "10:00 AM",
            amount = "5,000",
            isCredit = true,
            bankType = BankType.TRUST
        ),
        TransactionData(
            type = CategoryType.EXPENSE,
            name = "Groceries",
            dateTime = "2:30 PM",
            amount = "150",
            isCredit = false,
            bankType = BankType.REVOLUT
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