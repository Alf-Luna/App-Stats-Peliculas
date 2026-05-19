package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordDTO(
    @SerializedName("email") val email: String,
    @SerializedName("code") val code: String,
    @SerializedName("newPassword") val newPassword: String
)