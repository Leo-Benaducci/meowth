package br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.features.category.domain.gateways.CategoryGateway
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryId
import br.com.lbenaducci.meowth.shared.application.usecases.UseCase
import br.com.lbenaducci.meowth.shared.domain.exceptions.NotFoundException
import br.com.lbenaducci.meowth.shared.domain.exceptions.RepositoryException
import org.slf4j.LoggerFactory

class FindCategoryByIdUseCase(
    private val gateway: CategoryGateway
) : UseCase<FindCategoryByIdInput, FindCategoryByIdOutput> {
    private val log = LoggerFactory.getLogger(FindCategoryByIdUseCase::class.java)

    override fun execute(input: FindCategoryByIdInput): FindCategoryByIdOutput {
        val id = CategoryId(input.id)
        val category = runCatching { gateway.findById(id) }
            .onFailure {
                log.error("Error retrieving category by id", it)
                throw RepositoryException(CategoryErrorCatalog.REPOSITORY_ERROR)
            }.getOrNull() ?: throw NotFoundException(CategoryErrorCatalog.NOT_FOUND)
        return FindCategoryByIdOutput(
            id = category.id.value.toString(),
            createdAt = category.audit.createdAt,
            updatedAt = category.audit.updatedAt,
            name = category.name.value,
            type = category.type,
            icon = category.appearance.icon.value,
            color = category.appearance.color.value
        )
    }
}