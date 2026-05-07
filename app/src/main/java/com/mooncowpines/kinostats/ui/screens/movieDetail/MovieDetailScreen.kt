package com.mooncowpines.kinostats.ui.screens.movieDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.ui.components.KinoFAB
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToLog: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is MovieDetailState.Loading -> {
            Box(modifier = modifier.fillMaxSize().background(KinoBlack), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = KinoYellow)
            }
        }
        is MovieDetailState.Error -> {
            val errorMessage = (state as MovieDetailState.Error).message
            Box(modifier = modifier.fillMaxSize().background(KinoBlack), contentAlignment = Alignment.Center) {
                Text(errorMessage, color = Color.Red)
            }
        }
        is MovieDetailState.Success -> {
            val movie = (state as MovieDetailState.Success).movie
            MovieDetailContent(
                movie = movie,
                onNavigateBack = onNavigateBack,
                onNavigateToLog = onNavigateToLog,
                modifier = modifier
            )
        }
    }
}
@Composable
fun MovieDetailContent(
    movie: Movie,
    onNavigateBack: () -> Unit,
    onNavigateToLog: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KinoBlack,
        floatingActionButton = {
            KinoFAB(onClick = { onNavigateToLog(movie.id) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(KinoBlack)
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {

                Image(
                    painter = painterResource(id = movie.posterUrl),
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.TopCenter)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, KinoBlack),
                                startY = 50f
                            )
                        )
                )

                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .padding(top = 32.dp, start = 8.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = KinoWhite)
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Image(
                        painter = painterResource(id = movie.posterUrl),
                        contentDescription = "Portada",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(105.dp)
                            .height(155.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(
                            text = movie.title,
                            color = KinoWhite,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${movie.releaseYear}  •  ${movie.duration}",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /*Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                if (movie.tagLine.isNotEmpty()) {
                    Text(
                        text = movie.tagLine.uppercase(),
                        color = KinoYellow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = movie.synopsis,
                    color = KinoWhite,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            }*/

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.DarkGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Ficha Técnica",
                    color = KinoWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                DetailRow(label = "Director", value = movie.director)
                DetailRow(label = "País", value = movie.originCountry)
                DetailRow(label = "Cinematógrafo", value = movie.cinematographer)
                DetailRow(label = "Productora", value = movie.productionCompany)
                DetailRow(label = "Géneros", value = movie.genres.joinToString(separator = ", "))
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.DarkGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Reparto Principal",
                    color = KinoWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.actors.joinToString(separator = ", "),
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
            verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.35f)
        )
        Text(
            text = value,
            color = KinoWhite,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.65f)
        )
    }
}