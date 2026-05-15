package com.mooncowpines.kinostats.ui.screens.logDetail

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
import com.mooncowpines.kinostats.domain.repository.LogRepository

import com.mooncowpines.kinostats.utils.*
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@HiltViewModel
class LogDetailScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val logRepository: LogRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(LogDetailScreenState())
    val state: StateFlow<LogDetailScreenState> = _state.asStateFlow()

    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])
    private val logId: String? = savedStateHandle["logId"]

    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingMovie = true) }

            val movie = movieRepository.getMovieById(movieId)

            if (logId != null) {
                val existingLog = logRepository.getLogById(logId.toLong())

                if (existingLog != null) {
                    _state.update { it.copy(
                        movie = movie,
                        isLoadingMovie = false,
                        rating = existingLog.rating,
                        logText = existingLog.logText,
                        watchDate = existingLog.watchDate,
                        formattedWatchDate = existingLog.watchDate?.format(dateFormatter) ?: "Select date"
                    )}
                } else {
                    _state.update { it.copy(
                        movie = movie,
                        isLoadingMovie = false,
                        errorMsg = "The log failed to load"
                    )}
                }
            } else {
                _state.update { it.copy(movie = movie, isLoadingMovie = false) }
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
            val formattedDate = localDate.format(dateFormatter)

            _state.update {
                it.copy(
                    watchDate = localDate,
                    formattedWatchDate = formattedDate,
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

    fun logTextChange(newReviewText: String) {
        _state.update { it.copy(logText = newReviewText, logTextError = null, errorMsg = null)}
    }

    //Triggers a save attempt
    fun saveReview() {
        val currentState = _state.value
        if (currentState.isSubmitting) return

        //Local validation for the text fields
        val ratingErrorResult = getRatingError(currentState.rating)
        val watchDateErrorResult = getWatchDateError(currentState.watchDate)
        val textReviewErrorResult = getTextLogError(currentState.logText)

        if (ratingErrorResult != null || watchDateErrorResult != null || textReviewErrorResult != null) {
            _state.update {
                it.copy(
                    ratingError = ratingErrorResult,
                    watchDateError = watchDateErrorResult,
                    logTextError = textReviewErrorResult,
                    errorMsg = "Please check the required fields") }
            return

        }

        //Tries to save the review
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMsg = null, ratingError = null, watchDateError = null, logTextError = null) }

            val currentUser = authRepository.getCurrentUser()

            val isSuccess = if (logId != null) {
                logRepository.updateLog(
                    logId = logId.toLong(),
                    newMovieId = movieId,
                    newUserId = currentUser?.id,
                    newRating = currentState.rating,
                    newWatchDate = currentState.watchDate,
                    newReviewText = currentState.logText
                )
            } else {
                logRepository.saveLog(
                    newMovieId = movieId,
                    newUserId = currentUser?.id,
                    newRating = currentState.rating,
                    newWatchDate = currentState.watchDate,
                    newReviewText = currentState.logText
                )
            }

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