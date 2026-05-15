package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.LogDTO
import com.mooncowpines.kinostats.domain.model.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LogDTO.toDomain(): Log {
    return Log(
        id = this.id,
        movieId = this.filmId,
        userId = this.userId,
        rating = this.rating,
        watchDate = try { LocalDate.parse(this.date, DateTimeFormatter.ISO_LOCAL_DATE) } catch (e: Exception) { null },
        logText = this.review ?: "",
        posterUrl = if (this.posterPath != null) "https://image.tmdb.org/t/p/w500${this.posterPath}" else "",
        movieTitle = this.title,
        releaseYear = this.releaseYear ?: 0
    )
}