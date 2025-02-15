package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import com.example.inspend.R
import com.example.inspend.TransactionData
import java.text.SimpleDateFormat
import java.util.*

data class CategorySummary(
    val name: String,
    val icon: Int,
    val total: String,
    val percentage: String
)

enum class TimeFilter {
    TODAY,
    THIS_WEEK,
    THIS_MONTH,
    ALL
}

@Composable
fun CategoryCard(transactions: List<TransactionData>) {
    var selectedTimeFilter by remember { mutableStateOf(TimeFilter.TODAY) }

    // Filter transactions based on selected time
    val filteredTransactions = transactions.filter { transaction ->
        val transactionDate = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())
            .parse(transaction.dateTime)?.time ?: 0L
        val now = System.currentTimeMillis()
        val calendar = Calendar.getInstance()

        when (selectedTimeFilter) {
            TimeFilter.TODAY -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                transactionDate >= calendar.timeInMillis
            }
            TimeFilter.THIS_WEEK -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                transactionDate >= calendar.timeInMillis
            }
            TimeFilter.THIS_MONTH -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                transactionDate >= calendar.timeInMillis
            }
            TimeFilter.ALL -> true
            else -> true  // Add else branch
        }
    }

    // Calculate totals by category for expenses only
    val categoryTotals = filteredTransactions
        .filter { !it.isCredit && it.transactionType == "EXPENSE" }  // Only expenses
        .groupBy { it.categoryType.uppercase() }  // Group by category type
        .mapValues { (_, txns) ->
            txns.sumOf { tx -> 
                tx.amount.replace(",", "").toDoubleOrNull() ?: 0.0 
            }
        }

    // Calculate total expenses across all categories
    val totalExpenses = categoryTotals.values.sum().takeIf { it > 0 } ?: 1.0  // Avoid division by zero

    // Create category summaries with actual data
    val categories = listOf(
        CategorySummary(
            name = "Food",
            icon = R.drawable.food,
            total = String.format("$%.2f", categoryTotals["FOOD"] ?: 0.0),
            percentage = if (totalExpenses > 0) 
                "${((categoryTotals["FOOD"] ?: 0.0) * 100 / totalExpenses).toInt()}%" 
                else "0%"
        ),
        CategorySummary(
            name = "Travel",
            icon = R.drawable.travel,
            total = String.format("$%.2f", categoryTotals["TRAVEL"] ?: 0.0),
            percentage = if (totalExpenses > 0) 
                "${((categoryTotals["TRAVEL"] ?: 0.0) * 100 / totalExpenses).toInt()}%" 
                else "0%"
        ),
        CategorySummary(
            name = "Shopping",
            icon = R.drawable.shopping,
            total = String.format("$%.2f", categoryTotals["SHOPPING"] ?: 0.0),
            percentage = if (totalExpenses > 0) 
                "${((categoryTotals["SHOPPING"] ?: 0.0) * 100 / totalExpenses).toInt()}%" 
                else "0%"
        ),
        CategorySummary(
            name = "Transfer",
            icon = R.drawable.transfer,
            total = String.format("$%.2f", categoryTotals["TRANSFER"] ?: 0.0),
            percentage = if (totalExpenses > 0) 
                "${((categoryTotals["TRANSFER"] ?: 0.0) * 100 / totalExpenses).toInt()}%" 
                else "0%"
        ),
        CategorySummary(
            name = "Other",
            icon = R.drawable.other,
            total = String.format("$%.2f", categoryTotals["OTHER"] ?: 0.0),
            percentage = if (totalExpenses > 0) 
                "${((categoryTotals["OTHER"] ?: 0.0) * 100 / totalExpenses).toInt()}%" 
                else "0%"
        )
    ).sortedByDescending { it.total.replace("$", "").toDoubleOrNull() ?: 0.0 }  // Sort by amount

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(2.dp, Color(0xFFE0E2EB), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Text(
            text = "Spending Category",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF394371)
        )

        // Time filter row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TimeFilterChip(
                text = "Today",
                isSelected = selectedTimeFilter == TimeFilter.TODAY,
                onClick = { selectedTimeFilter = TimeFilter.TODAY }
            )
            TimeFilterChip(
                text = "This Week",
                isSelected = selectedTimeFilter == TimeFilter.THIS_WEEK,
                onClick = { selectedTimeFilter = TimeFilter.THIS_WEEK }
            )
            TimeFilterChip(
                text = "This Month",
                isSelected = selectedTimeFilter == TimeFilter.THIS_MONTH,
                onClick = { selectedTimeFilter = TimeFilter.THIS_MONTH }
            )
            TimeFilterChip(
                text = "All",
                isSelected = selectedTimeFilter == TimeFilter.ALL,
                onClick = { selectedTimeFilter = TimeFilter.ALL }
            )
        }

        // Category List
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            categories.forEach { category ->
                CategoryRow(category)
            }
        }
    }
}

@Composable
private fun CategoryRow(category: CategorySummary) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
            .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left section with icon and info
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = category.icon),
                contentDescription = category.name,
                tint = Color(0xFF526077),
                modifier = Modifier.size(24.dp)
            )
            
            Column {
                Text(
                    text = category.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    letterSpacing = 0.15.sp,
                    lineHeight = 20.sp
                )
                Text(
                    text = category.total,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    letterSpacing = 0.15.sp,
                    lineHeight = 14.sp
                )
            }
        }

        // Percentage
        Text(
            text = category.percentage,
            fontSize = 14.sp,
            color = Color(0xFF666A87),
            letterSpacing = 0.15.sp,
            lineHeight = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryCardPreview() {
    val sampleTransactions = listOf(
        TransactionData(
            type = "Other Transaction",
            name = "Groceries",
            dateTime = "2024-03-20 10:00 AM",
            amount = "150.00",
            paymentMethod = "WALLET",
            isCredit = false,
            transactionType = "EXPENSE",
            categoryType = "FOOD"
        ),
        TransactionData(
            type = "Other Transaction",
            name = "MRT",
            dateTime = "2024-03-20 2:30 PM",
            amount = "50.00",
            paymentMethod = "WALLET",
            isCredit = false,
            transactionType = "EXPENSE",
            categoryType = "TRAVEL"
        ),
        TransactionData(
            type = "Other Transaction",
            name = "Shopping Mall",
            dateTime = "2024-03-21 11:00 AM",
            amount = "200.00",
            paymentMethod = "TRUST",
            isCredit = false,
            transactionType = "EXPENSE",
            categoryType = "SHOPPING"
        ),
        TransactionData(
            type = "Other Transaction",
            name = "Rent Transfer",
            dateTime = "2024-03-22 9:00 AM",
            amount = "100.00",
            paymentMethod = "DBS",
            isCredit = false,
            transactionType = "EXPENSE",
            categoryType = "TRANSFER"
        ),
        TransactionData(
            type = "Other Transaction",
            name = "Mobile Recharge",
            dateTime = "2024-03-23 3:00 PM",
            amount = "30.00",
            paymentMethod = "REVOLUT",
            isCredit = false,
            transactionType = "EXPENSE",
            categoryType = "OTHER"
        )
    )

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        CategoryCard(transactions = sampleTransactions)
    }
}

@Composable
fun TimeFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(22.dp)
            .background(
                color = if (isSelected) Color(0xFF114993) else Color(0xFFECEEF2),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF526077),
            letterSpacing = 0.15.sp,
            lineHeight = 14.sp
        )
    }
} 