package com.example.codecup.ui.common

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(userName: String) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Good morning",
                    fontSize = 14.sp,
                    color = Color(0xFFD8D8D8)
                )
                Text(
                    text = userName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001833)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppHeaderPreview() {
    AppHeader(userName = "Ngoc Bao")
}
