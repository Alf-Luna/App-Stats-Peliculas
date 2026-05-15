package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mooncowpines.kinostats.ui.theme.KinoGray
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray

@Composable
fun KinoFallBackCoverCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(KinoLighterGray),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.ImageNotSupported,
            contentDescription = "No image available",
            tint = KinoGray,
            modifier = Modifier.size(48.dp)
        )
    }
}