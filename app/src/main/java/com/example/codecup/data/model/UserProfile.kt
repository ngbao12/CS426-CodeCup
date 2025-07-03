package com.example.codecup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val email: String,
    val name: String,
    val phone: String,
    val address: String
)

