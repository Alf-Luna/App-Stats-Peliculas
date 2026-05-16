package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDetailsUpdateDTO(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val pass: String // La contraseña actual para validar
)

data class UserPasswordUpdateDTO(
    @SerializedName("newPassword") val newPassword: String,
    @SerializedName("oldPassword") val oldPassword: String
)