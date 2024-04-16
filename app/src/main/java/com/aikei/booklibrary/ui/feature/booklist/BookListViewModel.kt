package com.aikei.booklibrary.ui.feature.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    private val _books = MutableStateFlow<Resource<List<Book>>>(Resource.Loading())
    val books: StateFlow<Resource<List<Book>>> = _books.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _books.emit(Resource.Loading())
            try {
                val loadedBooks = repository.getBooks()
                _books.emit(Resource.Success(data = loadedBooks))
            } catch (e: Exception) {
                _books.emit(Resource.Error(message = e.message ?: "Unknown error"))
            }
        }
    }
}
