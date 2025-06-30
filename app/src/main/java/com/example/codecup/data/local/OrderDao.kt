package com.example.codecup.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.codecup.data.model.OrderItem

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<OrderItem>)

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY timestamp DESC")
    fun getOrdersByStatus(status: String): Flow<List<OrderItem>>

    @Update
    suspend fun update(order: OrderItem)
}
