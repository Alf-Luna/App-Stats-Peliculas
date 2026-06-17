package com.mooncowpines.kinostats.ui.screens.register

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mooncowpines.kinostats.data.FakeAuthRepositoryImpl

import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertFalse

class RegisterScreenValidationTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun registerWithInvalidData_showsValidationErrors_andBlocksNavigation() {
        var hasNavigatedToHome = false
        val fakeRepo = FakeAuthRepositoryImpl()
        val testViewModel = RegisterScreenViewModel(fakeRepo)

        composeTestRule.setContent {
            RegisterScreen(
                viewModel = testViewModel,
                onNavigateBack = { },
                onNavigateToHome = { hasNavigatedToHome = true },
            )
        }

        composeTestRule.onNodeWithTag("email_input").performTextInput("correo-invalido")
        composeTestRule.onNodeWithTag("password_input").performTextInput("123")

        val registerButton = composeTestRule.onNodeWithText("Create Account")
        registerButton.performClick()

        assertFalse(
            "Error: The app allowed navigation to Home without a valid user",
            hasNavigatedToHome
        )

        composeTestRule.onNodeWithText("Please check the required fields").assertIsDisplayed()
        composeTestRule.onNodeWithText("• 7 characters min").assertIsDisplayed()
    }

    @Test
    fun passwordField_togglesMasking_whenTrailingIconIsClicked() {

        val fakeRepo = FakeAuthRepositoryImpl()
        val testViewModel = RegisterScreenViewModel(fakeRepo)

        composeTestRule.setContent {
            RegisterScreen(
                viewModel = testViewModel,
                onNavigateToHome = { },
                onNavigateBack = { }
            )
        }

        val secretPassword = "MiClaveSecreta123!"

        val passwordNode = composeTestRule.onNodeWithText("Password")
        passwordNode.performTextInput(secretPassword)

        val toggleButtonShow = composeTestRule.onAllNodesWithContentDescription("Show Password").onFirst()
        toggleButtonShow.assertIsDisplayed()
        toggleButtonShow.performClick()

        val toggleButtonHide = composeTestRule.onAllNodesWithContentDescription("Hide Password").onFirst()
        toggleButtonHide.assertIsDisplayed()
    }

}