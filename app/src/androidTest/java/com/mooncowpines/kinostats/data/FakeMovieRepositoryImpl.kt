package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.domain.repository.MovieRepository

class FakeMovieRepository : MovieRepository {

    var shouldReturnError: Boolean = false

    var mockMovie: Movie? = null
    var mockSearchResults: List<MovieCard> = emptyList()

    override suspend fun getMovieById(id: Long): Movie? {
        if (shouldReturnError) {
            return null
        }
        return mockMovie
    }

    override suspend fun searchMovies(query: String): List<MovieCard> {
        if (shouldReturnError) {
            return emptyList()
        }

        if (query.isNotBlank() && mockSearchResults.isNotEmpty()) {
            return mockSearchResults.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }

        return mockSearchResults
    }
}