package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.MovieCardDTO
import com.mooncowpines.kinostats.data.remote.dto.MovieDTO
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.utils.parseSafely
import java.time.LocalDate

fun MovieCardDTO.toDomain(): MovieCard {
    return MovieCard(
        id = this.id,
        title = this.title ?: "Unknown",
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        releaseDate = this.yearOfRelease?.let {
            try { LocalDate.of(it, 1, 1) } catch (e: Exception) { null }
        },
        duration = this.duration,
    )
}

fun MovieDTO.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        duration = this.runtime ?: 0,
        releaseDate = parseSafely(this.releaseDate),
        originCountry = this.productionCountries ?: "Unknown",
        genres = this.genres?.split(", ") ?: emptyList(),
        director = this.director ?: "Unknown",
        actors = this.actors ?: "No information available",
        backDropUrl = this.backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" } ?: "",
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        overview = this.overview ?: "No overview available.",
        productionCompany = this.productionCompany ?: "Unknown"
    )
}