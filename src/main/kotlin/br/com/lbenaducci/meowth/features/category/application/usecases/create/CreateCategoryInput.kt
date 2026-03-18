package br.com.lbenaducci.meowth.features.category.application.usecases.create

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType

data class CreateCategoryInput(
    val name: String,
    val type: CategoryType,
    val icon: String,
    val color: String
)
