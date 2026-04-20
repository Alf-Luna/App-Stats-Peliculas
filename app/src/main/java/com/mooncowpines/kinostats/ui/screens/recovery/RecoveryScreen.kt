package com.mooncowpines.kinostats.ui.screens.recovery

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel


import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoTextField

@Composable
fun RecoveryScreen(
    modifier: Modifier = Modifier,
    viewModel: RecoveryScreenViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    if (state.success) {
        Toast.makeText(context, "Recovery email sent!", Toast.LENGTH_LONG).show()
        onNavigateBack()
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
        Recovery(
            modifier = Modifier.align(Alignment.Center),
            emailValue = state.email,
            emailError = state.emailError,
            isSubmitting = state.isSubmitting,
            errorMsg = state.errorMsg,
            onEmailChange = { viewModel.onEmailChange(it) },
            onRecoveryClick = { viewModel.recovery() },
            onCancelClick = onNavigateBack
        )
    }
}

@Composable
fun Recovery(
    modifier: Modifier,
    emailValue: String,
    emailError: String?,
    isSubmitting: Boolean,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onRecoveryClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Forgot Your Password?",
            color = KinoYellow,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))
        //Column that wraps the text fields and buttons
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = KinoYellow,
                    shape = CutCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(24.dp),
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            //Email text and text field
            Column {
                Text(
                    text = "Email:",
                    color = KinoYellow,
                )
                HorizontalDivider(
                    color = KinoYellow,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )
                KinoTextField(
                    textValue = emailValue,
                    onTextChange = onEmailChange,
                    placeholderText = "example@gmail.com",
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailError != null) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Buttons text and buttons
            Column {
                if (errorMsg != null) {
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    if (isSubmitting) {
                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .height(48.dp),
                            contentAlignment = Alignment.Center
                        )
                        { CircularProgressIndicator(color = KinoYellow) }
                    } else {
                        KinoButton(
                            text = "Send",
                            onClick = onRecoveryClick,
                            modifier = Modifier.width(180.dp)
                        )
                    }

                    KinoButton(
                        text = "Cancel",
                        onClick = onCancelClick,
                        modifier = Modifier.width(150.dp),
                        enabled = !isSubmitting
                    )


                    }
                }
            }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "If your email is associated with an account you will receive a link to change your password ",
            color = KinoYellow,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 8.dp)
        )
        }
    }