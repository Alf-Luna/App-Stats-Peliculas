package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("releaseDate") val releaseDate: String?,
    @SerializedName("productionCountries") val productionCountries: String?,
    @SerializedName("genres") val genres: String?,
    @SerializedName("backdropPath") val backdropPath: String?,
    @SerializedName("posterPath") val posterPath: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("director") val director: String?
)