package com.mooncowpines.kinostats.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatsScreenViewModel @Inject constructor(
    private val statsRepository: StatsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StatsScreenState(
        selectedYear = LocalDate.now().year,
        selectedMonth = LocalDate.now().monthValue
    ))
    val state: StateFlow<StatsScreenState> = _state.asStateFlow()

    init {
        loadStatsOnly()
    }

    fun updateFilter(year: Int, month: Int?) {
        _state.update { it.copy(selectedYear = year, selectedMonth = month) }
        loadStatsOnly()
    }

    private fun loadStatsOnly() {
        viewModelScope.launch {
            val currentState = _state.value

            val user = authRepository.getCurrentUser()

            if (user == null) {
                _state.update { it.copy(isLoading = false, errorMsg = "You must be logged in") }
                return@launch
            }

            _state.update { it.copy(isLoading = true, errorMsg = null, stats = null) }

            try {
                val fetchedStats = statsRepository.getUserStats(
                    userId = user.id,
                    year = currentState.selectedYear,
                    month = currentState.selectedMonth
                )

                val maxMovieCount = fetchedStats.genres.maxOfOrNull { it.value } ?: 0

                _state.update {
                    it.copy(
                        isLoading = false,
                        stats = fetchedStats,
                        genreMaxMovieCount = maxMovieCount,
                        errorMsg = null
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMsg = "Error loading stats", stats = null) }
            }
        }
    }
}