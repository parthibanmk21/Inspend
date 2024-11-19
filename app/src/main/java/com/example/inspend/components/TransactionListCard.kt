package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.ui.theme.*

data class TransactionData(
    val type: CategoryType,
    val name: String,
    val dateTime: String,
    val amount: String,
    val isCredit: Boolean = true,
    val bankType: BankType
)

@Composable
fun TransactionListCard(
    transaction: TransactionData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            modifier = Modifier
                .wrapContentWidth()
                .height(46.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            // Category Icon
            CategoryIcon(type = transaction.type)

            // Right Content
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(46.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Transaction Details
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(42.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = transaction.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1F274B),
                        lineHeight = 20.sp
                    )
                    Text(
                        text = transaction.dateTime,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFAAADBE),
                        lineHeight = 14.sp
                    )
                }
        }
        }
        // Amount and Bank
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .height(46.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Amount
            Row(
                modifier = Modifier.height(20.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (transaction.isCredit) "+" else "-",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (transaction.isCredit) Color(0xFF1B6E1E) else Color(0xFFB91C1C),
                    lineHeight = 20.sp
                )
                Text(
                    text = transaction.amount,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (transaction.isCredit) Color(0xFF1B6E1E) else Color(0xFFB91C1C),
                    lineHeight = 20.sp
                )
            }

            // Bank Chip
            BankChip(
                type = transaction.bankType
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionListCardPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Income Transaction
        TransactionListCard(
            transaction = TransactionData(
                type = CategoryType.INCOME,
                name = "Salary",
                dateTime = "Today, 10:00 AM",
                amount = "5,000",
                isCredit = true,
                bankType = BankType.TRUST
            )
        )

        // Expense Transaction
        TransactionListCard(
            transaction = TransactionData(
                type = CategoryType.EXPENSE,
                name = "Groceries",
                dateTime = "Yesterday, 2:30 PM",
                amount = "150",
                isCredit = false,
                bankType = BankType.REVOLUT
            )
        )
    }
} 