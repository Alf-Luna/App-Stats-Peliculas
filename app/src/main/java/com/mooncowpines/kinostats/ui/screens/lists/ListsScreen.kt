package com.mooncowpines.kinostats.ui.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.ui.components.KinoDeleteDialog
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListsScreen(
    modifier: Modifier = Modifier,
    viewModel: ListsScreenViewModel = hiltViewModel(),
    onNavigateToListDetail: (Long) -> Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.loadLists()
    }
    val state by viewModel.state.collectAsState()

    state.listToDelete?.let { list ->
        KinoDeleteDialog(
            title = "Delete List",
            message = "Are you sure you want to delete '${list.name}'? All movies inside will be removed from this list.",
            onDismiss = { viewModel.onDismissDeleteDialog() },
            onConfirm = { viewModel.deleteList() }
        )
    }

    if (state.showCreateDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.setShowCreateDialog(false) },
            containerColor = KinoLighterGray,
            title = { Text(text = "Create New List", color = KinoYellow, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Enter the name for your new list:", color = KinoWhite, modifier = Modifier.padding(bottom = 8.dp))
                    OutlinedTextField(
                        value = state.newListName,
                        onValueChange = { viewModel.onNewListNameChange(it) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = KinoWhite,
                            unfocusedTextColor = KinoWhite,
                            focusedBorderColor = KinoYellow,
                            cursorColor = KinoYellow
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.createList() },
                    enabled = state.newListName.isNotBlank() && !state.isCreating
                ) {
                    if (state.isCreating) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = KinoYellow)
                    } else {
                        Text("Create", color = KinoYellow)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setShowCreateDialog(false) }) {
                    Text("Cancel", color = KinoWhite.copy(alpha = 0.7f))
                }
            }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.setShowCreateDialog(true) },
                containerColor = KinoYellow,
                contentColor = KinoBlack
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Create List")
            }
        }
    ) { paddingValues ->
        ListsContent(
            state = state,
            onNavigateToListDetail = onNavigateToListDetail,
            onDeleteClick = { list -> viewModel.onConfirmDeleteIntent(list) },
            onRefresh = { viewModel.loadLists() },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ListsContent(
    state: ListsScreenState,
    onNavigateToListDetail: (Long) -> Unit,
    onDeleteClick: (MovieList) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                onRefresh()

                delay(1000)

                isRefreshing = false
            }
        },
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(KinoBlack)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "My Lists",
                color = KinoYellow,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
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
                        Text(
                            text = "You don't have any lists yet.",
                            color = KinoWhite.copy(alpha = 0.7f)
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 88.dp)
                    ) {
                        items(state.lists) { list ->
                            ListCard(
                                movieList = list,
                                onClick = { onNavigateToListDetail(list.id) },
                                onDeleteClick = { onDeleteClick(list) }
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ListCard(
    movieList: MovieList,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
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
            if (!movieList.isWatchList) {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete list",
                    tint = Color.Red.copy(alpha = 0.8f)
                )
            }
            }
        }
    }
}