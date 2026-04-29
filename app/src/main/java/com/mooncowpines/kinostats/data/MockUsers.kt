package com.mooncowpines.kinostats.data

import com.mooncowpines.kinostats.utils.isPassMatch
import com.mooncowpines.kinostats.utils.isPassValid
import kotlinx.coroutines.delay

data class User(
    val id: Int,
    val userName: String,
    val email: String,
    val pass: String,
)

object FakeAuthApi {
    private val mockUsers = mutableListOf(
        User(id = 1,
            userName = "Alfonso",
            email = "alfonso@kinostats.com",
            pass ="Hola1234!"),
        User(id = 2,
            userName = "Felipe",
            email = "felipe@kinostats.com",
            pass ="Hola1234-"),
        User(id = 3,
            userName = "Ivan",
            email = "ivan@kinostats.com",
            pass = "Hola1234?"),
        User(id = 777,
            userName = "Admin",
            email = "admin@kinostats.com",
            pass = "IdontNeedAPass1234!")
    )

    suspend fun authenticate(email: String, pass: String): User? {
        delay(1500)

        return mockUsers.find { it.email == email && it.pass == pass }
    }

    suspend fun authenticateAdmin(): User? {
        delay(1500)

        val admin = mockUsers[3]

        return admin
    }

    suspend fun registerUser(newUserName: String, newEmail: String, newPass: String): Boolean {
        delay(1500)

        val emailExists = mockUsers.any { it.email == newEmail }
        if (emailExists) {
            return false
        }

        val newId = if (mockUsers.isEmpty()) 1 else mockUsers.maxOf { it.id } + 1

        val newUser = User(id = newId, userName = newUserName, email = newEmail, pass = newPass)
        mockUsers.add(newUser)

        return true
    }

    suspend fun sendRecoveryEmail(email: String): Boolean {
        delay(1500)

        return mockUsers.any {it.email == email}

    }

    suspend fun changePassword(pass: String, passCheck: String): Boolean {
        delay(1500)

        val validPass = isPassValid(pass)
        val matchPass = isPassMatch(pass, passCheck)

        return validPass && matchPass
    }

}