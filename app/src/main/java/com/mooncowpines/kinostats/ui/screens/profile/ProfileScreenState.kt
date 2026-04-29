package com.mooncowpines.kinostats.ui.screens.profile

data class ProfileScreenState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val email: String = "",
    val errorMsg: String? = null
)