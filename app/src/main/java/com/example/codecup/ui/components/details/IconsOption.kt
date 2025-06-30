package com.example.codecup.ui.components.details

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