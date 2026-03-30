package br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryMongoEntity
import org.junit.jupiter.api.Nested
import java.time.Instant
import java.util.*
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
            assertEquals(aCategory.audit.createdAt, actualEntity.createdAt)
            assertEquals(aCategory.audit.updatedAt, actualEntity.updatedAt)
            assertEquals(expectedName, actualEntity.name)
            assertEquals(expectedType, actualEntity.type)
            assertEquals(expectedIcon, actualEntity.icon)
            assertEquals(expectedColor, actualEntity.color)
        }
    }

    @Nested
    inner class ToEntity {
        @Test
        fun `given a valid CategoryMongoEntity, then return a Category`() {
            val expectedId = UUID.fromString("019cdab8-93fd-7262-8526-786a9ca26d05")
            val expectedCreatedAt = Instant.parse("2026-01-01T00:00:00.00Z")
            val expectedName = "Lazer"
            val expectedType = CategoryType.EXPENSE
            val expectedIcon = "house"
            val expectedColor = "#FFFFFF"

            val aCategory = CategoryMongoEntity(
                id = expectedId,
                createdAt = expectedCreatedAt,
                updatedAt = expectedCreatedAt,
                name = expectedName,
                type = expectedType,
                icon = expectedIcon,
                color = expectedColor
            )

            val actualCategory = aCategory.toEntity()

            assertEquals(expectedId, actualCategory.id.value)
            assertEquals(expectedCreatedAt, actualCategory.audit.createdAt)
            assertEquals(expectedCreatedAt, actualCategory.audit.updatedAt)
            assertEquals(expectedName, actualCategory.name.value)
            assertEquals(expectedType, actualCategory.type)
            assertEquals(expectedIcon, actualCategory.appearance.icon.value)
            assertEquals(expectedColor, actualCategory.appearance.color.value)
        }
    }
}