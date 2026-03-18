package br.com.lbenaducci.meowth.features.category.domain.datatypes

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CategoryTypeTest {
    @Test
    fun `given income type, then verify enum exists`() {
        val type = CategoryType.valueOf("INCOME")
        assertEquals(CategoryType.INCOME, type)
    }

    @Test
    fun `given expense type, then verify enum exists`() {
        val type = CategoryType.valueOf("EXPENSE")
        assertEquals(CategoryType.EXPENSE, type)
    }

    @Test
    fun `given account type, then verify enum exists`() {
        val type = CategoryType.valueOf("ACCOUNT")
        assertEquals(CategoryType.ACCOUNT, type)
    }

    @Test
    fun `verify all enum values`() {
        val expectedValues = listOf(CategoryType.INCOME, CategoryType.EXPENSE, CategoryType.ACCOUNT)
        assertEquals(expectedValues, CategoryType.entries)
    }
}
