package com.example.codecup.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun LoyaltyCard(points: Int, tier: String, stamps: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF324A59)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Loyalty Program", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tier: $tier", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Points: $points", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
