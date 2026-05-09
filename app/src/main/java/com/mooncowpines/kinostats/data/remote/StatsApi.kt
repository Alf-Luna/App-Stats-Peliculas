package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.domain.model.UserStats
import retrofit2.http.GET
import retrofit2.http.Path

interface StatsApi {
    @GET("stats")
    suspend fun getStats(): List<UserStats>

    @GET("stats/{id}")
    suspend fun getStatsById(@Path("id") id: Int): UserStats
}