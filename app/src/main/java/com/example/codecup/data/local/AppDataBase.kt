package com.example.codecup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.UserProfile
import com.example.codecup.data.local.CartDao
import com.example.codecup.data.local.UserDao
import com.example.codecup.data.model.OrderItem

@Database(entities = [CartItem::class, UserProfile::class, OrderItem::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
}
