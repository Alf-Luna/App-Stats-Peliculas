package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.ui.theme.KinoWhite

@Composable
fun MovieHeaderInfo(movie: Movie) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.width(64.dp).height(96.dp)
                .clip(RoundedCornerShape(4.dp)).background(Color.DarkGray)
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = "Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = movie.title, color = KinoWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            val yearText = movie.releaseDate?.year?.toString() ?: "Unknown year"
            Text(text = yearText, color = Color.Gray, fontSize = 16.sp)        }
    }
}