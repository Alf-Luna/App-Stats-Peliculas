package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.R
import kotlinx.coroutines.delay

// 1. El Modelo de Datos
data class Movie(
    val id: Int,
    val title: String,
    val year: String,
    val director: String,
    val duration: String,
    val tagLine: String,
    val synopsis: String,
    val rating: Float,
    val membersCount: String,
    val reviewsCount: String,
    val listsCount: String,
    val bannerResId: Int,
    val thumbnailResId: Int
)

// 2. La API Falsa (Simulando el Backend)
object FakeMovieApi {

    // Nuestra "Base de datos" de películas
    private val mockMovieDatabase = listOf(
        Movie(
            id = 1,
            title = "Hoppers",
            year = "2026",
            director = "Daniel Chong",
            duration = "105 mins",
            tagLine = "ACT NATURAL.",
            synopsis = "Scientists have discovered how to 'hop' human consciousness into lifelike robotic animals, allowing people to communicate with animals as animals...",
            rating = 3.8f,
            membersCount = "523k",
            reviewsCount = "228k",
            listsCount = "95k",
            bannerResId = R.drawable.kinostats_banner,
            thumbnailResId = R.drawable.kinostats_logo
        ),
        Movie(
            id = 2,
            title = "El Viaje de Chihiro",
            year = "2001",
            director = "Hayao Miyazaki",
            duration = "125 mins",
            tagLine = "NO MIRES ATRÁS.",
            synopsis = "Una niña de 10 años, Chihiro, se adentra en un mundo mágico habitado por dioses, brujas y espíritus, donde los humanos son transformados en bestias.",
            rating = 4.6f,
            membersCount = "1.2M",
            reviewsCount = "600k",
            listsCount = "450k",
            bannerResId = R.drawable.kinostats_banner,
            thumbnailResId = R.drawable.kinostats_logo
        )
    )

    suspend fun getMovies(): List<Movie> {
        delay(1500)
        return mockMovieDatabase
    }

    suspend fun getMovieById(id: Int): Movie? {
        delay(1500)
        return mockMovieDatabase.find { it.id == id }
    }
    val allMoviesSync = mockMovieDatabase
    fun getMovieByIdSync(id: Int): Movie? = mockMovieDatabase.find { it.id == id }
}