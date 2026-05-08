package com.mooncowpines.kinostats.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterScreenState())
    val state: StateFlow<RegisterScreenState> = _state.asStateFlow()

    //Functions to track text field value
    fun onUserNameChange(newUserName: String) {
        _state.update { it.copy(userName = newUserName, errorMsg = null, userNameError = null) }
    }

    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, errorMsg = null, emailError = null) }
    }

    fun onPassChange(newPass: String) {
        _state.update { it.copy(pass = newPass, errorMsg = null) }
    }

    fun onPassCheckChange(newPassCheck: String) {
        _state.update { it.copy(passCheck = newPassCheck, errorMsg = null) }
    }

    //Triggers a register attempt
    fun register() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text fields
        val emailErrorResult = getEmailError((currentState.email))
        val userNameErrorResult = getUserNameError(currentState.userName)
        val passValid = isPassValid(currentState.pass)
        val passMatch = isPassMatch(currentState.pass, currentState.passCheck)

        if (emailErrorResult != null || userNameErrorResult != null || !passValid || !passMatch) {
            _state.update {
                it.copy(
                    userNameError = userNameErrorResult,
                    emailError = emailErrorResult,
                    errorMsg = "Please check the required fields") }
            return
        }

        //Tries to register a user
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null, emailError = null, userNameError = null) }

            val isSuccess = authRepository.register(
                userName = currentState.userName,
                email = currentState.email,
                pass = currentState.pass
            )

            if (isSuccess) {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "Email is already registered") }
            }
        }
    }
}



