package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.domain.model.Home
import com.mooncowpines.kinostats.domain.repository.HomeRepository

class FakeHomeRepository : HomeRepository {

    var shouldReturnError: Boolean = false

    var mockHomeData: Home? = Home(
        watchlist = emptyList(),
        justWatched = emptyList(),
        lastSeen = null
    )

    override suspend fun getHomeData(userId: Long): Home? {
        if (shouldReturnError) {
            return null
        }

        return mockHomeData
    }
}