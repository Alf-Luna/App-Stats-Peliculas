package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.ui.theme.KinoSpacing

@Composable
fun KinoErrorText(message: String) {
    Text(
        text = message,
        color = Color.Red,
        fontSize = 14.sp,
        modifier = Modifier.padding(KinoSpacing.small)
    )
}