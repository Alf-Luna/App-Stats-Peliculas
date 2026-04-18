package com.mooncowpines.kinostats.data

import kotlinx.coroutines.delay

data class User(val userName: String, val email: String)

object FakeAuthApi {
    private val mockDatabase = mutableListOf(
        mapOf("userName" to "Alfonso", "email" to "alfonso@kinostats.com", "pass" to "Hola1234!"),
        mapOf("userName" to "Felipe", "email" to "felipe@kinostats.com", "pass" to "Hola1234-"),
        mapOf("userName" to "Ivan", "email" to "ivan@kinostats.com", "pass" to "Hola1234?")
    )

    suspend fun authenticate(email: String, pass: String): User? {
        delay(1500)

        val userRecord = mockDatabase.find { it["email"] == email && it["pass"] == pass }

        return if (userRecord != null) {
            User(userName = userRecord["userName"]!!, email = userRecord["email"]!!)
        } else {
            null
        }
    }

    suspend fun registerUser(userName: String, email: String, pass: String): Boolean {
        delay(1500)

        val emailExists = mockDatabase.any { it["email"] == email }

        if (emailExists) {
            return false
        }

        val newUser = mapOf("userName" to userName, "email" to email, "pass" to pass)
        mockDatabase.add(newUser)

        return true
    }

}