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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
        Text(
            text = "Add Transaction",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3A4252),
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        )
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
    var userName by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    // Fetch user name immediately
    SideEffect {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    userName = document.getString("name") ?: ""
                }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(89.dp)
            .background(Color.White)
            .drawBehind {
                val borderWidth = 1.5.dp.toPx()
                val y = size.height - borderWidth / 2
                drawLine(
                    color = Color(0xFFE0E2EB),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderWidth
                )
            }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = userName,  // Use fetched user name
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3A4252),
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8695AA),
                    lineHeight = 14.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
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
                        color = Color(0xFFFFFFFF),
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
        DefaultAppBar(
            title = "Default Title",
            onBackClick = {}
        )

        // Home AppBar Preview - Using the base implementation without Firebase
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(89.dp)
                .background(Color.White)
                .drawBehind {
                    val borderWidth = 1.5.dp.toPx()
                    val y = size.height - borderWidth / 2
                    drawLine(
                        color = Color(0xFFE0E2EB),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = borderWidth
                    )
                }
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Profile",
                        tint = Grey600,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center
                ) {

                    var userName by remember { mutableStateOf("") }
                    Text(
                        text = userName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF3A4252),
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp
                    )
                    Text(
                        text = "Welcome back",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8695AA),
                        lineHeight = 14.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Box(
//                    modifier = Modifier
//                        .size(48.dp)
//                        .background(
//                            color = Color(0xFFFFFFFF),
//                            shape = CircleShape
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.notification),
//                        contentDescription = "Notifications",
//                        tint = Grey600,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.setting),
                        contentDescription = "Logout",
                        tint = Grey600,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

