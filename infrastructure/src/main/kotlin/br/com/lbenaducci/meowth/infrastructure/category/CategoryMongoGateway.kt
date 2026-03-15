package br.com.lbenaducci.meowth.infrastructure.category

import br.com.lbenaducci.meowth.category.domain.entities.Category
import br.com.lbenaducci.meowth.category.domain.gateways.CategoryGateway
import br.com.lbenaducci.meowth.infrastructure.category.persistence.CategoryRepository
import br.com.lbenaducci.meowth.infrastructure.category.persistence.mappers.toMongoEntity
import org.springframework.stereotype.Component

@Component
class CategoryMongoGateway(
    private val repository: CategoryRepository
) : CategoryGateway {
    override fun save(category: Category) {
        repository.save(category.toMongoEntity())
    }
}