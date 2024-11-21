package com.example.inspend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.inspend.ui.theme.InspendTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.inspend.components.Button
import com.example.inspend.components.ButtonType
import com.example.inspend.components.IconPosition
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.view.WindowCompat
import androidx.compose.foundation.clickable
import com.example.inspend.ui.theme.Brand500
import com.example.inspend.ui.theme.Grey600
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import com.example.inspend.ui.theme.BGdefault
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        enableEdgeToEdge()

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set the status bar color
        window.statusBarColor = android.graphics.Color.parseColor("#434E61") // Dark color

        // Set the status bar icon color to light
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = false

        setContent {
            InspendTheme {
                val navController = rememberNavController()

                // Track navigation order
                val navigationOrder = remember { mutableStateOf(0) }

                NavHost(
                    navController = navController,
                    startDestination = "welcome"
                ) {
                    composable(
                        route = "welcome",
                        enterTransition = {
                            if (navigationOrder.value > 0) {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(0)
                                )
                            } else {
                                null
                            }
                        },
                        exitTransition = {
                            navigationOrder.value++
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(0)
                            )
                        }
                    ) {
                        WelcomeScreen(
                            onEmailLoginClick = {
                                navController.navigate("login")
                            },
                            onCreateAccountClick = {
                                navController.navigate("signin")
                            }
                        )
                    }
                    composable(
                        route = "login",
                        enterTransition = {
                            if (navigationOrder.value > 1) {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(0)
                                )
                            } else {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(0)
                                )
                            }
                        },
                        exitTransition = {
                            navigationOrder.value++
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(0)
                            )
                        }
                    ) {
                        LogInScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = "signin",
                        enterTransition = {
                            if (navigationOrder.value > 2) {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(0)
                                )
                            } else {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(0)
                                )
                            }
                        },
                        exitTransition = {
                            navigationOrder.value++
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(0)
                            )
                        }
                    ) {
                        SignInScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = "resetpassword",
                        enterTransition = {
                            if (navigationOrder.value > 3) {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(0)
                                )
                            } else {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(0)
                                )
                            }
                        },
                        exitTransition = {
                            navigationOrder.value++
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(0)
                            )
                        }
                    ) {
                        ResetPasswordScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = "verifyotp",
                        enterTransition = {
                            if (navigationOrder.value > 4) {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(0)
                                )
                            } else {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(0)
                                )
                            }
                        },
                        exitTransition = {
                            navigationOrder.value++
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(0)
                            )
                        }
                    ) {
                        VerifyOTPScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = "forgotpassword",
                        enterTransition = {
                            if (navigationOrder.value > 5) {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(0)
                                )
                            } else {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(0)
                                )
                            }
                        },
                        exitTransition = {
                            navigationOrder.value++
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(0)
                            )
                        }
                    ) {
                        ForgotPasswordScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = "setsecuritypin",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(0)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(0)
                            )
                        }
                    ) {
                        SetSecurityPINScreen(
                            navController = navController
                        )
                    }
                    composable("transactiontype") {
                        TransactionTypeScreen(
                            navController = navController
                        )
                    }
                    composable("home") {
                        HomePage(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun WelcomeScreen(
    onEmailLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BGdefault)
                .padding(horizontal = 16.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Brand Column
            Column(
                modifier = Modifier.width(328.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo and Brand Name
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.welcomelogo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Inspend",
                        color = Color(0xFF145CB5),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 26.sp
                    )
                }

                // Welcome Content
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Track",
                            color = Brand500,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            lineHeight = 46.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Your",
                            color = (Grey600).copy(alpha = 0.5f),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            lineHeight = 46.sp
                        )
                    }
                    Text(
                        text = "Spending, Own",
                        color = (Grey600).copy(alpha = 0.5f),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp,
                        lineHeight = 46.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Your",
                            color = (Grey600).copy(alpha = 0.5f),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            lineHeight = 46.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Finances.",
                            color = Brand500,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            lineHeight = 46.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Button Column
            Column(
                modifier = Modifier.width(328.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    text = "Log in with Email",
                    onClick = onEmailLoginClick,
                    icon = painterResource(id = R.drawable.mail),
                    iconPosition = IconPosition.LEFT
                )

                // Divider with "or"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 2.dp,
                        color = Color(0xFFD5D9E2).copy(alpha = 0.5f)
                    )
                    Text(
                        text = "or",
                        color = Color(0xFF526077).copy(alpha = 0.75f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.25.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 2.dp,
                        color = Color(0xFFD5D9E2).copy(alpha = 0.5f)
                    )
                }

                Button(
                    text = "Log in with Google",
                    onClick = { /* TODO */ },
                    type = ButtonType.SECONDARY,
                    icon = painterResource(id = R.drawable.google),
                    iconPosition = IconPosition.LEFT
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // New User Text
            Row(
                modifier = Modifier
                    .width(328.dp)
                    .clickable { onCreateAccountClick() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New User? ",
                    color = Color(0xFF868EA1).copy(alpha = 0.75f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.15.sp
                )
                Text(
                    text = "Create Account",
                    color = Color(0xFF1F274B).copy(alpha = 0.75f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.15.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                )
            }
        }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenFullPreview() {
    InspendTheme {
        WelcomeScreen(
            onEmailLoginClick = {},
            onCreateAccountClick = {}
        )
    }
}

