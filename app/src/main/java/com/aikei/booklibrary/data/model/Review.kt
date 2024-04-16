package com.aikei.booklibrary.data.model

data class Review(
    val id: Int,
    val bookId: Int,
    val userId: Int,
    val comment: String,
    val rating: Int,
    val createdAt: String
)