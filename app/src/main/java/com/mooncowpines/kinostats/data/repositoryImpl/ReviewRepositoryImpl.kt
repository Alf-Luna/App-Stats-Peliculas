package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.data.remote.ReviewApi
import com.mooncowpines.kinostats.domain.repository.ReviewRepository

import com.mooncowpines.kinostats.domain.model.Review
import java.time.LocalDate
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val api : ReviewApi
) : ReviewRepository {

    override suspend fun getReviewsForMovie(movieId: Long): List<Review> {
        return emptyList()
    }
    override suspend fun saveReview(newMovieId: Long, newUserId: Long?, newRating: Float, newWatchDate: LocalDate?, newReviewText: String): Boolean {
        return true
    }
}