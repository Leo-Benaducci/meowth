package br.com.lbenaducci.meowth.infrastructure.category

import br.com.lbenaducci.meowth.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.category.domain.entities.Category
import br.com.lbenaducci.meowth.infrastructure.MongoRepositoryTest
import br.com.lbenaducci.meowth.infrastructure.MongoTestContainer
import br.com.lbenaducci.meowth.infrastructure.category.persistence.CategoryRepository
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@MongoRepositoryTest
class CategoryMongoGatewayTest: MongoTestContainer() {
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
            assertEquals(category.createdAt, fetchedCategory.createdAt)
            assertEquals(name, fetchedCategory.name)
            assertEquals(type, fetchedCategory.type)
            assertEquals(icon, fetchedCategory.icon)
            assertEquals(color, fetchedCategory.color)
        }
    }
}