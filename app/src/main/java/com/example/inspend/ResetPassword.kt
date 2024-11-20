package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import com.example.inspend.components.InputField
import com.example.inspend.ui.theme.Grey400
import com.example.inspend.ui.theme.Grey700
import com.example.inspend.ui.theme.InspendTheme

@Composable
fun ResetPasswordScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(Color(0xFFECEEF2)),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // AppBar
        AppBar(
            title = "Reset Password",
            onBackClick = { 
                navController.navigate("login") {
                    launchSingleTop = true
                    popUpTo("login") { inclusive = true }
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween

        )

        {
            // Content Column
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Text(
                        text = "Verify your Email",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Grey700
                    )

                    Text(
                        text = "We will send an OTP Verification to Your Email.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Grey400
                    )
                }

                // Email Input
                var email by remember { mutableStateOf("") }
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Enter your Email",
                    placeholder = "eg. sammy123@domain.com",
                    value = email,
                    onValueChange = { email = it }
                )

                // Reset Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Send me the code",
                    onClick = { 
                        navController.navigate("verifyotp") {
                            launchSingleTop = true
                        }
                    }
                )

                // Back to Sign in
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth(),
////                    .height(69.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Wrong mail? ",
//                            color = Color(0xFF868EA1).copy(alpha = 0.75f),
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Medium,
//                            letterSpacing = 0.15.sp
//                        )
//                        Text(
//                            text = "Back to Sign in",
//                            color = Color(0xFF1F274B).copy(alpha = 0.75f),
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            letterSpacing = 0.15.sp,
//                            modifier = Modifier
//                                .wrapContentWidth()
//                                .clickable {
//                                    navController.navigate("login") {
//                                        launchSingleTop = true
//                                        popUpTo("login") { inclusive = true }
//                                    }
//                                }
//                        )
//                    }
//                }

            }
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
                        modifier = Modifier.wrapContentWidth()
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

@Preview
//    (
//    name = "Reset Password Screen",
//    showBackground = true,
//    device = "id:pixel_5"
//)
@Composable
fun ResetPasswordScreenPreview() {
    InspendTheme {
        ResetPasswordScreen(
            navController = rememberNavController()
        )
    }
} 