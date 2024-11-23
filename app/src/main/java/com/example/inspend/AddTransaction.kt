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
    // Add state for bottom sheet
    var showPaymentMethodSheet by remember { mutableStateOf(false) }
    // Add state for selected payment method
    var selectedPaymentMethod by remember { mutableStateOf(0) }  // 0 for Wallet (default)
    // Add state for transaction type
    var isExpense by remember { mutableStateOf(true) }  // true for Expense, false for Income
    
    // Define payment methods
    val paymentMethods = remember {
        listOf(
            PaymentMethodOption(R.drawable.wallet, "Wallet", "1,000"),
            PaymentMethodOption(R.drawable.trust, "Trust", "1,000"),
            PaymentMethodOption(R.drawable.dbs, "DBS", "1,000"),
            PaymentMethodOption(R.drawable.revolut, "Revolut", "1,000")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BGdefault)
            .padding(top = 24.dp)
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
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter Amount",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF526077),
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "$",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB1BBC8),
                            lineHeight = 32.sp
                        )
                        Text(
                            text = "0.00",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB1BBC8),
                            lineHeight = 32.sp
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

                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Transaction Name",
                    placeholder = "eg. Morning dinner",
                    value = "",
                    onValueChange = { }
                )

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
                                .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                                .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Select date",
                                fontSize = 16.sp,
                                color = Color(0xFFB1BBC8),
                                letterSpacing = 0.5.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.calender),
                                contentDescription = "Calendar",
                                tint = Color(0xFFD5D9E2),
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
                                .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                                .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Select time",
                                fontSize = 16.sp,
                                color = Color(0xFFB1BBC8),
                                letterSpacing = 0.5.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "Clock",
                                tint = Color(0xFFD5D9E2),
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

                // Save Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Add Transaction",
                    onClick = { }
                )
            }
        }

        // Use our CustomBottomSheet
        if (showPaymentMethodSheet) {
            CustomBottomSheet(
                onDismiss = { showPaymentMethodSheet = false }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Select Payment Method",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF526077),
                        lineHeight = 24.sp
                    )
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        paymentMethods.forEachIndexed { index, payment ->
                            PaymentType(
                                icon = payment.icon,
                                name = payment.name,
                                balance = payment.balance,
                                isSelected = index == selectedPaymentMethod,
                                onClick = { 
                                    selectedPaymentMethod = index
                                    showPaymentMethodSheet = false
                                }
                            )
                        }
                    }
                }
            }
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