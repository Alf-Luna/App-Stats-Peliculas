package com.mooncowpines.kinostats.ui.screens.logDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.ui.components.KinoCalendar
import com.mooncowpines.kinostats.ui.components.RatingDropdownSelector
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoGray
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mooncowpines.kinostats.ui.components.KinoErrorText
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun LogDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: LogDetailScreenViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.success) {
        if (state.success) onNavigateBack()
    }

    if (state.isLoadingMovie) {
        Box(modifier = modifier.fillMaxSize().background(KinoBlack), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = KinoYellow)
        }
    } else {
        state.movie?.let { safeMovie ->
            LogDetailContent(
                state = state,
                movie = safeMovie,
                onNavigateBack = onNavigateBack,
                onSaveReview = { viewModel.saveReview() },
                onRatingChange = { viewModel.onRatingChange(it) },
                onReviewTextChange = { viewModel.logTextChange(it) },
                onShowCalendar = { viewModel.setShowCalendar(it) },
                onDateSelected = { viewModel.onWatchDateSelected(it) },
                modifier = modifier
            )
        } ?: run {
        Box(modifier = modifier.fillMaxSize().background(KinoBlack), contentAlignment = Alignment.Center) {
            Text(state.errorMsg ?: "Error al cargar la película", color = Color.Red)
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailContent(
    state: LogDetailScreenState,
    movie: Movie,
    onNavigateBack: () -> Unit,
    onSaveReview: () -> Unit,
    onRatingChange: (Float) -> Unit,
    onReviewTextChange: (String) -> Unit,
    onShowCalendar: (Boolean) -> Unit,
    onDateSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KinoBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("I watched...", color = Color.Gray, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = KinoWhite)
                    }
                },
                actions = {
                    TextButton(
                        onClick = onSaveReview,
                        enabled = !state.isSubmitting
                    ) {
                        Text(
                            text = if (state.isSubmitting) "Saving..." else "Save",
                            color = KinoWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KinoBlack)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            state.errorMsg?.let { KinoErrorText(it) }

            Spacer(modifier = Modifier.height(8.dp))
            MovieHeaderInfo(movie)

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Specify the date you watched it", color = KinoGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = state.formattedWatchDate,
                color = if (state.watchDate == null) Color.DarkGray else KinoWhite,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onShowCalendar(true)
                    }
                    .padding(vertical = 8.dp)
            )

            state.watchDateError?.let { KinoErrorText(it) }

            if (state.showCalendar) {
                KinoCalendar(
                    onDismissRequest = { onShowCalendar(false) },
                    onDateSelected = { timestamp -> onDateSelected(timestamp)}
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Rating", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))

            RatingDropdownSelector(
                rating = state.rating,
                onRatingChange = onRatingChange
            )

            state.ratingError?.let { KinoErrorText(it) }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)


            Text(text = "Review", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(top = 16.dp))

            TextField(
                value = state.logText,
                onValueChange = onReviewTextChange,
                modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp),
                placeholder = { Text("Write a review...", color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedTextColor = KinoWhite,
                    unfocusedTextColor = KinoWhite,
                    cursorColor = KinoYellow,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
            )

            state.logTextError?.let { KinoErrorText(it) }
        }
    }
}

@Composable
private fun MovieHeaderInfo(movie: Movie) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.width(64.dp).height(96.dp)
                .clip(RoundedCornerShape(4.dp)).background(Color.DarkGray)
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = "Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = movie.title, color = KinoWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.releaseDate, color = Color.Gray, fontSize = 16.sp)
        }
    }
}