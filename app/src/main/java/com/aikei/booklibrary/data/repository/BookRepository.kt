package com.aikei.booklibrary.data.repository

import android.util.Log
import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getBooks(): List<Book> {
        val response = apiService.listBooks()
        Log.d("BookRepository", "Fetching books: ${response.code()}")
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            Log.e("BookRepository", "Error fetching books: ${response.errorBody()?.string()}")
            throw Exception("Error fetching books: ${response.errorBody()?.string()}")
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
