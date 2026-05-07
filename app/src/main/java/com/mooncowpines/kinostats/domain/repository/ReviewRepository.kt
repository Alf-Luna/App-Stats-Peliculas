package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.Review
import java.time.LocalDate

interface ReviewRepository {
    suspend fun getReviewsForMovie(movieId: Int) :List<Review>
    suspend fun saveReview(
        newMovieId: Int,
        newUserId: Int?,
        newRating: Float,
        newWatchDate: LocalDate?,
        newReviewText: String
    ): Boolean
}