package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.data.remote.MovieApi
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api : MovieApi
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        return emptyList()
    }
    override suspend fun getMovieById(id: Long): Movie? {
        return null
    }
    override suspend fun searchMovies(query: String): List<Movie> {
        return emptyList()
    }

}