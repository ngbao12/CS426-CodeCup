package com.example.codecup.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.codecup.R
import com.example.codecup.data.model.CoffeeItem
import com.example.codecup.ui.common.OptionSelector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import com.example.codecup.ui.common.DetailsTopBar
import com.example.codecup.viewmodel.DetailsViewModel
import androidx.lifecycle.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    coffeeItem: CoffeeItem,
    viewModel: DetailsViewModel =  DetailsViewModel()
) {
    val quantity = viewModel.quantity
    val shotIndex = viewModel.shotIndex
    val selectIndex = viewModel.selectIndex
    val sizeIndex = viewModel.sizeIndex
    val iceIndex = viewModel.iceIndex

    val totalPrice = viewModel.calculateTotalPrice(coffeeItem.price)

    val selectIcons = getSelectIcons()
    val sizeIcons = getSizeIcons()
    val iceIcons = getIceIcons()

    Scaffold(
        topBar = {
            DetailsTopBar(
                navController = navController,
                onCartClick = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp)
                    .background(Color(0xFFF7F8FB), shape = RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = coffeeItem.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(coffeeItem.name, style = MaterialTheme.typography.bodyLarge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.decreaseQuantity() }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(13.dp))
                    }
                    Text(quantity.toString(), fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp))
                    IconButton(onClick = { viewModel.increaseQuantity() }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(13.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OptionSelector("Shot", textOptions = listOf("Single", "Double"), selectedIndex = shotIndex) {
                viewModel.selectShot(it)
            }

            OptionSelector("Select", iconOptions = selectIcons, selectedIndex = selectIndex) {
                viewModel.selectSelect(it)
            }

            OptionSelector("Size", iconOptions = sizeIcons, selectedIndex = sizeIndex) {
                viewModel.selectSize(it)
            }

            OptionSelector("Ice", iconOptions = iceIcons, selectedIndex = iceIndex) {
                viewModel.selectIce(it)
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Amount", style = MaterialTheme.typography.bodyLarge)
                Text("$${"%.2f".format(totalPrice)}", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: Add to cart */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Add to Cart")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    val navController = rememberNavController()
    val coffeeItem = CoffeeItem(
        id = 1,
        name = "Cappuccino",
        price = 3.5,
        imageRes = R.drawable.cappuccino
    )
    DetailsScreen(navController, coffeeItem)
}
