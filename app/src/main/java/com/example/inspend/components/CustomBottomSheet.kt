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
    // Sample balances for preview
    val sampleBalances = mapOf(
        "WALLET" to "1,250.50",
        "TRUST" to "3,500.75",
        "DBS" to "2,800.25",
        "REVOLUT" to "1,750.80"
    )
    
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
                // Wallet Payment Type
                PaymentType(
                    icon = R.drawable.wallet,
                    name = "Wallet",
                    balance = sampleBalances["WALLET"] ?: "0.00",
                    isSelected = true,
                    onClick = { }
                )

                // Trust Payment Type
                PaymentType(
                    icon = R.drawable.trust,
                    name = "Trust",
                    balance = sampleBalances["TRUST"] ?: "0.00",
                    isSelected = false,
                    onClick = { }
                )

                // DBS Payment Type
                PaymentType(
                    icon = R.drawable.dbs,
                    name = "DBS",
                    balance = sampleBalances["DBS"] ?: "0.00",
                    isSelected = false,
                    onClick = { }
                )

                // Revolut Payment Type
                PaymentType(
                    icon = R.drawable.revolut,
                    name = "Revolut",
                    balance = sampleBalances["REVOLUT"] ?: "0.00",
                    isSelected = false,
                    onClick = { }
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
        // Sample balances for preview
        val sampleBalances = mapOf(
            "WALLET" to "1,250.50",
            "TRUST" to "3,500.75",
            "DBS" to "2,800.25",
            "REVOLUT" to "1,750.80"
        )

        CustomBottomSheet(
            onDismiss = {},
            title = "Select Payment Method"
        ) {
            PaymentBottomSheetContent(
                bankBalances = sampleBalances,
                selectedPayment = "Wallet",
                onPaymentSelected = {}
            )
        }
    }
}

@Composable
fun PaymentSelectionSheet(
    bankBalances: Map<String, String>,
    selectedPayment: String?,
    onPaymentSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    CustomBottomSheet(
        onDismiss = onDismiss,
        title = "Select Payment Method"
    ) {
        PaymentBottomSheetContent(
            bankBalances = bankBalances,
            selectedPayment = selectedPayment,
            onPaymentSelected = onPaymentSelected
        )
    }
}