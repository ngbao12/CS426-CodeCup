package com.example.codecup.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codecup.data.model.RewardItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RewardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReward(reward: RewardItem)

    @Query("SELECT * FROM rewards WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getAllRewardsFlow(email: String): Flow<List<RewardItem>>

    @Query("SELECT * FROM rewards WHERE userEmail = :email ORDER BY timestamp DESC")
    suspend fun getAllRewards(email: String): List<RewardItem>

    @Query("SELECT SUM(points) FROM rewards WHERE userEmail = :email")
    fun getTotalPoints(email: String): Flow<Int>

    @Query("SELECT SUM(points) FROM rewards WHERE userEmail = :email")
    suspend fun getTotalPointsOnce(email: String): Int?
}
