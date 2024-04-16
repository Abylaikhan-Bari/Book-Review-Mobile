package com.aikei.booklibrary.ui.feature.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    private val _book = MutableStateFlow(Book(0, "", "", "", ""))
    val book: StateFlow<Book> = _book

    fun loadBook(bookId: Int) {
        viewModelScope.launch {
            _book.value = repository.getBookById(bookId)  // Ensure this function exists in BookRepository
        }
    }
}
