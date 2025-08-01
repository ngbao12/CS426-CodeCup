package com.example.codecup.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.codecup.data.local.DatabaseProvider
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.model.RewardItem
import com.example.codecup.data.repository.RewardRepository
import com.example.codecup.data.repository.RewardStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RewardsViewModel(
    application: Application,
    private val rewardRepository: RewardRepository,
    private val statusRepository: RewardStatusRepository,
    private val userEmail: String
) : AndroidViewModel(application) {

    private val _rewardItems = mutableStateListOf<RewardItem>()
    val rewardItems: List<RewardItem> = _rewardItems

    var stampCount by mutableStateOf(0)
        private set

    var totalPoints by mutableStateOf(0)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val rewards = rewardRepository.getAllRewards(userEmail)
            val status = statusRepository.getStatus()

            withContext(Dispatchers.Main) {
                _rewardItems.addAll(rewards)
                stampCount = status.stamps
                totalPoints = status.points
            }
        }
    }

    fun addRewardsFromCartItems(cartItems: List<CartItem>) {
        val rewards = cartItems.map { item ->
            val total = item.price
            val points = calculatePoints(total)

            RewardItem(
                name = item.name,
                points = points,
                userEmail = userEmail
            )
        }

        val quantitySum = cartItems.sumOf { it.quantity }
        val pointsSum = rewards.sumOf { it.points }

        stampCount = (stampCount + quantitySum).coerceAtMost(8)
        totalPoints += pointsSum

        viewModelScope.launch(Dispatchers.IO) {
            rewards.forEach { rewardRepository.addReward(it) }
            statusRepository.updateStatus(stampCount, totalPoints)

            val updated = rewardRepository.getAllRewards(userEmail)
            withContext(Dispatchers.Main) {
                _rewardItems.clear()
                _rewardItems.addAll(updated)
            }
        }
    }

    fun addNegativeReward(rewardItem: RewardItem, onComplete: () -> Unit) {
        totalPoints += rewardItem.points

        viewModelScope.launch(Dispatchers.IO) {
            rewardRepository.addReward(rewardItem.copy(userEmail = userEmail))
            statusRepository.updateStatus(stampCount, totalPoints)

            val updated = rewardRepository.getAllRewards(userEmail)
            withContext(Dispatchers.Main) {
                _rewardItems.clear()
                _rewardItems.addAll(updated)
                onComplete()
            }
        }
    }

    fun resetStamps() {
        stampCount = 0
        viewModelScope.launch(Dispatchers.IO) {
            statusRepository.updateStatus(stampCount, totalPoints)
        }
    }

    private fun calculatePoints(total: Double): Int {
        return (total * 10).toInt()
    }
}

class RewardsViewModelFactory(
    private val application: Application,
    private val userEmail: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DatabaseProvider.getDatabase(application)
        val rewardRepository = RewardRepository(db.rewardDao())
        val rewardStatusRepository = RewardStatusRepository(db.rewardStatusDao(), userEmail)


        if (modelClass.isAssignableFrom(RewardsViewModel::class.java)) {
            return RewardsViewModel(application, rewardRepository, rewardStatusRepository, userEmail) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
