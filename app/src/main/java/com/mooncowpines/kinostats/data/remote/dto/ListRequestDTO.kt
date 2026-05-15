package com.mooncowpines.kinostats.data.remote.dto

data class MovieListRequest(
    val userId: Long,
    val name: String
)

data class MovieListAddRequest(
    val userId: Long,
    val movieListId: Long,
    val filmId: Long
)