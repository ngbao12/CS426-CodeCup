package com.example.codecup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.codecup.data.local.AppDatabase
import com.example.codecup.data.model.CartItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.codecup.data.model.isSameAs
import com.example.codecup.data.repository.CartRepository
import com.example.codecup.data.local.DatabaseProvider

class CartViewModel(
    application: Application,
    private val repository: CartRepository,
    private val userEmail: String
) : AndroidViewModel(application) {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _cartItems.addAll(repository.getAllItems(userEmail))
        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val allItems = repository.getAllItems(userEmail)
            val existing = allItems.find { it.isSameAs(item) }

            val newItem = if (existing != null) {
                existing.copy(
                    quantity = existing.quantity + item.quantity,
                    price = existing.price + item.price
                )
            } else {
                item.copy(userEmail = userEmail)
            }

            repository.insert(newItem)

            _cartItems.clear()
            _cartItems.addAll(repository.getAllItems(userEmail))
        }
    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(item)
            _cartItems.remove(item)
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.price }
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAll(userEmail)
            _cartItems.clear()
        }
    }
}
class CartViewModelFactory(
    private val application: Application,
    private val userEmail: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val repository = CartRepository(db.cartDao())

        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(application, repository, userEmail) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
