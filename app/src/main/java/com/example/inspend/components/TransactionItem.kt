package com.example.inspend.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em

@Composable
fun TransactionItem(
    name: String,
    time: String,
    amount: String,
    isCredit: Boolean,
    paymentMethod: String,
    modifier: Modifier = Modifier
) {
    // Define colors
    val green500 = Color(0xFF22C55E)
    val red500 = Color(0xFFEF4444)
    val grey700 = Color(0xFF434E61)
    val grey300 = Color(0xFF8695AA)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Name and Time
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = grey700,
                lineHeight = 16.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = grey300,
                    lineHeight = 14.sp
                )
                Text(
                    text = "•",
                    fontSize = 12.sp,
                    color = grey300
                )
                Text(
                    text = paymentMethod,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = grey300,
                    lineHeight = 14.sp
                )
            }
        }

        // Right side - Amount
        Text(
            text = "${if (isCredit) "+" else "-"}$${amount}",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isCredit) green500 else red500,
            lineHeight = 16.sp
        )
    }
} 