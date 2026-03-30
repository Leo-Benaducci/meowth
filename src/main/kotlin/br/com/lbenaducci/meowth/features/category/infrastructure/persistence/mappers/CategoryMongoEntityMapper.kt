package br.com.lbenaducci.meowth.features.category.infrastructure.persistence.mappers

import br.com.lbenaducci.meowth.features.category.domain.entities.Category
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryMongoEntity

fun Category.toMongoEntity(): CategoryMongoEntity {
    return CategoryMongoEntity(
        id = this.id.value,
        createdAt = this.audit.createdAt,
        updatedAt = this.audit.updatedAt,
        name = this.name.value,
        type = this.type,
        icon = this.appearance.icon.value,
        color = this.appearance.color.value
    )
}

fun CategoryMongoEntity.toEntity(): Category {
    return Category.with(
        id = this.id,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        name = this.name,
        type = this.type,
        icon = this.icon,
        color = this.color
    )
}