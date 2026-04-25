package com.mooncowpines.kinostats.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable


private val KinoColorScheme = darkColorScheme(
    primary = KinoYellow,
    onPrimary = KinoWhite,
    background = KinoBlack,
    onBackground = KinoWhite,
    surface = KinoDarkGray,
    onSurface = KinoWhite,
)

@Composable
fun KinoStatsTheme(content: @Composable () -> Unit) {

    val colorScheme =  KinoColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}
