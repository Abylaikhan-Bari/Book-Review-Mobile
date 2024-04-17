package com.aikei.booklibrary.ui.feature.booklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.ui.feature.login.LoginViewModel

@Composable
fun BookListScreen(navController: NavController) {
    val bookListViewModel: BookListViewModel = hiltViewModel()

    val bookResource by bookListViewModel.books.collectAsState()

    when (bookResource) {
        is Resource.Loading<*> -> Text("Loading...")
        is Resource.Success<*> -> {
            LazyColumn {
                items((bookResource as Resource.Success<List<Book>>).data) { book ->
                    BookItem(book)
                }
            }
        }
        is Resource.Error -> Text("Error: ${(bookResource as Resource.Error).message}")
    }
}


@Composable
fun BookItem(book: Book) {
    Column {
        Text("Title: ${book.title}")
        Text("Author: ${book.author}")
    }
}
