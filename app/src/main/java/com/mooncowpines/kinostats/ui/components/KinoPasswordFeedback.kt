package com.mooncowpines.kinostats.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun PasswordRequirementsFeedback(pass: String, modifier: Modifier = Modifier) {
    val requirements = listOf(
        "7 characters min" to (pass.length >= 7),
        "Includes a number" to pass.any { it.isDigit() },
        "Special Character (@, #, $)" to pass.any { !it.isLetterOrDigit() }
    )

    Column(modifier = Modifier.padding(top = KinoSpacing.small)) {
        requirements.forEach { (text, isMet) ->
            Text(
                text = if (isMet) "✓ $text" else "• $text",
                color = if (isMet) KinoYellow else Color.Gray.copy(alpha = 0.6f),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun PasswordMatchFeedback(pass: String, passCheck: String, modifier: Modifier = Modifier) {

    val isMatch = passCheck == pass && passCheck.isNotBlank()

    Column(modifier = Modifier.padding(top = KinoSpacing.small)) {
        Text(
            text = if (isMatch) "✓ Passwords Match" else "• Passwords Match",
            color =  if (isMatch) KinoYellow else Color.Gray.copy(alpha = 0.6f),
            fontSize = 13.sp
        )
    }
}