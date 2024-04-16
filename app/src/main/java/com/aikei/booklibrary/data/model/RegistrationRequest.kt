package com.aikei.booklibrary.data.model

data class RegistrationRequest(
    val username: String,
    val email: String,
    val password1: String,
    val password2: String
)
