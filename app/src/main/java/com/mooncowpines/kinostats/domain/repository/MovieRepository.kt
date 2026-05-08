package com.mooncowpines.kinostats.domain.repository
import com.mooncowpines.kinostats.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovieById(id: Int): Movie?
    suspend fun searchMovies(query: String): List<Movie>
}