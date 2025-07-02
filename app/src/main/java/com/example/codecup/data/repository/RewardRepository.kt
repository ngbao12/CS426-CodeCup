package com.example.codecup.data.repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.codecup.data.model.RewardItem
import com.example.codecup.data.local.RewardDao

class RewardRepository(private val rewardDao: RewardDao) {

    val rewardsFlow: Flow<List<RewardItem>> = rewardDao.getAllRewardsFlow()
    val totalPointsFlow: Flow<Int> = rewardDao.getTotalPoints()

    suspend fun addReward(reward: RewardItem) {
        rewardDao.insertReward(reward)
    }

    suspend fun getAllRewards(): List<RewardItem> {
        return rewardDao.getAllRewards()
    }

    suspend fun getTotalPoints(): Int {
        return rewardDao.getTotalPointsOnce() ?: 0
    }
}

