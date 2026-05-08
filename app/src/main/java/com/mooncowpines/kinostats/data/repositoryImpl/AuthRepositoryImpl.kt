package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.data.remote.KinoStatsApi
import com.mooncowpines.kinostats.domain.model.User
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api : KinoStatsApi
) : AuthRepository {

    override suspend fun login(email: String, pass: String): Boolean {
        return true
    }

    override suspend fun adminLogin(pass: String): Boolean {
        return true
    }

    override suspend fun logout(): Unit {}

    override suspend fun getCurrentUser(): User? {
        return null
    }

    override suspend fun register(userName: String, email: String, pass: String): Boolean {
        return true
    }

    override suspend fun changePassword(pass: String, passCheck: String): Boolean {
        return true
    }

    override suspend fun getUserById(userId: Int): User? {
        return null
    }

    override suspend fun sendRecoveryEmail(email: String): Boolean {
        return true
    }

}