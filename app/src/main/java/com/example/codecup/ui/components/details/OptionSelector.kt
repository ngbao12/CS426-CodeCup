package com.example.codecup.ui.components.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import android.util.Log
import androidx.compose.foundation.layout.Arrangement


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionSelector(
    label: String,
    textOptions: List<String>? = null,
    iconOptions: List<IconData>? = null,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Log.d("OptionSelector", "OptionSelector recomposed with label=$label, selectedIndex=$selectedIndex")

    val primary = MaterialTheme.colorScheme.primary
    val outline = MaterialTheme.colorScheme.outline
    val onBackground = MaterialTheme.colorScheme.onBackground

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = onBackground,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            textOptions?.forEachIndexed { index, option ->
                key(index) {
                    val isSelected = selectedIndex == index
                    OutlinedButton(
                        onClick = {
                            Log.d("OptionSelector", "Text option clicked: index=$index, option=$option")
                            onSelect(index)
                        },
                        border = BorderStroke(2.dp, if (isSelected) primary else outline),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .height(28.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isSelected) primary else outline
                        )
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            iconOptions?.forEachIndexed { index, iconData ->
                key(index) {
                    val isSelected = selectedIndex == index
                    IconButton(
                        onClick = {
                            Log.d("OptionSelector", "Icon option clicked: index=$index, description=${iconData.description}")
                            onSelect(index)
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(48.dp)
                            .background(
                                color = if (isSelected) primary.copy(alpha = 0.1f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = iconData.resId),
                            contentDescription = iconData.description,
                            modifier = Modifier.size(iconData.size.dp),
                            tint = if (isSelected) primary else outline
                        )
                    }
                }
            }
        }
    }
}
