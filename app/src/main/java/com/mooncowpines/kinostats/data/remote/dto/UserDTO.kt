package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("username") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val pass: String,
)