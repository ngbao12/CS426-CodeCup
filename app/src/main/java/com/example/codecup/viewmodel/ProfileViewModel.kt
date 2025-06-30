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

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "user-database"
    ).fallbackToDestructiveMigration().build()

    private val userDao = db.userDao()

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    init {
        viewModelScope.launch {
            _profile.value = userDao.getProfile()
        }
    }

    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch {
            userDao.saveProfile(profile)
            _profile.value = profile
        }
    }
}
