package com.example.codecup.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.codecup.ui.common.AppHeader
import com.example.codecup.ui.common.AppBottomNav
import com.example.codecup.viewmodel.HomeViewModel
import com.example.codecup.data.model.CoffeeItem
import com.example.codecup.ui.home.CoffeeList
import com.example.codecup.ui.home.LoyaltyCard
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController



@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    Scaffold(
        topBar = { AppHeader(userName = "Anderson") },
        bottomBar = { AppBottomNav(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize()
        ) {
            LoyaltyCard(
                stamps = viewModel.stamps,
                points = TODO(),
                tier = TODO()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Choose your coffee", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            CoffeeList(
                coffeeList = viewModel.coffeeList,
                onItemClick = { coffeeId ->
                    navController.navigate("details/$coffeeId")
                }
            )
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

