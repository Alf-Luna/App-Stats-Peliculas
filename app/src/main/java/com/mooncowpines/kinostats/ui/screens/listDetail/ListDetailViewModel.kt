package com.mooncowpines.kinostats.ui.screens.listDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.domain.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    private val listRepository: ListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<ListDetailState>(ListDetailState.Loading)
    val state: StateFlow<ListDetailState> = _state.asStateFlow()

    private val listId: Long = checkNotNull(savedStateHandle["listId"])

    init {
        loadListContent()
    }

    fun loadListContent() {
        viewModelScope.launch {
            _state.value = ListDetailState.Loading
            val result = listRepository.getListById(listId)
            if (result != null) {
                _state.value = ListDetailState.Success(movieList = result)
            } else {
                _state.value = ListDetailState.Error("Could not load list details")
            }
        }
    }

    fun onConfirmRemoveIntent(movie: MovieCard) {
        val currentState = _state.value
        if (currentState is ListDetailState.Success) {
            _state.value = currentState.copy(movieToRemove = movie)
        }
    }

    fun onDismissRemoveDialog() {
        val currentState = _state.value
        if (currentState is ListDetailState.Success) {
            _state.value = currentState.copy(movieToRemove = null)
        }
    }

    fun onActionDone() {
        val currentState = _state.value
        if (currentState is ListDetailState.Success) {
            _state.value = currentState.copy(success = false)
        }
    }

    fun removeMovieFromList() {
        val currentState = _state.value
        if (currentState is ListDetailState.Success) {
            val filmId = currentState.movieToRemove?.id ?: return

            viewModelScope.launch {
                _state.value = currentState.copy(isDeleting = true)

                val isRemoved = listRepository.removeFilmFromList(listId, filmId)

                if (isRemoved) {
                    val result = listRepository.getListById(listId)
                    if (result != null) {
                        _state.value = ListDetailState.Success(
                            movieList = result,
                            isDeleting = false,
                            movieToRemove = null,
                            success = true
                        )
                    } else {
                        _state.value = ListDetailState.Error("Could not load list details")
                    }
                } else {
                    _state.value = currentState.copy(
                        isDeleting = false,
                        movieToRemove = null,
                        actionMessage = "Failed to remove movie"
                    )
                }
            }
        }
    }

    fun clearMessage() {
        val currentState = _state.value
        if (currentState is ListDetailState.Success) {
            _state.value = currentState.copy(actionMessage = null)
        }
    }
}