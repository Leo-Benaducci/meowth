package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CategoryIdTest {
    @Nested
    inner class Generate {
        @Test
        fun `when generate, then instantiate CategoryId`() {
            val categoryId = CategoryId.generate()
            assertNotNull(categoryId)
            assertNotNull(categoryId.value)
        }
    }

    @Nested
    inner class With {
        @Test
        fun `given uuid v7, then instantiate CategoryId`() {
            val uuid = UUID.fromString("019cdab8-93fd-7262-8526-786a9ca26d05")
            val categoryId = CategoryId.with(uuid)
            assertNotNull(categoryId)
            assertEquals(uuid, categoryId.value)
        }

        @Test
        fun `given uuid not v7, then throw exception`() {
            val uuid = UUID.fromString("7d722c6b-1396-4883-b22c-6b13968883c4")
            assertFailsWith<ValidationException> { CategoryId.with(uuid) }
                .also { assertEquals(ErrorCatalog.UUID_VERSION.code, it.code) }
        }

        @Test
        fun `given uuid string, then instantiate CategoryId`() {
            val uuid = "019cdab8-93fd-7262-8526-786a9ca26d05"
            val categoryId = CategoryId.with(uuid)
            assertNotNull(categoryId)
            assertEquals(uuid, categoryId.value.toString())
        }

        @Test
        fun `given invalid uuid string, then throw exception`() {
            val uuid = "invalid-uuid"
            assertFailsWith<ValidationException> { CategoryId.with(uuid) }
                .also { assertEquals(ErrorCatalog.UUID_INVALID.code, it.code) }
        }
    }
}