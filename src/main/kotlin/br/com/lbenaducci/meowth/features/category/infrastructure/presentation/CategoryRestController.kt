package br.com.lbenaducci.meowth.features.category.infrastructure.presentation

import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get.FindCategoryByIdInput
import br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get.FindCategoryByIdUseCase
import br.com.lbenaducci.meowth.features.category.application.usecases.update.UpdateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.infrastructure.presentation.requests.CreateCategoryRequest
import br.com.lbenaducci.meowth.features.category.infrastructure.presentation.requests.UpdateCategoryRequest
import br.com.lbenaducci.meowth.features.category.infrastructure.presentation.responses.CreateCategoryResponse
import br.com.lbenaducci.meowth.features.category.infrastructure.presentation.responses.FindCategoryByIdResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/v1/categories")
class CategoryRestController(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val findCategoryByIdUseCase: FindCategoryByIdUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
) {
    @PostMapping
    fun create(@RequestBody request: CreateCategoryRequest): ResponseEntity<CreateCategoryResponse> {
        val output = createCategoryUseCase.execute(request.toInput())
        return ResponseEntity.created(URI("/categories/${output.id}"))
            .body(CreateCategoryResponse(output.id))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<FindCategoryByIdResponse> {
        val output = findCategoryByIdUseCase.execute(FindCategoryByIdInput(id))
        return ResponseEntity.ok(FindCategoryByIdResponse.from(output))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody request: UpdateCategoryRequest) {
        updateCategoryUseCase.execute(request.toInput(id))
    }
}