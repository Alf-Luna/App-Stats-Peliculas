package com.mooncowpines.kinostats.ui.screens.home

import com.mooncowpines.kinostats.domain.model.MovieCard

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(
        val watchlistMovies: List<MovieCard>,
        val justWatchedMovies: List<MovieCard>,
        val lastSeenMovie: MovieCard?
    ) : HomeScreenState()

    data class Error(val message: String) : HomeScreenState()
}
