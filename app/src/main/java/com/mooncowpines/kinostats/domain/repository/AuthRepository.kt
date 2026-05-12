package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.User
interface AuthRepository {
    //login functions
    suspend fun login(email: String, pass: String): Boolean
    suspend fun logout()
    suspend fun getCurrentUser(): User?

    //register functions
    suspend fun register(userName: String, email: String, pass: String): Boolean
    suspend fun changePassword(newPass: String, newPassCheck: String): Boolean
    suspend fun getUserById(userId: Long): User?

    //recovery functions
    suspend fun sendRecoveryEmail(email: String): Boolean
}