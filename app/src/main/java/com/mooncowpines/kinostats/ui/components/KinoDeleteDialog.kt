package com.mooncowpines.kinostats.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoGray

@Composable
fun KinoDeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "",
    message: String = ""
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Bold, color = KinoWhite) },
        text = { Text(text = message, color = KinoWhite.copy(alpha = 0.8f)) },
        containerColor = KinoGray,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = KinoWhite)
            }
        }
    )
}