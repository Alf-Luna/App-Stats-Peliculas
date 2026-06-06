package com.mooncowpines.kinostats.ui.screens.wrapped

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale
import com.mooncowpines.kinostats.domain.model.UserStats
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.utils.shareWrappedSlide
import kotlinx.coroutines.launch

@Composable
fun WrappedScreen(
    modifier: Modifier = Modifier,
    viewModel: WrappedViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    if (state.isLoading) {
        Box(modifier = modifier.fillMaxSize().background(KinoBlack), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = KinoYellow)
        }
    } else if (state.errorMsg != null) {
        Box(modifier = modifier.fillMaxSize().background(KinoBlack), contentAlignment = Alignment.Center) {
            Text(state.errorMsg!!, color = Color.Red)
        }
    } else {
        state.stats?.let { stats ->
            val periodText = if (viewModel.targetMonth != null) {
                val monthName = Month.of(viewModel.targetMonth)
                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                    .replaceFirstChar { it.uppercase() }

                "$monthName ${viewModel.targetYear}"
            } else {
                "${viewModel.targetYear}"
            }

            WrappedContent(
                stats = stats,
                periodText = periodText,
                onClose = onClose,
                modifier = modifier
            )
        }
    }
}

@Composable
fun WrappedContent(
    stats: UserStats,
    periodText: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentSlide by remember { mutableIntStateOf(0) }
    val totalSlides = 5
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(KinoBlack)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawContent()
                },
            contentAlignment = Alignment.Center
        ) {
            when (currentSlide) {
                0 -> WelcomeSlide(totalMovies = stats.totalMovies, period = periodText)
                1 -> TimeSlide(hours = stats.totalHours, minutes = stats.totalMinutes)
                2 -> CategorySlide(title = "Your Top Genre", value = stats.genres.firstOrNull()?.label ?: "Unknown")
                3 -> CategorySlide(title = "Your Top Director", value = stats.topDirectors.firstOrNull()?.label ?: "Unknown")
                4 -> EndSlide()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (i in 0 until totalSlides) {
                Box(modifier = Modifier.weight(1f).height(4.dp).background(if (i <= currentSlide) Color.White else Color.DarkGray))
            }
        }

        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxHeight().weight(0.3f).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                    if (currentSlide > 0) currentSlide--
                }
            )
            Box(
                modifier = Modifier.fillMaxHeight().weight(0.7f).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                    if (currentSlide < totalSlides - 1) currentSlide++ else onClose()
                }
            )
        }

        IconButton(
            onClick = {
                coroutineScope.launch {
                    val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
                    shareWrappedSlide(context, bitmap)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share Slide",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun WelcomeSlide(totalMovies: Int, period: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("During $period you watched", color = Color.Gray, fontSize = 20.sp)
        Text("$totalMovies", color = KinoYellow, fontSize = 80.sp, fontWeight = FontWeight.Bold)
        Text("Movies", color = Color.Gray, fontSize = 20.sp)
    }
}

@Composable
fun TimeSlide(hours: Int, minutes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Text("You spent", color = Color.Gray, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("${hours}h ${minutes}m", color = KinoYellow, fontSize = 60.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text("in front of the screen", color = Color.Gray, fontSize = 20.sp)
    }
}

@Composable
fun CategorySlide(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Text(title, color = Color.Gray, fontSize = 24.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Text(value, color = KinoYellow, fontSize = 36.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

@Composable
fun EndSlide() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Text("That was your", color = Color.Gray, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Kino Wrapped", color = KinoYellow, fontSize = 40.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))
        Text("Tap anywhere to exit", color = Color.DarkGray, fontSize = 16.sp)
    }
}