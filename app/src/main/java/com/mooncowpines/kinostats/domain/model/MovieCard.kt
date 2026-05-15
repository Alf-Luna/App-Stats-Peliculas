package com.mooncowpines.kinostats.domain.model

data class MovieCard(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: String? = null,
    val duration: Int? = null,
    val director: String? = null
)