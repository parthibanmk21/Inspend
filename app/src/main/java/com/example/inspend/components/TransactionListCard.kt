package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.ui.theme.*
import com.example.inspend.R

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
    title: String,
    time: String,
    amount: String,
    isIncome: Boolean,
    bankType: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
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
            // Use CategoryIcon instead of direct Icon
            CategoryIcon(
                type = if (isIncome) CategoryType.INCOME else CategoryType.EXPENSE,
                modifier = Modifier.size(40.dp)
            )

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
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1F274B),
                        lineHeight = 16.sp,
                        maxLines = 1, // Single line only
                        overflow = TextOverflow.Ellipsis, // Add ellipsis
                        modifier = Modifier
                            .widthIn(max = 179.dp), // Restrict max width
                    )
                    Text(
                        text = time,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Grey300,
                        lineHeight = 14.sp
                    )
                }
        }
        }
        // Amount and Bank
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Amount
            Row(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isIncome) "+$" else "-$",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isIncome) Color(0xFF1B6E1E) else Color(0xFFB91C1C),
                    lineHeight = 16.sp
                )
                Text(
                    text = amount,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isIncome) Color(0xFF1B6E1E) else Color(0xFFB91C1C),
                    lineHeight = 16.sp
                )
            }

            // Bank Chip
            BankChip(
                type = when (bankType.uppercase()) {
                    "WALLET" -> BankType.WALLET
                    "TRUST" -> BankType.TRUST
                    "DBS" -> BankType.DBS
                    "REVOLUT" -> BankType.REVOLUT
                    else -> BankType.WALLET  // Default fallback
                }
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
            title = "Salary",
            time = "Today, 10:00 AM",
            amount = "5,000",
            isIncome = true,
            bankType = "Trust"
        )

        // Expense Transaction
        TransactionListCard(
            title = "Groceries",
            time = "Yesterday, 2:30 PM",
            amount = "150",
            isIncome = false,
            bankType = "Revolut"
        )
    }
} 