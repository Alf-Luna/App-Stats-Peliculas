package com.mooncowpines.kinostats.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,

    @SerializedName("username")
    val userName: String,

    val email: String,

    @SerializedName("password")
    val pass: String,
)