package com.mooncowpines.kinostats.domain.model

import java.time.LocalDate

data class Log(
    val id: Long,
    val movieId: Long,
    val userId: Long?,
    val rating: Float,
    val watchDate: LocalDate?,
    val logText: String,
    val posterUrl: String,
    val movieTitle: String,
    val releaseYear: Int
)