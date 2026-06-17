package com.mooncowpines.kinostats.ui.screens.log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mooncowpines.kinostats.domain.model.Log
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import androidx.compose.ui.test.onNodeWithContentDescription


@RunWith(AndroidJUnit4::class)
class LogScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun logScreen_deleteAction_showsDialogAndConfirmsDeletion() {
        val testLog = Log(
            id = 101L,
            userId = 1L,
            movieId = 50L,
            movieTitle = "Test Movie",
            posterUrl = "",
            releaseYear = 2024,
            watchDate = LocalDate.now(),
            rating = 4.0f,
            logText = "A great movie."
        )

        var currentState = LogScreenState(
            isLoading = false,
            logs = listOf(testLog),
            logToDelete = null
        )

        var deleteConfirmed = false

        composeTestRule.setContent {
            var state by remember { mutableStateOf(currentState) }

            state.logToDelete?.let {
                com.mooncowpines.kinostats.ui.components.KinoDeleteDialog(
                    title = "Delete Log",
                    message = "Are you sure you want to delete this entry from your logs?",
                    onDismiss = { state = state.copy(logToDelete = null) },
                    onConfirm = { deleteConfirmed = true }
                )
            }

            LogContent(
                state = state,
                onNavigateToLogDetail = { _, _ -> },
                onDeleteClick = { log ->
                    state = state.copy(logToDelete = log)
                },
                onRefresh = { }
            )
        }

        composeTestRule.onNodeWithText("Are you sure you want to delete this entry from your logs?").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Delete", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Are you sure you want to delete this entry from your logs?").assertExists()
        composeTestRule.onNodeWithText("Delete").performClick()

        assertEquals("La confirmación de borrado no se ejecutó", true, deleteConfirmed)
    }
}