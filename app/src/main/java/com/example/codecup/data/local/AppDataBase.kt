package com.example.codecup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codecup.data.model.Account
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.OrderItem
import com.example.codecup.data.model.Review
import com.example.codecup.data.model.RewardItem
import com.example.codecup.data.model.RewardStatus
import com.example.codecup.data.model.UserProfile


@Database(entities = [CartItem::class, UserProfile::class, OrderItem::class, RewardItem::class, RewardStatus::class, Account::class, Review::class], version = 15)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun rewardDao(): RewardDao
    abstract fun rewardStatusDao(): RewardStatusDao
    abstract fun accountDao(): AccountDao
    abstract fun reviewDao(): ReviewDao
}
