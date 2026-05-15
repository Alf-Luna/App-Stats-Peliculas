package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.data.mapper.toDomain
import com.mooncowpines.kinostats.data.remote.LogApi
import com.mooncowpines.kinostats.domain.model.Log
import com.mooncowpines.kinostats.data.remote.dto.LogRequestDTO
import com.mooncowpines.kinostats.domain.repository.LogRepository

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class LogRepositoryImpl @Inject constructor(
    private val api : LogApi
) : LogRepository {

    override suspend fun getLogById(logId: Long): Log? {
        return try {
            val response = api.getLogById(logId)
            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else null
        } catch (e: Exception) { null }
    }

    override suspend fun getLogsByUser(userId: Long): List<com.mooncowpines.kinostats.domain.model.Log>? {
        return try {
            val response = api.getLogsByUserId(userId)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getLogsForMovies(movieId: Long): List<com.mooncowpines.kinostats.domain.model.Log> {
        return emptyList()
    }

    override suspend fun saveLog(
        newMovieId: Long,
        newUserId: Long?,
        newRating: Float,
        newWatchDate: LocalDate?,
        newReviewText: String
    ): Boolean {
        if (newUserId == null) {
            return false
        }

        val dateString = newWatchDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

        val requestBody = LogRequestDTO(
            date = dateString,
            review = newReviewText,
            rating = newRating,
            filmId = newMovieId,
            userId = newUserId,
            firstWatch = true
        )

        return try {
            api.saveLog(requestBody).isSuccessful
        } catch (e: Exception) {
            false
            }
    }

    override suspend fun updateLog(
        logId: Long,
        newMovieId: Long,
        newUserId: Long?,
        newRating: Float,
        newWatchDate: LocalDate?,
        newReviewText: String
    ): Boolean {
        if (newUserId == null) {
            return false
        }

        val dateString = newWatchDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)
            ?: LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

        val requestBody = LogRequestDTO(
            date = dateString,
            review = newReviewText,
            rating = newRating,
            filmId = newMovieId,
            userId = newUserId,
            firstWatch = true
        )

        return try {
            api.updateLog(logId, requestBody).isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteLog(logId: Long): Boolean {
        return try {
            val response = api.deleteLog(logId)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

}

