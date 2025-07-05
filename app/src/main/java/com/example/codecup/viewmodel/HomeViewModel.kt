package com.example.codecup.viewmodel

import androidx.lifecycle.ViewModel
import com.example.codecup.R
import com.example.codecup.data.model.CoffeeItem
import java.util.Calendar

class HomeViewModel : ViewModel() {

    val coffeeList = listOf(
        CoffeeItem(1, "Americano", 2.0, R.drawable.americano),
        CoffeeItem(2, "Cappuccino", 3.0, R.drawable.cappuccino),
        CoffeeItem(3, "Mocha", 4.0, R.drawable.mocha),
        CoffeeItem(4, "Flat White", 5.0, R.drawable.flat_white)
    )
    val stamps = 4

    fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            in 18..21 -> "Good evening"
            else -> "Good night"
        }
    }

}