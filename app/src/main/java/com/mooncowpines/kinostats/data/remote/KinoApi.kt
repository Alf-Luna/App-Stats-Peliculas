package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.domain.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path

interface KinoStatsApi {
    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @GET("movies/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Movie


    // Implementar el resto de métodos
}