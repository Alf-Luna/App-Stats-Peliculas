package com.mooncowpines.kinostats.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {

            val user = authRepository.getCurrentUser()
            if (user != null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        userName = user.userName,
                        email = user.email
                    )
                }
            } else {
                _state.update { it.copy(isLoading = false, errorMsg = "No user logged in") }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()

        }
    }
}