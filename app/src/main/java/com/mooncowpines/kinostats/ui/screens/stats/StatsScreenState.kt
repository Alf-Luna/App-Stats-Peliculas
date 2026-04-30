package com.mooncowpines.kinostats.ui.screens.stats

import com.mooncowpines.kinostats.R
import com.mooncowpines.kinostats.data.Movie


data class StatsScreenState(

    val isLoading: Boolean = true,

    val lastSeenMovie: Movie? = null,

    val weeklyWatchData: List<Pair<String, Float>> = listOf(
        "Jan" to 6f,
        "Feb" to 3f,
        "Mar" to 5f,
        "Apr" to 2f,
        "Jun" to 1f,
        "Jul" to 8f,
        "Aug" to 0.5f,
        "Sep" to 8f,
        "Oct" to 3f,
        "Nov" to 4f,
        "Dec" to 6f
    ),

    val todayWatchTime: String = "30 min",
    val last7DaysWatchTime: String = "8 hrs 39 min",

    val topDirectors: List<String> = listOf("Hayao Miyazaki", "Gore Verbinski", "James Cameron"),
    val topGenres: List<String> = listOf("Animation", "Action", "Drama"),

    val mostViewedFilm: String = "Spirited Away",
    val mostViewedCount: Int = 7
)