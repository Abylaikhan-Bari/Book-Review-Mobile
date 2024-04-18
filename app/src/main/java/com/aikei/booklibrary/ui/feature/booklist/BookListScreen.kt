package com.aikei.booklibrary.ui.feature.booklist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aikei.booklibrary.ui.common.MainViewModel

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

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("addBook/$token") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Book")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (bookResource) {
            is Resource.Loading<*> -> Text("Loading...")
            is Resource.Success<*> -> {
                LazyColumn {
                    items((bookResource as Resource.Success<List<Book>>).data) { book ->
                        BookItem(book) {
                            navController.navigate("bookDetail/$token/${book.id}")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            is Resource.Error -> Text("Error: ${(bookResource as Resource.Error).message}")
        }
    }
}

@Composable
fun BookItem(book: Book, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text("Title: ${book.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Click to view details",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

