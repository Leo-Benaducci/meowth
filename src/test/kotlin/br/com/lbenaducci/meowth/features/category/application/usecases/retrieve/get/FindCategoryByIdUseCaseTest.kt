package br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.features.category.domain.gateways.CategoryGateway
import br.com.lbenaducci.meowth.shared.domain.exceptions.NotFoundException
import br.com.lbenaducci.meowth.shared.domain.exceptions.RepositoryException
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class FindCategoryByIdUseCaseTest {
    @Mock
    private lateinit var gateway: CategoryGateway

    @InjectMocks
    private lateinit var useCase: FindCategoryByIdUseCase

    @Test
    fun `given valid id, then return category`() {
        val name = "Test Category"
        val type = CategoryType.EXPENSE
        val icon = "icon"
        val color = "#FF0000"
        val category = Category.create(name, type, icon, color)
        val id = category.id.value.toString()
        val input = FindCategoryByIdInput(id)
        whenever(gateway.findById(any())).thenReturn(category)

        val actual = useCase.execute(input)

        assertNotNull(actual)
        assertEquals(id, actual.id)
        assertEquals(category.audit.createdAt, actual.createdAt)
        assertEquals(category.audit.updatedAt, actual.updatedAt)
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
        whenever(gateway.findById(any())).thenReturn(null)

        assertFailsWith<NotFoundException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.NOT_FOUND, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
    }

    @Test
    fun `given repository error, then throw exception`() {
        val id = "019cdab8-93fd-7262-8526-786a9ca26d05"
        val input = FindCategoryByIdInput(id)
        whenever(gateway.findById(any())).thenThrow(RuntimeException("Repository failed"))

        assertFailsWith<RepositoryException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.REPOSITORY_ERROR, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
    }
}