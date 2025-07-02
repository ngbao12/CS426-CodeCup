package com.example.codecup.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.codecup.ui.components.home.AppHeader
import com.example.codecup.ui.components.home.AppBottomNav
import com.example.codecup.viewmodel.HomeViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import com.example.codecup.navigation.Screen
import com.example.codecup.ui.components.home.CoffeeList
import com.example.codecup.ui.components.home.LoyaltyCard
import com.example.codecup.viewmodel.ProfileViewModel
import com.example.codecup.viewmodel.RewardsViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    rewardsViewModel: RewardsViewModel = viewModel()
) {

    Scaffold(
        containerColor = Color.White,
        topBar = { AppHeader(userName = profileViewModel.userName, navController = navController) },
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
                color = Color(0xFF324A59)
            ) {
                LoyaltyCard(stamps = rewardsViewModel.stampCount)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color(0xFF324A59),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Choose your coffee",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFFCBD4D9)
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        CoffeeList(
                            coffeeList = viewModel.coffeeList,
                            onItemClick = { coffeeItem ->
                                navController.navigate(Screen.Details.createRoute(coffeeItem))
                            }
                        )
                    }
                    AppBottomNav(navController = navController)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(
        navController = navController

    )
}

