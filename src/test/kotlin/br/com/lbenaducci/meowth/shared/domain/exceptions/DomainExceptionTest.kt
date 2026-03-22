package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class DomainExceptionTest {
    @Nested
    inner class Constructor {
        @Test
        fun `given error, then instantiate DomainException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val field = "field"
            val rejectedValue = "invalid"
            val exception = DomainException(error, field, rejectedValue)

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
            val exception = DomainException(error, null, null)

            assertEquals(
                "DomainException(code='stub.error', message='Stub error message')",
                "$exception"
            )
        }

        @Test
        fun `given exception with optional values, then return string representation`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub.error"
                override val defaultMessage: String = "Stub error message"
            }
            val field = "field"
            val rejectedValue = "invalid"
            val exception = DomainException(error, field, rejectedValue)

            assertEquals(
                "DomainException(code='stub.error', message='Stub error message', field='field', rejectedValue='invalid')",
                "$exception"
            )
        }
    }
}
