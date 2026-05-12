package com.mooncowpines.kinostats.data.repositoryImpl

import com.mooncowpines.kinostats.domain.model.User
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import kotlinx.coroutines.delay


class MockAuthRepositoryImpl : AuthRepository {

    private val mockUsers = mutableListOf<User>(
        User(
            id = 1,
            userName = "Alfonso",
            email = "alfonso@kinostats.com",
            pass = "Hola1234!"
        ),
        User(
            id = 2,
            userName = "Felipe",
            email = "felipe@kinostats.com",
            pass = "Hola1234-"
        ),
        User(
            id = 3,
            userName = "Ivan",
            email = "ivan@kinostats.com",
            pass = "Hola1234?"
        ),
        User(
            id = 777,
            userName = "Admin",
            email = "admin@kinostats.com",
            pass = "IdontNeedAPass1234!"
        )
    )

    private var currentLoggedUserId: Long? = null

    override suspend fun login(email: String, pass: String): Boolean {
        delay(1500)
        val user = mockUsers.find { it.email == email && it.pass == pass }
        if (user != null) {
            currentLoggedUserId = user.id
            return true
        }
        return false
    }

    override suspend fun logout() {
        delay(500)
        currentLoggedUserId = null
    }

    override suspend fun getCurrentUser(): User? {
        return mockUsers.find { it.id == currentLoggedUserId }
    }

    override suspend fun register(newUserName: String, newEmail: String, newPass: String): Boolean {


        return true
    }

    override suspend fun changePassword(pass: String, passCheck: String): Boolean {
        delay(1500)

        if (pass != passCheck || pass.isBlank()) {
            return false
        }

        val userId = currentLoggedUserId ?: return false

        val userIndex = mockUsers.indexOfFirst { it.id == userId }
        if (userIndex != -1) {
            val oldUser = mockUsers[userIndex]
            mockUsers[userIndex] = oldUser.copy(pass = pass)
            return true
        }
        return false
    }

    override suspend fun getUserById(userId: Long): User? {
        delay(1500)
        return mockUsers.find { it.id == userId }
    }

    override suspend fun sendRecoveryEmail(email: String): Boolean {
        delay(1000)
        return true
    }
}

