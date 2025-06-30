package com.example.codecup.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.codecup.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ExperimentalMaterial3Api



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableProfileField(
    icon: Int,
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onDoneEditing: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF7F8FB), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Color(0xFF0D1A26),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
            )

            if (isEditing) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF324A59),
                        unfocusedIndicatorColor = Color.LightGray,
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { onDoneEditing() })
                )
            } else {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF324A59),
                        fontSize = 14.sp
                    )
                )
            }
        }
        IconButton(onClick = onEditClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit",
                tint = Color(0xFF1C1C1C)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditableProfileField() {
    var value by remember { mutableStateOf("Ngoc Bao") }

    EditableProfileField(
        icon = R.drawable.ic_user,
        label = "Full name",
        value = value,
        isEditing = true,
        onValueChange = { value = it },
        onEditClick = {},
        onDoneEditing = {}
    )
}


