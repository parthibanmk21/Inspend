package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inspend.R
import com.example.inspend.ui.theme.*

enum class CategoryType {
    INCOME, EXPENSE
}

@Composable
fun CategoryIcon(
    type: CategoryType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(
                color = Color(0xFFECEEF2),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = Color(0xFFE4E6EE),
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = when (type) {
                    CategoryType.INCOME -> R.drawable.uparrow
                    CategoryType.EXPENSE -> R.drawable.downarrow
                }
            ),
            contentDescription = if (type == CategoryType.INCOME) "Income" else "Expense",
            tint = Grey400,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CategoryIconRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(112.dp)
            .height(48.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon(type = CategoryType.INCOME)
        CategoryIcon(type = CategoryType.EXPENSE)
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryIconPreview() {
    CategoryIconRow()
} 