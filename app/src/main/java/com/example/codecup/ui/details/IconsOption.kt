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

data class IconData(val resId: Int, val description: String, val size: Int)

fun getSelectIcons(): List<IconData> = listOf(
    IconData(R.drawable.ic_select_hot, "Hot", 28),
    IconData(R.drawable.ic_select_cold, "Cold", 28)
)

fun getSizeIcons(): List<IconData> = listOf(
    IconData(R.drawable.ic_size_s, "Small", 24),
    IconData(R.drawable.ic_size_m, "Medium", 32),
    IconData(R.drawable.ic_size_l, "Large", 40)
)

fun getIceIcons(): List<IconData> = listOf(
    IconData(R.drawable.ic_ice_s, "Low Ice", 16),
    IconData(R.drawable.ic_ice_m, "Medium Ice", 32),
    IconData(R.drawable.ic_ice_l, "High Ice", 34)
)