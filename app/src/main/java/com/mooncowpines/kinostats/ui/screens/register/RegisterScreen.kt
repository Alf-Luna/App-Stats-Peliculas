package com.mooncowpines.kinostats.ui.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.hilt.navigation.compose.hiltViewModel

import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoFrame
import com.mooncowpines.kinostats.ui.components.KinoTextField
import com.mooncowpines.kinostats.ui.components.PasswordRequirementsFeedback
import com.mooncowpines.kinostats.ui.components.PasswordMatchFeedback
import com.mooncowpines.kinostats.ui.theme.KinoSpacing

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit
){

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.success) {
        if (state.success) {
            onNavigateToHome()
        }
    }

    Box(Modifier
        .fillMaxSize()
        .imePadding()
        .padding(KinoSpacing.extraLarge)) {
        Register(
            modifier = Modifier.align(Alignment.Center),
            userNameValue = state.userName,
            userNameError= state.userNameError,
            emailValue = state.email,
            emailError = state.emailError,
            passValue = state.pass,
            passCheckValue = state.passCheck,
            isSubmitting = state.isSubmitting,
            errorMsg = state.errorMsg,
            onUserNameChange = { viewModel.onUserNameChange(it) },
            onEmailChange = { viewModel.onEmailChange(it) },
            onPassChange = { viewModel.onPassChange(it) },
            onPassCheckChange = { viewModel.onPassCheckChange(it) },
            onRegisterClick = { viewModel.register() },
            onCancelClick = onNavigateBack
        )
    }
}

@Composable
fun Register(
    modifier: Modifier,
    userNameValue: String,
    userNameError: String?,
    emailValue: String,
    emailError: String?,
    passValue: String,
    passCheckValue: String,
    isSubmitting: Boolean,
    errorMsg: String?,
    onUserNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onPassCheckChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        //Header banner
        Text(
            text = "Create your account",
            color = KinoYellow,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

        //Frame to wrap the form
        KinoFrame {
            //Username field
            Column{
                Text(
                    text = "User Name:",
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
                    textValue = userNameValue,
                    onTextChange = onUserNameChange,
                    placeholderText = "User Name",
                    modifier = Modifier.fillMaxWidth())

                if (userNameError != null) {
                    Text(
                        text = userNameError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(KinoSpacing.small)
                    )
                }
            }

            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            //Email field
            Column{
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
                    modifier = Modifier.fillMaxWidth())

                if (emailError != null) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(KinoSpacing.small)
                    )
                }
            }

            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            //Password field
            Column{
                Text(
                    text = "Password:",
                    color = KinoYellow
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

            //Password check field
            Column{
                Text(
                    text = "Check Password:",
                    color = KinoYellow
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

            Column{
                //General error message
                if (errorMsg != null) {
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(KinoSpacing.small)
                    )
                }

                //Buttons section
                Text(
                    text = "ALL FIELDS ARE MANDATORY",
                    color = KinoYellow,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(bottom = KinoSpacing.small)
                )

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
                            text = "Create Account",
                            onClick = onRegisterClick,
                            modifier = Modifier.weight(1.5f))
                    }

                    KinoButton(
                        text = "Cancel",
                        onClick = onCancelClick,
                        enabled = !isSubmitting,
                        modifier = Modifier.weight(1f))
                }

            }
        }
    }
}