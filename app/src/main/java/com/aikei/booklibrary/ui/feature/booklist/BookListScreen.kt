package com.aikei.booklibrary.ui.feature.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun BookListScreen(navController: NavController, token: String) {
    val bookListViewModel: BookListViewModel = hiltViewModel()

    val bookResource by bookListViewModel.books.collectAsState()
    LaunchedEffect(key1 = token) {
        bookListViewModel.loadBooks(token)
    }
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
