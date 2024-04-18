package com.aikei.booklibrary.ui.feature.bookdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    private val _book = MutableStateFlow<Resource<Book>>(Resource.Loading())
    val book: StateFlow<Resource<Book>> = _book

    // Update the loadBook function to accept the token as well
    fun loadBook(token: String, bookId: String) {
        viewModelScope.launch {
            _book.value = Resource.Loading()
            val result = repository.getBookById(token, bookId.toInt()) // Convert bookId to Int
            _book.value = result
        }
    }
    fun updateBook(token: String, book: Book) {
        viewModelScope.launch {
            Log.d("BookDetailVM", "Updating book with ID: ${book.id}")
            val result = repository.updateBook("Bearer $token", book)
            if (result is Resource.Success) {
                Log.d("BookDetailVM", "Book updated successfully")
                _book.value = Resource.Success(result.data)
            } else if (result is Resource.Error) {
                Log.e("BookDetailVM", "Failed to update book: ${result.message}")
                _book.value = result
            }
        }
    }

    fun confirmDelete(book: Book, token: String) {
        viewModelScope.launch {
            Log.d("BookDetailVM", "Deleting book with ID: ${book.id}")
            val result = repository.deleteBook("Bearer $token", book.id)
            if (result is Resource.Success) {
                Log.d("BookDetailVM", "Book deleted successfully")
            } else if (result is Resource.Error) {
                Log.e("BookDetailVM", "Failed to delete book: ${result.message}")
            }
        }
    }

}
