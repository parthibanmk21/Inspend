package com.example.inspend

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.inspend.ui.theme.Grey100
import com.example.inspend.ui.theme.Grey600
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import com.example.inspend.components.AppBar
import com.example.inspend.components.Button
import com.example.inspend.components.ButtonType
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

object CurrencyManager {
    var selectedCurrency by mutableStateOf(
        Currency("USD", "US Dollar", "$")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController? = null) {
    var showLogoutSheet by remember { mutableStateOf(false) }
    var showLanguageSheet by remember { mutableStateOf(false) }
    var showCurrencySheet by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()

    val currencies = listOf(
        Currency("USD", "US Dollar", "$"),
        Currency("INR", "Indian Rupee", "₹"),
        Currency("EUR", "Euro", "€"),
        Currency("GBP", "British Pound", "£"),
        Currency("JPY", "Japanese Yen", "¥")
    )

    if (showCurrencySheet) {
        ModalBottomSheet(
            onDismissRequest = { showCurrencySheet = false },
            dragHandle = { 
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color(0xFFD5D9E2)
                    ) {}
                }
            },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Select Currency",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3A4252)
                )

                currencies.forEach { currency ->
                    CurrencyOption(
                        currency = "${currency.code} - ${currency.name}",
                        isSelected = currency.code == CurrencyManager.selectedCurrency.code,
                        onClick = {
                            CurrencyManager.selectedCurrency = currency
                            showCurrencySheet = false
                        }
                    )
                }
            }
        }
    }

    if (showLanguageSheet) {
        ModalBottomSheet(
            onDismissRequest = { showLanguageSheet = false },
            dragHandle = { 
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color(0xFFD5D9E2)
                    ) {}
                }
            },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Select Language",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3A4252)
                )

                LanguageOption(
                    language = "English",
                    isSelected = true,
                    onClick = { showLanguageSheet = false }
                )
                LanguageOption(
                    language = "தமிழ்",
                    isSelected = false,
                    onClick = { showLanguageSheet = false }
                )
                LanguageOption(
                    language = "हिंदी",
                    isSelected = false,
                    onClick = { showLanguageSheet = false }
                )
            }
        }
    }

    if (showLogoutSheet) {
        AlertDialog(
            onDismissRequest = { showLogoutSheet = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(8.dp),
            title = {
                Text(
                    text = "Logging out",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp
                )
            },
            text = {
                Text(
                    text = "Just making sure you didn't tap Log out by accident.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF526077),
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            confirmButton = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        text = "Cancel",
                        onClick = { showLogoutSheet = false },
                        type = ButtonType.SECONDARY,
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        text = "Yes, Log out",
                        onClick = {
                            auth.signOut()
                            navController?.navigate("welcome") {
                                popUpTo("welcome") {
                                    inclusive = true
                                }
                            }
                        },
                        type = ButtonType.PRIMARY,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp),
    ) {
        // AppBar
        AppBar(
            title = "Settings",
//            onBackClick = onBackClick
        )

        // Settings Body
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECEEF2))
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // General Section
                Text(
                    text = "General",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3A4252),
                    lineHeight = 16.sp,
                )

                SettingsSection {
                    SettingsItem(
                        iconResId = R.drawable.usericon,
                        title = "Profile",
                        showDivider = true
                    )
                    SettingsItem(
                        iconResId = R.drawable.accounts,
                        title = "Manage accounts",
                        showDivider = false
                    )
                }

                // App Settings Section
                Text(
                    text = "App settings",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3A4252),
                    lineHeight = 16.sp,
                )

                SettingsSection {
                    SettingsItem(
                        iconResId = R.drawable.security,
                        title = "Security",
                        showDivider = true
                    )
                    SettingsItem(
                        iconResId = R.drawable.language,
                        title = "Language",
                        showDivider = true,
                        onClick = { showLanguageSheet = true }
                    )
                    SettingsItem(
                        iconResId = R.drawable.currency,
                        title = "Currency",
                        showDivider = true,
                        onClick = { showCurrencySheet = true }
                    )
                    SettingsItem(
                        iconResId = R.drawable.share,
                        title = "Share the app",
                        showDivider = true
                    )
                    SettingsItem(
                        iconResId = R.drawable.report_a_bug,
                        title = "Report a bug",
                        showDivider = true
                    )
                    SettingsItem(
                        iconResId = R.drawable.feedback,
                        title = "Feedback",
                        showDivider = false
                    )
                }

                // General Section
                Text(
                    text = "Account",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3A4252),
                    lineHeight = 16.sp,
                )

                SettingsSection {
                    SettingsItem(
                        iconResId = R.drawable.logout,
                        title = "Log out",
                        showDivider = true,
                        onClick = { showLogoutSheet = true }
                    )
                    SettingsItem(
                        iconResId = R.drawable.trash,
                        title = "Delete account",
                        textColor = Color(0xFFDC2626),
                        showDivider = false
                    )
                }

                // Version
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Version 1.0.0",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8695AA),
                        modifier = Modifier
                            .wrapContentWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = Color(0xFFD5D9E2),
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun SettingsItem(
    iconResId: Int,
    title: String,
    textColor: Color = Grey600,
    showDivider: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .then(
                    if (onClick != null) {
                        Modifier.clickable(onClick = onClick)
                    } else {
                        Modifier
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = title,
                    tint = Grey600,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                modifier = Modifier.weight(1f)
            )
            
            // Chevron right icon placeholder
            if (title != "Log out" && title != "Delete account") {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chevronright),
                        contentDescription = "",
                        tint = Grey600,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Grey100
            )
        }
    }
}

