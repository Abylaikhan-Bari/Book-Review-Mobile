package com.aikei.booklibrary.ui.feature.bookdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import kotlinx.coroutines.launch

@Composable
fun BookDetailScreen(navController: NavController, token: String, bookId: String) {
    val bookDetailViewModel: BookDetailViewModel = hiltViewModel()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(bookId) {
        bookDetailViewModel.loadBook(token, bookId)
    }

    val bookResource = bookDetailViewModel.book.collectAsState().value

    Column(modifier = Modifier.padding(16.dp)) {
        when (val bookResult = bookResource) {
            is Resource.Success -> {
                val book = bookResult.data
                Card(modifier = Modifier.padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Title: ${book?.title ?: "Unknown"}")
                        Text("Author: ${book?.author ?: "Unknown"}")
                        Text("Synopsis: ${book?.synopsis ?: "Unknown"}")
                    }
                }

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete Book")
                }
            }
            is Resource.Loading -> {
                Text("Loading...")
            }
            is Resource.Error -> {
                Text("Error: ${bookResult.message ?: "Unknown error"}")
            }
        }

        if (showDialog && bookResource is Resource.Success && bookResource.data != null) {
            DeleteConfirmationDialog(
                navController = navController,
                book = bookResource.data!!,
                token = token,
                onDismiss = { showDialog = false },
                viewModel = bookDetailViewModel
            )
        }

        Button(
            onClick = { navController.navigate("bookList/$token") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Book List Screen")
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    navController: NavController,
    book: Book,
    token: String,
    onDismiss: () -> Unit,
    viewModel: BookDetailViewModel
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Book") },
        text = { Text("Are you sure you want to delete '${book.title}'?") },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.confirmDelete(book, token)
                    navController.navigate("bookList/$token")
                    onDismiss()
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun CardText(text: String) {
    Card {
        Text(text = text)
    }
}