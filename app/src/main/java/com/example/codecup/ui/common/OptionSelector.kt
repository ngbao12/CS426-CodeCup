package com.example.codecup.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.example.codecup.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.foundation.clickable


@Composable
fun OptionSelector(
    label: String,
    textOptions: List<String>? = null,
    iconOptions: List<@Composable () -> Unit>? = null,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            textOptions?.forEachIndexed { index, option ->
                val isSelected = selectedIndex == index
                OutlinedButton(
                    onClick = { onSelect(index) },
                    border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(36.dp)
                ) {
                    Text(option, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                }
            }

            iconOptions?.forEachIndexed { index, iconComposable ->
                val alpha = if (index == selectedIndex) 1f else 0.3f
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onSelect(index) }
                        .alpha(alpha)
                ) {
                    iconComposable()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSizeSelector() {
    var selectedSize by remember { mutableStateOf(1) }

    val sizeIcons = listOf<@Composable () -> Unit>(
        {
            Box(
                modifier = Modifier
                    .size(48.dp), // khung nền cố định
                contentAlignment = Alignment.BottomCenter
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_size_s),
                    contentDescription = "Small",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        {
            Box(
                modifier = Modifier
                    .size(48.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_size_m),
                    contentDescription = "Medium",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        {
            Box(
                modifier = Modifier
                    .size(48.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_size_l),
                    contentDescription = "Large",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )


    OptionSelector(
        label = "Size",
        iconOptions = sizeIcons,
        selectedIndex = selectedSize,
        onSelect = { selectedSize = it }
    )
}
