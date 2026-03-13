package br.com.lbenaducci.meowth.category.domain.entities

import br.com.lbenaducci.meowth.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CategoryTest {
    @Nested
    inner class Create {
        @Test
        fun `given valid data, then instantiate Category`() {
            val expectedName = "Food"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "fast-food"
            val expectedColor = "#FF0000"

            val category = Category.create(
                name = expectedName,
                type = expectedType,
                icon = expectedIcon,
                color = expectedColor
            )

            assertNotNull(category.id)
            assertNotNull(category.createdAt)
            assertEquals(expectedName, category.name.value)
            assertEquals(expectedType, category.type)
            assertEquals(expectedIcon, category.appearance.icon.value)
            assertEquals(expectedColor, category.appearance.color.value)
        }

        @Test
        fun `given invalid name, then throw exception`() {
            val name = ""
            val type = CategoryType.EXPENSE
            val icon = "fast-food"
            val color = "#FF0000"

            assertFailsWith<ValidationException> { Category.create(name, type, icon, color) }
                .also { assertEquals(CategoryErrorCatalog.NAME_BLANK.code, it.code) }
        }

        @Test
        fun `given invalid icon, then throw exception`() {
            val name = "Food"
            val type = CategoryType.EXPENSE
            val icon = ""
            val color = "#FF0000"

            assertFailsWith<ValidationException> { Category.create(name, type, icon, color) }
                .also { assertEquals(CategoryErrorCatalog.ICON_BLANK.code, it.code) }
        }

        @Test
        fun `given invalid color, when call create, then should throw exception`() {
            val name = "Food"
            val type = CategoryType.EXPENSE
            val icon = "fast-food"
            val color = "invalid"

            assertFailsWith<ValidationException> { Category.create(name, type, icon, color) }
                .also { assertEquals(CategoryErrorCatalog.COLOR_INVALID.code, it.code) }
        }
    }
}
