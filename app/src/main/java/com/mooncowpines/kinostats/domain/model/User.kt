package com.mooncowpines.kinostats.domain.model

data class User(
    val id: Long? = null,
    val userName: String,
    val email: String,
    val pass: String,
)