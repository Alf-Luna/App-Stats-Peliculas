package com.mooncowpines.kinostats.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import com.mooncowpines.kinostats.domain.repository.ListRepository
import com.mooncowpines.kinostats.domain.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val listRepository: ListRepository,
    private val logRepository: LogRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            try {

                val currentUser = authRepository.getCurrentUser()
                val userId = currentUser?.id

                var watchlistFromApi = emptyList<MovieCard>()

                if (userId != null) {
                    val allLists = listRepository.getListsByUser(userId)

                    val watchlist = allLists?.find { it.name.equals("Watchlist", ignoreCase = true) }

                    if (watchlist != null) {
                        watchlistFromApi = watchlist.movies
                    }
                }

                val justWatchedFromApi = emptyList<MovieCard>()
                val lastSeenFromApi: MovieCard? = null

                _state.value = HomeScreenState.Success(
                    watchlistMovies = watchlistFromApi,
                    justWatchedMovies = justWatchedFromApi,
                    lastSeenMovie = lastSeenFromApi
                )
            } catch (e: Exception) {
                _state.value = HomeScreenState.Error("Something went wrong: ${e.message}")
            }
        }
    }
}
