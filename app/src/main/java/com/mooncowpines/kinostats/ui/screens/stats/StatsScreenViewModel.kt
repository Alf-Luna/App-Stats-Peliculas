package com.mooncowpines.kinostats.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val statsRepository: StatsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StatsScreenState())
    val state: StateFlow<StatsScreenState> = _state.asStateFlow()

    init {

        loadStatsData()
    }

    fun updateFilter(year: Int?, month: Int?) {
        _state.update { it.copy(selectedYear = year, selectedMonth = month) }
        loadStatsOnly()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val movies = movieRepository.getMovies()
            val lastMovie = movies.firstOrNull()
            _state.update { it.copy(lastSeenMovie = lastMovie) }

            loadStatsOnly()
        }
    }

    private fun loadStatsOnly() {
        viewModelScope.launch {
            val currentState = _state.value

            if (currentState.selectedYear == null) {
                _state.update { it.copy(isLoading = false, stats = null) }
                return@launch
            }

            val user = authRepository.getCurrentUser()

            if (user == null) {
                _state.update { it.copy(isLoading = false, errorMsg = "You must be logged in") }
                return@launch
            }

            _state.update { it.copy(isLoading = true, errorMsg = null) }

            try {
                val fetchedStats = statsRepository.getUserStats(
                    userId = user.id,
                    year = currentState.selectedYear,
                    month = currentState.selectedMonth
                )

                val maxMinutes = fetchedStats.genres.maxOfOrNull { it.value } ?: 0

                _state.update {
                    it.copy(
                        isLoading = false,
                        stats = fetchedStats,
                        genreMaxMinutes = maxMinutes,
                        errorMsg = null
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMsg = "Error loading stats") }
            }
        }
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