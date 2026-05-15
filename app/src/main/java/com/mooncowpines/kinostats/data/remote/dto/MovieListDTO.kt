package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieListDTO(
    @SerializedName("movieListId") val movieListId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("movieCount") val movieCount: Int,
    @SerializedName("movies") val movies: List<MovieCardDTO>?
)