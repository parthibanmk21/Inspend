package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inspend.components.*
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.InspendTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun HomePage(
    navController: NavController
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    
    // State for user name
    var userName by remember { mutableStateOf("") }
    
    // Fetch user name when component is first created
    LaunchedEffect(Unit) {
        try {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val userDoc = db.collection("users").document(userId).get().await()
                userName = userDoc.getString("name") ?: ""
            }
        } catch (e: Exception) {
            // Handle error silently or show a toast
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(BGdefault)
    ) {
        // Home AppBar with user name
        AppBar(
            type = AppBarType.HOME,
            title = userName,  // Use the fetched user name
            subtitle = "Welcome back",
            onProfileClick = {
                // Handle profile click
            },
            navController = navController
        )

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Balance Card
            BalanceCard(
                balance = "25,000.00",
                onVisibilityToggle = {
                    // Handle visibility toggle
                }
            )

            // Transaction List
            val sampleTransactions = listOf(
                TransactionData(
                    type = CategoryType.INCOME,
                    name = "Salary",
                    dateTime = "10:00 AM",
                    amount = "5,000",
                    isCredit = true,
                    bankType = BankType.TRUST
                ),
                TransactionData(
                    type = CategoryType.EXPENSE,
                    name = "Groceries",
                    dateTime = "2:30 PM",
                    amount = "150",
                    isCredit = false,
                    bankType = BankType.REVOLUT
                )
            )

            Column(
                modifier = Modifier
//                    .padding(16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.5.dp,
                        color = Color(0xFFE0E2EB),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                TransactionList(
                    date = "Today",
                    transactions = sampleTransactions
                )
            }
        }
    }
}

@Preview(
    name = "Home Page",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun HomePagePreview() {
    InspendTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .background(BGdefault)
        ) {
            // Home AppBar with dummy data
            AppBar(
                type = AppBarType.HOME,
                title = "John Doe",  // Dummy name
                subtitle = "Welcome back",
                onProfileClick = { }
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Balance Card
                BalanceCard(
                    balance = "25,000.00",
                    onVisibilityToggle = { }
                )

                // Transaction List with sample data
                val sampleTransactions = listOf(
                    TransactionData(
                        type = CategoryType.INCOME,
                        name = "Salary",
                        dateTime = "10:00 AM",
                        amount = "5,000",
                        isCredit = true,
                        bankType = BankType.TRUST
                    ),
                    TransactionData(
                        type = CategoryType.EXPENSE,
                        name = "Groceries",
                        dateTime = "2:30 PM",
                        amount = "150",
                        isCredit = false,
                        bankType = BankType.REVOLUT
                    )
                )

                Column(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFFE0E2EB),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    TransactionList(
                        date = "Today",
                        transactions = sampleTransactions
                    )
                }
            }
        }
    }
} 