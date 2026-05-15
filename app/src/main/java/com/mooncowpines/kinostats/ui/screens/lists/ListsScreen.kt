package com.mooncowpines.kinostats.ui.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun ListsScreen(
    modifier: Modifier = Modifier,
    viewModel: ListsScreenViewModel = hiltViewModel(),
    onNavigateToListDetail: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()

    ListsContent(
        state = state,
        onNavigateToListDetail = onNavigateToListDetail,
        modifier = modifier
    )
}

@Composable
fun ListsContent(
    state: ListsScreenState,
    onNavigateToListDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KinoBlack)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "My Lists",
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
                    Text(text = state.errorMsg, color = Color.Red)
                }
            }
            state.lists.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "You don't have any lists yet.", color = KinoWhite.copy(alpha = 0.7f))
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(state.lists) { list ->
                        ListCard(
                            movieList = list,
                            onClick = { onNavigateToListDetail(list.id) }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ListCard(
    movieList: MovieList,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = KinoLighterGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = movieList.name,
                    color = KinoWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${movieList.movieCount} movies",
                    color = KinoWhite.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "View list",
                tint = KinoYellow
            )
        }
    }
}