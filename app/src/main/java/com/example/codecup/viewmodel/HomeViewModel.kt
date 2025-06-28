package com.example.codecup.viewmodel

import androidx.lifecycle.ViewModel
import com.example.codecup.R
import com.example.codecup.data.model.CoffeeItem

class HomeViewModel : ViewModel() {

    val coffeeList = listOf(
        CoffeeItem(1, "Americano", 2.5, R.drawable.americano),
        CoffeeItem(2, "Cappuccino", 3.0, R.drawable.cappuccino),
        CoffeeItem(3, "Mocha", 3.2, R.drawable.mocha),
        CoffeeItem(4, "Flat White", 3.1, R.drawable.flat_white)
    )
    val stamps = 4
}