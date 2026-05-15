package com.mooncowpines.kinostats.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        val initialQuery = savedStateHandle.get<String>("query") ?: ""

        _state.update { it.copy(searchQuery = initialQuery) }

        if (initialQuery.isNotBlank()) {
            performSearch(initialQuery)
        }
    }

    fun updateQuery(newQuery: String) {
        _state.update { it.copy(searchQuery = newQuery) }
        if (newQuery.isBlank()) {
            _state.update { it.copy(results = emptyList(), errorMsg = null) }
        }
    }

    fun submitSearch() {
        val currentQuery = _state.value.searchQuery
        if (currentQuery.isNotBlank()) {
            performSearch(currentQuery)
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMsg = null) }
            try {
                val searchResults = movieRepository.searchMovies(query)
                _state.update {
                    it.copy(
                        isLoading = false,
                        results = searchResults,
                        errorMsg = if (searchResults.isEmpty()) "No movies found for '$query'" else null
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMsg = "Error connecting to server") }
            }
        }
    }
}