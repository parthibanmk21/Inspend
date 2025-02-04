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
    categoryType: String = "",
    modifier: Modifier = Modifier
) {
    // Just use the time as is since it already includes AM/PM
    val formattedTime = try {
        val parts = time.split(" ")
        if (parts.size >= 2) {
            // If time has hours, minutes and AM/PM
            val timePart = parts[0]
            val amPm = parts[1]
            val timeComponents = timePart.split(":")
            val hour = timeComponents[0].toInt()
            val minute = timeComponents[1]
            
            // Format hour but keep original AM/PM
            when {
                hour == 0 -> "12:$minute $amPm"
                hour <= 12 -> "$hour:$minute $amPm"
                else -> "${hour-12}:$minute $amPm"
            }
        } else {
            time // Keep original if format is unexpected
        }
    } catch (e: Exception) {
        time // If parsing fails, use original time
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left section with icon and text
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Use CategoryIcon instead of up/down arrow
            CategoryIcon(categoryType = categoryType)
            
            // Title and time
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    lineHeight = 16.sp
                )
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color(0xFF8695AA),
                    lineHeight = 14.sp
                )
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
            bankType = "Trust",
            categoryType = ""
        )

        // Expense Transaction
        TransactionListCard(
            title = "Groceries",
            time = "Yesterday, 2:30 PM",
            amount = "150",
            isIncome = false,
            bankType = "Revolut",
            categoryType = ""
        )
    }
} 