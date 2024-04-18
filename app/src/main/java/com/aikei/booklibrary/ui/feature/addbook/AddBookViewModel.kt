package com.aikei.booklibrary.ui.feature.addbook

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
class AddBookViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {
    private val _bookAddState = MutableStateFlow<Resource<Book>>(Resource.Loading())
    val bookAddState: StateFlow<Resource<Book>> = _bookAddState

    fun addBook(token: String, title: String, author: String, synopsis: String) {
        val book = Book(id = 0, title = title, author = author, synopsis = synopsis)
        viewModelScope.launch {
            val result = bookRepository.addBook(token, book)
            _bookAddState.emit(result)
        }
    }
}