package com.mooncowpines.kinostats.ui.screens.recovery

data class RecoveryScreenState(
    val email: String = "",
    val emailError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)
