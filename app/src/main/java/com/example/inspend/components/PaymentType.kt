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

@Composable
fun PaymentBottomSheetContent(
    bankBalances: Map<String, String> = emptyMap(),
    selectedPayment: String? = null,
    onPaymentSelected: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Wallet Payment Type
        PaymentType(
            icon = R.drawable.wallet,
            name = "Wallet",
            balance = bankBalances["WALLET"] ?: "0.00",
            isSelected = selectedPayment == "Wallet",
            onClick = { onPaymentSelected("Wallet") }
        )

        // Trust Payment Type
        PaymentType(
            icon = R.drawable.trust,
            name = "Trust",
            balance = bankBalances["TRUST"] ?: "0.00",
            isSelected = selectedPayment == "Trust",
            onClick = { onPaymentSelected("Trust") }
        )

        // DBS Payment Type
        PaymentType(
            icon = R.drawable.dbs,
            name = "DBS",
            balance = bankBalances["DBS"] ?: "0.00",
            isSelected = selectedPayment == "DBS",
            onClick = { onPaymentSelected("DBS") }
        )

        // Revolut Payment Type
        PaymentType(
            icon = R.drawable.revolut,
            name = "Revolut",
            balance = bankBalances["REVOLUT"] ?: "0.00",
            isSelected = selectedPayment == "Revolut",
            onClick = { onPaymentSelected("Revolut") }
        )
    }
}

// Update the preview to show how it looks with sample balances
@Preview(showBackground = true)
@Composable
fun PaymentTypePreview() {
    val sampleBalances = mapOf(
        "WALLET" to "1,250.50",
        "TRUST" to "3,500.75",
        "DBS" to "2,800.25",
        "REVOLUT" to "1,750.80"
    )

    PaymentBottomSheetContent(
        bankBalances = sampleBalances,
        selectedPayment = "Wallet"
    )
} 