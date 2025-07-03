package com.example.codecup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.codecup.R
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.RedeemItem
import com.example.codecup.data.model.RewardItem
import com.example.codecup.viewmodel.OrdersViewModel
import com.example.codecup.viewmodel.ProfileViewModel
import com.example.codecup.viewmodel.RewardsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.codecup.ui.components.redeem.RedeemItemCard
import com.example.codecup.ui.components.order.CenteredTopBar
import com.example.codecup.data.model.OrderItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.example.codecup.viewmodel.AccountViewModel
import kotlinx.coroutines.flow.StateFlow
import com.example.codecup.data.model.Account

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedeemScreen(
    rewardsViewModel: RewardsViewModel,
    ordersViewModel: OrdersViewModel,
    profileViewModel: ProfileViewModel,
    accountViewModel: AccountViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val items = listOf(
        RedeemItem("Cafe Latte", R.drawable.americano, "04.07.26", 200),
        RedeemItem("Flat White", R.drawable.flat_white, "04.07.26", 300),
        RedeemItem("Cappuccino", R.drawable.cappuccino, "04.07.26", 400)
    )
    val currentAccountState = accountViewModel.currentAccount.collectAsState()
    val currentAccount = currentAccountState.value
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Redeem",
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color(0xFF324A59),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            LazyColumn(contentPadding = padding) {
                items(items) { item ->
                    RedeemItemCard(
                        item = item,
                        onRedeem = {
                            handleRedeem(
                                rewardItemName = item.name,
                                pointCost = item.pointCost,
                                rewardsViewModel = rewardsViewModel,
                                ordersViewModel = ordersViewModel,
                                profileViewModel = profileViewModel,
                                currentAccount = currentAccount,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Redeemed ${item.name}!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailure = {
                                    Toast.makeText(context, "Not enough points", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}


fun handleRedeem(
    rewardItemName: String,
    pointCost: Int,
    rewardsViewModel: RewardsViewModel,
    ordersViewModel: OrdersViewModel,
    profileViewModel: ProfileViewModel,
    currentAccount: Account?,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    if (rewardsViewModel.totalPoints >= pointCost) {
        val reward = RewardItem(
            name = rewardItemName,
            points = -pointCost,
            userEmail = currentAccount?.email.orEmpty()
        )

        rewardsViewModel.addNegativeReward(rewardItem = reward) {
            onSuccess()
        }

        ordersViewModel.saveRewardAsOrders(
            rewardItem = reward,
            address = profileViewModel.address
        )
    } else {
        onFailure()
    }
}
