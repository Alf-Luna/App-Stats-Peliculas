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
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.ui.components.KinoCountryPieChart

import com.mooncowpines.kinostats.ui.components.KinoDateSelector
import com.mooncowpines.kinostats.ui.components.KinoDecadeLineChart
import com.mooncowpines.kinostats.ui.components.KinoLastSeenCard
import com.mooncowpines.kinostats.ui.components.KinoGenreBarChart
import com.mooncowpines.kinostats.ui.components.KinoRatingBarChart
import com.mooncowpines.kinostats.ui.components.KinoTimeSummaryRow
import com.mooncowpines.kinostats.ui.components.KinoTopList
import com.mooncowpines.kinostats.ui.components.KinoTopListColumn
import com.mooncowpines.kinostats.ui.components.KinoYearlyBarChart
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.theme.KinoWhite

@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatsScreenViewModel = hiltViewModel(),
    onMovieClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()

        StatsContent(
            state = state,
            onMovieClick = onMovieClick,
            modifier = modifier,
            onFilterChange = { year, month -> viewModel.updateFilter(year, month) },
        )
}

@Composable
fun StatsContent(
    state: StatsScreenState,
    onMovieClick: (Long) -> Unit,
    onFilterChange: (Int?, Int?) -> Unit,
    modifier: Modifier = Modifier
) {
        val scrollState = rememberScrollState()

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
                    Text("You don't have any activity yet!", color = KinoWhite.copy(alpha = 0.7f))
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

            KinoDateSelector(
                selectedYear = state.selectedYear,
                selectedMonth = state.selectedMonth,
                onFilterChange = onFilterChange
            )

            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            if (state.selectedYear == null) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Select a year to check your stats!",
                        color = KinoWhite.copy(alpha = 0.5f),
                        fontSize = 16.sp
                    )
                }
            } else if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = KinoYellow)
                }
            } else {

                state.stats?.let { statsData ->

                    Text(
                        text = "Time Watched this Year",
                        fontSize = 18.sp,
                        color = KinoWhite.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = KinoSpacing.small)
                    )

                    KinoYearlyBarChart(data = statsData.weeklyWatchData, barColor = KinoYellow)

                    Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

                    KinoTimeSummaryRow(label = "Today", value = statsData.todayWatchTime)

                    Spacer(modifier = Modifier.height(KinoSpacing.small))

                    KinoTimeSummaryRow(label = "Last 7 days", value = statsData.last7DaysWatchTime)

                    Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

                    KinoGenreBarChart(genres = statsData.genres, maxMinutes = state.genreMaxMinutes)

                    Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

                    Text("Countries", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)

                    KinoCountryPieChart(countries = statsData.countries)

                    Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

                    Text("Ratings Distribution", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    KinoRatingBarChart(ratings = statsData.ratings)

                    Spacer(modifier = Modifier.height(32.dp))

                    Text("Movies by Decade", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    KinoDecadeLineChart(decades = statsData.decades)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        KinoTopList(
                            title = "Top Actors",
                            items = statsData.topActors.sortedByDescending { it.value }.take(5),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        KinoTopList(
                            title = "Top Directors",
                            items = statsData.topActors.sortedByDescending { it.value }.take(5),
                            modifier = Modifier.weight(1f)
                        )
                    }

                } ?: run {
                    if (state.errorMsg != null) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.errorMsg, color = Color.Red)
                        }
                }

                Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))
            }
        }
    }
}
