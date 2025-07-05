package com.example.codecup.data.model
import androidx.annotation.DrawableRes

data class CoffeeItem(
    val id: Int,
    val name: String,
    val price: Double,
    @DrawableRes val imageRes: Int
)
