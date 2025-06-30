package com.example.codecup.data.repository

import com.example.codecup.data.model.UserProfile
import com.example.codecup.data.local.UserDao

class UserProfileRepository(private val dao: UserDao) {

    suspend fun getProfile(): UserProfile? = dao.getProfile()

    suspend fun saveProfile(profile: UserProfile) = dao.saveProfile(profile)
}

