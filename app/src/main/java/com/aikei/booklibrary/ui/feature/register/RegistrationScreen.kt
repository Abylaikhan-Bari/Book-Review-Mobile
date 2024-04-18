package com.aikei.booklibrary.ui.feature.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.ui.feature.booklist.BookListViewModel

@Composable
fun RegistrationScreen(navController: NavController) {
    val registrationViewModel: RegistrationViewModel = hiltViewModel()
    val registrationState by registrationViewModel.registrationState.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(PaddingValues(16.dp))) {
        Text(text = "Register", style = MaterialTheme.typography.headlineSmall)

        // Input fields
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                } else {
                    registrationViewModel.register(username, email, password, confirmPassword)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Button(onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()) {
            Text("Go to Login")
        }

        // Error message display
        if (!errorMessage.isNullOrEmpty()) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.fillMaxWidth())
        }
    }

    // Handle navigation after successful registration
    LaunchedEffect(registrationState) {
        when (registrationState) {
            is RegistrationViewModel.RegistrationState.Success -> {
                navController.navigate("bookList/${(registrationState as RegistrationViewModel.RegistrationState.Success).token}") {
                    popUpTo("bookList") { inclusive = true }
                }
            }
            is RegistrationViewModel.RegistrationState.Error -> {
                errorMessage = (registrationState as RegistrationViewModel.RegistrationState.Error).message
            }
            else -> {}
        }
    }
}
