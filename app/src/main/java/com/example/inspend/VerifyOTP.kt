package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VerifyOTPScreen(
    navController: NavController
) {
    var otpValue by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(59) }
    var canResend by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        canResend = true
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 24.dp)
            .background(Color(0xFFECEEF2)),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        AppBar(
            title = "Verify OTP",
            onBackClick = { 
                navController.navigate("resetpassword") {
                    launchSingleTop = true
                    popUpTo("resetpassword") { inclusive = true }
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 0.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column (
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ){
                        Text(
                            text = "Verify OTP",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Grey700
                        )

                        Text(
                            text = "Enter the code we sent to the email id {your email id}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Grey400
                        )
                    }

                    OtpInputField(
                        modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center,
                        otpText = otpValue,
                        onOtpTextChange = { otpValue = it },
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (canResend) {
                            Text(
                                text = "Resend OTP",
                                color = Grey700,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable {
                                        timeLeft = 59
                                        canResend = false
                                        scope.launch {
                                            while (timeLeft > 0) {
                                                delay(1000)
                                                timeLeft--
                                            }
                                            canResend = true
                                        }
                                    }
                            )
                        } else {
                            Text(
                                text = "Resend OTP in ${timeLeft}s",
                                color = Color(0xFF868EA1),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Column (
                        modifier = Modifier
                            .height(360.dp),
                        verticalArrangement = Arrangement.Bottom
                    ){
                        // Numpad
                        Numpad(
                            onNumberClick = {
                                if (otpValue.length < 4) {
                                    otpValue += it
                                }
                            },
                            onDeleteClick = {
                                if (otpValue.isNotEmpty()) {
                                    otpValue = otpValue.dropLast(1)
                                }
                            }
                        )
                    }

                }


            }
//            // Verify Button
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 32.dp),
//                text = "Verify",
//                onClick = { }
//            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(width = 1.dp, color = Color(0xFFD5D9E2))
                    .padding(top = 24.dp, bottom = 32.dp),
                contentAlignment = Alignment.Center
            )
            {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = Color(0xFF868EA1).copy(alpha = 0.75f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.15.sp,
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = "Log in",
                        color = Color(0xFF1F274B).copy(alpha = 0.75f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.15.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                navController.navigate("login") {
                                    launchSingleTop = true
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                    )
                }
            }
        }

    }
}

@Preview(
    name = "Verify OTP Screen",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun VerifyOTPScreenPreview() {
    InspendTheme {
        VerifyOTPScreen(
            navController = rememberNavController()
        )
    }
} 