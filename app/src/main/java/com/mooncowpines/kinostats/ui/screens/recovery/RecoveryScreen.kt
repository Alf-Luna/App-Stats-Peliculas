package com.mooncowpines.kinostats.ui.screens.recovery

import android.widget.Toast
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoTextField
import com.mooncowpines.kinostats.ui.components.KinoFrame
import com.mooncowpines.kinostats.ui.theme.KinoSpacing

@Composable
fun RecoveryScreen(
    modifier: Modifier = Modifier,
    viewModel: RecoveryScreenViewModel = hiltViewModel(),
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

        //Header banner
        Text(
            text = "Forgot Your Password?",
            color = KinoYellow,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

        //Frame to wrap the form
        KinoFrame {
            //Email field
            Column {
                Text(
                    text = "Email:",
                    color = KinoYellow,
                )
                HorizontalDivider(
                    color = KinoYellow,
                    thickness = 1.dp,
                    modifier = Modifier.padding(
                        top = KinoSpacing.micro,
                        bottom = KinoSpacing.small)
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
                        modifier = Modifier.padding(bottom = KinoSpacing.small)
                    )
                }
            }

            Spacer(modifier = Modifier.height(KinoSpacing.medium))


            Column {
                //General error message
                if (errorMsg != null) {
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = KinoSpacing.small)
                    )
                }

                //Buttons section
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(KinoSpacing.medium)
                ) {
                    if (isSubmitting) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            contentAlignment = Alignment.Center)
                        { CircularProgressIndicator(color = KinoYellow) }
                    } else {
                        KinoButton(
                            text = "Send",
                            onClick = onRecoveryClick,
                            modifier = Modifier.weight(1.5f))
                    }

                    KinoButton(
                        text = "Cancel",
                        onClick = onCancelClick,
                        modifier = Modifier.weight(1f),
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