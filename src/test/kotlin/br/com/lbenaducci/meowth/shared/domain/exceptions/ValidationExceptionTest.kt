package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidationExceptionTest {
    @Nested
    inner class Constructor {
        @Test
        fun `given error, then instantiate ValidationException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val exception = ValidationException(error)

            assertEquals(error.code, exception.code)
            assertEquals(error.defaultMessage, exception.message)
        }

        @Test
        fun `given error and params, then instantiate ValidationException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val params = mapOf("param1" to "value1")
            val exception = ValidationException(error, params)

            assertEquals(error.code, exception.code)
            assertEquals(error.defaultMessage, exception.message)
            assertEquals(params, exception.params)
        }

        @Test
        fun `given error and invalidValue, then instantiate ValidationException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val invalidValue = "any_value"
            val exception = ValidationException(error, invalidValue)

            assertEquals(error.code, exception.code)
            assertEquals(error.defaultMessage, exception.message)
            assertEquals(1, exception.params.size)
            assertEquals(invalidValue, exception.params["invalid"])
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `given exception, then return string representation`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val exception = ValidationException(error, "any_value")

            assertEquals("ValidationException(code='stub_error', message='Stub error message', params={invalid=any_value})", "$exception")
        }
    }
}
