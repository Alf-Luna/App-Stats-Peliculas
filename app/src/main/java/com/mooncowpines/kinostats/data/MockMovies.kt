package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.R
import kotlinx.coroutines.delay

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

object FakeMovieApi {

    private val mockMovieDatabase = mutableListOf(
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
            bannerResId = R.drawable.hoppers_film_poster,
            thumbnailResId = R.drawable.hoppers_film_poster
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
            bannerResId = R.drawable.spirited_away_japanese_poster,
            thumbnailResId = R.drawable.spirited_away_japanese_poster
        ),
        Movie(
            id = 3,
            title = "Soul",
            year = "2020",
            director = "Pete Docter",
            duration = "100 mins",
            tagLine = "Everybody has a soul. Joe Gardner is about to find his.",
            synopsis = "After landing the gig of a lifetime, a New York jazz pianist suddenly finds himself trapped in a strange land between Earth and the afterlife.",
            rating = 4.1f,
            membersCount = "1.2M",
            reviewsCount = "450k",
            listsCount = "210k",
            bannerResId = R.drawable.soul_2020_film_poster,
            thumbnailResId = R.drawable.soul_2020_film_poster
        ),
        Movie(
            id = 4,
            title = "Nausicaä of the Valley of the Wind",
            year = "1984",
            director = "Hayao Miyazaki",
            duration = "117 mins",
            tagLine = "There is a valley where the wind still blows...",
            synopsis = "Warrior and pacifist Princess Nausicaä desperately struggles to prevent two warring nations from destroying themselves and their dying planet.",
            rating = 4.3f,
            membersCount = "850k",
            reviewsCount = "120k",
            listsCount = "95k",
            bannerResId = R.drawable.nausicaa,
            thumbnailResId = R.drawable.nausicaa
        ),
        Movie(
            id = 5,
            title = "Chainsaw Man – The Movie: Reze Arc",
            year = "2024",
            director = "Tatsuya Yoshihara",
            duration = "90 mins",
            tagLine = "A new spark ignites.",
            synopsis = "Denji encounters a mysterious girl named Reze who shows him a new path, but her explosive secret threatens to tear his world apart.",
            rating = 4.4f,
            membersCount = "320k",
            reviewsCount = "85k",
            listsCount = "40k",
            bannerResId = R.drawable.chainsaw_man,
            thumbnailResId = R.drawable.chainsaw_man
        ),
        Movie(
            id = 6,
            title = "The Good, the Bad and the Ugly",
            year = "1966",
            director = "Sergio Leone",
            duration = "178 mins",
            tagLine = "For three men the Civil War wasn't hell. It was practice.",
            synopsis = "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.",
            rating = 4.6f,
            membersCount = "1.5M",
            reviewsCount = "600k",
            listsCount = "350k",
            bannerResId = R.drawable.the_good_the_bad_and_the_ugly_poster,
            thumbnailResId = R.drawable.the_good_the_bad_and_the_ugly_poster
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