@Composable
private fun LanguageOption(
    language: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = language,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF3A4252)
        )
        Icon(
            painter = painterResource(
                id = if (isSelected) R.drawable.radio_checked else R.drawable.radio_unchecked
            ),
            contentDescription = if (isSelected) "Selected" else "Not selected",
            tint = Color(0xFF3A4252),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun CurrencyOption(
    currency: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF3A4252)
        )
        Icon(
            painter = painterResource(
                id = if (isSelected) R.drawable.radio_checked else R.drawable.radio_unchecked
            ),
            contentDescription = if (isSelected) "Selected" else "Not selected",
            tint = Color(0xFF3A4252),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            // Preview version without Firebase
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 0.dp),
            ) {
                // AppBar
                AppBar(
                    title = "Settings",
                    onBackClick = { }
                )

                // Settings Body
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFECEEF2))
                        .verticalScroll(scrollState)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // General Section
                        Text(
                            text = "General",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3A4252),
                            lineHeight = 16.sp,
                        )

                        SettingsSection {
                            SettingsItem(
                                iconResId = R.drawable.usericon,
                                title = "Profile",
                                showDivider = true
                            )
                            SettingsItem(
                                iconResId = R.drawable.accounts,
                                title = "Manage accounts",
                                showDivider = false
                            )
                        }

                        // App Settings Section
                        Text(
                            text = "App settings",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3A4252),
                            lineHeight = 16.sp,
                        )

                        SettingsSection {
                            SettingsItem(
                                iconResId = R.drawable.security,
                                title = "Security",
                                showDivider = true
                            )
                            SettingsItem(
                                iconResId = R.drawable.language,
                                title = "Language",
                                showDivider = true
                            )
                            SettingsItem(
                                iconResId = R.drawable.currency,
                                title = "Currency",
                                showDivider = true
                            )
                            SettingsItem(
                                iconResId = R.drawable.share,
                                title = "Share the app",
                                showDivider = true
                            )
                            SettingsItem(
                                iconResId = R.drawable.report_a_bug,
                                title = "Report a bug",
                                showDivider = true
                            )
                            SettingsItem(
                                iconResId = R.drawable.feedback,
                                title = "Feedback",
                                showDivider = false
                            )
                        }

                        // Account Section
                        Text(
                            text = "Account",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3A4252),
                            lineHeight = 16.sp,
                        )

                        SettingsSection {
                            SettingsItem(
                                iconResId = R.drawable.logout,
                                title = "Log out",
                                showDivider = true
                            )
                            SettingsItem(
                                iconResId = R.drawable.trash,
                                title = "Delete account",
                                textColor = Color(0xFFDC2626),
                                showDivider = false
                            )
                        }

                        // Version
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Version 1.0.0",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF8695AA),
                                modifier = Modifier.wrapContentWidth()
                            )
                        }
                    }
                }
            }
        }
    }
} 