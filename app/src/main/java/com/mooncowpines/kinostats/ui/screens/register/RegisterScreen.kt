package com.mooncowpines.kinostats.ui.screens.register

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.mooncowpines.kinostats.R
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoTextField
import com.mooncowpines.kinostats.ui.components.PasswordRequirementsFeedback
import com.mooncowpines.kinostats.ui.components.PasswordMatchFeedback

@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit
){

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.success) {
        if (state.success) {
            onNavigateToHome()
        }
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
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
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Create your account",
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
                    shape = CutCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(24.dp),
        ) {

            Column{
                Text(
                    text = "User Name:",
                    color = KinoYellow,
                )
                HorizontalDivider(
                    color = KinoYellow,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
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
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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

                PasswordRequirementsFeedback(passValue)
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Password text and text field
            Column{
                Text(
                    text = "Check Password:",
                    color = KinoYellow
                )
                HorizontalDivider(
                    color = KinoYellow,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )
                KinoTextField(
                    textValue = passCheckValue,
                    onTextChange = onPassCheckChange,
                    placeholderText = "Confirm Password",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth())
                PasswordMatchFeedback(passValue, passCheckValue)
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
                    text = "ALL FIELDS ARE MANDATORY",
                    color = KinoYellow,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    if (isSubmitting) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                            contentAlignment = Alignment.Center)
                        { CircularProgressIndicator(color = KinoYellow) }
                    } else {
                        KinoButton(
                            text = "Create Account",
                            onClick = onRegisterClick,
                            modifier = Modifier.width(180.dp))
                    }

                    KinoButton(
                        text = "Cancel",
                        onClick = onCancelClick,
                        modifier = Modifier.width(150.dp))


                }
            }
        }
    }
}