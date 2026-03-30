package br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryMongoEntity
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryRepository
import br.com.lbenaducci.meowth.platform.IntegrationTest
import br.com.lbenaducci.meowth.platform.MongoTestContainer
import br.com.lbenaducci.meowth.shared.domain.exceptions.NotFoundException
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Assertions.assertNull
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

@IntegrationTest
class FindCategoryByIdUseCaseIT : MongoTestContainer() {
    @Autowired
    private lateinit var repository: CategoryRepository

    @Autowired
    private lateinit var useCase: FindCategoryByIdUseCase

    @Test
    fun `given valid id, then return category`() {
        val id = "019cdab8-93fd-7262-8526-786a9ca26d05"
        val createdAt = Instant.parse("2026-01-01T00:00:00.000Z")
        val name = "Test Category"
        val type = CategoryType.EXPENSE
        val icon = "icon"
        val color = "#FF0000"
        val category = CategoryMongoEntity(
            id = UUID.fromString(id),
            createdAt = createdAt,
            updatedAt = createdAt,
            name = name,
            type = type,
            icon = icon,
            color = color
        )
        val input = FindCategoryByIdInput(id)
        assertEquals(0, repository.count())
        repository.save(category)
        assertEquals(1, repository.count())

        val actual = useCase.execute(input)

        assertNotNull(actual)
        assertEquals(id, actual.id)
        assertEquals(createdAt, actual.createdAt)
        assertEquals(createdAt, actual.updatedAt)
        assertEquals(name, actual.name)
        assertEquals(type, actual.type)
        assertEquals(icon, actual.icon)
        assertEquals(color, actual.color)
    }

    @Test
    fun `given invalid id, then throw exception`() {
        val input = FindCategoryByIdInput("invalid-id")

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
        val input = FindCategoryByIdInput(id)
        assertEquals(0, repository.count())

        assertFailsWith<NotFoundException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.NOT_FOUND, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
    }
}