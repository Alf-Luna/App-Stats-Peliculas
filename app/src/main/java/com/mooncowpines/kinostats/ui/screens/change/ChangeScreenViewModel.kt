package com.mooncowpines.kinostats.ui.screens.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.mooncowpines.kinostats.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ChangeScreenState())
    val state: StateFlow<ChangeScreenState> = _state.asStateFlow()

    init {
        loadCurrentUser()
    }

    //Functions to track text field value
    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, errorMsg = null, emailError = null) }
    }

    fun onUserNameChange(userName: String) {
        _state.update { it.copy(userName = userName, errorMsg = null, userNameError = null) }
    }

    fun onCurrentPassChange(currentPass: String) {
        _state.update { it.copy(currentPass = currentPass, errorMsg = null, currentPassError = null) }
    }

    fun onNewPassChange(newPass: String) {
        _state.update { it.copy(newPass = newPass, errorMsg = null) }
    }

    fun onNewPassCheckChange(newPassCheck: String) {
        _state.update { it.copy(newPassCheck = newPassCheck, errorMsg = null) }
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()

            user?.let { currentUser ->
                _state.update {
                    it.copy(
                        userName = currentUser.userName ?: "",
                        email = currentUser.email ?: ""
                    )
                }
            }
        }
    }

    //Triggers a data change attempt
    fun change() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text field

        val emailError = getEmailError(currentState.email)
        val userNameError = getUserNameError(currentState.userName)
        val currentPassError = getCurrentPassError(currentState.currentPass)

        val isTryingToChangePass = currentState.newPass.isNotEmpty() || currentState.newPassCheck.isNotEmpty()
        val isPassValid = isPassValid(currentState.newPass)
        val isPassCheckValid = isPassMatch(currentState.newPass, currentState.newPassCheck)

        if (emailError != null || userNameError != null || currentPassError != null || !isPassValid || !isPassCheckValid) {
            val generalError = when {
                currentState.currentPass == currentState.newPass && isTryingToChangePass -> "You cannot use the same password right away!"
                !isPassValid || !isPassCheckValid -> "Check the password validations"
                else -> "Please check the fields in red"
            }

            _state.update { it.copy(
                emailError = emailError,
                userNameError = userNameError,
                currentPassError = currentPassError,
                errorMsg = generalError
            )}
            return
        }

        //Tries a password change attempt
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null) }

            val isSuccess = authRepository.updateUser(
                userName = currentState.userName,
                email = currentState.email,
                currentPassword = currentState.currentPass,
                newPassword = if (isTryingToChangePass) currentState.newPass else null
            )

            if (isSuccess) {
                _state.update { it.copy(isSubmitting = false, success = true) }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "Could not change password") }
            }        }
    }
}



