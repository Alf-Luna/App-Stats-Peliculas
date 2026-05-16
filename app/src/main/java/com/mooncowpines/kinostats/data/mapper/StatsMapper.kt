package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.StatsResponseDTO
import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.domain.model.UserStats

fun StatsResponseDTO.toDomain(): UserStats {
    return UserStats(
        yearlyWatchData = listOf(
            Pair("Movies", this.moviesWatched.toFloat()),
            Pair("Hours", this.hoursWatched.toFloat()),
            Pair("Minutes", this.minutesWatched.toFloat())
        ),
        genres = this.moviesWatchedByGenre.map { StatItem(it.type ?: "Unknown", it.count) },
        countries = this.moviesWatchedByCountry.map { StatItem(it.type ?: "Unknown", it.count) },
        topActors = this.topActors.map { StatItem(it.type ?: "Unknown", it.count) },
        topDirectors = this.topDirectors.map { StatItem(it.type?: "Unknown", it.count) },
        ratings = this.ratingsCount.map { StatItem(it.rating, it.count) },
        decades = this.moviesWatchedByDecade.map { StatItem(it.decade, it.count) }
    )
}