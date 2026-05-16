package com.mooncowpines.kinostats.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HomeDTO(
    @SerializedName("watchList") val watchList: List<MovieCardDTO>?,
    @SerializedName("lastSeen") val lastSeen: MovieCardDTO?,
    @SerializedName("justWatched") val justWatched: List<MovieCardDTO>?
)