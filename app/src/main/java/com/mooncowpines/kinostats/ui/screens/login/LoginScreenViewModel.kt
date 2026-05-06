package com.mooncowpines.kinostats.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.data.FakeAuthApi
import com.mooncowpines.kinostats.data.MockSession
import com.mooncowpines.kinostats.utils.getEmailError
import com.mooncowpines.kinostats.utils.*

class LoginScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    //Functions to track text field value
    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, errorMsg = null) }
    }
    fun onPassChange(newPass: String) {
        _state.update { it.copy(pass = newPass, errorMsg = null) }
    }

    //Triggers a login attempt
    fun login() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text fields
        val emailErrorResult = getEmailError(currentState.email)
        val isPassBlank = currentState.pass.isBlank()

        if (emailErrorResult != null || isPassBlank) {
            _state.update { it.copy(errorMsg = "Invalid email or password") }
            return
        }

        //Tries to log in
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            val loggedUser = FakeAuthApi.authenticate(
                email = currentState.email,
                pass = currentState.pass
            )

            if (loggedUser != null) {
                MockSession.currentUserId = loggedUser.id
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update {
                    it.copy(
                        isSubmitting = false,
                        errorMsg = "Invalid Email or Password"
                    )
                }
            }
        }
    }

    //Triggers an admin login attempt
    fun adminLogin() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Tries to log in as admin, doesn't do a lot of validations
        viewModelScope.launch {

            val loggedAdmin = FakeAuthApi.authenticateAdmin()

            if (loggedAdmin != null) {
                MockSession.currentUserId = loggedAdmin.id
                Log.d("Session User", "The current logged user is: ${MockSession.currentUserId}")
                _state.update {
                    it.copy(isSubmitting = false, success = true)
                }
            } else {
                _state.update {
                    it.copy(
                        isSubmitting = false,
                        errorMsg = "There was a problem login in as admin"
                    )
                }
            }
        }
    }
}