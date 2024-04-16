package com.aikei.booklibrary.data.repository

import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getBooks(): List<Book> {
        val response = apiService.listBooks()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch books: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getBookById(bookId: Int): Book {
        val response = apiService.getBookById(bookId)
        if (response.isSuccessful) {
            return response.body() ?: throw IllegalStateException("No book found with ID: $bookId")
        } else {
            throw Exception("Failed to fetch book: ${response.errorBody()?.string()}")
        }
    }
}
