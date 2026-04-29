package com.mooncowpines.kinostats.ui.screens.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncowpines.kinostats.data.FakeAuthApi
import com.mooncowpines.kinostats.data.FakeReviewApi
import com.mooncowpines.kinostats.data.MockSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import android.util.Log

import com.mooncowpines.kinostats.utils.*

class ReviewScreenViewModel: ViewModel() {
    private val _state = MutableStateFlow(ReviewScreenState())
    val state: StateFlow<ReviewScreenState> = _state.asStateFlow()

    //Functions to track text field value
    fun onRatingChange(newRating: Float) {
        _state.update { it.copy(rating = newRating, ratingError = null, errorMsg = null ) }
    }

    fun onWatchDateChange(newWatchDate: LocalDate) {
        _state.update { it.copy(watchDate = newWatchDate, watchDateError = null, errorMsg = null)}
    }
    fun reviewTextChange(newReviewText: String) {
        _state.update { it.copy(reviewText = newReviewText, reviewTextError = null, errorMsg = null)}
    }

    //Triggers a save attempt
    fun saveReview(movieId: Int) {
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

            val isSuccess = FakeReviewApi.saveReview(
                newMovieId = movieId,
                newUserId = MockSession.currentUserId,
                newRating = currentState.rating,
                newWatchDate = currentState.watchDate,
                newReviewText = currentState.reviewText
            )


            if (isSuccess) {
                Log.d("Session User (Review)", "The current logged user is: ${MockSession.currentUserId}")
                _state.update {
                    it.copy(isSubmitting = false, success = true)
                }
            } else {
                _state.update { it.copy(isSubmitting = false, errorMsg = "Failed to save the review") }
            }
        }
    }


}