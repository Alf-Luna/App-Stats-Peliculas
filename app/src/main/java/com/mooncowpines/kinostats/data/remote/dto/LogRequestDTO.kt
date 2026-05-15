package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LogRequestDTO(
    @SerializedName("date") val date: String,
    @SerializedName("review") val review: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("filmId") val filmId: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("firstWatch") val firstWatch: Boolean
)