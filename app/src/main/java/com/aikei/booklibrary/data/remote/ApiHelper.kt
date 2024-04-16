package com.aikei.booklibrary.data.remote

import com.aikei.booklibrary.data.model.Book
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    fun listBooks() = apiService.listBooks()
    fun addBook(book: Book) = apiService.addBook(book)
    // Add more helper methods as needed
}