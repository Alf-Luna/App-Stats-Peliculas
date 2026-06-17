package com.mooncowpines.kinostats.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SessionManager @Inject constructor(
    private val prefs: SharedPreferences
) {
    companion object {
        const val AUTH_TOKEN_KEY = "auth_token_key"
        const val USER_ID_KEY = "user_id_key"
    }

    fun saveAuthToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(AUTH_TOKEN_KEY, null)
    }

    fun saveUserId(userId: Long) {
        prefs.edit().putLong(USER_ID_KEY, userId).apply()
    }

    fun fetchUserId(): Long? {
        val id = prefs.getLong(USER_ID_KEY, -1L)
        return if (id != -1L) id else null
    }

    fun clearSession() {
        prefs.edit()
            .remove(AUTH_TOKEN_KEY)
            .remove(USER_ID_KEY)
            .apply()
    }
}