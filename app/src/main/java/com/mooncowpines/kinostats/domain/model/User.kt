package com.mooncowpines.kinostats.domain.model

data class User(
    val id: Int,
    val userName: String,
    val email: String,
    val pass: String,
)