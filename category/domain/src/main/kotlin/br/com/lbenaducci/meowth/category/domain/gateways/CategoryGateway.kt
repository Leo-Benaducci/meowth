package br.com.lbenaducci.meowth.category.domain.gateways

import br.com.lbenaducci.meowth.category.domain.entities.Category

interface CategoryGateway {
    fun save(category: Category)
}