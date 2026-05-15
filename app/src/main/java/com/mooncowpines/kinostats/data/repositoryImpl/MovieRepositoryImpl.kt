package com.mooncowpines.kinostats.data.repositoryImpl

import android.util.Log
import com.mooncowpines.kinostats.data.mapper.toDomain
import com.mooncowpines.kinostats.data.remote.MovieApi
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api : MovieApi
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        TODO()
    }
    override suspend fun getMovieById(id: Long): Movie? {
        return try {
            val response = api.getMovieDetails(id)
            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else {
                Log.e("MOVIE_REPO", "Error: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MOVIE_REPO", "Network error", e)
            null
        }
    }
    override suspend fun searchMovies(query: String): List<MovieCard> {
        return try {
            val response = api.searchMovies(query)
            if (response.isSuccessful) {
                val listDTO = response.body() ?: emptyList()
                listDTO.map { cardDto -> cardDto.toDomain() }
            } else {
                Log.e("MOVIE_REPO", "Search Error: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MOVIE_REPO", "Search Network error", e)
            emptyList()
        }
    }

}