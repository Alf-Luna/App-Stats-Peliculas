package com.mooncowpines.kinostats.retrotest

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    val post = viewModel.postState.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(text = errorMessage)
        } else if (post != null) {
            Text(text = "Título: ${post.title}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Cuerpo: ${post.body}")
        } else {
            Text(text = "Presiona el botón para cargar datos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.fetchPost() }) {
            Text("Cargar Post")
        }
    }
}