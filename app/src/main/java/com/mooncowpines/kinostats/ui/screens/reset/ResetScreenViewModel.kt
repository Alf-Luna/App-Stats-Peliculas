package com.mooncowpines.kinostats.ui.screens.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.utils.*

class ResetScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(ResetScreenState())
    val state: StateFlow<ResetScreenState> = _state.asStateFlow()

    //Functions to track text field value
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

        if (!isPassValid || !isPassCheckValid) {
            _state.update { it.copy(errorMsg = "Check the password validations") }
            return
        }

        //Tries a password change attempt
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            delay(1500)

            _state.update { it.copy(isSubmitting = false, success = true) }
        }
    }
}



