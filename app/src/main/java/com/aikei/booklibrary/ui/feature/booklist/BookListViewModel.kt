package com.aikei.booklibrary.ui.feature.booklist

import android.util.Log
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

    fun loadBooks(token: String) {
        viewModelScope.launch {
            try {
                Log.d("BookListVM", "Loading books with token: $token")
                _books.emit(Resource.Loading())
                val result = repository.getBooks("Bearer $token")
                _books.emit(result)
                if (result is Resource.Success) {
                    Log.d("BookListVM", "Books loaded: ${result.data.size}")
                } else if (result is Resource.Error) {
                    Log.d("BookListVM", "Error loading books: ${result.message}")
                }
            } catch (e: Exception) {
                Log.e("BookListVM", "Failed to load books", e)
                _books.emit(Resource.Error("Failed to load books: ${e.message}"))
            }
        }
    }

}

