package br.com.lbenaducci.meowth.features.category.infrastructure.presentation

import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryRepository
import br.com.lbenaducci.meowth.platform.E2ETest
import br.com.lbenaducci.meowth.platform.MongoTestContainer
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
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
}
