package com.mooncowpines.kinostats.ui.screens.home

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoPosterCard
import com.mooncowpines.kinostats.ui.components.KinoLastSeenCard
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.ui.components.KinoSearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
    onSearchSubmit: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is HomeScreenState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = KinoYellow)
            }
        }
        is HomeScreenState.Error -> {
            val errorMessage = (state as HomeScreenState.Error).message
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage, color = KinoWhite)
            }
        }
        is HomeScreenState.Success -> {
            val successState = state as HomeScreenState.Success
            HomeContent(
                movies = successState.movies,
                movie = successState.lastSeenMovie,
                onMovieClick = onMovieClick,
                modifier = modifier,
                onSearchSubmit = onSearchSubmit
            )
        }
    }

    
}
@Composable
fun HomeContent(
    movies: List<Movie>,
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onSearchSubmit: (String) -> Unit,
) {

    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,

    ) {
        Spacer(modifier.height(KinoSpacing.extraSmall))

        KinoSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearchSubmit = { onSearchSubmit(searchQuery) },
            modifier = Modifier.padding(horizontal = KinoSpacing.medium)
        )

        Spacer(modifier.height(KinoSpacing.medium))

        WatchlistSection(movies = movies, onMovieClick = onMovieClick)

        Spacer(modifier.height(KinoSpacing.extraLarge))

        LastSeenSection(movie = movie, onMovieClick = onMovieClick)

        Spacer(modifier.height(KinoSpacing.extraLarge))

        JustWatchedSection(movies = movies, onMovieClick = onMovieClick)
    }
}

@Composable
fun WatchlistSection(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    Column {
        Text(
            text = "Watchlist...",
            color = KinoWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(150.dp),
            color = KinoYellow,
            thickness = 2.dp
        )

        Spacer(modifier.height(KinoSpacing.mediumSmall))

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
    }
}

@Composable
fun LastSeenSection(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (Int) -> Unit
) {
    Column {
        Text(
            text = "Last seen...",
            color = KinoWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(150.dp),
            color = KinoYellow,
            thickness = 2.dp
        )

        Spacer(modifier.height(KinoSpacing.mediumSmall))

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

@Composable
fun JustWatchedSection(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    Column {
        Text(
            text = "Just Watched...",
            color = KinoWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(150.dp),
            color = KinoYellow,
            thickness = 2.dp
        )

        Spacer(modifier.height(KinoSpacing.mediumSmall))

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
    }
}

