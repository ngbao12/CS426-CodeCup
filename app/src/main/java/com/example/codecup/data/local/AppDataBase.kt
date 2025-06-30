package com.example.codecup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.UserProfile

@Database(entities = [CartItem::class, UserProfile::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
}
