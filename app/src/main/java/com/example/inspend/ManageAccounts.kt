package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.components.*
import com.example.inspend.ui.theme.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
private fun AddTransactionContent(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BGdefault),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // AppBar
        AppBar(
            title = "Profile",
            onBackClick = onBackClick
        )
        //Body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Inner Design
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(2.dp, Color(0xFFE0E2EB), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Existing accounts",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey600,
                    lineHeight = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                )

                //Existing accounts card design comes here
                var amount = "0.00"
                PaymentCard(
                    title = "Wallet",
                    amount = amount,
                    onAmountChange = { amount = it }
                )
                var isTrustOpened by remember { mutableStateOf(false) }
                PaymentCardWithToggle(
                    title = "Trust",
                    icon = R.drawable.trust,
                    amount = amount,
                    onAmountChange = { amount = it },
                    isOpened = isTrustOpened,
                    onToggleChange = { isTrustOpened = it }  // Only changes Trust state
                )

                var isDbsOpened by remember { mutableStateOf(false) }
                PaymentCardWithToggle(
                    title = "DBS",
                    icon = R.drawable.dbs,
                    amount = amount,
                    onAmountChange = { amount = it },
                    isOpened = isDbsOpened,
                    onToggleChange = { isDbsOpened = it }  // Only changes DBS state
                )

                var isRevolutOpened by remember { mutableStateOf(false) }
                PaymentCardWithToggle(
                    title = "Revolut",
                    icon = R.drawable.revolut,
                    amount = amount,
                    onAmountChange = { amount = it },
                    isOpened = isRevolutOpened,
                    onToggleChange = { isRevolutOpened = it }  // Only changes Revolut state
                )

                Button(
                    text = "Add new account",
                    onClick = { },
                    type = ButtonType.SECONDARY
                )
                Button(
                    text = "Save",
                    onClick = { }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageAccountPreview() {
    InspendTheme {
        AddTransactionContent()
    }
}