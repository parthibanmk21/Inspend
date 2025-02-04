package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inspend.R
import androidx.compose.ui.tooling.preview.Preview
import com.example.inspend.ui.theme.InspendTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.clickable

@Composable
fun CategoryBottomSheet(
    selectedCategory: String = "Food",
    onCategorySelected: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onDismiss() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .clickable(
                        enabled = false,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { }
            ) {
                // Handle
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .width(32.dp)
                        .height(4.dp)
                        .background(
                            color = Color(0xFFE2E8F0),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .align(Alignment.CenterHorizontally)
                )

                // Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Select the category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF434E61)
                    )
                    
                    // First Row - Food, Travel, Shopping
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CategoryChip(
                            iconRes = R.drawable.food,
                            categoryName = "Food",
                            isSelected = selectedCategory == "Food",
                            onClick = { onCategorySelected("Food") }
                        )
                        CategoryChip(
                            iconRes = R.drawable.travel,
                            categoryName = "Travel",
                            isSelected = selectedCategory == "Travel",
                            onClick = { onCategorySelected("Travel") }
                        )
                        CategoryChip(
                            iconRes = R.drawable.shopping,
                            categoryName = "Shopping",
                            isSelected = selectedCategory == "Shopping",
                            onClick = { onCategorySelected("Shopping") }
                        )
                    }
                    
                    // Second Row - Transfer, Other
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CategoryChip(
                            iconRes = R.drawable.transfer,
                            categoryName = "Transfer",
                            isSelected = selectedCategory == "Transfer",
                            onClick = { onCategorySelected("Transfer") }
                        )
                        CategoryChip(
                            iconRes = R.drawable.other,
                            categoryName = "Other",
                            isSelected = selectedCategory == "Other",
                            onClick = { onCategorySelected("Other") }
                        )
                    }
                }
                
                content()
            }
        }
    }
}

// Preview for just the content
@Preview(name = "Category Content", showBackground = true)
@Composable
private fun CategoryContentPreview() {
    InspendTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Select the category",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF434E61)
            )
            
            // First Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CategoryChip(
                    iconRes = R.drawable.food,
                    categoryName = "Food",
                    isSelected = true,
                    onClick = {}
                )
                CategoryChip(
                    iconRes = R.drawable.travel,
                    categoryName = "Travel",
                    isSelected = false,
                    onClick = {}
                )
                CategoryChip(
                    iconRes = R.drawable.shopping,
                    categoryName = "Shopping",
                    isSelected = false,
                    onClick = {}
                )
            }
            
            // Second Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CategoryChip(
                    iconRes = R.drawable.transfer,
                    categoryName = "Transfer",
                    isSelected = false,
                    onClick = {}
                )
                CategoryChip(
                    iconRes = R.drawable.other,
                    categoryName = "Other",
                    isSelected = false,
                    onClick = {}
                )
            }
        }
    }
}

// Preview for full sheet with overlay
@Preview(name = "Full Category Sheet", showBackground = true, heightDp = 800)
@Composable
private fun CategoryBottomSheetPreview() {
    InspendTheme {
        CategoryBottomSheet(
            onDismiss = {}
        ) {
            // Empty content for preview
        }
    }
} 