package com.example.codecup.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.codecup.navigation.Screen
import com.example.codecup.ui.home.HomeScreen
import com.example.codecup.ui.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Rewards.route) {
            //RewardsScreen(navController)
        }
        composable(Screen.MyOrder.route) {
            //OrdersScreen(navController)
        }
        // Sau này bạn thêm các màn hình khác:
        // composable(route = Screen.Details.route) {
        //     DetailsScreen(navController)

    }
}