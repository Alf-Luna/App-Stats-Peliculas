package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.data.remote.KinoStatsApi
import com.mooncowpines.kinostats.domain.repository.ReviewRepository

import com.mooncowpines.kinostats.domain.model.Review
import java.time.LocalDate
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val api : KinoStatsApi
) : ReviewRepository {

    override suspend fun getReviewsForMovie(movieId: Int): List<Review> {
        return emptyList()
    }
    override suspend fun saveReview(newMovieId: Int, newUserId: Int?, newRating: Float, newWatchDate: LocalDate?, newReviewText: String): Boolean {
        return true
    }
}