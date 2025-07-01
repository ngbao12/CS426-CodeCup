package com.example.codecup.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.DismissDirection
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codecup.viewmodel.OrdersViewModel
import com.example.codecup.ui.components.order.OrderItemCard
import com.example.codecup.ui.components.order.CenteredTopBar
import kotlinx.coroutines.delay
import androidx.compose.material3.*
import com.example.codecup.data.model.OrderItem
import com.example.codecup.data.repository.OrderRepository

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel,
    navController: NavController
) {
    val ongoingOrders by ordersViewModel.ongoingOrders.collectAsState()
    val historyOrders by ordersViewModel.historyOrders.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("On going", "History")

    Scaffold(
        containerColor = Color.White,
        topBar = { CenteredTopBar(title = "Đơn hàng của tôi", navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                contentColor = Color(0xFF324A59)
            ) {
                tabs.forEachIndexed { index, title ->
                    androidx.compose.material.Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            androidx.compose.material.Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            val ordersToDisplay = if (selectedTab == 0) ongoingOrders else historyOrders

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(ordersToDisplay, key = { it.id }) { order ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = { dismissValue ->
                            if (dismissValue == DismissValue.DismissedToStart && selectedTab == 0) {
                                ordersViewModel.markAsHistory(order) // Gọi ngay
                                true // Cho phép Compose remove item
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {},
                        directions = if (selectedTab == 0) setOf(DismissDirection.EndToStart) else emptySet()
                    ) {
                        OrderItemCard(
                            order = order,
                            modifier = Modifier.animateItem()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
