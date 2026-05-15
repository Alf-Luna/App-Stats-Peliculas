package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.data.remote.dto.MovieCardDTO
import com.mooncowpines.kinostats.data.remote.dto.MovieDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("api/v1/movies/search/{query}")
    suspend fun searchMovies(@Path("query") query: String): Response<List<MovieCardDTO>>

    @GET("api/v1/movies/{id}")
    suspend fun getMovieDetails(@Path("id") id: Long): Response<MovieDTO>
}