package com.mooncowpines.kinostats.ui.screens.home

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
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            try {

                val allMovies = repository.getMovies()
                val lastSeen = allMovies.firstOrNull()

                if (allMovies.isNotEmpty() && lastSeen != null) {
                    _state.value = HomeScreenState.Success(
                        movies = allMovies,
                        lastSeenMovie = lastSeen
                    )
                } else {
                    _state.value = HomeScreenState.Error("No movies found")
                }
            } catch (e: Exception) {
                _state.value = HomeScreenState.Error("Something went wrong: ${e.message}")
            }
        }
    }
}
