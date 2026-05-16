package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.data.remote.dto.HomeDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {
    @GET("api/v1/home/{id}")
    suspend fun getHomeData(@Path("id") userId: Long): Response<HomeDTO>
}