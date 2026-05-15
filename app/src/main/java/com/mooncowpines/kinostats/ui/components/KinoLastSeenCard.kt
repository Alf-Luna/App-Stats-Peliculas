package com.mooncowpines.kinostats.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.mooncowpines.kinostats.domain.model.MovieCard

import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoGray
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray

@Composable
fun KinoLastSeenCard(
    movieCard: MovieCard,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card (modifier = modifier
        .fillMaxWidth(1.0f)
        .wrapContentHeight()
        .clickable { onClick(movieCard.id)},
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(KinoLighterGray),

    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movieCard.posterUrl,
                contentDescription = movieCard.title,
                modifier = Modifier
                    .fillMaxWidth(0.30f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(KinoSpacing.mediumSmall),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(KinoSpacing.medium))
            Column {
                Text(
                    text = movieCard.title,
                    color = KinoWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)

                if (!movieCard.releaseDate.isNullOrEmpty()) {

                    Text(movieCard.releaseDate, color = KinoGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(KinoSpacing.medium))
                }

                if (!movieCard.director.isNullOrEmpty()) {

                    Text(movieCard.director, color = KinoGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(KinoSpacing.medium))
                }

                if (movieCard.duration != null && movieCard.duration > 0) {

                    Text(text = "${movieCard.duration} min", color = KinoGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(KinoSpacing.medium))
                }

            }
        }
    }
}
