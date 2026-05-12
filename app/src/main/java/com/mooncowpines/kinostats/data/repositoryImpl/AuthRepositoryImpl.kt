package com.mooncowpines.kinostats.data.repositoryImpl

import android.util.Log
import com.mooncowpines.kinostats.data.local.SessionManager
import com.mooncowpines.kinostats.data.remote.AuthApi
import com.mooncowpines.kinostats.domain.model.User
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import retrofit2.HttpException
import okhttp3.Credentials
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api : AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    private var currentUser: User? = null

    override suspend fun login(email: String, pass: String): Boolean {
        return try {
            val authHeader = Credentials.basic(email, pass)
            val response = api.login(authHeader)

            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    currentUser = user
                    sessionManager.saveAuthToken(authHeader)
                    Log.d("LOGIN", "User logged: ${user.userName} with ID: ${user.id}")
                    return true
                }
            }
            return false
        } catch (e: Exception) {
            Log.e("AuthRepository", "Something went wrong trying to log in", e)
            false
        }
    }

    override suspend fun logout() {
        currentUser = null
        sessionManager.clearSession()
    }

    override suspend fun getCurrentUser(): User? {
        return currentUser
    }

    override suspend fun register(userName: String, email: String, pass: String): Boolean {
        return try {
            val newUser = User(
                userName = userName,
                email = email,
                pass = pass
            )

            val response = api.register(newUser)

            if (response.isSuccessful) {
                Log.d("REGISTER", "User $userName registered with email $email")
                login(email, pass)
            } else {
                Log.e("REGISTER", "Server error: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("REGISTER", "Network Error", e)
            false
        }
    }

    override suspend fun changePassword(newPass: String, newPassCheck: String): Boolean {
        if (newPass != newPassCheck) return false

        val userToUpdate = currentUser ?: return false

        val userId = userToUpdate.id ?: return false

        return try {
            val updatedUser = userToUpdate.copy(pass = newPass)

            val response = api.updateUser(userId, updatedUser)

            if (response.isSuccessful) {
                Log.d("PASSWORD", "Password changed successfully")

                val newAuthHeader = Credentials.basic(userToUpdate.email, newPass)
                sessionManager.saveAuthToken(newAuthHeader)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("PASSWORD", "Error changing password: ", e)
            false
        }
    }

    override suspend fun getUserById(userId: Long): User? {
        return try {
            val response = api.getUserById(userId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun sendRecoveryEmail(email: String): Boolean {
        TODO()
        return true
    }

}