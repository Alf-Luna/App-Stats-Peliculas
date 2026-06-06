package com.mooncowpines.kinostats.ui.screens.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.model.Log
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.AuthState
import com.mooncowpines.kinostats.domain.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogScreenViewModel @Inject constructor(
    private val logRepository: LogRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LogScreenState())
    val state: StateFlow<LogScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.authState.collect { authState ->
                if (authState == AuthState.LOGGED_OUT) {
                    _state.value = LogScreenState()
                }
            }
        }
        loadLogs()
    }

    fun loadLogs() {
        viewModelScope.launch {
            val showLoadingSpinner = _state.value.logs.isEmpty()

            _state.update { it.copy(isLoading = showLoadingSpinner, errorMsg = null) }

            val user = authRepository.getCurrentUser()
            val userId = user?.id

            if (userId == null) {
                _state.update { it.copy(isLoading = false, errorMsg = "User not found. Please log in again.") }
                return@launch
            }

            try {
                val userLogs = logRepository.getLogsByUser(userId)
                _state.update { it.copy(isLoading = false, logs = userLogs ?: emptyList()) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMsg = "Failed to load your diary.") }
            }
        }
    }

    fun onConfirmDeleteIntent(log: Log) {
        _state.update { it.copy(logToDelete = log) }
    }

    fun onDismissDeleteDialog() {
        _state.update { it.copy(logToDelete = null) }
    }

    fun onActionDone() {
        _state.update { it.copy(success = false) }
    }

    fun deleteLog() {
        val logId = _state.value.logToDelete?.id ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            val isDeleted = logRepository.deleteLog(logId)

            if (isDeleted) {
                _state.update {
                    it.copy(
                        logToDelete = null,
                        isDeleting = false,
                        success = true
                    )
                }
                loadLogs()
            } else {
                _state.update {
                    it.copy(isDeleting = false, logToDelete = null, errorMsg = "Failed to delete")
                }
            }
        }
    }
}