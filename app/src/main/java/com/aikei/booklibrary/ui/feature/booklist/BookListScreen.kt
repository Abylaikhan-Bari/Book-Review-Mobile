package com.aikei.booklibrary.ui.feature.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aikei.booklibrary.ui.MainViewModel

@Composable
fun BookListScreen(navController: NavController, token: String) {
    val bookListViewModel: BookListViewModel = hiltViewModel()

    val bookResource by bookListViewModel.books.collectAsState()
    LaunchedEffect(key1 = token) {
        bookListViewModel.loadBooks(token)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { navController.navigate("settings") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Settings")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (bookResource) {
            is Resource.Loading<*> -> Text("Loading...")
            is Resource.Success<*> -> {
                LazyColumn {
                    items((bookResource as Resource.Success<List<Book>>).data) { book ->
                        BookItem(book) {
                            // Navigate to BookDetailScreen when a book item is clicked
                            navController.navigate("bookDetail/${book.id}")
                        }
                    }
                }
            }
            is Resource.Error -> Text("Error: ${(bookResource as Resource.Error).message}")
        }
    }
}



@Composable
fun BookItem(book: Book, onItemClick: () -> Unit) {
    Column {
        Text("Title: ${book.title}")
        Text("Author: ${book.author}")
        // Use clickable modifier to make the item clickable
        Text(
            "Click to view details",
            modifier = Modifier.clickable(onClick = onItemClick)
        )
    }
}

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Out")
        }
    }
}
