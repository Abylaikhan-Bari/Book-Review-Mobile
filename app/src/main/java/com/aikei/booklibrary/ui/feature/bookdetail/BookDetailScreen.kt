package com.aikei.booklibrary.ui.feature.bookdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BookDetailScreen(bookId: Int, viewModel: BookDetailViewModel = viewModel()) {
    // Assume loadBook() is triggered externally or here directly
    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
    }

    val book = viewModel.book.collectAsState().value

    Column {
        Text("Title: ${book.title}")
        Text("Author: ${book.author}")
        Text("Synopsis: ${book.synopsis}")
    }
}