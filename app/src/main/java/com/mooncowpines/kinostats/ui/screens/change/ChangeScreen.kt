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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.hilt.navigation.compose.hiltViewModel

import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.components.KinoButton
import com.mooncowpines.kinostats.ui.components.KinoFrame
import com.mooncowpines.kinostats.ui.components.KinoTextField
import com.mooncowpines.kinostats.ui.components.PasswordRequirementsFeedback
import com.mooncowpines.kinostats.ui.components.PasswordMatchFeedback
import com.mooncowpines.kinostats.ui.components.KinoErrorText
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoWhite

@Composable
fun ChangeScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangeScreenViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.profileSuccess, state.passwordSuccess) {
        if (state.profileSuccess) {
            Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_LONG).show()
            viewModel.resetSuccessFlags()
            onNavigateBack()
        }
        if (state.passwordSuccess) {
            Toast.makeText(context, "Password changed successfully!", Toast.LENGTH_LONG).show()
            viewModel.resetSuccessFlags()
            onNavigateBack()
        }
    }

    Box(Modifier.fillMaxSize().padding(30.dp)) {
        ChangeContent(
            state = state,
            viewModel = viewModel,
            onCancelClick = onNavigateBack,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ChangeContent(
    state: ChangeScreenState,
    viewModel: ChangeScreenViewModel,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Update Your Info", color = KinoYellow, fontSize = 30.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

        KinoFrame {
            Text("Profile Information", color = KinoWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            KinoTextField(textValue = state.userName, onTextChange = { viewModel.onUserNameChange(it) }, placeholderText = "Username", modifier = Modifier.fillMaxWidth())
            state.userNameError?.let { KinoErrorText(it) }
            Spacer(modifier = Modifier.height(KinoSpacing.small))

            KinoTextField(textValue = state.email, onTextChange = { viewModel.onEmailChange(it) }, placeholderText = "Email", modifier = Modifier.fillMaxWidth())
            state.emailError?.let { KinoErrorText(it) }
            Spacer(modifier = Modifier.height(KinoSpacing.small))

            KinoTextField(textValue = state.passForProfile, onTextChange = { viewModel.onPassForProfileChange(it) }, placeholderText = "Current Password to verify", isPassword = true, modifier = Modifier.fillMaxWidth())
            state.passForProfileError?.let { KinoErrorText(it) }
            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            if (state.isSubmittingProfile) {
                CircularProgressIndicator(color = KinoYellow)
            } else {
                KinoButton(text = "Update Profile", onClick = { viewModel.changeProfile() }, modifier = Modifier.fillMaxWidth())
            }
        }

        Spacer(modifier = Modifier.height(KinoSpacing.extraLarge))

        KinoFrame {
            Text("Change Password", color = KinoWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            KinoTextField(textValue = state.newPass, onTextChange = { viewModel.onNewPassChange(it) }, placeholderText = "New Password", isPassword = true, modifier = Modifier.fillMaxWidth())
            PasswordRequirementsFeedback(state.newPass)
            Spacer(modifier = Modifier.height(KinoSpacing.small))

            KinoTextField(textValue = state.newPassCheck, onTextChange = { viewModel.onNewPassCheckChange(it) }, placeholderText = "Confirm New Password", isPassword = true, modifier = Modifier.fillMaxWidth())
            PasswordMatchFeedback(state.newPass, state.newPassCheck)
            Spacer(modifier = Modifier.height(KinoSpacing.small))

            KinoTextField(textValue = state.passForPassword, onTextChange = { viewModel.onPassForPasswordChange(it) }, placeholderText = "Current Password to verify", isPassword = true, modifier = Modifier.fillMaxWidth())
            state.passForPasswordError?.let { KinoErrorText(it) }
            Spacer(modifier = Modifier.height(KinoSpacing.medium))

            if (state.isSubmittingPassword) {
                CircularProgressIndicator(color = KinoYellow)
            } else {
                KinoButton(text = "Change Password", onClick = { viewModel.changePassword() }, modifier = Modifier.fillMaxWidth())
            }
        }

        state.errorMsg?.let { KinoErrorText(it) }
        Spacer(modifier = Modifier.height(KinoSpacing.large))
        KinoButton(text = "Cancel", onClick = onCancelClick, modifier = Modifier.fillMaxWidth(0.5f))
    }
}