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
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {
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
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Custom Bottom Sheet",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CustomBottomSheetPreview() {
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
                    PaymentType(
                        icon = R.drawable.dbs,
                        name = "DBS",
                        balance = "1,000",
                        isSelected = false,
                        onClick = {}
                    )
                }
            }
        }
    }
}

// Preview remains the same... 