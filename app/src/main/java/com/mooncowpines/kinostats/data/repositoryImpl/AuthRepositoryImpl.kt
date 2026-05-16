package com.mooncowpines.kinostats.data.repositoryImpl

import android.util.Log
import android.util.Log.e
import com.mooncowpines.kinostats.data.local.SessionManager
import com.mooncowpines.kinostats.data.mapper.toDomain
import com.mooncowpines.kinostats.data.remote.AuthApi
import com.mooncowpines.kinostats.domain.model.User
import com.mooncowpines.kinostats.data.remote.dto.UserDTO
import com.mooncowpines.kinostats.data.remote.dto.UserDetailsUpdateDTO
import com.mooncowpines.kinostats.data.remote.dto.UserPasswordUpdateDTO
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import retrofit2.HttpException
import okhttp3.Credentials
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api : AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    private var currentUser: User? = null

    override suspend fun login(username: String, pass: String): Boolean {
        return try {
            val authHeader = Credentials.basic(username, pass)
            val response = api.login(authHeader)

            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    val user = User(
                        id = loginResponse.userId,
                        userName = loginResponse.username,
                        email = loginResponse.email,
                        pass = pass
                    )
                    currentUser = user
                    sessionManager.saveAuthToken(authHeader)

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
            val newUserDTO = UserDTO(
                userName = userName,
                email = email,
                pass = pass
            )

            val response = api.register(newUserDTO)

            if (response.isSuccessful) {
                Log.d("REGISTER", "User $userName registered with email $email")
                login(userName, pass)
            } else {
                Log.e("REGISTER", "Server error: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("REGISTER", "Network Error", e)
            false
        }
    }

    override suspend fun updateUser(
        email: String,
        userName: String,
        currentPassword: String,
        newPassword: String?
    ): Boolean {
        val userToUpdate = currentUser ?: return false
        val userId = userToUpdate.id ?: return false

        return try {
            val isSuccess = if (newPassword == null) {
                val dto = UserDetailsUpdateDTO(
                    email = email,
                    username = userName,
                    pass = currentPassword
                )
                val response = api.updateUserDetails(userId, dto)
                response.isSuccessful
            } else {
                val dto = UserPasswordUpdateDTO(
                    newPassword = newPassword,
                    oldPassword = currentPassword
                )
                val response = api.updateUserPassword(userId, dto)
                response.isSuccessful
            }

            if (isSuccess) {
                val passToSave = newPassword ?: currentPassword
                val newAuthHeader = Credentials.basic(userName, passToSave)
                sessionManager.saveAuthToken(newAuthHeader)

                currentUser = userToUpdate.copy(
                    userName = userName,
                    email = email,
                    pass = passToSave
                )
                true
            } else {
                Log.e("UPDATE", "Server rejected the update")
                false
            }
        } catch (e: Exception) {
            Log.e("UPDATE", "Error updating account: ", e)
            false
        }
    }


    override suspend fun getUserById(userId: Long): User? {
        return try {
            val response = api.getUserById(userId)
            if (response.isSuccessful) {
                response.body()?.toDomain()
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
    }

}