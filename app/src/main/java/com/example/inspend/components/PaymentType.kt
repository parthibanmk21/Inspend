package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.R

@Composable
fun PaymentType(
    icon: Int,
    name: String,
    balance: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(
                color = Color(0xFFF6F7F9),
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF145CB5) else Color(0xFFD5D9E2),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Payment Icon
        Icon(
            painter = painterResource(id = icon),
            contentDescription = name,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )

        // Payment Details
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF526077),
                letterSpacing = 0.1.sp,
                lineHeight = 22.sp
            )

            Row {
                Text(
                    text = "$",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    letterSpacing = 0.1.sp,
                    lineHeight = 22.sp
                )
                Text(
                    text = balance,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    letterSpacing = 0.1.sp,
                    lineHeight = 22.sp
                )
            }
        }

        // Radio Button
        RadioButton(
            selected = isSelected,
            onSelect = onClick,
            state = RadioButtonState.ENABLED
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentTypePreview() {
    Column(
        modifier = Modifier
            .width(328.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Default State
        PaymentType(
            icon = R.drawable.wallet,
            name = "Wallet",
            balance = "0",
            isSelected = false,
            onClick = { }
        )

        // Selected State
        PaymentType(
            icon = R.drawable.wallet,
            name = "Wallet",
            balance = "1,000",
            isSelected = true,
            onClick = { }
        )
    }
} 