package com.example.inspend.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.inspend.R
import com.example.inspend.ui.theme.*
import com.example.inspend.utils.BiometricHelper

@Composable
fun BiometricDialog(
    onDismiss: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .width(328.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFD5D9E2),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BGdefault)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Dialog Top
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.biometric),
                    contentDescription = "Biometric",
                    tint = Color(0xFF526077),
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "Use Biometric?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF393F71),
                    letterSpacing = 0.sp
                )

                Text(
                    text = "Would you like to enable the biometric instead of using your passcode?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    letterSpacing = 0.15.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Dialog Bottom
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Yes, enable Biometric",
                    onClick = { onDismiss(true) }
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Not Now",
                    onClick = { onDismiss(false) },
                    type = ButtonType.SECONDARY
                )
            }
        }
    }
}

@Preview(
    name = "Biometric Dialog",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun BiometricDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        BiometricDialog(
            onDismiss = { }
        )
    }
}

@Preview(
    name = "Biometric Dialog (Component Only)",
    showBackground = true
)
@Composable
fun BiometricDialogComponentPreview() {
    BiometricDialog(
        onDismiss = { }
    )
} 