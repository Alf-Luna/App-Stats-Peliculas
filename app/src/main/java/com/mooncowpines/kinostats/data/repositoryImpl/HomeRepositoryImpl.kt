package com.mooncowpines.kinostats.data.repository

import android.util.Log
import com.mooncowpines.kinostats.data.mapper.toDomain
import com.mooncowpines.kinostats.data.remote.HomeApi
import com.mooncowpines.kinostats.domain.model.Home
import com.mooncowpines.kinostats.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : HomeRepository {

    override suspend fun getHomeData(userId: Long): Home? {
        return try {
            val response = api.getHomeData(userId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val watchlist = body.watchList?.map { it.toDomain() } ?: emptyList()
                    val justWatched = body.justWatched?.map { it.toDomain() } ?: emptyList()
                    val lastSeen = body.lastSeen?.toDomain()

                    Home(
                        watchlist = watchlist,
                        justWatched = justWatched,
                        lastSeen = lastSeen
                    )
                } else {
                    null
                }
            } else {
                Log.e("HomeRepository", "Server error: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("HomeRepository", "Network error: ${e.message}")
            null
        }
    }
}