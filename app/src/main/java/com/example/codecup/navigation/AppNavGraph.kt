package com.example.codecup.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.codecup.navigation.Screen
import com.example.codecup.ui.home.HomeScreen
import com.example.codecup.ui.SplashScreen
import com.example.codecup.data.model.CoffeeItem
import com.example.codecup.ui.details.DetailsScreen
import android.util.Log
import com.example.codecup.R
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.codecup.ui.cart.CartScreen
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import android.app.Application
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codecup.viewmodel.CartViewModel
import com.example.codecup.viewmodel.CartViewModelFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current.applicationContext as Application
    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(context)
    )
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
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
            CartScreen(navController, viewModel = cartViewModel)
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