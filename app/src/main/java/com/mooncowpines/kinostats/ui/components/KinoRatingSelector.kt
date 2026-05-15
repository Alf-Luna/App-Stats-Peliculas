package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun RatingDropdownSelector(
    rating: Float,
    onRatingChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray.copy(alpha = 0.2f))
            .clickable { expanded = true }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (rating == 0f) {
                Text("Tap to rate...", color = Color.Gray, fontSize = 16.sp)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = rating.toString(),
                        color = KinoYellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(45.dp)
                    )
                    StaticStars(rating = rating)
                }
            }
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir menú", tint = Color.Gray)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1F252B))
        ) {
            val options = listOf(
                0f, 0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f
            )

            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (option == 0f) "No rating" else option.toString(),
                                color = if (option == rating) KinoYellow else KinoWhite,
                                modifier = Modifier.width(70.dp)
                            )
                            if (option > 0f) StaticStars(rating = option)
                        }
                    },
                    onClick = {
                        onRatingChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun StaticStars(rating: Float) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        for (i in 1..5) {
            val fillAmount = (rating - (i - 1)).coerceIn(0f, 1f)
            Box(modifier = Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.StarBorder, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.fillMaxSize())
                if (fillAmount > 0f) {
                    Box(modifier = Modifier.fillMaxSize().clip(FractionalClip(fillAmount))) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = KinoYellow, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

class FractionalClip(val fraction: Float) : androidx.compose.ui.graphics.Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        return androidx.compose.ui.graphics.Outline.Rectangle(
            androidx.compose.ui.geometry.Rect(0f, 0f, size.width * fraction, size.height)
        )
    }
}