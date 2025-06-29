package com.example.codecup.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class DetailsViewModel : ViewModel() {
    var quantity by mutableStateOf(1)
        private set

    var shotIndex by mutableStateOf(0)
        private set

    var selectIndex by mutableStateOf(1)
        private set

    var sizeIndex by mutableStateOf(1)
        private set

    var iceIndex by mutableStateOf(1)
        private set

    fun increaseQuantity() { quantity++ }
    fun decreaseQuantity() { if (quantity > 1) quantity-- }

    fun selectShot(index: Int) { shotIndex = index }
    fun selectSelect(index: Int) { selectIndex = index }
    fun selectSize(index: Int) { sizeIndex = index }
    fun selectIce(index: Int) { iceIndex = index }

    fun calculateTotalPrice(basePrice: Double): Double {
        return basePrice * quantity
    }
}
