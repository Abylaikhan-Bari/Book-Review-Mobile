package com.aikei.booklibrary.data.repository

import android.util.Log
import com.aikei.booklibrary.data.Resource
import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getBooks(token: String): Resource<List<Book>> {
        val response = apiService.listBooks("Bearer $token")

        return if (response.isSuccessful) {
            Resource.Success(data = response.body() ?: emptyList())
        } else {
            Resource.Error(message = "Error code: ${response.code()}")
        }
    }

    suspend fun getBookById(token: String, bookId: Int): Resource<Book> {
        return try {
            val response = apiService.getBookById(token, bookId)
            if (response.isSuccessful) {
                Resource.Success(data = response.body() ?: throw IllegalStateException("No book found with ID: $bookId"))
            } else {
                Resource.Error(message = "Failed to fetch book: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown error")
        }
    }

    suspend fun addBook(token: String, book: Book): Resource<Book> {
        val response = apiService.addBook("Bearer $token", book)
        return if (response.isSuccessful) {
            Resource.Success(data = response.body() ?: throw IllegalStateException("Failed to add the book"))
        } else {
            Resource.Error(message = "Failed to add book: ${response.errorBody()?.string()}")
        }
    }

    suspend fun deleteBook(token: String, bookId: Int): Resource<Unit> {
        return try {
            val response = apiService.deleteBook("Bearer $token", bookId)
            if (response.isSuccessful) {
                Resource.Success(Unit)  // Successfully deleted the book
            } else {
                Resource.Error(message = "Failed to delete book: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Exception during book deletion: ${e.message}")
        }
    }
}