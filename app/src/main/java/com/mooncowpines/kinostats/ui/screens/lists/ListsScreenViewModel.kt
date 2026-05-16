package com.mooncowpines.kinostats.ui.screens.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsScreenViewModel @Inject constructor(
    private val listRepository: ListRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListsScreenState())
    val state: StateFlow<ListsScreenState> = _state.asStateFlow()

    init {
        loadLists()
    }

    fun loadLists() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMsg = null) }

            val user = authRepository.getCurrentUser()
            val userId = user?.id

            if (userId == null) {
                _state.update { it.copy(isLoading = false, errorMsg = "User not found. Please log in again.") }
                return@launch
            }

            val userLists = listRepository.getListsByUser(userId)

            if (userLists != null) {
                _state.update { it.copy(isLoading = false, lists = userLists) }
            } else {
                _state.update { it.copy(isLoading = false, errorMsg = "Failed to load lists.") }
            }
        }
    }

    fun onConfirmDeleteIntent(movieList: MovieList) {
        _state.update { it.copy(listToDelete = movieList) }
    }

    fun onDismissDeleteDialog() {
        _state.update { it.copy(listToDelete = null) }
    }

    fun deleteList() {
        val listId = _state.value.listToDelete?.id ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            val success = listRepository.deleteList(listId)

            if (success) {
                _state.update { it.copy(listToDelete = null, isDeleting = false) }
                loadLists()
            } else {
                _state.update {
                    it.copy(
                        isDeleting = false,
                        errorMsg = "Could not delete list",
                        listToDelete = null
                    )
                }
            }
        }
    }
}