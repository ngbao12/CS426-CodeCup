package com.example.codecup.navigation

import com.example.codecup.R

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Cart : Screen("cart", "Cart", R.drawable.ic_cart)
    object Rewards : Screen("rewards", "Rewards", R.drawable.ic_rewards)
    object Profile : Screen("profile", "Profile", R.drawable.ic_profile)
}