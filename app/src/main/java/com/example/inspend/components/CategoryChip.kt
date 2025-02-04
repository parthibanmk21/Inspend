package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import com.example.inspend.R

@Composable
fun CategoryChip(
    iconRes: Int,
    categoryName: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF114993) else Color(0xFFECEEF2)
    val contentColor = if (isSelected) Color.White else Color(0xFF64748B)

    Row(
        modifier = Modifier
            .height(32.dp)
            .wrapContentWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = categoryName,
            color = contentColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryChipPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Default state
        CategoryChip(
            iconRes = R.drawable.food,
            categoryName = "Food",
            isSelected = false,
            onClick = {}
        )

        // Selected state
        CategoryChip(
            iconRes = R.drawable.food,
            categoryName = "Food",
            isSelected = true,
            onClick = {}
        )
    }
}