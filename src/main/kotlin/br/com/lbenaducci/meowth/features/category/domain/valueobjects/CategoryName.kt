package br.com.lbenaducci.meowth.features.category.domain.valueobjects

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException

private const val FIELD = "category.name"

@JvmInline
value class CategoryName(
    val value: String
) {
    init {
        if (value.isBlank()) {
            throw ValidationException(CategoryErrorCatalog.NAME_BLANK, FIELD)
        }
        if (value.length < 3) {
            throw ValidationException(CategoryErrorCatalog.NAME_SHORT, FIELD, value)
        }
        if (value.length > 30) {
            throw ValidationException(CategoryErrorCatalog.NAME_LONG, FIELD, value)
        }
    }
}