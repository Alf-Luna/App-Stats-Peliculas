package com.mooncowpines.kinostats.ui.screens.reset

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
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoFrame
import com.mooncowpines.kinostats.ui.components.KinoTextField
import com.mooncowpines.kinostats.ui.components.PasswordRequirementsFeedback
import com.mooncowpines.kinostats.ui.components.PasswordMatchFeedback
import com.mooncowpines.kinostats.ui.theme.KinoSpacing

@Composable
fun ResetScreen(
    modifier: Modifier = Modifier,
    viewModel: ResetScreenViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.success) {
        if (state.success) {
            Toast.makeText(context, "Password changed successfully!", Toast.LENGTH_LONG).show()
            onNavigateToLogin()
        }
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
        Reset(
            modifier = Modifier.align(Alignment.Center),
            passValue = state.pass,
            passCheckValue = state.passCheck,
            isSubmitting = state.isSubmitting,
            errorMsg = state.errorMsg,
            onPassChange = { viewModel.onPassChange(it) },
            onPassCheckChange = { viewModel.onPassCheckChange(it) },
            onResetClick = { viewModel.reset() },
            onCancelClick = onNavigateBack,
            onNavigateToLogin = {}
        )
    }
}

@Composable
fun Reset(
    modifier: Modifier,
    passValue: String,
    passCheckValue: String,
    isSubmitting: Boolean,
    errorMsg: String?,
    onPassChange: (String) -> Unit,
    onPassCheckChange: (String) -> Unit,
    onResetClick: () -> Unit,
    onCancelClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        //Header banner
        Text(
            text = "Please Enter Your New Password",
            color = KinoYellow,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

        //Frame to wrap the form
        KinoFrame {
            //Password text field
            Column {
                Text(
                    text = "New Password:",
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
                    textValue = passValue,
                    onTextChange = onPassChange,
                    placeholderText = "Password",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth())
                //Visual feedback to password requirements
                PasswordRequirementsFeedback(passValue)
            }

            Spacer(modifier = Modifier.height(KinoSpacing.medium))
            //Password check test field
            Column {
                Text(
                    text = "Confirm New Password:",
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
                    textValue = passCheckValue,
                    onTextChange = onPassCheckChange,
                    placeholderText = "Confirm Password",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth())
                //Visual feedback for password match
                PasswordMatchFeedback(passValue, passCheckValue)
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
                    horizontalArrangement = Arrangement.spacedBy(KinoSpacing.medium)
                ) {
                    if (isSubmitting) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(color = KinoYellow) }
                    } else {
                        KinoButton(
                            text = "Change",
                            onClick = onResetClick,
                            modifier = Modifier.weight(1.5f)
                        )
                    }

                    KinoButton(
                        text = "Cancel",
                        onClick = onCancelClick,
                        modifier = Modifier.weight(1f),
                        enabled = !isSubmitting)
                    }
                }
            }
        }
    }