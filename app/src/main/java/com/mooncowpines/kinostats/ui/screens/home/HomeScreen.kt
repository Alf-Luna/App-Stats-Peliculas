package com.mooncowpines.kinostats.ui.screens.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mooncowpines.kinostats.data.Movie
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.mooncowpines.kinostats.data.FakeMovieApi
import com.mooncowpines.kinostats.ui.components.KinoPosterCard
import com.mooncowpines.kinostats.ui.components.KinoLastSeenCard

@Composable
fun HomeScreen(
    movies: List<Movie>,
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier

) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    )
    {
        Text(
            text = "Watchlist...",
            color = KinoWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp, top = 40.dp, bottom = 4.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(150.dp),
            color = KinoYellow,
            thickness = 2.dp
        )

        Spacer(modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                KinoPosterCard(
                    movie = movie,
                    onClick = { id -> onMovieClick(id)}
                )
            }
        }

        Spacer(modifier.height(16.dp))

        Text(
            text = "Last seen...",
            color = KinoWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp, top = 40.dp, bottom = 4.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(150.dp),
            color = KinoYellow,
            thickness = 2.dp
        )

        Spacer(modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KinoLastSeenCard(
                movie = movie,
                onClick = { id -> onMovieClick(id)}
            )
        }

    }
}

