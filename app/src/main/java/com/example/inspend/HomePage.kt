package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inspend.components.*
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.Grey600
import com.example.inspend.ui.theme.InspendTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

// Add this data class at the top level
data class TransactionData(
    val type: String = "",            // "Opening Balance" or "Other Transaction"
    val category: String? = null,     // Only for "Other Transaction"
    val name: String = "",
    val dateTime: String = "",
    val amount: String = "",
    val paymentMethod: String = "",
    val isCredit: Boolean = true,
    val transactionType: String = ""  // "INCOME" or "EXPENSE"
)

@Composable
fun HomePage(
    navController: NavController
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var userName by remember { mutableStateOf("") }
    var transactions by remember { mutableStateOf<List<TransactionData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch data immediately when component is created
    SideEffect {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            // Fetch user name
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    userName = document.getString("name") ?: ""
                }

            // Fetch transactions with real-time updates
            db.collection("users")
                .document(userId)
                .collection("transactions")
                .orderBy("createdAt", Query.Direction.DESCENDING)  // Latest first
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
                                    transactionType = doc.getString("transactionType") ?: ""
                                )
                            } catch (e: Exception) {
                                println("Error parsing transaction: ${e.message}")
                                null
                            }
                        }
                        isLoading = false
                    }
                }
        }
    }

    // UI Content
    HomePageContent(
        userName = userName,
        transactions = transactions,
        onAddTransaction = { navController.navigate("addtransaction") },
        navController = navController
    )
}

// Helper function to format timestamp to readable time
private fun formatDateTime(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(date)
}

@Preview
@Composable
fun HomePagePreview() {
    InspendTheme {
        HomePageContent(
            userName = "John Doe",
            transactions = listOf(
                TransactionData(
                    type = "Other Transaction",
                    name = "Salary",
                    dateTime = "10:00 AM",
                    amount = "5,000",
                    paymentMethod = "TRUST",
                    isCredit = true,
                    transactionType = "INCOME"
                ),
                TransactionData(
                    type = "Other Transaction",
                    name = "Groceries",
                    dateTime = "2:30 PM",
                    amount = "150",
                    paymentMethod = "REVOLUT",
                    isCredit = false,
                    transactionType = "EXPENSE"
                )
            ),
            onAddTransaction = { },
            navController = null
        )
    }
}

// Update HomePageContent to use TransactionList directly
@Composable
private fun HomePageContent(
    userName: String = "",
    transactions: List<TransactionData> = emptyList(),
    onAddTransaction: () -> Unit = {},
    navController: NavController? = null
) {
    // Calculate total balance
    val totalBalance = transactions.sumOf { transaction ->
        val amount = transaction.amount.replace(",", "").toDoubleOrNull() ?: 0.0
        if (transaction.isCredit) amount else -amount
    }.toString()

    Scaffold(
        modifier = Modifier
            .padding(vertical = 24.dp),
        topBar = {
            // Simple AppBar for preview when NavController is null
            if (navController == null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(89.dp)
                        .background(Color.White)
                        .drawBehind {
                            val borderWidth = 1.5.dp.toPx()
                            val y = size.height - borderWidth / 2
                            drawLine(
                                color = Color(0xFFE0E2EB),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = borderWidth
                            )
                        }
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = Color(0xFFECEEF2),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = "Profile",
                                tint = Grey600,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "John Doe",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF3A4252),
                                lineHeight = 20.sp,
                                letterSpacing = 0.1.sp
                            )
                            Text(
                                text = "Welcome back",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF8695AA),
                                lineHeight = 14.sp,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.notification),
                                contentDescription = "Notifications",
                                tint = Grey600,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.logout),
                                contentDescription = "Logout",
                                tint = Grey600,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            } else {
                // Real AppBar with NavController
                AppBar(
                    type = AppBarType.HOME,
                    title = userName,
                    subtitle = "Welcome back",
                    onProfileClick = { },
                    navController = navController
                )
            }
        },
        bottomBar = {
            NavigationBar(
                currentRoute = "home",
                onNavigate = { route ->
                    when (route) {
                        "add" -> navController?.navigate("addtransaction")
                        else -> navController?.navigate(route)
                    }
                }
            )
        },
        floatingActionButton = {
            // Add FAB
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = Color(0xFF145CB5),  // Primary Blue color
                        shape = CircleShape
                    )
                    .clickable {
                        navController?.navigate("addtransaction")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add Transaction",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,  // Position FAB at bottom end
        containerColor = BGdefault
    ) { paddingValues ->
        // Main content with proper padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)  // Add scaffold padding
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BalanceCard(
                balance = String.format("%.2f", totalBalance.toDoubleOrNull() ?: 0.0),
                onVisibilityToggle = { }
            )

            if (transactions.isNotEmpty()) {
                TransactionList(
                    date = "Today",
                    transactions = transactions
                )
            }
        }
    }
} 