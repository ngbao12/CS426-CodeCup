package com.example.codecup.ui.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.codecup.navigation.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun AppBottomNav(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Rewards,
        Screen.MyOrder
    )

    Surface(
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            containerColor = Color.Transparent,
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
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAppBottomNav() {
    AppBottomNav(navController = rememberNavController())
}