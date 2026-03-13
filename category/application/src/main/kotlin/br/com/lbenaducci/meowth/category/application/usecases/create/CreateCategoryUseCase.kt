package br.com.lbenaducci.meowth.category.application.usecases.create

import br.com.lbenaducci.meowth.category.domain.entities.Category
import br.com.lbenaducci.meowth.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.category.domain.repositories.CategoryRepository
import br.com.lbenaducci.meowth.shared.exceptions.RepositoryException
import org.slf4j.LoggerFactory

class CreateCategoryUseCase(
    private val repository: CategoryRepository
) {
    private val log = LoggerFactory.getLogger(CreateCategoryUseCase::class.java)

    fun execute(input: CreateCategoryInput): CreateCategoryOutput {
        val category = Category.create(
            name = input.name,
            type = input.type,
            icon = input.icon,
            color = input.color
        )
        try {
            repository.save(category)
        } catch (e: Exception) {
            log.error("Error creating category", e)
            throw RepositoryException(CategoryErrorCatalog.REPOSITORY_ERROR)
        }
        return CreateCategoryOutput(id = category.id.value.toString())
    }
}