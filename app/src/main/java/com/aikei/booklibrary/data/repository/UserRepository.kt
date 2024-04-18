package com.aikei.booklibrary.data.repository

import com.aikei.booklibrary.data.model.RegistrationRequest
import com.aikei.booklibrary.data.model.User
import com.aikei.booklibrary.data.remote.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun authenticate(username: String, password: String): User {
        val response = apiService.login(username, password)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Invalid login: No user data")
        } else {
            throw Exception("Network error: ${response.code()} - ${response.message()}")
        }
    }

    suspend fun register(username: String, email: String, password1: String, password2: String): Boolean {
        return try {
            val response = apiService.register(RegistrationRequest(username, email, password1, password2))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
