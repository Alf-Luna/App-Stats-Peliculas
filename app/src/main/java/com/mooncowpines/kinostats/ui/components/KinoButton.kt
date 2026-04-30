package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.mooncowpines.kinostats.ui.theme.*
import com.mooncowpines.kinostats.ui.theme.KinoDarkGray
import com.mooncowpines.kinostats.ui.theme.KinoWhite



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
            containerColor = KinoYellow,
            contentColor = KinoWhite,
            disabledContainerColor = KinoDarkGray,
            disabledContentColor = Color.LightGray
        ),
        shape = MaterialTheme.shapes.small
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