package br.com.lbenaducci.meowth.features.category.infrastructure.presentation

import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryOutput
import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get.FindCategoryByIdOutput
import br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get.FindCategoryByIdUseCase
import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.platform.ControllerTest
import br.com.lbenaducci.meowth.shared.domain.exceptions.NotFoundException
import br.com.lbenaducci.meowth.shared.domain.exceptions.RepositoryException
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.Instant
import java.util.*
import kotlin.test.Test

@ControllerTest(controllers = [CategoryRestController::class])
class CategoryRestControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var createCategoryUseCase: CreateCategoryUseCase

    @MockitoBean
    private lateinit var findCategoryByIdUseCase: FindCategoryByIdUseCase

    @Nested
    inner class Create {
        @Test
        fun `given valid input, then return 201 and category id`() {
            val requestBody = """
            {
                "name": "Test Category",
                "type": "EXPENSE",
                "icon": "icon",
                "color": "#FF0000"
            }
        """.trimIndent()
            val outputId = UUID.randomUUID().toString()
            whenever(createCategoryUseCase.execute(any()))
                .thenReturn(CreateCategoryOutput(outputId))

            mockMvc.post("/v1/categories") {
                contentType = MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isCreated() }
                header { string("Location", "/categories/$outputId") }
                jsonPath("$.id") { value(outputId) }
            }
        }

        @Test
        fun `given invalid input, then return 400`() {
            val requestBody = """
            {
                "name": "Test",
                "type": "EXPENSE",
                "icon": "icon",
                "color": "red"
            }
        """.trimIndent()

            whenever(createCategoryUseCase.execute(any()))
                .thenThrow(ValidationException(CategoryErrorCatalog.COLOR_INVALID, "category.appearance.color", "red"))

            mockMvc.post("/v1/categories") {
                contentType = MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isBadRequest() }
                jsonPath("$.timestamp") { exists() }
                jsonPath("$.status") { value("BAD_REQUEST") }
                jsonPath("$.path") { value("/v1/categories") }
                jsonPath("$.title") { value("Bad Request") }
                jsonPath("$.details.code") { value("invalid.category.color") }
                jsonPath("$.details.message") { value("category color must be a valid hex color code") }
                jsonPath("$.details.field") { value("category.appearance.color") }
                jsonPath("$.details.rejectedValue") { value("red") }
            }
        }

        @Test
        fun `given valid input, when call throw RepositoryException, then return 422`() {
            val requestBody = """
            {
                "name": "Test Category",
                "type": "EXPENSE",
                "icon": "icon",
                "color": "#FF0000"
            }
        """.trimIndent()
            whenever(createCategoryUseCase.execute(any()))
                .thenThrow(RepositoryException(CategoryErrorCatalog.REPOSITORY_ERROR))

            mockMvc.post("/v1/categories") {
                contentType = MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isInternalServerError() }
                jsonPath("$.timestamp") { exists() }
                jsonPath("$.status") { value("INTERNAL_SERVER_ERROR") }
                jsonPath("$.path") { value("/v1/categories") }
                jsonPath("$.title") { value("Internal Server Error") }
                jsonPath("$.details.code") { value("error.category.repository.unexpected") }
                jsonPath("$.details.message") { value("an unexpected error occurred in the category repository") }
            }
        }
    }

    @Nested
    inner class FindById {
        @Test
        fun `given valid id, then return 200 and category`() {
            val id = UUID.randomUUID().toString()
            val output = FindCategoryByIdOutput(
                id = id,
                name = "Test Category",
                type = CategoryType.EXPENSE,
                icon = "icon",
                color = "#FF0000",
                createdAt = Instant.now()
            )
            whenever(findCategoryByIdUseCase.execute(any()))
                .thenReturn(output)

            mockMvc.get("/v1/categories/$id")
                .andExpect {
                    status { isOk() }
                    jsonPath("$.id") { value(id) }
                    jsonPath("$.name") { value(output.name) }
                    jsonPath("$.type") { value(output.type.name) }
                    jsonPath("$.icon") { value(output.icon) }
                    jsonPath("$.color") { value(output.color) }
                    jsonPath("$.createdAt") { exists() }
                }
        }

        @Test
        fun `given non existing id, then return 404`() {
            val id = UUID.randomUUID().toString()
            whenever(findCategoryByIdUseCase.execute(any()))
                .thenThrow(NotFoundException(CategoryErrorCatalog.NOT_FOUND))

            mockMvc.get("/v1/categories/$id")
                .andExpect {
                    status { isNotFound() }
                    jsonPath("$.timestamp") { exists() }
                    jsonPath("$.status") { value("NOT_FOUND") }
                    jsonPath("$.path") { value("/v1/categories/$id") }
                    jsonPath("$.title") { value("Not Found") }
                    jsonPath("$.details.code") { value("error.category.not.found") }
                    jsonPath("$.details.message") { value("category not found") }
                }
        }

        @Test
        fun `given valid id, when call throw RepositoryException, then return 500`() {
            val id = UUID.randomUUID().toString()
            whenever(findCategoryByIdUseCase.execute(any()))
                .thenThrow(RepositoryException(CategoryErrorCatalog.REPOSITORY_ERROR))

            mockMvc.get("/v1/categories/$id")
                .andExpect {
                    status { isInternalServerError() }
                    jsonPath("$.timestamp") { exists() }
                    jsonPath("$.status") { value("INTERNAL_SERVER_ERROR") }
                    jsonPath("$.path") { value("/v1/categories/$id") }
                    jsonPath("$.title") { value("Internal Server Error") }
                    jsonPath("$.details.code") { value("error.category.repository.unexpected") }
                    jsonPath("$.details.message") { value("an unexpected error occurred in the category repository") }
                }
        }
    }
}