package com.example.codecup.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codecup.data.model.RewardStatus
import com.example.codecup.data.local.RewardStatusDao

class RewardStatusRepository(private val dao: RewardStatusDao) {
    suspend fun getStatus(): RewardStatus = dao.getStatus() ?: RewardStatus()
    suspend fun updateStatus(stamps: Int, points: Int) {
        dao.insertOrUpdate(RewardStatus(1, stamps, points))
    }
}
