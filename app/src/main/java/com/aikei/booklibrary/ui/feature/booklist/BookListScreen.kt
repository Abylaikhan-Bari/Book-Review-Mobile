package com.aikei.booklibrary.ui.feature.booklist

import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.aikei.booklibrary.ui.common.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                        onItemClick = { book ->
                            Log.d("BookListScreen", "Clicked on book: ${book.id}")
                            navController.navigate("bookDetail/$token/${book.id}") },
                    )
                    is Resource.Error -> Text("Error: ${books.message}")
                }

            }
        }
    }
}

@Composable
fun BookList(items: List<Book>, onItemClick: (Book) -> Unit) {
    LazyColumn {
        items(items, key = { it.id }) { book ->
            BookItem(book, onItemClick)
        }
    }
}

@Composable
fun BookItem(book: Book, onItemClick: (Book) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        coroutineScope.launch {
                            delay(500) // Adjust the delay duration as needed
                            showDialog = true
                        }
                    }
                )
            }
            .clickable { onItemClick(book) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text(book.author, style = MaterialTheme.typography.bodyMedium)
        }
    }

}
