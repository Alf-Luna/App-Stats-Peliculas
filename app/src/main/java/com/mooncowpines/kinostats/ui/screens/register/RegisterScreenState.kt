package com.mooncowpines.kinostats.ui.screens.register

data class RegisterScreenState(
    val userName: String ="",
    val email: String = "",
    val pass: String = "",
    val passCheck: String = "",
    val userNameError: String? = null,
    val emailError: String? = null,
    val passError: String? = null,
    val passCheckError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)
