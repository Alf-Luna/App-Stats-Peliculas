package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.domain.model.UserStats
import com.mooncowpines.kinostats.domain.repository.StatsRepository

class FakeStatsRepository : StatsRepository {

    var shouldReturnError: Boolean = false

    var mockUserStats: UserStats? = null

    override suspend fun getUserStats(userId: Long?, year: Int?, month: Int?): UserStats? {
        if (userId == null || year == null) {
            return null
        }

        if (shouldReturnError) {
            return null
        }

        return mockUserStats
    }
}