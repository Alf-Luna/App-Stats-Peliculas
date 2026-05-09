package com.mooncowpines.kinostats.data.repositoryImpl

import android.util.Log
import com.mooncowpines.kinostats.data.remote.AuthApi
import com.mooncowpines.kinostats.domain.model.User
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import retrofit2.HttpException
import okhttp3.Credentials
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api : AuthApi
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

    override suspend fun adminLogin(pass: String): Boolean {
        return true
    }

    override suspend fun logout() {
        currentUser = null
    }

    override suspend fun getCurrentUser(): User? {
        return currentUser
    }

    override suspend fun register(userName: String, email: String, pass: String): Boolean {
        return true
    }

    override suspend fun changePassword(pass: String, passCheck: String): Boolean {
        return true
    }

    override suspend fun getUserById(userId: Long): User? {
        return null

        /*try {
            api.getUserById(userId)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                null
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }*/

    }

    override suspend fun sendRecoveryEmail(email: String): Boolean {
        return true
    }

}