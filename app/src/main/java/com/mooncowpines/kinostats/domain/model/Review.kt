package com.mooncowpines.kinostats.domain.model

import java.time.LocalDate

data class Review(
    val id: Int,
    val movieId: Int,
    val userId: Int?,
    val rating: Float,
    val watchDate: LocalDate?,
    val reviewText: String
)