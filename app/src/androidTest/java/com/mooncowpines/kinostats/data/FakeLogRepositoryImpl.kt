package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.domain.model.Log
import com.mooncowpines.kinostats.domain.repository.LogRepository
import java.time.LocalDate

class FakeLogRepository : LogRepository {

    var shouldReturnError: Boolean = false

    var mockSingleLog: Log? = null
    var mockLogsByUser: List<Log>? = emptyList()
    var mockLogsForMovies: List<Log> = emptyList()

    override suspend fun getLogById(logId: Long): Log? {
        if (shouldReturnError) return null
        return mockSingleLog
    }

    override suspend fun getLogsByUser(userId: Long): List<Log>? {
        if (shouldReturnError) return null
        return mockLogsByUser
    }

    override suspend fun getLogsForMovies(movieId: Long): List<Log> {
        if (shouldReturnError) return emptyList()
        return mockLogsForMovies
    }

    override suspend fun saveLog(
        newMovieId: Long,
        newUserId: Long?,
        newRating: Float?,
        newWatchDate: LocalDate?,
        newReviewText: String?
    ): Boolean {
        if (newUserId == null) return false

        return !shouldReturnError
    }

    override suspend fun updateLog(
        logId: Long,
        newMovieId: Long,
        newUserId: Long?,
        newRating: Float?,
        newWatchDate: LocalDate?,
        newReviewText: String?
    ): Boolean {
        if (newUserId == null) return false

        return !shouldReturnError
    }

    override suspend fun deleteLog(logId: Long): Boolean {
        return !shouldReturnError
    }
}