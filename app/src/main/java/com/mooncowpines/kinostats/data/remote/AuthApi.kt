package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApi {

    @GET("api/v1/users/login")
    suspend fun login(@Header("Authorization") authHeader: String): Response<User>

    @GET("api/v1/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<User>

    @POST("api/v1/users/add")
    suspend fun register(@Body user: User): Response<User>

    @PUT("api/v1/users/{id}")
    suspend fun updateUser(@Path("id") id: Long?, @Body user: User): Response<User>
}