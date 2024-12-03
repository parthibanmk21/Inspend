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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.inspend.components.PaymentCardWithToggle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.Timestamp

@Composable
fun TransactionTypeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var amount by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf(false) }
    var isOpened by remember { mutableStateOf(false) }

    // Individual toggle states for each payment method
    var isTrustOpened by remember { mutableStateOf(false) }
    var isDbsOpened by remember { mutableStateOf(false) }
    var isRevolutOpened by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BGdefault)
            .padding(top = 24.dp)
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
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Text(
                    text = "Your transaction type",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Grey700
                )

                Text(
                    text = "Don’t worry no can see these number except you.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 18.sp,
                    color = Grey400
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    PaymentCard(
                        title = "Wallet",
                        amount = amount,
                        onAmountChange = {
                            amount = it
                            amountError = false
                        },
                        isError = amountError
                    )

                    if (amountError) {
                        Text(
                            text = "Wallet amount can't be empty",
                            color = Color(0xFFB91C1C),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.1.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            Button(
                text = "Continue",
                onClick = {
                    if (amount.isBlank()) {
                        amountError = true
                    } else {
                        coroutineScope.launch {
                            try {
                                val userId = auth.currentUser?.uid
                                if (userId != null) {
                                    // Create transaction data
                                    val transactionData = hashMapOf(
                                        "type" to "Opening Balance",
                                        "amount" to amount,
                                        "paymentMethod" to "WALLET",
                                        "createdAt" to Timestamp.now(),
                                        "isCredit" to true,
                                        "name" to "Opening Balance",
                                        "transactionType" to "INCOME"
                                    )

                                    // Add transaction to user's transactions collection
                                    db.collection("users")
                                        .document(userId)
                                        .collection("transactions")
                                        .add(transactionData)
                                        .await()

                                    // Navigate to home screen
                                    navController.navigate("home") {
                                        popUpTo("welcome") { inclusive = true }
                                    }
                                }
                            } catch (e: Exception) {
                                amountError = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
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
        var amount by remember { mutableStateOf("") }
        var amountError by remember { mutableStateOf(false) }
        
        // Add individual toggle states for preview
        var isTrustOpened by remember { mutableStateOf(false) }
        var isDbsOpened by remember { mutableStateOf(false) }
        var isRevolutOpened by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BGdefault)
                .padding(top = 24.dp)
        ) {
            AppBar(
                title = "Transaction Type",
                onBackClick = { }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Text(
                        text = "Your transaction type",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Grey700
                    )

                    Text(
                        text = "Don't worry no can see these number except you.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp,
                        color = Grey400
                    )
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        PaymentCard(
                            title = "Wallet",
                            amount = amount,
                            onAmountChange = { 
                                amount = it
                                amountError = false
                            },
                            isError = amountError
                        )
                        
                        if (amountError) {
                            Text(
                                text = "Wallet amount can't be empty",
                                color = Color(0xFFB91C1C),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.1.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                        Column (
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ){
                            Text(
                                text = "Other methods",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Grey400
                            )

                            // Trust Payment Card
                            PaymentCardWithToggle(
                                title = "Trust",
                                icon = R.drawable.trust,
                                amount = amount,
                                onAmountChange = { amount = it },
                                isOpened = isTrustOpened,
                                onToggleChange = { isTrustOpened = it }
                            )

                            // DBS Payment Card
                            PaymentCardWithToggle(
                                title = "DBS",
                                icon = R.drawable.dbs,
                                amount = amount,
                                onAmountChange = { amount = it },
                                isOpened = isDbsOpened,
                                onToggleChange = { isDbsOpened = it }
                            )

                            // Revolut Payment Card
                            PaymentCardWithToggle(
                                title = "Revolut",
                                icon = R.drawable.revolut,
                                amount = amount,
                                onAmountChange = { amount = it },
                                isOpened = isRevolutOpened,
                                onToggleChange = { isRevolutOpened = it }
                            )
                        }
                    }
                }

                Button(
                    text = "Continue",
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
} 