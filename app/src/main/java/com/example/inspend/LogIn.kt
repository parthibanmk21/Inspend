package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inspend.components.AppBar
import com.example.inspend.components.ButtonType
import com.example.inspend.components.IconPosition
import com.example.inspend.components.InputField
import com.example.inspend.components.PasswordField
import com.example.inspend.ui.theme.Grey400
import com.example.inspend.ui.theme.Grey700
import androidx.compose.foundation.clickable

@Composable
fun LogInScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(Color(0xFFECEEF2))
    ) {
        // AppBar
        AppBar(
            title = "Log in",
            onBackClick = { navController.navigate("welcome") }
        )

        // Wrapper Column for both Login Content and New User Row
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Login Column Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Log in to your account",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Grey700
                )

                // Email Input
                var email by remember { mutableStateOf("") }
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Email",
                    placeholder = "eg. sammy123@domain.com",
                    value = email,
                    onValueChange = { email = it }
                )

                // Password Input
                var password by remember { mutableStateOf("") }
                PasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Password",
                    placeholder = "Enter your password",
                    value = password,
                    onValueChange = { password = it }
                )

                // Login Button using custom component
                com.example.inspend.components.Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Log in",
                    onClick = { }
                )

                // Forget Password Row
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(69.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Forget password?",
                            color = Grey400.copy(alpha = 0.75f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.15.sp,
                            modifier = Modifier.width(120.dp)
                        )
                        Text(
                            text = "Reset it",
                            color = Grey700.copy(alpha = 0.75f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.15.sp,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }

                // Divider with "or"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFD5D9E2).copy(alpha = 0.5f),
                        thickness = 2.dp
                    )
                    Text(
                        text = "or",
                        color = Color(0xFF526077).copy(alpha = 0.75f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFD5D9E2).copy(alpha = 0.5f),
                        thickness = 2.dp
                    )
                }

                // Google Login Button
                com.example.inspend.components.Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Log in with Google",
                    onClick = { },
                    type = ButtonType.SECONDARY,
                    icon = painterResource(id = R.drawable.google),
                    iconPosition = IconPosition.LEFT
                )
            }

            // New User Row
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
                        text = "New User? ",
                        color = Color(0xFF868EA1).copy(alpha = 0.75f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.15.sp,
                        modifier = Modifier.width(74.dp)
                    )
                    Text(
                        text = "Create Account",
                        color = Color(0xFF1F274B).copy(alpha = 0.75f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.15.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable { navController.navigate("signin") }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInScreenPreview() {
    // Use a dummy NavController for preview
    LogInScreen(
        navController = rememberNavController()
    )
}