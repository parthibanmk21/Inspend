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
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomePage(
    navController: NavController
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    
    var userName by remember { mutableStateOf("") }
    var transactions by remember { mutableStateOf<List<TransactionData>>(emptyList()) }
    
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
                .orderBy("dateTime", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        transactions = snapshot.documents.mapNotNull { doc ->
                            try {
                                TransactionData(
                                    type = CategoryType.valueOf(doc.getString("type") ?: "INCOME"),
                                    name = doc.getString("name") ?: "",
                                    dateTime = formatDateTime(doc.getLong("dateTime") ?: 0),
                                    amount = doc.getString("amount") ?: "0",
                                    isCredit = doc.getBoolean("isCredit") ?: true,
                                    bankType = BankType.valueOf(doc.getString("bankType") ?: "WALLET")
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }
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

@Preview(
    name = "Home Page",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun HomePagePreview() {
    InspendTheme {
        // Using HomePageContent with default values and no NavController
        HomePageContent(
            userName = "John Doe",
            transactions = listOf(
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
            ),
            onAddTransaction = { },
            navController = null  // Explicitly passing null for preview
        )
    }
}

// Update HomePageContent to handle null NavController
@Composable
private fun HomePageContent(
    userName: String = "",
    transactions: List<TransactionData> = emptyList(),
    onAddTransaction: () -> Unit = {},
    navController: NavController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(BGdefault)
    ) {
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
                            text = userName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF3A4252)
                        )
                        Text(
                            text = "Welcome back",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF8695AA)
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

        // Rest of the content remains the same
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BalanceCard(
                balance = transactions.firstOrNull()?.amount ?: "0.00",
                onVisibilityToggle = { }
            )

            if (transactions.isNotEmpty()) {
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
                        transactions = transactions
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                text = "Add Transaction",
                onClick = onAddTransaction
            )
        }
    }
} 