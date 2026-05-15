package com.mooncowpines.kinostats.data.remote

import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.data.remote.dto.MovieListAddRequest
import com.mooncowpines.kinostats.data.remote.dto.MovieListDTO
import com.mooncowpines.kinostats.data.remote.dto.MovieListRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ListApi {

    @GET("api/v1/lists/user/{userId}")
    suspend fun getListsByUser(@Path("userId") userId: Long): Response<List<MovieListDTO>>

    @GET("api/v1/lists/{listId}")
    suspend fun getListById(@Path("listId") listId: Long): Response<MovieListDTO>

    @POST("api/v1/lists")
    suspend fun createList(@Body request: MovieListRequest): Response<MovieList>

    @POST("api/v1/lists/add")
    suspend fun addFilmToList(@Body request: MovieListAddRequest): Response<Unit>

    @DELETE("api/v1/lists/{listId}")
    suspend fun deleteList(@Path("listId") listId: Long): Response<Void>

    @DELETE("api/v1/lists/{listId}/film/{filmId}")
    suspend fun removeFilmFromList(
        @Path("listId") listId: Long,
        @Path("filmId") filmId: Long
    ): Response<Unit>
}