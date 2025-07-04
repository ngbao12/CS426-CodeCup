package com.example.codecup.data.local

import androidx.room.*
import com.example.codecup.data.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM Review WHERE orderId = :orderId AND userEmail = :userEmail LIMIT 1")
    suspend fun getReviewByOrderIdAndUser(orderId: Int, userEmail: String): Review?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review)
}
