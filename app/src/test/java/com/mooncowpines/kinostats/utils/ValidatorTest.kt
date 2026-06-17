package com.mooncowpines.kinostats.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ValidatorsTest {

    @Test
    fun `CP_01 getEmailError with valid email should return null`() {
        val validEmail = "ivan_dev@test.cl"

        val result = getEmailError(validEmail)

        assertNull("System should return null (no error)", result)
    }

    @Test
    fun `CP_02 getEmailError with invalid emails should return error message`() {
        val malformedEmails = listOf(
            "usuario-sin-arroba.com",
            "usuario@.com",
            "@dominio.com"
        )

        malformedEmails.forEach { invalidEmail ->
            val result = getEmailError(invalidEmail)

            assertEquals(
                "Validator failed and let the wrong email pass: $invalidEmail",
                "Invalid email format",
                result
            )
        }
    }
}