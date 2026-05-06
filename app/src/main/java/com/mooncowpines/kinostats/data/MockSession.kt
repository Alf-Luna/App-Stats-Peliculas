package com.mooncowpines.kinostats.data

object MockSession {
    var currentUserId: Int? = null

    fun isLoggedIn(): Boolean = currentUserId != null

    fun logout() {
        currentUserId = null
    }
}