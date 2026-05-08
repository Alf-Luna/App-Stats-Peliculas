package com.mooncowpines.kinostats.ui.screens.movieDetail

import com.mooncowpines.kinostats.domain.model.Movie

sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(val movie: Movie) : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
}