package br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import java.time.Instant


data class FindCategoryByIdOutput(
    val id: String,
    val createdAt: Instant,
    val name: String,
    val type: CategoryType,
    val icon: String,
    val color: String
)