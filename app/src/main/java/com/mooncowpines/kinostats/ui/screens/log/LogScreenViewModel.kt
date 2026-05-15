package com.mooncowpines.kinostats.ui.screens.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
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
        loadLogs()
    }

    fun loadLogs() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMsg = null) }

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

    fun deleteLog(logId: Long) {
        viewModelScope.launch {
            val success = logRepository.deleteLog(logId)
            if (success) {
                loadLogs()
            } else {
                _state.update { it.copy(errorMsg = "Could not delete the log") }
            }
        }
    }
}