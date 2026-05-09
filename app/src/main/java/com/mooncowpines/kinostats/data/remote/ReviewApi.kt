package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.model.Review
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewApi {
    @GET("review")
    suspend fun getReviews(): List<Review>

    @GET("review/{id}")
    suspend fun getReviewById(@Path("id") id: Int): Review
}