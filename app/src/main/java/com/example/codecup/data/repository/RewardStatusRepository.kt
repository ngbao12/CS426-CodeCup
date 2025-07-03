package com.example.codecup.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codecup.data.model.RewardStatus
import com.example.codecup.data.local.RewardStatusDao

class RewardStatusRepository(private val dao: RewardStatusDao, private val userEmail: String) {

    suspend fun getStatus(): RewardStatus {
        return dao.getStatus(userEmail) ?: RewardStatus(userEmail = userEmail)
    }

    suspend fun updateStatus(stamps: Int, points: Int) {
        dao.insertOrUpdate(
            RewardStatus(userEmail = userEmail, stamps = stamps, points = points)
        )
    }
}
