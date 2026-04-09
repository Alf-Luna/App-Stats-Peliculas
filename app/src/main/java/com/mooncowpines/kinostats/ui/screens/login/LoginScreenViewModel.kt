package com.mooncowpines.kinostats.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, errorMsg = null) }
        validateFields()
    }

    fun onPassChange(newPass: String) {
        _state.update { it.copy(pass = newPass, errorMsg = null) }
        validateFields()
    }

    private fun validateFields() {
        val currentState = _state.value
        val isValid = currentState.email.contains("@") && currentState.pass.isNotBlank()
        _state.update { it.copy(canSubmit = isValid) }
    }

    fun login() {
        val currentState = _state.value
        if (!currentState.canSubmit || currentState.isSubmitting) return

        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            delay(1500)

            if (currentState.email == "test@kinostats.com" && currentState.pass == "123456") {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "Incorrect email or password") }
            }
        }
    }
}


