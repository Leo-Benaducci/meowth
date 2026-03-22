package br.com.lbenaducci.meowth.features.category.domain.valueobjects

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CategoryIconTest {
    @Nested
    inner class Constructor {
        @Test
        fun `given valid icon, then instantiate CategoryIcon`() {
            val icon = "house"
            val categoryIcon = CategoryIcon(icon)
            assertEquals(icon, categoryIcon.value)
        }

        @Test
        fun `given blank icon, then throw exception`() {
            val icon = "   "
            assertFailsWith<ValidationException> { CategoryIcon(icon) }
                .also {
                    assertEquals(CategoryErrorCatalog.ICON_BLANK, it.detail.error)
                    assertEquals("category.appearance.icon", it.detail.field)
                    assertNull(it.detail.rejectedValue)
                }
        }

        @Test
        fun `given long icon, then throw exception`() {
            val icon = "a".repeat(31)
            assertFailsWith<ValidationException> { CategoryIcon(icon) }
                .also {
                    assertEquals(CategoryErrorCatalog.ICON_LONG, it.detail.error)
                    assertEquals("category.appearance.icon", it.detail.field)
                    assertEquals(icon, it.detail.rejectedValue)
                }
        }
    }
}
