package com.example.codecup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.codecup.data.local.DatabaseProvider
import com.example.codecup.data.local.ReviewDao
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.OrderItem
import com.example.codecup.data.model.Review
import com.example.codecup.data.model.RewardItem
import com.example.codecup.data.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OrdersViewModel(
    application: Application,
    private val repository: OrderRepository,
    private val reviewDao: ReviewDao,
    private val userEmail: String
) : AndroidViewModel(application) {

    private val _ongoingOrders = MutableStateFlow<List<OrderItem>>(emptyList())
    val ongoingOrders: StateFlow<List<OrderItem>> = _ongoingOrders.asStateFlow()

    private val _historyOrders = MutableStateFlow<List<OrderItem>>(emptyList())
    val historyOrders: StateFlow<List<OrderItem>> = _historyOrders.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOngoingOrders(userEmail).collect {
                _ongoingOrders.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistoryOrders(userEmail).collect {
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
                    price = it.price,
                    address = address,
                    status = "ongoing",
                    userEmail = it.userEmail
                )
            }
            repository.insertOrder(orderList)
        }
    }

    fun saveRewardAsOrders(rewardItem: RewardItem, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = OrderItem(
                name = rewardItem.name,
                price = 0.0,
                address = address,
                status = "ongoing",
                userEmail = rewardItem.userEmail
            )
            repository.insertOrder(listOf(order))
        }
    }

    fun getReviewByOrderId(orderId: Int, onLoaded: (Review?) -> Unit) {
        viewModelScope.launch {
            val review = reviewDao.getReviewByOrderIdAndUser(orderId, userEmail)
            onLoaded(review)
        }
    }

    fun saveReview(orderId: Int, text: String, onSaved: () -> Unit) {
        viewModelScope.launch {
            reviewDao.insertReview(
                Review(orderId = orderId, userEmail = userEmail, text = text)
            )
            onSaved()
        }
    }
}

class OrdersViewModelFactory(
    private val application: Application,
    private val userEmail: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val repository = OrderRepository(db.orderDao())
        val reviewDao = db.reviewDao()

        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel(application, repository, reviewDao, userEmail) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
