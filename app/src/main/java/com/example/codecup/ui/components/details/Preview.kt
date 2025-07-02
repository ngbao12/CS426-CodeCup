package com.example.codecup.ui.components.details


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import com.example.codecup.viewmodel.CartViewModel
import com.example.codecup.data.model.CartItem
import com.example.codecup.data.repository.CartRepository
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codecup.ui.components.details.CartPreview
import androidx.compose.foundation.background
import com.example.codecup.viewmodel.OrdersViewModel
import com.example.codecup.viewmodel.ProfileViewModel
import com.example.codecup.viewmodel.RewardsViewModel
import androidx.navigation.NavController

@Composable
fun CartPreview(cartViewModel: CartViewModel){
    val cartItems = cartViewModel.cartItems

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your Cart",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(cartItems) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.SemiBold)
                        Text(
                            "${item.shot}, ${item.select}, ${item.size}, ${item.ice}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("x${item.quantity}", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            "$${"%.2f".format(item.price)}",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Divider(Modifier.padding(vertical = 12.dp))

        val total = cartItems.sumOf { it.price }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", style = MaterialTheme.typography.bodyLarge)
            Text("$${"%.2f".format(total)}", fontWeight = FontWeight.Bold)
        }
    }
}
