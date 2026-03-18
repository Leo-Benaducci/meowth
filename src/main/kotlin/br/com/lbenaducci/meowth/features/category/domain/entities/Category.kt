package br.com.lbenaducci.meowth.features.category.domain.entities

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryAppearance
import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryId
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryName
import java.time.Instant
import java.time.temporal.ChronoUnit

class Category private constructor(
    val id: CategoryId,
    val createdAt: Instant,
    val name: CategoryName,
    val type: CategoryType,
    val appearance: CategoryAppearance
) {
    companion object {
        fun create(
            name: String,
            type: CategoryType,
            icon: String,
            color: String
        ): Category {
            return Category(
                id = CategoryId.generate(),
                createdAt = Instant.now().truncatedTo(ChronoUnit.MILLIS),
                name = CategoryName(name),
                type = type,
                appearance = CategoryAppearance.with(icon, color)
            )
        }
    }
}