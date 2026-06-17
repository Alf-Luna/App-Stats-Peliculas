package com.mooncowpines.kinostats.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginPasswordMaskingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_passwordField_masksInput_andTogglesVisibility() {
        composeTestRule.setContent {
            var fakeState by remember { mutableStateOf(LoginScreenState()) }

            Login(
                modifier = Modifier,
                state = fakeState,
                onUsernameChange = { fakeState = fakeState.copy(username = it) },
                onPassChange = { fakeState = fakeState.copy(pass = it) },
                onLoginClick = { },
                onRecoveryClick = { },
                onRegisterClick = { }
            )
        }

        val secretPassword = "SuperSecret123!"


        composeTestRule.onNodeWithText("Password").performTextInput(secretPassword)


        val toggleButtonShow = composeTestRule.onNodeWithContentDescription("Show Password")
        toggleButtonShow.assertIsDisplayed()
        toggleButtonShow.performClick()

        val toggleButtonHide = composeTestRule.onNodeWithContentDescription("Hide Password")
        toggleButtonHide.assertIsDisplayed()
    }
}