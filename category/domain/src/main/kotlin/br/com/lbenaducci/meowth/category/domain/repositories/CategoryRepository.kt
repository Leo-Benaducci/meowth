package br.com.lbenaducci.meowth.category.domain.repositories

import br.com.lbenaducci.meowth.category.domain.entities.Category

interface CategoryRepository {
    fun save(category: Category)
}