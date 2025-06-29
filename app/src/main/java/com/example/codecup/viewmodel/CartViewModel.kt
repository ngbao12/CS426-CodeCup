package com.example.codecup.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.codecup.data.model.CartItem

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    fun addToCart(item: CartItem) {
        _cartItems.add(item)
    }

    fun removeItem(item: CartItem) {
        _cartItems.remove(item)
    }

    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.price * it.quantity }
    }
}
