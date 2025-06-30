package com.example.codecup.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    @DrawableRes val imageRes: Int,
    val shot: String,
    val select: String,
    val size: String,
    val ice: String
){
    val uniqueKey: String
        get() = "$id-$shot-$select-$size-$ice"
}

fun CartItem.isSameAs(other: CartItem): Boolean {
    return this.id == other.id &&
            this.shot == other.shot &&
            this.select == other.select &&
            this.size == other.size &&
            this.ice == other.ice
}

