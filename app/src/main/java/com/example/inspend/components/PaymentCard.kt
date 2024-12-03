package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.R
import com.example.inspend.ui.theme.*

@Composable
fun PaymentCard(
    title: String,
    amount: String = "",
    onAmountChange: (String) -> Unit = {},
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (isError) Color(0xFFB91C1C) else Color(0xFFD5D9E2),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.wallet),
                        contentDescription = "Wallet",
                        tint = Grey500,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Grey500,
                        lineHeight = 28.sp
                    )
                }
                // Disabled - Checked
                Toggle(
                    isChecked = true,
                    onCheckedChange = { },
                    enabled = false
                )
            }
        // Icon and Title


        // Amount Input
        BasicTextField(
            value = amount,
            onValueChange = onAmountChange,
            textStyle = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = Grey500,
                lineHeight = 36.sp
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle keyboard done
                }
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Currency prefix - always visible
                    Text(
                        text = "$",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = Grey500,
                        lineHeight = 36.sp
                    )
                    
                    // Amount field with placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (amount.isEmpty()) {
                            Text(
                                text = "0.00",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Medium,
                                color = Grey300,
                                lineHeight = 36.sp
                            )
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            innerTextField()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

// Add new PaymentCard with toggle
@Composable
fun PaymentCardWithToggle(
    title: String,
    icon: Int = R.drawable.wallet,
    amount: String = "",
    onAmountChange: (String) -> Unit = {},
    isError: Boolean = false,
    isOpened: Boolean = true,
    onToggleChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isOpened) Color.White else Color(0xFFF6F7F9),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (isError) Color(0xFFB91C1C) else Color(0xFFD5D9E2),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey500,
                    lineHeight = 28.sp
                )
            }

            Toggle(
                isChecked = isOpened,
                onCheckedChange = onToggleChange,
                enabled = true
            )
        }

        if (isOpened) {
            BasicTextField(
                value = amount,
                onValueChange = onAmountChange,
                textStyle = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey500,
                    lineHeight = 36.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            color = Grey500,
                            lineHeight = 36.sp
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            if (amount.isEmpty()) {
                                Text(
                                    text = "0.00",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Grey300,
                                    lineHeight = 36.sp
                                )
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                innerTextField()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentCardPreview() {
    var amount by remember { mutableStateOf("") }
    
    // Individual toggle states
    var isTrustOpened by remember { mutableStateOf(false) }
    var isDbsOpened by remember { mutableStateOf(false) }
    var isRevolutOpened by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Original PaymentCard
        PaymentCard(
            title = "Wallet",
            amount = amount,
            onAmountChange = { amount = it }
        )

        // Different payment methods with their own toggle states
        PaymentCardWithToggle(
            title = "Trust",
            icon = R.drawable.trust,
            amount = amount,
            onAmountChange = { amount = it },
            isOpened = isTrustOpened,
            onToggleChange = { isTrustOpened = it }  // Only changes Trust state
        )

        PaymentCardWithToggle(
            title = "DBS",
            icon = R.drawable.dbs,
            amount = amount,
            onAmountChange = { amount = it },
            isOpened = isDbsOpened,
            onToggleChange = { isDbsOpened = it }  // Only changes DBS state
        )

        PaymentCardWithToggle(
            title = "Revolut",
            icon = R.drawable.revolut,
            amount = amount,
            onAmountChange = { amount = it },
            isOpened = isRevolutOpened,
            onToggleChange = { isRevolutOpened = it }  // Only changes Revolut state
        )
    }
} 