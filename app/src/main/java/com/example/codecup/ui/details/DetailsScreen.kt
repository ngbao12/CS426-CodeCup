package com.example.codecup.ui.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.codecup.R
import com.example.codecup.data.model.CoffeeItem
import com.example.codecup.ui.common.DetailsTopBar
import com.example.codecup.ui.common.OptionSelector
import com.example.codecup.viewmodel.DetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    coffeeItem: CoffeeItem,
    viewModel: DetailsViewModel = viewModel()
) {
    Log.d("DetailsScreen", "DetailsScreen recomposed with coffeeItem: $coffeeItem")

    val quantity by viewModel.quantity
    val shotIndex by viewModel.shotIndex
    val selectIndex by viewModel.selectIndex
    val sizeIndex by viewModel.sizeIndex
    val iceIndex by viewModel.iceIndex

    val totalPrice = viewModel.calculateTotalPrice(coffeeItem.price)

    Scaffold(
        containerColor = Color.White,
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
                .padding(horizontal = 20.dp)
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

            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            OptionSelector(
                label = "Shot",
                textOptions = listOf("Single", "Double"),
                selectedIndex = shotIndex,
                onSelect = { viewModel.selectShot(it) }
            )
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            OptionSelector(
                label = "Select",
                iconOptions = viewModel.selectIcons,
                selectedIndex = selectIndex,
                onSelect = { viewModel.selectSelect(it) }
            )

            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            OptionSelector(
                label = "Size",
                iconOptions = viewModel.sizeIcons,
                selectedIndex = sizeIndex,
                onSelect = { viewModel.selectSize(it) }
            )
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            OptionSelector(
                label = "Ice",
                iconOptions = viewModel.iceIcons,
                selectedIndex = iceIndex,
                onSelect = { viewModel.selectIce(it) }
            )

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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF324A59)),
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
