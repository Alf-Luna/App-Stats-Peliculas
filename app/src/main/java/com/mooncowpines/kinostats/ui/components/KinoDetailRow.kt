package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.ui.theme.KinoWhite

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp,
            maxLines = 1,
            modifier = Modifier.weight(0.35f)
        )
        Text(
            text = value,
            color = KinoWhite,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.65f)
        )
    }
}