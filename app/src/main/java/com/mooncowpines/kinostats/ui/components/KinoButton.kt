package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Composable
fun KinoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true) {
    //Standard button for KinoStats
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFC040),
            contentColor = Color.White,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.LightGray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212, widthDp = 200, name = "Fixed width")
@Composable
fun ButtonFixedWidthPreview() {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        KinoButton(
            text = "Log In...",
            onClick = { },
            modifier = Modifier.width(100.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212, widthDp = 200, name = "Max width")
@Composable
fun ButtonMaxWidthPreview() {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        KinoButton(
            text = "Log In...",
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}