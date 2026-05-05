package com.mooncowpines.kinostats.ui.screens.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.mooncowpines.kinostats.ui.components.KinoLastSeenCard
import com.mooncowpines.kinostats.ui.components.KinoTimeSummaryRow
import com.mooncowpines.kinostats.ui.components.KinoTopListColumn
import com.mooncowpines.kinostats.ui.components.KinoWeeklyBarChart
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.theme.KinoWhite


@Composable
fun StatsScreen(
    viewModel: StatsScreenViewModel = viewModel(),
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = KinoYellow)
        }
    } else {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            Column {
                Text(
                    text = "Last seen...",
                    color = KinoWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.width(150.dp),
                    color = KinoYellow,
                    thickness = 2.dp
                )
                Spacer(modifier = Modifier.height(KinoSpacing.mediumSmall))

                state.lastSeenMovie?.let { movie ->
                    KinoLastSeenCard(
                        movie = movie,
                        onClick = { id -> onMovieClick(id) }
                    )
                } ?: run {
                    Text("Aún no tienes actividad.", color = KinoWhite.copy(alpha = 0.7f))
                }
            }

            Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Statistics",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = KinoWhite,
                    modifier = Modifier.padding(bottom = KinoSpacing.medium)
                )
            }

            Text(
                text = "Time Watched this Year",
                fontSize = 18.sp,
                color = KinoWhite.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = KinoSpacing.medium)
            )

            KinoWeeklyBarChart(
                data = state.weeklyWatchData,
                barColor = KinoYellow
            )

            Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

            KinoTimeSummaryRow(label = "Today", value = state.todayWatchTime)
            Spacer(modifier = Modifier.height(KinoSpacing.small))
            KinoTimeSummaryRow(label = "Last 7 days", value = state.last7DaysWatchTime)

            Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                KinoTopListColumn(
                    title = "Top directors",
                    items = state.topDirectors,
                    modifier = Modifier.weight(1f)
                )
                KinoTopListColumn(
                    title = "Top genres",
                    items = state.topGenres,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

            Column {
                Text(
                    text = "Most viewed film",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KinoWhite
                )
                Text(
                    text = "${state.mostViewedFilm} - Viewed ${state.mostViewedCount} times",
                    fontSize = 16.sp,
                    color = KinoWhite.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = KinoSpacing.small)
                )
            }

            Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))
        }
    }
}