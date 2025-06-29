package com.example.codecup.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.codecup.R

@Composable
fun getSelectIcons(): List<@Composable () -> Unit> = listOf(
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_select_hot),
                contentDescription = "Hot",
                modifier = Modifier.size(28.dp)
            )
        }
    },
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_select_cold),
                contentDescription = "Cold",
                modifier = Modifier.size(28.dp)
            )
        }
    }
)

@Composable
fun getSizeIcons(): List<@Composable () -> Unit> = listOf(
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_size_s),
                contentDescription = "Small",
                modifier = Modifier.size(24.dp)
            )
        }
    },
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_size_m),
                contentDescription = "Medium",
                modifier = Modifier.size(32.dp)
            )
        }
    },
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_size_l),
                contentDescription = "Large",
                modifier = Modifier.size(40.dp)
            )
        }
    }
)

@Composable
fun getIceIcons(): List<@Composable () -> Unit> = listOf(
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ice_s),
                contentDescription = "Low Ice",
                modifier = Modifier.size(16.dp)
            )
        }
    },
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ice_m),
                contentDescription = "Medium Ice",
                modifier = Modifier.size(32.dp)
            )
        }
    },
    {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.BottomCenter) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ice_l),
                contentDescription = "High Ice",
                modifier = Modifier.size(34.dp)
            )
        }
    }
)