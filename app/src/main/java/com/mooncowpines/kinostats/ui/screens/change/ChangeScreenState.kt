package com.mooncowpines.kinostats.ui.screens.change

data class ChangeScreenState(
    val pass: String = "",
    val passCheck: String = "",
    val passError: String? = null,
    val passCheckError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)
