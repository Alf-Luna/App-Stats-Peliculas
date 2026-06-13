package com.mooncowpines.kinostats.data // Usa el paquete donde guardes tus fakes

import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.domain.repository.ListRepository

class FakeListRepository : ListRepository {

    var shouldReturnError: Boolean = false

    var mockListsByUser: List<MovieList>? = emptyList()
    var mockSingleList: MovieList? = null


    override suspend fun getListsByUser(userId: Long): List<MovieList>? {
        if (shouldReturnError) return null
        return mockListsByUser
    }

    override suspend fun getListById(listId: Long): MovieList? {
        if (shouldReturnError) return null
        return mockSingleList
    }


    override suspend fun createList(userId: Long, name: String): Boolean {
        return !shouldReturnError
    }

    override suspend fun addFilmToList(userId: Long, movieListId: Long, filmId: Long): Boolean {
        return !shouldReturnError
    }

    override suspend fun deleteList(listId: Long): Boolean {
        return !shouldReturnError
    }

    override suspend fun removeFilmFromList(listId: Long, filmId: Long): Boolean {
        return !shouldReturnError
    }
}