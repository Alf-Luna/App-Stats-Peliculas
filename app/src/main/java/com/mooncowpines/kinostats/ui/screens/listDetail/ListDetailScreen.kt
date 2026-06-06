package com.mooncowpines.kinostats.ui.screens.listDetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.ui.components.KinoDeleteDialog
import com.mooncowpines.kinostats.ui.components.MovieInListCard
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ListDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMovieClick: (Long) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    when (state) {
        is ListDetailState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize().background(KinoBlack),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = KinoYellow)
            }
        }

        is ListDetailState.Error -> {
            val errorMessage = (state as ListDetailState.Error).message
            Box(
                modifier = modifier.fillMaxSize().background(KinoBlack),
                contentAlignment = Alignment.Center
            ) {
                Text(errorMessage, color = Color.Red)
            }
        }

        is ListDetailState.Success -> {
            val successState = state as ListDetailState.Success

            LaunchedEffect(successState.success) {
                if (successState.success) {
                    Toast.makeText(context, "Movie removed from list", Toast.LENGTH_SHORT).show()
                    viewModel.onActionDone()
                }
            }

            successState.movieToRemove?.let { movie ->
                KinoDeleteDialog(
                    title = "Remove Movie",
                    message = "Are you sure you want to remove '${movie.title}' from this list?",
                    onDismiss = { viewModel.onDismissRemoveDialog() },
                    onConfirm = { viewModel.removeMovieFromList() }
                )
            }

            ListDetailContent(
                successState = successState,
                onNavigateBack = onNavigateBack,
                onMovieClick = onMovieClick,
                onDeleteMovieIntent = { movie -> viewModel.onConfirmRemoveIntent(movie) },
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailContent(
    successState: ListDetailState.Success,
    onNavigateBack: () -> Unit,
    onMovieClick: (Long) -> Unit,
    onDeleteMovieIntent: (MovieCard) -> Unit,
    modifier: Modifier = Modifier
) {
    val movies = successState.movieList.movies

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KinoBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = successState.movieList.name,
                        color = KinoYellow,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = KinoWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KinoBlack)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (movies.isEmpty()) {
                Text(
                    text = "This list is empty",
                    color = KinoWhite.copy(alpha = 0.5f),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movies) { movie ->
                        MovieInListCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            onDelete = { onDeleteMovieIntent(movie) }
                        )
                    }
                }
            }
        }
    }

}

