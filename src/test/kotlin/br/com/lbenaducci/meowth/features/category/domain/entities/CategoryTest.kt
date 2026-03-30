package br.com.lbenaducci.meowth.features.category.domain.entities

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import java.time.Instant
import java.util.*
import kotlin.test.*

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
            assertNotNull(category.audit)
            assertNotNull(category.audit.createdAt)
            assertNotNull(category.audit.updatedAt)
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
                .also {
                    assertEquals(CategoryErrorCatalog.NAME_BLANK, it.detail.error)
                    assertEquals("category.name", it.detail.field)
                    assertNull(it.detail.rejectedValue)
                }
        }

        @Test
        fun `given invalid icon, then throw exception`() {
            val name = "Food"
            val type = CategoryType.EXPENSE
            val icon = ""
            val color = "#FF0000"

            assertFailsWith<ValidationException> { Category.create(name, type, icon, color) }
                .also {
                    assertEquals(CategoryErrorCatalog.ICON_BLANK, it.detail.error)
                    assertEquals("category.appearance.icon", it.detail.field)
                    assertNull(it.detail.rejectedValue)
                }
        }

        @Test
        fun `given invalid color, when call create, then should throw exception`() {
            val name = "Food"
            val type = CategoryType.EXPENSE
            val icon = "fast-food"
            val color = "invalid"

            assertFailsWith<ValidationException> { Category.create(name, type, icon, color) }
                .also {
                    assertEquals(CategoryErrorCatalog.COLOR_INVALID, it.detail.error)
                    assertEquals("category.appearance.color", it.detail.field)
                    assertEquals(color, it.detail.rejectedValue)
                }
        }
    }

    @Nested
    inner class With {
        @Test
        fun `given valid data, then instantiate Category`() {
            val expectedId = UUID.fromString("019cdab8-93fd-7262-8526-786a9ca26d05")
            val expectedCreatedAt = Instant.parse("2026-01-01T00:00:00.00Z")
            val expectedName = "Lazer"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "house"
            val expectedColor = "#FFFFFF"

            val category = Category.with(
                id = expectedId,
                createdAt = expectedCreatedAt,
                updatedAt = expectedCreatedAt,
                name = expectedName,
                type = expectedType,
                icon = expectedIcon,
                color = expectedColor
            )

            assertEquals(expectedId, category.id.value)
            assertEquals(expectedCreatedAt, category.audit.createdAt)
            assertEquals(expectedCreatedAt, category.audit.updatedAt)
            assertEquals(expectedName, category.name.value)
            assertEquals(expectedType, category.type)
            assertEquals(expectedIcon, category.appearance.icon.value)
            assertEquals(expectedColor, category.appearance.color.value)
        }

        @Test
        fun `given invalid id, then throw exception`() {
            val expectedId = UUID.fromString("47a595fd-7bfb-4884-a595-fd7bfbe8847c")
            val expectedCreatedAt = Instant.parse("2026-01-01T00:00:00.00Z")
            val expectedName = "Lazer"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "house"
            val expectedColor = "#FFFFFF"

            assertFailsWith<ValidationException> {
                Category.with(
                    id = expectedId,
                    createdAt = expectedCreatedAt,
                    updatedAt = expectedCreatedAt,
                    name = expectedName,
                    type = expectedType,
                    icon = expectedIcon,
                    color = expectedColor
                )
            }.also {
                assertEquals(CategoryErrorCatalog.UUID_VERSION, it.detail.error)
                assertEquals("category.id", it.detail.field)
                assertEquals(expectedId, it.detail.rejectedValue)
            }
        }

        @Test
        fun `given invalid updateAt, then throw exception`() {
            val expectedId = UUID.fromString("019cdab8-93fd-7262-8526-786a9ca26d05")
            val expectedCreatedAt = Instant.parse("2026-01-01T01:00:00.00Z")
            val expectedUpdateAt = Instant.parse("2026-01-01T00:00:00.00Z")
            val expectedName = "Lazer"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "house"
            val expectedColor = "#FFFFFF"

            assertFailsWith<ValidationException> {
                Category.with(
                    id = expectedId,
                    createdAt = expectedCreatedAt,
                    updatedAt = expectedUpdateAt,
                    name = expectedName,
                    type = expectedType,
                    icon = expectedIcon,
                    color = expectedColor
                )
            }.also {
                assertEquals("invalid.audit.updatedAt", it.detail.error.code)
                assertEquals("updated at must be after created at", it.detail.error.defaultMessage)
                assertEquals("audit.updatedAt", it.detail.field)
                assertEquals(expectedUpdateAt, it.detail.rejectedValue)
            }
        }

        @Test
        fun `given invalid name, then throw exception`() {
            val expectedId = UUID.fromString("019cdab8-93fd-7262-8526-786a9ca26d05")
            val expectedCreatedAt = Instant.parse("2026-01-01T00:00:00.00Z")
            val expectedName = "   "
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "house"
            val expectedColor = "#FFFFFF"

            assertFailsWith<ValidationException> {
                Category.with(
                    id = expectedId,
                    createdAt = expectedCreatedAt,
                    updatedAt = expectedCreatedAt,
                    name = expectedName,
                    type = expectedType,
                    icon = expectedIcon,
                    color = expectedColor
                )
            }.also {
                assertEquals(CategoryErrorCatalog.NAME_BLANK, it.detail.error)
                assertEquals("category.name", it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
        }

        @Test
        fun `given invalid icon, then throw exception`() {
            val expectedId = UUID.fromString("019cdab8-93fd-7262-8526-786a9ca26d05")
            val expectedCreatedAt = Instant.parse("2026-01-01T00:00:00.00Z")
            val expectedName = "Lazer"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = ""
            val expectedColor = "#FFFFFF"

            assertFailsWith<ValidationException> {
                Category.with(
                    id = expectedId,
                    createdAt = expectedCreatedAt,
                    updatedAt = expectedCreatedAt,
                    name = expectedName,
                    type = expectedType,
                    icon = expectedIcon,
                    color = expectedColor
                )
            }.also {
                assertEquals(CategoryErrorCatalog.ICON_BLANK, it.detail.error)
                assertEquals("category.appearance.icon", it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
        }

        @Test
        fun `given invalid color, then throw exception`() {
            val expectedId = UUID.fromString("019cdab8-93fd-7262-8526-786a9ca26d05")
            val expectedCreatedAt = Instant.parse("2026-01-01T00:00:00.00Z")
            val expectedName = "Lazer"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "house"
            val expectedColor = "invalid"

            assertFailsWith<ValidationException> {
                Category.with(
                    id = expectedId,
                    createdAt = expectedCreatedAt,
                    updatedAt = expectedCreatedAt,
                    name = expectedName,
                    type = expectedType,
                    icon = expectedIcon,
                    color = expectedColor
                )
            }.also {
                assertEquals(CategoryErrorCatalog.COLOR_INVALID, it.detail.error)
                assertEquals("category.appearance.color", it.detail.field)
                assertEquals(expectedColor, it.detail.rejectedValue)
            }
        }
    }

    @Nested
    inner class Update {
        @Test
        fun `given valid params, then update category`() {
            val category = Category.create(
                name = "Food",
                type = CategoryType.EXPENSE,
                icon = "fast-food",
                color = "#FF0000"
            )
            val updatedAt = category.audit.updatedAt
            val name = "Lazer"
            val icon = "house"
            val color = "#FFFFFF"

            category.update(name, icon, color)

            assertEquals(name, category.name.value)
            assertEquals(icon, category.appearance.icon.value)
            assertEquals(color, category.appearance.color.value)
            assertNotSame(updatedAt, category.audit.updatedAt)
        }

        @Test
        fun `given invalid params, then throw exception`() {
            val category = Category.create(
                name = "Food",
                type = CategoryType.EXPENSE,
                icon = "fast-food",
                color = "#FF0000"
            )
            val updatedAt = category.audit.updatedAt
            val name = ""
            val icon = "fast-food"
            val color = "#FF0000"

            assertFailsWith<ValidationException> { category.update(name, icon, color) }
                .also {
                    assertEquals(CategoryErrorCatalog.NAME_BLANK, it.detail.error)
                    assertEquals("category.name", it.detail.field)
                    assertNull(it.detail.rejectedValue)
                }
            assertEquals(updatedAt, category.audit.updatedAt)
        }
    }
}
