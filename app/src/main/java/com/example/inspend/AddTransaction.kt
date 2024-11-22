package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inspend.components.*
import com.example.inspend.ui.theme.*

// Shared content function
@Composable
private fun AddTransactionContent(
    onBackClick: () -> Unit = {}
) {
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

        // Transaction Body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
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
                        type = ChipType.ERROR
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Chip(
                        text = "Income",
                        type = ChipType.SUCCESS
                    )
                }

                // Payment Method Dropdown
                Column(
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
                            .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xFFD5D9E2), RoundedCornerShape(4.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.wallet),
                            contentDescription = "Wallet",
                            tint = Color(0xFF526077),
                            modifier = Modifier.size(24.dp)
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Wallet",
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
                                    text = "0",
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
                            painter = painterResource(id = R.drawable.downarrow),
                            contentDescription = "Category",
                            tint = Color(0xFF526077),
                            modifier = Modifier.size(24.dp)
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
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Note (Optional)",
                    placeholder = "eg. Dinner at restaurant",
                    value = "",
                    onValueChange = { }
                )

                // Save Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Add Transaction",
                    onClick = { }
                )
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