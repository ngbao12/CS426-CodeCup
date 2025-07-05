package com.example.codecup.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.codecup.data.model.OrderItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<OrderItem>)

    @Query("SELECT * FROM orders WHERE status = :status AND userEmail = :email ORDER BY timestamp DESC")
    fun getOrdersByStatus(status: String, email: String): Flow<List<OrderItem>>

    @Update
    suspend fun update(order: OrderItem)
}
