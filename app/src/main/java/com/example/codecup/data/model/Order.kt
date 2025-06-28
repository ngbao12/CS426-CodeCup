package com.example.codecup.data.model

data class Order(
    val id: Int,
    val date: String,
    val status: String,
    val total: Double,
    val items: List<CartItem>
)
