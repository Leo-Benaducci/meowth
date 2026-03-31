package br.com.lbenaducci.meowth.features.category.application.usecases.update

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
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotSame

@ExtendWith(MockitoExtension::class)
class UpdateCategoryUseCaseTest {
    @Mock
    private lateinit var gateway: CategoryGateway

    @InjectMocks
    private lateinit var useCase: UpdateCategoryUseCase

    @Test
    fun `given valid params, then update category`() {
        val name = "Food"
        val type = CategoryType.EXPENSE
        val icon = "food"
        val color = "#000000"
        val category = Category.create("Test Category", type, "icon", "#FF0000")
        val id = category.id.value.toString()
        val input = UpdateCategoryInput(id, name, icon, color)
        whenever { gateway.findById(any()) }.thenReturn(category)

        useCase.execute(input)

        assertEquals(id, category.id.value.toString())
        assertNotSame(category.audit.createdAt, category.audit.updatedAt)
        assertEquals(name, category.name.value)
        assertEquals(type, category.type)
        assertEquals(icon, category.appearance.icon.value)
        assertEquals(color, category.appearance.color.value)
        verify(gateway).save(category)
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
        whenever { gateway.findById(any()) }.thenReturn(null)

        assertFailsWith<NotFoundException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.NOT_FOUND, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
    }

    @Test
    fun `given repository error to find, then throw exception`() {
        val id = "019cdab8-93fd-7262-8526-786a9ca26d05"
        val input = UpdateCategoryInput(id, "Food", "icon", "#FF0000")
        whenever { gateway.findById(any()) }.thenThrow(RuntimeException("Repository failed"))

        assertFailsWith<RepositoryException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.REPOSITORY_ERROR, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
    }

    @Test
    fun `given invalid params, then throw exception`() {
        val name = "Food"
        val type = CategoryType.EXPENSE
        val icon = "food"
        val color = "red"
        val category = Category.create("Test Category", type, "icon", "#FF0000")
        val id = category.id.value.toString()
        val input = UpdateCategoryInput(id, name, icon, color)
        whenever { gateway.findById(any()) }.thenReturn(category)

        assertFailsWith<ValidationException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.COLOR_INVALID, it.detail.error)
                assertEquals("category.appearance.color", it.detail.field)
                assertEquals("red", it.detail.rejectedValue)
            }
        verify(gateway, never()).save(category)
    }

    @Test
    fun `given repository error to save, then throw exception`() {
        val name = "Food"
        val type = CategoryType.EXPENSE
        val icon = "food"
        val color = "#000000"
        val category = Category.create("Test Category", type, "icon", "#FF0000")
        val id = category.id.value.toString()
        val input = UpdateCategoryInput(id, name, icon, color)
        whenever { gateway.findById(any()) }.thenReturn(category)
        whenever { gateway.save(any()) }.thenThrow(RuntimeException("Repository failed"))

        assertFailsWith<RepositoryException> { useCase.execute(input) }
            .also {
                assertEquals(CategoryErrorCatalog.REPOSITORY_ERROR, it.detail.error)
                assertNull(it.detail.field)
                assertNull(it.detail.rejectedValue)
            }
    }
}