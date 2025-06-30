package com.example.codecup.data.repository

import com.example.codecup.data.local.CartDao
import com.example.codecup.data.model.CartItem

class CartRepository(private val cartDao: CartDao) {

    suspend fun getAllItems(): List<CartItem> = cartDao.getAll()

    suspend fun insert(item: CartItem) = cartDao.insert(item)

    suspend fun delete(item: CartItem) = cartDao.delete(item)
}

