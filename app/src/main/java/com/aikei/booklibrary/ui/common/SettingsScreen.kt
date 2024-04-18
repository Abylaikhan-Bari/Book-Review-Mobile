package com.aikei.booklibrary.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.ui.theme.Custom

@Composable
fun SettingsScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                viewModel.signOut()
                navController.navigate("login") {
                    popUpTo("bookList") { inclusive = true }  // Adjust this as necessary based on your navigation graph
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Custom),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Out")
        }
        Button(
            onClick = { navController.navigate("bookList/{token}") },
            colors = ButtonDefaults.buttonColors(containerColor = Custom),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Book list screen")
        }
    }
}