package com.mooncowpines.kinostats.ui.screens.listDetail

import com.mooncowpines.kinostats.domain.model.MovieList

sealed class ListDetailState {
    object Loading : ListDetailState()
    data class Error(val message: String) : ListDetailState()
    data class Success(
        val movieList: MovieList,
        val isDeleting: Boolean = false,
        val actionMessage: String? = null
    ) : ListDetailState()
}