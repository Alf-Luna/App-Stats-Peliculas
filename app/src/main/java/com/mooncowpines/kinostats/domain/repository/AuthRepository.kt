package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.User
import kotlinx.coroutines.flow.StateFlow

enum class AuthState {
    LOGGED_IN,
    LOGGED_OUT
}
interface AuthRepository {

    val authState: StateFlow<AuthState>

    //login functions
    suspend fun login(username: String, pass: String): Boolean
    suspend fun logout()
    suspend fun getCurrentUser(): User?

    //register functions
    suspend fun register(userName: String, email: String, pass: String): Boolean
    suspend fun updateUser(email: String, userName: String, currentPassword: String, newPassword: String?): Boolean
    suspend fun getUserById(userId: Long): User?

    //recovery functions
    suspend fun sendRecoveryEmail(email: String): Boolean

    suspend fun resetPassword(email: String, code: String, newPass: String): Boolean
}