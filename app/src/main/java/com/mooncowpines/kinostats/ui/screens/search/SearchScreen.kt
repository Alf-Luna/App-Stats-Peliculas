package com.mooncowpines.kinostats.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import com.mooncowpines.kinostats.ui.components.KinoPosterCard
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun SearchScreen(
    onMovieClick: (Long) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    SearchContent(
        state = state,
        onMovieClick = onMovieClick,
        onBackClick = onBackClick,
        onQueryChange = { viewModel.updateQuery(it) },
        onSearchSubmit = { viewModel.submitSearch() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    state: SearchScreenState,
    onMovieClick: (Long) -> Unit,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(KinoSpacing.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = KinoWhite
                )
            }

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                placeholder = { Text("Search movies, actors...", color = Color.Gray) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchSubmit()
                        focusManager.clearFocus()
                    }                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = KinoWhite,
                    unfocusedTextColor = KinoWhite,
                    focusedBorderColor = KinoYellow,
                    unfocusedBorderColor = Color.DarkGray
                ),
                trailingIcon = {
                    if (state.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear", tint = KinoWhite)
                        }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(KinoSpacing.medium))

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        color = KinoYellow,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.errorMsg != null -> {
                    Text(
                        text = state.errorMsg,
                        color = KinoWhite.copy(alpha = 0.6f),
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.results.isNotEmpty() -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 32.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                text = "Results for '${state.searchQuery}'",
                                color = KinoWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        items(state.results) { movie ->
                            KinoPosterCard(
                                movieCard = movie,
                                onClick = { onMovieClick(movie.id) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}