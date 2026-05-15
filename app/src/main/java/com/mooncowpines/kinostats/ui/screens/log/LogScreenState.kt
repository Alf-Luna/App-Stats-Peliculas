package com.mooncowpines.kinostats.ui.screens.log


import com.mooncowpines.kinostats.domain.model.Log

data class LogScreenState(
    val isLoading: Boolean = true,
    val logs: List<Log> = emptyList(),
    val errorMsg: String? = null
)