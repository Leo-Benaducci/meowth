package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CategoryNameTest {
    @Nested
    inner class Constructor {
        @Test
        fun `given valid name, then instantiate CategoryName`() {
            val name = "test"
            val categoryName = CategoryName(name)
            assertEquals(name, categoryName.value)
        }

        @Test
        fun `given blank name, then throw exception`() {
            val name = "   "
            assertFailsWith<ValidationException> { CategoryName(name) }
                .also { assertEquals(CategoryErrorCatalog.NAME_BLANK.code, it.code) }
        }

        @ParameterizedTest
        @ValueSource(strings = ["a", "ab"])
        fun `given short name, then throw exception`(name: String) {
            assertFailsWith<ValidationException> { CategoryName(name) }
                .also { assertEquals(CategoryErrorCatalog.NAME_SHORT.code, it.code) }
        }

        @Test
        fun `given long name, then throw exception`() {
            val name = "a".repeat(31)
            assertFailsWith<ValidationException> { CategoryName(name) }
                .also { assertEquals(CategoryErrorCatalog.NAME_LONG.code, it.code) }
        }
    }
}