package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray


@Composable
fun KinoPosterCard(
    movieCard: MovieCard,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
            .width(110.dp)
            .aspectRatio(2f/3f)
            .clickable { onClick(movieCard.id)},
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movieCard.posterUrl)
                .crossfade(true)
                .crossfade(500)
                .build(),
            contentDescription = movieCard.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        ) {
            val state = painter.state
            when {
                movieCard.posterUrl.isEmpty() || state is AsyncImagePainter.State.Error -> {
                    KinoFallBackCoverCard(modifier = Modifier.fillMaxSize())
                }

                state is AsyncImagePainter.State.Loading -> {
                    Box(modifier = Modifier.fillMaxSize().background(KinoLighterGray))
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

    }
}


