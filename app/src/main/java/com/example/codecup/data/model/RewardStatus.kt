package com.example.codecup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reward_status")
data class RewardStatus(
    @PrimaryKey val userEmail: String,
    val stamps: Int = 0,
    val points: Int = 0
)
