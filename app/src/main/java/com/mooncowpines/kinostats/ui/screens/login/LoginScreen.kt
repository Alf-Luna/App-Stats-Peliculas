package com.mooncowpines.kinostats.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.imePadding
import androidx.hilt.navigation.compose.hiltViewModel

import com.mooncowpines.kinostats.R
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoFrame
import com.mooncowpines.kinostats.ui.components.KinoTextField
import com.mooncowpines.kinostats.ui.theme.KinoDarkYellow
import com.mooncowpines.kinostats.ui.theme.KinoSpacing

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToReset: () -> Unit,
    onNavigateToRecover: () -> Unit,
) {
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
        Login(
            modifier = Modifier.align(Alignment.Center),
            emailValue = state.email,
            passValue = state.pass,
            isSubmitting = state.isSubmitting,
            errorMsg = state.errorMsg,
            onEmailChange = { viewModel.onEmailChange(it) },
            onPassChange = { viewModel.onPassChange(it) },
            onLoginClick = { viewModel.login() },
            onRecoveryClick = onNavigateToRecover,
            onChangeClick = onNavigateToReset,
            onRegisterClick = onNavigateToRegister,
            onAdminClick = { viewModel.adminLogin() }
        )
    }
}

@Composable
fun Login(
    modifier: Modifier,
    emailValue: String,
    passValue: String,
    isSubmitting: Boolean,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRecoveryClick: () -> Unit,
    onChangeClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onAdminClick: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        //Header banner
        HeaderImage()

        Spacer(modifier = Modifier.height(KinoSpacing.jumbo))

        //Frame to wrap the form
        KinoFrame {
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
            }

            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            //Password Field
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

                //Text to navigate to Recovery Screen
                ForgotPassword(Modifier.align(Alignment.End), onClick = onRecoveryClick)
            }

            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            Column{
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
                Text(
                    text = "Create your account or log in:",
                    color = KinoYellow,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(bottom = KinoSpacing.small)
                )
                if (isSubmitting) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                        contentAlignment = Alignment.Center)
                    { CircularProgressIndicator(color = KinoYellow) }
                } else {
                    KinoButton(
                        text = "Log In...",
                        onClick = onLoginClick,
                        modifier = Modifier.fillMaxWidth())
                }
            }

            Column{
                KinoButton(
                    text = "Create Account",
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSubmitting)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(KinoSpacing.mediumSmall)) {
                KinoButton(
                    text = "Test",
                    onClick = onChangeClick,
                    modifier = Modifier.weight(1f),
                    enabled = !isSubmitting)

                KinoButton(
                    text = "Admin",
                    onClick = onAdminClick,
                    modifier = Modifier.weight(1f),
                    enabled = !isSubmitting)
            }
        }
    }
}

@Composable
fun ForgotPassword(modifier: Modifier, onClick:() -> Unit ) {
    Text(
        text = "forgot your password?",
        modifier = modifier.clickable { onClick()},
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = KinoDarkYellow
    )
}

@Composable
fun HeaderImage() {
    Image(painter = painterResource(id = R.drawable.kinostats_banner),
        contentDescription = "Banner de KinoStats",
        modifier = Modifier.fillMaxWidth())
}