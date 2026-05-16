package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.data.remote.dto.LoginDTO
import com.mooncowpines.kinostats.data.remote.dto.UserDTO
import com.mooncowpines.kinostats.data.remote.dto.UserDetailsUpdateDTO
import com.mooncowpines.kinostats.data.remote.dto.UserPasswordUpdateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApi {

    @GET("api/v1/auth/login")
    suspend fun login(@Header("Authorization") authHeader: String): Response<LoginDTO>

    @GET("api/v1/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<UserDTO>

    @POST("api/v1/users/add")
    suspend fun register(@Body user: UserDTO): Response<UserDTO>

    @PUT("api/v1/users/{id}/details")
    suspend fun updateUserDetails(@Path("id") id: Long, @Body details: UserDetailsUpdateDTO): Response<Unit>

    @PUT("api/v1/users/{id}/password")
    suspend fun updateUserPassword(@Path("id") id: Long, @Body passwords: UserPasswordUpdateDTO): Response<Unit>
}