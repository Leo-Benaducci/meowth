package br.com.lbenaducci.meowth.features.category.infrastructure.presentation.requests

import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryInput
import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType

data class CreateCategoryRequest(
    val name: String,
    val type: CategoryType,
    val icon: String,
    val color: String
) {
    fun toInput(): CreateCategoryInput {
        return CreateCategoryInput(
            name = name,
            type = type,
            icon = icon,
            color = color
        )
    }
}
