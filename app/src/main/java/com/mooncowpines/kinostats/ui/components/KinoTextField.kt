package com.mooncowpines.kinostats.ui.components

import com.mooncowpines.kinostats.ui.theme.*
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun KinoTextField(
    textValue: String,
    onTextChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    maxLength: Int = 50
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = textValue,
        onValueChange = {newText ->
            if (newText.length <= maxLength)
            {onTextChange(newText)}},
        modifier = modifier,
        placeholder = { Text(text = placeholderText, color = Color.Gray) },
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email),
        trailingIcon = if (isPassword) {
            {
                val image = if (passwordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                val description = if (passwordVisible) "Hide Password" else "Show Password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            }
        } else null,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = KinoOffWhite,
            unfocusedContainerColor = KinoOffWhite,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF121212, name = "Placeholder")
@Composable
fun PlaceholderPreview() {

    KinoTextField(
        textValue = "",
        onTextChange = {},
        placeholderText = "example@gmail.com",
    )

}

@Preview(showBackground = true, backgroundColor = 0xFF121212, name = "Text")
@Composable
fun TextFieldPreview() {

    KinoTextField(
        textValue = "alfonso.luna@gmail.com",
        onTextChange = {},
        placeholderText = "example@gmail.com",
    )

}

@Preview(showBackground = true, backgroundColor = 0xFF121212, name = "Password")
@Composable
fun PasswordFieldPreview() {

    KinoTextField(
        textValue = "Hola_12345!",
        onTextChange = {},
        placeholderText = "example@gmail.com",
        isPassword = true
    )
}


