package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.domain.model.User
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAuthRepositoryImpl : AuthRepository {

    var shouldReturnError: Boolean = false
    var customErrorMessage: String = "Network Error"

    private val _authState = MutableStateFlow(AuthState.LOGGED_OUT)
    override val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun emitAuthState(state: AuthState) {
        _authState.value = state
    }

    override suspend fun register(userName: String, email: String, pass: String): String? {
        if (shouldReturnError) {
            return customErrorMessage
        }
        return null
    }

    override suspend fun login(email: String, pass: String): String? {
        if (shouldReturnError) {
            return customErrorMessage
        }
        _authState.value = AuthState.LOGGED_IN
        return null
    }

    override suspend fun logout() {
        _authState.value = AuthState.LOGGED_OUT
    }

    override suspend fun getCurrentUser(): User? {
        return null
    }

    override suspend fun updateUser(email: String, userName: String, currentPassword: String, newPassword: String?): String? {
        return null
    }

    override suspend fun getUserById(userId: Long): User? {
        return null
    }

    override suspend fun sendRecoveryEmail(email: String): String? {
        return null
    }

    override suspend fun resetPassword(email: String, code: String, newPass: String): String? {
        return null
    }
}