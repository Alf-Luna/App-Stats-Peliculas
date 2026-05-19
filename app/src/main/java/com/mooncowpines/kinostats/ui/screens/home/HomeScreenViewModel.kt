package com.mooncowpines.kinostats.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.AuthState
import com.mooncowpines.kinostats.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.authState.collect { authState ->
                if (authState == AuthState.LOGGED_OUT) {
                    _state.value = HomeScreenState.Loading
                }
            }
        }

        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            if (_state.value !is HomeScreenState.Success) {
                _state.value = HomeScreenState.Loading
            }

            try {
                val currentUser = authRepository.getCurrentUser()
                val userId = currentUser?.id

                if (userId == null) {
                    _state.value = HomeScreenState.Error("User not found.")
                    return@launch
                }

                val homeData = homeRepository.getHomeData(userId)

                if (homeData != null) {
                    _state.value = HomeScreenState.Success(
                        watchlistMovies = homeData.watchlist.sortedByDescending { it.id },
                        justWatchedMovies = homeData.justWatched,
                        lastSeenMovie = homeData.lastSeen
                    )
                } else {
                    if (_state.value !is HomeScreenState.Success) {
                        _state.value = HomeScreenState.Error("Could not load home data.")
                    }
                }

            } catch (e: Exception) {
                if (_state.value !is HomeScreenState.Success) {
                    _state.value = HomeScreenState.Error("Something went wrong: ${e.message}")
                }
            }
        }
    }
}
