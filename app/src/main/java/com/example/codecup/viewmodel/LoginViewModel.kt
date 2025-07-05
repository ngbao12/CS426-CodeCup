package com.example.codecup.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.codecup.data.local.DatabaseProvider
import com.example.codecup.data.model.Account
import com.example.codecup.data.repository.AccountRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun login(
        onSuccess: (Account) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            val account = accountRepository.login(email, password)
            isLoading = false
            if (account != null) {
                onSuccess(account)
            } else {
                onError()
            }
        }
    }
}

class LoginViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val accountRepository = AccountRepository(db.accountDao())

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(accountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
