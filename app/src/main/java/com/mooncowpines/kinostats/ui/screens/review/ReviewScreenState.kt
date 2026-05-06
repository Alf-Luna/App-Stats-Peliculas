package com.mooncowpines.kinostats.ui.screens.review

import java.time.LocalDate

data class ReviewScreenState(
    val rating: Float = 0f,
    val watchDate: LocalDate? = null,
    val showCalendar: Boolean = false,
    val reviewText: String = "",
    val ratingError: String? = null,
    val watchDateError: String? = null,
    val reviewTextError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)

