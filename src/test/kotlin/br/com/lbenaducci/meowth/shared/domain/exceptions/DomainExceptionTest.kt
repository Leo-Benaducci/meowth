package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class DomainExceptionTest {
    @Nested
    inner class Constructor {
        @Test
        fun `given error and params, then instantiate DomainException`() {
            val error = object : ErrorCatalog {
                override val code: String = "stub_error"
                override val defaultMessage: String = "Stub error message"
            }
            val params = mapOf("param1" to "value1")
            val exception = DomainException(error, params)

            assertEquals(error.code, exception.code)
            assertEquals(error.defaultMessage, exception.message)
            assertEquals(params, exception.params)
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
            val params = mapOf("param1" to "value1")
            val exception = DomainException(error, params)

            assertEquals("DomainException(code='stub_error', message='Stub error message', params={param1=value1})", "$exception")
        }
    }
}
