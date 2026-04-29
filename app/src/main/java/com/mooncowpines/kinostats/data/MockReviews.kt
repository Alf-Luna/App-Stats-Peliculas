package com.mooncowpines.kinostats.data

import android.util.Log
import kotlinx.coroutines.delay
import org.w3c.dom.Text
import java.time.LocalDate

data class Review(
    val id: Int,
    val movieId: Int,
    val userId: Int?,
    val rating: Float,
    val watchDate: LocalDate?,
    val reviewText: String
)

object FakeReviewApi {

    private val mockReviews = mutableListOf(
        Review(
            id = 1,
            movieId = 1,
            userId = 1,
            rating = 4.5f,
            watchDate = LocalDate.of(2026, 4, 15),
            reviewText = "It was good"
        ),
        Review(
            id = 2,
            movieId = 2,
            userId = 1,
            rating = 5.0f,
            watchDate = LocalDate.of(2026, 3, 10),
            reviewText = "It was good"
        ),
        Review(
            id = 3,
            movieId = 1,
            userId = 2,
            rating = 3.5f,
            watchDate = LocalDate.of(2026, 2, 5),
            reviewText = "It was good"
        )
    )

    suspend fun getReviewsForMovie(movieId: Int): List<Review> {
        delay(1500)
        return FakeReviewApi.mockReviews.filter { it.movieId == movieId }
    }

    suspend fun saveReview(
        newMovieId: Int,
        newUserId: Int?,
        newRating: Float,
        newWatchDate: LocalDate?,
        newReviewText: String) : Boolean {

        delay(2000)
        val newId = if (mockReviews.isEmpty()) 1 else mockReviews.maxOf { it.id } + 1

        val newReview = Review(
            id = newId,
            movieId = newMovieId,
            userId = newUserId,
            rating = newRating,
            watchDate = newWatchDate,
            reviewText = newReviewText)
        mockReviews.add(newReview)

        Log.d("Review saved", "El usuario logueado es: ${newReview}")

        return true
    }
}

