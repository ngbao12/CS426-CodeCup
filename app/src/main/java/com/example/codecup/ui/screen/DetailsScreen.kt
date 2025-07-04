package com.example.codecup.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.codecup.data.model.CoffeeItem
import com.example.codecup.data.model.CartItem
import com.example.codecup.ui.components.details.DetailsTopBar
import com.example.codecup.ui.components.details.OptionSelector
import com.example.codecup.viewmodel.CartViewModel
import com.example.codecup.viewmodel.DetailsViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.codecup.navigation.Screen
import com.example.codecup.ui.components.details.CartPreview
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.codecup.viewmodel.OrdersViewModel
import com.example.codecup.viewmodel.ProfileViewModel
import com.example.codecup.viewmodel.AccountViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    coffeeItem: CoffeeItem,
    viewModel: DetailsViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    accountViewModel: AccountViewModel = viewModel(),
) {
    val currentAccountState = accountViewModel.currentAccount.collectAsState()
    val quantity by viewModel.quantity
    val shotIndex by viewModel.shotIndex
    val selectIndex by viewModel.selectIndex
    val sizeIndex by viewModel.sizeIndex
    val iceIndex by viewModel.iceIndex

    val totalPrice = viewModel.calculateTotalPrice(coffeeItem.price)

    val shotOptions = listOf("Single", "Double")
    val selectOptions = listOf("Hot", "Cold")
    val sizeOptions = listOf("Small", "Medium", "Large")
    val iceOptions = listOf("Less Ice", "Normal", "More Ice")

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            CartPreview(cartViewModel = cartViewModel)

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showBottomSheet = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Close", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DetailsTopBar(
                navController = navController,
                onCartClick = {
                    showBottomSheet = true
                    scope.launch { sheetState.show() }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(15.dp)),
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
                Text(coffeeItem.name, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.decreaseQuantity() }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.onBackground)
                    }
                    Text(quantity.toString(), fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp), color = MaterialTheme.colorScheme.onBackground)
                    IconButton(onClick = { viewModel.increaseQuantity() }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            OptionSelector("Shot", textOptions = shotOptions, selectedIndex = shotIndex, onSelect = { viewModel.selectShot(it) })
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f), thickness = 1.dp, modifier = Modifier.padding(vertical = 6.dp))

            OptionSelector("Select", iconOptions = viewModel.selectIcons, selectedIndex = selectIndex, onSelect = { viewModel.selectSelect(it) })
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f), thickness = 1.dp, modifier = Modifier.padding(vertical = 6.dp))

            OptionSelector("Size", iconOptions = viewModel.sizeIcons, selectedIndex = sizeIndex, onSelect = { viewModel.selectSize(it) })
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f), thickness = 1.dp, modifier = Modifier.padding(vertical = 6.dp))

            OptionSelector("Ice", iconOptions = viewModel.iceIcons, selectedIndex = iceIndex, onSelect = { viewModel.selectIce(it) })

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Amount", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
                Text("$${"%.2f".format(totalPrice)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    val cartItem = CartItem(
                        id = coffeeItem.id,
                        name = coffeeItem.name,
                        price = totalPrice,
                        quantity = quantity,
                        imageRes = coffeeItem.imageRes,
                        shot = shotOptions[shotIndex],
                        select = selectOptions[selectIndex],
                        size = sizeOptions[sizeIndex],
                        ice = iceOptions[iceIndex],
                        userEmail = currentAccountState.value?.email.orEmpty()
                    )
                    cartViewModel.addToCart(cartItem)
                    navController.navigate("cart")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Add to Cart", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
