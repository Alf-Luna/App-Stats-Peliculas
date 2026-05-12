package com.mooncowpines.kinostats.ui.screens.listDetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.domain.model.MovieCard
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    viewModel: ListDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMovieClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is ListDetailState.Success) {
            val success = state as ListDetailState.Success
            success.actionMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.clearMessage()
            }
        }
    }

    Scaffold(
        containerColor = KinoBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (state is ListDetailState.Success)
                            (state as ListDetailState.Success).movieList.name
                        else "List Detail",
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
            when (val currentState = state) {
                is ListDetailState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = KinoYellow)
                }
                is ListDetailState.Error -> {
                    Text(currentState.message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
                is ListDetailState.Success -> {
                    val movies = currentState.movieList.movies

                    if (movies.isEmpty()) {
                        Text(
                            "This list is empty",
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
                                    onDelete = { viewModel.removeMovieFromList(movie.id) }
                                )
                            }
                        }
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
            Box(
                modifier = Modifier.size(50.dp, 75.dp).background(Color.DarkGray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = movie.name,
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