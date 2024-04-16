package com.aikei.booklibrary.data.remote

import com.aikei.booklibrary.data.model.Book
import com.aikei.booklibrary.data.model.RegistrationRequest
import com.aikei.booklibrary.data.model.RegistrationResponse
import com.aikei.booklibrary.data.model.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("login/")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<User>
    @POST("dj-rest-auth/registration/")
    suspend fun register(@Body registrationData: RegistrationRequest): Response<RegistrationResponse>

    @GET("books/")
    suspend fun listBooks(): Response<List<Book>>

    @GET("books/{id}/")
    suspend fun getBookById(@Path("id") bookId: Int): Response<Book>

    @POST("books/")
    suspend fun addBook(@Body book: Book): Response<Book>
}
