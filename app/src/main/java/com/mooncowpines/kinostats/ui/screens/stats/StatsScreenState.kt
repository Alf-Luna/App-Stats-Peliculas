package com.mooncowpines.kinostats.ui.screens.stats

import com.mooncowpines.kinostats.domain.model.UserStats
import java.time.LocalDate


data class StatsScreenState(
    val isLoading: Boolean = true,
    val errorMsg: String? = null,

    val selectedYear: Int = LocalDate.now().year,
    val selectedMonth: Int? = LocalDate.now().monthValue,

    val stats: UserStats? = null,
    val genreMaxMovieCount: Int = 0
)