package com.example.codecup.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.codecup.ui.details.getIceIcons
import com.example.codecup.ui.details.getSelectIcons
import com.example.codecup.ui.details.getSizeIcons
import androidx.compose.runtime.State
import android.util.Log
import com.example.codecup.ui.details.IconData


class DetailsViewModel : ViewModel() {
    private val _quantity = mutableStateOf(1)
    val quantity: State<Int> = _quantity

    private val _shotIndex = mutableStateOf(0)
    val shotIndex: State<Int> = _shotIndex

    private val _selectIndex = mutableStateOf(0)
    val selectIndex: State<Int> = _selectIndex

    private val _sizeIndex = mutableStateOf(0)
    val sizeIndex: State<Int> = _sizeIndex

    private val _iceIndex = mutableStateOf(0)
    val iceIndex: State<Int> = _iceIndex

    val selectIcons: List<IconData> = getSelectIcons()
    val sizeIcons: List<IconData> = getSizeIcons()
    val iceIcons: List<IconData> = getIceIcons()

    init {
        Log.d("DetailsViewModel", "ViewModel created")
    }

    fun increaseQuantity() {
        _quantity.value++
        Log.d("DetailsViewModel", "Quantity increased: ${_quantity.value}")
    }

    fun decreaseQuantity() {
        if (_quantity.value > 1) {
            _quantity.value--
            Log.d("DetailsViewModel", "Quantity decreased: ${_quantity.value}")
        }
    }

    fun selectShot(index: Int) {
        if (index in 0..1 && _shotIndex.value != index) {
            _shotIndex.value = index
            Log.d("DetailsViewModel", "Shot selected: $index")
        } else {
            Log.w("DetailsViewModel", "Invalid or same shot index: $index")
        }
    }

    fun selectSelect(index: Int) {
        if (index in 0..1 && _selectIndex.value != index) {
            _selectIndex.value = index
            Log.d("DetailsViewModel", "Select selected: $index")
        } else {
            Log.w("DetailsViewModel", "Invalid or same select index: $index")
        }
    }

    fun selectSize(index: Int) {
        if (index in 0..2 && _sizeIndex.value != index) {
            _sizeIndex.value = index
            Log.d("DetailsViewModel", "Size selected: $index")
        } else {
            Log.w("DetailsViewModel", "Invalid or same size index: $index")
        }
    }

    fun selectIce(index: Int) {
        if (index in 0..2 && _iceIndex.value != index) {
            _iceIndex.value = index
            Log.d("DetailsViewModel", "Ice selected: $index")
        } else {
            Log.w("DetailsViewModel", "Invalid or same ice index: $index")
        }
    }

    fun calculateTotalPrice(basePrice: Double): Double {
        Log.d("DetailsViewModel", "Calculating total price: shotIndex=${_shotIndex.value}, sizeIndex=${_sizeIndex.value}, quantity=${_quantity.value}")
        val shotPrice = if (_shotIndex.value == 1) 0.5 else 0.0
        val sizeMultiplier = when (_sizeIndex.value) {
            0 -> 1.0 // Small
            1 -> 1.2 // Medium
            else -> 1.5 // Large
        }
        return basePrice * _quantity.value * sizeMultiplier + shotPrice
    }
}
