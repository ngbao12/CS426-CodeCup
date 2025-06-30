package com.example.codecup.navigation
import com.example.codecup.R
import android.net.Uri
import com.example.codecup.data.model.CoffeeItem

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Details : Screen(
        route = "details/{coffeeId}/{coffeeName}/{coffeePrice}/{imageResId}",
        title = "Details",
        icon = R.drawable.ic_home
    ) {
        fun createRoute(coffeeItem: CoffeeItem): String {
            return "details/${coffeeItem.id}/${Uri.encode(coffeeItem.name)}/${coffeeItem.price}/${coffeeItem.imageRes}"
        }
    }
    object OrderSuccess : Screen("order_success", "Order Success", R.drawable.ic_order_success)
    object Profile : Screen("profile", "Profile", R.drawable.ic_profile)
    object Rewards : Screen("rewards", "Rewards", R.drawable.ic_rewards)
    object MyOrder : Screen("my_order", "My Order", R.drawable.ic_my_order)
    object Splash : Screen("splash", "Splash", R.drawable.ic_my_order)
    object Cart : Screen("cart", "Cart", R.drawable.ic_cart)
}