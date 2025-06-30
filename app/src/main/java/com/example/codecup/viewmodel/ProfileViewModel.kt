package com.example.codecup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.codecup.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.codecup.data.local.AppDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.codecup.data.repository.UserProfileRepository

class ProfileViewModel(
    application: Application,
    private val repository: UserProfileRepository
) : AndroidViewModel(application) {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    val userName: String
        get() = _profile.value?.name ?: "Guest"

    init {
        viewModelScope.launch {
            _profile.value = repository.getProfile()
        }
    }

    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch {
            repository.saveProfile(profile)
            _profile.value = profile
        }
    }
}

class ProfileViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "user-database"
        ).fallbackToDestructiveMigration().build()

        val dao = db.userDao()
        val repository = UserProfileRepository(dao)

        return ProfileViewModel(application, repository) as T
    }
}

