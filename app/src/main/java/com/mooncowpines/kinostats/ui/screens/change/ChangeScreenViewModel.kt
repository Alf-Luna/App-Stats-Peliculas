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

    fun onPassForProfileChange(pass: String) {
        _state.update { it.copy(passForProfile = pass, errorMsg = null, passForProfileError = null) }
    }

    fun onNewPassChange(newPass: String) {
        _state.update { it.copy(newPass = newPass, errorMsg = null) }
    }
    fun onNewPassCheckChange(newPassCheck: String) {
        _state.update { it.copy(newPassCheck = newPassCheck, errorMsg = null) }
    }
    fun onPassForPasswordChange(currentPass: String) {
        _state.update { it.copy(passForPassword = currentPass, errorMsg = null, passForPasswordError = null) }
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()?.let { currentUser ->
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
    fun changeProfile() {
        val currentState = _state.value
        if (currentState.isSubmittingProfile || currentState.isSubmittingPassword) return

        val emailError = getEmailError(currentState.email)
        val userNameError = getUserNameError(currentState.userName)
        val currentPassError = getCurrentPassError(currentState.passForProfile)

        if (emailError != null || userNameError != null || currentPassError != null) {
            _state.update { it.copy(emailError = emailError, userNameError = userNameError, passForProfileError = currentPassError, errorMsg = "Check profile fields") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSubmittingProfile = true, errorMsg = null) }
            val isSuccess = authRepository.updateUser(
                userName = currentState.userName,
                email = currentState.email,
                currentPassword = currentState.passForProfile,
                newPassword = null
            )

            if (isSuccess) _state.update { it.copy(isSubmittingProfile = false, profileSuccess = true) }
            else _state.update { it.copy(isSubmittingProfile = false, errorMsg = "Could not update profile") }
        }
    }

    fun changePassword() {
        val currentState = _state.value
        if (currentState.isSubmittingProfile || currentState.isSubmittingPassword) return

        val currentPassError = getCurrentPassError(currentState.passForPassword)
        val isPassValid = isPassValid(currentState.newPass)
        val isPassCheckValid = isPassMatch(currentState.newPass, currentState.newPassCheck)

        if (currentPassError != null || !isPassValid || !isPassCheckValid) {
            val error = if (currentState.passForPassword == currentState.newPass) "Cannot use the same password" else "Check password fields"
            _state.update { it.copy(passForPasswordError = currentPassError, errorMsg = error) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSubmittingPassword = true, errorMsg = null) }
            val isSuccess = authRepository.updateUser(
                userName = currentState.userName,
                email = currentState.email,
                currentPassword = currentState.passForPassword,
                newPassword = currentState.newPass
            )

            if (isSuccess) _state.update { it.copy(isSubmittingPassword = false, passwordSuccess = true) }
            else _state.update { it.copy(isSubmittingPassword = false, errorMsg = "Could not update password") }
        }
    }

    fun resetSuccessFlags() = _state.update { it.copy(profileSuccess = false, passwordSuccess = false) }
}



