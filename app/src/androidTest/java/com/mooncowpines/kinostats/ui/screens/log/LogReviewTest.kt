package com.mooncowpines.kinostats.ui.screens.log
import androidx.compose.runtime.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.ui.screens.logDetail.LogDetailContent
import com.mooncowpines.kinostats.ui.screens.logDetail.LogDetailScreenState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class LogDetailReviewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun logDetailScreen_reviewField_capturesInputAndSaves() {
        var currentState = LogDetailScreenState(logText = "")
        var capturedText = ""

        composeTestRule.setContent {
            var state by remember { mutableStateOf(currentState) }

            LogDetailContent(
                state = state,
                movie = Movie(
                    id = 1L,
                    title = "Test Movie",
                    posterUrl = "",
                    backDropUrl = "",
                    overview = "Resumen de prueba",
                    releaseDate = LocalDate.now(),
                    genres = emptyList(),
                    actors = "Actor 1, Actor 2",
                    duration = 120,
                    originCountry = "USA",
                    director = "Director X",
                    productionCompany = "Studio Y"
                ),
                onNavigateBack = { },
                onSaveReview = { },
                onRatingChange = { },
                onReviewTextChange = {
                    state = state.copy(logText = it)
                    capturedText = it
                },
                onShowCalendar = { },
                onDateSelected = { }
            )
        }

        val testInput = "Esta es una gran película."
        composeTestRule.onNodeWithText("Write a review...").performTextInput(testInput)


        assertEquals("El texto de la reseña no coincide", testInput, capturedText)
    }


    @Test
    fun logDetailScreen_calendar_selectsDateAndUpdatesState() {
        var currentState = LogDetailScreenState(
            formattedWatchDate = "Tap to select a date...",
            showCalendar = false
        )
        var finalDisplayedDate = ""

        composeTestRule.setContent {
            var state by remember { mutableStateOf(currentState) }

            LogDetailContent(
                state = state,
                movie = Movie(
                    id = 1L,
                    title = "Test Movie",
                    posterUrl = "",
                    backDropUrl = "",
                    overview = "Resumen de prueba",
                    releaseDate = LocalDate.now(),
                    genres = emptyList(),
                    actors = "Actor 1, Actor 2",
                    duration = 120,
                    originCountry = "USA",
                    director = "Director X",
                    productionCompany = "Studio Y"
                ),
                onNavigateBack = { },
                onSaveReview = { },
                onRatingChange = { },
                onReviewTextChange = { },
                onShowCalendar = { state = state.copy(showCalendar = it) },
                onDateSelected = { timestamp ->
                    val mockTimestamp = timestamp ?: 1767225600000L

                    val localDate = java.time.Instant.ofEpochMilli(mockTimestamp)
                        .atZone(java.time.ZoneId.of("UTC")).toLocalDate()
                    val formatted = localDate.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy", java.util.Locale.getDefault()))

                    state = state.copy(
                        watchDate = localDate,
                        formattedWatchDate = formatted,
                        showCalendar = false
                    )
                    finalDisplayedDate = formatted
                }
            )
        }

        composeTestRule.onNodeWithText("Tap to select a date...").performClick()

        composeTestRule.onNodeWithText("Ok").performClick()

        composeTestRule.onNodeWithText(finalDisplayedDate).assertExists()
    }

    @Test
    fun logDetailScreen_ratingSelector_selectsIncrementalRatingAndUpdateState() {
        var currentState = LogDetailScreenState(rating = null)
        var capturedRating: Float? = null

        composeTestRule.setContent {
            var state by remember { mutableStateOf(currentState) }

            LogDetailContent(
                state = state,
                movie = Movie(
                    id = 1L,
                    title = "Test Movie",
                    posterUrl = "",
                    backDropUrl = "",
                    overview = "Resumen de prueba",
                    releaseDate = LocalDate.now(),
                    genres = emptyList(),
                    actors = "Actor 1, Actor 2",
                    duration = 120,
                    originCountry = "USA",
                    director = "Director X",
                    productionCompany = "Studio Y"
                ),
                onNavigateBack = { },
                onSaveReview = { },
                onRatingChange = { newRating ->
                    val safeRating = if (newRating < 0.5f) null else newRating
                    state = state.copy(rating = safeRating)
                    capturedRating = safeRating
                },
                onReviewTextChange = { },
                onShowCalendar = { },
                onDateSelected = { }
            )
        }

        composeTestRule.onNodeWithText("Tap to rate...").performClick()

        composeTestRule.onNodeWithText("4.5").performClick()

        assertEquals("La calificación capturada no es correcta", 4.5f, capturedRating)

        composeTestRule.onNodeWithText("4.5").assertExists()
        composeTestRule.onNodeWithText("Tap to rate...").assertDoesNotExist()
    }

    @Test
    fun logDetailScreen_saveAction_providesVisualFeedback() {
        var currentState = LogDetailScreenState(isSubmitting = false)
        var saveActionTriggered = false

        composeTestRule.setContent {
            var state by remember { mutableStateOf(currentState) }

            LogDetailContent(
                state = state,
                movie = Movie(
                    id = 1L,
                    title = "Test Movie",
                    posterUrl = "",
                    backDropUrl = "",
                    overview = "Resumen de prueba",
                    releaseDate = LocalDate.now(),
                    genres = emptyList(),
                    actors = "Actor",
                    duration = 120,
                    originCountry = "USA",
                    director = "Director X",
                    productionCompany = "Studio"
                ),
                onNavigateBack = { },
                onSaveReview = {
                    saveActionTriggered = true
                    state = state.copy(isSubmitting = true)
                },
                onRatingChange = { },
                onReviewTextChange = { },
                onShowCalendar = { },
                onDateSelected = { }
            )
        }

        composeTestRule.onNodeWithText("Save").assertExists()

        composeTestRule.onNodeWithText("Save").performClick()

        assertEquals("La acción de guardado no se disparó", true, saveActionTriggered)

        composeTestRule.onNodeWithText("Saving...").assertExists()
        composeTestRule.onNodeWithText("Save").assertDoesNotExist()
    }

    @Test
    fun logDetailScreen_saveAction_enforces5SecondTimeout() {
        // --- ARRANGE ---
        var currentState = LogDetailScreenState(isSubmitting = false, errorMsg = null)

        composeTestRule.setContent {
            var state by remember { mutableStateOf(currentState) }

            LogDetailContent(
                state = state,
                movie = Movie(
                    id = 1L,
                    title = "Test Movie",
                    posterUrl = "",
                    backDropUrl = "",
                    overview = "Resumen de prueba",
                    releaseDate = LocalDate.now(),
                    genres = emptyList(),
                    actors = "Actor",
                    duration = 120,
                    originCountry = "USA",
                    director = "Director X",
                    productionCompany = "Studio"
                ),
                onNavigateBack = { },
                onSaveReview = {
                    state = state.copy(isSubmitting = true)
                },
                onRatingChange = { },
                onReviewTextChange = { },
                onShowCalendar = { },
                onDateSelected = { }
            )
        }

        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText("Saving...").assertExists()
        composeTestRule.mainClock.advanceTimeBy(5001L)

        composeTestRule.onNodeWithText("Connection timeout. Please try again.").assertExists()
    }


}