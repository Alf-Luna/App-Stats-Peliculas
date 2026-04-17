package com.mooncowpines.kinostats.ui.screens.recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecoveryScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(RecoveryScreenState())
    val state: StateFlow<RecoveryScreenState> = _state.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, errorMsg = null) }
        validateFields()
    }

    private fun validateFields() {
        val currentState = _state.value
        val isValid = currentState.email.contains("@")
        _state.update { it.copy(canSubmit = isValid) }
    }

    fun recovery() {
        val currentState = _state.value
        if (!currentState.canSubmit || currentState.isSubmitting) return

        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            delay(1500)

            if (currentState.email == "test@kinostats.com") {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "This email doesn't have an account associated") }
            }
        }
    }
}


