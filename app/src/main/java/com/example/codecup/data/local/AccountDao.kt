package com.example.codecup.data.local

import androidx.room.*
import com.example.codecup.data.model.Account

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Account): Long

    @Query("SELECT * FROM account WHERE email = :email")
    suspend fun getAccountByEmail(email: String): Account?

    @Query("SELECT * FROM account WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): Account?
}

class FakeAccountDao : AccountDao {
    override suspend fun insertAccount(account: Account): Long {
        return 1L
    }

    override suspend fun getAccountByEmail(email: String): Account? {
        return null
    }

    override suspend fun login(email: String, password: String): Account? {
        return if (email == "test@example.com" && password == "123456") {
            Account(email = email, password = password, fullName = "Test User")
        } else {
            null
        }
    }
}

