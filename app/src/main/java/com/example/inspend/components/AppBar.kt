package com.example.inspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inspend.R
import com.example.inspend.ui.theme.BGdefault
import com.example.inspend.ui.theme.Brand100
import com.example.inspend.ui.theme.Grey700

@Composable
fun AppBar(
    title: String,
    onBackClick: () -> Unit,
    onTrailingIconClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(BGdefault)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
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

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppBar(
        title = "Preview Title",
        onBackClick = { /* Preview back click */ },
        onTrailingIconClick = { /* Preview trailing icon click */ }
    )
}

