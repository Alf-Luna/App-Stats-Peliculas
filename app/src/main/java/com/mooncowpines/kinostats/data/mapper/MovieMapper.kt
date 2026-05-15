package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.MovieCardDTO
import com.mooncowpines.kinostats.data.remote.dto.MovieDTO
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.model.MovieCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun MovieCardDTO.toDomain(): MovieCard {
    return MovieCard(
        id = this.id,
        title = this.title ?: "Unknown",
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        releaseDate = null
    )
}

fun MovieDTO.toCard(): MovieCard {
    return MovieCard(
        id = this.id,
        title = this.title,
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        releaseDate = this.releaseDate ?: "Unknown release date"
    )
}

fun MovieDTO.toDomain(): Movie {

    val formattedDate = try {
        if (!this.releaseDate.isNullOrBlank()) {
            val date = LocalDate.parse(this.releaseDate)
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            date.format(formatter)
        } else {
            "Unknown release date"
        }
    } catch (e: Exception) {
        this.releaseDate ?: "Unknown release date"
    }

    return Movie(
        id = this.id,
        title = this.title,
        duration = this.runtime ?: 0,
        releaseDate = formattedDate,
        originCountry = this.productionCountries ?: "Unknown",
        genres = this.genres?.split(", ") ?: emptyList(),
        director = this.director ?: "Unknown",
        backDropUrl = this.backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" } ?: "",
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        overview = this.overview ?: "No overview available."
    )
}