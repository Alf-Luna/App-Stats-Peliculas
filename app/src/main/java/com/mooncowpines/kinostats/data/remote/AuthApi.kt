package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AuthApi {

    @GET("api/v1/users/login")
    suspend fun login(@Header("Authorization") authHeader: String): Response<User>
}