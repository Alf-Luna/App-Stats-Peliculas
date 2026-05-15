package com.mooncowpines.kinostats.ui.screens.movieDetail

import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.model.MovieList

sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(
        val movie: Movie,
        val isFabMenuExpanded: Boolean = false,
        val isListSheetOpen: Boolean = false,
        val userLists: List<MovieList> = emptyList(),
        val isFetchingLists: Boolean = false,
        val listActionMessage: String? = null
    ) : MovieDetailState()

    data class Error(val message: String) : MovieDetailState()
}