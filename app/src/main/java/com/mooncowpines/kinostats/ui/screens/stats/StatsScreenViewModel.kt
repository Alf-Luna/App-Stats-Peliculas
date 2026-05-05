package com.mooncowpines.kinostats.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.data.FakeMovieApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatsScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(StatsScreenState())
    val state: StateFlow<StatsScreenState> = _state.asStateFlow()

    init {

        loadStatsData()
    }

    private fun loadStatsData() {
        viewModelScope.launch {

            val movies = FakeMovieApi.getMovies()


            val lastMovie = movies.firstOrNull()


            _state.value = _state.value.copy(
                isLoading = false,
                lastSeenMovie = lastMovie
            )
        }
    }
}