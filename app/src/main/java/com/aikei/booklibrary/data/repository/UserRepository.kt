package com.aikei.booklibrary.data.repository

import com.aikei.booklibrary.data.model.RegistrationRequest
import com.aikei.booklibrary.data.model.User
import com.aikei.booklibrary.data.remote.ApiHelper
import com.aikei.booklibrary.data.remote.ApiService
import javax.inject.Inject


class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun authenticate(username: String, password: String): User {
        val response = apiService.login(username, password)  // Ensure the login method exists
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Invalid login")
        } else {
            throw Exception("Network error")
        }
    }

    suspend fun register(username: String, email: String, password1: String, password2: String): Result<Boolean> {
        return try {
            val response = apiService.register(RegistrationRequest(username, email, password1, password2))
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(RuntimeException("Failed to register: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}