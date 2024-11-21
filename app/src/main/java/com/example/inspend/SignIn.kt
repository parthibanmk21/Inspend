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
import androidx.compose.foundation.clickable
import com.example.inspend.components.AppBar
import com.example.inspend.components.ButtonType
import com.example.inspend.components.IconPosition
import com.example.inspend.components.InputField
import com.example.inspend.components.PasswordField
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.Grey400
import com.example.inspend.ui.theme.Grey700
import com.example.inspend.ui.theme.InspendTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

@Composable
fun SignInScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(BGdefault)
    ) {
        // AppBar
        AppBar(
            title = "Sign up",
            onBackClick = {
                navController.navigate("welcome") {
                    launchSingleTop = true
                    popUpTo("welcome") { inclusive = true }
                }
            }
        )

        // Wrapper Column for both SignIn Content and Login Row
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // SignIn Column Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Create your account",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Grey700
                )

                // Name Input with Error
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Name",
                        placeholder = "Enter your name",
                        value = name,
                        onValueChange = { 
                            name = it
                            nameError = ""
                        },
                        isError = nameError.isNotEmpty()
                    )
                    
                    if (nameError.isNotEmpty()) {
                        Text(
                            text = nameError,
                            color = Color(0xFFB91C1C),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.1.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Email Input with Error
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Email",
                        placeholder = "eg. sammy123@domain.com",
                        value = email,
                        onValueChange = { 
                            email = it
                            emailError = ""
                        },
                        isError = emailError.isNotEmpty()
                    )
                    
                    if (emailError.isNotEmpty()) {
                        Text(
                            text = emailError,
                            color = Color(0xFFB91C1C),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.1.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Password Input with Error
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Password",
                        placeholder = "Enter your password",
                        value = password,
                        onValueChange = { 
                            password = it
                            passwordError = ""
                        },
                        isError = passwordError.isNotEmpty()
                    )
                    
                    if (passwordError.isNotEmpty()) {
                        Text(
                            text = passwordError,
                            color = Color(0xFFB91C1C),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.1.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Confirm Password Input with Error
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Confirm Password",
                        placeholder = "Re-enter your password",
                        value = confirmPassword,
                        onValueChange = { 
                            confirmPassword = it
                            confirmPasswordError = ""
                        },
                        isError = confirmPasswordError.isNotEmpty()
                    )
                    
                    if (confirmPasswordError.isNotEmpty()) {
                        Text(
                            text = confirmPasswordError,
                            color = Color(0xFFB91C1C),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.1.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                // SignIn Button
                com.example.inspend.components.Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Create my account",
                    onClick = { 
                        // Validate inputs
                        var hasError = false
                        
                        if (name.isBlank()) {
                            nameError = "Name is required"
                            hasError = true
                        }
                        
                        if (email.isBlank()) {
                            emailError = "Email is required"
                            hasError = true
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            emailError = "Invalid email format"
                            hasError = true
                        }
                        
                        if (password.isBlank()) {
                            passwordError = "Password is required"
                            hasError = true
                        } else if (password.length < 6) {
                            passwordError = "Password must be at least 6 characters"
                            hasError = true
                        }
                        
                        if (confirmPassword.isBlank()) {
                            confirmPasswordError = "Please confirm your password"
                            hasError = true
                        } else if (password != confirmPassword) {
                            confirmPasswordError = "Passwords do not match"
                            hasError = true
                        }
                        
                        if (!hasError) {
                            coroutineScope.launch {
                                try {
                                    // Create user in Firebase Auth
                                    val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                                    val userId = authResult.user?.uid ?: throw Exception("Failed to create user")

                                    // Store additional user data in Firestore
                                    val userData = hashMapOf(
                                        "name" to name,
                                        "email" to email
                                    )

                                    // Save user data to Firestore
                                    db.collection("users").document(userId)
                                        .set(userData)
                                        .await()

                                    // Navigate to PIN setup
                                    navController.navigate("setsecuritypin") {
                                        launchSingleTop = true
                                        popUpTo("welcome") { inclusive = true }
                                    }
                                } catch (e: Exception) {
                                    when {
                                        e.message?.contains("email address is already in use") == true -> {
                                            emailError = "Email already exists"
                                        }
                                        e.message?.contains("password is invalid") == true -> {
                                            passwordError = "Password is too weak"
                                        }
                                        e.message?.contains("malformed or has expired") == true -> {
                                            emailError = "Invalid email format"
                                        }
                                        else -> {
                                            emailError = "Sign up failed"
                                            passwordError = "Please try again"
                                        }
                                    }
                                }
                            }
                        }
                    }
                )

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

                // Google SignIn Button
                com.example.inspend.components.Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Continue with Google",
                    onClick = { },
                    type = ButtonType.SECONDARY,
                    icon = painterResource(id = R.drawable.google),
                    iconPosition = IconPosition.LEFT
                )
            }

            // Login Row
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(width = 1.dp, color = Color(0xFFD5D9E2))
                    .padding(top = 24.dp, bottom = 32.dp)
                    .clickable {
                        navController.navigate("login") {
                            launchSingleTop = true
                            popUpTo("welcome") { inclusive = true }
                        }
                    },
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
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    InspendTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .background(BGdefault)
        ) {
            AppBar(
                title = "Sign up",
                onBackClick = { }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 0.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Create your account",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Grey700
                    )

                    // Input fields
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Name",
                        placeholder = "Enter your name",
                        value = "",
                        onValueChange = { },
                        isError = false
                    )

                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Email",
                        placeholder = "eg. sammy123@domain.com",
                        value = "",
                        onValueChange = { },
                        isError = false
                    )

                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Password",
                        placeholder = "Enter your password",
                        value = "",
                        onValueChange = { },
                        isError = false
                    )

                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Confirm Password",
                        placeholder = "Re-enter your password",
                        value = "",
                        onValueChange = { },
                        isError = false
                    )

                    com.example.inspend.components.Button(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Create my account",
                        onClick = { }
                    )

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

                    // Google SignIn Button
                    com.example.inspend.components.Button(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Continue with Google",
                        onClick = { },
                        type = ButtonType.SECONDARY,
                        icon = painterResource(id = R.drawable.google),
                        iconPosition = IconPosition.LEFT
                    )
                }

                // Login Row at bottom
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
                            letterSpacing = 0.15.sp
                        )
                        Text(
                            text = "Log in",
                            color = Color(0xFF1F274B).copy(alpha = 0.75f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.15.sp
                        )
                    }
                }
            }
        }
    }
}