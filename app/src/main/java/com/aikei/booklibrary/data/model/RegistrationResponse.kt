package com.aikei.booklibrary.data.model

data class RegistrationResponse(
    val success: Boolean,
    val message: String,
    val token: String? // Assuming the API response includes a token on successful registration
)
