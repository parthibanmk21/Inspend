package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inspend.R

enum class RadioButtonState {
    ENABLED,
    PRESSED,
    DISABLED
}

@Composable
fun RadioButton(
    selected: Boolean,
    onSelect: () -> Unit,
    state: RadioButtonState = RadioButtonState.ENABLED,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = state != RadioButtonState.DISABLED,
                onClick = onSelect
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    when {
                        state == RadioButtonState.PRESSED -> Color(0xFFE3ECFB)
                        else -> Color.Transparent
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = if (selected) R.drawable.radio_checked 
                    else R.drawable.radio_unchecked
                ),
                contentDescription = if (selected) "Selected" else "Not Selected",
                tint = when {
                    state == RadioButtonState.DISABLED -> Color(0xFF8695AA).copy(alpha = 0.38f)
                    selected -> Color(0xFF1F6DC3)
                    else -> Color(0xFF64748B)
                },
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonPreview() {
    Row(
        modifier = Modifier
            .width(240.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        // Selected States
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RadioButton(
                selected = true,
                onSelect = { },
                state = RadioButtonState.ENABLED
            )
            RadioButton(
                selected = true,
                onSelect = { },
                state = RadioButtonState.PRESSED
            )
            RadioButton(
                selected = true,
                onSelect = { },
                state = RadioButtonState.DISABLED
            )
        }

        // Unselected States
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RadioButton(
                selected = false,
                onSelect = { },
                state = RadioButtonState.ENABLED
            )
            RadioButton(
                selected = false,
                onSelect = { },
                state = RadioButtonState.PRESSED
            )
            RadioButton(
                selected = false,
                onSelect = { },
                state = RadioButtonState.DISABLED
            )
        }
    }
} 