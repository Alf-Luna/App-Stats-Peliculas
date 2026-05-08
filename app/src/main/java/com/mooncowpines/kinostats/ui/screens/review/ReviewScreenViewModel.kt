package com.mooncowpines.kinostats.ui.screens.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import com.mooncowpines.kinostats.domain.repository.ReviewRepository

import com.mooncowpines.kinostats.utils.*
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ReviewScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(ReviewScreenState())
    val state: StateFlow<ReviewScreenState> = _state.asStateFlow()

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            val fetchedMovie = movieRepository.getMovieById(movieId)
            if (fetchedMovie != null) {
                _state.update { it.copy(isLoadingMovie = false, movie = fetchedMovie) }
            } else {
                _state.update { it.copy(isLoadingMovie = false, errorMsg = "Movie not found") }
            }
        }
    }

    //Function to show or hide the calendar
    fun setShowCalendar(show: Boolean) {
        _state.update { it.copy(showCalendar = show) }
    }

    //Function to track date field value
    fun onWatchDateSelected(timestamp: Long?) {
        if (timestamp != null) {
            val localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.of("UTC")).toLocalDate()

            _state.update {
                it.copy(
                    watchDate = localDate,
                    watchDateError = null,
                    errorMsg = null,
                    showCalendar = false
                )
            }
        } else {
            setShowCalendar(false)
        }
    }


    //Functions to track text field value
    fun onRatingChange(newRating: Float) {
        _state.update { it.copy(rating = newRating, ratingError = null, errorMsg = null ) }
    }

    fun reviewTextChange(newReviewText: String) {
        _state.update { it.copy(reviewText = newReviewText, reviewTextError = null, errorMsg = null)}
    }

    //Triggers a save attempt
    fun saveReview() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text fields
        val ratingErrorResult = getRatingError(currentState.rating)
        val watchDateErrorResult = getWatchDateError(currentState.watchDate)
        val textReviewErrorResult = getTextReviewError(currentState.reviewText)

        if (ratingErrorResult != null || watchDateErrorResult != null || textReviewErrorResult != null) {
            _state.update {
                it.copy(
                    ratingError = ratingErrorResult,
                    watchDateError = watchDateErrorResult,
                    reviewTextError = textReviewErrorResult,
                    errorMsg = "Please check the required fields") }
            return

        }

        //Tries to save the review
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null, ratingError = null, watchDateError = null, reviewTextError = null) }

            val currentUser = authRepository.getCurrentUser()

            val isSuccess = reviewRepository.saveReview(
                newMovieId = movieId,
                newUserId = currentUser?.id,
                newRating = currentState.rating,
                newWatchDate = currentState.watchDate,
                newReviewText = currentState.reviewText
            )


            if (isSuccess) {
                Log.d("Session User (Review)", "The current logged user is: $currentUser")
                _state.update {
                    it.copy(isSubmitting = false, success = true)
                }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "Failed to save the review") }
            }
        }
    }


}