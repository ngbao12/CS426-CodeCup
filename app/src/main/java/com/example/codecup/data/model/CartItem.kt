package com.example.codecup.data.model

import androidx.annotation.DrawableRes

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    @DrawableRes val imageRes: Int,
    val shot: String,
    val select: String,
    val size: String,
    val ice: String
)
