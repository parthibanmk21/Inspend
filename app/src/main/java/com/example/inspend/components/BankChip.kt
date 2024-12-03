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
import com.example.inspend.ui.theme.Grey100
import com.example.inspend.ui.theme.Grey600

enum class BankType(
    val icon: Int,
    val backgroundColor: Color,
    val displayName: String
) {
    WALLET(
        icon = R.drawable.wallet,
        backgroundColor = Color(0xFFF6F7F9),
        displayName = "Wallet"
    ),
    TRUST(
        icon = R.drawable.trust,
        backgroundColor = Color(0xFFF6F7F9),
        displayName = "Trust"
    ),
    DBS(
        icon = R.drawable.dbs,
        backgroundColor = Color(0xFFF6F7F9),
        displayName = "DBS"
    ),
    REVOLUT(
        icon = R.drawable.revolut,
        backgroundColor = Color(0xFFF6F7F9),
        displayName = "Revolut"
    )
}

@Composable
fun BankChip(
    type: BankType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Grey100,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = type.icon),
            contentDescription = type.displayName,
            tint = Color.Unspecified,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = type.displayName,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Grey600
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
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
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