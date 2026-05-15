package com.mooncowpines.kinostats.data.remote.dto

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

data class TypeWatchesDTO(val type: String, val count: Int)
data class RatingsCountDTO(val rating: Float, val count: Int)
data class DecadeWatchesDTO(val decade: Int, val count: Int)