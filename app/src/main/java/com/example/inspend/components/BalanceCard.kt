package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.R
import com.example.inspend.ui.theme.*

@Composable
fun BalanceCard(
    balance: String,
    bankBalances: Map<String, String> = emptyMap(),
    onVisibilityToggle: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E2EB),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.Top
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row (
            modifier = modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){
            // Balance Column
            Column(
                modifier = modifier
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = "Total Balance",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey400,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Grey600,
                        lineHeight = 32.sp,
                        letterSpacing = 0.sp
                    )
                    Text(
                        text = balance,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Grey600,
                        lineHeight = 32.sp,
                        letterSpacing = 0.sp
                    )
                }
            }

            // Eye Icon
            Icon(
                painter = painterResource(id = R.drawable.eyeopen),
                contentDescription = "Toggle Balance Visibility",
                tint = Grey600,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onVisibilityToggle)
            )
        }
        Row (
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            BankChipWithAmount(
                type = BankType.WALLET,
                amount = bankBalances["WALLET"] ?: "0.00"
            )

            if (bankBalances.containsKey("TRUST")) {
                BankChipWithAmount(
                    type = BankType.TRUST,
                    amount = bankBalances["TRUST"] ?: "0.00"
                )
            }

            if (bankBalances.containsKey("DBS")) {
                BankChipWithAmount(
                    type = BankType.DBS,
                    amount = bankBalances["DBS"] ?: "0.00"
                )
            }

            if (bankBalances.containsKey("REVOLUT")) {
                BankChipWithAmount(
                    type = BankType.REVOLUT,
                    amount = bankBalances["REVOLUT"] ?: "0.00"
                )
            }
        }

    }
}

@Composable
fun BankChipWithAmount(
    type: BankType,
    amount: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Grey100,
                shape = RoundedCornerShape(4.dp)
            )
            .wrapContentHeight()
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = type.icon),
            contentDescription = type.name,
            tint = Color.Unspecified,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = amount,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Grey600
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BalanceCardPreview() {
    val sampleBalances = mapOf(
        "WALLET" to "10.00",
        "TRUST" to "20.00",
        "DBS" to "30.00",
        "REVOLUT" to "50.00"
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BalanceCard(
            balance = "8,000.00",
            bankBalances = sampleBalances
        )
    }
} 