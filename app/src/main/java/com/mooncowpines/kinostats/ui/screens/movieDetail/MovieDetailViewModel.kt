package com.mooncowpines.kinostats.ui.screens.movieDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.ListRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val listRepository: ListRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            val movie = movieRepository.getMovieById(movieId)
            if (movie != null) {
                _state.value = MovieDetailState.Success(movie)
            } else {
                _state.value = MovieDetailState.Error("Movie not found")
            }
        }
    }

    fun toggleFabMenu() {
        val currentState = _state.value
        if (currentState is MovieDetailState.Success) {
            _state.value = currentState.copy(isFabMenuExpanded = !currentState.isFabMenuExpanded)
        }
    }

    fun dismissFabMenu() {
        val currentState = _state.value
        if (currentState is MovieDetailState.Success) {
            _state.value = currentState.copy(isFabMenuExpanded = false)
        }
    }

    fun onOpenListSheet() {
        val currentState = _state.value
        if (currentState is MovieDetailState.Success) {
            _state.value = currentState.copy(
                isFabMenuExpanded = false,
                isListSheetOpen = true,
                isFetchingLists = true
            )

            viewModelScope.launch {
                val user = authRepository.getCurrentUser() ?: return@launch

                val userId = user.id ?: return@launch

                if (user != null) {
                    val lists = listRepository.getListsByUser(userId) ?: emptyList()
                    val newState = _state.value
                    if (newState is MovieDetailState.Success) {
                        _state.value = newState.copy(userLists = lists, isFetchingLists = false)
                    }
                } else {
                    val newState = _state.value
                    if (newState is MovieDetailState.Success) {
                        _state.value = newState.copy(isFetchingLists = false, isListSheetOpen = false)
                    }
                }
            }
        }
    }

    fun dismissListSheet() {
        val currentState = _state.value
        if (currentState is MovieDetailState.Success) {
            _state.value = currentState.copy(isListSheetOpen = false)
        }
    }

    fun addFilmToList(movieList: MovieList) {
        val currentState = _state.value
        if (currentState !is MovieDetailState.Success) return

        val currentMovieId = currentState.movie.id

        viewModelScope.launch {
            _state.value = currentState.copy(isListSheetOpen = false)

            val user = authRepository.getCurrentUser() ?: return@launch
            val userId = user.id ?: return@launch
            val success = listRepository.addFilmToList(userId, movieList.id, currentMovieId)

            val finalState = _state.value
            if (finalState is MovieDetailState.Success) {
                if (success) {
                    _state.value = finalState.copy(listActionMessage = "Added to ${movieList.name}")
                } else {
                    _state.value = finalState.copy(listActionMessage = "Error adding to list")
                }
            }
        }
    }

    fun clearListMessage() {
        val currentState = _state.value
        if (currentState is MovieDetailState.Success) {
            _state.value = currentState.copy(listActionMessage = null)
        }
    }
}