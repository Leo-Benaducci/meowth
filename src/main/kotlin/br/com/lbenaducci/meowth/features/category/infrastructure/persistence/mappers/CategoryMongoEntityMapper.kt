package br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers

import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryMongoEntity

fun Category.toMongoEntity(): CategoryMongoEntity {
    return CategoryMongoEntity(
        id = this.id.value,
        createdAt = this.createdAt,
        name = this.name.value,
        type = this.type,
        icon = this.appearance.icon.value,
        color = this.appearance.color.value
    )
}