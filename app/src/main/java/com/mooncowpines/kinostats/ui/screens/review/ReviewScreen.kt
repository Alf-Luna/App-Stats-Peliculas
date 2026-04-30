package com.mooncowpines.kinostats.ui.screens.review

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mooncowpines.kinostats.data.Movie
import com.mooncowpines.kinostats.ui.components.RatingDropdownSelector
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    movie: Movie,
    viewModel: ReviewScreenViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.success) {
        if (state.success) {
            onNavigateBack()
        }
    }

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
                        onClick = { viewModel.saveReview(movieId = movie.id) },
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
            if (state.errorMsg != null) {
                Text(
                    text = state.errorMsg!!,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            MovieHeaderInfo(movie)

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))


            Text(text = "Specify the date you watched it", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
            val dateText = state.watchDate?.format(dateFormatter) ?: "Tap to select a date..."

            Text(
                text = dateText,
                color = if (state.watchDate == null) Color.DarkGray else KinoWhite,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        //Hacer algo pa la fecha
                        viewModel.onWatchDateChange(LocalDate.now())
                    }
                    .padding(vertical = 8.dp)
            )

            if (state.watchDateError != null) {
                Text(text = state.watchDateError!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Rating", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))

            RatingDropdownSelector(
                rating = state.rating,
                onRatingChange = { viewModel.onRatingChange(it) }
            )

            if (state.ratingError != null) {
                Text(text = state.ratingError!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)


            Text(text = "Review", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(top = 16.dp))

            TextField(
                value = state.reviewText,
                onValueChange = { viewModel.reviewTextChange(it) },
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

            if (state.reviewTextError != null) {
                Text(text = state.reviewTextError!!, color = Color.Red, fontSize = 12.sp)
            }
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
            Image(
                painter = painterResource(id = movie.posterUrl),
                contentDescription = "Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = movie.title, color = KinoWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.releaseYear, color = Color.Gray, fontSize = 16.sp)
        }
    }
}