package br.com.lbenaducci.meowth.features.category.infrastructure.persistence

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryId
import br.com.lbenaducci.meowth.platform.MongoRepositoryTest
import br.com.lbenaducci.meowth.platform.MongoTestContainer
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@MongoRepositoryTest
class CategoryMongoGatewayTest : MongoTestContainer() {
    @Autowired
    private lateinit var gateway: CategoryMongoGateway

    @Autowired
    private lateinit var repository: CategoryRepository

    @Nested
    inner class Create {
        @Test
        fun `given a valid category, then create a new category`() {
            val name = "Test Category"
            val type = CategoryType.ACCOUNT
            val icon = "account"
            val color = "#000000"
            val category = Category.create(
                name = name,
                type = type,
                icon = icon,
                color = color,
            )
            assertEquals(0, repository.count())

            gateway.save(category)

            assertEquals(1, repository.count())
            val fetchedCategory = repository.findById(category.id.value)
                .orElseGet { fail("Category not found") }
            assertEquals(category.audit.createdAt, fetchedCategory.createdAt)
            assertEquals(category.audit.updatedAt, fetchedCategory.updatedAt)
            assertEquals(name, fetchedCategory.name)
            assertEquals(type, fetchedCategory.type)
            assertEquals(icon, fetchedCategory.icon)
            assertEquals(color, fetchedCategory.color)
        }
    }

    @Nested
    inner class FindById {
        @Test
        fun `given a non-existent id, then return null`() {
            val nonExistentId = CategoryId("019d3073-c28f-7548-a502-e69c4f2b623a")
            assertEquals(0, repository.count())

            val fetchedCategory = gateway.findById(nonExistentId)

            assertNull(fetchedCategory)
        }

        @Test
        fun `given a valid id, then return the category`() {
            val id = CategoryId("019d3073-c28f-7548-a502-e69c4f2b623a")
            val category = CategoryMongoEntity(
                id = id.value,
                createdAt = Instant.now().truncatedTo(ChronoUnit.MILLIS),
                updatedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS),
                name = "Test Category",
                type = CategoryType.ACCOUNT,
                icon = "account",
                color = "#000000",
            )
            repository.save(category)
            assertEquals(1, repository.count())

            val fetchedCategory = gateway.findById(id)

            assertNotNull(fetchedCategory)
            assertEquals(category.id, fetchedCategory.id.value)
            assertEquals(category.createdAt, fetchedCategory.audit.createdAt)
            assertEquals(category.updatedAt, fetchedCategory.audit.updatedAt)
            assertEquals(category.name, fetchedCategory.name.value)
            assertEquals(category.type, fetchedCategory.type)
            assertEquals(category.icon, fetchedCategory.appearance.icon.value)
            assertEquals(category.color, fetchedCategory.appearance.color.value)
        }
    }
}