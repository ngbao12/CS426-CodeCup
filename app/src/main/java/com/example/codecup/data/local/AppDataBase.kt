package com.example.codecup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.UserProfile
import com.example.codecup.data.local.CartDao
import com.example.codecup.data.local.UserDao
import com.example.codecup.data.model.OrderItem
import com.example.codecup.data.model.RewardItem
import com.example.codecup.data.model.RewardStatus
import com.example.codecup.data.model.Account

@Database(entities = [CartItem::class, UserProfile::class, OrderItem::class, RewardItem::class, RewardStatus::class, Account::class], version = 10)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun rewardDao(): RewardDao
    abstract fun rewardStatusDao(): RewardStatusDao
    abstract fun accountDao(): AccountDao
}
