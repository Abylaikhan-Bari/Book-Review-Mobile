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
    @POST("auth/login/") // Updated path to target the login endpoint under the /auth/ base URL
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<User>

    @POST("auth/registration/") // Updated path to target the registration endpoint under the /auth/ base URL
    suspend fun register(@Body registrationData: RegistrationRequest): Response<RegistrationResponse>

    @GET("api/books/") // Updated path to target the listBooks endpoint under the /api/ base URL
    suspend fun listBooks(): Response<List<Book>>

    @GET("api/books/{id}/") // Updated path to target the getBookById endpoint under the /api/ base URL
    suspend fun getBookById(@Path("id") bookId: Int): Response<Book>

    @POST("api/books/") // Updated path to target the addBook endpoint under the /api/ base URL
    suspend fun addBook(@Body book: Book): Response<Book>
}

