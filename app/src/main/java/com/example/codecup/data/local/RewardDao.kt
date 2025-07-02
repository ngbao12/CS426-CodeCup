package com.example.codecup.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.codecup.data.model.RewardItem

@Dao
interface RewardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReward(reward: RewardItem)

    @Query("SELECT * FROM rewards ORDER BY timestamp DESC")
    fun getAllRewardsFlow(): Flow<List<RewardItem>>

    @Query("SELECT * FROM rewards ORDER BY timestamp DESC")
    suspend fun getAllRewards(): List<RewardItem>

    @Query("SELECT SUM(points) FROM rewards")
    fun getTotalPoints(): Flow<Int>

    @Query("SELECT SUM(points) FROM rewards")
    suspend fun getTotalPointsOnce(): Int?
}
