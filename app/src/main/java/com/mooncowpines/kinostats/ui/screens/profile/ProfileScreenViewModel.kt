package com.mooncowpines.kinostats.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.data.FakeAuthApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import com.mooncowpines.kinostats.data.MockSession
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        val currentUserId = MockSession.currentUserId

        if (currentUserId == null) {
            _state.update { it.copy(isLoading = false, errorMsg = "No user logged in") }
            return
        }

        viewModelScope.launch {
            val user = FakeAuthApi.getUserById(currentUserId)

            if (user != null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        userName = user.userName,
                        email = user.email
                    )
                }
            } else {
                _state.update { it.copy(isLoading = false, errorMsg = "User not found") }
            }
        }
    }
}