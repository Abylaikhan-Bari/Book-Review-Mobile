package com.aikei.booklibrary.data.model

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val token: String? // Make sure this is the same as what your backend returns
)
