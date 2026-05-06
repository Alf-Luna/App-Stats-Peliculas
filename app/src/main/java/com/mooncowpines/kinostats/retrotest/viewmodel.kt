package com.mooncowpines.kinostats.retrotest

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    // Estado para la UI
    private val _postState = mutableStateOf<Post?>(null)
    val postState: State<Post?> = _postState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchPost() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val post = RetrofitClient.apiService.getPost()
                _postState.value = post
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}