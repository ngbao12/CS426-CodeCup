package com.example.codecup.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val address: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "ongoing" // or "history"
)
