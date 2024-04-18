package com.aikei.booklibrary.data.repository

import com.aikei.booklibrary.data.model.LoginResponse
import com.aikei.booklibrary.data.model.RegistrationRequest
import com.aikei.booklibrary.data.model.RegistrationResponse
import com.aikei.booklibrary.data.model.User
import com.aikei.booklibrary.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun authenticate(username: String, password: String): Response<LoginResponse> {
        return apiService.login(username, password)
    }

    suspend fun register(username: String, email: String, password1: String, password2: String): RegistrationResponse {
        val response = apiService.register(RegistrationRequest(username, email, password1, password2))
        if (response.isSuccessful) {
            return response.body() ?: throw RegistrationException("Invalid registration response")
        } else {
            throw ApiException("Network error: ${response.code()} - ${response.errorBody()?.string()}")
        }
    }
}

// Define custom exceptions for clarity
class AuthenticationException(message: String) : Exception(message)
class RegistrationException(message: String) : Exception(message)
class ApiException(message: String) : Exception(message)

