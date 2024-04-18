package com.aikei.booklibrary.ui.feature.addbook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.ui.common.MainViewModel

@Composable
fun AddBookScreen(navController: NavController, token: String) {
    val viewModel: AddBookViewModel = hiltViewModel()
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Observe the state from the ViewModel
    val bookAddState by viewModel.bookAddState.collectAsState()

    // Handle state changes
    LaunchedEffect(bookAddState) {
        when (bookAddState) {
            is Resource.Success -> {
                navController.navigate("bookList/$token") {
                    popUpTo("bookList/$token") { inclusive = true }
                }
            }
            is Resource.Error -> errorMessage = (bookAddState as Resource.Error).message
            else -> {} // Do nothing on Loading or default state
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Add New Book", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = synopsis,
            onValueChange = { synopsis = it },
            label = { Text("Synopsis") },
            modifier = Modifier.fillMaxWidth()
        )

        if (!errorMessage.isNullOrEmpty()) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotEmpty() && author.isNotEmpty() && synopsis.isNotEmpty()) {
                    viewModel.addBook(token, title, author, synopsis)
                } else {
                    errorMessage = "Please fill all fields."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Book")
        }
        Button(
            onClick = { navController.navigate("bookList/{token}") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Book list screen")
        }
    }
}