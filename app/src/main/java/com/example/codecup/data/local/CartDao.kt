package com.example.codecup.data.local

import androidx.room.*
import com.example.codecup.data.model.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items WHERE userEmail = :email")
    suspend fun getAll(email: String): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items WHERE userEmail = :email")
    suspend fun clearAll(email: String)
}
