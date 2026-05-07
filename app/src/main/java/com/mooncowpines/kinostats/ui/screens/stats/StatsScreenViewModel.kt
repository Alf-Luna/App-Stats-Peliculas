package com.mooncowpines.kinostats.ui.screens.stats

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
class StatsScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StatsScreenState())
    val state: StateFlow<StatsScreenState> = _state.asStateFlow()

    init {

        loadStatsData()
    }

    private fun loadStatsData() {
        viewModelScope.launch {

            val movies = movieRepository.getMovies()


            val lastMovie = movies.firstOrNull()


            _state.value = _state.value.copy(
                isLoading = false,
                lastSeenMovie = lastMovie
            )
        }
    }
}