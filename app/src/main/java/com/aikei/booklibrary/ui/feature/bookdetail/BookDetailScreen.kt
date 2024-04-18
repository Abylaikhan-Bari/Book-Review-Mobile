package com.aikei.booklibrary.ui.feature.bookdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import com.aikei.booklibrary.ui.theme.Custom
import kotlinx.coroutines.launch

@Composable
fun BookDetailScreen(navController: NavController, token: String, bookId: String) {
    val bookDetailViewModel: BookDetailViewModel = hiltViewModel()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(bookId) {
        bookDetailViewModel.loadBook(token, bookId)
    }

    val bookResource = bookDetailViewModel.book.collectAsState().value

    Column(modifier = Modifier.padding(16.dp)) {
        when (bookResource) {
            is Resource.Loading -> Text("Loading...")
            is Resource.Success -> {
                bookResource.data?.let { book ->
                    BookDetailCard(book)
                    Button(
                        onClick = { showUpdateDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Custom),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Update Book")
                    }
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Custom),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Delete Book")
                    }
                }
            }
            is Resource.Error -> Text("Error: ${bookResource.message ?: "Unknown error"}")
        }

        if (showDeleteDialog && bookResource is Resource.Success && bookResource.data != null) {
            DeleteConfirmationDialog(
                navController = navController,
                book = bookResource.data,
                token = token,
                onDismiss = { showDeleteDialog = false },
                viewModel = bookDetailViewModel
            )
        }

        if (showUpdateDialog && bookResource is Resource.Success && bookResource.data != null) {
            UpdateBookDialog(
                book = bookResource.data,
                onDismiss = { showUpdateDialog = false },
                viewModel = bookDetailViewModel,
                token = token
            )
        }

        Button(
            onClick = { navController.navigate("bookList/$token") },
            colors = ButtonDefaults.buttonColors(containerColor = Custom),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Book List Screen")
        }
    }
}

@Composable
fun BookDetailCard(book: Book) {
    Card(modifier = Modifier.padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Title: ${book.title}")
            Text("Author: ${book.author}")
            Text("Synopsis: ${book.synopsis}")
        }
    }
}

@Composable
fun UpdateBookDialog(book: Book, onDismiss: () -> Unit, viewModel: BookDetailViewModel, token: String) {
    var title by remember { mutableStateOf(book.title) }
    var author by remember { mutableStateOf(book.author) }
    var synopsis by remember { mutableStateOf(book.synopsis) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Book") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(value = author, onValueChange = { author = it }, label = { Text("Author") })
                TextField(value = synopsis, onValueChange = { synopsis = it }, label = { Text("Synopsis") })
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val updatedBook = book.copy(title = title, author = author, synopsis = synopsis)
                    viewModel.updateBook(token, updatedBook)
                    onDismiss()
                }
            ) {
                Text("Save Changes")
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