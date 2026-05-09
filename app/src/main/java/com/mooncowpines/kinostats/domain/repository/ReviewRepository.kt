package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.Review
import java.time.LocalDate

interface ReviewRepository {
    suspend fun getReviewsForMovie(movieId: Long) :List<Review>
    suspend fun saveReview(
        newMovieId: Long,
        newUserId: Long?,
        newRating: Float,
        newWatchDate: LocalDate?,
        newReviewText: String
    ): Boolean
}