package com.example.codecup.data.repository

import com.example.codecup.data.local.AccountDao
import com.example.codecup.data.model.Account

class AccountRepository(private val dao: AccountDao) {
    suspend fun insertAccount(account: Account): Long = dao.insertAccount(account)
    suspend fun getAccountByEmail(email: String): Account? = dao.getAccountByEmail(email)
    suspend fun login(email: String, password: String): Account? = dao.login(email, password)
}
