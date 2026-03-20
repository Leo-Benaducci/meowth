package br.com.lbenaducci.meowth.features.category.infrastructure.presentation

import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.infrastructure.presentation.requests.CreateCategoryRequest
import br.com.lbenaducci.meowth.features.category.infrastructure.presentation.responses.CreateCategoryResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/v1/categories")
class CategoryRestController(
    private val createCategoryUseCase: CreateCategoryUseCase
) {
    @PostMapping
    fun create(@RequestBody request: CreateCategoryRequest): ResponseEntity<CreateCategoryResponse> {
        val output = createCategoryUseCase.execute(request.toInput())
        return ResponseEntity.created(URI("/categories/${output.id}"))
            .body(CreateCategoryResponse(output.id))
    }
}