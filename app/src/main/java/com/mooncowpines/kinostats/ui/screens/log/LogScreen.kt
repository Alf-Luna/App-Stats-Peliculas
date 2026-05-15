package com.mooncowpines.kinostats.ui.screens.log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.ui.components.KinoErrorText
import com.mooncowpines.kinostats.ui.components.KinoRatingCard
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun LogScreen(
    modifier: Modifier = Modifier,
    viewModel: LogScreenViewModel = hiltViewModel(),
    onNavigateToLogDetail: (Long, Long) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LogContent(
        state = state,
        onNavigateToLogDetail = onNavigateToLogDetail,
        onDeleteClick = { logId -> viewModel.deleteLog(logId) },
        modifier = modifier
    )
}

@Composable
fun LogContent(
    state: LogScreenState,
    onNavigateToLogDetail: (Long, Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KinoBlack)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "My Logs",
            color = KinoYellow,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 32.dp)
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = KinoYellow)
                }
            }
            state.errorMsg != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    KinoErrorText(message = state.errorMsg)
                }
            }
            state.logs.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "You haven't logged any movies yet.",
                        color = KinoWhite.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(state.logs) { log ->
                        KinoRatingCard(
                            log = log,
                            onClick = { onNavigateToLogDetail(log.movieId, log.id) },
                            onDeleteClick = { onDeleteClick(log.id) }
                        )
                    }
                }
            }
        }
    }
}