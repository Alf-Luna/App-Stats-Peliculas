package com.mooncowpines.kinostats.data.repositoryImpl

import android.util.Log
import com.mooncowpines.kinostats.data.mapper.toDomain
import com.mooncowpines.kinostats.data.remote.StatsApi
import com.mooncowpines.kinostats.data.remote.dto.StatsRequestDTO
import com.mooncowpines.kinostats.domain.model.UserStats
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(
    private val api: StatsApi
) : StatsRepository {

    override suspend fun getUserStats(userId: Long?, year: Int?, month: Int?): UserStats {
        if (userId == null || year == null) throw Exception("Missing required user or year data")

        val request = StatsRequestDTO(
            userId = userId,
            month = month,
            year = year
        )

        val response = api.getStats(request)

        if (response.isSuccessful) {
            val dto = response.body() ?: throw Exception("Empty response body")
            return dto.toDomain()
        } else {
            Log.e("StatsRepo", "Error: ${response.code()}")
            throw Exception("Failed to fetch stats")
        }
    }
}