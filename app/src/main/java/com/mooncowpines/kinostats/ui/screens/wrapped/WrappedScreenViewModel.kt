package com.mooncowpines.kinostats.ui.screens.wrapped

import androidx.lifecycle.SavedStateHandle
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
import javax.inject.Inject

@HiltViewModel
class WrappedViewModel @Inject constructor(
    private val statsRepository: StatsRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(WrappedScreenState())
    val state: StateFlow<WrappedScreenState> = _state.asStateFlow()

    val targetYear: Int = checkNotNull(savedStateHandle["year"])
    private val rawMonth: Int = savedStateHandle.get<Int>("month") ?: -1
    val targetMonth: Int? = if (rawMonth != -1) rawMonth else null

    init {
        loadWrappedData()
    }

    private fun loadWrappedData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMsg = null) }

            val user = authRepository.getCurrentUser()
            if (user == null || user.id == null) {
                _state.update { it.copy(isLoading = false, errorMsg = "User not found, please log in.") }
                return@launch
            }

            val fetchedStats = statsRepository.getUserStats(
                userId = user.id,
                year = targetYear,
                month = targetMonth
            )

            if (fetchedStats != null) {
                _state.update { it.copy(isLoading = false, stats = fetchedStats) }
            } else {
                _state.update { it.copy(isLoading = false, errorMsg = "Wrapped could not be loaded.") }
            }
        }
    }
}