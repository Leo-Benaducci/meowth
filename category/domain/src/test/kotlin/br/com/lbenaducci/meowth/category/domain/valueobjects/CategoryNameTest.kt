package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CategoryNameTest {
    @Nested
    inner class With {
        @Test
        fun `given valid name, then instantiate CategoryName`() {
            val name = "test"
            val categoryName = CategoryName.with(name)
            assertEquals(name, categoryName.value)
        }

        @Test
        fun `given blank name, then throw exception`() {
            val name = "   "
            assertFailsWith<ValidationException> { CategoryName.with(name) }
                .also { assertEquals(ErrorCatalog.NAME_BLANK.code, it.code) }
        }

        @Test
        fun `given short name, then throw exception`() {
            val name = "te"
            assertFailsWith<ValidationException> { CategoryName.with(name) }
                .also { assertEquals(ErrorCatalog.NAME_SHORT.code, it.code) }
        }

        @Test
        fun `given long name, then throw exception`() {
            val name = "Tincidunt commodo ea eum ea hendrerit"
            assertFailsWith<ValidationException> { CategoryName.with(name) }
                .also { assertEquals(ErrorCatalog.NAME_LONG.code, it.code) }
        }
    }
}