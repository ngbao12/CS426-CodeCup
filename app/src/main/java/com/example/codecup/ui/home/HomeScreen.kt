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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import com.example.codecup.navigation.Screen


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    Scaffold(
        containerColor = Color.White,
        topBar = { AppHeader(userName = "Anderson", navController = navController) },
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
                LoyaltyCard(stamps = viewModel.stamps)
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

