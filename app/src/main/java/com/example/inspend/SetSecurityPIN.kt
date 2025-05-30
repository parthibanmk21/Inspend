package com.example.inspend

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inspend.components.AppBar
import com.example.inspend.components.Button
import com.example.inspend.components.Numpad
import com.example.inspend.components.OtpInputField
import com.example.inspend.ui.theme.Grey400
import com.example.inspend.ui.theme.Grey700
import com.example.inspend.ui.theme.InspendTheme
import com.example.inspend.components.BiometricDialog
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.Error700
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SetSecurityPINScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var pinValue by remember { mutableStateOf("") }
    var confirmPinValue by remember { mutableStateOf("") }
    var isPinConfirmation by remember { mutableStateOf(false) }
    var showBiometricDialog by remember { mutableStateOf(false) }
    var isPinError by remember { mutableStateOf(false) }

    // Effect to handle PIN completion
    LaunchedEffect(pinValue) {
        if (pinValue.length == 4 && !isPinConfirmation) {
            // Automatically move to confirmation when first PIN is complete
            isPinConfirmation = true
        }
    }

    // Effect to handle confirmation PIN completion
    LaunchedEffect(confirmPinValue) {
        if (isPinConfirmation && confirmPinValue.length == 4) {
            if (pinValue == confirmPinValue) {
                showBiometricDialog = true
            } else {
                isPinError = true
                confirmPinValue = ""
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .background(BGdefault)
        ) {
            AppBar(
                title = if (isPinConfirmation) "Confirm your PIN" else "Set Security PIN",
                onBackClick = { 
                    if (isPinConfirmation) {
                        isPinConfirmation = false
                        confirmPinValue = ""
                        isPinError = false
                    } else {
                        navController.navigateUp()
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    Column (
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        Text(
                            text = if (isPinConfirmation) "Confirm your PIN" else "Set Security PIN",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Grey700
                        )

                        Text(
                            text = if (isPinConfirmation)
                                "Re-enter your PIN to confirm your account more secure."
                            else
                                "Add a PIN number to make your account more secure.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 18.sp,
                            color = Grey400
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OtpInputField(
                            otpText = if (isPinConfirmation) confirmPinValue else pinValue,
                            onOtpTextChange = { 
                                if (isPinConfirmation) {
                                    confirmPinValue = it
                                    isPinError = false
                                } else {
                                    pinValue = it
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isError = isPinError
                        )
                        
                        if (isPinError) {
                            Column (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "Incorrect PIN",
                                    color = Color(0xFFB91C1C),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Numpad(
                    onNumberClick = { 
                        if (isPinConfirmation) {
                            if (confirmPinValue.length < 4) {
                                confirmPinValue += it
                                isPinError = false
                            }
                        } else {
                            if (pinValue.length < 4) {
                                pinValue += it
                            }
                        }
                    },
                    onDeleteClick = {
                        if (isPinConfirmation) {
                            if (confirmPinValue.isNotEmpty()) {
                                confirmPinValue = confirmPinValue.dropLast(1)
                                isPinError = false
                            }
                        } else {
                            if (pinValue.isNotEmpty()) {
                                pinValue = pinValue.dropLast(1)
                            }
                        }
                    }
                )

//                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (showBiometricDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                BiometricDialog(
                    onDismiss = { useBiometric ->
                        showBiometricDialog = false
                        
                        // Store PIN and biometric preference in Firestore
                        coroutineScope.launch {
                            try {
                                val userId = auth.currentUser?.uid
                                if (userId != null) {
                                    // Update user document with PIN and biometric preference
                                    db.collection("users").document(userId)
                                        .update(mapOf(
                                            "securityPin" to pinValue,
                                            "useBiometric" to useBiometric
                                        ))
                                        .await()
                                    
                                    // Navigate to transaction type screen
                                    navController.navigate("transactiontype") {
                                        popUpTo("welcome") { inclusive = true }
                                    }
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Failed to save settings: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(
    name = "Set Security PIN Screen",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun SetSecurityPINScreenPreview() {
    InspendTheme {
        // Preview version without Firebase dependencies
        var pinValue by remember { mutableStateOf("") }
        var confirmPinValue by remember { mutableStateOf("") }
        var isPinConfirmation by remember { mutableStateOf(false) }
        var isPinError by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)
                    .background(BGdefault)
            ) {
                AppBar(
                    title = if (isPinConfirmation) "Confirm your PIN" else "Set Security PIN",
                    onBackClick = { 
                        if (isPinConfirmation) {
                            isPinConfirmation = false
                            confirmPinValue = ""
                            isPinError = false
                        }
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (isPinConfirmation) "Confirm your PIN" else "Set Security PIN",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Grey700
                            )

                            Text(
                                text = if (isPinConfirmation)
                                    "Re-enter your PIN to confirm your account more secure."
                                else
                                    "Add a PIN number to make your account more secure.",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 18.sp,
                                color = Grey400
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OtpInputField(
                                otpText = if (isPinConfirmation) confirmPinValue else pinValue,
                                onOtpTextChange = { 
                                    if (isPinConfirmation) {
                                        confirmPinValue = it
                                        isPinError = false
                                    } else {
                                        pinValue = it
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                isError = isPinError
                            )
                            
                            if (isPinError) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Incorrect PIN",
                                        color = Error700,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Numpad(
                        onNumberClick = { 
                            if (isPinConfirmation) {
                                if (confirmPinValue.length < 4) {
                                    confirmPinValue += it
                                    isPinError = false
                                }
                            } else {
                                if (pinValue.length < 4) {
                                    pinValue += it
                                }
                            }
                        },
                        onDeleteClick = {
                            if (isPinConfirmation) {
                                if (confirmPinValue.isNotEmpty()) {
                                    confirmPinValue = confirmPinValue.dropLast(1)
                                    isPinError = false
                                }
                            } else {
                                if (pinValue.isNotEmpty()) {
                                    pinValue = pinValue.dropLast(1)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
} 