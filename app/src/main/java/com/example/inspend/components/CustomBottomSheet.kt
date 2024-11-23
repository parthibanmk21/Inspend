package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.R
import com.example.inspend.ui.theme.InspendTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    onDismiss: () -> Unit,
    title: String? = null,
    showDragHandle: Boolean = true,
    content: @Composable () -> Unit
) {
    // Overlay (Scrim)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    )

    // Bottom Sheet
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        dragHandle = if (showDragHandle) {
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .background(
                                color = Color(0xFF797E7E).copy(alpha = 0.4f),
                                shape = RoundedCornerShape(100.dp)
                            )
                    )
                }
            }
        } else null,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        tonalElevation = 0.dp,
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Optional title
            if (title != null) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            
            content()
        }
    }
}

// Preview for just the overlay
@Preview(name = "Bottom Sheet Overlay")
@Composable
fun BottomSheetOverlayPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    )
}

// Preview for just the bottom sheet content
@Preview(name = "Bottom Sheet Content")
@Composable
fun BottomSheetContentPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Drag Handle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(
                            color = Color(0xFF797E7E).copy(alpha = 0.4f),
                            shape = RoundedCornerShape(100.dp)
                        )
                )
            }

            // Content
            Text(
                text = "Select Payment Method",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF526077),
                lineHeight = 24.sp
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PaymentType(
                    icon = R.drawable.wallet,
                    name = "Wallet",
                    balance = "1,000",
                    isSelected = true,
                    onClick = {}
                )
                PaymentType(
                    icon = R.drawable.trust,
                    name = "Trust",
                    balance = "1,000",
                    isSelected = false,
                    onClick = {}
                )
            }
        }
    }
}

// Full bottom sheet preview with overlay
@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Full Bottom Sheet with Overlay")
@Composable
fun FullBottomSheetPreview() {
    InspendTheme {
        CustomBottomSheet(
            onDismiss = {}
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Select Payment Method",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    lineHeight = 24.sp
                )
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PaymentType(
                        icon = R.drawable.wallet,
                        name = "Wallet",
                        balance = "1,000",
                        isSelected = true,
                        onClick = {}
                    )
                    PaymentType(
                        icon = R.drawable.trust,
                        name = "Trust",
                        balance = "1,000",
                        isSelected = false,
                        onClick = {}
                    )
                }
            }
        }
    }
}