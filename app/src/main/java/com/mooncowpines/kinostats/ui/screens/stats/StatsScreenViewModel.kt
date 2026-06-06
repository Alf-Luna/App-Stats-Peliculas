package com.mooncowpines.kinostats.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.AuthState
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
    private val statsRepository: StatsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StatsScreenState())
    val state: StateFlow<StatsScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.authState.collect { authState ->
                if (authState == AuthState.LOGGED_OUT) {
                    _state.value = StatsScreenState()
                }
            }
        }
        loadStatsOnly()
    }

    fun updateFilter(year: Int, month: Int?) {
        _state.update { it.copy(selectedYear = year, selectedMonth = month) }
        loadStatsOnly()
    }

    fun loadStatsOnly() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMsg = null, stats = null) }

            val user = authRepository.getCurrentUser()

            if (user == null) {
                _state.update { it.copy(isLoading = false, errorMsg = "You must be logged in") }
                return@launch
            }

            val currentYear = _state.value.selectedYear
            val currentMonth = _state.value.selectedMonth

            val fetchedStats = statsRepository.getUserStats(
                userId = user.id,
                year = currentYear,
                month = currentMonth
            )

            if (fetchedStats != null) {
                val maxMovieCount = fetchedStats.genres.maxOfOrNull { it.value } ?: 0

                _state.update {
                    it.copy(
                        isLoading = false,
                        stats = fetchedStats,
                        genreMaxMovieCount = maxMovieCount,
                        errorMsg = null
                    )
                }
            } else {
                _state.update { it.copy(isLoading = false, errorMsg = "Error loading stats", stats = null) }
            }
        }
    }
}