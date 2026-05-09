package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.data.remote.StatsApi
import com.mooncowpines.kinostats.domain.model.UserStats
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import javax.inject.Inject


class StatsRepositoryImpl @Inject constructor(
    private val api : StatsApi
) : StatsRepository {

    override suspend fun getUserStats(userId: Long, year: Int?, month: Int?): UserStats {
        TODO("Not developed yet")
    }
}