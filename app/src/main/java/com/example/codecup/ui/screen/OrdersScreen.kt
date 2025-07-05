package com.example.codecup.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codecup.navigation.Screen
import com.example.codecup.ui.components.home.AppBottomNav
import com.example.codecup.ui.components.order.CenteredTopBar
import com.example.codecup.ui.components.order.OrderItemCard
import com.example.codecup.viewmodel.OrdersViewModel


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
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                AppBottomNav(navController)
            }
        },
        topBar = {
            CenteredTopBar(title = "My Order", navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    androidx.compose.material.Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            androidx.compose.material.Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground
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
                                ordersViewModel.markAsHistory(order)
                                true
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
                        Column {
                            OrderItemCard(
                                order = order,
                                modifier = Modifier.animateItem()
                            )


                            if (selectedTab == 1) {
                                TextButton(
                                    onClick = {
                                        navController.navigate(Screen.Review.createRoute(order.id))
                                    },
                                    modifier = Modifier
                                        .padding(top = 4.dp, start = 8.dp)
                                        .align(Alignment.End)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.RateReview,
                                        contentDescription = "Review",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Review",
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
