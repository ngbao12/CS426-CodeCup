package com.example.codecup.data.repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.codecup.data.model.RewardItem
import com.example.codecup.data.local.RewardDao

class RewardRepository(private val rewardDao: RewardDao) {

    fun rewardsFlow(userEmail: String): Flow<List<RewardItem>> =
        rewardDao.getAllRewardsFlow(userEmail)

    fun totalPointsFlow(userEmail: String): Flow<Int> =
        rewardDao.getTotalPoints(userEmail)

    suspend fun addReward(reward: RewardItem) {
        rewardDao.insertReward(reward)
    }

    suspend fun getAllRewards(userEmail: String): List<RewardItem> {
        return rewardDao.getAllRewards(userEmail)
    }

    suspend fun getTotalPoints(userEmail: String): Int {
        return rewardDao.getTotalPointsOnce(userEmail) ?: 0
    }
}

