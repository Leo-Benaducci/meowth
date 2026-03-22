package br.com.lbenaducci.meowth.features.category.application.usecases.create

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.features.category.domain.gateways.CategoryGateway
import br.com.lbenaducci.meowth.shared.domain.exceptions.RepositoryException
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import kotlin.test.*

@ExtendWith(MockitoExtension::class)
class CreateCategoryUseCaseTest {
    @Mock
    private lateinit var gateway: CategoryGateway

    @InjectMocks
    private lateinit var useCase: CreateCategoryUseCase

    @Test
    fun `given valid input, then save category and return id`() {
        val input = CreateCategoryInput(
            name = "Test Category",
            type = CategoryType.EXPENSE,
            icon = "icon",
            color = "#FF0000"
        )

        val output = useCase.execute(input)

        assertNotNull(output.id)
        verify(gateway).save(any())
    }

    @Test
    fun `given invalid input, then throw exception`() {
        val input = CreateCategoryInput(
            name = "Test Category",
            type = CategoryType.INCOME,
            icon = "icon",
            color = "red"
        )

        assertFailsWith<ValidationException> { useCase.execute(input) }
            .also { assertEquals(CategoryErrorCatalog.COLOR_INVALID, it.detail.error) }

        verify(gateway, never()).save(any())
    }

    @Test
    fun `given repository error, then throw RepositoryException`() {
        val input = CreateCategoryInput(
            name = "Test Category",
            type = CategoryType.EXPENSE,
            icon = "icon",
            color = "#FF0000"
        )

        whenever(gateway.save(any())).doThrow(RuntimeException("Repository failed"))

        assertFailsWith<RepositoryException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.REPOSITORY_ERROR, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }

        verify(gateway).save(any())
    }
}