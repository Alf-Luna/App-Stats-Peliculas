package com.mooncowpines.kinostats.domain.model

data class Home(
    val watchlist: List<MovieCard>,
    val justWatched: List<MovieCard>,
    val lastSeen: MovieCard?
)