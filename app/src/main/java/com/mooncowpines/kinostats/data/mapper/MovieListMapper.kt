package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.MovieListDTO
import com.mooncowpines.kinostats.domain.model.MovieList

fun MovieListDTO.toDomain(): MovieList {
    return MovieList(
        id = this.movieListId,
        name = this.name,
        movieCount = this.movieCount,
        movies = this.movies?.map { it.toDomain() } ?: emptyList()
    )
}