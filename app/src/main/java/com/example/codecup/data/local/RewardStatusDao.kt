package com.example.codecup.data.local

import androidx.room.*
import com.example.codecup.data.model.RewardStatus

@Dao
interface RewardStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(status: RewardStatus)

    @Query("SELECT * FROM reward_status WHERE userEmail = :email")
    suspend fun getStatus(email: String): RewardStatus?
}
