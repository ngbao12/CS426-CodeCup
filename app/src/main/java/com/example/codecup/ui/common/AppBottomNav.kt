package com.example.codecup.ui.common

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.codecup.navigation.Screen
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppBottomNav(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Cart,
        Screen.Rewards,
        Screen.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAppBottomNav() {
    // Dùng rememberNavController nếu chạy thật
    AppBottomNav(navController = rememberNavController())
}