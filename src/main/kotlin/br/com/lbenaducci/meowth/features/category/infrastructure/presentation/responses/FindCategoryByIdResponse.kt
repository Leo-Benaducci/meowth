package br.com.lbenaducci.meowth.features.category.infrastructure.presentation.responses

import br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get.FindCategoryByIdOutput
import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import java.time.Instant

data class FindCategoryByIdResponse(
    val id: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val name: String,
    val type: CategoryType,
    val icon: String,
    val color: String
) {
    companion object {
        fun from(output: FindCategoryByIdOutput): FindCategoryByIdResponse {
            return FindCategoryByIdResponse(
                id = output.id,
                createdAt = output.createdAt,
                updatedAt = output.updatedAt,
                name = output.name,
                type = output.type,
                icon = output.icon,
                color = output.color
            )
        }
    }
}
