package br.com.lbenaducci.meowth.features.category.domain.entities

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryAppearance
import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryId
import br.com.lbenaducci.meowth.features.category.domain.valueobjects.CategoryName
import br.com.lbenaducci.meowth.shared.domain.datatypes.Audit
import java.time.Instant
import java.util.*

class Category private constructor(
    val id: CategoryId,
    val audit: Audit,
    name: CategoryName,
    val type: CategoryType,
    appearance: CategoryAppearance
) {
    var name: CategoryName = name
        private set
    var appearance: CategoryAppearance = appearance
        private set

    companion object {
        fun create(
            name: String,
            type: CategoryType,
            icon: String,
            color: String
        ): Category {
            return Category(
                id = CategoryId.generate(),
                audit = Audit.create(),
                name = CategoryName(name),
                type = type,
                appearance = CategoryAppearance.with(icon, color)
            )
        }

        fun with(
            id: UUID,
            createdAt: Instant,
            updatedAt: Instant,
            name: String,
            type: CategoryType,
            icon: String,
            color: String
        ): Category {
            return Category(
                id = CategoryId(id),
                audit = Audit.with(createdAt, updatedAt),
                name = CategoryName(name),
                type = type,
                appearance = CategoryAppearance.with(icon, color)
            )
        }
    }

    fun update(name: String, icon: String, color: String) {
        this.name = CategoryName(name)
        this.appearance = CategoryAppearance.with(icon, color)
        audit.update()
    }
}