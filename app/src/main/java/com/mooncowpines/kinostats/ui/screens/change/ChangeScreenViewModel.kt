package com.mooncowpines.kinostats.ui.screens.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.utils.*

class ChangeScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(ChangeScreenState())
    val state: StateFlow<ChangeScreenState> = _state.asStateFlow()

    fun onPassChange(newPass: String) {
        _state.update { it.copy(pass = newPass, errorMsg = null) }
    }

    fun onPassCheckChange(newPassCheck: String) {
        _state.update { it.copy(passCheck = newPassCheck, errorMsg = null) }
    }

    fun change() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        val isPassValid = isPassValid(currentState.pass)
        val isPassCheckValid = isPassMatch(currentState.pass, currentState.passCheck)

        if (!isPassValid || !isPassCheckValid) {
            _state.update { it.copy(errorMsg = "Check the password validations") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            delay(1500)

            _state.update { it.copy(isSubmitting = false, success = true) }
        }
    }
}



