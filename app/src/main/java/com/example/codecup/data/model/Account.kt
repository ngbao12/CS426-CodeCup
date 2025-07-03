package com.example.codecup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey val email: String,
    val password: String,
    val fullName: String
)
