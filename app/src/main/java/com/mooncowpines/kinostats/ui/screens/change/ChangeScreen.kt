package com.mooncowpines.kinostats.ui.screens.change

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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoTextField
import com.mooncowpines.kinostats.ui.components.PasswordRequirementsFeedback
import com.mooncowpines.kinostats.ui.components.PasswordMatchFeedback

@Composable
fun ChangeScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangeScreenViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    if (state.success) {
        Box(Modifier.fillMaxSize().padding(30.dp), contentAlignment = Alignment.Center) {
            Text(
                "FUNCIONA EL CHANGE VIEWMODEL",
                color = KinoYellow,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        return
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
        Change(
            modifier = Modifier.align(Alignment.Center),
            passValue = state.pass,
            passCheckValue = state.passCheck,
            isSubmitting = state.isSubmitting,
            errorMsg = state.errorMsg,
            onPassChange = { viewModel.onPassChange(it) },
            onPassCheckChange = { viewModel.onPassCheckChange(it) },
            onChangeClick = { viewModel.change() },
            onCancelClick = onNavigateBack
        )
    }
}

@Composable
fun Change(
    modifier: Modifier,
    passValue: String,
    passCheckValue: String,
    isSubmitting: Boolean,
    errorMsg: String?,
    onPassChange: (String) -> Unit,
    onPassCheckChange: (String) -> Unit,
    onChangeClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Please Enter Your New Password",
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
                    text = "New Password:",
                    color = KinoYellow,
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

            Column {
                Text(
                    text = "Confirm New Password:",
                    color = KinoYellow,
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
                                .fillMaxWidth()
                                .height(48.dp),
                            contentAlignment = Alignment.Center
                        )
                        { CircularProgressIndicator(color = KinoYellow) }
                    } else {
                        KinoButton(
                            text = "Change",
                            onClick = onChangeClick,
                            modifier = Modifier.width(160.dp)
                        )
                    }

                    KinoButton(
                        text = "Go to Log in",
                        onClick = onCancelClick,
                        modifier = Modifier.width(160.dp)
                    )


                    }
                }
            }
        }
    }