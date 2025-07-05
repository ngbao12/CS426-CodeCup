package com.example.codecup.data.repository

import com.example.codecup.data.local.RewardDao
import com.example.codecup.data.model.RewardItem
import kotlinx.coroutines.flow.Flow

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

