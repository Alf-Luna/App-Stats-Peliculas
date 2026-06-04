package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.StatsResponseDTO
import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.domain.model.UserStats

fun StatsResponseDTO.toDomain(): UserStats {
    return UserStats(
        totalMovies = this.moviesWatched,
        totalHours = this.hoursWatched,
        totalMinutes = this.minutesWatched,
        genres = this.moviesWatchedByGenre.map {
           val safeGenres = if (it.type.isNullOrBlank()) "Unknown" else it.type
            StatItem(safeGenres, it.count) },

        countries = this.moviesWatchedByCountry.map {
            val safeCountries = if (it.type.isNullOrBlank()) "Unknown" else it.type
            StatItem(safeCountries, it.count) },

        topActors = this.topActors.map {
            val safeActors = if (it.type.isNullOrBlank()) "Unknown" else it.type
            StatItem(safeActors, it.count) },

        topDirectors = this.topDirectors.map {
            val safeDirectors = if (it.type.isNullOrBlank()) "Unknown" else it.type
            StatItem(safeDirectors, it.count) },

        ratings = this.ratingsCount.map { StatItem(it.rating, it.count) },
        decades = this.moviesWatchedByDecade.map { StatItem(it.decade, it.count) }
    )
}