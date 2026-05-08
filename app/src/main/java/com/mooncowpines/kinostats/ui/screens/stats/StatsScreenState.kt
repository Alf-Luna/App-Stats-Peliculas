package com.mooncowpines.kinostats.ui.screens.stats

import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.model.UserStats


data class StatsScreenState(
    val isLoading: Boolean = true,
    val errorMsg: String? = null,

    val lastSeenMovie: Movie? = null,

    val selectedYear: Int? = null,
    val selectedMonth: Int? = null,

    val stats: UserStats? = null,
    val genreMaxMinutes: Int = 0
)