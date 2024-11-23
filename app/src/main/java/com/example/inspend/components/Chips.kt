package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ChipType {
    DEFAULT,
    ERROR,
    SUCCESS,
    INFO,
    DISABLED
}

@Composable
fun Chip(
    text: String,
    type: ChipType,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(100.dp)
    
    Box(
        modifier = Modifier
            .clip(shape)  // Clip the ripple to pill shape
            .background(
                color = when {
                    isSelected && type == ChipType.ERROR -> Color(0xFFFF9A9A)
                    isSelected && type == ChipType.SUCCESS -> Color(0xFF83D386)
                    else -> Color(0xFFF6F7F9)
                },
                shape = shape
            )
            .border(
                width = 1.dp,
                color = when {
                    isSelected && type == ChipType.ERROR -> Color(0xFFB74B4B)
                    isSelected && type == ChipType.SUCCESS -> Color(0xFF68A76B)
                    else -> Color(0xFFD5D9E2)
                },
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = when {
                isSelected && type == ChipType.ERROR -> Color(0xFF8D3E3E)
                isSelected && type == ChipType.SUCCESS -> Color(0xFF325333)
                else -> Color(0xFF526077)
            },
            letterSpacing = 0.1.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChipsPreview() {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Chip(
            text = "Default",
            type = ChipType.DEFAULT
        )
        Chip(
            text = "Error",
            type = ChipType.ERROR
        )
        Chip(
            text = "Success",
            type = ChipType.SUCCESS
        )
        Chip(
            text = "Info",
            type = ChipType.INFO
        )
        Chip(
            text = "Disabled",
            type = ChipType.DISABLED
        )
    }
} 