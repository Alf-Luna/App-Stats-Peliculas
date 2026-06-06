package com.mooncowpines.kinostats.utils

import android.util.Patterns
import java.time.LocalDate
import kotlin.text.isLetterOrDigit
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

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

fun getCodeError(code: String) : String? {
    if (code.isBlank()) {
        return "This field cannot be empty"
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

fun parseSafely(dateString: String?): LocalDate? {
    if (dateString.isNullOrBlank()) return null
    return try {
        LocalDate.parse(dateString)
    } catch (e: Exception) {
        null
    }
}

fun shareWrappedSlide(context: Context, bitmap: Bitmap) {
    try {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()

        val file = File(cachePath, "kino_wrapped.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share to Instagram!"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}