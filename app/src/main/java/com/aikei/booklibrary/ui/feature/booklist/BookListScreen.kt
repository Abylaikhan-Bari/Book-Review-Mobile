package com.aikei.booklibrary.ui.feature.booklist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.aikei.booklibrary.ui.common.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(navController: NavController, token: String) {
    val bookListViewModel: BookListViewModel = hiltViewModel()
    val bookResource by bookListViewModel.books.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = bookResource is Resource.Loading)
    LaunchedEffect(key1 = true) {
        bookListViewModel.loadBooks(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book List") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addBook/$token") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Book")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { bookListViewModel.loadBooks(token) }
            ) {
                when (val books = bookResource) {
                    is Resource.Loading -> Text("Loading...")
                    is Resource.Success -> BookList(
                        items = books.data ?: listOf(),
                        onItemClick = { book -> navController.navigate("bookDetail/$token/${book.id}") },
                        onDeleteConfirm = { /* Handle delete confirmation here */ }
                    )
                    is Resource.Error -> Text("Error: ${books.message}")
                }

            }
        }
    }
}

@Composable
fun BookList(items: List<Book>, onItemClick: (Book) -> Unit, onDeleteConfirm: () -> Unit) {
    LazyColumn {
        items(items, key = { it.id }) { book ->
            BookItem(book, onItemClick, onDeleteConfirm)
        }
    }
}



@Composable
fun BookItem(book: Book, onItemClick: (Book) -> Unit, onDeleteConfirm: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Book") },
            text = { Text("Are you sure you want to delete '${book.title}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteConfirm() // This is called when the user confirms deletion
                        showDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = { onItemClick(book) }) // Pass the book to onItemClick
            .pointerInput(Unit) { // This listens to pointer input and reacts to gestures
                detectTapGestures(
                    onLongPress = { showDialog = true } // This will show the dialog on long press
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Title: ${book.title}", style = MaterialTheme.typography.titleMedium)
            Text("Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

