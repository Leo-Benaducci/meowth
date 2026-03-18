package br.com.lbenaducci.meowth.features.category.domain.datatypes

import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryAppearanceTest {
    @Nested
    inner class With {
        @Test
        fun `given valid icon and color, then instantiate CategoryAppearance`() {
            val icon = "house"
            val color = "#FF0000"

            val appearance = CategoryAppearance.with(icon, color)

            assertEquals(icon, appearance.icon.value)
            assertEquals(color, appearance.color.value)
        }
    }
}