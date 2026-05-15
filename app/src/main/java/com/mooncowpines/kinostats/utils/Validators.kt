package com.mooncowpines.kinostats.utils

import android.util.Patterns
import java.time.LocalDate
import kotlin.text.isLetterOrDigit

fun getEmailError(email: String): String? {
    if (email.isBlank()) {
        return "Email cannot be empty"
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return "Invalid email format"
    }
    return null
}

fun getUserNameError(userName: String): String? {
    if (userName.isBlank()) {
        return "User name cannot be empty"
    }
    if (userName.length < 5) {
        return "User name cannot have less than 5 characters"
    }
    if (userName.any {!it.isLetter()}) {
        return "User name only can have letters"
    }
    val restrictedWords = listOf("admin", "support", "root", "moderator")
    val lowerCaseName = userName.lowercase()
    if (restrictedWords.any {lowerCaseName.contains(it)}) {
        return "This user name is reserved"
    }
        return null
}

fun getTextLogError(textLog: String): String?{
    if (textLog.isBlank()) {
        return "The log text cannot be empty"
    }

    return null
}

fun getRatingError(rating: Float): String?{
    if (rating == 0f) {
        return "The log rating cannot be 0"
    }
    return null
}

fun getWatchDateError(watchDate: LocalDate?): String? {
    if (watchDate == null) {
        return "The log must have a date"
    }
    return null
}

fun isPassValid(pass: String): Boolean {
    return pass.length >= 7 && pass.any {it.isDigit()} && pass.any { !it.isLetterOrDigit() }
}

fun isPassMatch(pass: String, passCheck: String): Boolean {
    return pass == passCheck && passCheck.isNotBlank()
}

fun getCurrentPassError(currentPass: String) :String? {
    if (currentPass.isBlank()) {
        return "This field cannot be empty"
    }

    return null
}

