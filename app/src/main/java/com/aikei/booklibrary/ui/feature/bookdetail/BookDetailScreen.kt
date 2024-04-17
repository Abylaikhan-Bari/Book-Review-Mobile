package com.aikei.booklibrary.ui.feature.bookdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book

@Composable
fun BookDetailScreen(token: String, bookId: String) {
    val bookDetailViewModel: BookDetailViewModel = hiltViewModel()

    LaunchedEffect(bookId) {
        bookDetailViewModel.loadBook(token, bookId)
    }

    val bookResource = bookDetailViewModel.book.collectAsState().value

    Column {
        when (bookResource) {
            is Resource.Success -> {
                val book = bookResource.data
                Text("Title: ${book?.title ?: "Unknown"}")
                Text("Author: ${book?.author ?: "Unknown"}")
                Text("Synopsis: ${book?.synopsis ?: "Unknown"}")
            }
            is Resource.Loading -> {
                Text("Loading...")
            }
            is Resource.Error -> {
                Text("Error: ${bookResource.message ?: "Unknown error"}")
            }
        }
    }
}
