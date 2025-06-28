package com.example.codecup.navigation

import com.example.codecup.R

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Rewards : Screen("rewards", "Rewards", R.drawable.ic_rewards)
    object MyOrder : Screen("my_order", "My Order", R.drawable.ic_my_order)
    object Splash : Screen("splash", "Splash", R.drawable.ic_my_order)
}