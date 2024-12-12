package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inspend.components.*
import com.example.inspend.ui.theme.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.interaction.MutableInteractionSource
import android.app.TimePickerDialog
import android.app.DatePickerDialog
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.ZoneId
import com.google.firebase.Timestamp
import com.example.inspend.data.Transaction
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect

// Add at the top level of the file
data class PaymentMethodOption(
    val icon: Int,
    val name: String,
    val balance: String
)

// Shared content function
@Composable
private fun AddTransactionContent(
    onBackClick: () -> Unit = {}
) {
    // Add state for transactions
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    
    // Fetch transactions from Firebase
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        
        db.collection("users")
            .document(userId)
            .collection("transactions")
            .get()
            .addOnSuccessListener { documents ->
                transactions = documents.map { doc ->
                    Transaction(
                        id = doc.id,
                        amount = doc.getString("amount") ?: "0.00",
                        paymentMethod = doc.getString("paymentMethod") ?: "",
                        isCredit = doc.getBoolean("isCredit") ?: false,
                        name = doc.getString("name") ?: "",
                        type = doc.getString("type") ?: "",
                        category = doc.getString("category") ?: "",
                        createdAt = doc.getTimestamp("createdAt")?.seconds ?: 0L
                    )
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching transactions: ${e.message}")
            }
    }

    // Add state for bottom sheet
    var showPaymentMethodSheet by remember { mutableStateOf(false) }
    // Add state for selected payment method
    var selectedPaymentMethod by remember { mutableStateOf(0) }  // 0 for Wallet (default)
    // Add state for transaction type
    var isExpense by remember { mutableStateOf(true) }  // true for Expense, false for Income
    // Add state for amount and focus
    var amount by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    // Add state for transaction name
    var transactionName by remember { mutableStateOf("") }
    
    // Add state variables for validation
    var amountError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    
    // Define bank balances first
    val bankBalances = remember {
        mapOf(
            "WALLET" to "1,250.50",
            "TRUST" to "3,500.75",
            "DBS" to "2,800.25",
            "REVOLUT" to "1,750.80"
        )
    }
    
    // Calculate payment balances from transactions
    val paymentBalances = transactions.groupBy { it.paymentMethod }
        .mapValues { (_, txns) ->
            val total = txns.sumOf { txn ->
                val amount = txn.amount.toDoubleOrNull() ?: 0.0
                if (txn.isCredit) amount else -amount
            }
            String.format("%.2f", total)
        }

    // Update payment methods to use calculated balances
    val paymentMethods = remember(paymentBalances) {
        listOf(
            PaymentMethodOption(R.drawable.wallet, "Wallet", paymentBalances["WALLET"] ?: "0.00"),
            PaymentMethodOption(R.drawable.trust, "Trust", paymentBalances["TRUST"] ?: "0.00"),
            PaymentMethodOption(R.drawable.dbs, "DBS", paymentBalances["DBS"] ?: "0.00"),
            PaymentMethodOption(R.drawable.revolut, "Revolut", paymentBalances["REVOLUT"] ?: "0.00")
        )
    }

    // Add context for dialogs
    val context = LocalContext.current
    
    // Get current date and time
    val currentDateTime = remember { LocalDateTime.now() }
    
    // States for date and time
    var selectedDate by remember { mutableStateOf(currentDateTime) }
    var selectedTime by remember { mutableStateOf(currentDateTime) }
    
    // Get screen width
    val configuration = LocalConfiguration.current
    val isSmallScreen = configuration.screenWidthDp <= 360
    
    // Date formatter based on screen size
    val dateFormatter = remember(isSmallScreen) { 
        if (isSmallScreen) {
            DateTimeFormatter.ofPattern("dd MMM yy")  // Small screen: "23 Nov 24"
        } else {
            DateTimeFormatter.ofPattern("dd MMM yyyy")  // Large screen: "23 Nov 2024"
        }
    }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("hh:mm a") }
    
    // Date Picker Dialog
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            R.style.CustomPickerDialog,  // Use our custom theme
            { _, year, month, dayOfMonth ->
                selectedDate = selectedDate.withYear(year)
                    .withMonth(month + 1)
                    .withDayOfMonth(dayOfMonth)
            },
            currentDateTime.year,
            currentDateTime.monthValue - 1,
            currentDateTime.dayOfMonth
        )
    }
    
    // Time Picker Dialog
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            R.style.CustomPickerDialog,  // Use our custom theme
            { _, hourOfDay, minute ->
                selectedTime = selectedTime.withHour(hourOfDay)
                    .withMinute(minute)
            },
            currentDateTime.hour,
            currentDateTime.minute,
            false
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BGdefault)
            .padding(top = 24.dp, bottom = 16.dp)
    ) {
        // AppBar
        AppBar(
            title = "Add Transaction",
            onBackClick = onBackClick
        )

        // Make Transaction Body scrollable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = "Add new Transaction",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF434E61)
                )
                Text(
                    text = "Lorem Ipsum Subtext",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8695AA).copy(alpha = 0.75f),
                    letterSpacing = 0.15.sp
                )
            }

            // New Transaction Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(2.dp, Color(0xFFE0E2EB), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Amount Input with center alignment
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter Amount",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFB1BBC8),
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp
                    )
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(horizontal = 4.dp)
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (amount.isEmpty()) Color(0xFFB1BBC8) else Color(0xFF526077),
                            lineHeight = 28.sp
                        )
                        BasicTextField(
                            value = amount,
                            onValueChange = { newValue ->
                                // Only allow numbers and decimal point
                                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                                    amount = newValue
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 38.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF526077),
                                lineHeight = 28.sp,
                                textAlign = TextAlign.Start
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .wrapContentHeight()
                                .onFocusChanged { isFocused = it.isFocused },
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .wrapContentHeight(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (amount.isEmpty()) {
                                        Text(
                                            text = "0.00",
                                            fontSize = 38.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFB1BBC8),
                                            lineHeight = 28.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                // Transaction Type Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Chip(
                        text = "Expense",
                        type = ChipType.ERROR,
                        isSelected = isExpense,
                        onClick = { isExpense = true }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Chip(
                        text = "Income",
                        type = ChipType.SUCCESS,
                        isSelected = !isExpense,
                        onClick = { isExpense = false }
                    )
                }

                //Transaction Name
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Transaction Name",
                    placeholder = "eg. Morning dinner",
                    value = transactionName,
                    onValueChange = { 
                        transactionName = it
                        nameError = false  // Clear error on change
                    },
                    isError = nameError,
                    errorMessage = if (nameError) "Transaction name is required" else null
                )

                // Payment Method Dropdown
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Payment Method",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF526077),
                        lineHeight = 16.sp,
                        letterSpacing = 0.1.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = { showPaymentMethodSheet = true }
                            )
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = paymentMethods[selectedPaymentMethod].icon),
                            contentDescription = paymentMethods[selectedPaymentMethod].name,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = paymentMethods[selectedPaymentMethod].name,
                                fontSize = 16.sp,
                                color = Color(0xFF526077),
                                letterSpacing = 0.5.sp,
                                lineHeight = 20.sp
                            )
                            Row {
                                Text(
                                    text = "$",
                                    fontSize = 16.sp,
                                    color = Color(0xFF526077),
                                    letterSpacing = 0.5.sp,
                                    lineHeight = 20.sp
                                )
                                Text(
                                    text = paymentMethods[selectedPaymentMethod].balance,
                                    fontSize = 16.sp,
                                    color = Color(0xFF526077),
                                    letterSpacing = 0.5.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.chevrondown),
                            contentDescription = "Expand",
                            tint = Color(0xFFD5D9E2),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Category Dropdown
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Category",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF526077),
                        lineHeight = 16.sp,
                        letterSpacing = 0.1.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cakeicon),
                            contentDescription = "Category",
                            tint = Color(0xFF526077),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Food",
                            fontSize = 16.sp,
                            color = Color(0xFF526077),
                            letterSpacing = 0.5.sp,
                            lineHeight = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.chevrondown),
                            contentDescription = "Expand",
                            tint = Color(0xFFD5D9E2),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Date and Time Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Date Input
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Transaction Date",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF526077),
                            lineHeight = 16.sp,
                            letterSpacing = 0.1.sp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                                .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true),
                                    onClick = { datePickerDialog.show() }
                                )
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedDate.format(dateFormatter),
                                fontSize = 16.sp,
                                color = Color(0xFF526077),
                                letterSpacing = 0.5.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.calender),
                                contentDescription = "Calendar",
                                tint = Color(0xFF526077),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Time Input
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Transaction Time",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF526077),
                            lineHeight = 16.sp,
                            letterSpacing = 0.1.sp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                                .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true),
                                    onClick = { timePickerDialog.show() }
                                )
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedTime.format(timeFormatter),
                                fontSize = 16.sp,
                                color = Color(0xFF526077),
                                letterSpacing = 0.5.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "Clock",
                                tint = Color(0xFF526077),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Note Input
