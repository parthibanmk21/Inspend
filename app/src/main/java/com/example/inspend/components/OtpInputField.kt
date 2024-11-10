package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OtpInputField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    BasicTextField(
        value = otpText,
        onValueChange = {
            if (it.length <= 4) {
                onOtpTextChange(it)
            }
        },
        enabled = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {
                repeat(4) { index ->
                    val char = when {
                        index >= otpText.length -> ""
                        else -> otpText[index].toString()
                    }
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .background(
                                color = Color(0xFFF6F7F9),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = when {
                                    isError -> Color(0xFFB91C1C)
                                    char.isEmpty() -> Color(0xFFD5D9E2)
                                    else -> Color(0xFF526077)
                                },
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isError) Color(0xFFB91C1C) else Color(0xFF526077),
                            textAlign = TextAlign.Center
                        )
                    }
                    if (index < 3) Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
        modifier = Modifier.focusRequester(remember { FocusRequester() })
    )
}

@Preview(showBackground = true)
@Composable
fun OtpInputFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Empty state
        var emptyOtp by remember { mutableStateOf("") }
        OtpInputField(
            otpText = emptyOtp,
            onOtpTextChange = { emptyOtp = it }
        )

        // Partially filled state
        var partialOtp by remember { mutableStateOf("12") }
        OtpInputField(
            otpText = partialOtp,
            onOtpTextChange = { partialOtp = it }
        )

        // Completely filled state
        var fullOtp by remember { mutableStateOf("1234") }
        OtpInputField(
            otpText = fullOtp,
            onOtpTextChange = { fullOtp = it }
        )
    }
} 