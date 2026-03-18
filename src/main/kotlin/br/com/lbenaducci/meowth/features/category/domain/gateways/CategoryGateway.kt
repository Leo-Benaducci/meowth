package br.com.lbenaducci.meowth.features.category.domain.gateways

import br.com.lbenaducci.meowth.features.category.domain.entities.Category

interface CategoryGateway {
    fun save(category: Category)
}