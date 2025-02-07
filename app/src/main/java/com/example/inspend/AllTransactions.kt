package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inspend.components.*
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.InspendTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.compose.composable

// Helper function to format timestamp to readable time
private fun formatDateTime(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
    return format.format(date)
}

@Composable
fun AllTransactions(
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    
    var transactions by remember { mutableStateOf<List<TransactionData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch all transactions
    SideEffect {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users")
                .document(userId)
                .collection("transactions")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        transactions = snapshot.documents.mapNotNull { doc ->
                            try {
                                TransactionData(
                                    type = doc.getString("type") ?: "",
                                    category = doc.getString("category"),
                                    name = doc.getString("name") ?: "",
                                    dateTime = formatDateTime((doc.getTimestamp("createdAt")?.seconds ?: 0) * 1000),
                                    amount = doc.getString("amount") ?: "0",
                                    paymentMethod = doc.getString("paymentMethod") ?: "",
                                    isCredit = doc.getBoolean("isCredit") ?: true,
                                    transactionType = doc.getString("transactionType") ?: "",
                                    categoryType = doc.getString("categoryType") ?: ""
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }
                        isLoading = false
                    }
                }
        }
    }

    Scaffold(
        modifier = Modifier
            .padding(top = 24.dp),
        topBar = {
            AppBar(
                modifier = Modifier
                    .padding(top = 8.dp),
                type = AppBarType.DEFAULT,
                title = "All Transactions",
                onBackClick = { navController.navigateUp() }
            )
        },
        containerColor = BGdefault
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding( start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (transactions.isNotEmpty()) {
                TransactionList(
                    transactions = transactions,
                    showViewAll = false  // No need for view all button here
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllTransactionsPreview() {
    InspendTheme {
        // Create sample transactions for preview
        val sampleTransactions = listOf(
            TransactionData(
                type = "Other Transaction",
                name = "Salary",
                dateTime = "2024-03-20 10:00 AM",
                amount = "5,000",
                paymentMethod = "TRUST",
                isCredit = true,
                transactionType = "INCOME",
                categoryType = "TRANSFER"
            ),
            TransactionData(
                type = "Other Transaction",
                name = "Groceries",
                dateTime = "2024-03-20 2:30 PM",
                amount = "150",
                paymentMethod = "REVOLUT",
                isCredit = false,
                transactionType = "EXPENSE",
                categoryType = "FOOD"
            )
        )

        // Create a preview-specific composable that doesn't depend on Firebase
        Scaffold(
            modifier = Modifier
                .padding(top = 24.dp),
            topBar = {
                AppBar(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    type = AppBarType.DEFAULT,
                    title = "All Transactions",
                    onBackClick = { }
                )
            },
            containerColor = BGdefault
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding( start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TransactionList(
                    transactions = sampleTransactions,
                    showViewAll = false
                )
            }
        }
    }
} 