package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.MovieList

interface ListRepository {
    suspend fun getListsByUser(userId: Long): List<MovieList>?
    suspend fun getListById(listId: Long): MovieList?

    suspend fun createList(userId: Long, name: String): Boolean
    suspend fun addFilmToList(userId: Long, movieListId: Long, filmId: Long): Boolean
    suspend fun removeFilmFromList(listId: Long, filmId: Long): Boolean
}