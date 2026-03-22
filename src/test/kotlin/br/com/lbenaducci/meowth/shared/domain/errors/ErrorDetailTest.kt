package br.com.lbenaducci.meowth.shared.domain.errors

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class ErrorDetailTest {
    @Nested
    inner class ToString {
        @Test
        fun `given error detail, then returns string representation`() {
            val catalog = object : ErrorCatalog {
                override val code: String = "stub.error"
                override val defaultMessage: String = "Default message"
            }
            val errorDetail = ErrorDetail(catalog, null, null)

            assertEquals("code='stub.error', message='Default message'", errorDetail.toString())
        }

        @Test
        fun `given error detail optionals values, then returns string representation`() {
            val catalog = object : ErrorCatalog {
                override val code: String = "stub.error"
                override val defaultMessage: String = "Default message"
            }
            val errorDetail = ErrorDetail(catalog, "field", "invalid")

            assertEquals("code='stub.error', message='Default message', field='field', rejectedValue='invalid'", errorDetail.toString())
        }
    }
}