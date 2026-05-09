package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.domain.model.UserStats
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import kotlinx.coroutines.delay

class MockStatsRepositoryImpl : StatsRepository {
    override suspend fun getUserStats(userId: Long, year: Int?, month: Int?): UserStats {
        delay(1500)

        return UserStats(
            weeklyWatchData = listOf(
                "Jan" to 6f, "Feb" to 3f, "Mar" to 5f, "Apr" to 2f,
                "May" to 4f, "Jun" to 1f, "Jul" to 8f, "Aug" to 0.5f,
                "Sep" to 8f, "Oct" to 3f, "Nov" to 4f, "Dec" to 6f
            ),
            todayWatchTime = "30 min",
            last7DaysWatchTime = "8 hrs 39 min",

            genres = listOf(
                StatItem("Drama", 120),
                StatItem("Romance", 100),
                StatItem("Comedy", 95),
                StatItem("Sci-Fi", 80),
                StatItem("Animation", 75),
                StatItem("Thriller", 60),
                StatItem("Horror", 50),
                StatItem("Fantasy", 45),
                StatItem("Mystery", 30),
                StatItem("Action", 20)
            ),

            countries = listOf(
                StatItem("USA", 45),
                StatItem("UK", 12),
                StatItem("South Korea", 9),
                StatItem("France", 7),
                StatItem("Japan", 5),
                StatItem("Italy", 4)
            ),

            topActors = listOf(
                StatItem("Ryan Gosling", 12),
                StatItem("Emma Stone", 10),
                StatItem("Leonardo DiCaprio", 8),
                StatItem("Brad Pitt", 8),
                StatItem("Margot Robbie", 7),
                StatItem("Cillian Murphy", 6),
                StatItem("Anya Taylor-Joy", 6),
                StatItem("Christian Bale", 5),
                StatItem("Tom Hardy", 5),
                StatItem("Florence Pugh", 5),
                StatItem("Willem Dafoe", 4),
                StatItem("Joaquin Phoenix", 4),
                StatItem("Natalie Portman", 4),
                StatItem("Robert De Niro", 3),
                StatItem("Al Pacino", 3),
                StatItem("Saoirse Ronan", 3),
                StatItem("Timothée Chalamet", 3),
                StatItem("Oscar Isaac", 2),
                StatItem("Zendaya", 2),
                StatItem("Paul Mescal", 2)
            ),

            topDirectors = listOf(
                StatItem("Christopher Nolan", 8),
                StatItem("Denis Villeneuve", 7),
                StatItem("Martin Scorsese", 6),
                StatItem("Quentin Tarantino", 6),
                StatItem("David Fincher", 5),
                StatItem("Greta Gerwig", 5),
                StatItem("Hayao Miyazaki", 4),
                StatItem("Steven Spielberg", 4),
                StatItem("Wes Anderson", 4),
                StatItem("Bong Joon Ho", 3),
                StatItem("Ridley Scott", 3),
                StatItem("Stanley Kubrick", 3),
                StatItem("Alfred Hitchcock", 3),
                StatItem("Paul Thomas Anderson", 2),
                StatItem("Guillermo del Toro", 2),
                StatItem("Sofia Coppola", 2),
                StatItem("Edgar Wright", 2),
                StatItem("Taika Waititi", 2),
                StatItem("Francis Ford Coppola", 1),
                StatItem("Damien Chazelle", 1)
            ),

            ratings = listOf(
                StatItem(0.25f, 0), StatItem(0.5f, 1), StatItem(0.75f, 1),
                StatItem(1.0f, 2), StatItem(1.25f, 1), StatItem(1.5f, 3),
                StatItem(1.75f, 2), StatItem(2.0f, 5), StatItem(2.25f, 4),
                StatItem(2.5f, 8), StatItem(2.75f, 6), StatItem(3.0f, 12),
                StatItem(3.25f, 10), StatItem(3.5f, 18), StatItem(3.75f, 15),
                StatItem(4.0f, 25), StatItem(4.25f, 20), StatItem(4.5f, 14),
                StatItem(4.75f, 8), StatItem(5.0f, 12)
            ),

            decades = listOf(
                StatItem(1910, 1),
                StatItem(1920, 2),
                StatItem(1930, 3),
                StatItem(1940, 5),
                StatItem(1950, 8),
                StatItem(1960, 12),
                StatItem(1970, 15),
                StatItem(1980, 22),
                StatItem(1990, 30),
                StatItem(2000, 35),
                StatItem(2010, 48),
                StatItem(2020, 25)
            )
        )
    }
}