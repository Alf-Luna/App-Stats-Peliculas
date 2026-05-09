package com.mooncowpines.kinostats.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mooncowpines.kinostats.domain.model.Movie
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
import androidx.compose.foundation.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoGray
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray

@Composable
fun KinoLastSeenCard(
    movie: Movie,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card (modifier = modifier
        .fillMaxWidth(1.0f)
        .wrapContentHeight()
        .clickable { onClick(movie.id)},
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(KinoLighterGray),

    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = movie.posterUrl),
                contentDescription = movie.title,
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
                    text = movie.title,
                    color = KinoWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)

                Text(movie.releaseYear, color = KinoGray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(KinoSpacing.medium))

            }
        }
    }
}
