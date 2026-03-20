package br.com.lbenaducci.meowth.features.category.application.usecases.create

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryRepository
import br.com.lbenaducci.meowth.platform.IntegrationTest
import br.com.lbenaducci.meowth.platform.MongoTestContainer
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@IntegrationTest
class CreateCategoryUseCaseIT : MongoTestContainer() {
    @Autowired
    private lateinit var repository: CategoryRepository

    @Autowired
    private lateinit var useCase: CreateCategoryUseCase

    @Test
    fun `given valid input, then save category and return id`() {
        val input = CreateCategoryInput(
            name = "Test Category",
            type = CategoryType.EXPENSE,
            icon = "icon",
            color = "#FF0000"
        )

        assertEquals(0, repository.count())

        val output = useCase.execute(input)

        assertNotNull(output.id)
        assertEquals(1, repository.count())
        val savedCategory = repository.findById(UUID.fromString(output.id))
            .orElseGet { fail("ID does not exist") }
        assertNotNull(savedCategory.createdAt)
        assertEquals(input.name, savedCategory.name)
        assertEquals(input.type, savedCategory.type)
        assertEquals(input.icon, savedCategory.icon)
        assertEquals(input.color, savedCategory.color)
    }

    @Test
    fun `given invalid input, then throw exception`() {
        val input = CreateCategoryInput(
            name = "Test Category",
            type = CategoryType.INCOME,
            icon = "icon",
            color = "red"
        )

        assertEquals(0, repository.count())

        assertFailsWith<ValidationException> { useCase.execute(input) }
            .also { assertEquals(CategoryErrorCatalog.COLOR_INVALID.code, it.code) }

        assertEquals(0, repository.count())
    }
}