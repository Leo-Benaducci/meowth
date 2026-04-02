package br.com.lbenaducci.meowth.features.category.infrastructure.presentation.requests

import br.com.lbenaducci.meowth.features.category.application.usecases.update.UpdateCategoryInput

data class UpdateCategoryRequest(
    val name: String,
    val icon: String,
    val color: String
) {
    fun toInput(id: String): UpdateCategoryInput {
        return UpdateCategoryInput(
            id = id,
            name = name,
            icon = icon,
            color = color
        )
    }
}
