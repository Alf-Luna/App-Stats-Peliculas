package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieCardDTO(
    @SerializedName("id") val id: Long,
    @SerializedName(value = "name", alternate = ["title"]) val title: String?,
    @SerializedName(value = "yearOfRelease") val yearOfRelease: Int?,
    @SerializedName(value = "duration") val duration: Int?,
    @SerializedName(value = "path", alternate = ["posterPath", "poster_path"]) val posterPath: String?,
    @SerializedName(value = "director") val director : String? = "Ligma Nuts"
)