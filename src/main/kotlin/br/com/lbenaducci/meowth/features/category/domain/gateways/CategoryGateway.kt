package br.com.lbenaducci.meowth.features.category.domain.gateways

import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryId

interface CategoryGateway {
    fun save(category: Category)

    fun findById(id: CategoryId): Category?
}