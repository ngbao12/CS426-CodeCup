package com.example.codecup.data.repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.codecup.data.model.OrderItem
import com.example.codecup.data.local.OrderDao

class OrderRepository(private val orderDao: OrderDao) {

    fun getOngoingOrders(): Flow<List<OrderItem>> = orderDao.getOrdersByStatus("ongoing")

    fun getHistoryOrders(): Flow<List<OrderItem>> = orderDao.getOrdersByStatus("history")

    suspend fun insertOrder(order: List<OrderItem>) = orderDao.insertAll(order)

    suspend fun updateOrder(order: OrderItem) = orderDao.update(order)
}
