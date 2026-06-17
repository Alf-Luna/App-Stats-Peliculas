package com.mooncowpines.kinostats.ui.screens.log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mooncowpines.kinostats.domain.model.Movie
import com.mooncowpines.kinostats.ui.screens.movieDetail.MovieDetailContent
import com.mooncowpines.kinostats.ui.screens.movieDetail.MovieDetailState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class MovieDetailTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun movieDetailScreen_fabAddLog_navigatesToCreateNewLog() {
        val testMovie = Movie(
            id = 99L,
            title = "Inception",
            posterUrl = "",
            backDropUrl = "",
            overview = "Un sueño dentro de un sueño.",
            releaseDate = LocalDate.now(),
            genres = emptyList(),
            actors = "Leo DiCaprio",
            duration = 148,
            originCountry = "USA",
            director = "Christopher Nolan",
            productionCompany = "Warner"
        )

        var currentState = MovieDetailState.Success(movie = testMovie)
        var navigatedMovieId: Long? = null

        composeTestRule.setContent {
            var state by remember { mutableStateOf(currentState) }

            MovieDetailContent(
                state = state,
                onNavigateBack = { },
                onNavigateToLog = { movieId ->
                    navigatedMovieId = movieId
                },
                onToggleFabMenu = { state = state.copy(isFabMenuExpanded = !state.isFabMenuExpanded) },
                onDismissFabMenu = { state = state.copy(isFabMenuExpanded = false) },
                onOpenListSheet = { },
                onDismissListSheet = { },
                onAddFilmToList = { },
                onClearListMessage = { }
            )
        }


        composeTestRule.onNodeWithContentDescription("Add Review", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Add Log").performClick()

        assertEquals("No se navegó a la pantalla de registro con el ID correcto", 99L, navigatedMovieId)

    }
}