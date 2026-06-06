package com.mooncowpines.kinostats.domain.model

import java.time.LocalDate

data class MovieCard(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: LocalDate?,
    val duration: Int?,
)