package com.mooncowpines.kinostats.ui.screens.recovery

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
import androidx.compose.ui.text.font.FontStyle
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

    if (state.success) {
        Box(Modifier.fillMaxSize().padding(30.dp), contentAlignment = Alignment.Center) {
            Text(
                "FUNCIONA EL RECOVERY VIEWMODEL",
                color = KinoYellow,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        return
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
        Recovery(
            modifier = Modifier.align(Alignment.Center),
            emailValue = state.email,
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
    isSubmitting: Boolean,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onRecoveryClick: () -> Unit,
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
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Password text and text field
            Column {
                Text(
                    text = "Password:",
                    color = KinoYellow
                )
                HorizontalDivider(
                    color = KinoYellow,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

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

                Text(
                    text = "ALL FIELDS ARE MANDATORY",
                    color = KinoYellow,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    if (isSubmitting) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            contentAlignment = Alignment.Center
                        )
                        { CircularProgressIndicator(color = KinoYellow) }
                    } else {
                        KinoButton(
                            text = "Create Account",
                            onClick = onRecoveryClick,
                            modifier = Modifier.width(180.dp)
                        )
                    }

                    KinoButton(
                        text = "Cancel",
                        onClick = onCancelClick,
                        modifier = Modifier.width(150.dp)
                    )


                    }
                }
            }
        }
    }