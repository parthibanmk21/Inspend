package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.inspend.R

enum class ButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

enum class IconPosition {
    LEFT,
    RIGHT,
    NONE
}

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    type: ButtonType = ButtonType.PRIMARY,
    icon: Painter? = null,
    iconPosition: IconPosition = IconPosition.NONE,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val height = when(type) {
        ButtonType.TERTIARY -> 28.dp
        else -> 44.dp
    }

    val backgroundColor = when(type) {
        ButtonType.PRIMARY -> Color(0xFF1F6DC3)
        else -> Color.White
    }

    val textColor = when(type) {
        ButtonType.PRIMARY -> Color(0xFFF1F6FE)
        else -> Color(0xFF64748B)
    }

    val borderModifier = when(type) {
        ButtonType.SECONDARY -> Modifier.border(
            width = 1.dp,
            color = Color(0xFFD5D9E2),
            shape = RoundedCornerShape(4.dp)
        )
        else -> Modifier
    }

    Row(
        modifier = modifier
            .then(borderModifier)
            .width(328.dp)
            .height(height)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(
                horizontal = 32.dp,
                vertical = when(type) {
                    ButtonType.TERTIARY -> 4.dp
                    else -> 12.dp
                }
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconPosition == IconPosition.LEFT && icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = if (type == ButtonType.SECONDARY) Color.Unspecified else textColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.1.sp,
            lineHeight = 20.sp
        )

        if (iconPosition == IconPosition.RIGHT && icon != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = icon,
                contentDescription = null,
                tint = if (type == ButtonType.SECONDARY) Color.Unspecified else textColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Primary Button
        Button(
            text = "Button",
            onClick = { }
        )

        // Primary Button with left icon
        Button(
            text = "Button",
            onClick = { },
            icon = painterResource(id = R.drawable.google),
            iconPosition = IconPosition.LEFT
        )

        // Primary Button with right icon
        Button(
            text = "Button",
            onClick = { },
            icon = painterResource(id = R.drawable.google),
            iconPosition = IconPosition.RIGHT
        )

        // Secondary Button
        Button(
            text = "Button",
            onClick = { },
            type = ButtonType.SECONDARY
        )

        // Secondary Button with left icon
        Button(
            text = "Button",
            onClick = { },
            type = ButtonType.SECONDARY,
            icon = painterResource(id = R.drawable.google),
            iconPosition = IconPosition.LEFT
        )

        // Secondary Button with right icon
        Button(
            text = "Button",
            onClick = { },
            type = ButtonType.SECONDARY,
            icon = painterResource(id = R.drawable.google),
            iconPosition = IconPosition.RIGHT
        )

        // Tertiary Button
        Button(
            text = "Button",
            onClick = { },
            type = ButtonType.TERTIARY
        )
    }
} 