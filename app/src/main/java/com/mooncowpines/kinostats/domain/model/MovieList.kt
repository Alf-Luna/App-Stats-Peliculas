package com.mooncowpines.kinostats.domain.model

data class MovieList(
    val id: Long,
    val name: String,
    val movieCount: Int,
    val movies: List<MovieCard>
)