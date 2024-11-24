package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.components.*
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.InspendTheme

@Composable
fun DummyPage() {
    Scaffold(
        topBar = {
            // Simple AppBar without Firebase
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = "John Doe",
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
        },
        bottomBar = {
            NavigationBar(
                currentRoute = "home",
                onNavigate = { }
            )
        },
        containerColor = BGdefault
    ) { paddingValues ->
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Balance Card
            BalanceCard(
                balance = "1,234.56",
                onVisibilityToggle = { }
            )

            // Transactions List with dummy data
            TransactionList(
                date = "Today",
                transactions = listOf(
                    TransactionData(
                        type = "Other Transaction",
                        name = "Lunch",
                        dateTime = "12:30 PM",
                        amount = "25.50",
                        paymentMethod = "WALLET",
                        isCredit = false,
                        transactionType = "EXPENSE"
                    ),
                    TransactionData(
                        type = "Other Transaction",
                        name = "Salary",
                        dateTime = "9:00 AM",
                        amount = "5,000.00",
                        paymentMethod = "BANK",
                        isCredit = true,
                        transactionType = "INCOME"
                    )
                )
            )
        }
    }
}

@Preview(
    name = "Dummy Page",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun DummyPagePreview() {
    InspendTheme {
        DummyPage()
    }
} 