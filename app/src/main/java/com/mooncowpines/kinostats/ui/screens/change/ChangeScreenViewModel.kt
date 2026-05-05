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

    //Functions to track text field value
    fun onCurrentPassChange(currentPass: String) {
        _state.update { it.copy(currentPass = currentPass, errorMsg = null, currentPassError = null) }
    }

    fun onNewPassChange(newPass: String) {
        _state.update { it.copy(newPass = newPass, errorMsg = null) }
    }

    fun onNewPassCheckChange(newPassCheck: String) {
        _state.update { it.copy(newPassCheck = newPassCheck, errorMsg = null) }
    }

    //Triggers a password change attempt
    fun change() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text field

        val currentPassError = getCurrentPassError(currentPass = currentState.currentPass)
        val isPassValid = isPassValid(currentState.newPass)
        val isPassCheckValid = isPassMatch(currentState.newPass, currentState.newPassCheck)

        if (currentPassError != null || !isPassValid || !isPassCheckValid) {
            _state.update { it.copy(
                currentPassError = currentPassError,
                errorMsg =
                    if (currentState.currentPass == currentState.newPass)
                        {"You cannot use the same password right away!"} else {"Check the password validations"})
            }
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



