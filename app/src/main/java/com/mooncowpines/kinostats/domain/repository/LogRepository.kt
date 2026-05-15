package com.mooncowpines.kinostats.domain.repository
import com.mooncowpines.kinostats.domain.model.Log
import java.time.LocalDate

interface LogRepository {

    suspend fun getLogById(logId: Long): Log?
    suspend fun getLogsByUser(userId: Long): List<Log>?
    suspend fun getLogsForMovies(movieId: Long) :List<Log>
    suspend fun saveLog(
        newMovieId: Long,
        newUserId: Long?,
        newRating: Float,
        newWatchDate: LocalDate?,
        newReviewText: String
    ): Boolean

    suspend fun updateLog(
        logId: Long,
        newMovieId: Long,
        newUserId: Long?,
        newRating: Float,
        newWatchDate: LocalDate?,
        newReviewText: String
    ): Boolean

    suspend fun deleteLog(logId: Long): Boolean
}