package com.aikei.booklibrary.data.remote

import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.model.RegistrationRequest
import com.aikei.booklibrary.data.model.RegistrationResponse
import com.aikei.booklibrary.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login/")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<User>

    @FormUrlEncoded
    @POST("auth/login/")
    suspend fun loginWithToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Header("Authorization") token: String
    ): Response<User>

    @POST("auth/registration/")
    suspend fun register(@Body registrationData: RegistrationRequest): Response<RegistrationResponse>

    @GET("api/books/")
    suspend fun listBooks(@Header("Authorization") token: String): Response<List<Book>>

    @GET("api/books/{id}/")
    suspend fun getBookById(@Header("Authorization") token: String, @Path("id") bookId: Int): Response<Book>

    @POST("api/books/")
    suspend fun addBook(@Header("Authorization") token: String, @Body book: Book): Response<Book>
}
