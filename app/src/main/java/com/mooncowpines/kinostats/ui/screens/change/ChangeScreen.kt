package com.mooncowpines.kinostats.ui.screens.change

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
fun ChangeScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangeScreenViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.success) {
        if (state.success) {
            Toast.makeText(context, "Password changed successfully!", Toast.LENGTH_LONG).show()
            onNavigateToHome()
        }
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
        Change(
            modifier = Modifier.align(Alignment.Center),
            currentPassValue = state.currentPass,
            newPassValue = state.newPass,
            newPassCheckValue = state.newPassCheck,
            currentPassError = state.currentPassError,
            isSubmitting = state.isSubmitting,
            errorMsg = state.errorMsg,
            onCurrentPassChange = { viewModel.onCurrentPassChange(it)},
            onNewPassChange = { viewModel.onNewPassChange(it) },
            onNewPassCheckChange = { viewModel.onNewPassCheckChange(it) },
            onChangeClick = { viewModel.change() },
            onCancelClick = onNavigateBack,
        )
    }
}

@Composable
fun Change(
    modifier: Modifier,
    currentPassValue: String,
    newPassValue: String,
    newPassCheckValue: String,
    currentPassError: String?,
    isSubmitting: Boolean,
    errorMsg: String?,
    onCurrentPassChange: (String) -> Unit,
    onNewPassChange: (String) -> Unit,
    onNewPassCheckChange: (String) -> Unit,
    onChangeClick: () -> Unit,
    onCancelClick: () -> Unit,
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
            //Current password text field
            Column {
                Text(
                    text = "Current Password:",
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
                    textValue = currentPassValue,
                    onTextChange = onCurrentPassChange,
                    placeholderText = "Current Password",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth())

                //Visual feedback to password errors
                if (currentPassError != null) {
                    Text(
                        text = currentPassError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(KinoSpacing.small)
                    )
                }

            }

            Spacer(modifier = Modifier.height(KinoSpacing.medium))

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
                    textValue = newPassValue,
                    onTextChange = onNewPassChange,
                    placeholderText = "New Password",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth())
                //Visual feedback to password requirements
                PasswordRequirementsFeedback(newPassValue)
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
                    textValue = newPassCheckValue,
                    onTextChange = onNewPassCheckChange,
                    placeholderText = "Confirm Password",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth())
                //Visual feedback for password match
                PasswordMatchFeedback(newPassValue, newPassCheckValue)
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
                            onClick = onChangeClick,
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