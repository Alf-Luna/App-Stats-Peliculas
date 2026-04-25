package com.mooncowpines.kinostats.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mooncowpines.kinostats.data.Movie
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
import com.mooncowpines.kinostats.ui.theme.KinoYellow
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
import androidx.compose.ui.tooling.preview.Preview

import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.data.FakeMovieApi
import com.mooncowpines.kinostats.ui.theme.KinoGray

@Composable
fun KinoLastSeenCard(
    movie: Movie,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card (modifier = modifier
        .fillMaxWidth(1.0f)
        .wrapContentHeight()
        .clickable { onClick(movie.id)},
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = movie.thumbnailResId),
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

                Text(movie.year, color = KinoGray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(KinoSpacing.medium))
                Text("★ ${movie.rating}", color = KinoYellow, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212, widthDp = 150, heightDp = 150, name = "KinoLastSeenCard")
@Composable
fun KinoLastSeenCardPreview() {

    val mockMovie = FakeMovieApi.getMovieByIdSync(5) ?: FakeMovieApi.allMoviesSync.first()

    KinoLastSeenCard(
        movie = mockMovie,
        onClick = { }
    )
}
