package com.mooncowpines.kinostats.ui.screens.search

import com.mooncowpines.kinostats.domain.model.Movie

data class SearchScreenState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val searchQuery: String = "",
    val results: List<Movie> = emptyList()
)