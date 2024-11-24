package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

enum class BankType {
    TRUST, REVOLUT, DBS, WALLET
}

@Composable
fun BankChip(
    type: BankType,
    modifier: Modifier = Modifier
) {
    val bankName = when (type) {
        BankType.TRUST -> "Trust"
        BankType.REVOLUT -> "Revolut"
        BankType.DBS -> "DBS"
        BankType.WALLET -> "Wallet"
    }

    Row(
        modifier = modifier
            .height(22.dp)
            .wrapContentWidth()
            .background(
                color = Color(0xFFEFF1F5),
                shape = RoundedCornerShape(44.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFE4E6EE),
                shape = CircleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bank Icon
        Icon(
            painter = painterResource(
                id = when (type) {
                    BankType.TRUST -> R.drawable.trust
                    BankType.REVOLUT -> R.drawable.revolut
                    BankType.DBS -> R.drawable.dbs
                    BankType.WALLET -> R.drawable.wallet
                }
            ),
            contentDescription = bankName,
            tint = Color.Unspecified,
            modifier = Modifier.size(14.dp)
        )

        // Bank Name
        Text(
            text = bankName,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF666A87),
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun BankChipsColumn(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .height(115.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        BankChip(
            type = BankType.TRUST
        )
        BankChip(
            type = BankType.REVOLUT
        )
        BankChip(
            type = BankType.DBS
        )
        BankChip(
            type = BankType.WALLET
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BankChipsPreview() {
    BankChipsColumn()
} 