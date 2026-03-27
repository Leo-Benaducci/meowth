package br.com.lbenaducci.meowth.features.category.infrastructure.persistence

import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.domain.gateways.CategoryGateway
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryId
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers.toEntity
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers.toMongoEntity
import org.springframework.stereotype.Component

@Component
class CategoryMongoGateway(
    private val repository: CategoryRepository
) : CategoryGateway {
    override fun save(category: Category) {
        repository.save(category.toMongoEntity())
    }

    override fun findById(id: CategoryId): Category? {
        return repository.findById(id.value)
            .map { it.toEntity() }
            .orElse(null)
    }
}