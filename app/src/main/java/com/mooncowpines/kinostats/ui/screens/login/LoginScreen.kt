package com.mooncowpines.kinostats.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.mooncowpines.kinostats.R
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoFrame
import com.mooncowpines.kinostats.ui.components.KinoTextField

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToChange: () -> Unit,
    onNavigateToRecover: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.success) {
        if (state.success) {
            onNavigateToHome()
        }
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
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
            onChangeClick = onNavigateToChange,
            onRegisterClick = onNavigateToRegister
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
    onRegisterClick: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        //Top Banner
        HeaderImage()

        Spacer(modifier = Modifier.height(100.dp))

        //Column that wraps the text fields and buttons
        KinoFrame {
            //Email text and text field
            Column{
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
                    modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Password text and text field
            Column{
                Text(
                    text = "Password:",
                    color = KinoYellow
                )
                HorizontalDivider(
                    color = KinoYellow,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )
                KinoTextField(
                    textValue = passValue,
                    onTextChange = onPassChange,
                    placeholderText = "Password",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth())
                ForgotPassword(Modifier.align(Alignment.End), onClick = onRecoveryClick)
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Buttons text and buttons
            Column{
                if (errorMsg != null) {
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Text(
                    text = "Create your account or log in:",
                    color = KinoYellow,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(bottom = 8.dp)
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

            Spacer(modifier = Modifier.padding(2.dp))

            Column{
                KinoButton(
                    text = "Create Account",
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSubmitting)
            }

            Column{
                KinoButton(
                    text = "Test",
                    onClick = onChangeClick,
                    modifier = Modifier.fillMaxWidth(),
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
        color = Color(0xFFFB9600)
    )
}

@Composable
fun HeaderImage() {
    Image(painter = painterResource(id = R.drawable.kinostats_banner),
        contentDescription = "Banner de KinoStats",
        modifier = Modifier.fillMaxWidth())
}