//                InputField(
//                    modifier = Modifier.fillMaxWidth(),
//                    label = "Note (Optional)",
//                    placeholder = "eg. Dinner at restaurant",
//                    value = "",
//                    onValueChange = { }
//                )

                // Add Transaction Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Add Transaction",
                    onClick = {
                        // Reset errors
                        amountError = false
                        nameError = false
                        
                        // Validate inputs
                        if (amount.isBlank()) {
                            amountError = true
                        }
                        if (transactionName.isBlank()) {
                            nameError = true
                        }
                        
                        // Only proceed if no errors
                        if (!amountError && !nameError) {
                            val db = FirebaseFirestore.getInstance()
                            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@Button
                            
                            // Convert selected date and time to timestamp
                            val dateTime = selectedDate
                                .withHour(selectedTime.hour)
                                .withMinute(selectedTime.minute)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli()

                            // Create transaction data
                            val transactionData = hashMapOf(
                                "type" to "Other Transaction",
                                "category" to "Food",
                                "amount" to amount,
                                "paymentMethod" to paymentMethods[selectedPaymentMethod].name.uppercase(),
                                "createdAt" to Timestamp.now(),
                                "isCredit" to !isExpense,
                                "name" to transactionName,
                                "transactionType" to if (isExpense) "EXPENSE" else "INCOME"
                            )

                            // Save to Firebase
                            db.collection("users")
                                .document(userId)
                                .collection("transactions")
                                .add(transactionData)
                                .addOnSuccessListener {
                                    onBackClick()
                                }
                                .addOnFailureListener { e ->
                                    println("Error saving transaction: ${e.message}")
                                }
                        }
                    }
                )
            }
        }

        // Use our CustomBottomSheet
        if (showPaymentMethodSheet) {
            PaymentSelectionSheet(
                transactions = transactions,
                selectedPayment = paymentMethods[selectedPaymentMethod].name,
                onPaymentSelected = { selectedName ->
                    selectedPaymentMethod = paymentMethods.indexOfFirst { it.name == selectedName }
                    showPaymentMethodSheet = false
                },
                onDismiss = { showPaymentMethodSheet = false }
            )
        }
    }
}

// Actual screen using shared content
@Composable
fun AddTransactionScreen(
    navController: NavController
) {
    AddTransactionContent(
        onBackClick = { navController.navigateUp() }
    )
}

// Preview using shared content
@Preview(
    name = "Add Transaction Screen",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun AddTransactionScreenPreview() {
    InspendTheme {
        AddTransactionContent()
    }
}