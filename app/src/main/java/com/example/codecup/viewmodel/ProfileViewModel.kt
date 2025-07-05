package com.example.codecup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.codecup.data.local.AppDatabase
import com.example.codecup.data.model.UserProfile
import com.example.codecup.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application,
    private val repository: UserProfileRepository,
    private val userEmail: String
) : AndroidViewModel(application) {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    val userName: String
        get() = _profile.value?.name ?: "Guest"

    val address: String
        get() = _profile.value?.address ?: "Unknown"

    init {
        viewModelScope.launch {
            _profile.value = repository.getProfile(userEmail)
        }
    }

    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch {
            val updated = profile.copy(email = userEmail)
            repository.saveProfile(updated)
            _profile.value = updated
        }
    }
}

class ProfileViewModelFactory(
    private val application: Application,
    private val userEmail: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "user-database"
        ).fallbackToDestructiveMigration().build()

        val dao = db.userDao()
        val repository = UserProfileRepository(dao)

        return ProfileViewModel(application, repository, userEmail) as T
    }
}
