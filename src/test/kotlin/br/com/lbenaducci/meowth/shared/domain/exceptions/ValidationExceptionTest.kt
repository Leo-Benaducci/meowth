package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertNull
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
            val field = "field"
            val exception = ValidationException(error, field)

            assertEquals(error, exception.detail.error)
            assertEquals(field, exception.detail.field)
            assertNull(exception.detail.rejectedValue)
        }

        @Test
        fun `given error with optional values, then instantiate ValidationException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val field = "field"
            val rejectedValue = "invalid"
            val exception = ValidationException(error, field, rejectedValue)

            assertEquals(error, exception.detail.error)
            assertEquals(field, exception.detail.field)
            assertEquals(rejectedValue, exception.detail.rejectedValue)
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `given exception, then return string representation`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub.error"
                override val defaultMessage: String = "Stub error message"
            }
            val field = "field"
            val rejectedValue = "invalid"
            val exception = ValidationException(error, field, rejectedValue)

            assertEquals(
                "ValidationException(code='stub.error', message='Stub error message', field='field', rejectedValue='invalid')",
                "$exception"
            )
        }
    }
}
