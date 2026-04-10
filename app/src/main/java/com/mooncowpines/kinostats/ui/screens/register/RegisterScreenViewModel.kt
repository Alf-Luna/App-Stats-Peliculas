package com.mooncowpines.kinostats.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(RegisterScreenState())
    val state: StateFlow<RegisterScreenState> = _state.asStateFlow()

    fun onUserNameChange(newUserName: String) {
        _state.update { it.copy(userName = newUserName, errorMsg = null) }
        validateFields()
    }
    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, errorMsg = null) }
        validateFields()
    }

    fun onPassChange(newPass: String) {
        _state.update { it.copy(pass = newPass, errorMsg = null) }
        validateFields()
    }

    fun onPassCheckChange(newPassCheck: String) {
        _state.update { it.copy(pass = newPassCheck, errorMsg = null) }
        validateFields()
    }

    private fun validateFields() {
        val currentState = _state.value
        val isValid = currentState.email.contains("@") && currentState.pass.isNotBlank() && currentState.passCheck.isNotBlank() && currentState.userName.isNotBlank()
        _state.update { it.copy(canSubmit = isValid) }
    }

    fun register() {
        val currentState = _state.value
        if (!currentState.canSubmit || currentState.isSubmitting) return

        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            delay(1500)

            if (currentState.userName == "Alfonso" && currentState.email == "test@kinostats.com" && currentState.pass == "123456" && currentState.passCheck == currentState.pass) {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "Incorrect email or password") }
            }
        }
    }
}


