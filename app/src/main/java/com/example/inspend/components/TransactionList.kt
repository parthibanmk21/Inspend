package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
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
import com.example.inspend.TransactionData
import com.example.inspend.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.clickable

private fun formatDisplayDate(dateStr: String): String {
    try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr)
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)

        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = date!!

        return when {
            isSameDay(dateCalendar, today) -> "Today"
            isSameDay(dateCalendar, yesterday) -> "Yesterday"
            else -> SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
        }
    } catch (e: Exception) {
        return dateStr
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

@Composable
fun TransactionList(
    transactions: List<TransactionData>,
    showViewAll: Boolean = false,
    onViewAllClick: () -> Unit = {}
) {
    // Group transactions by date
    val groupedTransactions = transactions.groupBy { it.dateTime.split(" ")[0] }
        .toSortedMap(reverseOrder())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E2EB),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(top=12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transaction",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF394371),
                lineHeight = 18.sp
            )

            if (showViewAll) {
                Text(
                    text = "View All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF145CB5),
                    modifier = Modifier.clickable { onViewAllClick() }
                )
            }
        }

        //Search design
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0E2EB),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(top=12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Back",
                tint = Grey300,
                modifier = Modifier.size(20.dp)
            )
            Text(
//                modifier = Modifier
//                    .fillMaxWidth(),
                text = "Search here",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFB1BBC8),
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp
            )

            //Clear icon
//            Icon(
//            painter = painterResource(id = R.drawable.close),
//            contentDescription = "Back",
//            tint = Grey300,
//            modifier = Modifier.size(20.dp)
//            )
        }

        // Transaction filter row
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            var selectedTimeFilter by remember { mutableStateOf(TimeFilter.TODAY) }
//
//            // Filter transactions based on selected time
//            val filteredTransactions = transactions.filter { transaction ->
//                val transactionDate = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())
//                    .parse(transaction.dateTime)?.time ?: 0L
//                val now = System.currentTimeMillis()
//                val calendar = Calendar.getInstance()
//
//                when (selectedTimeFilter) {
//                    TimeFilter.TODAY -> {
//                        calendar.timeInMillis = now
//                        calendar.set(Calendar.HOUR_OF_DAY, 0)
//                        calendar.set(Calendar.MINUTE, 0)
//                        transactionDate >= calendar.timeInMillis
//                    }
//                    TimeFilter.THIS_WEEK -> {
//                        calendar.timeInMillis = now
//                        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
//                        transactionDate >= calendar.timeInMillis
//                    }
//                    TimeFilter.THIS_MONTH -> {
//                        calendar.timeInMillis = now
//                        calendar.set(Calendar.DAY_OF_MONTH, 1)
//                        transactionDate >= calendar.timeInMillis
//                    }
//                    TimeFilter.ALL -> true
//                    else -> true  // Add else branch
//                }
//            }
//            TimeFilterChip(
//                text = "Today",
//                isSelected = selectedTimeFilter == TimeFilter.TODAY,
//                onClick = { selectedTimeFilter = TimeFilter.TODAY }
//            )
//            TimeFilterChip(
//                text = "This Week",
//                isSelected = selectedTimeFilter == TimeFilter.THIS_WEEK,
//                onClick = { selectedTimeFilter = TimeFilter.THIS_WEEK }
//            )
//            TimeFilterChip(
//                text = "This Month",
//                isSelected = selectedTimeFilter == TimeFilter.THIS_MONTH,
//                onClick = { selectedTimeFilter = TimeFilter.THIS_MONTH }
//            )
//            TimeFilterChip(
//                text = "All",
//                isSelected = selectedTimeFilter == TimeFilter.ALL,
//                onClick = { selectedTimeFilter = TimeFilter.ALL }
//            )
//        }

        // Iterate through grouped transactions
        groupedTransactions.forEach { (date, transactionsForDate) ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // Date header
                Text(
                    text = formatDisplayDate(date),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Grey400,
                    lineHeight = 14.sp
                )

                // Transactions for this date
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    transactionsForDate.forEach { transaction ->
                        // Get both time and AM/PM parts
                        val timeParts = transaction.dateTime.split(" ")
                        val timeWithAmPm = if (timeParts.size >= 3) {
                            "${timeParts[1]} ${timeParts[2]}"  // Include both time and AM/PM
                        } else {
                            "12:00 AM" // fallback
                        }

                        TransactionListCard(
                            title = transaction.name,
                            time = timeWithAmPm,  // Pass both time and AM/PM
                            amount = transaction.amount,
                            isIncome = transaction.isCredit,
                            bankType = transaction.paymentMethod,
                            categoryType = transaction.categoryType,  // Pass the categoryType
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Add divider after each group except the last one
                if (date != groupedTransactions.keys.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFFE0E2EB))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionListPreview() {
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
        ),
        TransactionData(
            type = "Other Transaction",
            name = "Coffee",
            dateTime = "2024-03-21 9:00 AM",
            amount = "50",
            paymentMethod = "DBS",
            isCredit = false,
            transactionType = "EXPENSE",
            categoryType = "FOOD"
        )
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
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
        TransactionList(transactions = sampleTransactions)
    }
}

@Composable
fun CategoryIcon(categoryType: String) {
    val iconRes = when(categoryType.uppercase()) {
        "FOOD" -> R.drawable.food
        "TRAVEL" -> R.drawable.travel
        "SHOPPING" -> R.drawable.shopping
        "TRANSFER" -> R.drawable.transfer
        "OTHER" -> R.drawable.other
        else -> R.drawable.other
    }

    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = "Category Icon",
        tint = Color(0xFF526077),
        modifier = Modifier.size(24.dp)
    )
} 