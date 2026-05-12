package com.mooncowpines.kinostats.domain.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("movieListId") val id: Long,
    val name: String,
    val movieCount: Int,
    val movies: List<MovieCard>
)