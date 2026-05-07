package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.User
interface AuthRepository {
    //login functions
    suspend fun login(email: String, pass: String): Boolean
    suspend fun adminLogin(pass: String): Boolean
    suspend fun logout()
    suspend fun getCurrentUser(): User?

    //register functions
    suspend fun register(userName: String, email: String, pass: String): Boolean
    suspend fun changePassword(pass: String, passCheck: String): Boolean
    suspend fun getUserById(userId: Int): User?

    //recovery functions
    suspend fun sendRecoveryEmail(email: String): Boolean
}