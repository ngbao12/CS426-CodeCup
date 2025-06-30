package com.example.codecup.ui.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.example.codecup.R
import androidx.compose.foundation.background

@Composable
fun LoyaltyCard(stamps: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF324A59) // Màu nền đậm
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Tiêu đề + điểm
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Loyalty card",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
                )
                Text(
                    text = "$stamps / 8",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    repeat(8) { index ->
                        val alpha = if (index < stamps) 1f else 0.3f
                        Image(
                            painter = painterResource(id = R.drawable.ic_coffee_stamp),
                            contentDescription = "Coffee Stamp",
                            modifier = Modifier
                                .size(24.dp)
                                .alpha(alpha)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoyaltyCard() {
    LoyaltyCard( stamps = 5)
}