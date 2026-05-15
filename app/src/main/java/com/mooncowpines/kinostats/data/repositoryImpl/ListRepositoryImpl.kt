package com.mooncowpines.kinostats.data.repositoryImpl


import android.util.Log
import com.mooncowpines.kinostats.data.mapper.toDomain
import com.mooncowpines.kinostats.data.remote.ListApi
import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.data.remote.dto.MovieListAddRequest
import com.mooncowpines.kinostats.data.remote.dto.MovieListRequest
import com.mooncowpines.kinostats.domain.repository.ListRepository
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val api: ListApi
) : ListRepository {

    override suspend fun getListsByUser(userId: Long): List<MovieList>? {
        return try {
            val response = api.getListsByUser(userId)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() }
            } else {
                Log.e("ListRepository", "Error al obtener las listas: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ListRepository", "Error de red en getListsByUser", e)
            null
        }
    }

    override suspend fun getListById(listId: Long): MovieList? {
        return try {
            val response = api.getListById(listId)
            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else {
                Log.e("ListRepository", "Error al obtener la lista $listId: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ListRepository", "Error de red en getListById", e)
            null
        }
    }

    override suspend fun createList(userId: Long, name: String): Boolean {
        return try {
            val request = MovieListRequest(userId = userId, name = name)
            val response = api.createList(request)

            if (response.isSuccessful) {
                Log.d("ListRepository", "Lista '$name' creada exitosamente")
                true
            } else {
                Log.e("ListRepository", "Error al crear lista: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("ListRepository", "Error de red al crear lista", e)
            false
        }
    }

    override suspend fun addFilmToList(userId: Long, movieListId: Long, filmId: Long): Boolean {
        return try {
            val request = MovieListAddRequest(
                userId = userId,
                movieListId = movieListId,
                filmId = filmId
            )
            val response = api.addFilmToList(request)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("ListRepository", "Error de red al añadir película", e)
            false
        }
    }

    override suspend fun deleteList(listId: Long): Boolean {
        return try {
            val response = api.deleteList(listId)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeFilmFromList(listId: Long, filmId: Long): Boolean {
        return try {
            val response = api.removeFilmFromList(listId, filmId)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("ListRepository", "Error de red al remover película", e)
            false
        }
    }
}