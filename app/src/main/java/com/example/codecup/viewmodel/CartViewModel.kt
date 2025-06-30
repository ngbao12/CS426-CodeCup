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

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "cart-database"
    ).fallbackToDestructiveMigration().build()

    private val cartDao = db.cartDao()

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    init {
        // Load cart on startup
        viewModelScope.launch(Dispatchers.IO) {
            _cartItems.addAll(cartDao.getAll())
        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val allItems = cartDao.getAll()
            val existing = allItems.find { it.isSameAs(item) }

            if (existing != null) {
                val updated = existing.copy(quantity = existing.quantity + item.quantity)
                cartDao.insert(updated)
            } else {
                cartDao.insert(item)
            }

            // Refresh list
            _cartItems.clear()
            _cartItems.addAll(cartDao.getAll())
        }
    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.delete(item)
            _cartItems.remove(item)
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.price * it.quantity }
    }
}

class CartViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

