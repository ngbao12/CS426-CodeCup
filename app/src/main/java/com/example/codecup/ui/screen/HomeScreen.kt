package com.example.codecup.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.codecup.navigation.Screen
import com.example.codecup.ui.components.home.AppBottomNav
import com.example.codecup.ui.components.home.AppHeader
import com.example.codecup.ui.components.home.CoffeeList
import com.example.codecup.ui.components.home.LoyaltyCard
import com.example.codecup.viewmodel.HomeViewModel
import com.example.codecup.viewmodel.ProfileViewModel
import com.example.codecup.viewmodel.RewardsViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    rewardsViewModel: RewardsViewModel = viewModel()
) {
    val primaryContainer = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface
    val backgroundColor = MaterialTheme.colorScheme.background
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val onSurface = MaterialTheme.colorScheme.onSurface

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            AppHeader(
                greeting = viewModel.getGreeting(),
                userName = profileViewModel.userName,
                navController = navController
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                AppBottomNav(navController)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp,
                color = primaryContainer
            ) {
                LoyaltyCard(stamps = rewardsViewModel.stampCount)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = primaryContainer,
                shape = RoundedCornerShape(32.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Choose your coffee",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = onPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        CoffeeList(
                            coffeeList = viewModel.coffeeList,
                            onItemClick = { coffeeItem ->
                                navController.navigate(Screen.Details.createRoute(coffeeItem))
                            }
                        )
                    }
                }
            }
        }
    }
}