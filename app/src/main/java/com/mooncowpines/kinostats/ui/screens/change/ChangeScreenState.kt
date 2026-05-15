package com.mooncowpines.kinostats.ui.screens.change

data class ChangeScreenState(
    val userName: String = "",
    val email: String = "",
    val currentPass: String = "",
    val newPass: String = "",
    val newPassCheck: String = "",
    val userNameError: String? = null,
    val emailError: String? = null,
    val currentPassError: String? = null,
    val newPassError: String? = null,
    val passCheckError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)
