package com.aikei.booklibrary.ui.feature.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.aikei.booklibrary.ui.theme.Custom
import kotlinx.coroutines.delay

@Composable
fun RegistrationScreen(navController: NavController) {
    val registrationViewModel: RegistrationViewModel = hiltViewModel()
    val registrationState by registrationViewModel.registrationState.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
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

        // Register button
        Button(
            onClick = {
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                } else {
                    registrationViewModel.register(username, email, password, confirmPassword)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Custom),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        // Go to Login button
        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(containerColor = Custom),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Login")
        }

        // Error message display
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Success message display
        registrationState?.let {
            if (it is RegistrationViewModel.RegistrationState.Success) {
                Text(
                    text = "Registration successful!",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
                LaunchedEffect(Unit) {
                    delay(2000) // Delay for 2 seconds before navigating to login
                    navController.navigate("login")
                }
            }
        }
    }
}