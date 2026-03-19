package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class RepositoryExceptionTest {
    @Nested
    inner class Constructor {
        @Test
        fun `given error, then instantiate RepositoryException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val exception = RepositoryException(error)

            assertEquals(error.code, exception.code)
            assertEquals(error.defaultMessage, exception.message)
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
            val exception = RepositoryException(error)

            assertEquals("RepositoryException(code='stub_error', message='Stub error message')", "$exception")
        }
    }
}
