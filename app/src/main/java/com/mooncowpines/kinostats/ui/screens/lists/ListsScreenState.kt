package com.mooncowpines.kinostats.ui.screens.lists

import com.mooncowpines.kinostats.domain.model.MovieList

data class ListsScreenState(
    val isLoading: Boolean = true,
    val lists: List<MovieList> = emptyList(),
    val errorMsg: String? = null
)