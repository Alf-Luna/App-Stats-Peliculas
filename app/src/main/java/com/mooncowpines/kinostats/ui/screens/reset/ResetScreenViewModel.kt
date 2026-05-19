package com.mooncowpines.kinostats.ui.screens.reset

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ResetScreenState())
    val state: StateFlow<ResetScreenState> = _state.asStateFlow()

    private val userEmail: String = checkNotNull(savedStateHandle["email"]) ?: "CORREO_NO_ENCONTRADO"

    //Functions to track text field value
    fun onCodeChange(newCode: String) {
        if (newCode.length <= 6) {
            _state.update { it.copy(code = newCode, errorMsg = null) }
        }
    }

    fun onPassChange(newPass: String) {
        _state.update { it.copy(pass = newPass, errorMsg = null) }
    }

    fun onPassCheckChange(newPassCheck: String) {
        _state.update { it.copy(passCheck = newPassCheck, errorMsg = null) }
    }

    //Triggers a password change attempt
    fun reset() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text field
        val isPassValid = isPassValid(currentState.pass)
        val isPassCheckValid = isPassMatch(currentState.pass, currentState.passCheck)
        val codeErrorResult = getCodeError(currentState.code)

        if (codeErrorResult != null) {
            _state.update { it.copy(codeError = codeErrorResult) }
            return
        }

        if (!isPassValid || !isPassCheckValid) {
            _state.update { it.copy(errorMsg = "Check the password validations") }
            return
        }

        //Tries a password change attempt
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            val isSuccess = authRepository.resetPassword(
                email = userEmail,
                code = currentState.code,
                newPass = currentState.pass
            )

            if (isSuccess) {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
            _state.update {
                it.copy(isSubmitting = false, errorMsg = "Invalid or expired code")
            }
        }
        }
    }
}




