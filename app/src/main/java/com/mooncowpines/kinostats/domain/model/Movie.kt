package com.mooncowpines.kinostats.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val duration: Int,
    val releaseDate: String,
    val originCountry: String,
    val genres: List<String>,
    val director: String,
    val backDropUrl: String,
    val posterUrl: String,
    val overview: String,

    val actors: String,
    val productionCompany: String,
)