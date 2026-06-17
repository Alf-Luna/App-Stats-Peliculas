package com.mooncowpines.kinostats.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterPasswordMaskingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerScreen_passwordFields_maskInput_andToggleVisibility() {

        composeTestRule.setContent {

            var fakeState by remember { mutableStateOf(RegisterScreenState()) }

            Register(
                modifier = Modifier,
                state = fakeState,
                onUserNameChange = { fakeState = fakeState.copy(userName = it) },
                onEmailChange = { fakeState = fakeState.copy(email = it) },
                onPassChange = { fakeState = fakeState.copy(pass = it) },
                onPassCheckChange = { fakeState = fakeState.copy(passCheck = it) },
                onRegisterClick = { },
                onCancelClick = { }
            )
        }

        val secretPassword = "SuperSecret123!"


        composeTestRule.onNodeWithText("Password").performTextInput(secretPassword)

        val showPasswordNodes = composeTestRule.onAllNodesWithContentDescription("Show Password")

        showPasswordNodes[0].assertIsDisplayed()
        showPasswordNodes[0].performClick()


        val hidePasswordNodes = composeTestRule.onAllNodesWithContentDescription("Hide Password")
        hidePasswordNodes[0].assertIsDisplayed()
    }
}