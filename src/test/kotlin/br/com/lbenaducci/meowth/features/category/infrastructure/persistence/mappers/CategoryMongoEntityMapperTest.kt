package br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryMongoEntityMapperTest {
    @Nested
    inner class ToMongoEntity {
        @Test
        fun `given a valid category, then return a CategoryMongoEntity`() {
            val expectedName = "Lazer"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "house"
            val expectedColor = "#FFFFFF"

            val aCategory = Category.create(
                name = expectedName,
                type = expectedType,
                icon = expectedIcon,
                color = expectedColor
            )

            val actualEntity = aCategory.toMongoEntity()

            assertEquals(aCategory.id.value, actualEntity.id)
            assertEquals(aCategory.createdAt, actualEntity.createdAt)
            assertEquals(expectedName, actualEntity.name)
            assertEquals(expectedType, actualEntity.type)
            assertEquals(expectedIcon, actualEntity.icon)
            assertEquals(expectedColor, actualEntity.color)
        }
    }
}