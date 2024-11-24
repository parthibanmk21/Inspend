package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspend.R

@Composable
fun BottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Item
        BottomBarItem(
            icon = R.drawable.home,
            label = "Home",
            isSelected = currentRoute == "home",
            onClick = { onNavigate("home") }
        )

        // Add Item
        BottomBarItem(
            icon = R.drawable.add,
            label = "Add",
            isSelected = currentRoute == "add",
            onClick = { onNavigate("add") }
        )

        // Checklist Item
        BottomBarItem(
            icon = R.drawable.checklist,
            label = "Checklist",
            isSelected = currentRoute == "checklist",
            onClick = { onNavigate("checklist") }
        )

        // Settings Item
        BottomBarItem(
            icon = R.drawable.setting,
            label = "Setting",
            isSelected = currentRoute == "settings",
            onClick = { onNavigate("settings") }
        )
    }
}

@Composable
private fun BottomBarItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .height(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Icon Container
        Box(
            modifier = Modifier
                .width(64.dp)
                .height(if (isSelected) 32.dp else 28.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) Color(0xFFE3ECFB) else Color.White)
                .padding(horizontal = if (isSelected) 16.dp else 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = if (isSelected) Color(0xFF145CB5) else Color(0xFF8695AA),
                modifier = Modifier.size(20.dp)
            )
        }

        // Label
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color(0xFF145CB5) else Color(0xFF8695AA),
            letterSpacing = 0.5.sp,
            lineHeight = 14.sp
        )
    }
}

@Preview(
    name = "Bottom Bar",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 360
)
@Composable
fun BottomBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Preview with different selected states
        BottomBar(
            currentRoute = "home",  // Show home as selected
            onNavigate = { },
            modifier = Modifier
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        BottomBar(
            currentRoute = "add",  // Show add as selected
            onNavigate = { },
            modifier = Modifier
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        BottomBar(
            currentRoute = "checklist",  // Show checklist as selected
            onNavigate = { },
            modifier = Modifier
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        BottomBar(
            currentRoute = "settings",  // Show settings as selected
            onNavigate = { },
            modifier = Modifier
        )
    }
} 