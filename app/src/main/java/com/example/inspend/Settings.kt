package com.example.inspend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp),
    ) {
        // Profile Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(73.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFECEEF2))
            )

            Column {
                var userName by remember { mutableStateOf("") }
                Text(
                    //Testing name
                    text = "Parthiban MK",
//                    text = userName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3A4252),
                    lineHeight = 16.sp,
                )
                Text(
                    text = "Member since November 2024",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8695AA),
                    lineHeight = 16.sp,

                )
            }
        }

        // Settings Body

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECEEF2))
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
                    title = "Accounts",
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
    showDivider: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
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

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen()
    }
} 