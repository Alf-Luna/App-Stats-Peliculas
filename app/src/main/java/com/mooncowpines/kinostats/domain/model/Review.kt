package com.mooncowpines.kinostats.domain.model

import java.time.LocalDate

data class Review(
    val id: Long,
    val movieId: Long,
    val userId: Long?,
    val rating: Float,
    val watchDate: LocalDate?,
    val reviewText: String
)