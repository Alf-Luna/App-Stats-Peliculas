package com.mooncowpines.kinostats.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.utils.getEmailError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    //Functions to track text field value
    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, errorMsg = null) }
    }
    fun onPassChange(newPass: String) {
        _state.update { it.copy(pass = newPass, errorMsg = null) }
    }

    fun testApiEndpoint() {
        viewModelScope.launch {
            Log.d("API_TEST", "Iniciando llamada al backend...")
            try {
                val user = authRepository.getUserById(1)

                if (user != null) {
                    Log.d("API_TEST", "✅ ÉXITO: Usuario recibido -> ${user.userName} | ${user.email}")
                } else {
                    Log.w("API_TEST", "⚠️ AVISO: El backend respondió pero el usuario no existe (404)")
                }
            } catch (e: Exception) {
                Log.e("API_TEST", "❌ ERROR: Falló la comunicación con la API", e)
            }
        }
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

            val isSuccess = authRepository.login(
                email = currentState.email,
                pass = currentState.pass
            )

            if (isSuccess) {
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

        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true) }

            val isSuccess = authRepository.adminLogin("kinoadmin2026")

            if (isSuccess) {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "Admin login failed") }
            }
        }
    }
}