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
import com.example.inspend.components.PasswordField
import com.example.inspend.ui.theme.Grey400
import com.example.inspend.ui.theme.Grey700
import com.example.inspend.ui.theme.InspendTheme

@Composable
fun ForgotPasswordScreen(
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
            title = "Create New Password",
            onBackClick = { 
                navController.navigate("verifyotp") {
                    launchSingleTop = true
                    popUpTo("verifyotp") { inclusive = true }
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Content Column
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Text(
                        text = "Create new password",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Grey700
                    )

                    Text(
                        text = "Your new password must be different from previous used passwords.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp,
                        color = Grey400
                    )
                }

                // Password Input
                var password by remember { mutableStateOf("") }
                PasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Password",
                    placeholder = "Enter your password",
                    value = password,
                    onValueChange = { password = it }
                )

                // Confirm Password Input
                var confirmPassword by remember { mutableStateOf("") }
                PasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Confirm Password",
                    placeholder = "Re-enter your password",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it }
                )

                // Reset Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Reset Password",
                    onClick = { }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(width = 1.dp, color = Color(0xFFD5D9E2))
                    .padding(top = 24.dp, bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
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

@Preview(
    name = "Forgot Password Screen",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun ForgotPasswordScreenPreview() {
    InspendTheme {
        ForgotPasswordScreen(
            navController = rememberNavController()
        )
    }
} 