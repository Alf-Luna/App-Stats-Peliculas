package com.mooncowpines.kinostats.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val releaseYear: String,
    val originCountry: String,
    val director: String,
    val cinematographer: String,
    val productionCompany: String,
    val genres: List<String>,
    val duration: Int,
    val posterUrl: Int,
    val actors: List<String>
)