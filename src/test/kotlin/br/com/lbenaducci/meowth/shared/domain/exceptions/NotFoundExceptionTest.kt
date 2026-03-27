package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class NotFoundExceptionTest {
    @Nested
    inner class Constructor {
        @Test
        fun `given error, then instantiate NotFoundException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val exception = NotFoundException(error)

            assertEquals(error, exception.detail.error)
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
            val exception = NotFoundException(error)

            assertEquals(
                "NotFoundException(code='stub.error', message='Stub error message')",
                "$exception"
            )
        }
    }
}