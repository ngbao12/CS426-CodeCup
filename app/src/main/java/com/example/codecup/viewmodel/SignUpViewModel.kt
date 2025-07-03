package com.example.codecup.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.codecup.data.repository.AccountRepository
import androidx.lifecycle.viewModelScope
import com.example.codecup.data.local.DatabaseProvider
import com.example.codecup.data.model.Account
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: AccountRepository) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var fullName by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun onEmailChange(value: String) { email = value }
    fun onPasswordChange(value: String) { password = value }
    fun onFullNameChange(value: String) { fullName = value }

    fun register(
        onSuccess: () -> Unit,
        onAccountExists: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            // Kiểm tra xem email đã tồn tại chưa
            val existingAccount = repository.getAccountByEmail(email)
            if (existingAccount != null) {
                isLoading = false
                onAccountExists()
                return@launch
            }

            // Nếu chưa có, thì insert tài khoản mới
            val result = repository.insertAccount(
                Account(email = email, password = password, fullName = fullName)
            )
            isLoading = false

            if (result != -1L) {
                onSuccess()
            } else {
                onAccountExists() // fallback nếu insert thất bại
            }
        }
    }
}


class SignUpViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val repository = AccountRepository(db.accountDao())
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
