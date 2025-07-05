package com.example.codecup.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codecup.R
import com.example.codecup.navigation.Screen
import com.example.codecup.viewmodel.AccountViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController, accountViewModel: AccountViewModel) {
    val currentAccountState = accountViewModel.currentAccount.collectAsState()

    LaunchedEffect(currentAccountState.value) {
        delay(1000)
        val destination = if (currentAccountState.value?.email.isNullOrEmpty()) {
            Screen.Login.route
        } else {
            Screen.Home.route
        }
        navController.navigate(destination) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Logo + Text ở giữa
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_coffee_logo),
                contentDescription = "Coffee Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ordinary Coffee House",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White
                )
            )
        }
    }
}
