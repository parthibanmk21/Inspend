package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    type: ChipType = ChipType.DEFAULT,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (type) {
        ChipType.DEFAULT -> Color(0xFFF6F7F9) to Color(0xFF526077)
        ChipType.ERROR -> Color(0xFFFECACA) to Color(0xFFEF4444)
        ChipType.SUCCESS -> Color(0xFFBBF7D1) to Color(0xFF16A349)
        ChipType.INFO -> Color(0xFFC0D8F7) to Color(0xFF1F6DC3)
        ChipType.DISABLED -> Color(0xFFF6F7F9) to Color(0xFFB1BBC8)
    }

    Row(
        modifier = modifier
            .width(80.dp)
            .height(28.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 16.sp,
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