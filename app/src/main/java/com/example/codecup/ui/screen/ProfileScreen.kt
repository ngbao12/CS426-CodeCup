package com.example.codecup.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.codecup.R
import com.example.codecup.data.model.UserProfile
import com.example.codecup.ui.components.profile.EditableProfileField
import com.example.codecup.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val profile by viewModel.profile.collectAsState()

    var name: String by remember { mutableStateOf("") }
    var phone: String by remember { mutableStateOf("") }
    val email: String = viewModel.profile.value?.email ?: ""
    var address: String by remember { mutableStateOf("") }

    var editingField by remember { mutableStateOf<String?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(profile) {
        profile?.let {
            name = it.name
            phone = it.phone
            address = it.address
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "Profile",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        EditableProfileField(
            icon = R.drawable.ic_user,
            label = "Full name",
            value = name,
            isEditing = editingField == "name",
            onValueChange = { name = it },
            onEditClick = { editingField = "name" },
            onDoneEditing = {
                editingField = null
                viewModel.saveProfile(UserProfile(name = name, phone = phone, email = email, address = address))
            }
        )

        EditableProfileField(
            icon = R.drawable.ic_phone,
            label = "Phone number",
            value = phone,
            isEditing = editingField == "phone",
            onValueChange = { phone = it },
            onEditClick = { editingField = "phone" },
            onDoneEditing = {
                editingField = null
                viewModel.saveProfile(UserProfile(name = name, phone = phone, email = email, address = address))
            }
        )

        EditableProfileField(
            icon = R.drawable.ic_email,
            label = "Email",
            value = email,
            isEditing = false,
            onValueChange = {},
            onEditClick = {},
            onDoneEditing = {},
            isEditingEnabled = false
        )

        EditableProfileField(
            icon = R.drawable.ic_location,
            label = "Address",
            value = address,
            isEditing = editingField == "address",
            onValueChange = { address = it },
            onEditClick = { editingField = "address" },
            onDoneEditing = {
                editingField = null
                viewModel.saveProfile(UserProfile(name = name, phone = phone, email = email, address = address))
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { showLogoutDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Log Out", color = MaterialTheme.colorScheme.onPrimary)
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Log out", color = MaterialTheme.colorScheme.onBackground) },
                text = { Text("Are you sure to log out?", color = MaterialTheme.colorScheme.onBackground) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    ) {
                        Text("Yes", color = MaterialTheme.colorScheme.primary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel", color = MaterialTheme.colorScheme.primary)
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}
