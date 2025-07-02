package com.example.codecup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.OrderItem
import com.example.codecup.data.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.codecup.data.local.AppDatabase
import com.example.codecup.data.local.DatabaseProvider


class OrdersViewModel(
    application: Application,
    private val repository: OrderRepository
) : AndroidViewModel(application) {

    private val _ongoingOrders = MutableStateFlow<List<OrderItem>>(emptyList())
    val ongoingOrders: StateFlow<List<OrderItem>> = _ongoingOrders.asStateFlow()

    private val _historyOrders = MutableStateFlow<List<OrderItem>>(emptyList())
    val historyOrders: StateFlow<List<OrderItem>> = _historyOrders.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOngoingOrders().collect {
                _ongoingOrders.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistoryOrders().collect {
                _historyOrders.value = it
            }
        }
    }

    fun markAsHistory(order: OrderItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateOrder(order.copy(status = "history"))
        }
    }

    fun saveCartAsOrders(cartItems: List<CartItem>, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val orderList = cartItems.map {
                OrderItem(
                    name = it.name,
                    price = it.price * it.quantity,
                    address = address,
                    status = "ongoing"
                )
            }
            repository.insertOrder(orderList)
        }
    }
}

class OrdersViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val orderDao = db.orderDao()
        val repository = OrderRepository(orderDao)

        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}