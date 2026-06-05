package com.mooncowpines.kinostats.data.repositoryImpl

import android.util.Log
import com.mooncowpines.kinostats.data.local.SessionManager
import com.mooncowpines.kinostats.data.mapper.toDomain
import com.mooncowpines.kinostats.data.remote.AuthApi
import com.mooncowpines.kinostats.data.remote.dto.ForgotPasswordDTO
import com.mooncowpines.kinostats.data.remote.dto.ResetPasswordDTO
import com.mooncowpines.kinostats.domain.model.User
import com.mooncowpines.kinostats.data.remote.dto.UserDTO
import com.mooncowpines.kinostats.data.remote.dto.UserDetailsUpdateDTO
import com.mooncowpines.kinostats.data.remote.dto.UserPasswordUpdateDTO
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.AuthState
import com.mooncowpines.kinostats.utils.getErrorMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import okhttp3.Credentials
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api : AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    private val _authState = MutableStateFlow(
        if (sessionManager.fetchAuthToken() != null) AuthState.LOGGED_IN else AuthState.LOGGED_OUT
    )

    override val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private var currentUser: User? = null

    init {
        val token = sessionManager.fetchAuthToken()
        val savedUserId = sessionManager.fetchUserId()

        if (token != null && savedUserId != null) {
            _authState.value = AuthState.LOGGED_IN
        } else {
            _authState.value = AuthState.LOGGED_OUT
        }
    }

    override suspend fun login(username: String, pass: String): String? {
        try {
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
                    sessionManager.saveUserId(loginResponse.userId)

                    _authState.value = AuthState.LOGGED_IN

                    return null
                }
                return "Empty response from server"
            } else {
                return response.getErrorMessage()
            }
        } catch (e: IOException) {
            return "Network error. Check your connection."
        } catch (e: Exception) {
            Log.e("AuthRepository", "Something went wrong trying to log in", e)
            return "An unexpected error occurred"
        }
    }

    override suspend fun logout() {
        currentUser = null
        sessionManager.clearSession()
        _authState.value = AuthState.LOGGED_OUT
    }

    override suspend fun getCurrentUser(): User? {

        if (currentUser != null) {
            return currentUser
        }

        val savedUserId = sessionManager.fetchUserId()

        if (savedUserId != null) {
            currentUser = getUserById(savedUserId)
        }
        return currentUser
    }

    override suspend fun register(userName: String, email: String, pass: String): String? {
        try {
            val newUserDTO = UserDTO(
                userName = userName,
                email = email,
                pass = pass
            )

            val response = api.register(newUserDTO)

            if (response.isSuccessful) {
                Log.d("REGISTER", "User $userName registered with email $email")
                return login(userName, pass)
            } else {
                return response.getErrorMessage()
            }
        } catch (e: IOException) {
            return "Network error. Check your connection."
        } catch (e: Exception) {
            Log.e("REGISTER", "Network Error", e)
            return "An unexpected error occurred"
        }
    }

    override suspend fun updateUser(
        email: String,
        userName: String,
        currentPassword: String,
        newPassword: String?
    ): String? {
        val userToUpdate = currentUser ?: return "User not found"
        val userId = userToUpdate.id ?: return "User ID missing"

        return try {
            val response = if (newPassword == null) {
                val dto = UserDetailsUpdateDTO(email = email, username = userName, pass = currentPassword)
                api.updateUserDetails(userId, dto)
            } else {
                val dto = UserPasswordUpdateDTO(newPassword = newPassword, oldPassword = currentPassword)
                api.updateUserPassword(userId, dto)
            }

            if (response.isSuccessful) {
                val passToSave = newPassword ?: currentPassword
                val newAuthHeader = Credentials.basic(userName, passToSave)
                sessionManager.saveAuthToken(newAuthHeader)

                currentUser = userToUpdate.copy(
                    userName = userName,
                    email = email,
                    pass = passToSave
                )
                null
            } else {
                response.getErrorMessage()
            }
        } catch (e: IOException) {
            "Network error. Check your connection."
        } catch (e: Exception) {
            Log.e("UPDATE", "Error updating account: ", e)
            "An unexpected error occurred"
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

    override suspend fun sendRecoveryEmail(email: String): String? {
        try {
            val request = ForgotPasswordDTO(email = email)
            val response = api.requestPasswordReset(request)

            if (response.isSuccessful) {
                Log.d("AuthRepository", "Recovery email sent successfully")
                return null
            } else {
                return response.getErrorMessage()
            }
        } catch (e: IOException) {
            return "Network error. Check your connection."
        } catch (e: Exception) {
            Log.e("AuthRepository", "Network error sending email", e)
            return "An unexpected error occurred"
        }
    }

    override suspend fun resetPassword(email: String, code: String, newPass: String): String? {
         try {
            val request = ResetPasswordDTO(
                email = email,
                code = code,
                newPassword = newPass
            )

            val response = api.resetPassword(request)

             if (response.isSuccessful) {
                 Log.d("AuthRepository", "Password reset successfully")
                 return null
             } else {
                 return response.getErrorMessage()
             }
         } catch (e: IOException) {
             return "Network error. Check your connection."
         } catch (e: Exception) {
             Log.e("AuthRepository", "Network error resetting password", e)
             return "An unexpected error occurred"
         }
    }

}