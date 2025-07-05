package com.example.codecup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class RewardItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val points: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val userEmail: String
)

