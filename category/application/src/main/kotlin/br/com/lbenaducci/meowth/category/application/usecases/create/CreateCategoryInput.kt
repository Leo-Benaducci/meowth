package br.com.lbenaducci.meowth.category.application.usecases.create

import br.com.lbenaducci.meowth.category.domain.datatypes.CategoryType

data class CreateCategoryInput(
    val name: String,
    val type: CategoryType,
    val icon: String,
    val color: String
)
