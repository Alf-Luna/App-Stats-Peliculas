package com.mooncowpines.kinostats.ui.screens.home

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoPosterCard
import com.mooncowpines.kinostats.ui.components.KinoLastSeenCard
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.ui.components.KinoSearchBar
import com.mooncowpines.kinostats.ui.components.KinoSkeletonPosterCard
import com.mooncowpines.kinostats.ui.theme.KinoGray

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Long) -> Unit,
    onSearchSubmit: (String) -> Unit,
    shouldRefresh: Boolean = false,
    onRefreshDone: () -> Unit = {}
) {
    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            viewModel.loadHomeData()
            onRefreshDone()
        }
    }

    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is HomeScreenState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = KinoYellow)
            }
        }
        is HomeScreenState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = currentState.message, color = KinoWhite)
            }
        }
        is HomeScreenState.Success -> {
            HomeContent(
                state = currentState,
                onMovieClick = onMovieClick,
                onSearchSubmit = onSearchSubmit,
                modifier = modifier
            )
        }
    }

    
}
@Composable
fun HomeContent(
    state: HomeScreenState.Success,
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onSearchSubmit: (String) -> Unit,
) {

    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        Spacer(modifier = Modifier.height(KinoSpacing.large))

        KinoSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearchSubmit = { onSearchSubmit(searchQuery) },
            modifier = Modifier.padding(horizontal = KinoSpacing.medium)
        )

        Spacer(modifier = Modifier.height(KinoSpacing.large))

        if (state.watchlistMovies.isEmpty() && state.justWatchedMovies.isEmpty() && state.lastSeenMovie == null) {

            WelcomeEmptyState()

        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start,

                ) {

                WatchlistSection(movieCards = state.watchlistMovies, onMovieClick = onMovieClick)

                Spacer(modifier.height(KinoSpacing.extraLarge))

                LastSeenSection(movieCard = state.lastSeenMovie, onMovieClick = onMovieClick)

                Spacer(modifier.height(KinoSpacing.extraLarge))

                JustWatchedSection(movieCards = state.justWatchedMovies, onMovieClick = onMovieClick)

                Spacer(modifier = Modifier.height(KinoSpacing.medium))
            }
        }
    }
}
@Composable
fun WatchlistSection(
    modifier: Modifier = Modifier,
    movieCards: List<MovieCard>,
    onMovieClick: (Long) -> Unit
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

        if (movieCards.isEmpty()) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(4) {
                    KinoSkeletonPosterCard()
                }
            }
        } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movieCards) { movieCard ->
                        KinoPosterCard(
                            movieCard = movieCard,
                            onClick = { id -> onMovieClick(id) }
                        )
                    }
                }
            }
    }
}

@Composable
fun LastSeenSection(
    modifier: Modifier = Modifier,
    movieCard: MovieCard?,
    onMovieClick: (Long) -> Unit
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

        if (movieCard == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 165.dp)
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No reviews yet. Use the search bar above to find a movie and share your thoughts!",
                    color = KinoGray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KinoLastSeenCard(
                    movieCard = movieCard,
                    onClick = { id -> onMovieClick(id) }
                )
            }
        }
    }
}

@Composable
fun JustWatchedSection(
    modifier: Modifier = Modifier,
    movieCards: List<MovieCard>,
    onMovieClick: (Long) -> Unit
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

        if (movieCards.isEmpty()) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(4) {
                    KinoSkeletonPosterCard()
                }
            }
        } else {

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movieCards) { movieCards ->
                        KinoPosterCard(
                            movieCard = movieCards,
                            onClick = { id -> onMovieClick(id) }
                        )
                    }
                }
            }
    }
}

@Composable
fun WelcomeEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(KinoSpacing.large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.ConfirmationNumber,
            contentDescription = null,
            tint = KinoYellow,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(KinoSpacing.medium))

        Text(
            text = "Welcome to KinoStats!",
            color = KinoWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(KinoSpacing.small))

        Text(
            text = "Use the search bar above to find your first movie and start building your lists!",
            color = KinoGray,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

