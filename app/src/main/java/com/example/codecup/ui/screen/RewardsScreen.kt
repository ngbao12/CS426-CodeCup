package com.example.codecup.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.example.codecup.R
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Button
import com.example.codecup.viewmodel.RewardsViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.codecup.ui.components.order.CenteredTopBar

@Composable
fun RewardsScreen(
    rewardsViewModel: RewardsViewModel,
    navController: NavController
) {
    val rewardItems by remember { derivedStateOf { rewardsViewModel.rewardItems } }
    val totalPoints = rewardsViewModel.totalPoints
    val stampCount = rewardsViewModel.stampCount

    Scaffold(
        topBar = {
            CenteredTopBar(title = "Rewards", navController = navController)
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Loyalty Card
            Text("Loyalty card", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(8) { index ->
                    val filled = index < stampCount
                    Icon(
                        painter = painterResource(id = R.drawable.ic_coffee_stamp),
                        contentDescription = null,
                        tint = if (filled) Color(0xFF6D4C41) else Color.LightGray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // My Points
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2E3A59))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("My Points", color = Color.White)
                        Text(
                            text = totalPoints.toString(),
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { rewardsViewModel.resetStamps() },
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Redeem drinks")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("History Rewards", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(rewardItems) { reward ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(reward.name)
                            Text("+${reward.points} Pts")
                        }
                        Text(
                            text = SimpleDateFormat("dd MMM | HH:mm", Locale.getDefault())
                                .format(Date(reward.timestamp)),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
