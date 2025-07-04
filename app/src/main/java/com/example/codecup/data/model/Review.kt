package com.example.codecup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: Int,
    val userEmail: String,
    val text: String
)
