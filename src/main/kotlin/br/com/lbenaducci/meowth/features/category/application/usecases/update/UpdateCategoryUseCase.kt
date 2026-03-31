package br.com.lbenaducci.meowth.features.category.application.usecases.update

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.features.category.domain.gateways.CategoryGateway
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryId
import br.com.lbenaducci.meowth.shared.application.usecases.UseCase
import br.com.lbenaducci.meowth.shared.domain.exceptions.NotFoundException
import br.com.lbenaducci.meowth.shared.domain.exceptions.RepositoryException
import org.slf4j.LoggerFactory

class UpdateCategoryUseCase(
    private val gateway: CategoryGateway
) : UseCase<UpdateCategoryInput, Unit> {
    private val log = LoggerFactory.getLogger(UpdateCategoryUseCase::class.java)

    override fun execute(input: UpdateCategoryInput) {
        val id = CategoryId(input.id)
        val category = runCatching { gateway.findById(id) }
            .onFailure {
                log.error("Error retrieving category by id", it)
                throw RepositoryException(CategoryErrorCatalog.REPOSITORY_ERROR)
            }.getOrNull() ?: throw NotFoundException(CategoryErrorCatalog.NOT_FOUND)
        category.update(input.name, input.icon, input.color)
        try {
            gateway.save(category)
        } catch (e: Exception) {
            log.error("Error updating category", e)
            throw RepositoryException(CategoryErrorCatalog.REPOSITORY_ERROR)
        }
    }
}