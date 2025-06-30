package com.example.codecup.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.codecup.viewmodel.ProfileViewModel
import com.example.codecup.data.model.UserProfile
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.TextField
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.codecup.ui.components.profile.EditableProfileField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val profile by viewModel.profile.collectAsState()

    var name: String by remember { mutableStateOf("") }
    var phone: String by remember { mutableStateOf("") }
    var email: String by remember { mutableStateOf("") }
    var address: String by remember { mutableStateOf("") }

    var editingField by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(profile) {
        profile?.let {
            name = it.name
            phone = it.phone
            email = it.email
            address = it.address
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "Profile",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ) ,
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
            isEditing = editingField == "email",
            onValueChange = { email = it },
            onEditClick = { editingField = "email" },
            onDoneEditing = {
                editingField = null
                viewModel.saveProfile(UserProfile(name = name, phone = phone, email = email, address = address))
            }
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
    }
}