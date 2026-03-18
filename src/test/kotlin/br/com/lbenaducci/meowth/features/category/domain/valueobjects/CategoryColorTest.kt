package br.com.lbenaducci.meowth.features.category.domain.valueobjects

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CategoryColorTest {
    @Nested
    inner class Constructor {
        @ParameterizedTest
        @ValueSource(strings = ["#000", "#000000", "#FFF", "#FFFFFF", "#a1b2c3", "#A1B2C3"])
        fun `given valid color, then instantiate CategoryColor`(color: String) {
            val categoryColor = CategoryColor(color)
            assertEquals(color, categoryColor.value)
        }

        @Test
        fun `given blank color, then throw exception`() {
            val color = "   "
            assertFailsWith<ValidationException> { CategoryColor(color) }
                .also { assertEquals(CategoryErrorCatalog.COLOR_BLANK.code, it.code) }
        }

        @ParameterizedTest
        @ValueSource(strings = ["#00", "#0000", "#00000", "#0000000", "000000", "#GGGGGG", "#12345G"])
        fun `given invalid color, then throw exception`(color: String) {
            assertFailsWith<ValidationException> { CategoryColor(color) }
                .also { assertEquals(CategoryErrorCatalog.COLOR_INVALID.code, it.code) }
        }
    }
}
