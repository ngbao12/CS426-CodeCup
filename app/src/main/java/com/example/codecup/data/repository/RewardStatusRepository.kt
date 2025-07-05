package com.example.codecup.data.repository

import com.example.codecup.data.local.RewardStatusDao
import com.example.codecup.data.model.RewardStatus

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
