package com.aikei.booklibrary.data.remote

import com.aikei.booklibrary.data.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//class ApiHelper @Inject constructor(private val apiService: ApiService) {
//    private val scope = CoroutineScope(Dispatchers.IO)
//
//    fun listBooks(callback: (Result<List<Book>>) -> Unit) {
//        scope.launch {
//            try {
//                val response = apiService.listBooks()
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        callback(Result.success(it))
//                    } ?: callback(Result.failure(RuntimeException("No books found")))
//                } else {
//                    callback(Result.failure(RuntimeException("Failed to fetch books")))
//                }
//            } catch (e: Exception) {
//                callback(Result.failure(e))
//            }
//        }
//    }
//
//    fun addBook(book: Book, callback: (Result<Book>) -> Unit) {
//        scope.launch {
//            try {
//                val response = apiService.addBook(book)
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        callback(Result.success(it))
//                    } ?: callback(Result.failure(RuntimeException("Failed to add book")))
//                } else {
//                    callback(Result.failure(RuntimeException("Failed to add book")))
//                }
//            } catch (e: Exception) {
//                callback(Result.failure(e))
//            }
//        }
//    }
//}
