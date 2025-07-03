package com.example.codecup.data.local

import androidx.room.*
import com.example.codecup.data.model.UserProfile

@Dao
interface UserDao {

    @Query("SELECT * FROM user_profile WHERE email = :email")
    suspend fun getProfile(email: String): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: UserProfile)
}
