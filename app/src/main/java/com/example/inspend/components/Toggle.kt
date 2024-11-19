package com.example.inspend.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inspend.ui.theme.Brand500

@Composable
fun Toggle(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val toggleWidth = 52.dp
    val toggleHeight = 26.dp
    val knobSize = 16.dp
    
    Box(
        modifier = modifier
            .width(toggleWidth)
            .height(toggleHeight)
            .clip(CircleShape)
            .background(
                when {
                    !enabled -> Color.White
                    isChecked -> Brand500
                    else -> Color.White
                }
            )
            .border(
                width = 2.dp,
                color = when {
                    !enabled -> Color(0xFFD5D9E2)
                    isChecked -> Brand500
                    else -> Color(0xFF8695AA)
                },
                shape = CircleShape
            )
            .clickable(enabled = enabled) { onCheckedChange(!isChecked) }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(knobSize)
                .align(if (isChecked) Alignment.CenterEnd else Alignment.CenterStart)
                .clip(CircleShape)
                .background(
                    when {
                        !enabled -> Color(0xFFD5D9E2)
                        isChecked -> Color(0xFFF1F6FE)
                        else -> Color(0xFF647487)
                    }
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TogglePreview() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Enabled - Checked
        var isChecked1 by remember { mutableStateOf(true) }
        Toggle(
            isChecked = isChecked1,
            onCheckedChange = { isChecked1 = it }
        )

        // Enabled - Unchecked
        var isChecked2 by remember { mutableStateOf(false) }
        Toggle(
            isChecked = isChecked2,
            onCheckedChange = { isChecked2 = it }
        )

        // Disabled - Checked
        Toggle(
            isChecked = true,
            onCheckedChange = { },
            enabled = false
        )

        // Disabled - Unchecked
        Toggle(
            isChecked = false,
            onCheckedChange = { },
            enabled = false
        )
    }
} 