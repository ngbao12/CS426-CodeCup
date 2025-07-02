package com.example.codecup.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.codecup.ui.screen.HomeScreen
import com.example.codecup.ui.screen.SplashScreen
import com.example.codecup.data.model.CoffeeItem
import com.example.codecup.ui.screen.DetailsScreen
import com.example.codecup.R
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.codecup.ui.screen.CartScreen
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import android.app.Application
import com.example.codecup.viewmodel.CartViewModel
import com.example.codecup.viewmodel.CartViewModelFactory
import com.example.codecup.ui.screen.OrderSuccessScreen
import com.example.codecup.ui.screen.ProfileScreen
import com.example.codecup.viewmodel.ProfileViewModel
import com.example.codecup.viewmodel.ProfileViewModelFactory
import com.example.codecup.viewmodel.OrdersViewModel
import com.example.codecup.viewmodel.OrdersViewModelFactory
import com.example.codecup.ui.screen.OrdersScreen
import com.example.codecup.ui.screen.RewardsScreen
import com.example.codecup.viewmodel.RewardsViewModel
import com.example.codecup.viewmodel.RewardsViewModelFactory
import com.example.codecup.ui.screen.RedeemScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current.applicationContext as Application
    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(context)
    )
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(context)
    )
    val ordersViewModel: OrdersViewModel = viewModel(
        factory = OrdersViewModelFactory(context)
    )
    val rewardsViewModel: RewardsViewModel = viewModel(
        factory = RewardsViewModelFactory(context)
    )
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController, profileViewModel = profileViewModel, rewardsViewModel = rewardsViewModel)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("coffeeId") { type = NavType.IntType },
                navArgument("coffeeName") { type = NavType.StringType },
                navArgument("coffeePrice") { type = NavType.FloatType },
                navArgument("imageResId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("coffeeId") ?: return@composable
            val name = backStackEntry.arguments?.getString("coffeeName") ?: ""
            val price = backStackEntry.arguments?.getFloat("coffeePrice")?.toDouble() ?: 0.0
            val imageRes = backStackEntry.arguments?.getInt("imageResId") ?: R.drawable.americano

            val coffeeItem = CoffeeItem(id, name, price, imageRes)

            DetailsScreen(navController, coffeeItem,cartViewModel= cartViewModel)
        }
        composable(Screen.Cart.route) {
            CartScreen(navController, viewModel = cartViewModel, ordersViewModel = ordersViewModel, profileViewModel = profileViewModel, rewardsViewModel = rewardsViewModel)
        }
        composable(Screen.OrderSuccess.route) {
            OrderSuccessScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, viewModel = profileViewModel)
        }
        composable(Screen.Rewards.route) {
            RewardsScreen(rewardsViewModel,navController)
        }
        composable(Screen.MyOrder.route) {
            OrdersScreen(ordersViewModel = ordersViewModel,navController = navController)
        }
        composable(Screen.Redeem.route) {
            RedeemScreen(rewardsViewModel,ordersViewModel,profileViewModel,navController)
        }

    }
}