package br.com.lbenaducci.meowth.features.category.infrastructure

import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.domain.gateways.CategoryGateway
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryRepository
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers.toMongoEntity
import org.springframework.stereotype.Component

@Component
class CategoryMongoGateway(
    private val repository: CategoryRepository
) : CategoryGateway {
    override fun save(category: Category) {
        repository.save(category.toMongoEntity())
    }
}