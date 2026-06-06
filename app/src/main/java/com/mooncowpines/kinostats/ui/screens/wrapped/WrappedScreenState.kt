package com.mooncowpines.kinostats.ui.screens.wrapped

import com.mooncowpines.kinostats.domain.model.UserStats

data class WrappedScreenState(
    val isLoading: Boolean = true,
    val stats: UserStats? = null,
    val errorMsg: String? = null
)