package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences
    )
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF526077),
            lineHeight = 16.sp,
            letterSpacing = 0.1.sp
        )
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
                .fillMaxWidth()
                .height(46.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF6F7F9), RoundedCornerShape(4.dp))
                .border(
                    width = 1.dp,
                    color = when {
                        isError -> Color(0xFFFF3B3B)
                        isFocused -> Color(0xFF526077)
                        else -> Color(0xFFD5D9E2)
                    },
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 12.dp)
                .onFocusChanged { isFocused = it.isFocused },
            keyboardOptions = keyboardOptions,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Default state
        var defaultValue by remember { mutableStateOf("") }
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = defaultValue,
            onValueChange = { defaultValue = it }
        )

        // Typing state
        var typingValue by remember { mutableStateOf("Placeholder") }
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = typingValue,
            onValueChange = { typingValue = it }
        )

        // Error state
        var errorValue by remember { mutableStateOf("") }
        InputField(
            label = "Label",
            placeholder = "Placeholder",
            value = errorValue,
            onValueChange = { errorValue = it },
            isError = true
        )
    }
} 