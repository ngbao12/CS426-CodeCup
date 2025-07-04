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
import androidx.compose.foundation.clickable
import com.example.codecup.ui.components.home.AppBottomNav
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

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
        bottomBar = {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                AppBottomNav(navController)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Loyalty card",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "${stampCount.coerceAtMost(8)}/8",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = stampCount == 8) {
                                rewardsViewModel.resetStamps()
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(8) { index ->
                                val filled = index < stampCount
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_coffee_stamp),
                                    contentDescription = null,
                                    tint = if (filled)
                                        MaterialTheme.colorScheme.tertiary
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("My Points:", color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)
                        Text(
                            text = totalPoints.toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { navController.navigate("redeem") },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Redeem drinks")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "History Rewards",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(rewardItems) { reward ->
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(reward.name, color = MaterialTheme.colorScheme.onBackground)
                                val text = if (reward.points >= 0) "+${reward.points}" else "${reward.points}"
                                Text(text, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                            }
                            Text(
                                text = SimpleDateFormat("dd MMM | HH:mm", Locale.getDefault())
                                    .format(Date(reward.timestamp)),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}
