package com.aikei.booklibrary.data.repository

import android.util.Log
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getBooks(): Resource<List<Book>> {
        return try {
            val response = apiService.listBooks()
            if (response.isSuccessful) {
                Resource.Success(data = response.body() ?: emptyList())
            } else {
                Resource.Error(message = "Error code: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown error")
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
