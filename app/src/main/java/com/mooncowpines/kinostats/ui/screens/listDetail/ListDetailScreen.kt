package com.mooncowpines.kinostats.ui.screens.listDetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.ui.components.KinoFallBackCoverCard
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ListDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMovieClick: (Long) -> Unit,
    onDataModified: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
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
            ListDetailContent(
                successState = successState,
                onNavigateBack = onNavigateBack,
                onMovieClick = onMovieClick,
                onDeleteMovie = { movieId -> viewModel.removeMovieFromList(movieId) },
                onClearMessage = { viewModel.clearMessage() },
                onDataModified = onDataModified,
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
    onDeleteMovie: (Long) -> Unit,
    onClearMessage: () -> Unit,
    onDataModified: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val movies = successState.movieList.movies

    LaunchedEffect(successState.actionMessage) {
        successState.actionMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

            onDataModified()
            onClearMessage()
        }
    }

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
                            onDelete = { onDeleteMovie(movie.id) }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun MovieInListCard(
    movie: MovieCard,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = KinoLighterGray)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp, 75.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                val state = painter.state
                when {
                    movie.posterUrl.isEmpty() || state is AsyncImagePainter.State.Error -> {
                        KinoFallBackCoverCard(modifier = Modifier.fillMaxSize())
                    }
                    state is AsyncImagePainter.State.Loading -> {
                        Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
                    }
                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = movie.title,
                color = KinoWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Gray)
            }
        }
    }
}