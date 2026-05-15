package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray

@Composable
fun KinoSkeletonPosterCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(110.dp)
            .aspectRatio(2f / 3f)
            .clip(MaterialTheme.shapes.small)
            .background(KinoLighterGray.copy(alpha = 0.3f))
    )
}