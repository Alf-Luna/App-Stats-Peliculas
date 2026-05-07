package com.mooncowpines.kinostats.ui.screens.home

import com.mooncowpines.kinostats.domain.model.Movie

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val movies: List<Movie>, val lastSeenMovie: Movie) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}
