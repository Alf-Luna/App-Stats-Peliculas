package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.UserStats

interface StatsRepository {

    suspend fun getUserStats(userId: Long, year: Int?, month: Int?): UserStats
}