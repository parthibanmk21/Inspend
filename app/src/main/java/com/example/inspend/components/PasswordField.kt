package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.inspend.R

@Composable
fun PasswordField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .width(296.dp)
            .height(66.dp)
            .padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Label
        Text(
            text = label,
            color = Color(0xFF526077),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp,
            lineHeight = 16.sp
        )

        // Input Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(
                    color = Color(0xFFF6F7F9),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = when {
                        isError -> Color(0xFFB91C1C)
                        isFocused -> Color(0xFF8695AA)
                        else -> Color(0xFFD5D9E2)
                    },
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.5.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF526077)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { isFocused = it.isFocused },
                    visualTransformation = if (isPasswordVisible) 
                        VisualTransformation.None 
                    else 
                        PasswordVisualTransformation(),
                    decorationBox = { innerTextField ->
                        Box {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = when {
                                        isError -> Color(0xFFB1BBC8)
                                        isFocused -> Color(0xFF8695AA)
                                        else -> Color(0xFFB1BBC8)
                                    },
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    letterSpacing = 0.5.sp,
                                    lineHeight = 20.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible) 
                                R.drawable.eyeclose
                            else 
                                R.drawable.eyeopen
                        ),
                        contentDescription = if (isPasswordVisible) 
                            "Hide password" 
                        else 
                            "Show password",
                        tint = Color(0xFF526077)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordFieldPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Default state
        var defaultValue by remember { mutableStateOf("") }
        PasswordField(
            label = "Password",
            placeholder = "Enter your password",
            value = defaultValue,
            onValueChange = { defaultValue = it }
        )

        // Typing state
        var typingValue by remember { mutableStateOf("password123") }
        PasswordField(
            label = "Password",
            placeholder = "Enter your password",
            value = typingValue,
            onValueChange = { typingValue = it }
        )

        // Error state
        var errorValue by remember { mutableStateOf("") }
        PasswordField(
            label = "Password",
            placeholder = "Enter your password",
            value = errorValue,
            onValueChange = { errorValue = it },
            isError = true
        )
    }
}

