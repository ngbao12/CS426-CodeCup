package com.example.codecup.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.isSameAs

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    fun addToCart(item: CartItem) {
        val existing = _cartItems.find { it.isSameAs(item) }

        if (existing != null) {
            val updated = existing.copy(quantity = existing.quantity + item.quantity)
            _cartItems[_cartItems.indexOf(existing)] = updated
        } else {
            _cartItems.add(item)
        }
    }


    fun removeItem(item: CartItem) {
        _cartItems.remove(item)
    }

    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.price * it.quantity }
    }
}
