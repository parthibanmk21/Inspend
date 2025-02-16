package com.example.inspend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inspend.components.*
import com.example.inspend.ui.theme.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.userProfileChangeRequest

// Shared content function
@Composable
private fun AddTransactionContent(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BGdefault),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // AppBar
        AppBar(
            title = "Profile",
            onBackClick = onBackClick
        )

        // Make Transaction Body scrollable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // New Transaction Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(2.dp, Color(0xFFE0E2EB), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(96.dp)
                        .background(Grey50, RoundedCornerShape(100.dp))
                        .border(2.dp, Color(0xFFE0E2EB), RoundedCornerShape(100.dp))
                        .padding(16.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.user),
                    tint = Grey300,
                    contentDescription = stringResource(id = R.string.app_name)
                )

                Text(
                    text = "Tap to change profile picture",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey300,
                    lineHeight = 16.sp,
                    modifier = Modifier
                        .wrapContentWidth(),
                )
                //User id it will generate automaticacly
                var user_id by remember { mutableStateOf("") }
                InputField(
                    label = "User id",
                    placeholder = "@parthibanmk21",
                    value = user_id,
                    onValueChange = { user_id = it }
                )
                var name by remember { mutableStateOf("") }
                InputField(
                    label = "Full name",
                    placeholder = "Parthiban MK",
                    value = name,
                    onValueChange = { name = it }
                )
                var email by remember { mutableStateOf("") }
                InputField(
                    label = "Email",
                    placeholder = "parthibanmk21@gmail.com",
                    value = email,
                    onValueChange = { email = it }
                )

                Button(
                    text = "Save",
                    onClick = { }
                )
            }
        }
    }
}

// Actual screen using shared content
@Composable
fun ProfilePreview(
    navController: NavController
) {
    AddTransactionContent(
        onBackClick = { navController.navigateUp() }
    )
}

// Preview using shared content
@Preview(
    name = "Profile Screen",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
fun ProfilePreview() {
    InspendTheme {
        AddTransactionContent()
    }
}