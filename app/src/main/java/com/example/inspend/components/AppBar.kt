package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.R
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.Brand100
import com.example.inspend.ui.theme.Grey600
import com.example.inspend.ui.theme.Grey700
import androidx.navigation.NavController

enum class AppBarType {
    DEFAULT, HOME
}

@Composable
fun AppBar(
    type: AppBarType = AppBarType.DEFAULT,
    title: String = "",
    subtitle: String = "",
    onBackClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    when (type) {
        AppBarType.DEFAULT -> DefaultAppBar(
            title = title,
            onBackClick = onBackClick,
            modifier = modifier
        )
        AppBarType.HOME -> HomeAppBar(
            title = title,
            subtitle = subtitle,
            onProfileClick = onProfileClick,
            modifier = modifier,
            navController = navController
        )
    }
}

@Composable
private fun DefaultAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(BGdefault)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading Icon
        Box(
            modifier = Modifier
                .background(
                    color = BGdefault,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .clip(shape)
                .size(48.dp)
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = "Back",
                tint = Grey700,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun HomeAppBar(
    title: String,
    subtitle: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    Row(
            modifier = modifier
                .fillMaxWidth()
                .height(89.dp)
                .background(Color.White)
                .drawBehind {
                    val borderWidth = 1.5.dp.toPx() // Thickness of the bottom border
                    val y = size.height - borderWidth / 2 // Position at the bottom
                    drawLine(
                        color = Color(0xFFE0E2EB), // Border color
                        start = androidx.compose.ui.geometry.Offset(
                            0f,
                            y
                        ), // Start from leftmost edge
                        end = androidx.compose.ui.geometry.Offset(
                            size.width,
                            y
                        ), // End at rightmost edge
                        strokeWidth = borderWidth
                    )
                }
                .padding(horizontal = 16.dp), // Padding for the content inside the Row
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                modifier = Modifier
                ,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                // Profile Button
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFECEEF2),
                            shape = CircleShape
                        )
                        .clickable(onClick = onProfileClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Profile",
                        tint = Grey600,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // User Info
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,  // User name
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,  // Made bolder for name
                        color = Color(0xFF3A4252),
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp
                    )
                    Text(
                        text = subtitle,  // Welcome back
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8695AA),
                        lineHeight = 14.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Row (
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFECEEF2),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notifications",
                        tint = Grey600,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFECEEF2),
                            shape = CircleShape
                        )
                        .clickable {
                            // Navigate to welcome screen on logout
                            navController?.navigate("welcome") {
                                popUpTo(0) // Clear the entire back stack
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Logout",
                        tint = Grey600,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }


}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Default AppBar
        AppBar(
            type = AppBarType.DEFAULT,
            title = "Default Title",
            onBackClick = {}
        )

        // Home AppBar
        AppBar(
            type = AppBarType.HOME,
            title = "Name",
            subtitle = "Welcome back",
            onProfileClick = {}
        )
    }
}

