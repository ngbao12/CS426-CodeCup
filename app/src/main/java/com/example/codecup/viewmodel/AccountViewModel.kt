package com.example.codecup.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.codecup.data.local.DatabaseProvider
import com.example.codecup.data.model.Account
import com.example.codecup.data.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AccountViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    // Private mutable state flow
    private val _currentAccount = MutableStateFlow<Account?>(null)

    // Public read-only state flow
    val currentAccount: StateFlow<Account?> = _currentAccount.asStateFlow()

    fun loadAccount(email: String) {
        viewModelScope.launch {
            val account = accountRepository.getAccountByEmail(email)
            _currentAccount.value = account
        }
    }

    fun logout() {
        _currentAccount.value = null
    }
}

class AccountViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val repo = AccountRepository(db.accountDao())

        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
