package com.example.codecup.data.repository

import com.example.codecup.data.local.CartDao
import com.example.codecup.data.model.CartItem

class CartRepository(private val cartDao: CartDao) {

    suspend fun getAllItems(userEmail: String): List<CartItem> =
        cartDao.getAll(userEmail)

    suspend fun insert(item: CartItem) =
        cartDao.insert(item)

    suspend fun delete(item: CartItem) =
        cartDao.delete(item)

    suspend fun clearAll(userEmail: String) =
        cartDao.clearAll(userEmail)
}
