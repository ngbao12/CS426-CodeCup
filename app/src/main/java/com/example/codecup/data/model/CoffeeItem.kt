package com.example.codecup.data.model
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

data class CoffeeItem(
    val id: Int,
    val name: String,
    val price: Double,
    @DrawableRes val imageRes: Int
)
