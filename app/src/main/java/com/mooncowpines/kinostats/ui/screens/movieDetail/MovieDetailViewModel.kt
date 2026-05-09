package com.mooncowpines.kinostats.ui.screens.movieDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                _state.value = MovieDetailState.Error("Película no encontrada")
            }
        }
    }
}