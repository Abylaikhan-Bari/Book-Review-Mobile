package com.aikei.booklibrary.ui.feature.bookdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    Column(modifier = Modifier.padding(16.dp)) {
        when (bookResource) {
            is Resource.Success -> {
                val book = bookResource.data
                Card(modifier = Modifier.padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        CardText("Title: ${book?.title ?: "Unknown"}")
                        CardText("Author: ${book?.author ?: "Unknown"}")
                        CardText("Synopsis: ${book?.synopsis ?: "Unknown"}")
                    }
                }
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

@Composable
fun CardText(text: String) {
    Card {
        Text(text = text)
    }
}