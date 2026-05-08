package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.R
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import kotlinx.coroutines.delay

class MockMovieRepositoryImpl : MovieRepository {
    private val mockMovieDatabase = mutableListOf(
        Movie(
            id = 1,
            title = "Hoppers",
            releaseYear = "2026",
            originCountry = "United States",
            director = "Daniel Chong",
            cinematographer = "Unknown",
            productionCompany = "Pixar Animation Studios",
            genres = listOf("Animation", "Adventure", "Comedy"),
            duration = 105,
            posterUrl = R.drawable.hoppers_film_poster,
            actors = listOf("Jon Hamm", "Piper Curda", "Bobby Moynihan")
        ),
        Movie(
            id = 2,
            title = "El Viaje de Chihiro",
            releaseYear = "2001",
            originCountry = "Japan",
            director = "Hayao Miyazaki",
            cinematographer = "Atsushi Okui",
            productionCompany = "Studio Ghibli",
            genres = listOf("Animation", "Family", "Fantasy"),
            duration = 125,
            posterUrl = R.drawable.spirited_away_japanese_poster,
            actors = listOf("Rumi Hiiragi", "Miyu Irino", "Mari Natsuki")
        ),
        Movie(
            id = 3,
            title = "Soul",
            releaseYear = "2020",
            originCountry = "United States",
            director = "Pete Docter",
            cinematographer = "Matt Aspbury",
            productionCompany = "Pixar Animation Studios",
            genres = listOf("Animation", "Family", "Comedy"),
            duration = 100,
            posterUrl = R.drawable.soul_2020_film_poster,
            actors = listOf("Jamie Foxx", "Tina Fey", "Graham Norton")
        ),
        Movie(
            id = 4,
            title = "Nausicaä of the Valley of the Wind",
            releaseYear = "1984",
            originCountry = "Japan",
            director = "Hayao Miyazaki",
            cinematographer = "Hideshi Shirai",
            productionCompany = "Topcraft",
            genres = listOf("Animation", "Adventure", "Fantasy"),
            duration = 117,
            posterUrl = R.drawable.nausicaa,
            actors = listOf("Sumi Shimamoto", "Mahito Tsujimura", "Hisako Kyoda")
        ),
        Movie(
            id = 5,
            title = "Chainsaw Man – The Movie: Reze Arc",
            releaseYear = "2024",
            originCountry = "Japan",
            director = "Tatsuya Yoshihara",
            cinematographer = "Teppei Ito",
            productionCompany = "MAPPA",
            genres = listOf("Animation", "Action", "Fantasy"),
            duration = 90,
            posterUrl = R.drawable.chainsaw_man,
            actors = listOf("Kikunosuke Toya", "Reina Ueda", "Tomori Kusunoki")
        ),
        Movie(
            id = 6,
            title = "The Good, the Bad and the Ugly",
            releaseYear = "1966",
            originCountry = "Italy",
            director = "Sergio Leone",
            cinematographer = "Tonino Delli Colli",
            productionCompany = "Produzioni Europee Associate",
            genres = listOf("Western"),
            duration = 178,
            posterUrl = R.drawable.the_good_the_bad_and_the_ugly_poster,
            actors = listOf("Clint Eastwood", "Lee Van Cleef", "Eli Wallach")
        ),
        Movie(
            id = 7,
            title = "The Matrix",
            releaseYear = "1999",
            originCountry = "United States",
            director = "Lana Wachowski, Lilly Wachowski",
            cinematographer = "Bill Pope",
            productionCompany = "Warner Bros.",
            genres = listOf("Action", "Sci-Fi"),
            duration = 136,
            posterUrl = R.drawable.the_matrix,
            actors = listOf("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss")
        ),
        Movie(
            id = 8,
            title = "Parasite",
            releaseYear = "2019",
            originCountry = "South Korea",
            director = "Bong Joon Ho",
            cinematographer = "Hong Kyung-pyo",
            productionCompany = "Barunson E&A",
            genres = listOf("Comedy", "Thriller", "Drama"),
            duration = 132,
            posterUrl = R.drawable.parasite,
            actors = listOf("Song Kang-ho", "Lee Sun-kyun", "Cho Yeo-jeong")
        ),
        Movie(
            id = 9,
            title = "Interstellar",
            releaseYear = "2014",
            originCountry = "United States",
            director = "Christopher Nolan",
            cinematographer = "Hoyte van Hoytema",
            productionCompany = "Syncopy",
            genres = listOf("Adventure", "Drama", "Sci-Fi"),
            duration = 169,
            posterUrl = R.drawable.interstellar,
            actors = listOf("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain")
        ),
        Movie(
            id = 10,
            title = "Spider-Man: Into the Spider-Verse",
            releaseYear = "2018",
            originCountry = "United States",
            director = "Bob Persichetti, Peter Ramsey, Rodney Rothman",
            cinematographer = "Unknown",
            productionCompany = "Sony Pictures Animation",
            genres = listOf("Action", "Adventure", "Animation", "Sci-Fi"),
            duration = 117,
            posterUrl = R.drawable.spiderman,
            actors = listOf("Shameik Moore", "Jake Johnson", "Hailee Steinfeld")
        ),
        Movie(
            id = 11,
            title = "Akira",
            releaseYear = "1988",
            originCountry = "Japan",
            director = "Katsuhiro Otomo",
            cinematographer = "Katsuji Misawa",
            productionCompany = "Tokyo Movie Shinsha",
            genres = listOf("Animation", "Action", "Sci-Fi"),
            duration = 124,
            posterUrl = R.drawable.akira,
            actors = listOf("Mitsuo Iwata", "Nozomu Sasaki", "Mami Koyama")
        ),
        Movie(
            id = 12,
            title = "Whiplash",
            releaseYear = "2014",
            originCountry = "United States",
            director = "Damien Chazelle",
            cinematographer = "Sharone Meir",
            productionCompany = "Blumhouse Productions",
            genres = listOf("Drama", "Music"),
            duration = 106,
            posterUrl = R.drawable.whiplash,
            actors = listOf("Miles Teller", "J.K. Simmons", "Paul Reiser")
        ),
        Movie(
            id = 13,
            title = "Mad Max: Fury Road",
            releaseYear = "2015",
            originCountry = "Australia",
            director = "George Miller",
            cinematographer = "John Seale",
            productionCompany = "Kennedy Miller Mitchell",
            genres = listOf("Action", "Adventure", "Sci-Fi"),
            duration = 120,
            posterUrl = R.drawable.madmax,
            actors = listOf("Tom Hardy", "Charlize Theron", "Nicholas Hoult")
        ),
        Movie(
            id = 14,
            title = "Your Name.",
            releaseYear = "2016",
            originCountry = "Japan",
            director = "Makoto Shinkai",
            cinematographer = "Makoto Shinkai",
            productionCompany = "CoMix Wave Films",
            genres = listOf("Romance", "Animation", "Drama"),
            duration = 106,
            posterUrl = R.drawable.your_name,
            actors = listOf("Ryunosuke Kamiki", "Mone Kamishiraishi", "Ryo Narita")
        ),
        Movie(
            id = 15,
            title = "The Lord of the Rings: The Fellowship of the Ring",
            releaseYear = "2001",
            originCountry = "New Zealand",
            director = "Peter Jackson",
            cinematographer = "Andrew Lesnie",
            productionCompany = "WingNut Films",
            genres = listOf("Adventure", "Fantasy", "Action"),
            duration = 178,
            posterUrl = R.drawable.lord,
            actors = listOf("Elijah Wood", "Ian McKellen", "Orlando Bloom")
        ),
        Movie(
            id = 16,
            title = "Everything Everywhere All at Once",
            releaseYear = "2022",
            originCountry = "United States",
            director = "Daniel Kwan, Daniel Scheinert",
            cinematographer = "Larkin Seiple",
            productionCompany = "A24",
            genres = listOf("Action", "Adventure", "Sci-Fi"),
            duration = 139,
            posterUrl = R.drawable.every,
            actors = listOf("Michelle Yeoh", "Ke Huy Quan", "Stephanie Hsu")
        ),
        Movie(
            id = 17,
            title = "Blade Runner 2049",
            releaseYear = "2017",
            originCountry = "United States",
            director = "Denis Villeneuve",
            cinematographer = "Roger Deakins",
            productionCompany = "Alcon Entertainment",
            genres = listOf("Sci-Fi", "Drama", "Mystery"),
            duration = 164,
            posterUrl = R.drawable.blade_runner,
            actors = listOf("Ryan Gosling", "Harrison Ford", "Ana de Armas")
        ),
        Movie(
            id = 18,
            title = "The Truman Show",
            releaseYear = "1998",
            originCountry = "United States",
            director = "Peter Weir",
            cinematographer = "Peter Biziou",
            productionCompany = "Paramount Pictures",
            genres = listOf("Comedy", "Drama"),
            duration = 103,
            posterUrl = R.drawable.truman,
            actors = listOf("Jim Carrey", "Ed Harris", "Laura Linney")
        ),
        Movie(
            id = 19,
            title = "Perfect Blue",
            releaseYear = "1997",
            originCountry = "Japan",
            director = "Satoshi Kon",
            cinematographer = "Hisao Shirai",
            productionCompany = "Madhouse",
            genres = listOf("Animation", "Thriller"),
            duration = 81,
            posterUrl = R.drawable.perfect_blue,
            actors = listOf("Junko Iwao", "Rica Matsumoto", "Shinpachi Tsuji")
        ),
        Movie(
            id = 20,
            title = "The Grand Budapest Hotel",
            releaseYear = "2014",
            originCountry = "United States",
            director = "Wes Anderson",
            cinematographer = "Robert Yeoman",
            productionCompany = "Fox Searchlight Pictures",
            genres = listOf("Comedy", "Drama"),
            duration = 99,
            posterUrl = R.drawable.grand_budapest,
            actors = listOf("Ralph Fiennes", "F. Murray Abraham", "Mathieu Amalric")
        )
    )

    override suspend fun getMovies(): List<Movie> {
        delay(1500)
        return mockMovieDatabase
    }

    override suspend fun getMovieById(id: Int): Movie? {
        delay(1500)
        return mockMovieDatabase.find { it.id == id }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        delay(1000)

        val results = mockMovieDatabase.filter { movie ->
            movie.title.contains(query, ignoreCase = true)
        }

        return results.take(15)
    }
}
