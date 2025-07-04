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
import com.example.codecup.ui.screen.LoginScreen
import com.example.codecup.viewmodel.LoginViewModelFactory
import com.example.codecup.viewmodel.LoginViewModel
import com.example.codecup.ui.screen.SignUpScreen
import com.example.codecup.viewmodel.SignUpViewModelFactory
import com.example.codecup.viewmodel.SignUpViewModel
import com.example.codecup.viewmodel.AccountViewModelFactory
import com.example.codecup.viewmodel.AccountViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.codecup.ui.screen.*
import androidx.compose.runtime.remember



@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current.applicationContext as Application

    val accountViewModel: AccountViewModel = viewModel(
        factory = AccountViewModelFactory(context)
    )
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(context)
    )
    val signUpViewModel: SignUpViewModel = viewModel(
        factory = SignUpViewModelFactory(context)
    )

    val currentAccount by accountViewModel.currentAccount.collectAsState()
    val email = currentAccount?.email

    val cartViewModel = remember(email) {
        email?.let { CartViewModelFactory(context, it).create(CartViewModel::class.java) }
    }
    val profileViewModel = remember(email) {
        email?.let { ProfileViewModelFactory(context, it).create(ProfileViewModel::class.java) }
    }
    val ordersViewModel = remember(email) {
        email?.let { OrdersViewModelFactory(context, it).create(OrdersViewModel::class.java) }
    }
    val rewardsViewModel = remember(email) {
        email?.let { RewardsViewModelFactory(context, it).create(RewardsViewModel::class.java) }
    }

    // Luôn bắt đầu từ splash
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(navController, accountViewModel = accountViewModel)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, loginViewModel, accountViewModel)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController, signUpViewModel)
        }

        // HOME
        composable(Screen.Home.route) {
            if (email != null && profileViewModel != null && rewardsViewModel != null) {
                HomeScreen(navController, rewardsViewModel= rewardsViewModel, profileViewModel = profileViewModel)
            }
        }

        // DETAILS
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("coffeeId") { type = NavType.IntType },
                navArgument("coffeeName") { type = NavType.StringType },
                navArgument("coffeePrice") { type = NavType.FloatType },
                navArgument("imageResId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            if (email != null && cartViewModel != null) {
                val id = backStackEntry.arguments?.getInt("coffeeId") ?: return@composable
                val name = backStackEntry.arguments?.getString("coffeeName") ?: ""
                val price = backStackEntry.arguments?.getFloat("coffeePrice")?.toDouble() ?: 0.0
                val imageRes = backStackEntry.arguments?.getInt("imageResId") ?: R.drawable.americano

                val coffeeItem = CoffeeItem(id, name, price, imageRes)
                DetailsScreen(navController, coffeeItem, cartViewModel = cartViewModel, accountViewModel = accountViewModel)
            }
        }

        // CART
        composable(Screen.Cart.route) {
            if (email != null &&
                cartViewModel != null &&
                ordersViewModel != null &&
                profileViewModel != null &&
                rewardsViewModel != null
            ) {
                CartScreen(navController, cartViewModel, ordersViewModel, profileViewModel, rewardsViewModel)
            }
        }

        // ORDER SUCCESS
        composable(Screen.OrderSuccess.route) {
            OrderSuccessScreen(navController)
        }

        // PROFILE
        composable(Screen.Profile.route) {
            if (profileViewModel != null) {
                ProfileScreen(navController, profileViewModel)
            }
        }

        // REWARDS
        composable(Screen.Rewards.route) {
            if (rewardsViewModel != null) {
                RewardsScreen(rewardsViewModel, navController)
            }
        }

        // MY ORDERS
        composable(Screen.MyOrder.route) {
            if (ordersViewModel != null) {
                OrdersScreen(ordersViewModel, navController)
            }
        }

        // REDEEM
        composable(Screen.Redeem.route) {
            if (rewardsViewModel != null &&
                ordersViewModel != null &&
                profileViewModel != null
            ) {
                RedeemScreen(rewardsViewModel, ordersViewModel, profileViewModel, accountViewModel, navController)
            }
        }

        //Review
        composable(
            route = Screen.Review.route,
            arguments = listOf(navArgument("orderId") { type = NavType.IntType })
        ) { backStackEntry ->
            if (ordersViewModel != null) {
                val orderId = backStackEntry.arguments?.getInt("orderId") ?: return@composable
                ReviewScreen(orderId = orderId, ordersViewModel = ordersViewModel, navController = navController)
            }
        }
    }
}
