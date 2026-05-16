package com.mooncowpines.kinostats.ui.screens.login

data class LoginScreenState(
    val username: String = "",
    val pass: String = "",
    val usernameError: String? = null,
    val passError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)
