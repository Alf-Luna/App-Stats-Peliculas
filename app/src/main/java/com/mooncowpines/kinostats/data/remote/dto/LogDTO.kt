package com.mooncowpines.kinostats.data.remote.dto

data class LogDTO(
    val id: Long,
    val date: String,
    val rating: Float,
    val review: String?,
    val filmId: Long,
    val userId: Long,
    val posterPath: String?,
    val releaseYear: Int?,
    val title: String
)