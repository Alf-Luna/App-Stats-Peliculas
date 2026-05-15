package com.mooncowpines.kinostats.ui.screens.logDetail

import java.time.LocalDate
import com.mooncowpines.kinostats.domain.model.Movie

data class LogDetailScreenState(
    val isLoadingMovie: Boolean = true,
    val movie: Movie? = null,

    val rating: Float = 0f,
    val watchDate: LocalDate? = null,
    val formattedWatchDate: String = "Tap to select a date...",
    val showCalendar: Boolean = false,
    val logText: String = "",
    val ratingError: String? = null,
    val watchDateError: String? = null,
    val logTextError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)

