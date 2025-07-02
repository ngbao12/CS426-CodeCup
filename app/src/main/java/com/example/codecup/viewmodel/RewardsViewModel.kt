package com.example.codecup.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.codecup.data.local.AppDatabase
import com.example.codecup.data.model.RewardItem
import com.example.codecup.data.repository.RewardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.local.DatabaseProvider

class RewardsViewModel(
    application: Application,
    private val repository: RewardRepository
) : AndroidViewModel(application) {

    private val _rewardItems = mutableStateListOf<RewardItem>()
    val rewardItems: List<RewardItem> = _rewardItems

    var totalPoints: Int = 0
        private set

    val stampCount: Int
        get() = _rewardItems.size.coerceAtMost(8)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _rewardItems.addAll(repository.getAllRewards())
            totalPoints = repository.getTotalPoints() ?: 0
        }
    }

    fun addRewardsFromCartItems(cartItems: List<CartItem>) {
        val rewards = cartItems.map { item ->
            val total = item.price * item.quantity
            val points = calculatePoints(total)

            RewardItem(
                name = item.name, // giống với OrderItem.name
                points = points
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            rewards.forEach { repository.addReward(it) }

            val updated = repository.getAllRewards()
            val updatedPoints = repository.getTotalPoints()

            withContext(Dispatchers.Main) {
                _rewardItems.clear()
                _rewardItems.addAll(updated)
                totalPoints = updatedPoints
            }
        }
    }

    fun resetStamps() {
        // Logic reset nếu cần (hiện để trống)
    }

    private fun calculatePoints(total: Double): Int {
        return (total / 10000).toInt() * 10
    }
}

class RewardsViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val rewardDao = db.rewardDao()
        val repository = RewardRepository(rewardDao)

        if (modelClass.isAssignableFrom(RewardsViewModel::class.java)) {
            return RewardsViewModel(application, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}