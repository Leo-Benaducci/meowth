package br.com.lbenaducci.meowth.features.category.infrastructure.presentation

import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get.FindCategoryByIdUseCase
import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryRepository
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers.toMongoEntity
import br.com.lbenaducci.meowth.platform.E2ETest
import br.com.lbenaducci.meowth.platform.MongoTestContainer
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import kotlin.test.Test
import kotlin.test.assertEquals

@E2ETest
class CategoryRestControllerE2ETest : MongoTestContainer() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var createCategoryUseCase: CreateCategoryUseCase

    @Autowired
    private lateinit var findCategoryByIdUseCase: FindCategoryByIdUseCase

    @Autowired
    private lateinit var repository: CategoryRepository

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
            assertEquals(0, repository.count())

            mockMvc.post("/v1/categories") {
                contentType = MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isCreated() }
                header { exists("Location") }
                jsonPath("$.id") { exists() }
            }

            assertEquals(1, repository.count())
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

            assertEquals(0, repository.count())

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

            assertEquals(0, repository.count())
        }
    }

    @Nested
    inner class FindById {
        @Test
        fun `given valid id, then return 200 and category`() {
            val category = Category.create(
                name = "Test Category",
                type = CategoryType.EXPENSE,
                icon = "icon",
                color = "#FF0000"
            )
            repository.save(category.toMongoEntity())

            val id = category.id.value.toString()

            mockMvc.get("/v1/categories/$id")
                .andExpect {
                    status { isOk() }
                    jsonPath("$.id") { value(id) }
                    jsonPath("$.name") { value(category.name.value) }
                    jsonPath("$.type") { value(category.type.name) }
                    jsonPath("$.icon") { value(category.appearance.icon.value) }
                    jsonPath("$.color") { value(category.appearance.color.value) }
                    jsonPath("$.createdAt") { exists() }
                }
        }

        @Test
        fun `given non existing id, then return 404`() {
            val id = "019d37d5-a8a8-7e00-84ba-1409b5305738"

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
    }
}
