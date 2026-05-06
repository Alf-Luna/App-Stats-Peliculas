package com.mooncowpines.kinostats.ui.screens.change

data class ChangeScreenState(
    val currentPass: String = "",
    val newPass: String = "",
    val newPassCheck: String = "",
    val currentPassError: String? = null,
    val newPassError: String? = null,
    val passCheckError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)
