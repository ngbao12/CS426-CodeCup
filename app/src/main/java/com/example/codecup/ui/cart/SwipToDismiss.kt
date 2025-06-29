package com.example.codecup.ui.cart

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codecup.data.model.CartItem
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import com.example.codecup.R
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissItem(
    item: CartItem,
    onRemove: (CartItem) -> Unit
) {
    val dismissState = rememberDismissState()

    SwipeToDismiss(
        state = dismissState,
        background = {
            val fraction = dismissState.progress.fraction

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red.copy(alpha = 0.1f))
                    .padding(end = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (fraction > 0.2f) { // chỉ hiện nếu swipe đủ sâu
                    IconButton(onClick = { onRemove(item) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            }
        },
        directions = setOf(DismissDirection.EndToStart),
        dismissContent = {
            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation()
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text("${item.shot} | ${item.select} | ${item.size} | ${item.ice}", fontSize = 12.sp)
                        Text("x${item.quantity}", fontSize = 12.sp)
                    }
                    Text("$${"%.2f".format(item.price)}")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSwipeToDismissItem() {
    val dummyItem = CartItem(
        id = 1,
        name = "Cappuccino",
        price = 3.0,
        quantity = 1,
        imageRes = R.drawable.cappuccino,
        shot = "single",
        select = "iced",
        size = "medium",
        ice = "full ice"
    )
    SwipeToDismissItem(item = dummyItem, onRemove = {})
}