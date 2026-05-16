package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StatsRequestDTO(
    val userId: Long,
    val month: Int?,
    val year: Int
)

data class StatsResponseDTO(
    val moviesWatched: Int,
    val minutesWatched: Int,
    val hoursWatched: Int,
    val moviesWatchedByGenre: List<TypeWatchesDTO>,
    val moviesWatchedByCountry: List<TypeWatchesDTO>,
    val topDirectors: List<TypeWatchesDTO>,
    val topActors: List<TypeWatchesDTO>,
    val ratingsCount: List<RatingsCountDTO>,
    val moviesWatchedByDecade: List<DecadeWatchesDTO>
)

data class TypeWatchesDTO(
    @SerializedName("name")val type: String,
    @SerializedName("watches")val count: Int)
data class RatingsCountDTO(
    @SerializedName("rating")val rating: Float,
    @SerializedName("amount")val count: Int)
data class DecadeWatchesDTO(
    @SerializedName("decades")val decade: Int,
    @SerializedName("watches")val count: Int)