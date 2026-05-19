package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.MovieListDTO
import com.mooncowpines.kinostats.domain.model.MovieList

fun MovieListDTO.toDomain(): MovieList {
    return MovieList(
        id = this.movieListId,
        name = this.name ?: "Unnamed List",
        movieCount = this.movieCount ?: 0,
        movies = this.movies?.map { it.toDomain() } ?: emptyList(),
        isWatchList = this.isWatchList ?: false
    )
}