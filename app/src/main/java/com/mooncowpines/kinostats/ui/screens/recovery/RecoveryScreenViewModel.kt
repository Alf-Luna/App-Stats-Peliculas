package com.mooncowpines.kinostats.ui.screens.recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.utils.getEmailError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.data.FakeAuthApi.sendRecoveryEmail

class RecoveryScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(RecoveryScreenState())
    val state: StateFlow<RecoveryScreenState> = _state.asStateFlow()

    //Functions to track text field value
    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, emailError = null, errorMsg = null) }
    }

    //Triggers an email recovery attempt
    fun recovery() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text field
        val emailErrorResult = getEmailError(currentState.email)

        if (emailErrorResult != null) {
            _state.update {
                it.copy(emailError = emailErrorResult)
            }
            return
        }

        //Tries an email recovery attempt
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            val isSuccess = sendRecoveryEmail(currentState.email)

            if (isSuccess) {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "This email doesn't have an account associated") }
            }
        }
    }
}


