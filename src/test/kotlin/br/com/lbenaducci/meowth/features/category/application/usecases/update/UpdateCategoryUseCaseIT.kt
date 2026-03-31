package br.com.lbenaducci.meowth.features.category.application.usecases.update

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryMongoEntity
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryRepository
import br.com.lbenaducci.meowth.platform.IntegrationTest
import br.com.lbenaducci.meowth.platform.MongoTestContainer
import br.com.lbenaducci.meowth.shared.domain.exceptions.NotFoundException
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.fail
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotSame

@IntegrationTest
class UpdateCategoryUseCaseIT : MongoTestContainer() {
    @Autowired
    private lateinit var repository: CategoryRepository

    @Autowired
    private lateinit var useCase: UpdateCategoryUseCase

    @Test
    fun `given valid id, then update category`() {
        val id = "019cdab8-93fd-7262-8526-786a9ca26d05"
        val createdAt = Instant.parse("2026-01-01T00:00:00.000Z")
        val name = "Food"
        val type = CategoryType.EXPENSE
        val icon = "food"
        val color = "#000000"
        val category = CategoryMongoEntity(
            id = UUID.fromString(id),
            createdAt = createdAt,
            updatedAt = createdAt,
            name = "Test Category",
            type = type,
            icon = "icon",
            color = "#FF0000"
        )
        val input = UpdateCategoryInput(id, name, icon, color)
        assertEquals(0, repository.count())
        repository.save(category)
        assertEquals(1, repository.count())

        useCase.execute(input)

        assertEquals(1, repository.count())
        val fetchedCategory = repository.findById(UUID.fromString(id))
            .orElseGet { fail("ID does not exist") }
        assertNotSame(fetchedCategory.createdAt, fetchedCategory.updatedAt)
        assertEquals(name, fetchedCategory.name)
        assertEquals(type, fetchedCategory.type)
        assertEquals(icon, fetchedCategory.icon)
        assertEquals(color, fetchedCategory.color)
    }

    @Test
    fun `given invalid id, then throw exception`() {
        val input = UpdateCategoryInput("invalid-id", "Food", "icon", "#FF0000")

        assertFailsWith<ValidationException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.UUID_INVALID, it.detail.error)
                assertEquals("category.id", it.detail.field)
                assertEquals("invalid-id", it.detail.rejectedValue)
            }
    }

    @Test
    fun `given category not found, then throw exception`() {
        val id = "019cdab8-93fd-7262-8526-786a9ca26d05"
        val input = UpdateCategoryInput(id, "Food", "icon", "#FF0000")

        assertFailsWith<NotFoundException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.NOT_FOUND, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
    }

    @Test
    fun `given invalid params, then throw exception`() {
        val id = "019cdab8-93fd-7262-8526-786a9ca26d05"
        val createdAt = Instant.parse("2026-01-01T00:00:00.000Z")
        val name = "Food"
        val type = CategoryType.EXPENSE
        val icon = "food"
        val color = "red"
        val category = CategoryMongoEntity(
            id = UUID.fromString(id),
            createdAt = createdAt,
            updatedAt = createdAt,
            name = "Test Category",
            type = type,
            icon = "icon",
            color = "#FF0000"
        )
        val input = UpdateCategoryInput(id, name, icon, color)
        assertEquals(0, repository.count())
        repository.save(category)
        assertEquals(1, repository.count())

        assertFailsWith<ValidationException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.COLOR_INVALID, it.detail.error)
                assertEquals("category.appearance.color", it.detail.field)
                assertEquals("red", it.detail.rejectedValue)
            }
        assertEquals(1, repository.count())
        val fetchedCategory = repository.findById(UUID.fromString(id))
            .orElseGet { fail("ID does not exist") }
        assertEquals(fetchedCategory.createdAt, fetchedCategory.updatedAt)
        assertEquals("Test Category", fetchedCategory.name)
        assertEquals(type, fetchedCategory.type)
        assertEquals("icon", fetchedCategory.icon)
        assertEquals("#FF0000", fetchedCategory.color)
    }
}