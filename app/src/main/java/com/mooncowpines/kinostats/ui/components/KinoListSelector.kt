package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.domain.model.MovieList
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KinoListSelectionSheet(
    lists: List<MovieList>,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onListSelected: (MovieList) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = KinoBlack,
        contentColor = KinoWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Save to...",
                color = KinoYellow,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = KinoYellow)
                }
            } else if (lists.isEmpty()) {
                Text(
                    text = "You don't have any lists yet.",
                    color = KinoWhite.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                LazyColumn {
                    items(lists) { list ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onListSelected(list) }
                                .padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = list.name, fontSize = 16.sp, color = KinoWhite)
                            Text(
                                text = "${list.movieCount} movies",
                                fontSize = 14.sp,
                                color = KinoWhite.copy(alpha = 0.5f)
                            )
                        }
                        HorizontalDivider(color = KinoWhite.copy(alpha = 0.1f))
                    }
                }
            }
        }
    }
}