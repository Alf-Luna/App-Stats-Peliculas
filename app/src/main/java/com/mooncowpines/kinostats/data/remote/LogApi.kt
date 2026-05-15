package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.data.remote.dto.LogDTO
import com.mooncowpines.kinostats.data.remote.dto.LogRequestDTO
import com.mooncowpines.kinostats.domain.model.Log
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LogApi {
    @GET("logs")
    suspend fun getLogs(): List<Log>

    @GET("api/v1/logs/all/{id}")
    suspend fun getLogsByUserId(@Path("id") userId: Long): Response<List<LogDTO>>

    @GET("api/v1/logs/log/{id}")
    suspend fun getLogById(@Path("id") id: Long): Response<LogDTO>
    @POST("api/v1/logs/add")
    suspend fun saveLog(
        @Body request: LogRequestDTO
    ): Response<Void>

    @PUT("api/v1/logs/{id}")
    suspend fun updateLog(@Path("id") id: Long, @Body request: LogRequestDTO): Response<Void>

    @DELETE("api/v1/logs/{id}")
    suspend fun deleteLog(@Path("id") id: Long): Response<Void>
}