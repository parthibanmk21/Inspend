package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inspend.components.AppBar
import com.example.inspend.components.Button
import com.example.inspend.components.PaymentCard
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.Grey400
import com.example.inspend.ui.theme.Grey700
import com.example.inspend.ui.theme.InspendTheme

@Composable
fun TransactionTypeScreen(
    navController: NavController
) {
    var selectedCard by remember { mutableStateOf<String?>(null) }
    var amounts by remember { mutableStateOf(mapOf<String, String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BGdefault)
            .padding(top = 24.dp, bottom = 16.dp)
    ) {
        AppBar(
            title = "Transaction Type",
            onBackClick = { 
                navController.navigateUp()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ){
                Text(
                    text = "Your transaction type’s",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Grey700
                )

                Text(
                    text = "Don’t worry no can see these number except you.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey400
                )
            }

            // Payment Cards
            PaymentCard(
                title = "Wallet",
                amount = amounts["Wallet"] ?: "",
                onAmountChange = { newAmount ->
                    amounts = amounts.toMutableMap().apply {
                        put("Wallet", newAmount)
                    }
                }
            )
            Column (
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ){
                //Button
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Continue",
                    onClick = { }
                )
            }
        }
    }
}

@Preview(
    name = "Transaction Type Screen",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun TransactionTypeScreenPreview() {
    InspendTheme {
        TransactionTypeScreen(
            navController = rememberNavController()
        )
    }
} 