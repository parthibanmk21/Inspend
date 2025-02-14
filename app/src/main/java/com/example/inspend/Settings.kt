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
import androidx.compose.ui.res.painterResource
import com.example.inspend.ui.theme.Grey700

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
                .padding(horizontal = 16.dp),
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
                Text(
                    text = "John Doe",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3A4252)
                )
                Text(
                    text = "Member since Jan 2024",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8695AA)
                )
            }
        }

        // Settings Body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECEEF2))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // General Section
            Text(
                text = "General",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3A4252)
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
                text = "App Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3A4252)
            )
            
            SettingsSection {
                SettingsItem(
                    iconResId = R.drawable.security,
                    title = "Security",
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
                    showDivider = true
                )
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
            Text(
                text = "Version 1.0.0",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8695AA),
                modifier = Modifier.fillMaxWidth()
            )
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
    textColor: Color = Color(0xFF526077),
    showDivider: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
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
                    tint = Grey700,
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
                        tint = Grey700,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        
        if (showDivider) {
            Divider(
                color = Color(0xFFD5D9E2),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
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