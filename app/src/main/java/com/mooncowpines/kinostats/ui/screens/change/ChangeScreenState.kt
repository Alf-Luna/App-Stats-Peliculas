package com.mooncowpines.kinostats.ui.screens.change

data class ChangeScreenState(
    val userName: String = "",
    val email: String = "",
    val passForProfile: String = "",

    val newPass: String = "",
    val newPassCheck: String = "",
    val passForPassword: String = "",

    val userNameError: String? = null,
    val emailError: String? = null,
    val passForProfileError: String? = null,
    val newPassError: String? = null,
    val passForPasswordError: String? = null,
    val errorMsg: String? = null,

    val isSubmittingProfile: Boolean = false,
    val isSubmittingPassword: Boolean = false,
    val profileSuccess: Boolean = false,
    val passwordSuccess: Boolean = false
)
